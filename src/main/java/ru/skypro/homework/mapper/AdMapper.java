package ru.skypro.homework.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.skypro.homework.config.MapperConfig;
import ru.skypro.homework.dto.AdDTO;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.dto.ExtendedAd;
import ru.skypro.homework.entity.AdEntity;

@Service
public class AdMapper {
    @Value("${download.url}")
    protected String downloadUrl;

    private final MapperConfig mapper;

    @Autowired
    public AdMapper(MapperConfig mapper) {
        this.mapper = mapper;
    }

    public AdDTO adEntityToAdDTO(AdEntity adEntity) {
        AdDTO adDTO = mapper.getMapper().map(adEntity, AdDTO.class);
        adDTO.setImage(adEntity.getImageEntity() == null ? "" : downloadUrl + adEntity.getImageEntity().getImageId());

        adDTO.setAuthor(adEntity.getUserEntity().getUserId().intValue());
        adDTO.setPk(adEntity.getId().intValue());
        return adDTO;
    }

    public AdEntity adDTOToAdEntityWithoutId(AdDTO adDTO) {
        AdEntity adEntity = mapper.getMapper().map(adDTO, AdEntity.class);
        adEntity.setId(null); // Убедитесь, что ID не установлен при создании новой сущности
        return adEntity;
    }

    public AdEntity createOrUpdateAdToAdEntity(CreateOrUpdateAd createOrUpdateAd) {
        return mapper.getMapper().map(createOrUpdateAd, AdEntity.class);
    }

    public void updateAdEntityFromDto(CreateOrUpdateAd createOrUpdateAd, AdEntity adEntity) {
        adEntity.setPrice(createOrUpdateAd.getPrice());
        adEntity.setTitle(createOrUpdateAd.getTitle());
    }

    public ExtendedAd adEntityToExtendedAd(AdEntity adEntity) {
        ExtendedAd extendedAd = new ExtendedAd();
        extendedAd.setPk(adEntity.getId().intValue()); // Приводим Long к Integer
        extendedAd.setImage(adEntity.getImageEntity() == null ? "" : downloadUrl + adEntity.getImageEntity().getImageId());

        extendedAd.setPrice(adEntity.getPrice());
        extendedAd.setTitle(adEntity.getTitle());
        return extendedAd;
    }
}


