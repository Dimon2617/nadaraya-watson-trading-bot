package sanberuzadre.tradingbot.nadarayawatson.order.websocket.strategy.blocktwo.steps;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.publisher.MonoSink;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;
import sanberuzadre.tradingbot.nadarayawatson.order.dtos.response.oco_order.GetOcoOrderResponseDTO;
import sanberuzadre.tradingbot.nadarayawatson.order.dtos.response.oco_order.NewOcoOrderResponseDTO;
import sanberuzadre.tradingbot.nadarayawatson.order.services.command.CommandExecutorService;
import sanberuzadre.tradingbot.nadarayawatson.order.websocket.command.CommandKey;
import sanberuzadre.tradingbot.nadarayawatson.order.websocket.handlers.response.GetOcoOrderHandler;
import sanberuzadre.tradingbot.nadarayawatson.order.websocket.strategy.AsyncResultProvider;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class GetVerificationOcoOrderStep implements AsyncResultProvider<GetOcoOrderResponseDTO, NewOcoOrderResponseDTO> {

    private final GetOcoOrderHandler getOcoOrderHandler;
    private final CommandExecutorService commandExecutorService;

    @Override
    public Mono<GetOcoOrderResponseDTO> execute(NewOcoOrderResponseDTO params) {
        return Mono.create(sink -> repeatCommand(params, sink));
    }

    private void repeatCommand(NewOcoOrderResponseDTO params, MonoSink<GetOcoOrderResponseDTO> sink) {
        commandExecutorService.executeCommand(CommandKey.GET_OCO_ORDER, params.orderListId);

        Scheduler scheduler = Schedulers.single();
        getOcoOrderHandler.setCallback(result -> {
            scheduler.schedule(() -> {
                try {
                    if (result != null && !result.listOrderStatus.equals("\"ALL_DONE\"")) {
                        System.out.println(result);
                        sink.success(result);
                    } else {
                        boolean shouldRepeat = true;
                        if (shouldRepeat) {
                            repeatCommand(params, sink);
                        } else {
                            sink.error(new RuntimeException("order " + params.orderListId + " is not filled"));
                        }
                    }
                } catch (Exception e) {
                    sink.error(e);
                }
            }, 5, TimeUnit.SECONDS);

        });
    }

}
