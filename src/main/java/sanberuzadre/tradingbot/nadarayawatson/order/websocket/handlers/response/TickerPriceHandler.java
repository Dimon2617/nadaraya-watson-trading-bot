package sanberuzadre.tradingbot.nadarayawatson.order.websocket.handlers.response;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;
import sanberuzadre.tradingbot.nadarayawatson.order.dtos.TickerPriceDTO;
import sanberuzadre.tradingbot.nadarayawatson.order.dtos.response.NewOrderResponseDTO;
import sanberuzadre.tradingbot.nadarayawatson.order.websocket.handlers.Handler;

import java.lang.reflect.Type;
import java.util.function.Consumer;

@Component
public class TickerPriceHandler extends Handler<TickerPriceDTO> {

    private Consumer<TickerPriceDTO> callback;
    @Override
    public void handle(Object data, Pair<Type, Boolean> type) {
        super.handle(data, type);
        if(callback != null){
            callback.accept(item);
        }else {
            throw new RuntimeException("Callback not set");
        }
    }

    @Override
    public boolean acceptsArray() {
        return true;
    }

    public void setCallback(Consumer<TickerPriceDTO> callback) {
        this.callback = callback;
    }
}
