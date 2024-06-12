package com.bar.games.mouseracegame.mouserace;

import com.bar.games.mouseracegame.redis.RedisClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Leaderboard {
    private static final String LEADERBOARD_KEY = "leaderboard";
    private RedisClient redisClient;
    private ObjectMapper objectMapper;

    public Leaderboard() {
        redisClient = new RedisClient();
        objectMapper = new ObjectMapper();
    }

    public void addScore(String playerName, long elapsedTime) {
        try {
            List<Score> scores = getScoresFromRedis();
            scores.add(new Score(playerName, elapsedTime));
            scores.sort(Comparator.comparingLong(Score::getElapsedTime));
            saveScoresToRedis(scores);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getTopScoresString() {
        List<Score> scores = getScoresFromRedis();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < Math.min(scores.size(), 3); i++) {
            Score score = scores.get(i);
            sb.append(String.format("%d. %s - %d seconds%n", i + 1, score.getPlayerName(), score.getElapsedTime()));
        }
        return sb.toString();
    }

    private List<Score> getScoresFromRedis() {
        String scoresJson = redisClient.get(LEADERBOARD_KEY);
        if (scoresJson == null) {
            return new ArrayList<>();
        }
        try {
            return objectMapper.readValue(scoresJson, objectMapper.getTypeFactory().constructCollectionType(List.class, Score.class));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private void saveScoresToRedis(List<Score> scores) {
        try {
            String scoresJson = objectMapper.writeValueAsString(scores);
            redisClient.set(LEADERBOARD_KEY, scoresJson);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    /**
     * Represents a score entry in the leaderboard.
     */
    private static class Score {
        public String playerName;
        public long elapsedTime;

        public Score() {}

        public Score(String playerName, long elapsedTime) {
            this.playerName = playerName;
            this.elapsedTime = elapsedTime;
        }

        public String getPlayerName() {
            return playerName;
        }

        public long getElapsedTime() {
            return elapsedTime;
        }
    }
}
