package kz.newmanne.irbis.mapper;

import kz.newmanne.irbis.dto.NewsDTO;
import kz.newmanne.irbis.entity.NewsEntity;

public class NewsMapper {
    public static NewsDTO toNewsDTO(NewsEntity newsEntity) {
        return NewsDTO.builder()
                .news(newsEntity.getNewsContent())
                .source(newsEntity.getSource())
                .topic(newsEntity.getTopic()).build();
    }
}
