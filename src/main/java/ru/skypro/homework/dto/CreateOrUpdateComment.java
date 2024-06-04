package ru.skypro.homework.dto;

import lombok.Data;
import lombok.NonNull;

@Data
public class CreateOrUpdateComment {
    @NonNull//not found validate
    private String text;
    private Integer adId;
}
