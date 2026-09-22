package co.fastshipping.usecase.route.command;

public record RegisterRouteStopCommand(
        Integer sequence,
        String city
) {
}
