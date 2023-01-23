package kz.newmanne.irbis.service.source;

import kz.newmanne.irbis.entity.NewsEntity;
import kz.newmanne.irbis.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Slf4j
@RequiredArgsConstructor
public class SourceServiceImpl implements SourceService {
    private final NewsRepository newsRepository;

    @Override
    @Transactional(readOnly = true)
    public List<String> getSources() {
        return newsRepository
                .findAll()
                .stream()
                .map(NewsEntity::getSource)
                .collect(Collectors.toList());
    }
}
