package kz.newmanne.irbis.web.controller;


import kz.newmanne.irbis.service.news.NewsService;
import kz.newmanne.irbis.service.source.SourceService;
import kz.newmanne.irbis.service.topic.TopicService;
import kz.newmanne.irbis.web.dto.NewsDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class IrbisController {
    private final SourceService sourceService;
    private final TopicService topicService;
    private final NewsService newsService;

    @GetMapping("sources")
    public ResponseEntity<List<String>> getSources() {
        var sources = sourceService.getSources();

        if (sources.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(sources);
    }

    @GetMapping("topics")
    public ResponseEntity<List<String>> getTopics() {
        var topics = topicService.getTopics();

        if (topics.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(topics);
    }

    @GetMapping(value = "news")
    public ResponseEntity<List<NewsDTO>> getNews(
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @RequestParam(value = "source", required = false) String source,
            @RequestParam(value = "topic", required = false) String topic
    ) {
        var news = newsService.getNews(page, pageSize, source, topic);

        if (news.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(news);
        }
    }
}
