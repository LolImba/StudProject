package com.mySampleApplication.client.GXTClient;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.mySampleApplication.client.GlobalSettings;
import com.mySampleApplication.client.TradePoint;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.info.Info;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UnplannedPointsController {
    ArrayList<TradePoint> unplannedPoints;
    //ArrayList<TradePoint> unplannedPointsOnCurDate;
    VerticalLayoutContainer container;
    ArrayList<Integer> arrayIndexs;
    ContentPanel cp;

    public UnplannedPointsController(ArrayList<TradePoint> unplannedPoints, VerticalLayoutContainer container) {
        this.unplannedPoints = unplannedPoints;
        this.container = container;
        container.setTitle("Unplanned points");
    }

    public UnplannedPointsController() {
        //setUpUnplannedPointsOnCurDate();
        container = new VerticalLayoutContainer();
        unplannedPoints = new ArrayList<>();
        //unplannedPointsOnCurDate = new ArrayList<>();
        //setUpUnplannedPointsOnCurDate();
        arrayIndexs = new ArrayList<>();
        //container.setTitle("Unplanned points");
        cp = new ContentPanel();
        cp.setHeading("Unplanned points");
        cp.add(container);
        cp.addButton(new TextButton("AddPoint", new SelectEvent.SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {
                addPoint(new TradePoint(), true);
            }
        }));

    }

    public ArrayList<TradePoint> getUnplannedPoints() {
        return unplannedPoints;
    }

    public void setUnplannedPoints(ArrayList<TradePoint> unplannedPoints) {
        this.unplannedPoints = unplannedPoints;
    }

    public VerticalLayoutContainer getContainer() {
        return container;
    }

    public void setContainer(VerticalLayoutContainer container) {
        this.container = container;
    }

    public ContentPanel getCp() {
        return cp;
    }

    public void setCp(ContentPanel cp) {
        this.cp = cp;
    }

    public void addPoint(TradePoint point, boolean createDialog){
        //unplannedPoints.add(point);
        if(createDialog){
            PointDialogGXT dialog = new PointDialogGXT(point, UnplannedPointsController.this, true, null, createListOfPossibleToAddPoints());
            dialog.autoSize();
            dialog.show();
        }
    }
    public void addPointToPanel(TradePoint point){
        unplannedPoints.add(point);
        container.add(createPointButton(point));
    }

    public TextButton createPointButton(TradePoint point){
        TextButton pointButton = new TextButton(point.getName());
        pointButton.addSelectHandler(new SelectEvent.SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {
                PointDialogGXT dialog = new PointDialogGXT(point, UnplannedPointsController.this, false, pointButton, createListOfPossibleToAddPoints());
                dialog.autoSize();
                dialog.show();
            }
        });
        return pointButton;
    }

    public void sortPointOnCurDate(){
//        container.clear();
//        for (TradePoint point : unplannedPoints) {
//            Date date = GlobalSettings.getCurrentDate();
//            Date pointDate = point.getDate();
//            if(pointDate.getYear() == date.getYear()
//                    && pointDate.getMonth() == date.getMonth() && pointDate.getDate() == date.getDate()){
//                container.add(createPointButton(point));
//                unplannedPointsOnCurDate.add(point);
//            }
//        }
    }

    //public ArrayList<TradePoint> getUnplannedPointsOnCurDate() {
  //      return unplannedPointsOnCurDate;
   // }

    public ListStore<TradePoint> createListOfPossibleToAddPoints(){
        ListStore<TradePoint> pointListStore = GlobalSettingsWindowController.getPointListStore();
//        int size = MainEntry.getPointsFromDB().size();
//        Info.display("Success",size + "");
//        MainEntry.appServiceAsync.getRoutePointsIndexOnCurDate(
//                new java.sql.Date(GlobalSettings.getCurrentDate().getTime()), new AsyncCallback<ArrayList<Integer>>() {
//                    @Override
//                    public void onFailure(Throwable caught) {
//                        Info.display("Fail","Get route points");
//                    }
//
//                    @Override
//                    public void onSuccess(ArrayList<Integer> result) {
//                        Info.display("Success","Get route points");
//                        arrayIndexs = result;
//                    }
//                });
//        List<TradePoint> pointsList = pointListStore.getAll();
//        //Info.display("Success",pointListStore.size() + "");
//        ArrayList<TradePoint> outPoints = new ArrayList<>();
//        for (int i = 0; i < arrayIndexs.size(); i++) {
//            int j = 0;
//            while (j < pointsList.size()) {
//                if(pointsList.get(j).getPointIdInDB() == arrayIndexs.get(i)){
//                    outPoints.add(pointsList.get(j));
//                    break;
//                }
//                j++;
//            }
//        }
//        pointListStore.clear();
//        pointListStore.addAll(outPoints);
        return pointListStore;
    }
    public void setUpUnplannedPointsOnCurDate() {
        container.clear();
        MainEntry.appServiceAsync.getRoutePointOnCurDate(new java.sql.Date(GlobalSettings.getCurrentDate().getTime()),
                new AsyncCallback<ArrayList<TradePoint>>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        Info.display("Fail","Get points on current date");
                    }

                    @Override
                    public void onSuccess(ArrayList<TradePoint> result) {
                        Info.display("Successful","Get points on current date");
//                        for (int i = 0; i < GlobalSettingsWindowController.getPointListStore().size(); i++) {
//                            for (int j = 0; j < result.size(); j++) {
//                                if(result.get(j).getPointIdInDB() ==
//                                        GlobalSettingsWindowController.getPointListStore().get(i).getPointIdInDB()){
//                                    unplannedPoints.add(GlobalSettingsWindowController.getPointListStore().get(i));
//                                    break;
//                                }
//                            }
//                        }
                        unplannedPoints = result;
                        for (int i = 0; i < unplannedPoints.size(); i++) {
                            addPoint(unplannedPoints.get(i), false);
                            addPointToPanel(unplannedPoints.get(i));
                        }
                    }
                });
    }
}
