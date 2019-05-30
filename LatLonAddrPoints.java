package servletTest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

public class LatLonAddrPoints extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        String userName = "root";
        String password = "sergey342535";
        String connectionURL = "jdbc:mysql://localhost:3306/netcracker?serverTimezone=UTC&useSSL=false";
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try(Connection connection = DriverManager.getConnection(connectionURL, userName, password);
            Statement statement = connection.createStatement()){
            ResultSet pointSet = statement.executeQuery("Select Name, id from sp_objects where type = 'TradePoint'");
            pointSet.last();
            int size = pointSet.getRow();
            pointSet.beforeFirst();
            String[] pointsArr = new String[size];
            //String[] coordsLat = new String[size];
            ResultSet resultLatitude = statement.executeQuery("Select Value from sp_objectsattributes " +
                    "where AttrId = 3");
            int i = 0;
            while(resultLatitude.next()){
                //temp.setLatitude(Double.valueOf(resultLatitude.getString("Value")));
                pointsArr[i] = resultLatitude.getString("Value");
                i++;
            }
            ResultSet resultLongitude = statement.executeQuery("Select Value from sp_objectsattributes " +
                    "where AttrId = 4");
            i = 0;
            while (resultLongitude.next()){
                //coords[i] = coordsLat[i] + "," + resultLongitude.getString("Value");
                pointsArr[i] += "/" + resultLongitude.getString("Value");
                i++;
            }
            ResultSet resultAddr = statement.executeQuery("Select Value from sp_objectsattributes " +
                    "where AttrId = 6");
            i = 0;
            while (resultAddr.next()){
                pointsArr[i] += "/" + resultAddr.getString("Value");
                i++;
//                if(i < size)
//                    pointsArr[i] += "=";
            }
            for (int j = 0; j < size; j++) {
                out.println(pointsArr[i]);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
