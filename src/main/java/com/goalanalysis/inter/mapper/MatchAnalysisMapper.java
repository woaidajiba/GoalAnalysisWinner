package com.goalanalysis.inter.mapper;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.goalanalysis.inter.entity.MatchAnalysis;
 
public interface MatchAnalysisMapper extends JpaRepository<MatchAnalysis,String>{
	public List<MatchAnalysis> findAllByMatchId(int matchId);
}
