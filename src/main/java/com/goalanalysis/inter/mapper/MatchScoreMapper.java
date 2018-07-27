package com.goalanalysis.inter.mapper;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.goalanalysis.inter.entity.MatchScore;
@Repository
public interface MatchScoreMapper extends MongoRepository<MatchScore,String>{
     public List<MatchScore> findAllByMatchId(int matchId);
}
