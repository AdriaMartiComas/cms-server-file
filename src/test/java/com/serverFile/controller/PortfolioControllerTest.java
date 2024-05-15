package com.serverFile.controller;

import com.serverFile.domain.Portfolio;
import com.serverFile.domain.Project;
import com.serverFile.service.PortfolioServiceImpl;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class PortfolioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PortfolioServiceImpl portfolioServiceImpl;

    @BeforeEach
    public void setUp() {
        Portfolio portfolio = new Portfolio("owner");
        portfolio.setPortfolioName("portfolioName");

        Mockito.when(portfolioServiceImpl.getPortfolio("portfolioName")).thenReturn(portfolio);
    }

    @Test
    void getPortfolio() throws Exception{
        String portfolioName = "portfolioName";

        mockMvc.perform(get("/api/portfolio/" + portfolioName)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.portfolioName", is(portfolioName)))
                .andExpect(jsonPath("$.owner", is("owner")));
    }

    @Test
    void getProjects() throws Exception {
        String portfolioName = "portfolioName";
        List<Project> projects = new ArrayList<>();

        Project project1 = new Project();
        project1.setProjectName("Project 1");
        project1.setProjSubtitle("Subtitle 1");
        project1.setProjDescription("Description 1");
        project1.setProjImages(List.of("image1.jpg"));

        Project project2 = new Project();
        project2.setProjectName("Project 2");
        project2.setProjSubtitle("Subtitle 2");
        project2.setProjDescription("Description 2");
        project2.setProjImages(List.of("image2.jpg"));

        projects.add(project1);
        projects.add(project2);

        Mockito.when(portfolioServiceImpl.getProjects(portfolioName)).thenReturn(projects);

        mockMvc.perform(get("/api/portfolio/" + portfolioName + "/projects")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(projects.size())))
                .andExpect(jsonPath("$[0].projectName", is(project1.getProjectName())))
                .andExpect(jsonPath("$[0].projSubtitle", is(project1.getProjSubtitle())))
                .andExpect(jsonPath("$[0].projDescription", is(project1.getProjDescription())))
                .andExpect(jsonPath("$[0].projImages[0]", is(project1.getProjImages().get(0))))
                .andExpect(jsonPath("$[1].projectName", is(project2.getProjectName())))
                .andExpect(jsonPath("$[1].projSubtitle", is(project2.getProjSubtitle())))
                .andExpect(jsonPath("$[1].projDescription", is(project2.getProjDescription())))
                .andExpect(jsonPath("$[1].projImages[0]", is(project2.getProjImages().get(0))));
    }

    @Test
    void getProject() throws Exception {
        String portfolioName = "portfolioName";

        Project project = new Project();
        project.setProjectName("Project 1");
        project.setProjSubtitle("Subtitle 1");
        project.setProjDescription("Description 1");
        project.setProjImages(List.of("image1.jpg", "image2.jpg"));

        Mockito.when(portfolioServiceImpl.getProject(portfolioName, project.getId().toString())).thenReturn(project);

        mockMvc.perform(get("/api/portfolio/" + portfolioName + "/projects/" + project.getId().toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.projectName", is("Project 1")))
                .andExpect(jsonPath("$.projSubtitle", is("Subtitle 1")))
                .andExpect(jsonPath("$.projDescription", is("Description 1")))
                .andExpect(jsonPath("$.projImages", is(List.of("image1.jpg", "image2.jpg"))));
    }

    @Test
    void getProjectNotFound() throws Exception {
        String portfolioName = "portfolioName";
        ObjectId projectId = new ObjectId();
        String projectIdString = projectId.toString();

       /* Project project = new Project();
        project.setProjectName("Project 1");
        project.setProjSubtitle("Subtitle 1");
        project.setProjDescription("Description 1");
        project.setProjImages(List.of("image1.jpg", "image2.jpg"));*/

        Mockito.when(portfolioServiceImpl.getProject(portfolioName, projectIdString))
                .thenThrow(new RuntimeException("Project not found"));


        mockMvc.perform(get("/api/portfolio/" + portfolioName + "/projects/" + projectId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }




}
