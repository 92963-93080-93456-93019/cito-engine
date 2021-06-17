package ua.tqs.cito.utils;

public class HttpResponses {
    public static final String INVALID_APP = "{\"code\" : 403, \"message\" : \"Invalid appId.\"}";
    public static final String INVALID_CONSUMER = "{\"code\" : 403, \"message\" : \"Invalid clientId.\"}";
    public static final String INVALID_RIDER = "{\"code\" : 403, \"message\" : \"Invalid rider.\"}";
    public static final String INVALID_ORDER = "{\"code\" : 403, \"message\" : \"Invalid orderId.\"}";
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
    public static final String RIDER_RATED = "{\"code\" : 200, \"message\" : \"Rider rated.\"}";
    public static final String RIDER_AVAILABILITY_UPDATED = "{\"code\" : 200, \"message\" : \"Rider availability updated.\"}";
    public static final String RIDER_AVAILABILITY_INVALID = "{\"code\" : 403, \"message\" : \"Rider availability invalid.\"}";
    public static final String RIDER_LOCATION_INVALID = "{\"code\" : 403, \"message\" : \"Rider location invalid.\"}";
    public static final String ORDER_LOCATION_INVALID = "{\"code\" : 403, \"message\" : \"Order location invalid.\"}";
    public static final String RIDER_LOCATION_UPDATED = "{\"code\" : 200, \"message\" : \"Rider location updated.\"}";
    public static final String NO_RIDERS_AVAILABLE = "{\"code\" : 404, \"message\" : \"There are no riders.\"}";
    public static final String INVALID_MANAGER = "{\"code\" : 403, \"message\" : \"Invalid managerId.\"}";
    public static final String INVALID_TAX = "{\"code\" : 403, \"message\" : \"Invalid tax for app.\"}";
    public static final String INVALID_NAME = "{\"code\" : 403, \"message\" : \"Invalid name for app.\"}";
    public static final String INVALID_APP_ADDRESS = "{\"code\" : 403, \"message\" : \"Invalid address for app.\"}";
    public static final String INVALID_IMAGE = "{\"code\" : 403, \"message\" : \"Invalid image for app.\"}";
    public static final String INVALID_SCHEDULE = "{\"code\" : 403, \"message\" : \"Invalid schedule for app.\"}";
    public static final String APP_CREATED = "{\"code\" : 201, \"message\" : \"App created.\"}";
    public static final String INVALID_MANAGER_FIRSTNAME = "{\"code\" : 403, \"message\" : \"Invalid manager first name.\"}";
    public static final String INVALID_MANAGER_LASTNAME = "{\"code\" : 403, \"message\" : \"Invalid manager last name.\"}";
    public static final String INVALID_MANAGER_PHONE = "{\"code\" : 403, \"message\" : \"Invalid manager phone.\"}";
    public static final String INVALID_MANAGER_ADDRESS = "{\"code\" : 403, \"message\" : \"Invalid manager address.\"}";
    public static final String MANAGER_SAVED = "{\"code\" : 201, \"message\" : \"Manager saved.\"}";
}
