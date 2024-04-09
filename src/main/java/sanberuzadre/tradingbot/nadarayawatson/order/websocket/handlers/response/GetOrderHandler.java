package sanberuzadre.tradingbot.nadarayawatson.order.websocket.handlers.response;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;
import sanberuzadre.tradingbot.nadarayawatson.order.dtos.response.GetOrderResponseDTO;
import sanberuzadre.tradingbot.nadarayawatson.order.websocket.handlers.Handler;

import java.lang.reflect.Type;
import java.util.function.Consumer;

@Component
public class GetOrderHandler extends Handler<GetOrderResponseDTO> {

    private Consumer<GetOrderResponseDTO> callback;

    @Override
    public void handle(Object data, Pair<Type, Boolean> type) {
        super.handle(data, type);
        if (callback != null) {
            //emit the callback
            callback.accept(item);
        }
    }

    @Override
    public boolean acceptsArray() {
        return false;
    }

    public void setCallback(Consumer<GetOrderResponseDTO> callback) {
        this.callback = callback;
    }
}