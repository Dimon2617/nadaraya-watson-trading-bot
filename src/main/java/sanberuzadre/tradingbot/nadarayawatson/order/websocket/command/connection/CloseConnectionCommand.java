package sanberuzadre.tradingbot.nadarayawatson.order.websocket.command.connection;

import com.binance.connector.client.WebSocketApiClient;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;
import sanberuzadre.tradingbot.nadarayawatson.order.websocket.command.Command;
import sanberuzadre.tradingbot.nadarayawatson.order.websocket.command.CommandKey;

import java.lang.reflect.Type;

@Component
@RequiredArgsConstructor
public class CloseConnectionCommand implements Command<Object> {

    private final WebSocketApiClient webSocketApiClient;

    @Override
    public CommandKey getKey() {
        return CommandKey.CLOSE_CONNECTION;
    }

    @Override
    public Pair<Type, Boolean> getResponseSignature() {
        return Pair.of(null, false);
    }

    @Override
    public void execute(Object params) {
        if (webSocketApiClient != null) {
            webSocketApiClient.close();
        }
    }

}