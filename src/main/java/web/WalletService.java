package web;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public interface WalletService {

    @GET
    @Path("/{id}/balance")
    @Produces(MediaType.APPLICATION_JSON)
    Response getBalance(@PathParam("id") int id);

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    Response getAllWallets();

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    Response getWalletById(@PathParam("id") int id);

    @POST
    @Path("/{id}/buy")
    @Produces(MediaType.APPLICATION_JSON)
    Response buyGood(@PathParam("id") int id,
                     @QueryParam("price") double price);

    @POST
    @Path("/{id}/putMoney")
    @Produces(MediaType.APPLICATION_JSON)
    Response putMoneyOnWallet(@PathParam("id") int id,
                              @QueryParam("moneyQnt") double moneyQnt);

    @POST
    @Path("/add")
    @Produces(MediaType.APPLICATION_JSON)
    Response addNewWallet();

    @DELETE
    @Path("/{id}/delete")
    @Produces(MediaType.APPLICATION_JSON)
    Response deleteWalletById(@PathParam("id") Integer id);
}
