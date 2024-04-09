package sanberuzadre.tradingbot.nadarayawatson.order.websocket.command.orders;

import com.binance.connector.client.WebSocketApiClient;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import sanberuzadre.tradingbot.nadarayawatson.order.websocket.command.Command;
import sanberuzadre.tradingbot.nadarayawatson.order.websocket.command.CommandKey;

import java.lang.reflect.Type;

@Component
@RequiredArgsConstructor
public class CancelAllOpenOrdersCommand implements Command<String> {

    private final WebSocketApiClient webSocketApiClient;

    @Override
    public CommandKey getKey() {
        return CommandKey.CANCEL_ALL_OPEN_ORDERS;
    }

    @Override
    public Pair<Type, Boolean> getResponseSignature() {
        return Pair.of(null, false);
    }

    @Override
    public void execute(String currencyChain) {
        JSONObject json = new JSONObject();
        webSocketApiClient.trade().cancelAllOpenOrders(currencyChain, json);
    }

}