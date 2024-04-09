package sanberuzadre.tradingbot.nadarayawatson.order.websocket.strategy.blockone.steps;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import sanberuzadre.tradingbot.nadarayawatson.order.entities.RegularOrderEntity;
import sanberuzadre.tradingbot.nadarayawatson.order.repository.RegularOrderRepository;
import sanberuzadre.tradingbot.nadarayawatson.order.websocket.strategy.AsyncResultProvider;

@Component
@RequiredArgsConstructor
public class GetNullableOrderFromDBStep implements AsyncResultProvider<RegularOrderEntity, Pair<String, String>> {

    private final RegularOrderRepository orderRepository;

    @Override
    public Mono<RegularOrderEntity> execute(Pair<String, String> params) {
        final String currencyChain = params.getKey();
        final String operation = params.getValue();

        return orderRepository.findFirstByCurrencyChainAndOperation(currencyChain, operation);
    }

}