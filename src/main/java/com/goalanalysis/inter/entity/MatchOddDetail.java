package com.goalanalysis.inter.entity;
import java.util.Date;

import lombok.Data;
@Data
public class MatchOddDetail {
       private String matchId;
       private String companyId;
       private String companyName;
       private Date   date;
       private double goalOdd;
       private double asiaOdd;
       private double europeOdd;
       private double homeGoalOddValue;
       private double awayGoalOddValue;
       private double homeAsiaOddValue;
       private double awayAsiaOddValue;
       private double homeEuropeOddValue;
       private double awayEuropeOddValue;
       private double peakEuropeOddValue;
       //滚球：1，赛前：0
       private int oddStatus;
}
