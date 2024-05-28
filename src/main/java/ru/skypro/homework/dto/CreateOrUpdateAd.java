package ru.skypro.homework.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Data;

//import javax.validation.constraints.Max;
//import javax.validation.constraints.Min;
//import javax.validation.constraints.Size;

@Data
public class CreateOrUpdateAd {
    @Size(min = 4, max=32)
    private String title;
    @Min(0)
    @Max(10000000)
    private Integer price;
    @Size(min = 8, max=64)
    private String description;

}
