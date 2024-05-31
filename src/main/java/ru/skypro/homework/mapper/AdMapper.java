package ru.skypro.homework.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.skypro.homework.dto.AdDTO;
import ru.skypro.homework.entity.Ad;

    @Mapper
    public interface AdMapper {
        ru.skypro.homework.mapper.AdMapper INSTANCE = Mappers.getMapper(ru.skypro.homework.mapper.AdMapper.class);

        AdDTO adToAdDTO(Ad ad);
        Ad adDTOToAd(AdDTO adDTO);
    }


