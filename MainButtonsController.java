package com.mySampleApplication.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;

public class MainButtonsController {
    private HorizontalPanel container;

    public MainButtonsController(MainTableController mainTableController, PointsPanelController pointsPanelController) {
        container = new HorizontalPanel();
        Button optimize = new Button("Optimize");
        TradePoint start = new TradePoint();
        start.setLongtide(49.932707);
        start.setLatitude(11.588051);
        start.setDate(new Date(GlobalSettings.currentDate.getTime()));
        optimize.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
//                ArrayList<TradePoint> arrayList = new ArrayList<>();
//                arrayList.add(start);
//                for (int i = 0; i < MainTableController.getWorkerPlan().size(); i++) {
//                    mainTableController.getWorkerPlan().get(i).getWorker().getWorkPlan().put
//                            (new Date(GlobalSettings.currentDate.getTime()), arrayList);
//                }
//                try {
//                    MySampleApplication.appServiceAsync.getInfo(mainTableController.getWorkers(),
//                            pointsPanelController.getUnplannedPointsOnCurDate().toArray
//                                    (new TradePoint[pointsPanelController.getUnplannedPointsOnCurDate().size()]),
//                            start, 30000, new AsyncCallback<WorkersAndPointsStruct>() {
//                                @Override
//                                public void onFailure(Throwable caught) {
//                                    pointsPanelController.getPoints();
//                                    pointsPanelController.getPoints();
//                                }
//
//                                @Override
//                                public void onSuccess(WorkersAndPointsStruct result) {
//                                    mainTableController.setWorkers(result.workers);
//                                    for (int i = 0; i < result.removedPoints.size(); i++) {
//                                        pointsPanelController.getPoints().remove(result.removedPoints.get(i).intValue());
//                                    }
//                                    mainTableController.createWorkPlan();
//                                }
//                            });
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//        Button save = new Button("Save");
//        save.addClickHandler(new ClickHandler() {
//            @Override
//            public void onClick(ClickEvent event) {
//
//            }
//        });
//        container.add(optimize);
//        container.add(save);
//        container.setSpacing(10);
//    }
//
//    public HorizontalPanel getContainer() {
//        return container;
//        return null;
    }
});
    }
}