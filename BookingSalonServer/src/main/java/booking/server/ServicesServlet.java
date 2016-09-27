package booking.server;



import booking.server.data.ServicesForTable;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class ServicesServlet extends HttpServlet {
    private Logger logger = LogManager.getLogger(this.getClass().getSimpleName());
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        logger.info("Received services request!");

        SQLHelper sqlHelper = null;
        try {
            sqlHelper = new SQLHelper();
            Gson gson = new Gson();
            //String jsonString = gson.toJson(sqlHelper.getServices());
            String jsonString = gson.toJson(new ServicesForTable(sqlHelper.getServices()));
            logger.info("JSON String - " + jsonString);
            Utils.sendJSON(jsonString,response);
            sqlHelper.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}