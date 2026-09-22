package co.fastshipping.usecase.route.command;

import java.util.Set;

public record RegisterRouteCommand(
        String name,
        Set<RegisterRouteStopCommand> stops
) {
}
