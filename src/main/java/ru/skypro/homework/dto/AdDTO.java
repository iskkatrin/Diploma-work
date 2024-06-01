package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
//@Schema//( type = "string", format = "binary")
public class AdDTO {
    private Integer author;
    @Schema( type = "string", format = "binary")
    private String image;
    private Integer pk;
    private Integer price;
    private String title;
}

