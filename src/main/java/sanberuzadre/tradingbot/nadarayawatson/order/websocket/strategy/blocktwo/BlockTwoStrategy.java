package sanberuzadre.tradingbot.nadarayawatson.order.websocket.strategy.blocktwo;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
import sanberuzadre.tradingbot.nadarayawatson.order.dtos.WebSocketOcoOrderDTO;
import sanberuzadre.tradingbot.nadarayawatson.order.dtos.response.NewOrderResponseDTO;
import sanberuzadre.tradingbot.nadarayawatson.order.dtos.response.oco_order.GetOcoOrderResponseDTO;
import sanberuzadre.tradingbot.nadarayawatson.order.dtos.response.oco_order.NewOcoOrderResponseDTO;
import sanberuzadre.tradingbot.nadarayawatson.order.websocket.strategy.blocktwo.steps.CreateNewOcoOrderStep;
import sanberuzadre.tradingbot.nadarayawatson.order.websocket.strategy.blocktwo.steps.GetVerificationOcoOrderStep;
import sanberuzadre.tradingbot.nadarayawatson.order.websocket.strategy.blocktwo.steps.GetVerificationOrderStep;
import sanberuzadre.tradingbot.nadarayawatson.order.websocket.strategy.blocktwo.steps.InitializationOcoRequestStep;

@RequiredArgsConstructor
public class BlockTwoStrategy {

    private final InitializationOcoRequestStep initializationOcoRequest;
    private final GetVerificationOrderStep verificationOrder;
    private final CreateNewOcoOrderStep createNewOcoOrderStep;
    private final GetVerificationOcoOrderStep verificationOcoOrderStep;

    public Mono<GetOcoOrderResponseDTO> executeStrategy(NewOrderResponseDTO newOrderResponseDTO) {

        return verificationOrder.execute(newOrderResponseDTO)
                .flatMap(orderResponse -> {
                    if (orderResponse != null) {

                        WebSocketOcoOrderDTO webSocketOcoOrderDTOStopLoss =
                                initializationOcoRequest.performOcoOrderOpeningStopLoss(
                                        Double.parseDouble(orderResponse.price),
                                        orderResponse.side
                                );

                        WebSocketOcoOrderDTO webSocketOcoOrderDTOTakeProfit =
                                initializationOcoRequest.performOcoOrderOpeningTakeProfit(
                                        Double.parseDouble(orderResponse.price),
                                        orderResponse.side
                                );
                        //todo: до цього моменту програма не доходила, вона зупинялась на попередньому степі
                        //todo: наступні степи ще не перевіряв
                        Mono<NewOcoOrderResponseDTO> orderResponseDTOStopLoss =
                                createNewOcoOrderStep.execute(webSocketOcoOrderDTOStopLoss);

                        Mono<NewOcoOrderResponseDTO> orderResponseDTOTakeProfit =
                                createNewOcoOrderStep.execute(webSocketOcoOrderDTOTakeProfit);

                        return Mono.zip(orderResponseDTOStopLoss, orderResponseDTOTakeProfit)
                                .map(tuple -> {
                                    tuple.getT1();
                                    tuple.getT2();
                                    return new NewOcoOrderResponseDTO();
                                });
                    } else {
                        return Mono.error(new RuntimeException(""));
                    }
                })
                .flatMap(verificationOcoOrderStep::execute);

    }
}
