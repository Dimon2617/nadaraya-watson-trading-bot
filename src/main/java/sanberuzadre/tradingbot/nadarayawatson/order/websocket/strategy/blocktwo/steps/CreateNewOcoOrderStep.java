package sanberuzadre.tradingbot.nadarayawatson.order.websocket.strategy.blocktwo.steps;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import sanberuzadre.tradingbot.nadarayawatson.order.dtos.WebSocketOcoOrderDTO;
import sanberuzadre.tradingbot.nadarayawatson.order.dtos.response.oco_order.NewOcoOrderResponseDTO;
import sanberuzadre.tradingbot.nadarayawatson.order.entities.LimitOrderEntity;
import sanberuzadre.tradingbot.nadarayawatson.order.repository.OrderRepository;
import sanberuzadre.tradingbot.nadarayawatson.order.services.command.CommandExecutorService;
import sanberuzadre.tradingbot.nadarayawatson.order.services.mapper.OcoMapper;
import sanberuzadre.tradingbot.nadarayawatson.order.websocket.command.CommandKey;
import sanberuzadre.tradingbot.nadarayawatson.order.websocket.handlers.response.NewOcoOrderHandler;
import sanberuzadre.tradingbot.nadarayawatson.order.websocket.strategy.AsyncResultProvider;

@Component
@RequiredArgsConstructor
public class CreateNewOcoOrderStep implements AsyncResultProvider<NewOcoOrderResponseDTO, WebSocketOcoOrderDTO> {
    private final NewOcoOrderHandler newOcoOrderHandler;
    private final CommandExecutorService commandExecutorService;
    private final OrderRepository orderRepository;
    private final OcoMapper ocoMapper;

    @Override
    public Mono<NewOcoOrderResponseDTO> execute(WebSocketOcoOrderDTO params) {
        return Mono.create(sink -> {
            commandExecutorService.executeCommand(CommandKey.NEW_OCO_ORDER, params);

            newOcoOrderHandler.setCallback(result -> {
                if (result != null) {
                    System.out.println("Save Oco");
                    LimitOrderEntity orderEntity = ocoMapper.toEntity(result);
                    orderRepository.save(orderEntity).block();
                    sink.success(result);
                } else {
                    sink.error(new RuntimeException("oco order was`t create"));
                }
            });
        });
    }
}
