package com.mySampleApplication.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.mySampleApplication.NetCrackerProject.TSPParser;
import com.mySampleApplication.client.*;
import com.mySampleApplication.client.GXTClient.PointStartEndTime;
import com.mySampleApplication.client.GXTClient.WorkerWithBool;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class ServerMain extends RemoteServiceServlet implements AppService{
    @Override
    public WorkersAndPointsStruct getInfo(Worker[] workers, TradePoint[] tradePoints, TradePoint startPoint, int speed) throws IOException {
        String arrPoints = "";
        String next;
        //System.out.println("Hey");
        for (int i = 0; i < tradePoints.length; i++){
            next = "point=" + tradePoints[i].getLongtide() + "," + tradePoints[i].getLatitude() + "&";
            arrPoints += next;
//            points += "point=" + next + "&";
        }
        Date selectedDate = tradePoints[0].getDate();
        String url = "https://graphhopper.com/api/1/matrix?" + "point=" + startPoint.getLongtide() + "," + startPoint.getLatitude() + "&"
                + arrPoints + "type=json&vehicle=car&debug=true&out_array=distances&key=0435d6d9-9653-4901-838c-899fa77d66d8";
        int[][] matrix = TSPParser.parse(url);
        //TSPAlgorithm algorithm = new TSPAlgorithm(tradePoints, workers, startPoint, speed, new java.sql.Date(tradePoints[0].getDate().getTime()));
        //WorkersAndPointsStruct struct = new WorkersAndPointsStruct();
        //return algorithm.optimize(matrix);
        return null;
    }

    @Override
    public WorkersAndPointsStruct optimize(WorkerWithBool[] workers, TradePoint[] tradePoints, TradePoint startPoint, int speed, java.sql.Date curDate) throws IOException {
        String arrPoints = "";
        String next;
        //System.out.println("Hey");
        for (int i = 0; i < tradePoints.length; i++){
            next = "point=" + tradePoints[i].getLongtide() + "," + tradePoints[i].getLatitude() + "&";
            arrPoints += next;
//            points += "point=" + next + "&";
        }
        //Date selectedDate = tradePoints[0].getDate();
        String url = "https://graphhopper.com/api/1/matrix?" + "point=" + startPoint.getLongtide() + "," + startPoint.getLatitude() + "&"
                + arrPoints + "type=json&vehicle=car&debug=true&out_array=distances&key=0435d6d9-9653-4901-838c-899fa77d66d8";
        int[][] matrix = TSPParser.parse(url);

        TSPAlgorithm algorithm = new TSPAlgorithm(tradePoints, workers, startPoint, speed, new Date(curDate.getTime()));
        //WorkersAndPointsStruct struct = new WorkersAndPointsStruct();
        return algorithm.optimize(matrix);
    }

    @Override
    public void sendWorker(Worker worker) {
        String userName = "root";
        String password = "sergey342535";
        String connectionURL = "jdbc:mysql://localhost:3306/netcracker?serverTimezone=UTC&useSSL=false";
        //Class.forName("com.mysql.jdbc.Driver");
        try(Connection connection = DriverManager.getConnection(connectionURL, userName, password);
            Statement statement = connection.createStatement()){
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "insert into sp_objects (name, description, type, activateFlag) values (?, ?, 'Driver', true)");
            preparedStatement.setString(1, worker.getName());
            preparedStatement.setString(2, worker.getInfo());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<Worker> getWorkers() {
        String userName = "root";
        String password = "sergey342535";
        String connectionURL = "jdbc:mysql://localhost:3306/netcracker?serverTimezone=UTC&useSSL=false";
        //Class.forName("com.mysql.jdbc.Driver");
        try(Connection connection = DriverManager.getConnection(connectionURL, userName, password);
            Statement statement = connection.createStatement()){
            ResultSet workerSet = statement.executeQuery("Select name, description from sp_objects where type = 'Driver'");
            ArrayList<Worker> workersArr = new ArrayList<>();
            while (workerSet.next()){
                Worker worker = new Worker();
                worker.setName(workerSet.getString("name"));
                worker.setInfo(workerSet.getString("description"));
                workersArr.add(worker);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String testMethod() {
        return "Hey";
    }

    @Override
    public ArrayList<TradePoint> getPoints() throws Exception{
        String userName = "root";
        String password = "sergey342535";
        String connectionURL = "jdbc:mysql://localhost:3306/netcracker?serverTimezone=UTC&useSSL=false";
        //Class.forName("com.mysql.jdbc.Driver");
        try(Connection connection = DriverManager.getConnection(connectionURL, userName, password);
            Statement statement = connection.createStatement()){
            System.out.println("Connected to db");
//            ResultSet pointSet = statement.executeQuery("Select Name, id from sp_objects where type = 'TradePoint' " +
//                    "and parentId = 0");
////            pointSet.last();
////            int size = pointSet.getRow();
////            pointSet.beforeFirst();
//            ArrayList<TradePoint> pointsArr = new ArrayList<>();
//            while(pointSet.next()){
//                TradePoint temp = new TradePoint();
//                temp.setPointIdInDB(pointSet.getInt("id"));
//                temp.setName(pointSet.getString("Name"));
//                pointsArr.add(temp);
//            }
//            //ArrayList<TradePoint> pointsArr = new ArrayList<>();
//            //String[] coordsLat = new String[size];
//            ResultSet resultLatitude = statement.executeQuery("Select Value from sp_objectsattributes " +
//                    "where AttrId = 3");
//            int i = 0;
//            while(resultLatitude.next()){
////                TradePoint temp = new TradePoint();
////                temp.setLatitude(Double.valueOf(resultLatitude.getString("Value")));
////                pointsArr.add(temp);
//                pointsArr.get(i).setLatitude(Double.valueOf(resultLatitude.getString("Value")));
//                i++;
//            }
//            ResultSet resultLongitude = statement.executeQuery("Select Value from sp_objectsattributes " +
//                    "where AttrId = 4");
//            i = 0;
//            while (resultLongitude.next()){
//                //coords[i] = coordsLat[i] + "," + resultLongitude.getString("Value");
//                pointsArr.get(i).setLongtide(Double.valueOf(resultLongitude.getString("Value")));
//                i++;
//            }
//            ResultSet resultAddr = statement.executeQuery("Select Value from sp_objectsattributes " +
//                    "where AttrId = 6");
//            i = 0;
//            while (resultAddr.next()){
//                pointsArr.get(i).setAddress(resultAddr.getString("Value"));
//                i++;
//            }
//            ResultSet resultColor = statement.executeQuery("Select ObjectId,Value from sp_objectsattributes" +
//                    " where AttrId = 7");
//            i = 0;
//            while (resultColor.next()){
//                pointsArr.get(i).setColor(resultColor.getString("Value"));
//                i++;
//            }
//            ResultSet resultAvailableStart = statement.executeQuery("Select ObjectId,Value from sp_objectsattributes" +
//                    " where AttrId = 8");
//            i = 0;
//            while (resultAvailableStart.next()){
//                String temp = resultAvailableStart.getString("Value");
//                String[] hoursAndMins = temp.split(":");
//                pointsArr.get(i).setAvailableStartTime(new HoursAndMinutes(
//                        Integer.valueOf(hoursAndMins[0]), Integer.valueOf(hoursAndMins[1])));
//                i++;
//            }
//            ResultSet resultAvailableEnd = statement.executeQuery("Select ObjectId,Value from sp_objectsattributes" +
//                    " where AttrId = 9");
//            i = 0;
//            while (resultAvailableEnd.next()){
//                String temp = resultAvailableEnd.getString("Value");
//                String[] hoursAndMins = temp.split(":");
//                pointsArr.get(i).setAvailavleEndTime(new HoursAndMinutes(
//                        Integer.valueOf(hoursAndMins[0]), Integer.valueOf(hoursAndMins[1])));
//                i++;
//            }
//            ResultSet resultDescription = statement.executeQuery("Select ObjectId,Value from sp_objectsattributes" +
//                    " where AttrId = 10");
//            i = 0;
//            while (resultDescription.next()){
//                pointsArr.get(i).setDescription(resultDescription.getString("Value"));
//                i++;
//            }
            return createArrPoints(statement);

        }
    }

    @Override
    public ArrayList<TradePoint> getRoutePointOnCurDate(java.sql.Date curDate) {
        String userName = "root";
        String password = "sergey342535";
        String connectionURL = "jdbc:mysql://localhost:3306/netcracker?serverTimezone=UTC&useSSL=false";
        //java.sql.Date date = java.sql.Date.valueOf(curDate.toString());
        java.sql.Date date = new java.sql.Date(curDate.getTime() + 10800000);
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try(Connection connection = DriverManager.getConnection(connectionURL, userName, password);
            Statement statement = connection.createStatement()){
            ArrayList<TradePoint> allPointsArr = createArrPoints(statement);
            PreparedStatement preparedStatement = connection.prepareStatement
                    ("Select pointId from sp_routepoints where PointDate = ?");
            preparedStatement.setDate(1, date);
            ResultSet pointsOnCurDateSet = preparedStatement.executeQuery();
            ArrayList<Integer> pointsAlreadyInCurDate = new ArrayList<>();
            while (pointsOnCurDateSet.next()){
                pointsAlreadyInCurDate.add(pointsOnCurDateSet.getInt("pointId"));
            }
            ArrayList<TradePoint> pointsOnCurDate = new ArrayList<>();
            for (int i = 0; i < allPointsArr.size(); i++) {
                for (int j = 0; j < pointsAlreadyInCurDate.size(); j++) {
                    if(allPointsArr.get(i).getPointIdInDB() == pointsAlreadyInCurDate.get(j)){
//                        allPointsArr.remove(i);
                        pointsOnCurDate.add(allPointsArr.get(i));
                        break;
                    }
                }
            }
            return pointsOnCurDate;

        } catch (SQLException e) {
            e.printStackTrace();
        }
       return null;
    }

    @Override
    public void addStartPoint(TradePoint startPoint) {
//        String userName = "root";
//        String password = "sergey342535";
//        String connectionURL = "jdbc:mysql://localhost:3306/netcracker?serverTimezone=UTC&useSSL=false";
//        try {
//            Class.forName("com.mysql.jdbc.Driver");
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//        try(Connection connection = DriverManager.getConnection(connectionURL, userName, password);
//            Statement statement = connection.createStatement()){
//            statement.executeUpdate("Update sp_objects set description = null where type = 'TradePoint'" +
//                    " and parentId = 0");
//            PreparedStatement preparedStatement = connection.prepareStatement(
//                    "Update sp_objects set description = 'StartPoint' where id = ?");
//            preparedStatement.setInt(1, startPoint.getPointIdInDB());
//            preparedStatement.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public TradePoint getStartPoint() {
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
            ResultSet resultPoint = statement.executeQuery("Select id, name from sp_objects where description = 'StartPoint'");
            resultPoint.last();
            int size = resultPoint.getRow();
            resultPoint.beforeFirst();
            if(size == 1){
                if(resultPoint.next()){
                TradePoint startPoint = new TradePoint();
                startPoint.setPointIdInDB(resultPoint.getInt("id"));
                startPoint.setName(resultPoint.getString("name"));
                PreparedStatement pointLatStatement = connection.prepareStatement(
                        "Select Value from sp_objectsattributes where AttrId = 3 and ObjectId = ?");
                pointLatStatement.setInt(1, startPoint.getPointIdInDB());
                ResultSet pointLatResult = pointLatStatement.executeQuery();
                while(pointLatResult.next()){
                    startPoint.setLatitude(Double.valueOf(pointLatResult.getString("Value")));
                }
                PreparedStatement pointLonStatement = connection.prepareStatement(
                        "Select Value from sp_objectsattributes where AttrId = 4 and ObjectId = ?");
                pointLonStatement.setInt(1, startPoint.getPointIdInDB());
                ResultSet pointLonResult = pointLonStatement.executeQuery();
                while (pointLonResult.next()){
                    startPoint.setLongtide(Double.valueOf(pointLonResult.getString("Value")));
                }
                PreparedStatement pointAddrStatement = connection.prepareStatement(
                        "Select Value from sp_objectsattributes where AttrId = 6 and ObjectId = ?");
                pointAddrStatement.setInt(1, startPoint.getPointIdInDB());
                ResultSet pointAddrResult = pointAddrStatement.executeQuery();
                while (pointAddrResult.next()){
                    startPoint.setAddress(pointAddrResult.getString("Value"));
                }
                return startPoint;
                }

            } else
                return null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void sendPoints(TradePoint startPoint, TradePoint selectedPoint){
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
            statement.executeUpdate("Update sp_objects set description = null where type = 'TradePoint'" +
                    " and parentId = 0");
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "Update sp_objects set description = 'StartPoint' where id = ?");
            preparedStatement.setInt(1, startPoint.getPointIdInDB());
            preparedStatement.executeUpdate();
            updatePoint(selectedPoint, connection);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updatePoint(TradePoint selectedPoint, Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("Update sp_objectsattributes " +
                "set Value = ? where ObjectId = ? and AttrId = 7");
        preparedStatement.setString(1, selectedPoint.getColor());
        preparedStatement.setInt(2, selectedPoint.getPointIdInDB());
        preparedStatement.executeUpdate();

        preparedStatement = connection.prepareStatement("Update sp_objectsattributes " +
                "set Value = ? where ObjectId = ? and AttrId = 8");
        preparedStatement.setInt(2, selectedPoint.getPointIdInDB());
        preparedStatement.setString(1, selectedPoint.getAvailableStartTime().asString());
        preparedStatement.executeUpdate();

        preparedStatement = connection.prepareStatement("Update sp_objectsattributes " +
                "set Value = ? where ObjectId = ? and AttrId = 9");
        preparedStatement.setInt(2, selectedPoint.getPointIdInDB());
        preparedStatement.setString(1, selectedPoint.getAvailavleEndTime().asString());
        preparedStatement.executeUpdate();

        preparedStatement = connection.prepareStatement("Update sp_objectsattributes " +
                "set Value = ? where ObjectId = ? and AttrId = 10");
        preparedStatement.setInt(2, selectedPoint.getPointIdInDB());
        preparedStatement.setString(1, selectedPoint.getDescription());
        preparedStatement.executeUpdate();

        preparedStatement = connection.prepareStatement("Update sp_objects set Name = ? where id = ?");
        preparedStatement.setString(1, selectedPoint.getName());
        preparedStatement.setInt(2, selectedPoint.getPointIdInDB());
        preparedStatement.executeUpdate();
    }


    @Override
    public WorkersAndPointsStruct getWorkersAndRoutePoints(java.sql.Date curDate) {
        String userName = "root";
        String password = "sergey342535";
        String connectionURL = "jdbc:mysql://localhost:3306/netcracker?serverTimezone=UTC&useSSL=false";
        //java.sql.Date date = java.sql.Date.valueOf(curDate.toString());
        java.sql.Date date = new java.sql.Date(curDate.getTime() + 10800000);
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try(Connection connection = DriverManager.getConnection(connectionURL, userName, password);
            Statement statement = connection.createStatement()){
            ResultSet resultStartPoint = statement.executeQuery("Select id from sp_objects where description = 'StartPoint'");
            int startPointID = 0;
            if(resultStartPoint.next())
                startPointID = resultStartPoint.getInt("id");
            ArrayList<TradePoint> allPointsArr = createArrPoints(statement);
            PreparedStatement preparedStatement = connection.prepareStatement
                    ("Select pointId from sp_routepoints where PointDate = ?");
            preparedStatement.setDate(1, date);
            ResultSet pointsOnCurDateSet = preparedStatement.executeQuery();
            ArrayList<Integer> pointsAlreadyInCurDate = new ArrayList<>();
            while (pointsOnCurDateSet.next()){
                pointsAlreadyInCurDate.add(pointsOnCurDateSet.getInt("pointId"));
            }
            ArrayList<TradePoint> pointsOnCurDate = new ArrayList<>();
            for (int i = 0; i < allPointsArr.size(); i++) {
                for (int j = 0; j < pointsAlreadyInCurDate.size(); j++) {
                    if(allPointsArr.get(i).getPointIdInDB() == pointsAlreadyInCurDate.get(j)){
//                        allPointsArr.remove(i);
                        pointsOnCurDate.add(allPointsArr.get(i));
                        break;
                    }
                }
            }
            for (int i = 0; i < pointsOnCurDate.size(); i++) {
                if(pointsOnCurDate.get(i).getPointIdInDB() == startPointID){
                    pointsOnCurDate.remove(i);
                    break;
                }
            }
            ResultSet resultWorkers = statement.executeQuery(
                    "Select id, name, description from sp_objects where type = 'Driver'");

            ArrayList<Worker> workersArr = new ArrayList<>();
            ArrayList<Integer> workersIndexsInDB = new ArrayList<>();
            while (resultWorkers.next()){
                Worker temp = new Worker();
                temp.setName(resultWorkers.getString("name"));
                temp.setInfo(resultWorkers.getString("description"));
                workersIndexsInDB.add(resultWorkers.getInt("id"));
                workersArr.add(temp);
            }
            preparedStatement = connection.prepareStatement("Select pointId, StartsAt, EndsAt from sp_routepoints " +
                    "where PointDate = ?");
            preparedStatement.setDate(1, date);
            ResultSet resultRoutePoints = preparedStatement.executeQuery();
            ArrayList<PointStartEndTime> pointStartEndTimes = new ArrayList<>();
            resultRoutePoints.last();
            int size = resultRoutePoints.getRow();
            resultRoutePoints.beforeFirst();
            while (resultRoutePoints.next()){
                PointStartEndTime temp = new PointStartEndTime();
                String startTime = resultRoutePoints.getString("StartsAt");
                if(startTime != null) {
                    String[] startHoursAndMins = startTime.split(":");
                    temp.setStartTime(new HoursAndMinutes(Integer.valueOf(startHoursAndMins[0]), Integer.valueOf(startHoursAndMins[1])));
                    String endTime = resultRoutePoints.getString("EndsAt");
                    String[] endHoursAndMins = endTime.split(":");
                    temp.setEndTime(new HoursAndMinutes(Integer.valueOf(endHoursAndMins[0]), Integer.valueOf(endHoursAndMins[1])));
                }else {
                    temp.setTimeMarker(true);
                }
                int pointID = resultRoutePoints.getInt("pointId");
                for (int i = 0; i < allPointsArr.size(); i++) {
                    if(allPointsArr.get(i).getPointIdInDB() == pointID){
                        temp.setPoint(allPointsArr.get(i));
                        break;
                    }
                }
                pointStartEndTimes.add(temp);
            }
            preparedStatement = connection.prepareStatement("Select RouteByPointsID, workerId, id from sp_routes " +
                    "where Date = ?");
            preparedStatement.setDate(1, date);
            ResultSet resultRoutes = preparedStatement.executeQuery();
            HashMap<Worker, ArrayList<PointStartEndTime>> workerPlan = new HashMap<>();
            while(resultRoutes.next()) {
                for (int i = 0; i < workersIndexsInDB.size(); i++) {
                    if (resultRoutes.getInt("workerId") == workersIndexsInDB.get(i)) {
                        workerPlan.put(workersArr.get(i), new ArrayList<>());
                        String routeString = resultRoutes.getString("RouteByPointsID");
                        String[] routePointsIndexs = routeString.split(",");
                        for (int l = 0; l < routePointsIndexs.length; l++) {
                            for (int j = 0; j < pointStartEndTimes.size(); j++) {
                                if (Integer.valueOf(routePointsIndexs[l]) == pointStartEndTimes.get(j).getPoint().getPointIdInDB()) {
                                    workerPlan.get(workersArr.get(i)).add(pointStartEndTimes.get(j));
                                    break;
                                }
                            }
                        }
                        break;
                    }
                }
            }
            int pointIndex = 0;
            for (int j = 0; j < workersArr.size(); j++) {
                HoursAndMinutes currentTime = new HoursAndMinutes(0, 0);
                int i = 0;
                if(workerPlan.get(workersArr.get(j)) == null)
                    continue;
                while (true) {
                    if(pointIndex < workerPlan.get(workersArr.get(j)).size() && i < 48){
                        if(workerPlan.get(workersArr.get(j)).get(pointIndex).getStartTime().compareTo(currentTime) > 0){
                            if(pointIndex == 0){
                                TradePoint temp = new TradePoint();
                                temp.setName("Wait");
                                workersArr.get(j).getWorkPlan()[i] = temp;
                            } else {
                                TradePoint temp = new TradePoint();
                                temp.setColor(workerPlan.get(workersArr.get(j)).get(pointIndex - 1).getPoint().getColor());
                                //temp.setName(workerPlan.get(workersArr.get(j)).get(pointIndex - 1).getPoint().getName() + "");
                                temp.setName("Wait");
                                workersArr.get(j).getWorkPlan()[i] = temp;
                            }
                            currentTime.addTime(new HoursAndMinutes(0, 30));
                            i++;
                            continue;
                        }
                        else if(workerPlan.get(workersArr.get(j)).get(pointIndex).getStartTime().compareTo(currentTime) <= 0 &&
                                workerPlan.get(workersArr.get(j)).get(pointIndex).getEndTime().compareTo(currentTime) > 0){
                            workersArr.get(j).getWorkPlan()[i] = workerPlan.get(workersArr.get(j)).get(pointIndex).getPoint();
                            currentTime.addTime(new HoursAndMinutes(0, 30));
                            i++;
                            continue;
                        }
                        else{
                            pointIndex++;
                        }
                    }else if(i >= 48){
                        pointIndex = 0;
                        break;
                    }else  if(pointIndex < workerPlan.get(workersArr.get(j)).size()){
                        TradePoint temp = new TradePoint();
                        temp.setName("Wait");
                        workersArr.get(j).getWorkPlan()[i] = temp;
                        currentTime.addTime(new HoursAndMinutes(0, 30));
                        i++;
                        continue;
                    } else{
                        pointIndex = 0;
                        break;
                    }
                }
//                            for (int k = 0; k < 48; k++) {
//
//                            }
//                            int startIndex = workerPlan.get(workersArr.get(j)).get(0).getStartTime().getHours() * 2 +
//                                    workerPlan.get(workersArr.get(j)).get(0).getStartTime().getMinutes() / 30;
//                            int endIndex = workerPlan.get(workersArr.get(j)).get(0).getEndTime().getHours() * 2 +
//                                    workerPlan.get(workersArr.get(j)).get(0).getEndTime().getMinutes() / 30;
            }
//                        HoursAndMinutes currentTime = new HoursAndMinutes(0, 0);
//                        for (int j = 0; j < 48; j++) {
//                            if(k < routePointsIndexs.length){
//                                TradePoint temp = new TradePoint();
//                            }
//                            if(workersArr.get(i).getWorkPlan()[j] == null){
//                                TradePoint temp = new TradePoint();
//                                if(j - 1 >= 0){
//                                    if(workersArr.get(i).getWorkPlan()[j - 1] != null){
//                                        temp.setColor(workersArr.get(i).getWorkPlan()[j - 1].getColor());
//                                        temp.setName("Wait");
//                                        workersArr.get(i).getWorkPlan()[j] = temp;
//                                    } else{
//                                        temp.setName("Wait");
//                                        workersArr.get(i).getWorkPlan()[j] = temp;
//                                    }
//                                }
//                            }
//                            k++;
//                            currentTime.addMinutes(30);
//                        }
//                    }
//                    break;
//                }
            WorkersAndPointsStruct tempVal = new WorkersAndPointsStruct(workersArr.toArray(new Worker[workersArr.size()]), pointsOnCurDate);
            return tempVal;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
//    @Override
//    public ArrayList<TradePoint> getRoutePointsIndexOnCurDate(java.sql.Date curDate) {
//        String userName = "root";
//        String password = "sergey342535";
//        String connectionURL = "jdbc:mysql://localhost:3306/netcracker?serverTimezone=UTC&useSSL=false";
//        try {
//            Class.forName("com.mysql.jdbc.Driver");
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//        try(Connection connection = DriverManager.getConnection(connectionURL, userName, password);
//            Statement statement = connection.createStatement()){
//            PreparedStatement preparedStatement = connection.prepareStatement
//                    ("Select pointId from sp_routepoints where PointDate = ?");
//            preparedStatement.setDate(1, curDate);
//            ResultSet pointsOnCurDateSet = preparedStatement.executeQuery();
//            ArrayList<Integer> pointsIndex = new ArrayList<>();
//            while (pointsOnCurDateSet.next()){
//                pointsIndex.add(pointsOnCurDateSet.getInt("pointId"));
//            }
//            ArrayList<Integer> pointsIndexOut = new ArrayList<>();
//            ResultSet pointsCanBeAddedSet = statement.executeQuery(
//                    "Select id from sp_objects where type = 'TradePoint' and description != 'StartPoint'");
//            while(pointsCanBeAddedSet.next()){
//                int tempIndex = pointsCanBeAddedSet.getInt("id");
//                boolean flag = false;
//                for (int i = 0; i < pointsIndex.size(); i++) {
//                    if(tempIndex == pointsIndex.get(i)){
//                        pointsIndex.remove(i);
//                        flag = true;
//                    }
//                }
//                if(!flag){
//                    pointsIndexOut.add(tempIndex);
//                }
//            }
//            return null;
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    @Override
    public void sendRoutePoint(TradePoint sendedPoint, java.sql.Date curDate) {
        String userName = "root";
        String password = "sergey342535";
        String connectionURL = "jdbc:mysql://localhost:3306/netcracker?serverTimezone=UTC&useSSL=false";
        java.sql.Date date = new java.sql.Date(curDate.getTime() + 10800000);
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try(Connection connection = DriverManager.getConnection(connectionURL, userName, password);
            Statement statement = connection.createStatement()){
            PreparedStatement preparedStatement = connection.prepareStatement("Insert into sp_routepoints" +
                    "(RouteId, pointId, PointDate) VALUES (0, ?, ?)");
            preparedStatement.setInt(1, sendedPoint.getPointIdInDB());
            preparedStatement.setDate(2, date);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateRoutePoint(TradePoint sendedPoint, java.sql.Date curDate) {
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
            PreparedStatement preparedStatement = connection.prepareStatement("Update sp_routepoints " +
                    "set RouteId = ? where pointId = ? and PointDate = ?");
            preparedStatement.setInt(1, 0);
            preparedStatement.setInt(2, sendedPoint.getPointIdInDB());
            preparedStatement.setDate(3, curDate);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<TradePoint> createArrPoints(Statement statement) throws SQLException {
        ResultSet pointSet = statement.executeQuery("Select Name, id from sp_objects where type = 'TradePoint' " +
                "and parentId = 0");
//            pointSet.last();
//            int size = pointSet.getRow();
//            pointSet.beforeFirst();
        ArrayList<TradePoint> pointsArr = new ArrayList<>();
        while(pointSet.next()){
            TradePoint temp = new TradePoint();
            temp.setPointIdInDB(pointSet.getInt("id"));
            temp.setName(pointSet.getString("Name"));
            pointsArr.add(temp);
        }
        //ArrayList<TradePoint> pointsArr = new ArrayList<>();
        //String[] coordsLat = new String[size];
        ResultSet resultLatitude = statement.executeQuery("Select Value from sp_objectsattributes " +
                "where AttrId = 3");
        int i = 0;
        while(resultLatitude.next()){
//                TradePoint temp = new TradePoint();
//                temp.setLatitude(Double.valueOf(resultLatitude.getString("Value")));
//                pointsArr.add(temp);
            pointsArr.get(i).setLatitude(Double.valueOf(resultLatitude.getString("Value")));
            i++;
        }
        ResultSet resultLongitude = statement.executeQuery("Select Value from sp_objectsattributes " +
                "where AttrId = 4");
        i = 0;
        while (resultLongitude.next()){
            //coords[i] = coordsLat[i] + "," + resultLongitude.getString("Value");
            pointsArr.get(i).setLongtide(Double.valueOf(resultLongitude.getString("Value")));
            i++;
        }
        ResultSet resultAddr = statement.executeQuery("Select Value from sp_objectsattributes " +
                "where AttrId = 6");
        i = 0;
        while (resultAddr.next()){
            pointsArr.get(i).setAddress(resultAddr.getString("Value"));
            i++;
        }
        ResultSet resultColor = statement.executeQuery("Select ObjectId,Value from sp_objectsattributes" +
                " where AttrId = 7");
        i = 0;
        while (resultColor.next()){
            pointsArr.get(i).setColor(resultColor.getString("Value"));
            i++;
        }
        ResultSet resultAvailableStart = statement.executeQuery("Select ObjectId,Value from sp_objectsattributes" +
                " where AttrId = 8");
        i = 0;
        while (resultAvailableStart.next()){
            String temp = resultAvailableStart.getString("Value");
            String[] hoursAndMins = temp.split(":");
            pointsArr.get(i).setAvailableStartTime(new HoursAndMinutes(
                    Integer.valueOf(hoursAndMins[0]), Integer.valueOf(hoursAndMins[1])));
            i++;
        }
        ResultSet resultAvailableEnd = statement.executeQuery("Select ObjectId,Value from sp_objectsattributes" +
                " where AttrId = 9");
        i = 0;
        while (resultAvailableEnd.next()){
            String temp = resultAvailableEnd.getString("Value");
            String[] hoursAndMins = temp.split(":");
            pointsArr.get(i).setAvailavleEndTime(new HoursAndMinutes(
                    Integer.valueOf(hoursAndMins[0]), Integer.valueOf(hoursAndMins[1])));
            i++;
        }
        ResultSet resultDescription = statement.executeQuery("Select ObjectId,Value from sp_objectsattributes" +
                " where AttrId = 10");
        i = 0;
        while (resultDescription.next()){
            pointsArr.get(i).setDescription(resultDescription.getString("Value"));
            i++;
        }
        return pointsArr;
    }

    @Override
    public boolean checkUser(String login, String password) {
        String userName = "root";
        String pass = "sergey342535";
        //int loginCode = login.hashCode();
        //int passwordCode = password.hashCode();
        String connectionURL = "jdbc:mysql://localhost:3306/netcracker?serverTimezone=UTC&useSSL=false";
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try(Connection connection = DriverManager.getConnection(connectionURL, userName, pass);
            Statement statement = connection.createStatement()){
//            ResultSet usersResult = statement.executeQuery("Select id from sp_objects where type = 'SuperVisor'");
//            ArrayList<Integer> userIds = new ArrayList<>();
            //int i = 0;
            PreparedStatement preparedStatement = connection.prepareStatement("Select ObjectId from sp_objectsattributes " +
                    "where AttrId = 1 and Value = ?");
            preparedStatement.setString(1, login.hashCode() + "");
            ResultSet resultLogin = preparedStatement.executeQuery();
            int userId = 0;
            if(resultLogin.next())
                userId = resultLogin.getInt("ObjectId");
            else
                return false;
//            resultLogin.last();
//            if(resultLogin.getRow() == 0) {
//                return false;
//            }else{
//                if(resultLogin.next())
//                userId = resultLogin.getInt("ObjectId");
//            }
            preparedStatement = connection.prepareStatement("select ObjectId from sp_objectsattributes " +
                    "where AttrId = 2 and Value = ?");
            preparedStatement.setString(1, password.hashCode() + "");
            ResultSet resultPass = preparedStatement.executeQuery();
            if(resultPass.next()){
                if(resultPass.getInt("ObjectId") == userId)
                    return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void sendPointFromUnplannedPoints(TradePoint selectedPoint) {
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
            updatePoint(selectedPoint, connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

