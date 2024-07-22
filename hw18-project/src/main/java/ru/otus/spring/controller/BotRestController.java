package ru.otus.spring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.spring.dto.WebsiteRqDto;
import ru.otus.spring.service.WebsiteService;

@RestController
@RequiredArgsConstructor
public class BotRestController {

    private final WebsiteService websiteService;

    @PostMapping("/api/order")
    public void getWebsiteRequest(@RequestBody WebsiteRqDto request) {
        websiteService.addMessage(request);
    }

}
