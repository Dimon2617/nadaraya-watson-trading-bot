package sanberuzadre.tradingbot.nadarayawatson.order.websocket.handlers.response;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;
import sanberuzadre.tradingbot.nadarayawatson.order.dtos.response.NewOrderResponseDTO;
import sanberuzadre.tradingbot.nadarayawatson.order.websocket.handlers.Handler;

import java.lang.reflect.Type;
import java.util.function.Consumer;

@Component
public class NewOrderHandler extends Handler<NewOrderResponseDTO> {

    private Consumer<NewOrderResponseDTO> callback;

    @Override
    public void handle(Object data, Pair<Type, Boolean> type) {
        super.handle(data, type);
        if(callback != null) {
            callback.accept(item);
        }
    }

    @Override
    public boolean acceptsArray() {
        return false;
    }

    public void setCallback(Consumer<NewOrderResponseDTO> callback) {
        this.callback = callback;
    }

}