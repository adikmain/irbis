package kz.newmanne.irbis.service.news;

import kz.newmanne.irbis.web.dto.NewsDTO;

import java.util.List;

public interface NewsService {

    List<NewsDTO> getNews(Integer page, Integer pageSize, String source, String topic);
}
