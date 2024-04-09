package sanberuzadre.tradingbot.nadarayawatson.order.services.command;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sanberuzadre.tradingbot.nadarayawatson.order.websocket.command.Command;
import sanberuzadre.tradingbot.nadarayawatson.order.websocket.command.CommandKey;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class CommandExecutorService {

    private Map<CommandKey, Command> commands;

    @Autowired
    public CommandExecutorService(List<Command> commandList) {
        commands = new EnumMap<>(CommandKey.class);
        commandList.forEach(command -> commands.put(command.getKey(), command));
    }

    public <T> void executeCommand(CommandKey commandKey, T param) {
        var command = getCommandByType(commandKey);
        command.execute(param);
    }

    private Command getCommandByType(CommandKey commandKey) {
        if (commands.containsKey(commandKey)) {
            return commands.get(commandKey);
        } else {
            throw new RuntimeException("""
                    Command by key '%s' does not exist
                    """.formatted(commandKey));
        }
    }

}