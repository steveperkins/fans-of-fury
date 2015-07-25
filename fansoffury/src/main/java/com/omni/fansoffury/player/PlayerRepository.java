package com.omni.fansoffury.player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.WeakHashMap;

import com.omni.fansoffury.level.ScoreLevelingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.omni.fansoffury.model.Headset;
import com.omni.fansoffury.model.Player;
import com.sperkins.mindwave.event.EventType;

/**
 * Repository for players and player sessions.
 */
@Repository
public class PlayerRepository {
	@Autowired
	protected JdbcTemplate jdbc;

	// cache to ensure that if a reference is being held to an existing player (e.g. one that is playing) that
	// the one held in memory is returned to everything is consistent.
	// TODO figure out a better way to have a shared player object
	private final Set<Player> playerCache = Collections.newSetFromMap(new WeakHashMap<Player, Boolean>());

	private static RowMapper<Player> playerMapper = new RowMapper<Player>() {
		@Override
		public Player mapRow(ResultSet resultSet, int i) throws SQLException {
			return new Player(resultSet.getString("qr_code"));
		}
	};



	public Player findOrCreateByQRCode(final String qrCode) {
		// figure out a better way of sharing active players
		synchronized (playerCache) {
			for (Player player : playerCache) {
				if (player.getId().equals(qrCode)) {
					return player;
				}
			}

			List<Player> players = jdbc.query("SELECT p.qr_code FROM player p WHERE p.qr_code = ?", playerMapper, qrCode);
			if (players.size() > 0) {
				final Player player = players.get(0);
				// Defaults levels
				player.setAttentionLevel(0.0);
				player.setMeditationLevel(0.0);

				jdbc.query(
						"SELECT measurement_type, count(1) as lifetime_points " +
						"FROM " +
						"player_session ps " +
						"INNER JOIN score s ON s.session_id = ps.id " +
						"WHERE ps.qr_code = ? " +
						"GROUP BY measurement_type", new ResultSetExtractor<Player>() {
					@Override
					public Player extractData(ResultSet resultSet) throws SQLException, DataAccessException {
						while (resultSet.next()) {
							EventType eventType = EventType.valueOf(resultSet.getString("measurement_type"));
							double level = ScoreLevelingStrategy.LEVEL_INCREMENT * resultSet.getInt("lifetime_points");
							if (eventType.equals(EventType.ATTENTION)) {
								player.setAttentionLevel(level);
							} else if (eventType.equals(EventType.MEDITATION)) {
								player.setMeditationLevel(level);
							}
						}
						return player;
					}
				},
				player.getId());


				// Assume that when a new player is retrieved from the DB that a new session is starting
				player.setScore(0);

				playerCache.add(player);
				return player;
			} else {
				jdbc.execute("INSERT INTO player (qr_code) VALUES(?)", new PreparedStatementCallback<Boolean>() {
					@Override
					public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
						ps.setString(1, qrCode);
						return ps.execute();
					}
				});

				Player player = new Player();
				player.setId(qrCode);
				playerCache.add(player);
				return player;
			}

		}
	}

	public void startPlayerSession(final Headset headset) {
		if (!(headset.getPlayer().getMeasurementType().equals(EventType.ATTENTION) || headset.getPlayer().getMeasurementType().equals(EventType.MEDITATION))) {
			throw new IllegalArgumentException("player must play with either meditation or attention.  Got: " + headset.getPlayer().getMeasurementType());
		}
		jdbc.execute("INSERT INTO player_session (qr_code, start_datetime, headset, fan, measurement_type) " +
				"VALUES(?,?,?,?,?)", new PreparedStatementCallback<Boolean>() {

			@Override
			public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
				ps.setString(1, headset.getPlayer().getId());
				ps.setTimestamp(2, new java.sql.Timestamp(new Date().getTime()));
				ps.setString(3, headset.getId());
				ps.setString(4, (headset.getDevice() == null) ? null : headset.getDevice().getId());
				ps.setString(5, headset.getPlayer().getMeasurementType().toString());
				return ps.execute();
			}
		});
	}

	public void endPlayerSession(final Headset headset) {
		jdbc.execute("UPDATE player_session SET end_datetime = ? WHERE headset=? AND end_datetime IS NULL",
				new PreparedStatementCallback<Boolean>() {
					@Override
					public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
						ps.setTimestamp(1, new java.sql.Timestamp(new Date().getTime()));
						ps.setString(2, headset.getId());
						return ps.execute();
					}
				});
	}
}
