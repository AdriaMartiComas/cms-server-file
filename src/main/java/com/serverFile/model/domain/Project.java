package com.serverFile.model.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Project {

    @Id
    private String id;
    private String title;
    private String subtitle;
    private String description;
    private List<String> images;
}
