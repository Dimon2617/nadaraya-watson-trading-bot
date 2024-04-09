package sanberuzadre.tradingbot.nadarayawatson.order.websocket.handlers;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;
import sanberuzadre.tradingbot.nadarayawatson.order.services.math.NadarayaWatsonService;
import sanberuzadre.tradingbot.nadarayawatson.order.websocket.command.market.dtoTest.Root;

import java.lang.reflect.Type;

@Component
@RequiredArgsConstructor
public class KlineDataHandler extends Handler<Root> {

    private final NadarayaWatsonService nadarayaWatsonService;

    @Override
    public void handle(Object data, Pair<Type, Boolean> type) {
        super.handle(data, type);
        nadarayaWatsonService.calculate(item);
    }

    @Override
    public boolean acceptsArray() {
        return false;
    }
}