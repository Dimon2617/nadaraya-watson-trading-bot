package sanberuzadre.tradingbot.nadarayawatson.order.websocket.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;
import sanberuzadre.tradingbot.nadarayawatson.order.dtos.response.ResponseDTO;
import sanberuzadre.tradingbot.nadarayawatson.order.services.command.HandlerResolver;
import sanberuzadre.tradingbot.nadarayawatson.order.services.typisation.TypeService;

import java.lang.reflect.Type;

@Service
@RequiredArgsConstructor
public class WebSocketMessageHandler {

    private final TypeService typeService;
    private final HandlerResolver handlerResolver;

    private ObjectMapper objectMapper;

    public void handle(String data) {
        System.out.println(data);

        objectMapper = new ObjectMapper();
        try {
            ResponseDTO response = objectMapper.readValue(data, ResponseDTO.class);
            if(response.error != null) {
                System.out.println("error when parsing");
                return;
            }
            processResponse(response.result);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private void processResponse(Object response) {
        Pair<Type, Boolean> type = typeService.getType(response);
        Handler<?> handler = handlerResolver.getHandler(type);
        handler.handle(response, type);
    }

}