package com.serverFile.model.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@Document(collection = "portfolios")
public class Portfolio {

    @Id
    @Setter(AccessLevel.NONE)
    private ObjectId id;
    @Setter(AccessLevel.NONE)
    private String owner;
    @Indexed(unique = true)
    private String portfolioName;
    private Title title;
    private String about;
    private String email;
    private List<Project> projects;
    private List<Tag> tags;

    //constructor generates id, owner is set by service input and the rest is empty
    public Portfolio(String owner) {
        this.owner = owner;
        this.title = new Title();
        this.about = "";
        this.email = "";
        this.projects = List.of();
        this.tags = List.of();
    }
}
