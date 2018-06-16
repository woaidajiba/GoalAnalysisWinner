package com.goalanalysis.inter.mapper;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import com.goalanalysis.inter.entity.ScoreTableDocument;
@Component
public interface ScoreTableDocumentMapper extends MongoRepository<ScoreTableDocument,String> {
 }
