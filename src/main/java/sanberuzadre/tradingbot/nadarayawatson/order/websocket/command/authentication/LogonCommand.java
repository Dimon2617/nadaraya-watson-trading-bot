package sanberuzadre.tradingbot.nadarayawatson.order.websocket.command.authentication;

import com.binance.connector.client.WebSocketApiClient;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import sanberuzadre.tradingbot.nadarayawatson.order.dtos.response.LogOnResponseDTO;
import sanberuzadre.tradingbot.nadarayawatson.order.websocket.command.Command;
import sanberuzadre.tradingbot.nadarayawatson.order.websocket.command.CommandKey;
import sanberuzadre.tradingbot.nadarayawatson.order.websocket.handlers.WebSocketMessageHandler;

import java.lang.reflect.Type;

@Component
@RequiredArgsConstructor
public class LogonCommand implements Command<Object> {

    private final WebSocketApiClient webSocketApiClient;

    private WebSocketMessageHandler webSocketMessageHandler;

    @Lazy
    @Autowired
    public void setWebSocketMessageHandler(WebSocketMessageHandler webSocketMessageHandler) {
        this.webSocketMessageHandler = webSocketMessageHandler;
    }

    @Override
    public CommandKey getKey() {
        return CommandKey.LOG_ON;
    }

    @Override
    public Pair<Type, Boolean> getResponseSignature() {
        return Pair.of(LogOnResponseDTO.class, false);
    }

    @Override
    public void execute(Object params) {
        webSocketApiClient.connect(webSocketMessageHandler::handle);

        JSONObject json = new JSONObject();
        webSocketApiClient.auth().logon(json);
        json.clear();
    }

}