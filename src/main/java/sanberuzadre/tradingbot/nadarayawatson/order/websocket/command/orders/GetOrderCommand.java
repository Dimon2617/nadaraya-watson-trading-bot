package sanberuzadre.tradingbot.nadarayawatson.order.websocket.command.orders;

import com.binance.connector.client.WebSocketApiClient;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import sanberuzadre.tradingbot.nadarayawatson.order.dtos.GetOrderDTO;
import sanberuzadre.tradingbot.nadarayawatson.order.dtos.response.GetOrderResponseDTO;
import sanberuzadre.tradingbot.nadarayawatson.order.websocket.command.Command;
import sanberuzadre.tradingbot.nadarayawatson.order.websocket.command.CommandKey;

import java.lang.reflect.Type;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class GetOrderCommand implements Command<GetOrderDTO> {

    private final WebSocketApiClient webSocketApiClient;

    @Override
    public CommandKey getKey() {
        return CommandKey.GET_ORDER;
    }

    @Override
    public Pair<Type, Boolean> getResponseSignature() {
        return Pair.of(GetOrderResponseDTO.class, false);
    }

    @Override
    public void execute(GetOrderDTO params) {
        Date date = new Date();
        JSONObject json = new JSONObject();
        json.put("timestamp", date.getTime());
        json.put("orderId", params.orderId());
        webSocketApiClient.trade().getOrder(params.currencyChain(), json);
    }

}
