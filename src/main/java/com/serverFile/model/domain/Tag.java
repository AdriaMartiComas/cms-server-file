package com.serverFile.model.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
public class Tag {

    @Id
    private String id;
    private String name;

    public Tag(String name) {
        this.name = name;
    }
}
