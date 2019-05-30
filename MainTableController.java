package com.mySampleApplication.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;

import java.util.*;

public class MainTableController {
    private FlexTable maintable;
    private static ArrayList<MainTableRow> workerPlan = new ArrayList<>();
    private int timeRows;
    private PointsPanelController pointsPanelController;

    public class MainTableRow {
        private Worker worker;
        private Button workerEditor;

        public MainTableRow(Worker worker) {
            this.worker = worker;
            workerEditor = new Button(worker.getName());
            //workerEditor.setWidth("10px");
            workerEditor.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    WorkerDialog workerDialog = new WorkerDialog(MainTableController.this, MainTableRow.this);
                    workerDialog.setPopupPosition(Window.getClientWidth()/ 2, Window.getClientHeight()/ 2);
                    workerDialog.show();
                }
            });
        }

        public Worker getWorker() {
            return worker;
        }

        public void setWorker(Worker worker) {
            this.worker = worker;
        }

        public Button getWorkerEditor() {
            return workerEditor;
        }

    }

    public void setPointsPanelController(PointsPanelController pointsPanelController) {
        this.pointsPanelController = pointsPanelController;
    }

    //            Arrays.asList(new MainTableRow(new Worker()),
//            new MainTableRow(new Worker()), new MainTableRow(new Worker()));

    public MainTableController(){
        maintable = new FlexTable();
        maintable.setText(0, 0, "Workers");
        for (int i = 0; i < workerPlan.size(); i++) {
            maintable.setWidget(i + 1,0, workerPlan.get(i).getWorkerEditor());
//            for (int j = 0; j < workerPlan.get(i).getWorker().getWorkPlan().get(); j++) {
//
//            }
        }
        maintable.setBorderWidth(1);
        String[] times = WorkerDialog.createHoursForTextBox(
                new HoursAndMinutes(8, 0), new HoursAndMinutes(18, 0));
        timeRows = times.length;
        for (int i = 0; i < times.length; i++) {
            Label time = new Label(times[i]);
            time.setWidth("60px");
            //time.setHorizontalAlignment(new Anchor());
            maintable.setWidget(0, i + 1, time);
            //maintable.setText(0, i + 1, times[i]);
        }
    }
    public Worker[] getWorkers(){
        Worker[] workers = new Worker[workerPlan.size()];
        for (int i = 0; i < workers.length; i++) {
            workers[i] = workerPlan.get(i).getWorker();
        }
        return workers;
    }

    public void setWorkers(Worker[] workers){
        for (int i = 0; i < workers.length; i++) {
            workerPlan.get(i).setWorker(workers[i]);
            //workerPlan.get(i).getWorkerEditor().setHTML(workers[i].getName());
        }
    }

    public FlexTable getMaintable() {
        return maintable;
    }

    public void setMaintable(FlexTable maintable) {
        this.maintable = maintable;
    }

    public static List<MainTableRow> getWorkerPlan() {
        return workerPlan;
    }

    public void addWorker (Worker worker) {
        workerPlan.add(new MainTableRow(worker));
    }

    public void createWorkPlan(){
//        for (int i = 0; i < workerPlan.size(); i++) {
//            ArrayList<TradePoint> currentWorkPlan = workerPlan.get(i).getWorker().getWorkPlan().get(GlobalSettings.currentDate);
//            int pointIndex = 0;
//            for (int j = 0; j < timeRows; j++) {
//                TradePoint point = currentWorkPlan.get(pointIndex);
//                maintable.setWidget(1, j + 1, pointsPanelController.createPointButton(point));
//                if(pointIndex + 1 < currentWorkPlan.size() &&
//                        (point.getDuration().getHours() + j/2 > currentWorkPlan.get(pointIndex + 1).getDuration().getHours() ||
//                                (point.getDuration().getHours() + j/2 == currentWorkPlan.get(pointIndex + 1).getDuration().getHours() &&
//                                        point.getDuration().getMinutes() + j%2 >= currentWorkPlan.get(pointIndex + 1).getDuration().getMinutes())))
//                    pointIndex++;
//            }
//        }
    }

    public void removeWorker (MainTableRow mainTableRow) {
        workerPlan.remove(mainTableRow);
    }
}
