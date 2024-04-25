package com.serverFile.controller;

import com.serverFile.model.domain.Portfolio;
import com.serverFile.model.service.PortfolioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/portfolios")
public class PortfolioController {

    @Autowired
    private PortfolioServiceImpl portfolioService;

    //TODO
    //get all projects from a portfolio - public GET
    @GetMapping("/{owner}/projects")
    public Portfolio getPortfolioProjects(String owner){
        return portfolioService.getPortfolio(owner);
    }

    //Post new project to a portfolio - private to porfolio owner POST
    @PostMapping("/{owner}/projects")
    public void addProjectToPortfolio(@PathVariable String owner, @RequestBody Portfolio portfolio){
        //TODO
        Portfolio porfolio = portfolioService.getPortfolio(owner);

    }




}
