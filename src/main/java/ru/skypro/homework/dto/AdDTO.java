package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
//@Schema//( type = "string", format = "binary")
@NoArgsConstructor
@AllArgsConstructor
public class AdDTO {
    private Integer author;
    @Schema( type = "string", format = "binary")
    private String image;
    private Integer pk;
    private Integer price;
    private String title;
}

