package sanberuzadre.tradingbot.nadarayawatson.common.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bot/nadaraya-watson/management")
public class BotManagementController {

    @PostMapping("/start")
    public void start() {
    }

    @PostMapping("/stop")
    public void stop() {
    }

}
