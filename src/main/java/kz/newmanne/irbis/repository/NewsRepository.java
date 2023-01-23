package kz.newmanne.irbis.repository;

import kz.newmanne.irbis.entity.NewsCountBySourceAndTopic;
import kz.newmanne.irbis.entity.NewsEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NewsRepository extends JpaRepository<NewsEntity, String> {
    List<NewsEntity> findByTopic(String topic, Pageable pageRequest);

    List<NewsEntity> findBySource(String source, Pageable pageRequest);

    @Query(
            "select e.source as source, " +
                    "e.topic as topic, " +
                    "count(e.newsContent) as newsCount " +
                    "from NewsEntity " +
                    "e group by e.source, e.topic"
    )
    List<NewsCountBySourceAndTopic> getNewsCountBySourceAndTopic();
}