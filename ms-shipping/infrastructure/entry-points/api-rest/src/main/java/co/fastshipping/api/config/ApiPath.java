package co.fastshipping.api.config;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiPath {

    public static final String ROUTE_PARCEL  = "/api/parcel";
    public static final String ROUTE_PARCEL_HISTORY = "/api/parcel-history";
    public static final String ROUTE_SHIPMENT = "/api/shipment";

    public static final String V1 = "1";
}
