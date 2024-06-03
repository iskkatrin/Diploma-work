package ru.skypro.homework.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.skypro.homework.config.MapperConfig;
import ru.skypro.homework.dto.AdDTO;
import ru.skypro.homework.entity.AdEntity;

@Service
public class AdMapper {
    private final MapperConfig mapper;

    @Autowired
    public AdMapper(MapperConfig mapper) {
        this.mapper = mapper;
    }

    public AdDTO adEntityToAdDTO(AdEntity adEntity) {
        AdDTO adDTO = mapper.getMapper().map(adEntity, AdDTO.class);
        adDTO.setAuthor(adEntity.getAuthorId().intValue());
        //не знаю что такое pk, поэтому так написал
        adDTO.setPk(-1);
        return adDTO;
    }

    public AdEntity adDTOToAdEntityWithoutId(AdDTO adDTO) {
        return mapper.getMapper().map(adDTO, AdEntity.class);
    }
}


