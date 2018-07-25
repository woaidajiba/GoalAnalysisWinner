package com.goalanalysis.inter.mapper;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.goalanalysis.inter.entity.MatchScore;
@Repository
public interface MatchScoreMapper extends JpaRepository<MatchScore,String>{
     public List<MatchScore> findAllByMatchId(int matchId);
}
