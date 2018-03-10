package exception;

import javax.ws.rs.core.Response.Status;

public class WalletException extends RuntimeException {

    private String message;
    private Status status;

    public WalletException(String message, Status status) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public Status getStatus() {
        return status;
    }
}
