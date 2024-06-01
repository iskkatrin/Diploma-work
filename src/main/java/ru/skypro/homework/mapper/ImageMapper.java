//package ru.skypro.homework.mapper;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import ru.skypro.homework.config.MapperConfig;
//import ru.skypro.homework.dto.ImageDTO;
//import ru.skypro.homework.entity.ImageEntity;
//
//@Service
//public class ImageMapper {
//
//    private final MapperConfig mapper;
//
//    @Autowired
//    public ImageMapper(MapperConfig mapper) {
//        this.mapper = mapper;
//    }
//
//    public ImageDTO imageEntityToImageDTO(ImageEntity imageEntity) {
//        ImageDTO imageDTO = mapper.getMapper().map(imageEntity, ImageDTO.class)
//    }
//
//}


// закоментировал тк перестал писать изза точто что у нас не должно быть
// такого дто как imageDTO