package sanberuzadre.tradingbot.nadarayawatson.order.websocket.strategy.blockone.steps;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import sanberuzadre.tradingbot.nadarayawatson.order.dtos.GetOrderDTO;
import sanberuzadre.tradingbot.nadarayawatson.order.dtos.response.GetOrderResponseDTO;
import sanberuzadre.tradingbot.nadarayawatson.order.services.command.CommandExecutorService;
import sanberuzadre.tradingbot.nadarayawatson.order.websocket.command.CommandKey;
import sanberuzadre.tradingbot.nadarayawatson.order.websocket.handlers.response.GetOrderHandler;
import sanberuzadre.tradingbot.nadarayawatson.order.websocket.strategy.AsyncResultProvider;

@Component
@RequiredArgsConstructor
public class GetOrderFromAPIStep implements AsyncResultProvider<GetOrderResponseDTO, GetOrderDTO> {

    private final GetOrderHandler getOrderHandler;
    private final CommandExecutorService commandExecutorService;

    @Override
    public Mono<GetOrderResponseDTO> execute(GetOrderDTO params) {
        return Mono.create(sink -> {
            commandExecutorService.executeCommand(CommandKey.GET_ORDER, params);

            getOrderHandler.setCallback(result -> {
                if(result != null) {
                    sink.success(result);
                } else {
                    sink.success();
                }
            });
        });
    }

}