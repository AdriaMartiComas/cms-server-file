package com.serverFile.model.service;

import com.serverFile.model.domain.Portfolio;
import com.serverFile.model.domain.Project;
import com.serverFile.model.domain.Tag;
import com.serverFile.model.domain.Title;
import com.serverFile.model.dto.InfoDto;

import java.util.List;


public interface PortfolioService {
    //Public methods
    Portfolio getPortfolio(String portfolioName);
    List<Project> getProjects(String portfolioName);
    Project getProject(String portfolioName, String id);

    //Only admin users can create a Portfolio
    Portfolio addPortfolio(Portfolio portfolio);

    //Only owner of the Portfolio can update it
    Portfolio updatePortfolioInfo(String portfolioName, InfoDto infoDto);
    Portfolio updatePortfolioTitle(String portfolioName, Title title);
    Portfolio updatePortfolioAbout(String portfolioName, String about);
    Project addProject(String portfolioName, Project project);
    Project updateProject(String portfolioName, Project project);
    void deleteProject(String portfolioName, String id);
    Tag addTag(String portfolioName, Tag tag);
    Tag updateTag(String portfolioName, Tag tag);
    void deleteTag(String portfolioName, String id);
}
