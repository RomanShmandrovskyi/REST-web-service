package web.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import dao.WalletDao;
import exception.WalletException;
import model.Wallet;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.util.List;

public class ResponseHandler {
    private static final String BUY_GOOD = "product was purchased successfully";
    private static final String REFILL_BALANCE = "balance was refill successfully";
    private static final String ADD_NEW_WALLET = "new wallet added successfully";
    private static final String WALLET_DELETED = "wallet deleted successfully";

    private static final String MAX_LIMIT_EXCEEDED = "you have exceeded your maximum limit";
    private static final String CREDIT_LIMIT_EXCEEDED = "you have exceeded your credit limit";
    private static final String WALLET_NOT_FOUND = "there is no one wallet with such id";
    private static final String NO_ONE_WALLET = "there is no one wallet! Try to add one!";

    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static WalletDao dao = new WalletDao();

    public static Response getAllWallets() {
        List<Wallet> wallets = dao.getAllWallets();

        if (wallets.size() == 0) {
            throw new WalletException(NO_ONE_WALLET, Status.NO_CONTENT);
        }

        return Response
                .status(200)
                .entity(gson.toJson(wallets))
                .build();
    }

    public static Response getBalance(int id) {
        double balance = dao.getBalance(id);

        if (balance == Double.MAX_VALUE) {
            throw new WalletException(WALLET_NOT_FOUND, Status.NO_CONTENT);
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
            throw new WalletException(WALLET_NOT_FOUND, Status.NO_CONTENT);
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
        dao.addNewWallet();
        JsonObject message = new JsonObject();
        message.addProperty("message", ADD_NEW_WALLET);
        return Response
                .status(200)
                .entity(message.toString())
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
