package sanberuzadre.tradingbot.nadarayawatson.order.websocket.command.orders;

import com.binance.connector.client.WebSocketApiClient;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import sanberuzadre.tradingbot.nadarayawatson.order.websocket.command.Command;
import sanberuzadre.tradingbot.nadarayawatson.order.websocket.command.CommandKey;

import java.lang.reflect.Type;

/**
 * Object[]:
 * // param 0 - orderId
 * // param 1 - currency chain
 */
@Component
@RequiredArgsConstructor
public class CancelOrderCommand implements Command<Object[]> {

    private final WebSocketApiClient webSocketApiClient;

    @Override
    public CommandKey getKey() {
        return CommandKey.CANCEL_ORDER;
    }

    @Override
    public Pair<Type, Boolean> getResponseSignature() {
        return Pair.of(null, false);
    }

    @Override
    public void execute(Object[] params) { // ToDo: change to CancelOrderRequest with int and String as fields
        JSONObject json = new JSONObject();
        json.put("orderId", (int) params[0]);
        webSocketApiClient.trade().cancelOrder((String) params[1], json);
    }

}