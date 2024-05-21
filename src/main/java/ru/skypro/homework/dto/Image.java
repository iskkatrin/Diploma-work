package ru.skypro.homework.dto;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class Image {

    private Long id;
    private String filePath;
    private long fileSize;
    private String mediaType;
    private byte[] data;
}
