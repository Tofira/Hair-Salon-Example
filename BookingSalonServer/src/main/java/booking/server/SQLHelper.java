package booking.server;

import booking.server.data.Appointment;
import booking.server.data.Service;

import java.sql.*;
import java.text.ParseException;
import java.util.*;

public class SQLHelper {
    private static final String DATABASE_URL_START = "jdbc:mysql://";//
    private static final String DATABASE_HOST = "localhost";
    private static final String DATABASE_NAME = "booking_saloon";
    private static final String DATABASE_USERNAME = "root";
    private static final String DATABASE_PASSWORD= "pass123";

    private static final String SERVICES_TABLE_NAME = "services";
    private static final String SERVICES_COLUMN_SERVICE_ID = "idservices";
    private static final String SERVICES_COLUMN_SERVICE_NAME = "service_name";
    private static final String SERVICES_COLUMN_SERVICE_DURATION = "service_duration";
    private static final String SERVICES_COLUMN_SERVICE_DESCRIPTION = "service_description";
    private static final String SERVICES_COLUMN_SERVICE_COST = "service_cost";

    private Connection connect = null;
    private Statement statement = null;
    private ResultSet resultSet = null;

    public SQLHelper() throws Exception
    {

        Class.forName("com.mysql.jdbc.Driver");

        connect = DriverManager
                .getConnection(DATABASE_URL_START + DATABASE_HOST + "/" + DATABASE_NAME + "?" + "user=" + DATABASE_USERNAME + "&password="+DATABASE_PASSWORD);

        statement = connect.createStatement();
    }

    /**
     * Register the user to the Database, if he's not already registered.
     * @param name
     * @param facebookID
     * @param email
     * @return Whether the operation has been successful (returns false if the user is already registered).
     * @throws SQLException
     * @throws ParseException
     */
    public boolean registerUser(String name,String facebookID, String email) throws SQLException, ParseException {

        //Prepare and execute the query designed to check if the user is already registered.
        resultSet = statement.executeQuery("select COUNT(*) from users where facebook_id = " + facebookID);
        resultSet.next();
        if(resultSet.getInt("COUNT(*)") > 0)
            return false;

        //Prepare and execute the insertion command.
        PreparedStatement pstmt = connect.prepareStatement(
                "INSERT INTO users ( facebook_id,name,email) " +
                        " values (?, ?, ?)");
        pstmt.setString( 1, facebookID );
        pstmt.setString( 2, name);
        pstmt.setString( 3, email);

        pstmt.execute();
        return true;
    }

    /**
     * Insert a new appointment to the database, if the given date and time is available.
     * @param startDateMilli The starting time of the appointment, in milliseconds.
     * @param endDateMilli The ending time of the appointment, in milliseconds.
     * @param userFacebookId The user's unique Facebook ID.
     * @param serviceID The service ID.
     * @return Whether the operation has been successful. The method returns false if the given appointment time is already booked.
     * @throws SQLException
     * @throws ParseException
     */
    public boolean insertNewAppointment(String startDateMilli,String endDateMilli, String userFacebookId, int serviceID) throws SQLException, ParseException {

        //Convert the given dates into Timestamp objects.
        java.sql.Timestamp startDate = new Timestamp(Long.parseLong(startDateMilli));
        java.sql.Timestamp endDate = new Timestamp(Long.parseLong(endDateMilli));

        //Execute the statement designed to check whether the given appointment time is available.
        PreparedStatement preparedStatement = connect.prepareStatement("SELECT COUNT(*) FROM appointments WHERE NOT (start_time >= ? OR end_time <= ?)" );
        preparedStatement.setTimestamp(1, endDate);
        preparedStatement.setTimestamp(2, startDate);
        preparedStatement.execute();

        //Check how many items has been returned - if 0, it means the appointment time is free. Otherwise, it's booked.
        resultSet = preparedStatement.getResultSet();
        resultSet.next();
        if(resultSet.getInt("COUNT(*)") > 0)
            return false;

        //Prepare and execute the insertion statement.
        PreparedStatement pstmt = connect.prepareStatement(
                "INSERT INTO appointments ( start_time, end_time, customer_facebook_id, serviceID) " +
                        " values (?, ?, ?, ?)");
        pstmt.setTimestamp( 1, startDate );
        pstmt.setTimestamp( 2, endDate);
        pstmt.setString(3, userFacebookId);
        pstmt.setInt(4, serviceID);
        pstmt.execute();

        return true;
    }

    /**
     * Fetches the appointments from the database in the given range.
     * @param startTime
     * @param endTime
     * @return - ArrayList of Appointments between the given dates.
     */
    public ArrayList<Appointment> getAppointments(String startTime, String endTime)
    {
        ArrayList<Appointment> appointments = new ArrayList<Appointment>();
        try {
            // Statements allow to issue SQL queries to the database
            statement = connect.createStatement();
            // Result set get the result of the SQL query
            resultSet = statement
                    .executeQuery("SELECT * FROM appointments WHERE DATE(start_time) >= '" + startTime+ "'" +
                            "AND DATE(end_time) <= '" + endTime+ "'");

            //Fullcalendar requires each event(appointment) to have and ID.
            int id = 0;
            //Iterate over the given results and fetch them into Appointment objects.
            while (resultSet.next()) {
                String appStartDate = resultSet.getString("start_time");
                String appEndDate = resultSet.getString("end_time");

                System.out.println("appStartDate: " + appStartDate + ", appEndDate - " + appEndDate);

                appointments.add( new Appointment("",Integer.toString(++id),appStartDate,appEndDate));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointments;
    }

    /**
     * Fetch and returns all the services from the datatabase.
     * @return ArrayList of Services.
     */
    public ArrayList<Service> getServices()
    {
        ArrayList<Service> services = new ArrayList<Service>();
        try {
            // Statements allow to issue SQL queries to the database
            statement = connect.createStatement();
            // Result set get the result of the SQL query
            resultSet = statement
                    .executeQuery("select * from " + SERVICES_TABLE_NAME);

            //Iterate over the given results and fetch them Service object.
            while (resultSet.next()) {
                String serviceName = resultSet.getString(SERVICES_COLUMN_SERVICE_NAME);
                String serviceDuration = resultSet.getString(SERVICES_COLUMN_SERVICE_DURATION);
                String serviceDescription = resultSet.getString(SERVICES_COLUMN_SERVICE_DESCRIPTION);
                String serviceCost = resultSet.getString(SERVICES_COLUMN_SERVICE_COST);
                int serviceID = resultSet.getInt(SERVICES_COLUMN_SERVICE_ID);

                System.out.println("service Name: " + serviceName + ", duration - " + serviceDuration + ", description - " + serviceDescription + ", cost - " + serviceCost+ ", serviceID - " + serviceID);

                services.add( new Service(serviceID,serviceName,serviceDuration,serviceDescription,serviceCost) );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return services;
    }


    /**
     * Close the active connection to the database.
      */
    public void close() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }

            if (statement != null) {
                statement.close();
            }

            if (connect != null) {
                connect.close();
            }
        } catch (Exception e) {

        }
    }
}