package com.goalanalysis.inter.mapper;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import com.goalanalysis.inter.entity.MatchAnalysisDocument;
import com.goalanalysis.inter.entity.MatchOddDocument;
@Component
public interface MatchAnalysisDocumentMapper extends MongoRepository<MatchAnalysisDocument, String> {
    public List<MatchAnalysisDocument> findAllByMatchId(int matchId);
}
