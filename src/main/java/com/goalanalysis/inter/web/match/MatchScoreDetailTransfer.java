package com.goalanalysis.inter.web.match;

import java.util.Map;

import com.goalanalysis.inter.entity.MatchScoreDetail;

public class MatchScoreDetailTransfer {
    MatchScoreDetail getMatchScoreDetail(Map<String,Object> map) {
    	MatchScoreDetail match=new MatchScoreDetail();
    	match.setMatchId((int)map.get("0"));
   	    match.setLeague((int)map.get("1"));
   	    match.setHomeTeamId((int)map.get("2"));
   	    match.setAwayTeamId((int)map.get("3"));
   	    match.setHomeTeamSname((String)map.get("4"));
   	    match.setHomeTeamTname((String)map.get("5"));
   	    match.setHomeTeamEname((String)map.get("6"));
   	    match.setAwayTeamSname((String)map.get("7"));
   	    match.setAwayTeamTname((String)map.get("8"));
   	    match.setAwayTeamEname((String)map.get("9"));
   	    match.setStartTime((String)map.get("10"));
   	    match.setTimeString((String)map.get("11"));
   	    match.setGameStatus((int)map.get("12"));
   	    match.setHomeTeamGoal((int)map.get("13"));
   	    match.setAwayTeamGoal((int)map.get("14"));
   	    if (map.get("15") !=null) {
   	    match.setHomeTeamHalfGoal((int)map.get("15"));
   	    }
   	    if (map.get("16") !=null) {
   	    match.setAwayTeamHalfGoal((int)map.get("16"));
   	    }
   	    match.setHomeTeamRedCard((int)map.get("17"));
   	    match.setAwayTeamRedCard((int)map.get("18"));
   	    match.setHomeTeamYellowCard((int)map.get("19"));
   	    match.setAwayTeamYellowCard((int)map.get("20"));
   	    match.setHomeTeamRank((String)map.get("21"));
   	    match.setAwayTeamRank((String)map.get("22"));
   	    match.setIsOdds((String)map.get("24"));
   	    if (map.get("25") !=null) {
   	    match.setAsiaOdd(Double.valueOf((map.get("25").toString())));
   	    }
   	    if (map.get("33")  !=null) {
   	    match.setGoalOdd(Double.valueOf((map.get("33").toString())));
   	    }
   	    match.setHomeTeamCorner((int)map.get("34"));
   	    match.setAwayTeamCorner((int)map.get("35"));
    return match;
    }
}