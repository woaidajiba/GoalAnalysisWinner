package com.goalanalysis.inter.mapper;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.goalanalysis.inter.entity.MatchAnalysisDocument;
import com.goalanalysis.inter.entity.MatchOddDocument;
@Component
@Repository
public interface MatchAnalysisDocumentMapper extends MongoRepository<MatchAnalysisDocument, String> {
    public List<MatchAnalysisDocument> findAllByMatchId(int matchId);
}
