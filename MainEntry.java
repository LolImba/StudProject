package com.mySampleApplication.client.GXTClient;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.ScriptInjector;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.LinkElement;
import com.google.gwt.event.logical.shared.BeforeSelectionEvent;
import com.google.gwt.event.logical.shared.BeforeSelectionHandler;
import com.google.gwt.resources.client.*;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.mySampleApplication.client.*;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.PlainTabPanel;
import com.sencha.gxt.widget.core.client.TabPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.Viewport;
import com.sencha.gxt.widget.core.client.info.Info;

import java.sql.Date;
import java.util.ArrayList;

public class MainEntry implements EntryPoint {
    static AppServiceAsync appServiceAsync = GWT.create(AppService.class);
    static Widget mapWidget;
    static BorderLayoutContainer container = new BorderLayoutContainer();
    GlobalSettingsWindowController settingsController;

    @Override
    public void onModuleLoad() {
        //SimpleContainer container = new SimpleContainer();
        MainGridController gridController = new MainGridController();
        ContentPanel pnl = gridController.getPanel();
        NorthWidgetController north = new NorthWidgetController();
        ContentPanel cp = new ContentPanel();
        TabPanel folderTabPanel = new TabPanel();
        settingsController = new GlobalSettingsWindowController();
        folderTabPanel.addBeforeSelectionHandler(new BeforeSelectionHandler<Widget>() {
            @Override
            public void onBeforeSelection(BeforeSelectionEvent<Widget> event) {
                if (event.getItem() == settingsController.getSettingsWindow()){
                    //Info.display("Info","SettingsSelected");
                    getPointsFromDB();
                }

            }
        });
        appServiceAsync.getWorkersAndRoutePoints(new Date(GlobalSettings.getCurrentDate().getTime()),
                new AsyncCallback<WorkersAndPointsStruct>() {
            @Override
            public void onFailure(Throwable caught) {

            }

            @Override
            public void onSuccess(WorkersAndPointsStruct result) {

            }
        });
        //cp.setHeading("North");
        //cp.add(new Label("North Content"));
        BorderLayoutContainer.BorderLayoutData d = new BorderLayoutContainer.BorderLayoutData(.05);
        d.setMargins(new Margins(5));
        d.setCollapsible(true);
        d.setSplit(true);
        container.setNorthWidget(north.getContentPanel(), d);

        //cp = new ContentPanel();
        //cp.setHeading("UnplannedPoints");
        UnplannedPointsController unplannedPointsController = new UnplannedPointsController();
        //VerticalLayoutContainer pointsContainer = unplannedPointsController.getContainer();
        ContentPanel contentPanel = unplannedPointsController.getCp();
        //contentPanel.add(pointsContainer);
        d = new BorderLayoutContainer.BorderLayoutData(.20);
        d.setMargins(new Margins(0, 5, 5, 5));
        d.setCollapsible(true);
        d.setSplit(true);
        d.setCollapseMini(true);
        container.setEastWidget(contentPanel, d);

        //JavaScriptObject obj = ScriptInjector.fromUrl("https://api-maps.yandex.ru/2.1/?lang=ru_RU").inject();
//        HTML map = new HTML();
//        map.setPixelSize(600,400);
////        d = new BorderLayoutContainer.BorderLayoutData(.20);
////        d.setMargins(new Margins(5, 5, 0, 5));
////        d.setCollapsible(true);
////        d.setSplit(true);
////        d.setCollapseMini(true);
//        buildMap();

        Widget map = RootPanel.get("map");
        ContentPanel contentPanel1 = new ContentPanel();
        d = new BorderLayoutContainer.BorderLayoutData(.30);
        d.setCollapsible(true);
        d.setSplit(true);
        d.setCollapseMini(true);
        contentPanel1.add(map);
        container.setSouthWidget(contentPanel1, d);

//        buildMap();

        d = new BorderLayoutContainer.BorderLayoutData();
        d.setMargins(new Margins(0, 5, 5, 0));
        container.setCenterWidget(pnl, d);

        Viewport v = new Viewport();
        v.setPixelSize(500,500);
        v.add(container);
        folderTabPanel.add(v, "Main");
        folderTabPanel.add(settingsController.getSettingsWindow(), "Settings");
        RootPanel.get().add(folderTabPanel);
        //RootPanel.get("map").getWidgetCount();
        //Info.display("Event", RootPanel.get("map").getElement().toString());
        gridController.setUnplannedPointsPanel(unplannedPointsController);
        north.setPointsController(unplannedPointsController);
        north.setGridController(gridController);
        //north.setPointsController(unplannedPointsController);
        TradePoint point1 = new TradePoint();
        point1.setLongtide(50.041935);
        point1.setLatitude(11.747375);
        point1.setDate(new Date(GlobalSettings.getCurrentDate().getTime()));
        point1.setName("1");
        point1.setAvailableStartTime(new HoursAndMinutes(5, 0));
        point1.setColor("red");
        TradePoint point2 = new TradePoint();
        point2.setLongtide(50.018817);
        point2.setLatitude(11.483337);
        point2.setDate(new Date(GlobalSettings.getCurrentDate().getTime()));
        point2.setName("2");
        point2.setColor("yellow");
        point2.setAvailableStartTime(new HoursAndMinutes(7, 0));
        point2.setAvailavleEndTime(new HoursAndMinutes(7, 30));
        TradePoint point3 = new TradePoint();
        point3.setLongtide(50.032);
        point3.setLatitude(11.5);
        point3.setDate(new Date(GlobalSettings.getCurrentDate().getTime()));
        point3.setName("3");
        point3.setColor("orange");
        point3.setAvailableStartTime(new HoursAndMinutes(7, 0));
        point3.setAvailavleEndTime(new HoursAndMinutes(7, 30));
        TradePoint point4 = new TradePoint();
        point4.setLongtide(50.02);
        point4.setLatitude(11.6);
        point4.setDate(new Date(GlobalSettings.getCurrentDate().getTime()));
        point4.setName("4");
        point4.setAvailableStartTime(new HoursAndMinutes(7, 0));
        point4.setAvailavleEndTime(new HoursAndMinutes(7, 30));
        point4.setColor("green");
        TradePoint point5 = new TradePoint();
        point5.setLongtide(50.03);
        point5.setLatitude(11.55);
        point5.setDate(new Date(GlobalSettings.getCurrentDate().getTime()));
        point5.setName("5");
        TradePoint point6 = new TradePoint();
        point6.setLongtide(50.04);
        point6.setLatitude(11.57);
        point6.setDate(new Date(GlobalSettings.getCurrentDate().getTime()));
        point6.setName("6");
        TradePoint point7 = new TradePoint();
        point7.setLongtide(50.07);
        point7.setLatitude(11.52);
        point7.setDate(new Date(GlobalSettings.getCurrentDate().getTime()));
        point7.setName("7");
        TradePoint point8 = new TradePoint();
        point8.setLongtide(50.1);
        point8.setLatitude(11.56);
        point8.setDate(new Date(GlobalSettings.getCurrentDate().getTime()));
        point8.setName("8");
        TradePoint point9 = new TradePoint();
        point9.setLongtide(50.04);
        point9.setLatitude(11.75);
        point9.setDate(new Date(GlobalSettings.getCurrentDate().getTime()));
        point9.setName("9");
//        unplannedPointsController.addPoint(point1, false);
//        unplannedPointsController.addPointToPanel(point1);
//        unplannedPointsController.addPoint(point2, false);
//        unplannedPointsController.addPointToPanel(point2);
//        unplannedPointsController.addPoint(point3, false);
//        unplannedPointsController.addPointToPanel(point3);
//        unplannedPointsController.addPoint(point4, false);
//        unplannedPointsController.addPointToPanel(point4);
//        unplannedPointsController.addPoint(point5, false);
//        unplannedPointsController.addPointToPanel(point5);
//        unplannedPointsController.addPoint(point6, false);
//        unplannedPointsController.addPointToPanel(point6);
//        unplannedPointsController.addPoint(point7, false);
//        unplannedPointsController.addPointToPanel(point7);
//        unplannedPointsController.addPoint(point8, false);
//        unplannedPointsController.addPointToPanel(point8);
//        unplannedPointsController.addPoint(point9, false);
//        unplannedPointsController.addPointToPanel(point9);
        settingsController.setUpInfoOnCurDate(unplannedPointsController, gridController);
        north.setGlobalSettingsWindowController(settingsController);
        LoginDialog loginDialog = new LoginDialog(container);
        loginDialog.show();
        container.disable();
    }

    public static ArrayList<TradePoint> getPointsFromDB(){
        ArrayList<TradePoint> tradePoints = new ArrayList<>();
        try {
            appServiceAsync.getPoints(new AsyncCallback<ArrayList<TradePoint>>() {
                @Override
                public void onFailure(Throwable caught) {
                    Info.display("Fail","Connect failed");
                }

                @Override
                public void onSuccess(ArrayList<TradePoint> result) {
                    //Info.display("Success",String.valueOf(result.get(0).getAddress()));
                    for (int i = 0; i < result.size(); i++) {
                        System.out.println(result.get(i).getAddress());
                    }
                    //Info.display("Success", String.valueOf(out.size()));
                    GlobalSettingsWindowController.addPointsToComboBox(result);
                    tradePoints.addAll(result);

                    //Info.display("Success", out.get(0).getAddress());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tradePoints;
    }

    native final void buildMap()/*-{

        var script = document.createElement('script');

        script.src = 'https://api-maps.yandex.ru/2.1/?lang=ru_RU&mode=debug';
        script.async = false;
        document.head.appendChild(script);
        script.onload = function () {
            var container = document.createElement('div');
            container.id = 'testMap';
            document.body.appendChild(container);
        ymaps.ready(init);
        function init() {
            var myMap = new ymaps.Map("testMap", {
                center: [55.76, 37.64],
                zoom: 7
            });

            var myPlacemark = new ymaps.Placemark([55.76, 37.64], {
                hintContent: 'Содержимое всплывающей подсказки',
                balloonContent: 'Содержимое балуна'
            });
            myMap.geoObjects.add(myPlacemark);
            //@com.mySampleApplication.client.GXTClient.MainEntry::addMap()();
        }
    }
    }-*/;
    native final void test1()/*-{
        var script = document.createElement('script');
        script.src = 'BuildMap.js';
        script.async = false;
        document.head.appendChild(script);
    }-*/;
//    static void addMap(){
//        mapWidget = RootPanel.get("testMap");
//        if(test == null){
//            Info.display("Event", "Null");
//        }
//        ContentPanel contentPanel1 = new ContentPanel();
//        BorderLayoutContainer.BorderLayoutData d = new BorderLayoutContainer.BorderLayoutData(.30);
//        d.setCollapsible(true);
//        d.setSplit(true);
//        d.setCollapseMini(true);
//        //test1();
//        contentPanel1.add(test);
//        container.setSouthWidget(contentPanel1, d);
//    }

//    public static interface MyApiJs extends ClientBundle {
//        MyApiJs INSTANCE = GWT.create(MyApiJs.class);
//
//        @Source("https://api-maps.yandex.ru/2.1/?lang=ru_RU")
//        TextResource sync();
//
//        @Source("https://api-maps.yandex.ru/2.1/?lang=ru_RU") // Should be in the same domain or configure CORS
//        ExternalTextResource async();
//    }
//
//    public void loadSync() {
//        //String js = MyApiJs.INSTANCE.sync().getText();
//        //ScriptInjector.fromString(js).inject();
//        loadFromExternalUrl();
//    }
//
//    public void loadAsync() throws ResourceException {
//        MyApiJs.INSTANCE.async().getText(new ResourceCallback<TextResource>() {
//            public void onSuccess(TextResource r) {
//                String js = r.getText();
//                ScriptInjector.fromString(js).inject();
//            }
//            public void onError(ResourceException e) {
//            }
//        });
//    }
//
//    public void loadFromExternalUrl() {
//        ScriptInjector.fromUrl("https://api-maps.yandex.ru/2.1/?lang=ru_RU").inject();
//    }
}
