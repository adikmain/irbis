package kz.newmanne.irbis.service.news;

import kz.newmanne.irbis.dto.NewsDTO;
import kz.newmanne.irbis.entity.NewsEntity;
import kz.newmanne.irbis.mapper.NewsMapper;
import kz.newmanne.irbis.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Slf4j
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {
    private final NewsRepository newsRepository;

    @Override
    @Transactional(readOnly = true)
    public List<NewsDTO> getNews(Integer page, Integer pageSize, String source, String topic) {
        if (pageSize == null || pageSize < 1)
            pageSize = 10;

        if (page == null || page < 0)
            page = 0;

        var pageRequest = PageRequest.of(page, pageSize);
        List<NewsEntity> newsEntities = new ArrayList<>();

        if (StringUtils.hasText(source))
            newsEntities = newsRepository.findBySource(source, pageRequest);

        else if (StringUtils.hasText(topic))
            newsEntities = newsRepository.findByTopic(topic, pageRequest);

        else if (!StringUtils.hasText(source) && !StringUtils.hasText(topic))
            newsEntities = newsRepository.findAll(pageRequest).stream().toList();

        return newsEntities
                .stream()
                .map(NewsMapper::toNewsDTO)
                .collect(Collectors.toList());
    }
}
