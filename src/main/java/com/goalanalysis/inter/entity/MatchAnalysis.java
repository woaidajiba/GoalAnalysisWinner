package com.goalanalysis.inter.entity;
import java.util.Date;

import org.springframework.data.annotation.Id;

import lombok.Data;
@Data
public class MatchAnalysis {
    private int matchId;
    private double finalGoal;
    private Date createTime;
    private Date updateTime;
}
