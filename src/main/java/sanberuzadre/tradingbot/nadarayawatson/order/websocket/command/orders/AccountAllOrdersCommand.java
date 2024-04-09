package sanberuzadre.tradingbot.nadarayawatson.order.websocket.command.orders;

import com.binance.connector.client.WebSocketApiClient;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;
import sanberuzadre.tradingbot.nadarayawatson.order.dtos.response.GetOrderResponseDTO;
import sanberuzadre.tradingbot.nadarayawatson.order.websocket.command.Command;
import sanberuzadre.tradingbot.nadarayawatson.order.websocket.command.CommandKey;

import java.lang.reflect.Type;

@Component
@RequiredArgsConstructor
public class AccountAllOrdersCommand implements Command<String> {

    private final WebSocketApiClient webSocketApiClient;

    @Override
    public CommandKey getKey() {
        return CommandKey.ACCOUNT_ALL_ORDERS;
    }

    @Override
    public Pair<Type, Boolean> getResponseSignature() {
        return Pair.of(GetOrderResponseDTO.class, true);
    }

    @Override
    public void execute(String symbol) {
        webSocketApiClient.account().accountAllOrders(symbol, null);
    }

}