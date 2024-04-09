package sanberuzadre.tradingbot.nadarayawatson.listeners;

import com.binance.connector.client.WebSocketStreamClient;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;
import sanberuzadre.tradingbot.nadarayawatson.order.services.command.CommandExecutorService;
import sanberuzadre.tradingbot.nadarayawatson.order.websocket.command.CommandKey;

@Component
@RequiredArgsConstructor
public class ContextClosedListener implements ApplicationListener<ContextClosedEvent> {

    private final CommandExecutorService commandExecutorService;
    private final WebSocketStreamClient webSocketStreamClient;

    @Override
    public void onApplicationEvent(@NotNull ContextClosedEvent event) {
        webSocketStreamClient.closeAllConnections();
        commandExecutorService.executeCommand(CommandKey.CLOSE_CONNECTION, null);
    }

}