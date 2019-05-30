package com.mySampleApplication.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.datepicker.client.DatePicker;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.Viewport;

import java.sql.Date;
import java.util.*;

/**
 * Entry point classes define <code>onModuleLoad()</code>
 */
public class MySampleApplication{
    static AppServiceAsync appServiceAsync = GWT.create(AppService.class);
    public void onModuleLoad() {
        DockLayoutPanel dockLayoutPanel = new DockLayoutPanel(Style.Unit.PX);
        BorderLayoutContainer container = new BorderLayoutContainer();

//        FlexTable table = new FlexTable();
//        table.setText(0, 0, "Workers");
//        for (int i = 0; i < workerPlan.size(); i++) {
//            table.setWidget(i + 1,0, workerPlan.get(i).getWorkerEditor());
////            for (int j = 0; j < workerPlan.get(i).getWorker().getWorkPlan().get(); j++) {
////
////            }
//        }
//        table.setBorderWidth(1);
//        String[] times = WorkerDialog.createHoursForTextBox(
//                new HoursAndMinutes(8, 0), new HoursAndMinutes(18, 30));
//        for (int i = 0; i < times.length; i++) {
//            table.setText(0, i + 1, times[i]);
//        }
        MainTableController mainTableController = new MainTableController();
        FlexTable table = mainTableController.getMaintable();
        PointsPanelController pointsPanelController = new PointsPanelController();
        //pointsPanelController.addPoint(new TradePoint());
        ButtonsOnNorth buttonsOnNorth = new ButtonsOnNorth(mainTableController, pointsPanelController);
        HorizontalPanel northPanel = buttonsOnNorth.getHorizontalPanel();
        DateController dateController = new DateController(pointsPanelController);
        DatePicker datePicker = dateController.getDatePicker();
        MainButtonsController mainButtonsController = new MainButtonsController(mainTableController, pointsPanelController);
        //HorizontalPanel mainButtonsContainer = mainButtonsController.getContainer();
        TradePoint point1 = new TradePoint();
        point1.setLongtide(50.041935);
        point1.setLatitude(11.747375);
        point1.setDate(new Date(GlobalSettings.currentDate.getTime()));
        point1.setName("1");
        TradePoint point2 = new TradePoint();
        point2.setLongtide(50.018817);
        point2.setLatitude(11.483337);
        point2.setDate(new Date(GlobalSettings.currentDate.getTime()));
        point2.setName("2");
        TradePoint point3 = new TradePoint();
        point3.setLongtide(50.032);
        point3.setLatitude(11.5);
        point3.setDate(new Date(GlobalSettings.currentDate.getTime()));
        point3.setName("3");
        TradePoint point4 = new TradePoint();
        point4.setLongtide(50.02);
        point4.setLatitude(11.6);
        point4.setDate(new Date(GlobalSettings.currentDate.getTime()));
        point4.setName("4");
        pointsPanelController.addPoint(point1, false);
        pointsPanelController.addPoint(point2, false);
        pointsPanelController.addPoint(point3, false);
        pointsPanelController.addPoint(point4, false);
        //TestTable testController = new TestTable();
        //VerticalPanel v1 = testController.mainPanel;
//        dockLayoutPanel.addEast(pointsPanelController.getVerticalPanel(), 100);
//        dockLayoutPanel.addNorth(northPanel, 50);
//        dockLayoutPanel.addNorth(mainButtonsContainer, 50);
//        dockLayoutPanel.addEast(datePicker,300);
//       //dockLayoutPanel.addSouth(v1, 300);
//        dockLayoutPanel.add(table);

        ContentPanel cp = new ContentPanel();
        cp.setHeading("North");
        cp.add(northPanel);
        //cp.add(mainButtonsContainer);
        BorderLayoutContainer.BorderLayoutData d = new BorderLayoutContainer.BorderLayoutData(.20);
        d.setMargins(new Margins(5));
        d.setCollapsible(true);
        d.setSplit(true);
        container.setNorthWidget(cp, d);

        cp = new ContentPanel();
        cp.setHeading("East");
        cp.add(pointsPanelController.getVerticalPanel());
        cp.add(datePicker);
        d = new BorderLayoutContainer.BorderLayoutData(.20);
        d.setMargins(new Margins(0, 5, 5, 5));
        d.setCollapsible(true);
        d.setSplit(true);
        d.setCollapseMini(true);
        container.setEastWidget(cp, d);

        VerticalLayoutContainer vContainer = new VerticalLayoutContainer();
        d = new BorderLayoutContainer.BorderLayoutData();
        d.setMargins(new Margins(0, 5, 5, 0));
        container.setCenterWidget(table, d);

        Viewport v = new Viewport();
        v.add(container);
        RootPanel.get().add(v);
        //RootLayoutPanel.get().add(dockLayoutPanel);
        mainTableController.setPointsPanelController(pointsPanelController);
        LoginDialog loginDialog = new LoginDialog();
        loginDialog.setPopupPosition(Window.getClientWidth()/ 2, Window.getClientHeight()/ 2);
        loginDialog.show();
    }
}

