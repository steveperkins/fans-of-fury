package com.omni.fansoffury.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;


@Component
public class DataSync {

	private static Logger logger = LoggerFactory.getLogger(DataSync.class);

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");


	@Autowired
	protected JdbcTemplate jdbc;

	@Scheduled(fixedRate = 1000 * 60 * 60) // sync every hour
	public void sendDataToPublicServer() {
		List<Map<String, Object>> scoreSummary =
				jdbc.queryForList("SELECT ps.qr_code, count(1) AS player_score " +
						"FROM player_session ps " +
						"INNER JOIN score s ON ps.id = s.session_id " +
						"GROUP BY ps.qr_code");

		logger.debug("About to sync " + scoreSummary.size() + " player scores");

		// todo do something awesome with the score summary;
	}

}
