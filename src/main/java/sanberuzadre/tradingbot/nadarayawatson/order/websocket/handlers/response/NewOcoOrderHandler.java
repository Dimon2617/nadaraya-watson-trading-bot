package sanberuzadre.tradingbot.nadarayawatson.order.websocket.handlers.response;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;
import sanberuzadre.tradingbot.nadarayawatson.order.dtos.response.oco_order.NewOcoOrderResponseDTO;
import sanberuzadre.tradingbot.nadarayawatson.order.websocket.handlers.Handler;

import java.lang.reflect.Type;
import java.util.function.Consumer;

@Component
public class NewOcoOrderHandler extends Handler<NewOcoOrderResponseDTO> {

    private Consumer<NewOcoOrderResponseDTO> callback;

    @Override
    public void handle(Object data, Pair<Type, Boolean> type) {
        super.handle(data, type);
        if (callback != null) {
            System.out.println("NewOcoOrderHandler " + item);
            callback.accept(item);
        }
    }

    @Override
    public boolean acceptsArray() {
        return false;
    }

    public void setCallback(Consumer<NewOcoOrderResponseDTO> callback) {
        this.callback = callback;
    }

}
