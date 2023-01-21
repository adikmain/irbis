package kz.newmanne.irbis.mapper;

import kz.newmanne.irbis.entity.NewsEntity;
import kz.newmanne.irbis.web.dto.NewsDTO;

public class NewsMapper {
    public static NewsDTO toNewsDTO(NewsEntity newsEntity) {
        var newsDTO = new NewsDTO();
        newsDTO.setNews(newsEntity.getNewsContent());
        newsDTO.setSource(newsEntity.getSource());
        newsDTO.setTopic(newsEntity.getTopic());

        return newsDTO;
    }

}
