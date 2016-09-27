package booking.server;



import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class GetDatesServlet extends HttpServlet {
    private Logger logger = LogManager.getLogger(this.getClass().getSimpleName());
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Received request for dates");

        String startDate = request.getParameter("start");
        logger.info("start date = " + startDate);

        String endDate = request.getParameter("end");
        logger.info("end date = " + endDate);

        SQLHelper sqlHelper = null;
        try {
            //Init SQL Helper and Gson
            sqlHelper = new SQLHelper();
            Gson gson = new Gson();
            //Compute the appointments in the database between the received dates, and parse them to a JSON String.
            String jsonString = gson.toJson(sqlHelper.getAppointments(startDate,endDate));

            Utils.sendJSON(jsonString,response);

            sqlHelper.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}