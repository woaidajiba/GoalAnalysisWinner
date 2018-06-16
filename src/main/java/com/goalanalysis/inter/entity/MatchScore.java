package com.goalanalysis.inter.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;

import lombok.Data;
@Data
public class MatchScore {
 
      private String matchId;
      private String homeTeamId;
      private String homeTeamSname;
      private String awayTeamId;
      private String awayTeamSname;
      private int homeTeamGoal;
      private int awayTeamGoal;
      private int halfHomeTeamGoal;
      private int halfAwayTeamGoal;
      private int homeTeamCorner;
      private int awayTeamCorner;
      private int halfHomeTeamCorner;
      private int halfAwayTeamCorner;
      private Date StartTime;
}
