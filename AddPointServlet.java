package servletTest;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.Scanner;
import java.util.Set;

public class AddPointServlet extends HttpServlet {
    static int pointId = 0;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String coords = req.getParameter("coords");
        String address = req.getParameter("address");
        System.out.println(address + " " + coords);
        try {
            sendPointToDB(coords, address);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //out.println("Access-Control-Allow-Origin: *");
        //resp.setHeader("Access-Control-Allow-Origin: *", "http://localhost:8080/addPoint");
    }

    void sendPointToDB(String coords, String address) throws Exception{
        String[] coordsArr = coords.split(",");
        String userName = "root";
        String password = "sergey342535";
        String connectionURL = "jdbc:mysql://localhost:3306/netcracker?serverTimezone=UTC&useSSL=false";
        Class.forName("com.mysql.jdbc.Driver");
        try(Connection connection = DriverManager.getConnection(connectionURL, userName, password);
            Statement statement = connection.createStatement()){
            System.out.println("Connected");
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "Insert into sp_objects(name, type, activateFlag, parentId) values "
                            + "(?,'TradePoint', true, 0)");
            preparedStatement.setString(1, pointId + "");
            preparedStatement.executeUpdate();
            preparedStatement = connection.prepareStatement(
                    "select id from sp_objects where name = ? and type = 'TradePoint'");
            preparedStatement.setString(1, pointId + "");
            ResultSet set = preparedStatement.executeQuery();
            int pointId = 0;
            while(set.next()){
                pointId = set.getInt("id");
            }
            preparedStatement = connection.prepareStatement(
                    "Insert into sp_objectsattributes(ObjectId, AttrId, Value)" +
                            " values(?, 3, ?)");
            preparedStatement.setInt(1, pointId);
            preparedStatement.setString(2, coordsArr[0]);
            preparedStatement.executeUpdate();
            preparedStatement = connection.prepareStatement(
                    "Insert into sp_objectsattributes(ObjectId, AttrId, Value)" +
                            " values(?, 4, ?)");
            preparedStatement.setInt(1, pointId);
            preparedStatement.setString(2, coordsArr[1]);
            preparedStatement.executeUpdate();
            preparedStatement = connection.prepareStatement(
                    "Insert into sp_objectsattributes(ObjectId, AttrId, Value)" +
                            " values(?, 6, ?)");
            preparedStatement.setInt(1, pointId);
            preparedStatement.setString(2, address);
            preparedStatement.executeUpdate();
            preparedStatement = connection.prepareStatement("Insert into sp_objectsattributes " +
                    "(ObjectId, AttrId, Value) values (?, 7, ?)");
            preparedStatement.setInt(1, pointId);
            preparedStatement.setString(2, "FFFFF0");
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement("Insert into sp_objectsattributes " +
                    "(ObjectId, AttrId, Value) values (?, 8, ?)");
            preparedStatement.setInt(1, pointId);
            preparedStatement.setString(2, "0:0");
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement("Insert into sp_objectsattributes " +
                    "(ObjectId, AttrId, Value) values (?, 9, ?)");
            preparedStatement.setInt(1, pointId);
            preparedStatement.setString(2, "23:30");
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement("Insert into sp_objectsattributes " +
                    "(ObjectId, AttrId, Value) values (?, 10, ?)");
            preparedStatement.setInt(1, pointId);
            preparedStatement.setString(2, "Description");
            preparedStatement.executeUpdate();
            //pointId++;

        }
    }
}
