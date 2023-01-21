package kz.newmanne.irbis.scheduled;


import kz.newmanne.irbis.entity.NewsEntityInfo;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
@Slf4j
@RequiredArgsConstructor
public class NewsRunnerImpl implements NewsRunner {

    private final ExecutorService executorService = Executors.newWorkStealingPool();

    private final NewsRepository newsRepository;

    @Value("${directory-name}")
    private String directoryName;

    @Override
    @Scheduled(fixedDelay = 10_000)
    public void loadCsv() {
        List<NewsEntityInfo> statistics = newsRepository.getStatistics();
        Map<String, List<NewsEntityInfo>> sourceAgainstStats = new HashMap<>();

        statistics.forEach(stat -> sourceAgainstStats.putIfAbsent(stat.getSource(), new ArrayList<>()));
        statistics.forEach(stat -> sourceAgainstStats.get(stat.getSource()).add(stat));

        sourceAgainstStats.forEach(
                (sourceName, stats) -> executorService.submit(
                        () -> {
                            File file = new File(directoryName + "/" + sourceName + ".csv");
                            if (file.getParentFile().mkdirs()) {

                                try (CSVPrinter printer = new CSVPrinter(
                                        new FileWriter(file),
                                        CSVFormat.DEFAULT)
                                ) {
                                    stats.forEach(
                                            stat -> {
                                                try {
                                                    printer.printRecord(stat.getSource(), stat.getTopic(), stat.getCount());
                                                } catch (IOException e) {
                                                    throw new RuntimeException(e);
                                                }
                                            }
                                    );

                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }
                )
        );


        System.out.println("Scheduled works!");
    }
}
