package sanberuzadre.tradingbot.nadarayawatson.order.websocket.strategy.blocktwo.steps;


import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import sanberuzadre.tradingbot.nadarayawatson.order.services.command.CommandExecutorService;
import sanberuzadre.tradingbot.nadarayawatson.order.websocket.command.CommandKey;
import sanberuzadre.tradingbot.nadarayawatson.order.websocket.handlers.response.TickerPriceHandler;
import sanberuzadre.tradingbot.nadarayawatson.order.websocket.strategy.AsyncResultProvider;

@Component
@RequiredArgsConstructor
public class VerifyStopPriceStep implements AsyncResultProvider<Boolean, Pair<Double, String>> {
    private final CommandExecutorService commandExecutorService;
    private final TickerPriceHandler tickerPriceHandler;

    @Override
    public Mono<Boolean> execute(Pair<Double, String> params) {

        return Mono.create(sink -> {
            tickerPriceHandler.setCallback(result -> {
                if (result != null) {
                    boolean isVerified;
                    if (params.getRight().equals("SELL")) {
                        isVerified = params.getLeft() > result.price();
                    } else {
                        isVerified = params.getLeft() < result.price();
                    }
                    if (!isVerified) {
                        throw new RuntimeException("Price is not verified");
                    }
                    sink.success(isVerified);
                }
            });
            //todo: не спрацьовує команда
            commandExecutorService.executeCommand(CommandKey.TICKER_PRICE, new String[]{"ETHUSDT"});
        });
    }
}
