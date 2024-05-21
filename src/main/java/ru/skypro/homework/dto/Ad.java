package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
//@Schema//( type = "string", format = "binary")
public class Ad {
    private String pk;
    private Integer author;
    @Schema( type = "string", format = "binary")
    private Image image;
    private Integer price;
    private String title;
}
