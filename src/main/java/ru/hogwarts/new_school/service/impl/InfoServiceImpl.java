package ru.hogwarts.new_school.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.hogwarts.new_school.service.InfoService;

import java.util.stream.IntStream;
import java.util.stream.Stream;


@Service
public class InfoServiceImpl implements InfoService {

    @Value("${server.port}")
    private String port;

    Logger logger = LoggerFactory.getLogger(InfoServiceImpl.class);

    @Override
    public String getPort() {
        return "The application is running on port " + port;
    }

    @Override
    public Integer calculate() {

        {
            long startTime = System.currentTimeMillis();
            int sum = Stream.iterate(1, i -> i + 1)
                    .limit(1_000_000)
                    .reduce(0, Integer::sum);
            long endTime = System.currentTimeMillis();
            logger.info("Total time taken: {}ms (result={})", endTime - startTime, sum);
        }

        {
            long startTime = System.currentTimeMillis();
            int sum = Stream.iterate(1, i -> i + 1)
                    .limit(1_000_000)
                    .parallel()
                    .reduce(0, Integer::sum);
            long endTime = System.currentTimeMillis();
            logger.info("Total time taken: {}ms (result={})", endTime - startTime, sum);
        }

        {
            long startTime = System.currentTimeMillis();
            int sum = IntStream.rangeClosed(1, 1_000_000)
                    .reduce(0, Integer::sum);
            long endTime = System.currentTimeMillis();
            logger.info("Total time taken: {}ms (result={})", endTime - startTime, sum);
        }

        {
            long startTime = System.currentTimeMillis();
            int sum = IntStream.rangeClosed(1, 1_000_000)
                    .parallel()
                    .reduce(0, Integer::sum);
            long endTime = System.currentTimeMillis();
            logger.info("Total time taken: {}ms (result={})", endTime - startTime, sum);
        }

        {
            long startTime = System.currentTimeMillis();
            int sum = IntStream.rangeClosed(1, 1_000_000)
                    .sum();
            long endTime = System.currentTimeMillis();
            logger.info("Total time taken: {}ms (result={})", endTime - startTime, sum);
        }

        {
            long startTime = System.currentTimeMillis();
            int sum = IntStream.rangeClosed(1, 1_000_000)
                    .parallel()
                    .sum();
            long endTime = System.currentTimeMillis();
            logger.info("Total time taken: {}ms (result={})", endTime - startTime, sum);
        }

        return 0;
    }
}
