package booking.server;



import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;


public class RegisterServlet extends HttpServlet {
    private Logger logger = LogManager.getLogger(this.getClass().getSimpleName());
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        OutputStream printWriter = response.getOutputStream();

        SQLHelper sqlHelper = null;
        try {
            sqlHelper = new SQLHelper();

            //Attempt to register the user.
            boolean succeeded = sqlHelper.registerUser(request.getParameter("name"),request.getParameter("facebook_id"), request.getParameter("email") );

            Utils.sendBinaryResult(succeeded,response);
            sqlHelper.close();

        } catch (Exception e) {
            printWriter.write("err".getBytes());
            e.printStackTrace();
        }

    }

}