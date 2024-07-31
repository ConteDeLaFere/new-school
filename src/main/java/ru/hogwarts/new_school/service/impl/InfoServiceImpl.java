package ru.hogwarts.new_school.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.hogwarts.new_school.service.InfoService;


@Service
public class InfoServiceImpl implements InfoService {

    @Value("${server.port}")
    private String port;

    @Override
    public String getPort() {
        return "The application is running on port " + port;
    }

}
