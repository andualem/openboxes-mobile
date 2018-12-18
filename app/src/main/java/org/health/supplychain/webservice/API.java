package org.health.supplychain.webservice;


import org.apache.commons.codec.binary.Base64;
import org.health.supplychain.webservice.entities.AuthenticationRequest;
import org.health.supplychain.webservice.entities.AvailableItemListResponse;
import org.health.supplychain.webservice.entities.LocationData;
import org.health.supplychain.webservice.entities.LocationListResponse;
import org.health.supplychain.webservice.entities.Product;
import org.health.supplychain.webservice.entities.ProductCategory;
import org.health.supplychain.webservice.entities.ProductListResponse;
import org.health.supplychain.webservice.entities.RequisitionRequest;
import org.health.supplychain.webservice.entities.SearchAttributesRequest;
import org.health.supplychain.webservice.entities.ServerTimeResponse;
import org.health.supplychain.webservice.entities.Shipment;
import org.health.supplychain.webservice.entities.ShipmentInitialRequest;
import org.health.supplychain.webservice.entities.ShipmentItemRequest;
import org.health.supplychain.webservice.entities.ShipmentRequest;
import org.health.supplychain.webservice.entities.ShipmentResponse;
import org.health.supplychain.webservice.entities.ShipmentTypeResponse;
import org.health.supplychain.webservice.entities.SingleShipmentResponse;
import org.health.supplychain.webservice.entities.StockMovement;
import org.health.supplychain.webservice.entities.StockMovementListResponse;
import org.health.supplychain.webservice.entities.StockMovementResponse;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;

import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.*;

/**
 * Created by Aworkneh on 5/2/2018.
 */

public class API {

    public static final String ACCEPT_JSON = "Accept: application/json, text/javascript, */*; q=0.01";
    public static final String ACCEPT_ANY = "Accept: */*";
    public static final String AUTHORIZATION = "Authorization";
    public static final String CONNECTION_ALIVE = "Connection:keep-alive";
    public static final String TOKEN_HEADER = "Token";
    public static final String COOKIE = "Cookie";
    public static String mInstanceUrl = "";
    private static String username = "", encryptedPassword = "";
    public static String mTenantIdentifier = "default";

    public static AuthenticationService authenticationService;
    public static OfficerService officerService;
    public static ProductService productService;
    public static LocationService locationService;
    public static StockMovementService stockMovementService;
    public static ShipmentService shipmentService;
    public static RequisitionService requisitionService;
    public static SettingService settingService;
    private static String responseToken;


    static {
        init();
    }

    private static Retrofit retrofit;

    private static synchronized void init() {
        readAPIPropertyFromPropertyFile();
        retrofit = createRestAdapter(getInstanceUrl());
        authenticationService = retrofit.create(AuthenticationService.class);
        productService = retrofit.create(ProductService.class);
        locationService = retrofit.create(LocationService.class);
        stockMovementService = retrofit.create(StockMovementService.class);
        shipmentService = retrofit.create(ShipmentService.class);
        requisitionService = retrofit.create(RequisitionService.class);
    }



    private static Retrofit createRestAdapter(final String url) {


        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder clientBuilder = new OkHttpClient().newBuilder();
        clientBuilder.addInterceptor(interceptor);

        clientBuilder.addInterceptor(new Interceptor() {
            @Override public Response intercept(Chain chain) throws IOException {

                Request request = chain.request();//.newBuilder().addHeader("key", "value").build();
                Request requestWithHeader;

                // Authentication header related
                if(responseToken == null || "".equals(responseToken)){
                    String encodedCredential;
                    encodedCredential = getEncodedCredential();
                    if (encodedCredential != null && !"".equals(encodedCredential)) {
                        requestWithHeader = request.newBuilder()
                                .addHeader(AUTHORIZATION, encodedCredential)
                                .build();
                        return chain.proceed(requestWithHeader);
                    }
                }


                // Token header related
                if(responseToken != null && !"".equals(responseToken)){
                    requestWithHeader = request.newBuilder()
                            .addHeader(TOKEN_HEADER,responseToken)
                            .build();
                    return chain.proceed(requestWithHeader);
                }

                return chain.proceed(request);
            }
        });

        clientBuilder.readTimeout(60, TimeUnit.SECONDS);
        clientBuilder.writeTimeout(60, TimeUnit.SECONDS);
        OkHttpClient client = clientBuilder.build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        return retrofit;
    }


    public static synchronized String getInstanceUrl() {
        return mInstanceUrl;
    }

    public static synchronized void setInstanceUrl(String url) {
        mInstanceUrl = url;
        init();
    }

    public static synchronized String getTenantIdentifier() {
        return mTenantIdentifier;
    }

    public static synchronized void setTenantIdentifier(String tenantIdentifier) {
        mTenantIdentifier = tenantIdentifier;
        init();
    }


    public static void readAPIPropertyFromPropertyFile() {
        Properties properties = new Properties();
        String propertyFilename = "api.properties";

//        mInstanceUrl = "http://localhost:8080/openboxes-0.8.0-SNAPSHOT/api/";

        mInstanceUrl = "http://10.42.0.1:8080/openboxes-qf/api/";


//        InputStream inputStream = API.class.getClassLoader().getResourceAsStream(propertyFilename);
//        if (inputStream != null) {
//            try {
//                properties.load(inputStream);
//                mInstanceUrl = properties.getProperty("api.evoucher.url");
//                username = properties.getProperty("api.evoucher.username");
//                password = properties.getProperty("api.evoucher.password");
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        } else {
//            try {
//                throw new FileNotFoundException("Property file '" + propertyFilename + "' not found in the classpath.");
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
//        }

    }

    public static void setResponseToken(String token) {
        responseToken = token;
    }

    public static String getResponseToken(){
        return responseToken;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        API.username = username;
    }

    public static String getEncryptedPassword() {
        return encryptedPassword;
    }

    public static void setEncryptedPassword(String encryptedPassword) {
        API.encryptedPassword = encryptedPassword;
    }

    private static String getEncodedCredential(){
        String encodedCredential, usernamePassword;
        usernamePassword = username + ":" + encryptedPassword;
        byte[] encodedBytes = Base64.encodeBase64(usernamePassword.getBytes());
        encodedCredential = "Basic " + new String(encodedBytes);
        return encodedCredential;
    }

    public interface AuthenticationService {
        @Headers({ACCEPT_ANY, CONNECTION_ALIVE})
        @POST(APIEndPoint.LOGIN)
        public Call<ResponseBody> login(@Body AuthenticationRequest authenticationRequest);


        @Headers({ACCEPT_ANY, CONNECTION_ALIVE})
        @POST(APIEndPoint.LOGOUT)
        public Call<ResponseBody> logout(@Header(COOKIE) String jsession);

    }

    public interface OfficerService {

    }




    public interface ProductService {

        @Headers({ACCEPT_ANY, CONNECTION_ALIVE})
        @GET(APIEndPoint.GENERIC + APIEndPoint.GENERIC_PRODUCT)
        public Call<ProductListResponse> getGenericProduct(@Header(COOKIE) String jsession);

        @Headers({ACCEPT_ANY, CONNECTION_ALIVE})
        @POST(APIEndPoint.CATEGORIES)
        public Call<ProductCategory> pushProductCategory(@Header(COOKIE) String jsession, @Body ProductCategory productCategory);


        @Headers({ACCEPT_ANY, CONNECTION_ALIVE})
        @GET(APIEndPoint.PRODUCTS)
        public Call<ProductListResponse> getProducts(@Header(COOKIE) String jsession);


        //TODO: requires clarification on how to send search parameter
//        @Headers({ACCEPT_ANY, CONNECTION_ALIVE})
//        @GET(APIEndPoint.PRODUCTS)
//        public Call<ProductListResponse> getProducts(@Header(COOKIE) String jsession,
//                                                     @Body SearchRequest searchRequest);


        @Headers({ACCEPT_ANY, CONNECTION_ALIVE})
        @POST(APIEndPoint.PRODUCTS)
        public Call<Product> pushProduct(@Header(COOKIE) String jsession, @Body Product product);


        @Headers({ACCEPT_ANY, CONNECTION_ALIVE})
        @GET(APIEndPoint.PRODUCTS + "/{productId}")
        public Call<Product> getProducts(@Header(COOKIE) String jsession, @Path("productId") String productId);


        @Headers({ACCEPT_ANY, CONNECTION_ALIVE})
        @GET(APIEndPoint.PRODUCTS + "/{productId}" + APIEndPoint.AVAILABLE_ITEMS)
        public Call<AvailableItemListResponse> getInventoryItems(@Header(COOKIE) String jsession,
                                                                 @Path("productId") String productId,
                                                                 @Query("location.id") String locationId);

        //AvailableProductAssociationResponse
        @Headers({ACCEPT_ANY, CONNECTION_ALIVE})
        @GET(APIEndPoint.PRODUCTS + "/{productId}" + APIEndPoint.ASSOCIATED_PRODUCTS)
        public Call<AvailableItemListResponse> getProductAssociations(@Header(COOKIE) String jsession,
                                                                 @Path("productId") String productId,
                                                                 @Query("type") List<String> typeList,
                                                                 @Query("location.id") String locationId);


    }


    public interface LocationService {

        @Headers({ACCEPT_ANY, CONNECTION_ALIVE})
        @GET(APIEndPoint.LOCATIONS)
        public Call<LocationListResponse> getLocations(@Header(COOKIE) String jsession);


        @Headers({ACCEPT_ANY, CONNECTION_ALIVE})
        @GET(APIEndPoint.INTERNAL_LOCATIONS)
        public Call<LocationListResponse> getInternalLocations(@Header(COOKIE) String jsession,
                                                               @Query("location.id") String locationId);

        @Headers({ACCEPT_ANY, CONNECTION_ALIVE})
        @GET(APIEndPoint.INTERNAL_LOCATIONS + "/{locationId}")
        public Call<LocationData> getInternalLocation(@Header(COOKIE) String jsession,
                                                      @Path("locationId") String locationId);


        //api to choose location
//        $ curl -i -c cookies.txt -X POST -H "Content-Type: application/json" https://yourserver.com/openboxes/api/chooseLocation/:locationId

        @Headers({ACCEPT_ANY, CONNECTION_ALIVE})
        @GET(APIEndPoint.CHOOSE_LOCATION + "/{locationId}")
        public Call<ResponseBody> chooseLocation(@Header(COOKIE) String jsession,
                                                      @Path("locationId") String locationId);

    }


    public interface StockMovementService {

        @Headers({ACCEPT_ANY, CONNECTION_ALIVE})
        @GET(APIEndPoint.STOCK_MOVEMENTS)
        public Call<StockMovementListResponse> getStockMovementList(@Header(COOKIE) String jsession,
                                                                    @Query("max") String max, @Query("offset") String offset,
                                                                    @Query("exclude") String exclude);


        @Headers({ACCEPT_ANY, CONNECTION_ALIVE})
        @GET(APIEndPoint.STOCK_MOVEMENTS + "/{id}")
        public Call<StockMovementResponse> getStockMovementById(@Header(COOKIE) String jsession, @Path("id") String id);


        @Headers({ACCEPT_ANY, CONNECTION_ALIVE})
        @POST(APIEndPoint.STOCK_MOVEMENTS)
        public Call<StockMovementResponse> pushStockMovement(@Header(COOKIE) String jsession, @Body StockMovement stockMovement);


        @Headers({ACCEPT_ANY, CONNECTION_ALIVE})
        @POST(APIEndPoint.STOCK_MOVEMENTS + "/{id}")
        public Call<StockMovementResponse> updateStockMovement(@Header(COOKIE) String jsession,
                                                               @Body StockMovement stockMovement,
                                                               @Path("id") String id);


        @Headers({ACCEPT_ANY, CONNECTION_ALIVE})
        @POST(APIEndPoint.STOCK_MOVEMENTS + "/{id}")
        public Call<StockMovementResponse> pushStockMovementItems(@Header(COOKIE) String jsession,
                                                               @Body StockMovement stockMovement,
                                                               @Path("id") String id);


    }


    public interface ShipmentService {
        @Headers({ACCEPT_ANY, CONNECTION_ALIVE})
        @GET(APIEndPoint.GENERIC + APIEndPoint.GENERIC_SHIPMENT)
        public Call<ShipmentResponse> getShipment(@Header(COOKIE) String jsession,
                                                           @Query("max") String max);



        @Headers({ACCEPT_ANY, CONNECTION_ALIVE})
        @POST(APIEndPoint.GENERIC + APIEndPoint.GENERIC_SHIPMENT + APIEndPoint.SEARCH)
        public Call<ShipmentResponse> searchShipment(@Header(COOKIE) String jsession,
                                                  @Body SearchAttributesRequest searchAttributesRequest);



        @Headers({ACCEPT_ANY, CONNECTION_ALIVE})
        @GET(APIEndPoint.GENERIC + APIEndPoint.GENERIC_SHIPMENT_TYPE)
        public Call<ShipmentTypeResponse> getShipmentType(@Header(COOKIE) String jsession);


        @Headers({ACCEPT_ANY, CONNECTION_ALIVE})
        @POST(APIEndPoint.GENERIC + APIEndPoint.GENERIC_SHIPMENT)
        public Call<SingleShipmentResponse> pushShipmentInitialRequest(@Header(COOKIE) String jsession,
                                                                       @Body ShipmentInitialRequest shipmentInitialRequest);


        @Headers({ACCEPT_ANY, CONNECTION_ALIVE})
        @POST(APIEndPoint.GENERIC + APIEndPoint.GENERIC_SHIPMENT)
        public Call<ShipmentResponse> pushShipment(@Header(COOKIE) String jsession,
                                                   @Body Shipment shipment);



        @Headers({ACCEPT_ANY, CONNECTION_ALIVE})
        @POST(APIEndPoint.GENERIC + APIEndPoint.GENERIC_SHIPMENT_ITEM)
        public Call<ShipmentResponse> pushShipmentItem(@Header(COOKIE) String jsession,
                                                   @Body List<ShipmentItemRequest> shipmentItemRequestList);

    }

    public interface RequisitionService {
        @Headers({ACCEPT_ANY, CONNECTION_ALIVE})
        @POST(APIEndPoint.GENERIC + APIEndPoint.GENERIC_REQUISITION)
        public Call<RequisitionRequest> pushRequisition(@Header(COOKIE) String jsession,
                                                       @Body RequisitionRequest requisitionRequest);


        @Headers({ACCEPT_ANY, CONNECTION_ALIVE})
        @GET(APIEndPoint.GENERIC + APIEndPoint.GENERIC_REQUISITION)
        public Call<RequisitionRequest> getRequisition(@Header(COOKIE) String jsession);
    }


    public interface SettingService {
        @Headers({ACCEPT_ANY, CONNECTION_ALIVE})
        @GET(APIEndPoint.SETTINGS + APIEndPoint.SERVER_TIME)
        public Call<ServerTimeResponse> getServerTime();

    }


}

