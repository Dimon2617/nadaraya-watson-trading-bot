package sanberuzadre.tradingbot.nadarayawatson.order.websocket.command.market;

import com.binance.connector.client.WebSocketStreamClient;
import lombok.RequiredArgsConstructor;
import okhttp3.Response;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;
import sanberuzadre.tradingbot.nadarayawatson.order.websocket.command.Command;
import sanberuzadre.tradingbot.nadarayawatson.order.websocket.command.CommandKey;
import sanberuzadre.tradingbot.nadarayawatson.order.websocket.handlers.SocketKlineMessageHandler;

import java.lang.reflect.Type;
import sanberuzadre.tradingbot.nadarayawatson.order.websocket.command.market.dtoTest.Root;

//param 0 - currencyChain
//param 1 - timeInterval
@Component
@RequiredArgsConstructor
public class KlinePriceCommand implements Command<String[]> {

    private final WebSocketStreamClient webSocketStreamClient;
    private final SocketKlineMessageHandler socketKlineMessageHandler;

    @Override
    public CommandKey getKey() {
        return CommandKey.KLINE_PRICE;
    }

    @Override
    public Pair<Type, Boolean> getResponseSignature() {
        return Pair.of(null, false);
    }

    @Override
    public void execute(String[] params) {
        var currency = params[0];
        var interval = params[1];

        webSocketStreamClient.klineStream(currency, interval, this::onOpenHandler, this::onMessage, this::onClosing, this::onClosed, this::onFailure);
    }

    private void onOpenHandler(Response msg) {
    }

    private void onMessage(String msg) {
        socketKlineMessageHandler.handle(msg, Pair.of(Root.class, false));
    }

    private void onClosing(int code, String msg) {
    }

    private void onClosed(int code, String msg) {
    }

    private void onFailure(Throwable t, Response response) {
        closeAllConnections();
    }

    private void closeAllConnections() {
        webSocketStreamClient.closeAllConnections();
    }

}