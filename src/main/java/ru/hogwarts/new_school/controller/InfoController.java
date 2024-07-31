package ru.hogwarts.new_school.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hogwarts.new_school.service.InfoService;

@RestController
public class InfoController {

    @Autowired
    private InfoService infoService;

    @GetMapping("/port")
    public String getPort() {
        return infoService.getPort();
    }
}
