package kz.newmanne.irbis.scheduled;


import kz.newmanne.irbis.entity.NewsCountBySourceAndTopic;
import kz.newmanne.irbis.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Component
@Slf4j
@RequiredArgsConstructor
public class NewsStatisticsCsvConcurrentBackup implements NewsStatistics {
    private final NewsRepository newsRepository;
    @Value("${statistics-folder:stats}")
    private String statisticsFolder;

    @Scheduled(fixedDelay = 60 * 1000)
    @Transactional(readOnly = true)
    public void backup() {
        createStatisticsFolder();

        Page<NewsCountBySourceAndTopic> pageToGetCount =
                newsRepository.getNewsCountBySourceAndTopic(PageRequest.of(0, 2));

        List<CompletableFuture<List<NewsCountBySourceAndTopic>>> asyncTasks = new ArrayList<>();

        for (int i = 0; i < pageToGetCount.getTotalPages(); i++) {
            int currentPage = i;
            asyncTasks.add(
                    CompletableFuture
                            .supplyAsync(
                                    () -> newsRepository
                                            .getNewsCountBySourceAndTopic(
                                                    PageRequest.of(currentPage, 2)
                                            )
                                            .getContent()
                            )
            );
        }

        asyncTasks.forEach(task -> task.thenAccept(this::convertStatisticsToCsv));
    }

    private void createStatisticsFolder() {
        File directoryPath = new File(statisticsFolder + "/");
        boolean isDirectoryCreated = directoryPath.mkdirs();

        if (!isDirectoryCreated) {
            if (directoryPath.isDirectory() && directoryPath.exists()) {
                log.info("Directory is already created!");
            }
        }
    }

    private void convertStatisticsToCsv(List<NewsCountBySourceAndTopic> newsStatistics) {
        newsStatistics
                .stream()
                .collect(Collectors.groupingBy(NewsCountBySourceAndTopic::getSource))
                .forEach((source, topicAndNewsCountListBySource) -> CompletableFuture.runAsync(
                                () -> {
                                    File csvFile = new File(statisticsFolder + "/" + source + ".csv");

                                    for (var topicWithNewsCount : topicAndNewsCountListBySource) {
                                        try (CSVPrinter csvWriter = new CSVPrinter(new FileWriter(csvFile, true), CSVFormat.DEFAULT)) {
                                            csvWriter.printRecord(topicWithNewsCount.getTopic(), topicWithNewsCount.getNewsCount());
                                        } catch (IOException e) {
                                            log.info("unable to write statistics", e);
                                        }
                                    }
                                }
                        )
                );
    }
}
