package com.mySampleApplication.client.GXTClient;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.mySampleApplication.client.*;
import com.mySampleApplication.client.GXTClient.times.MinutePropertyEditor;
import com.sencha.gxt.cell.core.client.form.ComboBoxCell;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HBoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.*;
import com.sencha.gxt.widget.core.client.info.Info;
import com.sencha.gxt.widget.core.client.menu.ColorMenu;
import com.sencha.gxt.widget.core.client.toolbar.LabelToolItem;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class GlobalSettingsWindowController {
    private ContentPanel settingsWindow;
    static private ArrayList<TradePoint> points;
    private TradePoint startPoint;
    private static ListStore<TradePoint> pointListStore;
    private static ListStore<TradePoint> pointListStoreCopy;
    private static PointProperties properties = GWT.create(PointProperties.class);
    private static PointProperties pointProperties = GWT.create(PointProperties.class);
    private TradePoint selectedPoint;
    private MainGridController gridController;
    private UnplannedPointsController pointsController;

    interface PointProperties extends PropertyAccess<TradePoint>{
        @Editor.Path("address")
        LabelProvider<TradePoint> nameLabel();
        ValueProvider<TradePoint, String> address();
        @Editor.Path("id")
        ModelKeyProvider<TradePoint> id();
    }
    public GlobalSettingsWindowController() {
        settingsWindow = new ContentPanel();
        VerticalLayoutContainer container = new VerticalLayoutContainer();
        points = new ArrayList<>();
        //points = MainEntry.getPointsFromDB();
        //Info.display("Success", points.get(0).getAddress());
        pointListStore = new ListStore<>(properties.id());
        pointListStoreCopy = new ListStore<>(pointProperties.id());
//        for (int i = 0; i < points.size(); i++) {
//            pointListStore.add(points.get(i));
//        }
        //pointListStore.addAll(points);
        //Info.display("Success", pointListStore.get(0).getAddress());
        ComboBox<TradePoint> startPointList = new ComboBox<>(pointListStore, properties.nameLabel());
        startPointList.setMinListWidth(500);
        startPointList.setWidth(500);
        startPointList.setAllowBlank(true);
        startPointList.setForceSelection(true);
        startPointList.setTriggerAction(ComboBoxCell.TriggerAction.ALL);
        MainEntry.appServiceAsync.getStartPoint(new AsyncCallback<TradePoint>() {
            @Override
            public void onFailure(Throwable caught) {
                Info.display("Fail","Get start point failed");
            }

            @Override
            public void onSuccess(TradePoint result) {
                Info.display("Successful","Get start point");
                startPoint = result;
                GlobalSettings.setStartPoint(startPoint);
                startPointList.setValue(result);
        }
        });
        //startPoint = tradePointList.getValue();
        //tradePointList.setValue();

        startPointList.addSelectionHandler(new SelectionHandler<TradePoint>() {
            @Override
            public void onSelection(SelectionEvent<TradePoint> event) {
                startPoint = event.getSelectedItem();
            }
        });
        FieldLabel startPointLabel = new FieldLabel(startPointList, "Select start point");
        ComboBox<TradePoint> tradePointList = new ComboBox<>(pointListStoreCopy, pointProperties.nameLabel());
        tradePointList.setMinListWidth(500);
        tradePointList.setWidth(500);
        tradePointList.setAllowBlank(true);
        tradePointList.setForceSelection(true);
        tradePointList.setTriggerAction(ComboBoxCell.TriggerAction.ALL);

        FieldLabel tradePointsLabel = new FieldLabel(tradePointList, "Select point");

        TextField pointName = new TextField();
        if(selectedPoint != null)
            pointName.setValue(selectedPoint.getName());
        TextField pointDescription = new TextField();
        if(selectedPoint != null)
            pointDescription.setValue(selectedPoint.getDescription());
        FieldLabel pointNameLabel = new FieldLabel(pointName, "Point name");
        FieldLabel pointDescLabel = new FieldLabel(pointDescription, "Point description");

        SpinnerField<Integer> startMinField = new SpinnerField<Integer>(new MinutePropertyEditor(NumberFormat.getFormat("00")));
        startMinField.setWidth(70);
        startMinField.setMinValue(0);
        startMinField.setMaxValue(59);
        if(selectedPoint != null)
            startMinField.setValue(selectedPoint.getAvailableStartTime().getMinutes());

        IntegerSpinnerField startHourField = new IntegerSpinnerField();
        startHourField.setWidth(70);
        startHourField.setMinValue(0);
        startHourField.setMaxValue(23);
        if(selectedPoint != null)
            startHourField.setValue(selectedPoint.getAvailableStartTime().getHours());

        HBoxLayoutContainer startTimeBox = new HBoxLayoutContainer(HBoxLayoutContainer.HBoxLayoutAlign.MIDDLE);
        startTimeBox.add(startHourField);
        startTimeBox.add(new LabelToolItem(":"));
        startTimeBox.add(startMinField);
        FieldLabel availableStartTime = new FieldLabel(startTimeBox, "Available start time");

        SpinnerField<Integer> endMinField = new SpinnerField<Integer>(new MinutePropertyEditor(NumberFormat.getFormat("00")));
        endMinField.setWidth(70);
        endMinField.setMinValue(0);
        endMinField.setMaxValue(59);
        if(selectedPoint != null)
            endMinField.setValue(selectedPoint.getAvailavleEndTime().getMinutes());

        IntegerSpinnerField endHourField = new IntegerSpinnerField();
        endHourField.setWidth(70);
        endHourField.setMinValue(0);
        endHourField.setMaxValue(23);
        if(selectedPoint != null)
            endHourField.setValue(selectedPoint.getAvailavleEndTime().getHours());

        HBoxLayoutContainer endTimeBox = new HBoxLayoutContainer(HBoxLayoutContainer.HBoxLayoutAlign.MIDDLE);
        endTimeBox.add(endHourField);
        endTimeBox.add(new LabelToolItem(":"));
        endTimeBox.add(endMinField);
        FieldLabel availableEndTime = new FieldLabel(endTimeBox, "Available end time");

        final ColorMenu colorMenu = new ColorMenu();

        colorMenu.getPalette().addValueChangeHandler(new ValueChangeHandler<String>(){

            public void onValueChange(ValueChangeEvent<String> event){
                String color = event.getValue();
                if(selectedPoint != null)
                    selectedPoint.setColor(color);
                Info.display("Color ", event.getValue());
                //System.out.println("Color value is "+color);
                //StyleInjector.inject(".CustomColor1 > div > div { background-color: "+color+" !important; border-color: #c4c5c5 !important;} ");
                colorMenu.hide();
            }
        });

        tradePointList.addSelectionHandler(new SelectionHandler<TradePoint>() {
            @Override
            public void onSelection(SelectionEvent<TradePoint> event) {
                selectedPoint = event.getSelectedItem();
                startMinField.setValue(selectedPoint.getAvailableStartTime().getMinutes());
                startHourField.setValue(selectedPoint.getAvailableStartTime().getHours());
                endMinField.setValue(selectedPoint.getAvailavleEndTime().getMinutes());
                endHourField.setValue(selectedPoint.getAvailavleEndTime().getHours());
                pointName.setValue(selectedPoint.getName());
                pointDescription.setValue(selectedPoint.getDescription());
                colorMenu.setColor(selectedPoint.getColor());
            }
        });
        container.add(startPointLabel);
        container.add(tradePointsLabel);
        container.add(pointNameLabel);
        container.add(pointDescLabel);
        container.add(availableStartTime);
        container.add(availableEndTime);
        container.add(colorMenu);
        container.setHeight(300);
        settingsWindow.add(container);
        TextButton save = new TextButton("Save");
        save.addSelectHandler(new SelectEvent.SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {
                selectedPoint.setDescription(pointDescription.getValue());
                selectedPoint.setColor(colorMenu.getColor());
                selectedPoint.setAvailableStartTime(new HoursAndMinutes(startHourField.getValue(), startMinField.getValue()));
                selectedPoint.setAvailavleEndTime(new HoursAndMinutes(endHourField.getValue(), endMinField.getValue()));
                selectedPoint.setName(pointName.getValue());
//                for (int i = 0; i < pointsController.getUnplannedPoints().size(); i++) {
//                    if(selectedPoint.getPointIdInDB() == pointsController.getUnplannedPoints().get(i).getPointIdInDB()){
//                        pointsController.getUnplannedPoints().set(i, selectedPoint);
//                        break;
//                    }
//                }
                MainEntry.appServiceAsync.sendPoints(startPoint, selectedPoint, new AsyncCallback<Void>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        Info.display("Fail","Update points");
                    }

                    @Override
                    public void onSuccess(Void result) {
                        Info.display("Successful","Update points");
                        GlobalSettings.setStartPoint(startPoint);
                    }
                });
            }
        });
        settingsWindow.setButtonAlign(BoxLayoutContainer.BoxLayoutPack.CENTER);
        settingsWindow.addButton(save);

    }

    public TradePoint getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(TradePoint startPoint) {
        this.startPoint = startPoint;
    }

    public MainGridController getGridController() {
        return gridController;
    }

    public void setGridController(MainGridController gridController) {
        this.gridController = gridController;
    }

    public UnplannedPointsController getPointsController() {
        return pointsController;
    }

    public void setPointsController(UnplannedPointsController pointsController) {
        this.pointsController = pointsController;
    }

    public static void addPointsToComboBox(ArrayList<TradePoint> pointsToAdd){
        pointListStore.clear();
        pointListStore.addAll(pointsToAdd);
        pointListStoreCopy.clear();
        pointListStoreCopy.addAll(pointsToAdd);
        //Info.display("Success", String.valueOf(pointsToAdd.size()));
    }

    public static ListStore<TradePoint> getPointListStore() {
        return pointListStore;
    }

    public static PointProperties getProperties() {
        return properties;
    }

    public static ListStore<TradePoint> getPointListStoreCopy() {
        return pointListStoreCopy;
    }

    public static ArrayList<TradePoint> getPoints() {
        return points;
    }

    public static void setPoints(ArrayList<TradePoint> points) {
        GlobalSettingsWindowController.points = points;
    }

    public ContentPanel getSettingsWindow() {
        return settingsWindow;
    }

    public void setUpInfoOnCurDate(UnplannedPointsController pointsController, MainGridController gridController) {
        MainEntry.appServiceAsync.getWorkersAndRoutePoints(new Date(GlobalSettings.getCurrentDate().getTime()),
                new AsyncCallback<WorkersAndPointsStruct>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        Info.display("Fail","Get info on current date");
                    }

                    @Override
                    public void onSuccess(WorkersAndPointsStruct result) {
//                        List<Worker> workersList = gridController.getGrid().getStore().getAll();
                        //
//                        for (Worker worker : workersList) {
//                            gridController.getGrid().getStore().remove(worker);
//                        }
                        gridController.getGrid().getStore().clear();
                        Info.display("Success","Get info on current date");
                        for (int i = 0; i < result.workers.length; i++) {
                            //gridController.getGrid().getStore().remove(result.workers[i].getId());
                            gridController.getGrid().getStore().add(result.workers[i]);
                        }
                        pointsController.container.clear();
                        pointsController.getUnplannedPoints().clear();
                        for (int i = 0; i < result.points.size(); i++) {
                            pointsController.addPoint(result.points.get(i), false);
                            pointsController.addPointToPanel(result.points.get(i));
                        }
                    }
                });
    }
}
