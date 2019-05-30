package com.mySampleApplication.server;

import com.mySampleApplication.client.*;
import com.mySampleApplication.client.GXTClient.PointStartEndTime;
import com.mySampleApplication.client.GXTClient.PointsWithIndex;
import com.mySampleApplication.client.GXTClient.WorkerWithBool;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;

public class TSPAlgorithm {
    private TradePoint[] points;
    //private WorkerWithBool[] outWorkers;
    Worker[] outWorkers;
    private Worker[] workers;
    private TradePoint startPoint;
    private int speed;
    private boolean[] flags;
    private int currentPointIndex;
    private ArrayList<PointsWithIndex> allPoints;
    private Date currentDate;
    private ArrayList<TradePoint> removedPoints;
    private ArrayList<Integer> removedPointsIndexs;
    ArrayList<PointsWithIndex> unreachablePoints;
    private int lastRecordIndex = -1;
    private int markerIndex = -1;
    int workersIndex;
    HoursAndMinutes currentTime;
    Date curDate;
    HashMap<Worker, ArrayList<PointStartEndTime>> routes;

//    private class PointStartEndTime{
//        TradePoint point;
//        HoursAndMinutes startTime;
//        HoursAndMinutes endTime;
//
//        public PointStartEndTime(TradePoint point, HoursAndMinutes startTime, HoursAndMinutes endTime) {
//            this.point = point;
//            this.startTime = startTime;
//            this.endTime = endTime;
//        }
//    }

    //private ArrayList<TradePoint> unsortedPoints;

    public TSPAlgorithm(TradePoint[] points, WorkerWithBool[] allWorkers, TradePoint startPoint, int speed, Date date) {
   //     startPoint.setName("Start");
        //startPoint.setName(startPoint.getName());
        //this.allWorkers = allWorkers;
        outWorkers = new Worker[allWorkers.length];
        for (int i = 0; i < allWorkers.length; i++) {
            outWorkers[i] = allWorkers[i].worker;
            allWorkers[i].worker.setWorkPlan(new TradePoint[48]);
        }
        ArrayList<Worker> optimizedWorkers = new ArrayList<>();
        for (int i = 0; i < allWorkers.length; i++) {
            if(allWorkers[i].marker)
            optimizedWorkers.add(allWorkers[i].worker);
        }
        workers = optimizedWorkers.toArray(new Worker[optimizedWorkers.size()]);
        curDate = new Date(date.getTime()+ 10800000);
        this.points = points;
        workersIndex = 0;
        //this.workers = workers;
        this.startPoint = startPoint;
        this.speed = speed;
        //allPoints = new TradePoint[points.length + 1];
        allPoints = new ArrayList<>();
        allPoints.add(new PointsWithIndex(startPoint, 0));
        routes = new HashMap<>();
        for (int i = 0; i < points.length; i++) {
            allPoints.add(new PointsWithIndex(points[i], i + 1));
        }
        //curDate = allPoints.get(1).getDate();
        flags = new boolean[allPoints.size()];
        flags[0] = true;
        currentPointIndex = 0;
        //speed = speed * 1000;
//        currentDate = points[0].getDate();
        removedPoints = new ArrayList<>();
        removedPoints.add(startPoint.clonePoint());
        removedPointsIndexs = new ArrayList<>();
        unreachablePoints = new ArrayList<>();
        unreachablePoints.add(new PointsWithIndex(startPoint, 0));
        checkCurDateInDB(new java.sql.Date(curDate.getTime()));
        //unsortedPoints = new ArrayList<>();
    }

    public WorkersAndPointsStruct optimize(int[][] matrix){
        //int workersIndex = 0;
        currentTime = new HoursAndMinutes(0, 0);
        routes.put(workers[workersIndex], new ArrayList<>());
        while(true) {

            //workers[workersIndex].getPoints().get(allPoints[1].getDate())[0] = allPoints[0];
            //workers[workersIndex].getWorkPlan().get(points[0].getDate()).add(startPoint.clonePoint());
            //startPoint.setDuration(new HoursAndMinutes(0, 0));
            //TradePoint record = allPoints[1];
            TradePoint record = new TradePoint();
            //boolean[] unreachablePoints = new boolean[allPoints.size()];
            //unreachablePoints[0] = true;
            record.setAvailableStartTime(new HoursAndMinutes(23, 59));
            int arrayIndex = -1;

            int recordIndex = -1;
            for (int i = 1; i < allPoints.size(); i++) {
                if (flags[i] == false) {
                    if (allPoints.get(i).getTradePoint().getAvailableStartTime().getHours() < record.getAvailableStartTime().getHours() &&
                            unreachablePoints.contains(allPoints.get(i)) == false) {
                        record = allPoints.get(i).getTradePoint();
                        recordIndex = allPoints.get(i).getIndex();
                        arrayIndex = i;
                    } else if (allPoints.get(i).getTradePoint().getAvailableStartTime().getHours() == record.getAvailableStartTime().getHours() &&
                            allPoints.get(i).getTradePoint().getAvailableStartTime().getMinutes() <= record.getAvailableStartTime().getMinutes() &&
                            unreachablePoints.contains(allPoints.get(i)) == false) {
                        record = allPoints.get(i).getTradePoint();
                        recordIndex = allPoints.get(i).getIndex();
                        arrayIndex = i;
                    }
                }
            }
            if (recordIndex != -1) {
                for (int i = 1; i < allPoints.size(); i++) {
                    if (flags[i] == false) {
                        if (allPoints.get(i).getTradePoint().getAvailableStartTime().getHours() < record.getAvailableStartTime().getHours() &&
                                unreachablePoints.contains(allPoints.get(i)) == false &&
                                matrix[currentPointIndex][recordIndex] >= matrix[currentPointIndex][i]) {
                            record = allPoints.get(i).getTradePoint();
                            recordIndex = allPoints.get(i).getIndex();
                            arrayIndex = i;
                        } else if (allPoints.get(i).getTradePoint().getAvailableStartTime().getHours() == record.getAvailableStartTime().getHours() &&
                                allPoints.get(i).getTradePoint().getAvailableStartTime().getMinutes() <= record.getAvailableStartTime().getMinutes() &&
                                unreachablePoints.contains(allPoints.get(i)) == false &&
                                matrix[currentPointIndex][recordIndex] >= matrix[currentPointIndex][i]) {
                            record = allPoints.get(i).getTradePoint();
                            recordIndex = allPoints.get(i).getIndex();
                            arrayIndex = i;
                        }
                    }
                }
                lastRecordIndex = recordIndex;
            }
//            if(recordIndex != -1 && flags[recordIndex]){
            System.out.println(recordIndex);
            //System.out.println(allPoints[recordIndex].getAvailableStartTime().getHours());
//            }
//            if(flags[recordIndex]){
//                int arrSize = 0;
//                for (int i = 1; i < allPoints.length; i++) {
//                    if(!flags[i]) {
//                        arrSize++;
//                    }
//                }
//                TradePoint[] lastPoints = new TradePoint[arrSize];
//                int j = 0;
//                for (int i = 1; i < allPoints.length; i++) {
//                    if(!flags[i]){
//                        lastPoints[j] = allPoints[i];
//                        j++;
//                    }
//                }
//
//                for(int i = 0; i < lastPoints.length; i++){
//                    if(!flags[i] && )
//                }
//            }
            // System.out.println(recordIndex);
//            if (recordIndex == 2){
//                System.out.println("asd");
//            }
            if (recordIndex == -1) {
//                TradePoint endPoint = startPoint.clonePoint();
//                endPoint.setName("Start Point");
                double finalDuration = (double) matrix[currentPointIndex][0] / speed;
                int[] hoursAndMinutes3 = generateHoursAndMinutes(finalDuration, currentTime.getHours(), currentTime.getMinutes());
                routes.get(workers[workersIndex]).add(new PointStartEndTime(allPoints.get(0).getTradePoint(),
                        new HoursAndMinutes(currentTime.getHours(), currentTime.getMinutes()),
                        new HoursAndMinutes(hoursAndMinutes3[0], hoursAndMinutes3[1]), true));
                if (routes.get(workers[workersIndex]).size() > 1) {
                    for (int i = currentTime.getHours() * 2 + currentTime.getMinutes() / 30; i < hoursAndMinutes3[0] * 2 + hoursAndMinutes3[1] / 30; i++) {
                        //workers[workersIndex].getPoints().get(curDate)[i] = allPoints.get(0);
                        workers[workersIndex].getWorkPlan()[i] = allPoints.get(0).getTradePoint();
                    }
//                endPoint.setDuration(new HoursAndMinutes(hoursAndMinutes3[0], hoursAndMinutes3[1]));
                    //workers[workersIndex].getWorkPlan().get(currentDate).add(startPoint.clonePoint());
                    //workers[workersIndex].getWorkPlanByCurrentDate()
                    //removedPoints.add(startPoint.clonePoint());

                    for (int i = 0; i < 48; i++) {
                        if (workers[workersIndex].getWorkPlan()[i] == null) {
                            TradePoint temp = new TradePoint();
                            if (i - 1 >= 0) {
                                if (workers[workersIndex].getWorkPlan()[i - 1] != null) {
                                    temp.setColor(workers[workersIndex].getWorkPlan()[i - 1].getColor());
                                    temp.setName("Wait");
                                    workers[workersIndex].getWorkPlan()[i] = temp;
                                } else {
                                    temp.setName("Wait");
                                    workers[workersIndex].getWorkPlan()[i] = temp;
                                }
                            }
                        }
                    }
                }else{
                    routes.remove(workers[workersIndex]);
                }
                if (workersIndex + 1 >= workers.length) {
                    //workers[workersIndex].getWorkPlan().get(currentDate).add(startPoint.clonePoint());
                    break;
                }
                useNextWorker();
                continue;
        }
            double duration = (double)matrix[currentPointIndex][recordIndex]/speed;
            int [] hoursAndMinutes = generateHoursAndMinutes(duration, currentTime.getHours(), currentTime.getMinutes());
//            hoursAndMinutes[1] += 30;
//            if(hoursAndMinutes[1] >= 60){
//                hoursAndMinutes[0]++;
//                hoursAndMinutes[1] -= 60;
//            }

            if(hoursAndMinutes[0] < allPoints.get(arrayIndex).getTradePoint().getAvailavleEndTime().getHours() ||
                    (hoursAndMinutes[0] == allPoints.get(arrayIndex).getTradePoint().getAvailavleEndTime().getHours() &&
                            hoursAndMinutes[1] <= allPoints.get(arrayIndex).getTradePoint().getAvailavleEndTime().getMinutes())){

                double durationToStart = (double)matrix[recordIndex][0]/speed;
                int[] hoursAndMinutes1 =  generateHoursAndMinutes(durationToStart, hoursAndMinutes[0], hoursAndMinutes[1]);

                if(hoursAndMinutes1[0] < 23 || (hoursAndMinutes1[0] == 23 && hoursAndMinutes1[1] <= 59)){

                    if(hoursAndMinutes[0] < allPoints.get(arrayIndex).getTradePoint().getAvailableStartTime().getHours() ||
                            hoursAndMinutes[0] == allPoints.get(arrayIndex).getTradePoint().getAvailableStartTime().getHours() &&
                                    hoursAndMinutes[1] <= allPoints.get(arrayIndex).getTradePoint().getAvailableStartTime().getMinutes()){
                        int hours = allPoints.get(arrayIndex).getTradePoint().getAvailableStartTime().getHours() - hoursAndMinutes[0];
                        int minutes = allPoints.get(arrayIndex).getTradePoint().getAvailableStartTime().getMinutes() - hoursAndMinutes[1];
                        currentTime.addMinutes(hours * 60 + minutes);
                       // currentTime.addTime(new HoursAndMinutes(hours, minutes));
                        hoursAndMinutes[0] += hours;
                        hoursAndMinutes[1] += minutes;
                    }
                    flags[arrayIndex] = true;
                    hoursAndMinutes[1] += 30;
                    if(hoursAndMinutes[1] >= 60){
                        hoursAndMinutes[0]++;
                        hoursAndMinutes[1] -= 60;
                    }
                    routes.get(workers[workersIndex]).add(new PointStartEndTime(allPoints.get(arrayIndex).getTradePoint(),
                            new HoursAndMinutes(currentTime.getHours(), currentTime.getMinutes()),
                            new HoursAndMinutes(hoursAndMinutes[0], hoursAndMinutes[1]), true));
                    int startIndex = currentTime.getHours() * 2 + currentTime.getMinutes() / 30;
                    int endIndex = hoursAndMinutes[0] * 2 + hoursAndMinutes[1] / 30;
                    for (int i = startIndex; i < endIndex; i++) {
                        workers[workersIndex].getWorkPlan()[i] = allPoints.get(arrayIndex).getTradePoint();
//                        if(i > startIndex)
//                            workers[workersIndex].getPoints().get(allPoints[1].getDate())[i].setName("Driving");
//                        if(i == endIndex - 1)
//                            workers[workersIndex].getPoints().get(allPoints[1].getDate())[i].setName("Unloading");
                    }
                    currentTime.setHours(hoursAndMinutes[0]);
                    currentTime.setMinutes(hoursAndMinutes[1]);
                    //workers[workersIndex].getWorkPlan().get(currentDate).add(allPoints[recordIndex]);
                    //allPoints[recordIndex].setDuration(new HoursAndMinutes(hoursAndMinutes1[0], hoursAndMinutes1[1]));
                    //removedPoints.add(allPoints.get(recordIndex).getTradePoint());
                    //removedPointsIndexs.add(recordIndex - 1);
                    currentPointIndex = recordIndex;
                    continue;
                }else{
                    double durationToEnd = (double)matrix[currentPointIndex][0] / speed;
                    int[] hoursAndMinutes2 = generateHoursAndMinutes(durationToEnd, currentTime.getHours(), currentTime.getMinutes());
                    int startIndex = currentTime.getHours() * 2 + currentTime.getMinutes() / 30;
                    int endIndex = hoursAndMinutes2[0] * 2 + hoursAndMinutes2[1] / 30;
                    routes.get(workers[workersIndex]).add(new PointStartEndTime(allPoints.get(0).getTradePoint(),
                            new HoursAndMinutes(currentTime.getHours(), currentTime.getMinutes()),
                            new HoursAndMinutes(hoursAndMinutes2[0], hoursAndMinutes2[1]), true));
                    for (int i = startIndex; i < endIndex; i++) {
                        workers[workersIndex].getWorkPlan()[i] = allPoints.get(0).getTradePoint();
//                        if(i > startIndex)
//                            workers[workersIndex].getPoints().get(allPoints[1].getDate())[i].setName("Driving");
//                        if(i == endIndex - 1)
//                            workers[workersIndex].getPoints().get(allPoints[1].getDate())[i].setName("Unloading");
                    }

                    //workers[workersIndex].getWorkPlan().get(currentDate).add(startPoint);
                }
//                boolean breakCycle = false;
//                for (int i = 0; i < flags.length; i++) {
//                    if(flags[i] == false){
//                        breakCycle = true;
//                        break;
//                    }
//                }
//                if (breakCycle)
//                    break;
            }else{
                if(!unreachablePoints.contains(allPoints.get(recordIndex))){
                    unreachablePoints.add(allPoints.get(recordIndex));
                    continue;
                }
            }
            for (int i = 0; i < 48; i++) {
                if(workers[workersIndex].getWorkPlan()[i] == null){
                    TradePoint temp = new TradePoint();
                    if(i - 1 >= 0)
                        temp.setColor(workers[workersIndex].getWorkPlan()[i - 1].getColor());
                    workers[workersIndex].getWorkPlan()[i] = temp;
                }
            }
//            workersIndex++;
//            allPoints = unreachablePoints;
//            unreachablePoints.clear();
//            unreachablePoints.add(startPoint);
            if(workersIndex + 1 >= workers.length){
                //workers[workersIndex].getWorkPlan().get(currentDate).add(startPoint.clonePoint());
                break;
            }
            useNextWorker();

        }
        sendRoutesToDB();
        return new WorkersAndPointsStruct(outWorkers, null);
    }
    public int[] generateHoursAndMinutes(double duration, int currentHours, int currentMinutes){
        int hours = (int)duration;
        int minutes = (int)((duration - hours) * 60);
        int hoursWill = currentHours + hours;
        int minutesWill = currentMinutes + minutes;
        if(minutesWill >= 60){
            hoursWill++;
            minutesWill -= 60;
        }
        int[] out = {hoursWill, minutesWill};
        if(out[1] % 30 != 0){
            if(out[1] > 30){
                out[0]++;
                out[1] = 0;
            }else{
                out[1] = 30;
            }
        }
        return out;
    }

    private void useNextWorker(){
        workersIndex++;
        allPoints.clear();
        allPoints.addAll(unreachablePoints);
        //allPoints = unreachablePoints;
        unreachablePoints.clear();
        unreachablePoints.add(new PointsWithIndex(startPoint, 0));
        currentTime = new HoursAndMinutes(0, 0);
        flags = new boolean[allPoints.size()];
        currentPointIndex = 0;
        flags[0] = true;
        routes.put(workers[workersIndex], new ArrayList<>());
    }

    private void sendRoutesToDB(){
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
            Set<Worker> workerSet = routes.keySet();
            Worker[] workers = workerSet.toArray(new Worker[workerSet.size()]);
            //ArrayList<Integer> workersIndexsInDB = new ArrayList<>();
            for (int i = 0; i < workers.length; i++) {
                PreparedStatement preparedStatement = connection.prepareStatement("Select id from sp_objects " +
                        "where name = ? and description = ?");
                preparedStatement.setString(1, workers[i].getName());
                preparedStatement.setString(2, workers[i].getInfo());
                ResultSet resultWorkerIndexs = preparedStatement.executeQuery();
                int workerIndex = 0;
                while (resultWorkerIndexs.next()){
                    workerIndex = resultWorkerIndexs.getInt("id");
                }
                String routeString = "";
                for (int j = 0; j < routes.get(workers[i]).size(); j++) {
                    routeString += routes.get(workers[i]).get(j).getPoint().getPointIdInDB();
                    if(j < routes.get(workers[i]).size() - 1)
                        routeString += ",";
                }
                preparedStatement = connection.prepareStatement("Select id from sp_routes " +
                        "where workerId = ? and date = ?");
                preparedStatement.setInt(1, workerIndex);
                preparedStatement.setDate(2, new java.sql.Date(curDate.getTime()));
                ResultSet checkResult = preparedStatement.executeQuery();
                checkResult.last();
                int routeInDBMarker = checkResult.getRow();
                checkResult.beforeFirst();
                if(routeInDBMarker <= 0){
                preparedStatement = connection.prepareStatement("Insert into sp_routes " +
                        "(date, activateFlag, workerId, RouteByPointsID) values (?, true, ?, ?)");
                preparedStatement.setDate(1, new java.sql.Date(curDate.getTime()));
                preparedStatement.setInt(2, workerIndex);
                preparedStatement.setString(3, routeString);
                preparedStatement.executeUpdate();
                }else{
                    if(checkResult.next()){
                        int routeId = checkResult.getInt("id");
                        preparedStatement = connection.prepareStatement("Update sp_routes " +
                            "set date = ?, activateFlag = true, workerId = ?, RouteByPointsID = ? " +
                            "where id = ?");
                        preparedStatement.setDate(1, new java.sql.Date(curDate.getTime()));
                        preparedStatement.setInt(2, workerIndex);
                        preparedStatement.setString(3, routeString);
                        preparedStatement.setInt(4, routeId);
                        preparedStatement.executeUpdate();
                    }
                }
                int routeId = 0;
                ResultSet routeIdResult = statement.executeQuery("Select max(id) as id_max from sp_routes");
                if(routeIdResult.next()){
                    routeId = routeIdResult.getInt("id_max");
                }
                int iterator = 0;
                for (int j = 0; j < routes.get(workers[i]).size(); j++) {
                    if(j == routes.get(workers[i]).size() - 1){
//                        preparedStatement = connection.prepareStatement("Select routePointId from sp_routepoints " +
//                                "where routeId = ? and pointId = ? and PointDate = ?");
//                        preparedStatement.setInt(1, routeId);
//                        preparedStatement.setInt(2, routes.get(workers[i]).get(j).getPoint().getPointIdInDB());
//                        preparedStatement.setDate(3, new java.sql.Date(curDate.getTime()));
//                        ResultSet resultStartPoint = preparedStatement.executeQuery();
//                        resultStartPoint.last();
//                        int marker = resultStartPoint.getRow();
//                        resultStartPoint.beforeFirst();
//                        if(marker <= 0){
                        preparedStatement = connection.prepareStatement("Insert into sp_routepoints" +
                                "(RouteId, pointId, PointDate, StartsAt, EndsAt) values (?, ?, ?, ?, ?)");
                        //preparedStatement.setInt(1, routeId - workers.length + 1 + i);
                        iterator = routeId;
                        preparedStatement.setInt(1, routeId);
                        preparedStatement.setInt(2, routes.get(workers[i]).get(j).getPoint().getPointIdInDB());
                        preparedStatement.setDate(3, new java.sql.Date(curDate.getTime()));
                        preparedStatement.setString(4, routes.get(workers[i]).get(j).getStartTime().asString());
                        preparedStatement.setString(5, routes.get(workers[i]).get(j).getEndTime().asString());
                        preparedStatement.execute();
//                        }else{
//                            preparedStatement = connection.prepareStatement("Update sp_routepoints " +
//                                    "set StartsAt = ?, EndsAt = ? where RouteId = ");
//                            //preparedStatement.setInt(1, routeId - workers.length + 1 + i);
//                            iterator = routeId;
//                            preparedStatement.setInt(1, routeId);
//                            preparedStatement.setInt(2, routes.get(workers[i]).get(j).getPoint().getPointIdInDB());
//                            preparedStatement.setDate(3, new java.sql.Date(curDate.getTime()));
//                            preparedStatement.setString(4, routes.get(workers[i]).get(j).getStartTime().asString());
//                            preparedStatement.setString(5, routes.get(workers[i]).get(j).getEndTime().asString());
//                            preparedStatement.execute();
//                        }
                    }else{
                    preparedStatement = connection.prepareStatement("Update sp_routepoints " +
                            "set RouteId = ?, StartsAt = ?, EndsAt = ? where pointId = ?");
                    //preparedStatement.setInt(1, routeId - workers.length + 1 + i);
                    preparedStatement.setInt(1, routeId);
                    preparedStatement.setString(2, routes.get(workers[i]).get(j).getStartTime().asString());
                    preparedStatement.setString(3, routes.get(workers[i]).get(j).getEndTime().asString());
                    preparedStatement.setInt(4, routes.get(workers[i]).get(j).getPoint().getPointIdInDB());
                    preparedStatement.executeUpdate();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    void checkCurDateInDB(java.sql.Date date){
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
            PreparedStatement preparedStatement = connection.prepareStatement("Delete from sp_routepoints where " +
                    "pointId = ? and PointDate = ?");
            preparedStatement.setInt(1, startPoint.getPointIdInDB());
            preparedStatement.setDate(2, date);
            preparedStatement.execute();
            for (int i = 0; i < points.length; i++) {
                preparedStatement = connection.prepareStatement("UPDATE sp_routepoints set " +
                        "RouteId = 0, StartsAt = null, EndsAt = null where " +
                        "pointId = ? and PointDate = ?");
                preparedStatement.setInt(1, points[i].getPointIdInDB());
                preparedStatement.setDate(2, date);
                preparedStatement.execute();
            }
            preparedStatement = connection.prepareStatement("Delete from sp_routes where Date = ?");
            preparedStatement.setDate(1, date);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

