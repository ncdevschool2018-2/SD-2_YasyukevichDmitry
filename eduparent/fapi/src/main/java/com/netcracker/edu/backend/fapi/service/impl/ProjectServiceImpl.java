package com.netcracker.edu.backend.fapi.service.impl;

import com.netcracker.edu.backend.fapi.model.Project;
import com.netcracker.edu.backend.fapi.service.ProjectService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {
    @Value("${backend.server.url}")
    private  String backendUrl;

    @Override
    public List<Project> getAllProjects() {
        RestTemplate restTemplate = new RestTemplate();
        Project[] projects = restTemplate.getForObject(backendUrl + "/api/projects/all",Project[].class);
        return Arrays.asList(projects);
    }

    @Override
    public Project saveProject(Project project) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForEntity(backendUrl + "/api/projects",project,Project.class).getBody();
    }
}
