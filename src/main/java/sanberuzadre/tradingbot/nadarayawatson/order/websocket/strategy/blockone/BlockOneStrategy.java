package sanberuzadre.tradingbot.nadarayawatson.order.websocket.strategy.blockone;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import reactor.core.publisher.Mono;
import sanberuzadre.tradingbot.nadarayawatson.order.dtos.GetOrderDTO;
import sanberuzadre.tradingbot.nadarayawatson.order.dtos.WebSocketOrderDTO;
import sanberuzadre.tradingbot.nadarayawatson.order.dtos.response.GetOrderResponseDTO;
import sanberuzadre.tradingbot.nadarayawatson.order.dtos.response.NewOrderResponseDTO;
import sanberuzadre.tradingbot.nadarayawatson.order.entities.RegularOrderEntity;
import sanberuzadre.tradingbot.nadarayawatson.order.repository.RegularOrderRepository;
import sanberuzadre.tradingbot.nadarayawatson.order.services.mapper.RegularOrderMapper;
import sanberuzadre.tradingbot.nadarayawatson.order.websocket.strategy.blockone.steps.CreateNewRegularOrderStep;
import sanberuzadre.tradingbot.nadarayawatson.order.websocket.strategy.blockone.steps.GetNullableOrderFromDBStep;
import sanberuzadre.tradingbot.nadarayawatson.order.websocket.strategy.blockone.steps.GetOrderFromAPIStep;

@RequiredArgsConstructor
public class BlockOneStrategy {

    private final GetNullableOrderFromDBStep dbStep;
    private final GetOrderFromAPIStep getOrderStep;
    private final CreateNewRegularOrderStep createOrderStep;

    private final RegularOrderRepository orderRepository;
    private final RegularOrderMapper mapper;

    public Mono<NewOrderResponseDTO> executeStrategy(WebSocketOrderDTO webSocketOrderDTO, Pair<String, String> params) {
        RegularOrderEntity dbResult = dbStep.execute(params)
                .block();

        if (dbResult == null) {
            System.out.println("regular order doesn't exist in DB");
            return Mono.just(createNewOrder(webSocketOrderDTO));
        }

        GetOrderDTO getOrderDTO = new GetOrderDTO(String.valueOf(dbResult.getOrderId()), params.getKey());

        GetOrderResponseDTO getOrderResult = getOrderStep.execute(getOrderDTO)
                .block();

        if (getOrderResult == null || getOrderResult.status.equals("NEW") || getOrderResult.status.equals("PARTIALLY_FILLED")) {
            return Mono.error(new RuntimeException("could not create"));
        }

        return Mono.just(createNewOrder(webSocketOrderDTO));
    }

    private NewOrderResponseDTO createNewOrder(WebSocketOrderDTO webSocketOrderDTO) {
        var createOrderResult = createOrderStep.execute(webSocketOrderDTO)
                .block();
        logToDB(createOrderResult);
        return createOrderResult;
    }

    private void logToDB(NewOrderResponseDTO dto) {
        var entity = mapper.newOrderResponseToEntity(dto);
        orderRepository.save(entity)
                .block();
    }

}