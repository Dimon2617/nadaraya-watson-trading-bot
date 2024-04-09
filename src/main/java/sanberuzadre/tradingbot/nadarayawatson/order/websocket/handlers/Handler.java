package sanberuzadre.tradingbot.nadarayawatson.order.websocket.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.ArrayList;

@Component
public abstract class Handler<T> {

    public abstract boolean acceptsArray();

    public void handle(Object data, Pair<Type, Boolean> type) {
        convertToType((T) type.getKey(), data);
    }

    private ObjectMapper mapper = new ObjectMapper();

    protected T item;

    private void convertToType(T type, Object data) {
        Class<?> typeClass = type.getClass();

        if (data instanceof ArrayList<?>) {
            // If it's an array type
            item = mapper.convertValue(((ArrayList<?>) data).get(0), (Class<T>) type);
           // item = mapper.convertValue(data, mapper.getTypeFactory().constructArrayType(typeClass));
        } else {
            // If it's a non-array type
            item = mapper.convertValue(data, (Class<T>) type);
        }
    }

}