package sanberuzadre.tradingbot.nadarayawatson.order.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;
import sanberuzadre.tradingbot.nadarayawatson.order.entities.RegularOrderEntity;

import java.util.Optional;

public interface RegularOrderRepository extends R2dbcRepository<RegularOrderEntity, Long> {

    @Query("SELECT or FROM regular_order_entity or ORDER BY or.id DESC")
    Mono<Optional<RegularOrderEntity>> findTopByOrderByIdDesc();

    Mono<RegularOrderEntity> findFirstByOrderIdAndCurrencyChain(Long orderId, String currencyChain);

    Mono<RegularOrderEntity> findFirstByCurrencyChainAndOperation(String currencyChain, String operation);

}