package sanberuzadre.tradingbot.nadarayawatson.order.websocket.command;

import org.apache.commons.lang3.tuple.Pair;

import java.lang.reflect.Type;

public interface Command<T> {
    CommandKey getKey();
    Pair<Type, Boolean> getResponseSignature();
    void execute(T params);
}