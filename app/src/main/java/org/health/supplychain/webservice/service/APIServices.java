package org.health.supplychain.webservice.service;

import org.health.supplychain.webservice.API;
import org.health.supplychain.webservice.entities.AuthenticationRequest;
import org.health.supplychain.webservice.entities.AvailableItemListResponse;
import org.health.supplychain.webservice.entities.LocationData;
import org.health.supplychain.webservice.entities.LocationListResponse;
import org.health.supplychain.webservice.entities.Product;
import org.health.supplychain.webservice.entities.ProductCategory;
import org.health.supplychain.webservice.entities.ProductListResponse;
import org.health.supplychain.webservice.entities.Requisition;
import org.health.supplychain.webservice.entities.RequisitionRequest;
import org.health.supplychain.webservice.entities.ServerTimeResponse;
import org.health.supplychain.webservice.entities.ShipmentInitialRequest;
import org.health.supplychain.webservice.entities.ShipmentItemRequest;
import org.health.supplychain.webservice.entities.ShipmentRequest;
import org.health.supplychain.webservice.entities.ShipmentResponse;
import org.health.supplychain.webservice.entities.ShipmentType;
import org.health.supplychain.webservice.entities.ShipmentTypeResponse;
import org.health.supplychain.webservice.entities.SingleShipmentResponse;
import org.health.supplychain.webservice.entities.StockMovement;
import org.health.supplychain.webservice.entities.StockMovementListResponse;
import org.health.supplychain.webservice.entities.StockMovementResponse;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Response;

/**
 * Created by Aworkneh on 5/3/2018.
 */

public class APIServices {


    public Response authenticateUser(String username, String password) throws IOException {
        AuthenticationRequest authenticationRequest = new AuthenticationRequest();
        authenticationRequest.setUsername(username);
        authenticationRequest.setPassword(password);

        return API.authenticationService.login(authenticationRequest).execute();
    }

    public Response logoutUser(String jsession) throws IOException {
        return API.authenticationService.logout(jsession).execute();
    }

    public Response<ServerTimeResponse> getServerTime() throws IOException {
        return API.settingService.getServerTime().execute();
    }


    public Response<ProductListResponse> getGenericProduct (String session) throws IOException {
        return API.productService.getGenericProduct(session).execute();
    }

    public Response<LocationListResponse> getLocations(String session) throws IOException {
        return API.locationService.getLocations(session).execute();
    }


    public Response<ProductCategory> pushProductCategory(String session, ProductCategory productCategory) throws IOException {
        return API.productService.pushProductCategory(session, productCategory).execute();
    }


    public Response<LocationListResponse> getInternalLocations(String session, String locationId) throws IOException {
        return API.locationService.getInternalLocations(session, locationId).execute();
    }

    public Response<LocationData> getInternalLocation(String session, String locationId) throws IOException {
        return API.locationService.getInternalLocation(session, locationId).execute();
    }

    public Response<ProductListResponse> getProducts (String session) throws IOException {
        return API.productService.getProducts(session).execute();
    }

    //TODO: requires clarification on how to send search parameter
    public Response<ProductListResponse> getProducts (String session, String id, String name, Integer offset, Integer max) throws IOException {

        //TODO: requires clarification on how to send search parameter
//        SearchRequest searchRequest = new SearchRequest(id, name, offset, max);


        return API.productService.getProducts(session).execute();
    }


    public Response<Product> pushProduct(String session, Product product) throws IOException {
        return API.productService.pushProduct(session, product).execute();
    }

    public Response<Product> getProducts(String session, String id) throws IOException {
        return API.productService.getProducts(session, id).execute();
    }

    public Response<AvailableItemListResponse> getInventoryItems(String session, String productId, String locationId) throws IOException {
        return API.productService.getInventoryItems(session, productId, locationId).execute();
    }

    public Response<AvailableItemListResponse> getProductAssociations(String session, String productId,
                                                                      List<String> typeList, String locationId) throws IOException {
        return API.productService.getProductAssociations(session, productId, typeList, locationId).execute();
    }


    public Response<StockMovementListResponse> getStockMovementList(String session, String max, String offset, String exclude) throws IOException {
        return API.stockMovementService.getStockMovementList(session, max, offset, exclude).execute();
    }


    public Response<StockMovementResponse> getStockMovementById(String session, String id) throws IOException {
        return API.stockMovementService.getStockMovementById(session, id).execute();
    }

    public Response<StockMovementResponse> pushStockMovement(String session, StockMovement stockMovement) throws IOException {
        return API.stockMovementService.pushStockMovement(session, stockMovement).execute();
    }

    public Response<StockMovementResponse> updateStockMovement(String session, StockMovement stockMovement, String id) throws IOException {
        return API.stockMovementService.updateStockMovement(session, stockMovement, id).execute();
    }

    public Response<ResponseBody> chooseLocation(String session, String locationId) throws IOException {
        return API.locationService.chooseLocation(session, locationId).execute();
    }

    public Response<ShipmentResponse> getShipment(String session, String max) throws IOException {
        return API.shipmentService.getShipment(session, max).execute();
    }


    public Response<SingleShipmentResponse> pushShipmentInitialRequest(String session, ShipmentInitialRequest shipmentInitialRequest) throws IOException {
        return API.shipmentService.pushShipmentInitialRequest(session, shipmentInitialRequest).execute();
    }


    public Response<ShipmentResponse> pushShipment(String session, ShipmentRequest shipmentRequest) throws IOException {
        return API.shipmentService.pushShipment(session, shipmentRequest.getData().get(0)).execute();
    }

    public Response<ShipmentResponse> pushShipmentItem(String session, List<ShipmentItemRequest> shipmentItemRequestList) throws IOException {
        return API.shipmentService.pushShipmentItem(session, shipmentItemRequestList).execute();
    }

    public Response<ShipmentTypeResponse> getShipmentType(String session) throws IOException {
        return API.shipmentService.getShipmentType(session).execute();
    }

    public Response<RequisitionRequest> pushRequisition(String session, RequisitionRequest requisitionRequest) throws IOException {
        return API.requisitionService.pushRequisition(session, requisitionRequest).execute();
    }


    public Response<RequisitionRequest> getRequisition(String session) throws IOException {
        return API.requisitionService.getRequisition(session).execute();
    }
}
