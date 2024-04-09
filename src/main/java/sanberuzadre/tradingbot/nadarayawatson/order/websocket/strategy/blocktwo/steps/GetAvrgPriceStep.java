package sanberuzadre.tradingbot.nadarayawatson.order.websocket.strategy.blocktwo.steps;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import sanberuzadre.tradingbot.nadarayawatson.order.services.math.NadarayaWatsonService;
import sanberuzadre.tradingbot.nadarayawatson.order.websocket.strategy.AsyncResultProvider;

@Component
@RequiredArgsConstructor
public class GetAvrgPriceStep implements AsyncResultProvider<Double, String> {

    private final NadarayaWatsonService nadarayaWatsonService;

    @Override
    public Mono<Double> execute(String params) {
        return Mono.create(sink -> {
            nadarayaWatsonService.setCallback(result -> {
                if (result != null) {
                    sink.success(result);
                } else {
                    sink.error(new RuntimeException("Avrg price was not found"));
                }
            });
        });
    }
}
