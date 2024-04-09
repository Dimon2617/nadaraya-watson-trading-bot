package sanberuzadre.tradingbot.nadarayawatson.order.websocket.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;
import sanberuzadre.tradingbot.nadarayawatson.order.websocket.command.market.dtoTest.Root;

import java.lang.reflect.Type;

@Component
@RequiredArgsConstructor
public class SocketKlineMessageHandler extends Handler<String> {

    private final KlineDataHandler klineDataHandler;
    private final ObjectMapper mapper;

    private String strTemplate = ",\"k\":";

    private int indexOfResult;

    private String res;

    private String supposedJsonData;

    private String removeLastCharacter(String message) {
        if (message != null && message.length() > 0) {
            return message.substring(0, message.length() - 1);
        } else {
            return message;
        }
    }

    private <T> T mapToObj(String msg, Class type) {
        try {
            var res = mapper.readValue(msg, type);
            return (T) res;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        throw new RuntimeException();
    }

    @Override
    public void handle(Object data, Pair<Type, Boolean> type) {
        String klineData = (String) data;
        indexOfResult = klineData.indexOf(strTemplate);
        res = klineData.substring(indexOfResult + strTemplate.length());
        supposedJsonData = removeLastCharacter(res);
        Object obj = mapToObj(supposedJsonData, Root.class);
        klineDataHandler.handle(obj, Pair.of(obj.getClass(), false));
    }

    @Override
    public boolean acceptsArray() {
        return false;
    }
}