package com.serverFile.controller;

import com.serverFile.model.domain.Portfolio;
import com.serverFile.model.domain.Project;
import com.serverFile.model.domain.Tag;
import com.serverFile.model.domain.Title;
import com.serverFile.model.dto.InfoDto;
import com.serverFile.model.service.PortfolioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/portfolio")
public class PortfolioController {

    @Autowired
    private PortfolioServiceImpl portfolioService;

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
    @GetMapping("/{portfolioName}/projects/{id}")
    public ResponseEntity<Project> getProject(@PathVariable String portfolioName, @PathVariable String id) {
        return ResponseEntity.ok(portfolioService.getProject(portfolioName, id));
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
    @PutMapping("/{portfolioName}/title")
    public ResponseEntity<Portfolio> updatePortfolioTitle(@PathVariable String portfolioName, @RequestBody Title title) {
        return ResponseEntity.ok(portfolioService.updatePortfolioTitle(portfolioName, title));
    }

    //UPDATE PORTFOLIO ABOUT
    @PutMapping("/{portfolioName}/about")
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
    @DeleteMapping("/{portfolioName}/deleteProject/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable String portfolioName, @PathVariable String id) {
        portfolioService.deleteProject(portfolioName, id);
        return ResponseEntity.noContent().build();
    }

    //ADD TAG
    @PostMapping("/{portfolioName}/addTag")
    public ResponseEntity<Tag> addTag(@PathVariable String portfolioName, @RequestBody Tag tag) {
        return ResponseEntity.ok(portfolioService.addTag(portfolioName, tag));
    }

    //UPDATE TAG
    @PutMapping("/{portfolioName}/updateTag")
    public ResponseEntity<Tag> updateTag(@PathVariable String portfolioName, @RequestBody Tag tag) {
        return ResponseEntity.ok(portfolioService.updateTag(portfolioName, tag));
    }

    //DELETE TAG
    @DeleteMapping("/{portfolioName}/deleteTag/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable String portfolioName, @PathVariable String id) {
        portfolioService.deleteTag(portfolioName, id);
        return ResponseEntity.status(204).build();
    }

}
