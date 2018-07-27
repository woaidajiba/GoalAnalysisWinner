package com.goalanalysis.inter.mapper;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import com.goalanalysis.inter.entity.MatchOddDocument;
@Component
public interface MatchOddDocumentMapper extends MongoRepository<MatchOddDocument,String>  {
    public List<MatchOddDocument> findAllByMatchId(int matchId);

}
