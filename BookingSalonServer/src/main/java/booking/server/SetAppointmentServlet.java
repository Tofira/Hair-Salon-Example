package booking.server;



import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;


public class SetAppointmentServlet extends HttpServlet {
    private Logger logger = LogManager.getLogger(this.getClass().getSimpleName());
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        OutputStream printWriter = response.getOutputStream();

        logger.info("Received request to set appointment");

        String startDateMilli = request.getParameter("start");
        logger.info("start date = " + startDateMilli);

        String endDateMilli = request.getParameter("end");
        logger.info("end date = " + endDateMilli);

        String userFacebookId = request.getParameter("user_facebook_id");
        logger.info("userFacebookId = " + userFacebookId);

        int serviceID = Integer.parseInt(request.getParameter("service_id"));
        logger.info("serviceID = " + serviceID);

        SQLHelper sqlHelper = null;
        try {
            sqlHelper = new SQLHelper();

            //Attempt to insert the given appointment.
            boolean succeeded = sqlHelper.insertNewAppointment(startDateMilli,endDateMilli, userFacebookId, serviceID);

            Utils.sendBinaryResult(succeeded,response);
            sqlHelper.close();
        } catch (Exception e) {
            printWriter.write("err".getBytes());
            e.printStackTrace();
        }

    }

}