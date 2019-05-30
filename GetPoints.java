package servletTest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class GetPoints extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        try {
            String[] coords = getPointsFromDB();
            for (int i = 0; i < coords.length; i++) {
                out.write(coords[i]);
                if(i + 1 != coords.length)
                    out.write("/");
                System.out.println(coords[i]);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String[] getPointsFromDB() throws Exception{
        String userName = "root";
        String password = "sergey342535";
        String connectionURL = "jdbc:mysql://localhost:3306/netcracker?serverTimezone=UTC&useSSL=false";
        Class.forName("com.mysql.cj.jdbc.Driver");
        try(Connection connection = DriverManager.getConnection(connectionURL, userName, password);
            Statement statement = connection.createStatement()){
            System.out.println("Connected to getPoints");
            ResultSet resultSet = statement.executeQuery("Select Name, id from sp_objects where type = 'TradePoint'");
            resultSet.last();
            int size = resultSet.getRow();
            resultSet.beforeFirst();
            String[] names = new String[size];
            int[] ids = new int[size];
//            for(int i = 0; i < size; i++){
//                ids[i] = resultSet.getInt("id");
                //names[i] = resultSet.getString("Name");
//            }
            String[] coordsLat = new String[size];
            ResultSet resultLatitude = statement.executeQuery("Select Value from sp_objectsattributes " +
                    "where AttrId = 3");
            int i = 0;
            while(resultLatitude.next()){
                coordsLat[i] = resultLatitude.getString("Value");
                i++;
            }
            ResultSet resultLongitude = statement.executeQuery("Select Value from sp_objectsattributes " +
                    "where AttrId = 4");
            i = 0;
            String[] coords = new String[size];
            while (resultLongitude.next()){
                coords[i] = coordsLat[i] + "," + resultLongitude.getString("Value");
                i++;
            }

            return coords;
        }
    }
}
