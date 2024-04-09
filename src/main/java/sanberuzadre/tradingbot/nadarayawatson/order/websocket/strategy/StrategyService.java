package sanberuzadre.tradingbot.nadarayawatson.order.websocket.strategy;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;
import sanberuzadre.tradingbot.nadarayawatson.order.dtos.WebSocketOrderDTO;
import sanberuzadre.tradingbot.nadarayawatson.order.dtos.response.NewOrderResponseDTO;
import sanberuzadre.tradingbot.nadarayawatson.order.services.math.NadarayaWatsonService;
import sanberuzadre.tradingbot.nadarayawatson.order.websocket.strategy.blockone.BlockOneStrategy;
import sanberuzadre.tradingbot.nadarayawatson.order.websocket.strategy.blockone.steps.CreateNewRegularOrderStep;
import sanberuzadre.tradingbot.nadarayawatson.order.websocket.strategy.blocktwo.BlockTwoStrategy;

@Service
public class StrategyService {

    private final BlockOneStrategy blockOneStrategy;
    private final BlockTwoStrategy blockTwoStrategy;
    private final CreateNewRegularOrderStep createNewRegularOrderStep;

    public StrategyService(NadarayaWatsonService nadarayaWatsonService,
                           BlockOneStrategy blockOneStrategy,
                           BlockTwoStrategy blockTwoStrategy,
                           CreateNewRegularOrderStep createNewRegularOrderStep) {
        this.createNewRegularOrderStep = createNewRegularOrderStep;
        this.blockOneStrategy = blockOneStrategy;
        this.blockTwoStrategy = blockTwoStrategy;

        nadarayaWatsonService.initializeCallbacks(this::handleOnUpperFar, this::handleOnLowerFar);
    }

    private void handleOnUpperFar(double price) {
        System.out.println("selling UpperFar\n");
        strategyOne("SELL", price);
    }

    private void handleOnLowerFar(double price) {
        System.out.println("buying LowerFar\n");
        strategyOne("BUY", price);
    }

    private void strategyOne(String operation, double price) {
        WebSocketOrderDTO newOrderParams = new WebSocketOrderDTO("ETHUSDT", operation, "LIMIT", "rand", 0.1, price);
        createNewRegularOrderStep.execute(newOrderParams).subscribe(newOrderResponseDTO -> {
            System.out.println("Block two start");
            blockTwoStrategy.executeStrategy(newOrderResponseDTO).block();
            System.out.println("Block two finished");
        });
      /*  blockOneStrategy.executeStrategy(newOrderParams, Pair.of("BTCUSDT", operation))
                .subscribe(result -> {
                    System.out.println("step1 res: " + result.status);
                            strategyTwo(result);
                        },
                        error -> {
                            throw new RuntimeException("step1 error");
                        },
                        () -> System.out.println("step1 completed")
                );*/
    }

    private void strategyTwo(NewOrderResponseDTO newOrderResponseDTO) {
        blockTwoStrategy.executeStrategy(newOrderResponseDTO)
                .subscribe(result -> System.out.println("bebra " + result.orders));
    }

}