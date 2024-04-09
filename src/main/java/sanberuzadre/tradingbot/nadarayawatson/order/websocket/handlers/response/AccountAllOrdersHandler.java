package sanberuzadre.tradingbot.nadarayawatson.order.websocket.handlers.response;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;
import sanberuzadre.tradingbot.nadarayawatson.order.dtos.response.GetOrderResponseDTO;
import sanberuzadre.tradingbot.nadarayawatson.order.websocket.handlers.Handler;

import java.lang.reflect.Type;

@Component
public class AccountAllOrdersHandler extends Handler<GetOrderResponseDTO> {
    @Override
    public void handle(Object data, Pair<Type, Boolean> type) {
        System.out.println("");
    }

    @Override
    public boolean acceptsArray() {
        return true;
    }

}