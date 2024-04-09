package sanberuzadre.tradingbot.nadarayawatson.order.websocket.strategy.blockone.steps;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import sanberuzadre.tradingbot.nadarayawatson.order.dtos.WebSocketOrderDTO;
import sanberuzadre.tradingbot.nadarayawatson.order.dtos.response.NewOrderResponseDTO;
import sanberuzadre.tradingbot.nadarayawatson.order.services.command.CommandExecutorService;
import sanberuzadre.tradingbot.nadarayawatson.order.websocket.command.CommandKey;
import sanberuzadre.tradingbot.nadarayawatson.order.websocket.handlers.response.NewOrderHandler;
import sanberuzadre.tradingbot.nadarayawatson.order.websocket.strategy.AsyncResultProvider;

@Component
@RequiredArgsConstructor
public class CreateNewRegularOrderStep implements AsyncResultProvider<NewOrderResponseDTO, WebSocketOrderDTO> {

    private final NewOrderHandler newOrderHandler;
    private final CommandExecutorService commandExecutorService;

    @Override
    public Mono<NewOrderResponseDTO> execute(WebSocketOrderDTO params) {
        return Mono.create(sink -> {
            commandExecutorService.executeCommand(CommandKey.NEW_ORDER, params);

            newOrderHandler.setCallback(result -> {
                if(result != null ){
                    sink.success(result);
                } else {
                    sink.success();
                }
            });
        });
    }

}