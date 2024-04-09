package sanberuzadre.tradingbot.nadarayawatson.order.websocket.command.market;

import com.binance.connector.client.WebSocketStreamClient;
import lombok.RequiredArgsConstructor;
import okhttp3.Response;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;
import sanberuzadre.tradingbot.nadarayawatson.order.websocket.command.Command;
import sanberuzadre.tradingbot.nadarayawatson.order.websocket.command.CommandKey;

import java.lang.reflect.Type;

@Component
@RequiredArgsConstructor
public class SymbolPriceCommand implements Command<String> {

    private int counter = 10;
    private final WebSocketStreamClient client;

    @Override
    public CommandKey getKey() {
        return CommandKey.SYMBOL_PRICE;
    }

    @Override
    public Pair<Type, Boolean> getResponseSignature() {
        return Pair.of(null, false);
    }

    @Override
    public void execute(String params) {
        client.symbolTicker(params, this::onOpened, this::onMessage, this::onClosing, this::onClosed, this::onFailure);
    }

    private void onOpened(Response msg) {
        System.out.println(msg);
    }

    private void onMessage(String msg) {
        counter--;
        System.out.println(msg);
        if (counter <= 0) {
            closeAllConnections();
        }
    }

    private void onClosing(int code, String msg) {

    }

    private void onClosed(int code, String msg) {

    }

    private void onFailure(Throwable t, Response response) {

    }

    private void closeAllConnections() {
        client.closeAllConnections();
    }

}