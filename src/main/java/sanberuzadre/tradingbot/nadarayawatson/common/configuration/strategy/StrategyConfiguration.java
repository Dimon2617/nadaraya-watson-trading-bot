package sanberuzadre.tradingbot.nadarayawatson.common.configuration.strategy;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sanberuzadre.tradingbot.nadarayawatson.order.repository.RegularOrderRepository;
import sanberuzadre.tradingbot.nadarayawatson.order.services.mapper.RegularOrderMapper;
import sanberuzadre.tradingbot.nadarayawatson.order.websocket.strategy.blockone.BlockOneStrategy;
import sanberuzadre.tradingbot.nadarayawatson.order.websocket.strategy.blockone.steps.CreateNewRegularOrderStep;
import sanberuzadre.tradingbot.nadarayawatson.order.websocket.strategy.blockone.steps.GetNullableOrderFromDBStep;
import sanberuzadre.tradingbot.nadarayawatson.order.websocket.strategy.blockone.steps.GetOrderFromAPIStep;
import sanberuzadre.tradingbot.nadarayawatson.order.websocket.strategy.blocktwo.BlockTwoStrategy;
import sanberuzadre.tradingbot.nadarayawatson.order.websocket.strategy.blocktwo.steps.CreateNewOcoOrderStep;
import sanberuzadre.tradingbot.nadarayawatson.order.websocket.strategy.blocktwo.steps.GetVerificationOcoOrderStep;
import sanberuzadre.tradingbot.nadarayawatson.order.websocket.strategy.blocktwo.steps.GetVerificationOrderStep;
import sanberuzadre.tradingbot.nadarayawatson.order.websocket.strategy.blocktwo.steps.InitializationOcoRequestStep;

@Configuration
public class StrategyConfiguration {

    @Bean
    public BlockOneStrategy blockOneStrategy(GetNullableOrderFromDBStep dbStep, GetOrderFromAPIStep apiStep,
                                             CreateNewRegularOrderStep newOrderStep, RegularOrderRepository repository,
                                             RegularOrderMapper mapper) {
        return new BlockOneStrategy(dbStep, apiStep, newOrderStep, repository, mapper);
    }

    @Bean
    public BlockTwoStrategy blockTwoStrategy(InitializationOcoRequestStep initializationOcoRequest,
                                             GetVerificationOrderStep verificationOrder,
                                             CreateNewOcoOrderStep createNewOcoOrderStep,
                                             GetVerificationOcoOrderStep verificationOcoOrderStep) {
        return new BlockTwoStrategy(initializationOcoRequest, verificationOrder, createNewOcoOrderStep, verificationOcoOrderStep);
    }

}