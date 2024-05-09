package com.serverFile.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.serverFile.util.ObjectIdDeserializer;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Project {

    @Id
    @Setter(AccessLevel.NONE)
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonDeserialize(using = ObjectIdDeserializer.class)
    private ObjectId id;
    private String projectName;
    private String projSubtitle;
    private String projDescription;
    private List<String> projImages;

    //Constructor creates id and the rest is empty
    public Project() {
        this.id = new ObjectId();
        this.projectName = "";
        this.projSubtitle = "";
        this.projDescription = "";
        this.projImages = List.of();
    }
}
