package co.fastshipping.api.route.response;

public record RouteStopResponse(
        Long id,
        Integer sequence,
        String city
) {
}
