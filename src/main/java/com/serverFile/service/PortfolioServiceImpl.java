package com.serverFile.service;

import com.serverFile.domain.Portfolio;
import com.serverFile.domain.Project;
import com.serverFile.domain.Tag;
import com.serverFile.domain.Title;
import com.serverFile.dto.InfoDto;
import com.serverFile.exception.EntityAlreadyExistsException;
import com.serverFile.repository.PortfolioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;

import java.util.List;
import java.util.Objects;

@Service
public class PortfolioServiceImpl implements PortfolioService {

    private final PortfolioRepository portfolioRepository;
    @Autowired
    public PortfolioServiceImpl(PortfolioRepository portfolioRepository) {
        this.portfolioRepository = portfolioRepository;
    }

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
        if (portfolioRepository.findByPortfolioName(portfolio.getPortfolioName()) != null){
            throw new EntityAlreadyExistsException("Portfolio already exists");
        }

        return portfolioRepository.save(portfolio);
    }

    @Override
    public Portfolio updatePortfolioInfo(String existingPortfolioName, InfoDto infoDto) {
        Portfolio existingPortfolio = getExistingPortfolio(existingPortfolioName);
        if (portfolioRepository.findByPortfolioName(infoDto.getPortfolioName()) != null){
            throw new EntityAlreadyExistsException("Portfolio name already exists");
        }

        if (!Objects.equals(infoDto.getPortfolioName(), "")){
            existingPortfolio.setPortfolioName(infoDto.getPortfolioName());
        }
        if (!Objects.equals(infoDto.getEmail(), "")){
            existingPortfolio.setEmail(infoDto.getEmail());
        }
        return portfolioRepository.save(existingPortfolio);
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

        if (existingPortfolio.getProjects().stream().anyMatch(p -> p.getProjectName().equals(project.getProjectName()))){
            throw new EntityAlreadyExistsException("Project already exists");
        }

        Project newProject = new Project();
        newProject.setProjectName(project.getProjectName());
        newProject.setProjSubtitle(project.getProjSubtitle());
        newProject.setProjDescription(project.getProjDescription());
        newProject.setProjImages(project.getProjImages());

        existingPortfolio.getProjects().add(newProject);

        return portfolioRepository.save(existingPortfolio).getProjects().get(existingPortfolio.getProjects().size()-1);
    }

    @Override
    public Project updateProject(String portfolioName, Project project) {
        Portfolio existingPortfolio = getExistingPortfolio(portfolioName);

        Project existingProject = existingPortfolio.getProjects().stream()
                .filter(p -> p.getId().equals(project.getId()))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Project not found"));

        existingProject.setProjectName(project.getProjectName());
        existingProject.setProjSubtitle(project.getProjSubtitle());
        existingProject.setProjDescription(project.getProjDescription());
        existingProject.getProjImages().addAll(project.getProjImages());

        return portfolioRepository.save(existingPortfolio).getProjects().stream()
                .filter(p -> p.getId().equals(project.getId()))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Project not found"));
    }

    @Override
    public void deleteProject(String portfolioName, String id) {
        Portfolio existingPortfolio = getExistingPortfolio(portfolioName);

        boolean removed = existingPortfolio
                .getProjects().
                removeIf(project -> {
                    return project.getId().toString().equals(id);
                });
        if (!removed){
            throw new EntityNotFoundException("Project not found");
        }

        portfolioRepository.save(existingPortfolio);
    }

    @Override
    public Tag addTag(String portfolioName, Tag tag) {
        Portfolio existingPortfolio = getExistingPortfolio(portfolioName);

        //Check if tag already exists
        if (existingPortfolio.getTags().stream().anyMatch(t -> t.getName().equals(tag.getName()))){
            throw new EntityAlreadyExistsException("Tag already exists");
        }

        existingPortfolio.getTags().add(tag);
        return portfolioRepository.save(existingPortfolio).getTags().get(existingPortfolio.getTags().size()-1);
    }

    @Override
    public void deleteTag(String portfolioName, String id) {
        Portfolio existingPortfolio = getExistingPortfolio(portfolioName);

        boolean removed = existingPortfolio
                .getTags().
                removeIf(tag -> {
                    return tag.getId().toString().equals(id);
                });
        if (!removed){
            throw new EntityNotFoundException("Tag not found");
        }

        portfolioRepository.save(existingPortfolio);
    }

    //Util Methods
    private Portfolio getExistingPortfolio(String portfolioName) {
        Portfolio existingPortfolio = portfolioRepository.findByPortfolioName(portfolioName);
        if (existingPortfolio == null) {
            throw new EntityNotFoundException("Portfolio not found");
        }
        return existingPortfolio;
    }


}
