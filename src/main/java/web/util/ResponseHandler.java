package web.util;

import com.google.gson.*;
import com.jayway.jsonpath.JsonPath;
import dao.WalletDao;
import exception.WalletException;
import model.Wallet;
import org.codehaus.jackson.map.ObjectWriter;
import org.json.JSONArray;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ResponseHandler {
    private static final String BUY_GOOD = "Product was purchased successfully";
    private static final String REFILL_BALANCE = "Balance was refill successfully";
    private static final String ADD_NEW_WALLET = "New wallet added successfully";
    private static final String WALLET_DELETED = "Wallet deleted successfully";

    private static final String MAX_LIMIT_EXCEEDED = "You have exceeded your maximum limit";
    private static final String CREDIT_LIMIT_EXCEEDED = "You have exceeded your credit limit";
    private static final String WALLET_NOT_FOUND = "There is no one wallet with such id";

    private static Gson gson = new GsonBuilder().create();
    private static WalletDao dao = new WalletDao();

    public static Response getAllWallets(UriInfo uriInfo) {
        System.out.println(uriInfo.getPathParameters());
        System.out.println(uriInfo.getQueryParameters());

        List<Wallet> wallets = dao.getAllWallets();

        if (wallets.size() == 0) {
            return Response.status(200)
                    .entity(gson.toJson(new JsonArray()))
                    .build();
        }

        if (uriInfo.getQueryParameters().size() == 0) {
            return Response.status(200)
                    .entity(gson.toJson(wallets))
                    .build();
        }

        JSONArray walletsArr = new JSONArray(wallets);

        for (String key : uriInfo.getQueryParameters().keySet()) {
            if (!walletsArr.getJSONObject(0).has(key)) {
                return Response.status(200)
                        .entity(gson.toJson(new JsonArray()))
                        .build();
            }
        }

        return Response
                .status(200)
                .entity(gson.toJson(wallets))
                .build();
    }

    public static Response getBalance(int id) {
        double balance = dao.getBalance(id);

        if (balance == Double.MAX_VALUE) {
            throw new WalletException(WALLET_NOT_FOUND, Status.NOT_FOUND);
        }

        JsonObject res = new JsonObject();
        res.addProperty("balance", balance);

        return Response
                .status(200)
                .entity(res.toString())
                .build();
    }

    public static Response getWalletById(int id) {
        Wallet wallet = dao.getWalletById(id);

        if (wallet == null) {
            throw new WalletException(WALLET_NOT_FOUND, Status.NOT_FOUND);
        }

        return Response
                .status(200)
                .entity(gson.toJson(dao.getWalletById(id)))
                .build();
    }

    public static Response buyGood(int id, double price) {
        JsonObject message = new JsonObject();
        double result = dao.buyGood(price, id);

        if (result == Double.MAX_VALUE) {
            throw new WalletException(WALLET_NOT_FOUND, Status.NO_CONTENT);
        } else {
            if (result < -50.0) {
                dao.cancelTransaction(id);
                throw new WalletException(CREDIT_LIMIT_EXCEEDED, Status.NOT_MODIFIED);
            } else {
                Wallet wallet = dao.getWalletById(id);
                message.addProperty("message", BUY_GOOD);
                message.addProperty("currentBalance", wallet.getBalance());
                return Response
                        .status(200)
                        .entity(gson.toJson(wallet))
                        .entity(message.toString())
                        .build();
            }
        }
    }

    public static Response putMoneyOnWallet(int id, double moneyQnt) {
        JsonObject message = new JsonObject();
        double balance = dao.putMoneyOnWallet(moneyQnt, id);

        if (balance == Double.MAX_VALUE) {
            throw new WalletException(WALLET_NOT_FOUND, Status.NO_CONTENT);
        } else {
            if (balance > 1000) {
                dao.cancelTransaction(id);
                throw new WalletException(MAX_LIMIT_EXCEEDED, Status.NOT_MODIFIED);
            } else {
                Wallet wallet = dao.getWalletById(id);
                message.addProperty("message", REFILL_BALANCE);
                return Response
                        .status(200)
                        .entity(gson.toJson(wallet))
                        .entity(message.toString())
                        .build();
            }
        }
    }

    public static Response addNewWallet() {
        Wallet wallet = dao.addNewWallet();
        JsonObject response = new JsonObject();
        response.addProperty("message", ADD_NEW_WALLET);
        response.add("wallet", gson.toJsonTree(wallet));
        return Response
                .status(200)
                .entity(response.toString())
                .build();
    }

    public static Response deleteWalletById(int id) {
        Wallet wallet = dao.getWalletById(id);
        JsonObject message = new JsonObject();
        if (wallet == null) {
            throw new WalletException(WALLET_NOT_FOUND, Status.BAD_REQUEST);
        } else {
            dao.deleteWalletById(id);
            message.addProperty("message", WALLET_DELETED);
        }

        return Response
                .status(200)
                .entity(message.toString())
                .build();
    }
}
