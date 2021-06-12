package ua.tqs.cito.utils;

public class HttpResponses {
    public static final String INVALID_APP = "{\"code\" : 403, \"message\" : \"Invalid appId.\"}";
    public static final String INVALID_CONSUMER = "{\"code\" : 403, \"message\" : \"Invalid clientId.\"}";
    public static final String INVALID_RIDER = "{\"code\" : 403, \"message\" : \"Invalid rider.\"}";
    public static final String INSUFFICIENT_PRODUCTS = "{\"code\" : 403, \"message\" : \"Order with insufficient products (0).\"}";
    public static final String INVALID_PRODUCT = "{\"code\" : 403, \"message\" : \"Invalid productId (#).\"}";
    public static final String INVALID_ADDRESS = "{\"code\" : 403, \"message\" : \"No delivery address provided.\"}";
    public static final String ORDER_SAVED = "{\"code\" : 201, \"message\" : \"Order saved.\"}";
    public static final String RIDER_SAVED = "{\"code\" : 201, \"message\" : \"Rider saved.\"}";
    public static final String ORDER_UPDATED = "{\"code\" : 201, \"message\" : \"Order updated (#).\"}";
    public static final String PRODUCT_SAVED = "{\"code\" : 201, \"message\" : \"Product saved.\"}";
    public static final String PRODUCT_NOT_SAVED = "{\"code\" : 500, \"message\" : \"Bad parameters for product.\"}";
    public static final String MANAGER_NOT_FOUND_FOR_APP = "{\"code\" : 404, \"message\" : \"Manager not found for app.\"}";
    public static final String INVALID_STATUS = "{\"code\" : 404, \"message\" : \"Status invalid.\"}";
    public static final String INVALID_VEHICLE = "{\"code\" : 403, \"message\" : \"Vehicle not valid.\"}";
    public static final String RIDER_RATED = "{\"code\" : 200, \"message\" : \"Rader rated.\"}";
}
