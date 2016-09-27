package booking.server;


import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class Utils {

    /**
     * Send 'yes' or 'no' back to the caller, according to the received boolean value.
     * @param succeeded Whether to send a successful response ('yes) or an unsuccessful response ('no).
     * @param response
     * @throws IOException
     */
    public static void sendBinaryResult(boolean succeeded,HttpServletResponse response) throws IOException
    {
        String res  = succeeded ? "yes" : "no";;

        response.setContentType("text");
        response.addHeader("Access-Control-Allow-Origin", "*");
        byte[] utf8JsonString = res.getBytes("UTF8");
        response.getOutputStream().write(utf8JsonString, 0, utf8JsonString.length);
    }

    /**
     * Send JSON as a response to a request.
     * @param jsonString The Json to send.
     * @param response
     * @throws IOException
     */
    public static void sendJSON(String jsonString, HttpServletResponse response) throws IOException {
        response.setContentType("application/json; charset=UTF-8");
        response.addHeader("Access-Control-Allow-Origin", "*");
        byte[] utf8JsonString = jsonString.getBytes("UTF8");
        response.getOutputStream().write(utf8JsonString, 0, utf8JsonString.length);
    }
}
