package model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "wallet")
public class Wallet {
    private int walletId;
    private double balance;

    public Wallet(int id) {
        this.walletId = id;
        this.balance = 100.0;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getWalletId() {
        return walletId;
    }
}
