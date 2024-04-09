package sanberuzadre.tradingbot.nadarayawatson.listeners;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import sanberuzadre.tradingbot.nadarayawatson.order.dtos.WebSocketOcoOrderDTO;
import sanberuzadre.tradingbot.nadarayawatson.order.dtos.WebSocketOrderDTO;
import sanberuzadre.tradingbot.nadarayawatson.order.dtos.response.NewOrderResponseDTO;
import sanberuzadre.tradingbot.nadarayawatson.order.repository.OrderRepository;
import sanberuzadre.tradingbot.nadarayawatson.order.repository.RegularOrderRepository;
import sanberuzadre.tradingbot.nadarayawatson.order.services.command.CommandExecutorService;
import sanberuzadre.tradingbot.nadarayawatson.order.services.mapper.OcoMapper;
import sanberuzadre.tradingbot.nadarayawatson.order.websocket.command.CommandKey;
import sanberuzadre.tradingbot.nadarayawatson.order.websocket.handlers.response.TickerPriceHandler;
import sanberuzadre.tradingbot.nadarayawatson.order.websocket.strategy.blocktwo.BlockTwoStrategy;

@Component
@RequiredArgsConstructor
public class ApplicationStartedListener {

    private final CommandExecutorService commandExecutorService;
    private final BlockTwoStrategy blockTwoStrategy;
    private final OrderRepository orderRepository;
    private final OcoMapper ocoMapper;
    private final RegularOrderRepository regularOrderRepository;
    private final TickerPriceHandler tickerPriceHandler;

    @PostConstruct
    public void onApplicationEvent() {
        commandExecutorService.executeCommand(CommandKey.LOG_ON, null);
        commandExecutorService.executeCommand(CommandKey.KLINE_PRICE, new String[]{"ethusdt", "1s"});

        NewOrderResponseDTO dto = new NewOrderResponseDTO()
                .setSymbol("ETHUSDT")
                .setOrderId(10852998);

        blockTwoStrategy.executeStrategy(dto)
                .subscribe(result -> System.out.println("bebra " + result.orders));
    }

}