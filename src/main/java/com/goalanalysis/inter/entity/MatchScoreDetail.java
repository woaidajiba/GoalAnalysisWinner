package com.goalanalysis.inter.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document
public class MatchScoreDetail {
    private int matchId;
    private int league;
    private int homeTeamId;
    private int awayTeamId;
    private String homeTeamTname;
    private String homeTeamSname;
    private String homeTeamEname;
    private String awayTeamTname;
    private String awayTeamSname;
    private String awayTeamEname;
    private String startTime;
    private String timeString;
    //比赛状态，未开始0，上半场1，中场2,下半场3,完场-1
    private int gameStatus;
    private int homeTeamGoal;
    private int awayTeamGoal;
    private int homeTeamHalfGoal;
    private int awayTeamHalfGoal;
    private int homeTeamRedCard;
    private int awayTeamRedCard;
    private int homeTeamYellowCard;
    private int awayTeamYellowCard;
    private String homeTeamRank;
    private String awayTeamRank;
    private String leagueName;
    private String isOdds;
    private double asiaOdd;
    private String tvShows;
    private String tvShowsLink;
    private String unknow2;
    private String unknow3;
    private String unknow4;
    private String unknow5;
    private String unknow6;
    private double goalOdd;
    private int homeTeamCorner;
    private int awayTeamCorner;
    private int homeTeamunknow1;
    private int awayTeamunknow1;
}
