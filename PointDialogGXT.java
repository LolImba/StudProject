package com.mySampleApplication.client.GXTClient;

import com.google.gwt.dom.client.StyleInjector;
import com.google.gwt.dom.client.Text;
import com.google.gwt.event.logical.shared.*;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.mySampleApplication.client.GXTClient.times.MinutePropertyEditor;
import com.mySampleApplication.client.GlobalSettings;
import com.mySampleApplication.client.HoursAndMinutes;
import com.mySampleApplication.client.TradePoint;
import com.mySampleApplication.client.Worker;
import com.sencha.gxt.cell.core.client.form.ComboBoxCell;
import com.sencha.gxt.core.client.util.DateWrapper;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.Composite;
import com.sencha.gxt.widget.core.client.Dialog;
import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.button.SplitButton;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HBoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.CollapseEvent;
import com.sencha.gxt.widget.core.client.event.ExpandEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.TriggerClickEvent;
import com.sencha.gxt.widget.core.client.form.*;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.info.Info;
import com.sencha.gxt.widget.core.client.menu.ColorMenu;
import com.sencha.gxt.widget.core.client.toolbar.LabelToolItem;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PointDialogGXT extends Dialog {
    Worker selectedWorker;
    TradePoint selectedPoint;
    ArrayList<Integer> arrayIndexs;
 //   TradePoint pointInList;

    public PointDialogGXT(TradePoint point, UnplannedPointsController pointsController, boolean addPoint, TextButton pointButton, ListStore<TradePoint> pointListStore) {
        setTitle("Edit point");
        setPredefinedButtons();
        VerticalLayoutContainer container = new VerticalLayoutContainer();
//        if(point.getPointIdInDB() == 0)
//            pointInList = point;

        int size = MainEntry.getPointsFromDB().size();
        //ListStore<TradePoint> pointListStore = GlobalSettingsWindowController.getPointListStore();
        Info.display("Success",size + "");
//        MainEntry.appServiceAsync.getRoutePointsIndexOnCurDate(
//                new java.sql.Date(GlobalSettings.getCurrentDate().getTime()), new AsyncCallback<ArrayList<Integer>>() {
//            @Override
//            public void onFailure(Throwable caught) {
//                //Info.display("Fail","Get route points");
//            }
//
//            @Override
//            public void onSuccess(ArrayList<Integer> result) {
//                //Info.display("Success","Get route points");
//                arrayIndexs = result;
//            }
//        });
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
//        pointListStore.remove();
        ComboBox<TradePoint> tradePointList = new ComboBox<>(pointListStore,
                GlobalSettingsWindowController.getProperties().nameLabel());
        tradePointList.setMinListWidth(500);
        tradePointList.setWidth(500);
        tradePointList.setAllowBlank(true);
        tradePointList.setForceSelection(true);
        tradePointList.setTriggerAction(ComboBoxCell.TriggerAction.ALL);
        //startPoint = tradePointList.getValue();

        if(pointListStore.getAll().contains(point))
            tradePointList.setValue(point);
        FieldLabel selectPointLabel = new FieldLabel(tradePointList, "Select point");

        SpinnerField<Integer> startMinField = new SpinnerField<Integer>(new MinutePropertyEditor(NumberFormat.getFormat("00")));
        startMinField.setWidth(70);
        startMinField.setMinValue(0);
        startMinField.setMaxValue(59);
        startMinField.setValue(point.getAvailableStartTime().getMinutes());

        IntegerSpinnerField startHourField = new IntegerSpinnerField();
        startHourField.setWidth(70);
        startHourField.setMinValue(0);
        startHourField.setMaxValue(23);
        startHourField.setValue(point.getAvailableStartTime().getHours());

        HBoxLayoutContainer startTimeBox = new HBoxLayoutContainer(HBoxLayoutContainer.HBoxLayoutAlign.MIDDLE);
        startTimeBox.add(startHourField);
        startTimeBox.add(new LabelToolItem(":"));
        startTimeBox.add(startMinField);
        FieldLabel availableStartTime = new FieldLabel(startTimeBox, "Available start time");

        SpinnerField<Integer> endMinField = new SpinnerField<Integer>(new MinutePropertyEditor(NumberFormat.getFormat("00")));
        endMinField.setWidth(70);
        endMinField.setMinValue(0);
        endMinField.setMaxValue(59);
        endMinField.setValue(point.getAvailavleEndTime().getMinutes());

        IntegerSpinnerField endHourField = new IntegerSpinnerField();
        endHourField.setWidth(70);
        endHourField.setMinValue(0);
        endHourField.setMaxValue(23);
        endHourField.setValue(point.getAvailavleEndTime().getHours());

        HBoxLayoutContainer endTimeBox = new HBoxLayoutContainer(HBoxLayoutContainer.HBoxLayoutAlign.MIDDLE);
        endTimeBox.add(endHourField);
        endTimeBox.add(new LabelToolItem(":"));
        endTimeBox.add(endMinField);
        FieldLabel availableEndTime = new FieldLabel(endTimeBox, "Available end time");

        //DateField dateField = new DateField();
        //dateField.setValue(point.getDate());
        //FieldLabel pointDateLabel = new FieldLabel(dateField, "Select point Date");
        //DateTimeFieldAdapter dateTimeField = new DateTimeFieldAdapter();
        TextField pointName = new TextField();
        pointName.setValue(point.getName());
        TextField pointDescription = new TextField();
        pointDescription.setValue(point.getDescription());
        FieldLabel pointNameLabel = new FieldLabel(pointName, "Point name");
        FieldLabel pointDescLabel = new FieldLabel(pointDescription, "Point description");

        DoubleField pointLatitude = new DoubleField();
        pointLatitude.setValue(point.getLatitude());
        DoubleField pointLongitude = new DoubleField();
        pointLongitude.setValue(point.getLongtide());
        FieldLabel pointLatitudeLabel = new FieldLabel(pointLatitude, "Latitude");
        FieldLabel pointLongitudeLabel = new FieldLabel(pointLongitude, "Longitude");
        pointLatitudeLabel.disable();
        pointLongitude.disable();

        SplitButton colorPicker = new SplitButton();
        final ColorMenu colorMenu = new ColorMenu();

        colorMenu.getPalette().addValueChangeHandler(new ValueChangeHandler<String>(){

            public void onValueChange(ValueChangeEvent<String> event){
                String color = event.getValue();
                point.setColor(color);
                //System.out.println("Color value is "+color);
                //StyleInjector.inject(".CustomColor1 > div > div { background-color: "+color+" !important; border-color: #c4c5c5 !important;} ");
                colorMenu.hide();
            }
        });

        tradePointList.addSelectionHandler(new SelectionHandler<TradePoint>() {
            @Override
            public void onSelection(SelectionEvent<TradePoint> event) {
            //    pointInList = selectedPoint;
                selectedPoint = event.getSelectedItem();
                ///TradePoint temp = event.getSelectedItem();
                pointName.setValue(selectedPoint.getName());
                pointDescription.setValue(selectedPoint.getDescription());
                pointLatitude.setValue(selectedPoint.getLatitude());
                pointLongitude.setValue(selectedPoint.getLongtide());
                startHourField.setValue(selectedPoint.getAvailableStartTime().getHours());
                startMinField.setValue(selectedPoint.getAvailableStartTime().getMinutes());
                endHourField.setValue(selectedPoint.getAvailavleEndTime().getHours());
                endMinField.setValue(selectedPoint.getAvailavleEndTime().getMinutes());
                colorMenu.setColor(selectedPoint.getColor());
            }
        });

//        colorPicker.setMenu(colorMenu);
//        colorPicker.setHeight(20);
//        colorPicker.setWidth(150);
        //colorMenu.setPixelSize(200, 300);
        //colorMenu.setHeight(300);
        //FieldLabel pointColor = new FieldLabel(colorMenu, "Point color");
//        StyleInjector.inject(".CustomColor1 > div {background:none !important; background-image:none !important; background-color: #FFFFFF !important; border-color: #c4c5c5 !important; border-width: 1px !important;} ");
//        colorPicker.setStyleName("CustomColor1");


        VerticalLayoutContainer.VerticalLayoutData vld = new VerticalLayoutContainer.VerticalLayoutData(-1, -1, new Margins(20));
        container.add(selectPointLabel, vld);
        container.add(pointNameLabel, vld);
        container.add(pointDescLabel, vld);
        //container.add(pointDateLabel, vld);
        container.add(pointLatitudeLabel, vld);
        container.add(pointLongitudeLabel, vld);
        container.add(availableStartTime, vld);
        container.add(availableEndTime, vld);
        container.add(colorMenu, vld);

//        FramedPanel panel = new FramedPanel();
//        panel.setHeading("Date Time Adapter Field Example");
//        panel.add(container);

        setWidget(container);
        TextButton save = new TextButton("Save");
        save.addSelectHandler(new SelectEvent.SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {
                //point =
                selectedPoint.setName(pointName.getValue());
                selectedPoint.setDescription(pointDescription.getValue());
                //selectedPoint.setDate(new java.sql.Date(dateField.getValue().getTime()));
                selectedPoint.setLatitude(pointLatitude.getValue());
                selectedPoint.setLongtide(pointLongitude.getValue());
                selectedPoint.setAvailableStartTime(new HoursAndMinutes(startHourField.getValue(), startMinField.getValue()));
                selectedPoint.setAvailavleEndTime(new HoursAndMinutes(endHourField.getValue(), endMinField.getValue()));
                if(addPoint){
                    Date date = GlobalSettings.getCurrentDate();
                    Date pointDate = selectedPoint.getDate();
                   // if(pointDate.getYear() == date.getYear()
                    //        && pointDate.getMonth() == date.getMonth() && pointDate.getDate() == date.getDate()){
                        pointsController.addPointToPanel(selectedPoint);
                    //}
                    MainEntry.appServiceAsync.sendRoutePoint(selectedPoint, new java.sql.Date(date.getTime()), new AsyncCallback<Void>() {
                        @Override
                        public void onFailure(Throwable caught) {

                        }

                        @Override
                        public void onSuccess(Void result) {

                        }
                    });
                }else{
                    Date date = GlobalSettings.getCurrentDate();
                    Date pointDate = selectedPoint.getDate();
                    //if(pointDate.getYear() == date.getYear()
                  //          && pointDate.getMonth() == date.getMonth() && pointDate.getDate() == date.getDate()){
                        int index = pointsController.getContainer().getWidgetIndex(pointButton);
                        pointsController.getContainer().remove(index);
                        pointsController.getContainer().insert(pointsController.createPointButton(selectedPoint), index);
                 //   }
                }

                PointDialogGXT.this.hide();
            }
        });
        TextButton cancel = new TextButton("Cancel");
        cancel.addSelectHandler(new SelectEvent.SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {
                PointDialogGXT.this.hide();
            }
        });


//        if(true){
//            TextButton addToTable = new TextButton("Add to table");
//            Dialog pointAddToTable = new Dialog();
//            pointAddToTable.setPredefinedButtons();
//            VerticalLayoutContainer pointContainer = new VerticalLayoutContainer();
//            ListStore<Worker> workerStore = GlobalSettings.getMainGrid().getStore();
//            ComboBox<Worker> workerList = new ComboBox<Worker>(workerStore, MainGridController.getProperties().nameLabel());
//            workerList.setAllowBlank(true);
//            workerList.setForceSelection(true);
//            workerList.setTriggerAction(ComboBoxCell.TriggerAction.ALL);
//            selectedWorker = workerList.getValue();
//            workerList.addValueChangeHandler(new ValueChangeHandler<Worker>() {
//                @Override
//                public void onValueChange(ValueChangeEvent<Worker> event) {
//                    selectedWorker = event.getValue();
//                    //workerList.select(selectedWorker);
//                }
//            });
//            FieldLabel workerLabel = new FieldLabel(workerList, "Select worker");
//            SpinnerField<Integer> minField = new SpinnerField<Integer>(new MinutePropertyEditor(NumberFormat.getFormat("00")));
//            minField.setWidth(70);
//            minField.setMinValue(0);
//            minField.setMaxValue(59);
//            minField.setValue(point.getAvailavleEndTime().getMinutes());
//
//            IntegerSpinnerField hourField = new IntegerSpinnerField();
//            hourField.setWidth(70);
//            hourField.setMinValue(0);
//            hourField.setMaxValue(23);
//            hourField.setValue(point.getAvailavleEndTime().getHours());
//
//            HBoxLayoutContainer timeBox = new HBoxLayoutContainer(HBoxLayoutContainer.HBoxLayoutAlign.MIDDLE);
//            timeBox.add(hourField);
//            timeBox.add(new LabelToolItem(":"));
//            timeBox.add(minField);
//            FieldLabel pointTime = new FieldLabel(timeBox, "Point start at");


//            TimeField time = new TimeField();
//            time.setTriggerAction(ComboBoxCell.TriggerAction.ALL);
//            time.setFormat(DateTimeFormat.getFormat("hh:mm"));
//            time.setIncrement(30);
//            time.setMinValue(new DateWrapper().clearTime().addHours(0).asDate());
//            time.setMaxValue(new DateWrapper().clearTime().addHours(23).addMinutes(30).asDate());
            //time.setIncrement(30);
//            pointContainer.add(workerList);
//            pointContainer.add(pointTime);
//            pointAddToTable.setWidget(pointContainer);
//            addToTable.addSelectHandler(new SelectEvent.SelectHandler() {
//                @Override
//                public void onSelect(SelectEvent event) {
//                    pointAddToTable.show();
//                }
//            });
//            addButton(addToTable);
//        }
        addButton(save);
        addButton(cancel);

    }
}
