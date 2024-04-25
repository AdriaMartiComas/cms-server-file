package com.serverFile.model.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Title {

    private String title;
    private String subtitle;
    private String image;
    private List<String> iconImg;
    private List<String> iconLink;
}
