package sanberuzadre.tradingbot.nadarayawatson.order.websocket.command.market;

import com.binance.connector.client.WebSocketApiClient;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import sanberuzadre.tradingbot.nadarayawatson.order.dtos.TickerPriceDTO;
import sanberuzadre.tradingbot.nadarayawatson.order.websocket.command.Command;
import sanberuzadre.tradingbot.nadarayawatson.order.websocket.command.CommandKey;

import java.lang.reflect.Type;

@Component
@RequiredArgsConstructor
public class TickerPriceCommand implements Command<String[]> {

    private final WebSocketApiClient webSocketApiClient;

    @Override
    public CommandKey getKey() {
        return CommandKey.TICKER_PRICE;
    }

    @Override
    public Pair<Type, Boolean> getResponseSignature() {
        return Pair.of(TickerPriceDTO.class, true);
    }

    @Override
    public void execute(String[] currencyChain) {
        JSONObject json = new JSONObject();
        json.put("symbols", currencyChain);
        webSocketApiClient.market().tickerPrice(json);
    }

}