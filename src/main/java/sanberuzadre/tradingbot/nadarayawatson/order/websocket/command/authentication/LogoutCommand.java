package sanberuzadre.tradingbot.nadarayawatson.order.websocket.command.authentication;

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
public class LogoutCommand implements Command<Object> {

    private final WebSocketApiClient webSocketApiClient;

    @Override
    public CommandKey getKey() {
        return CommandKey.LOG_OUT;
    }

    @Override
    public Pair<Type, Boolean> getResponseSignature() {
        return Pair.of(null, false);
    }

    @Override
    public void execute(Object params) {
        JSONObject json = new JSONObject();
        json.put("requestId", "randomId");
        webSocketApiClient.auth().logout(json);
    }

}