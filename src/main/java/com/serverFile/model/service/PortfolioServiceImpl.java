package com.serverFile.model.service;

import com.serverFile.model.domain.*;
import com.serverFile.model.repository.PortfolioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class PortfolioServiceImpl implements PortfolioService {

    @Autowired
    private PortfolioRepository portfolioRepository;

    public Portfolio getPortfolio(String portfolioName) {

        return getExistingPortfolio(portfolioName);
    }

    @Override
    public List<Project> getProjects(String portfolioName) {
        return getExistingPortfolio(portfolioName).getProjects();
    }

    @Override
    public Project getProject(String portfolioName, String id) {

        return getExistingPortfolio(portfolioName).getProjects().stream()
                .filter(project -> project.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Project not found"));
    }

    @Override
    public Portfolio addPortfolio(Portfolio portfolio) {
        return portfolioRepository.save(portfolio);
    }

    @Override
    public Portfolio updatePortfolioInfo(String portfolioName, Portfolio portfolio) {
        Portfolio existingPortfolio = getExistingPortfolio(portfolioName);

        if (!Objects.equals(portfolio.getPortfolioName(), "")){
            existingPortfolio.setPortfolioName(portfolio.getPortfolioName());
        }
        if (!Objects.equals(portfolio.getEmail(), "")){
            existingPortfolio.setEmail(portfolio.getEmail());
        }
        return portfolioRepository.save(portfolio);
    }

    @Override
    public Portfolio updatePortfolioTitle(String portfolioName, Title title) {
        Portfolio existingPortfolio = getExistingPortfolio(portfolioName);

        if (!Objects.equals(title.getTitle(), "")){
            existingPortfolio.getTitle().setTitle(title.getTitle());
        }
        if (!Objects.equals(title.getSubtitle(), "")){
            existingPortfolio.getTitle().setSubtitle(title.getSubtitle());
        }
        if (!Objects.equals(title.getImage(), "")){
            existingPortfolio.getTitle().setImage(title.getImage());
        }
        if (!title.getIconImg().isEmpty()){
            existingPortfolio.getTitle().getIconImg().addAll(title.getIconImg());
        }
        if (!title.getIconLink().isEmpty()){
            existingPortfolio.getTitle().getIconLink().addAll(title.getIconLink());
        }

        return portfolioRepository.save(existingPortfolio);
    }

    @Override
    public Portfolio updatePortfolioAbout(String portfolioName, String about) {
        Portfolio existingPortfolio = getExistingPortfolio(portfolioName);

        existingPortfolio.setAbout(about);
        return portfolioRepository.save(existingPortfolio);
    }

    @Override
    public Project addProject(String portfolioName, Project project) {
        Portfolio existingPortfolio = getExistingPortfolio(portfolioName);

        existingPortfolio.getProjects().add(project);
        return portfolioRepository.save(existingPortfolio).getProjects().get(existingPortfolio.getProjects().size()-1);
    }

    @Override
    public Project updateProject(String portfolioName, Project project) {
        Portfolio existingPortfolio = getExistingPortfolio(portfolioName);

        Project existingProject = existingPortfolio.getProjects().stream()
                .filter(p -> p.getId().equals(project.getId()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Project not found"));
        if (!Objects.equals(project.getTitle(), "")){
            existingProject.setTitle(project.getTitle());
        }
        if (!Objects.equals(project.getSubtitle(), "")){
            existingProject.setSubtitle(project.getSubtitle());
        }
        if (!Objects.equals(project.getDescription(), "")){
            existingProject.setDescription(project.getDescription());
        }
        if (!project.getImages().isEmpty()){
            existingProject.getImages().addAll(project.getImages());
        }
        return portfolioRepository.save(existingPortfolio).getProjects().stream()
                .filter(p -> p.getId().equals(project.getId()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Project not found"));
    }

    @Override
    public void deleteProject(String portfolioName, String id) {
        Portfolio existingPortfolio = getExistingPortfolio(portfolioName);
        existingPortfolio.getProjects().removeIf(project -> project.getId().equals(id));
        portfolioRepository.save(existingPortfolio);
    }



    @Override
    public Tag addTag(String portfolioName, Tag tag) {
        Portfolio existingPortfolio = getExistingPortfolio(portfolioName);

        existingPortfolio.getTags().add(tag);
        return portfolioRepository.save(existingPortfolio).getTags().get(existingPortfolio.getTags().size()-1);
    }

    @Override
    public Tag updateTag(String portfolioName, Tag tag) {
        Portfolio existingPortfolio = getExistingPortfolio(portfolioName);
        Tag existingTags = existingPortfolio.getTags().stream()
                .filter(t -> t.getId().equals(tag.getId()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Tag not found"));
        if (!Objects.equals(tag.getName(), "")){
            existingTags.setName(tag.getName());
        }
        return portfolioRepository.save(existingPortfolio).getTags().stream()
                .filter(t -> t.getId().equals(tag.getId()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Tag not found"));
    }

    @Override
    public void deleteTag(String portfolioName, String id) {
        Portfolio existingPortfolio = getExistingPortfolio(portfolioName);
        existingPortfolio.getTags().removeIf(tag -> tag.getId().equals(id));
        portfolioRepository.save(existingPortfolio);
    }

    //Util Methods
    private Portfolio getExistingPortfolio(String portfolioName) {
        Portfolio existingPortfolio = portfolioRepository.findByPortfolioName(portfolioName);
        if (existingPortfolio == null) {
            throw new RuntimeException("Portfolio not found");
        }
        return existingPortfolio;
    }


}
