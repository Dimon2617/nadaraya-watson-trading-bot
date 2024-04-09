package sanberuzadre.tradingbot.nadarayawatson.order.websocket.command.orders;

import com.binance.connector.client.WebSocketApiClient;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import sanberuzadre.tradingbot.nadarayawatson.order.dtos.WebSocketOrderDTO;
import sanberuzadre.tradingbot.nadarayawatson.order.dtos.response.NewOrderResponseDTO;
import sanberuzadre.tradingbot.nadarayawatson.order.websocket.command.Command;
import sanberuzadre.tradingbot.nadarayawatson.order.websocket.command.CommandKey;

import java.lang.reflect.Type;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class NewOrderCommand implements Command<WebSocketOrderDTO> {

    private final WebSocketApiClient webSocketApiClient;

    @Override
    public CommandKey getKey() {
        return CommandKey.NEW_ORDER;
    }

    @Override
    public Pair<Type, Boolean> getResponseSignature() {
        return Pair.of(NewOrderResponseDTO.class, false);
    }

    @Override
    public void execute(WebSocketOrderDTO params) {
        Date date = new Date();
        long timestamp = date.getTime();
        JSONObject json = new JSONObject();
        json.put("requestId", params.getOrderId());
        json.put("quantity", params.getQuantity());
        json.put("price", params.getPrice());
        json.put("timeInForce", "GTC");
        json.put("timestamp", timestamp);

        webSocketApiClient.trade().newOrder(
                params.getCurrencyChain(),
                params.getOperation(),
                params.getType(),
                json
        );
    }

}