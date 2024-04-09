package sanberuzadre.tradingbot.nadarayawatson.order.websocket.command.orders;

import com.binance.connector.client.WebSocketApiClient;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import sanberuzadre.tradingbot.nadarayawatson.order.dtos.response.oco_order.GetOcoOrderResponseDTO;
import sanberuzadre.tradingbot.nadarayawatson.order.websocket.command.Command;
import sanberuzadre.tradingbot.nadarayawatson.order.websocket.command.CommandKey;

import java.lang.reflect.Type;
import java.util.Date;


@Component
@RequiredArgsConstructor
public class GetOcoOrderCommand implements Command<String> {

    private final WebSocketApiClient webSocketApiClient;

    @Override
    public CommandKey getKey() {
        return CommandKey.GET_OCO_ORDER;
    }

    @Override
    public Pair<Type, Boolean> getResponseSignature() {
        return Pair.of(GetOcoOrderResponseDTO.class, false);
    }

    @Override
    public void execute(String orderListId) {
        Date date = new Date();
        long timestamp = date.getTime();
        JSONObject json = new JSONObject();
        json.put("orderListId", orderListId);
        json.put("timestamp", timestamp);
        webSocketApiClient.trade().getOcoOrder(json);
    }

}
