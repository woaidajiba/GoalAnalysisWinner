package com.goalanalysis.inter.mapper;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Component;

import com.goalanalysis.inter.entity.MatchOddDocument;
@Component
public interface MatchOddDocumentMapper extends JpaRepository<MatchOddDocument,String>  {
    public List<MatchOddDocument> findAllByMatchId(int matchId);

}
