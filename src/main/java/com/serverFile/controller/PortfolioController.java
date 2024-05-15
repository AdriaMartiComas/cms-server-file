package com.serverFile.controller;

import com.serverFile.domain.Portfolio;
import com.serverFile.domain.Project;
import com.serverFile.domain.Tag;
import com.serverFile.domain.Title;
import com.serverFile.dto.InfoDto;
import com.serverFile.service.PortfolioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/portfolio")
public class PortfolioController {

    private final PortfolioServiceImpl portfolioService;

    @Autowired
    public PortfolioController(PortfolioServiceImpl portfolioService) {
        this.portfolioService = portfolioService;
    }

    //Public methods

    //GET FULL PORTFOLIO
    @GetMapping("/{portfolioName}")
    public ResponseEntity<Portfolio> getPortfolio(@PathVariable String portfolioName) {
        return ResponseEntity.ok(portfolioService.getPortfolio(portfolioName));
    }

    //GET ALL PROJECTS
    @GetMapping("/{portfolioName}/projects")
    public ResponseEntity<List<Project>> getProjects(@PathVariable String portfolioName) {
        return ResponseEntity.ok(portfolioService.getProjects(portfolioName));
    }

    //GET PROJECT
    @GetMapping("/{portfolioName}/projects/{projectId}")
    public ResponseEntity<Project> getProject(@PathVariable String portfolioName, @PathVariable String projectId) {
        try {
            return ResponseEntity.ok(portfolioService.getProject(portfolioName, projectId));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    //TODO: Add security layer to the following methods

    //Only admin methods
    //CREATE PORTFOLIO
    @PostMapping("/addPortfolio/{portfolioName}/{owner}")
    public ResponseEntity<Portfolio> addPortfolio(@PathVariable String portfolioName, @PathVariable String owner) {
        Portfolio portfolio = new Portfolio(owner);
        portfolio.setPortfolioName(portfolioName);
        return ResponseEntity.ok(portfolioService.addPortfolio(portfolio));
    }

    //User methods
    //UPDATE PORTFOLIO INFO
    @PutMapping("/{portfolioName}/updateInfo")
    public ResponseEntity<Portfolio> updatePortfolioInfo(@PathVariable String portfolioName, @RequestBody InfoDto infoDto) {
        return ResponseEntity.ok(portfolioService.updatePortfolioInfo(portfolioName, infoDto));
    }

    //UPDATE PORTFOLIO TITLE
    @PutMapping("/{portfolioName}/updateTitle")
    public ResponseEntity<Portfolio> updatePortfolioTitle(@PathVariable String portfolioName, @RequestBody Title title) {
        return ResponseEntity.ok(portfolioService.updatePortfolioTitle(portfolioName, title));
    }

    //UPDATE PORTFOLIO ABOUT
    @PutMapping("/{portfolioName}/updateAbout")
    public ResponseEntity<Portfolio> updatePortfolioAbout(@PathVariable String portfolioName, @RequestBody String about) {
        return ResponseEntity.ok(portfolioService.updatePortfolioAbout(portfolioName, about));
    }

    //ADD PROJECT
    @PostMapping("/{portfolioName}/addProject")
    public ResponseEntity<Project> addProject(@PathVariable String portfolioName, @RequestBody Project project) {
        return ResponseEntity.ok(portfolioService.addProject(portfolioName, project));
    }

    //UPDATE PROJECT
    @PutMapping("/{portfolioName}/updateProject")
    public ResponseEntity<Project> updateProject(@PathVariable String portfolioName, @RequestBody Project project) {
        return ResponseEntity.ok(portfolioService.updateProject(portfolioName, project));
    }

    //DELETE PROJECT
    @DeleteMapping("/{portfolioName}/deleteProject/{projectId}")
    public ResponseEntity<Void> deleteProject(@PathVariable String portfolioName, @PathVariable String projectId) {
        portfolioService.deleteProject(portfolioName, projectId);
        return ResponseEntity.noContent().build();
    }

    //ADD TAG
    @PostMapping("/{portfolioName}/addTag")
    public ResponseEntity<Tag> addTag(@PathVariable String portfolioName, @RequestBody Tag tag) {
        return ResponseEntity.ok(portfolioService.addTag(portfolioName, tag));
    }

    //DELETE TAG
    @DeleteMapping("/{portfolioName}/deleteTag/{tagId}")
    public ResponseEntity<Void> deleteTag(@PathVariable String portfolioName, @PathVariable String tagId) {
        portfolioService.deleteTag(portfolioName, tagId);
        return ResponseEntity.status(204).build();
    }

}
