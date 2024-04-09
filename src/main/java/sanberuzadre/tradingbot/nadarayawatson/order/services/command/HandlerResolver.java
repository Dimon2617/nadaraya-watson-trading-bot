package sanberuzadre.tradingbot.nadarayawatson.order.services.command;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sanberuzadre.tradingbot.nadarayawatson.order.websocket.command.Command;
import sanberuzadre.tradingbot.nadarayawatson.order.websocket.handlers.Handler;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class HandlerResolver {

    private Map<Pair<Type, Boolean>, Handler<?>> handlerMap;

    @Autowired
    public HandlerResolver(List<Command> commandList, List<Handler<?>> handlerList) {
        handlerMap = new HashMap<>();
        for (Handler<?> handler : handlerList) {
            Type genericType = extractType(handler);
            Command command = findMatchingCommands(commandList, genericType, handler.acceptsArray());

            if (command != null) {
                Pair<Type, Boolean> commandSignature = command.getResponseSignature();
                handlerMap.put(commandSignature, handler);
            }
        }
    }

    public Handler<?> getHandler(Pair<Type, Boolean> responseType) {
        if (handlerMap.containsKey(responseType)) {
            return handlerMap.get(responseType);
        } else {
            throw new RuntimeException("Handler not specified");
        }
    }

    private <T> Type extractType(Handler<T> handler) {
        Type genericSuperclass = handler.getClass().getGenericSuperclass();

        if (genericSuperclass instanceof ParameterizedType parameterizedType) {
            return parameterizedType.getActualTypeArguments()[0];
        }

        throw new IllegalArgumentException("Handler class must implement a generic interface.");
    }

    private Command<?> findMatchingCommands(List<Command> commandList, Type genericType, boolean isArray) {
        return commandList.stream()
                .filter(command -> command.getResponseSignature().getLeft() == genericType
                        && (Boolean) command.getResponseSignature().getRight() == isArray)
                .findFirst()
                .orElse(null);
    }

}