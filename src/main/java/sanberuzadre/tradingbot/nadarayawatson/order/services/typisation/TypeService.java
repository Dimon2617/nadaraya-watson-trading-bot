package sanberuzadre.tradingbot.nadarayawatson.order.services.typisation;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;
import sanberuzadre.tradingbot.nadarayawatson.order.dtos.OrderDTO;
import sanberuzadre.tradingbot.nadarayawatson.order.dtos.TickerPriceDTO;
import sanberuzadre.tradingbot.nadarayawatson.order.dtos.response.GetOrderResponseDTO;
import sanberuzadre.tradingbot.nadarayawatson.order.dtos.response.LogOnResponseDTO;
import sanberuzadre.tradingbot.nadarayawatson.order.dtos.response.NewOrderResponseDTO;
import sanberuzadre.tradingbot.nadarayawatson.order.dtos.response.oco_order.GetOcoOrderResponseDTO;
import sanberuzadre.tradingbot.nadarayawatson.order.dtos.response.oco_order.NewOcoOrderResponseDTO;
import sanberuzadre.tradingbot.nadarayawatson.order.entities.LimitOrderEntity;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class TypeService {

    private final Map<List<String>, Type> dtoMap;

    public TypeService() {
        dtoMap = new HashMap<>();
        dtoMap.put(getFieldNames(OrderDTO.class), OrderDTO.class);
        dtoMap.put(getFieldNames(LimitOrderEntity.class), LimitOrderEntity.class);
        dtoMap.put(getFieldNames(GetOrderResponseDTO.class), GetOrderResponseDTO.class);
        dtoMap.put(getFieldNames(LogOnResponseDTO.class), LogOnResponseDTO.class);
        dtoMap.put(getFieldNames(NewOrderResponseDTO.class), NewOrderResponseDTO.class);
        dtoMap.put(getFieldNames(NewOcoOrderResponseDTO.class), NewOcoOrderResponseDTO.class);
        dtoMap.put(getFieldNames(GetOcoOrderResponseDTO.class), GetOcoOrderResponseDTO.class);
        dtoMap.put(getFieldNames(TickerPriceDTO.class), TickerPriceDTO.class);
    }

    /**
     * Will return a pair, where:
     * <pre>the left part displays the Type</pre>
     * <pre>the right pair displays if the object is an array</pre>
     *
     * @param object
     * @return Type, Boolean isArray
     */
    public Pair<Type, Boolean> getType(Object object) {
        if (object instanceof ArrayList<?>) {
            ArrayList<?> arrayList = (ArrayList<?>) object;
            if (!arrayList.isEmpty() && arrayList.get(0) instanceof LinkedHashMap<?, ?>) {
                LinkedHashMap<String, Object> linkedHashMap = (LinkedHashMap<String, Object>) arrayList.get(0);
                Type type = getTypeInternal(linkedHashMap);
                return Pair.of(type, true);
            }
        }

        return Pair.of(getTypeInternal((LinkedHashMap<String, Object>) object), false);
    }

    private List<String> getFieldNames(Class<?> type) {
        List<String> fieldNames = new ArrayList<>();
        for (Field field : type.getDeclaredFields()) {
            fieldNames.add(field.getName());
        }
        return fieldNames;
    }

    private Type getTypeInternal(LinkedHashMap<String, Object> fields) {
        List<String> fieldNames = new ArrayList<>(fields.keySet());

        return dtoMap.entrySet().stream()
                .filter(entry -> fieldNames.equals(entry.getKey()))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Type os not specified"));
    }

}