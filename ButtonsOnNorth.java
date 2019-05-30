package com.mySampleApplication.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;

public class ButtonsOnNorth {
    private HorizontalPanel horizontalPanel;

    public ButtonsOnNorth(MainTableController mainTableController, PointsPanelController pointsController) {
        horizontalPanel = new HorizontalPanel();
        horizontalPanel.setSpacing(10);
        Button addWorker = new Button("Add worker");
        addWorker.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                mainTableController.addWorker(new Worker());
                mainTableController.getMaintable().setWidget(mainTableController.getMaintable().getRowCount(), 0,
                        MainTableController.getWorkerPlan().get(MainTableController.getWorkerPlan().size() - 1).getWorkerEditor());
                MainTableController.getWorkerPlan().get(MainTableController.getWorkerPlan().size() - 1).getWorkerEditor().click();
            }
        });
        Button addPoint = new Button("Add Point");
        addPoint.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                pointsController.addPointToPointList(new TradePoint());
            }
        });
        horizontalPanel.add(addWorker);
        horizontalPanel.add(addPoint);
    }

    public HorizontalPanel getHorizontalPanel() {
        return horizontalPanel;
    }
}
