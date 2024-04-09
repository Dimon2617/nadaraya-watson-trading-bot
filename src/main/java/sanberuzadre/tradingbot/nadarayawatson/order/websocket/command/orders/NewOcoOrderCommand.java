package sanberuzadre.tradingbot.nadarayawatson.order.websocket.command.orders;

import com.binance.connector.client.WebSocketApiClient;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import sanberuzadre.tradingbot.nadarayawatson.order.dtos.WebSocketOcoOrderDTO;
import sanberuzadre.tradingbot.nadarayawatson.order.dtos.response.oco_order.NewOcoOrderResponseDTO;
import sanberuzadre.tradingbot.nadarayawatson.order.websocket.command.Command;
import sanberuzadre.tradingbot.nadarayawatson.order.websocket.command.CommandKey;

import java.lang.reflect.Type;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class NewOcoOrderCommand implements Command<WebSocketOcoOrderDTO> {

    private final WebSocketApiClient webSocketApiClient;

    @Override
    public CommandKey getKey() {
        return CommandKey.NEW_OCO_ORDER;
    }

    @Override
    public Pair<Type, Boolean> getResponseSignature() {
        return Pair.of(NewOcoOrderResponseDTO.class, false);
    }

    @Override
    public void execute(WebSocketOcoOrderDTO params) {
        Date date = new Date();
        long timestamp = date.getTime();
        JSONObject json = new JSONObject();
        json.put("requestId", String.valueOf(params.getOrderId()));
        json.put("stopPrice", params.getStopPrice());
        json.put("stopLimitPrice", params.getStopLimitPrice());
        json.put("stopLimitTimeInForce", params.getTimeInForce());
        json.put("timestamp", timestamp);

        webSocketApiClient.trade().newOcoOrder(
                params.getCurrencyChain(),
                params.getOperation(),
                params.getPrice(),
                params.getQuantity(),
                json
        );
    }

}
