package web.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import exception.WalletException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.HashMap;
import java.util.Map;

@Provider
public class MyExceptionMapper implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception e) {
        Map<String, Object> map = new HashMap<>();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        if (e instanceof WalletException) {
            WalletException we = (WalletException) e;
            int statusCode = we.getStatus().getStatusCode();

            map.put("statusCode", we.getStatus().getStatusCode());
            map.put("error", we.getStatus().getReasonPhrase());
            map.put("reason", we.getMessage());
            return Response.status(statusCode).entity(gson.toJson(map)).build();
        } else {
            map.put("message", e.getMessage());
        }

        return Response.status(200).entity(gson.toJson(map)).build();
    }
}
