package sanberuzadre.tradingbot.nadarayawatson.common.configuration.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sanberuzadre.tradingbot.nadarayawatson.order.services.math.NadarayaWatsonService;
import sanberuzadre.tradingbot.nadarayawatson.order.websocket.handlers.KlineDataHandler;
import sanberuzadre.tradingbot.nadarayawatson.order.websocket.handlers.SocketKlineMessageHandler;

@Configuration
@RequiredArgsConstructor
public class WebSocketHandlerConfiguration {

    @Bean
    public NadarayaWatsonService nadarayaWatsonService() {
        return new NadarayaWatsonService();
    }

    @Bean
    public SocketKlineMessageHandler socketKlineMessageHandler() {
        return new SocketKlineMessageHandler(klineDataHandler(), new ObjectMapper());
    }

    @Bean
    public KlineDataHandler klineDataHandler() {
        return new KlineDataHandler(nadarayaWatsonService());
    }

}