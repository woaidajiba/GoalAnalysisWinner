package com.goalanalysis.inter.mapper;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.goalanalysis.inter.entity.MatchScore;
import com.goalanalysis.inter.entity.ProxyIP;

@Repository
@Service
@Component
public interface ProxyIPMapper extends MongoRepository<ProxyIP,String> {
    public List<ProxyIP> findOneByNoGreaterThan(int i);
}
