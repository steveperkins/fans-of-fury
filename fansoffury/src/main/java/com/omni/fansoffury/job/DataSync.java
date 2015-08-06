package com.omni.fansoffury.job;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.HttpURLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Component
public class DataSync {

	private static Logger logger = LoggerFactory.getLogger(DataSync.class);

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");


	@Autowired
	protected JdbcTemplate jdbc;

	@Scheduled(fixedRate = 1000 * 60) // sync every minute
	public void sendDataToPublicServer() {
		try {
			List<Map<String, Object>> scoreSummary =
					jdbc.queryForList("SELECT ps.qr_code, count(1) AS player_score " +
							"FROM player_session ps " +
							"INNER JOIN score s ON ps.id = s.session_id " +
							"GROUP BY ps.qr_code");
	
			logger.debug("About to sync " + scoreSummary.size() + " player scores");
			
			List<ScoreData> toTransmit = new ArrayList<ScoreData>();
			
			for (int i = 0; i < scoreSummary.size(); i++) {
				toTransmit.add(new ScoreData(scoreSummary.get(i)));
			}
	
			ObjectMapper mapper = new ObjectMapper();
	    
			String jsonString = mapper.writeValueAsString(toTransmit);
			
			URLConnection connection = new URL("https://tc2015.omniresources.com/api/Sync?p=Nh1WBLc1msU6fc8As9pn").openConnection();
			connection.setDoOutput(true);
			connection.setRequestProperty("Content-Type", "application/json");
			
			try (OutputStream output = connection.getOutputStream())
			{
				output.write(jsonString.getBytes());
			}
			
			connection.connect();
			int response = ((HttpURLConnection)connection).getResponseCode();
			
			if (response != 200) {
				logger.error("Got response code " + response + " back from server... :(");
			} else {
				logger.debug("Score sync successful!");
			}
		} catch (Exception e) {
			logger.error("Error syncing scores...", e);
		} 
	}
	
	private class ScoreData
    {
		private String userId;
		private long score;
		
		public ScoreData(Map<String, Object> data) {
			this.setUserId((String)data.get("qr_code"));
			this.setScore((long)data.get("player_score"));
		}
		
		public String getUserId() {
			return userId;
		}
		
		public void setUserId(String userId) {
			this.userId = userId;
		}
		
		public long getScore() {
			return score;
		}
		
		public void setScore(long score) {
			this.score = score;
		}
		
    }

}
