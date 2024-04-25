package com.serverFile.model.repository;

import com.serverFile.model.domain.Portfolio;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PortfolioRepository extends MongoRepository<Portfolio, String> {
    Portfolio findByPortfolioName(String portfolioName);
}
