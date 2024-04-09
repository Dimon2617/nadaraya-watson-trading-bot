package sanberuzadre.tradingbot.nadarayawatson.order.websocket.handlers.response;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;
import sanberuzadre.tradingbot.nadarayawatson.order.dtos.response.LogOnResponseDTO;
import sanberuzadre.tradingbot.nadarayawatson.order.websocket.handlers.Handler;

import java.lang.reflect.Type;

@Component
public class LogonHandler extends Handler<LogOnResponseDTO> {

    @Override
    public void handle(Object data, Pair<Type, Boolean> type) {
        System.out.println("LogonHandler " + data);
    }

    @Override
    public boolean acceptsArray() {
        return false;
    }
}