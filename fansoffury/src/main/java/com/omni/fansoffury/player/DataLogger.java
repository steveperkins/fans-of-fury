package com.omni.fansoffury.player;


import com.omni.fansoffury.headset.service.BluetoothSocketService;
import com.omni.fansoffury.level.LevelingStrategy;
import com.omni.fansoffury.model.event.ScoreChangedEvent;
import com.sperkins.mindwave.event.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;

import javax.annotation.PostConstruct;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

public class DataLogger implements ScoreListener, MindwaveEventListener {

	@Autowired
	private BluetoothSocketService bluetoothSocketService;

	@Autowired
	private ScoreService scoreService;
	
	@Autowired
	private LevelingStrategy levelingStrategy;

	@Autowired
	private JdbcTemplate jdbcTemplate;


	@Override
	public void onEvent(final Event event) {
		final Integer value;

		if (EventType.ATTENTION.equals(event.getEventType())) {
			value = ((AttentionEvent) event).getValue();
		} else if(EventType.MEDITATION.equals(event.getEventType())) {
			value = ((MeditationEvent) event).getValue();
		} else {
			// todo figure out if we can store the other types of measures
			// This would be a good spot to store raw EEG values
			return;
		}

		jdbcTemplate.execute("INSERT INTO measurement(session_id, measure_type, measure_datetime, value) " +
						"SELECT id, ?, ?, ? FROM player_session WHERE headset = ? AND end_datetime IS NULL",
				new PreparedStatementCallback<Boolean>() {
					@Override
					public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
						ps.setString(1, event.getEventType().toString());
						// todo can the date be sent as part of the event?
						ps.setTimestamp(2, new java.sql.Timestamp(new Date().getTime()));
						ps.setInt(3, value);
						ps.setString(4, event.getDeviceAddress());
						return ps.execute();
					}
				});

	}

	@Override
	public void scoreChanged(final ScoreChangedEvent event) {
		jdbcTemplate.execute("INSERT INTO score(session_id, score_datetime)" +
				"SELECT id, ? FROM player_session WHERE headset = ? AND end_datetime IS NULL", new PreparedStatementCallback<Boolean>() {

			@Override
			public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
				// todo can the date be sent as part of the event?
				ps.setTimestamp(1, new java.sql.Timestamp(new Date().getTime()));
				ps.setString(2, event.getHeadset().getId());
				return ps.execute();
			}
		});
	}

	@PostConstruct
	private void postConstruct() {
		// end all session that were left open.
		jdbcTemplate.update("UPDATE player_session set end_datetime = ? where end_datetime is null", new Timestamp(new Date().getTime()));

		bluetoothSocketService.addListener(this);
		scoreService.addListener(this);

	}
}
