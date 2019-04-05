package web.impl;

import web.WalletService;
import web.util.ResponseHandler;

import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("/service/wallet")
public class WalletServiceImpl implements WalletService {
    @Override
    public Response getBalance(int id) {
        return ResponseHandler.getBalance(id);
    }

    @Override
    public Response getAllWallets(UriInfo uriInfo) {
        return ResponseHandler.getAllWallets(uriInfo);
    }

    @Override
    public Response getWalletById(int id) {
        return ResponseHandler.getWalletById(id);
    }

    @Override
    public Response buyGood(int id, double price) {
        return ResponseHandler.buyGood(id, price);
    }

    @Override
    public Response putMoneyOnWallet(int id, double moneyQnt) {
        return ResponseHandler.putMoneyOnWallet(id, moneyQnt);
    }

    @Override
    public Response addNewWallet() {
        return ResponseHandler.addNewWallet();
    }

    @Override
    public Response deleteWalletById(Integer id) {
        return ResponseHandler.deleteWalletById(id);
    }
}
