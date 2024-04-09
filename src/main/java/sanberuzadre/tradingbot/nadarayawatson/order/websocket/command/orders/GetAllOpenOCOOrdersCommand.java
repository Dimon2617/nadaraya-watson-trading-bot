package sanberuzadre.tradingbot.nadarayawatson.order.websocket.command.orders;

import com.binance.connector.client.WebSocketApiClient;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import sanberuzadre.tradingbot.nadarayawatson.order.dtos.response.GetOrderResponseDTO;
import sanberuzadre.tradingbot.nadarayawatson.order.websocket.command.Command;
import sanberuzadre.tradingbot.nadarayawatson.order.websocket.command.CommandKey;

import java.lang.reflect.Type;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class GetAllOpenOCOOrdersCommand implements Command<Object> {

    private final WebSocketApiClient webSocketApiClient;

    @Override
    public CommandKey getKey() {
        return CommandKey.GET_ALL_OPEN_OCO_ORDERS;
    }

    @Override
    public Pair<Type, Boolean> getResponseSignature() {
        return Pair.of(null, true);
    }

    @Override
    public void execute(Object params) {
        Date date = new Date();
        long timestamp = date.getTime();

        JSONObject json = new JSONObject();
        json.put("timestamp", timestamp);
        webSocketApiClient.trade().getOpenOcoOrders(json);
    }

}