package ru.skypro.homework.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Comment {

    private Integer author;
    private String authorImage;
    private String authorFirstName;
    private int createdOn;
    private Integer pk;
    private String text;

}
