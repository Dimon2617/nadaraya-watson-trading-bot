package sanberuzadre.tradingbot.nadarayawatson.order.websocket.strategy;

import reactor.core.publisher.Mono;

/**
 * An async command returning one element
 * @param <R> return type of a provider
 * @param <P> parameters
 */
public interface AsyncResultProvider<R, P>{
    Mono<R> execute(P params);
}