package com.mySampleApplication.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

import java.util.ArrayList;
import java.util.Date;

public class PointsPanelController {
    private ArrayList<TradePoint> points;
    private ArrayList<TradePoint> unplannedPointsOnCurDate;
    private VerticalPanel verticalPanel;

    public PointsPanelController(ArrayList<TradePoint> points) {
        createPanel();
        this.points = points;
        unplannedPointsOnCurDate = new ArrayList<>();
    }

    public PointsPanelController() {
        createPanel();
        points = new ArrayList<>();
        unplannedPointsOnCurDate = new ArrayList<>();
    }

    private void createPanel(){
        verticalPanel = new VerticalPanel();
        verticalPanel.add(new Label("Unplanned points"));
    }

    public void addPoint(TradePoint point, boolean createWindow){
        points.add(point);
        if (createWindow)
            addPointToPanel(point).click();
        else
            addPointToPanel(point);
    }

    public ArrayList<TradePoint> getPoints() {
        return points;
    }

    public VerticalPanel getVerticalPanel() {
        return verticalPanel;
    }

    public ArrayList<TradePoint> getUnplannedPointsOnCurDate() {
        return unplannedPointsOnCurDate;
    }

    public void sortPoints(){
        verticalPanel.clear();
        verticalPanel.add(new Label("Unplanned points"));
        Date date = GlobalSettings.currentDate;
        for (int i = 0; i < points.size(); i++) {
            Date pointDate = points.get(i).getDate();
            if(pointDate.getYear() == date.getYear()
                    && pointDate.getMonth() == date.getMonth() && pointDate.getDate() == date.getDate()){
                addPointToPanel(points.get(i));
            }
        }
    }

    public Button addPointToPanel(TradePoint point){
        Button button = createPointButton(point);
        unplannedPointsOnCurDate.add(point);
        verticalPanel.add(button);
        return button;
    }

    public Button createPointButton(TradePoint point){
        Button button = new Button(point.getName());
        button.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                PointDialog pointDialog = new PointDialog(point, button, PointsPanelController.this);
                pointDialog.setPopupPosition(Window.getClientWidth()/ 2, Window.getClientHeight()/ 2);
                pointDialog.show();
            }
        });
        button.setHTML(point.getName());
        return button;
    }
    public void addPointToPointList(TradePoint point){
        PointDialog pointDialog = new PointDialog(point, null, this);
        pointDialog.setPopupPosition(Window.getClientWidth()/ 2, Window.getClientHeight()/ 2);
        pointDialog.show();
        points.add(point);
    }
}
