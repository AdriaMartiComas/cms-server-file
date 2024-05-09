package com.serverFile.repository;

import com.serverFile.domain.Portfolio;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PortfolioRepository extends MongoRepository<Portfolio, String> {
    Portfolio findByPortfolioName(String portfolioName);
}
