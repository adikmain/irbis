package kz.newmanne.irbis.scheduled;


import kz.newmanne.irbis.entity.NewsCountBySourceAndTopic;
import kz.newmanne.irbis.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
@RequiredArgsConstructor
public class NewsStatisticsCsvConcurrentBackup implements NewsStatistics {

    private final NewsRepository newsRepository;

    @Value("${statistics-folder:stats}")
    private String statisticsFolder;

    @Override
    @Scheduled(fixedDelay = 60 * 1000)
    public void backup() {
        List<NewsCountBySourceAndTopic> newsStatistics = newsRepository.getNewsCountBySourceAndTopic();

        newsStatistics
                .stream()
                .collect(Collectors.groupingBy(NewsCountBySourceAndTopic::getSource))
                .forEach((source, topicAndNewsCountListBySource) -> {
                    File csvFile = new File(statisticsFolder + "/" + source + ".csv");
                    csvFile.getParentFile().mkdirs();

                    for (var topicWithNewsCount : topicAndNewsCountListBySource) {
                        try (CSVPrinter csvWriter = new CSVPrinter(new FileWriter(csvFile), CSVFormat.DEFAULT)) {
                            csvWriter.printRecord(topicWithNewsCount.getTopic(), topicWithNewsCount.getNewsCount());
                        } catch (IOException e) {
                            log.info("unable to write statistics", e);
                        }
                    }
                });
    }
}
