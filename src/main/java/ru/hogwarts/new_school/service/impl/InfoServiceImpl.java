package ru.hogwarts.new_school.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.hogwarts.new_school.service.InfoService;

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
        logger.info("Calculating the result without parallel streams");
        long startTime = System.currentTimeMillis();
        int result1 = Stream.iterate(1, a -> a + 1)
                .limit(1_000_000)
                .reduce(0, Integer::sum);
        logger.info("Time taken: {} ms", System.currentTimeMillis() - startTime);

        logger.info("Calculating the result with parallel streams");
        startTime = System.currentTimeMillis();
        int result2 = Stream.iterate(1, a -> a + 1)
                .limit(1_000_000)
                .parallel()
                .reduce(0, Integer::sum);
        logger.info("Time taken: {} ms", System.currentTimeMillis() - startTime);

        return result2;
    }
}
