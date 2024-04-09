package sanberuzadre.tradingbot.nadarayawatson.order.websocket.strategy.blocktwo.steps;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.publisher.MonoSink;
import sanberuzadre.tradingbot.nadarayawatson.order.dtos.GetOrderDTO;
import sanberuzadre.tradingbot.nadarayawatson.order.dtos.response.GetOrderResponseDTO;
import sanberuzadre.tradingbot.nadarayawatson.order.dtos.response.NewOrderResponseDTO;
import sanberuzadre.tradingbot.nadarayawatson.order.services.command.CommandExecutorService;
import sanberuzadre.tradingbot.nadarayawatson.order.websocket.command.CommandKey;
import sanberuzadre.tradingbot.nadarayawatson.order.websocket.handlers.response.GetOrderHandler;
import sanberuzadre.tradingbot.nadarayawatson.order.websocket.strategy.AsyncResultProvider;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class GetVerificationOrderStep implements AsyncResultProvider<GetOrderResponseDTO, NewOrderResponseDTO> {

    private final GetOrderHandler getOrderHandler;
    private final CommandExecutorService commandExecutorService;

    @Override
    public Mono<GetOrderResponseDTO> execute(NewOrderResponseDTO params) {
        return Mono.create(sink -> repeatCommand(params, sink));
    }

    private void repeatCommand(NewOrderResponseDTO params, MonoSink<GetOrderResponseDTO> sink) {
        commandExecutorService.executeCommand(CommandKey.GET_ORDER, new GetOrderDTO(String.valueOf(params.orderId), params.symbol));

        getOrderHandler.setCallback(result -> {
            try {
                if (result != null && "FILLED".equals(result.status)) {
                    System.out.println("GetVerificationOrderStep");
                    sink.success(result);
                } else {
                    Mono.delay(Duration.ofSeconds(5))
                            .subscribe(ignore -> repeatCommand(params, sink));
                }
            } catch (Exception e) {
                sink.error(e);
            }
        });
    }
}
