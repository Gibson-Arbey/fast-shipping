package co.fastshipping.api.config;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiPath {

    public static final String ROUTE_VEHICLE  = "/api/vehicle";
    public static final String ROUTE_DRIVER = "/api/driver";
    public static final String ROUTE_LICENSE_CATEGORY = "/api/license-category";

    public static final String V1 = "1";
}
