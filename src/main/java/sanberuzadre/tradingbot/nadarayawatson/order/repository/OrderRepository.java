package sanberuzadre.tradingbot.nadarayawatson.order.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import sanberuzadre.tradingbot.nadarayawatson.order.entities.LimitOrderEntity;

public interface OrderRepository extends R2dbcRepository<LimitOrderEntity, Long> {

}