package com.mySampleApplication.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.user.datepicker.client.DatePicker;

import java.util.Date;

public class PointDialog extends DialogBox {
    TradePoint point;

    public PointDialog(TradePoint point, Button button, PointsPanelController panelController) {
        setText("Edit Point");
        setAnimationEnabled(true);
        setGlassEnabled(true);
        FlexTable table = new FlexTable();
        this.setWidget(table);
        table.setText(0, 0, "Name: ");
        table.setText(1, 0, "Description: ");
        table.setText(2,0, "Select date: ");
        table.setText(3,0,"Longtide: ");
        table.setText(4,0,"Latitude: ");
        //table.setText(5, 0, "Duration: ");
        table.setText(5,0, "Available start time: ");
        table.setText(6, 0, "Available end time");
        TextBox namePoint = new TextBox();
        namePoint.setText(point.getName());
        table.setWidget(0,1, namePoint);
        TextArea descPoint = new TextArea();
        descPoint.setText(point.getDescription());
        table.setWidget(1, 1, descPoint);
        DateTimeFormat dateFormat =
                DateTimeFormat.getFormat("MM/dd/yyyy");
        DateBox dateBox = new DateBox();
        dateBox.setFormat(new DateBox.DefaultFormat(dateFormat));
        dateBox.setValue(GlobalSettings.currentDate);
        table.setWidget(2, 1, dateBox);
        if(point.getDate()!= null)
            dateBox.setValue(new Date(point.getDate().getTime()));
        else
            dateBox.setValue(new Date(System.currentTimeMillis()));
        TextBox longtidePoint = new TextBox();
        Double temp = new Double(point.getLongtide());
        longtidePoint.setText(temp.toString());
        table.setWidget(3, 1, longtidePoint);
        TextBox latitudePoint = new TextBox();
        Double temp1 = new Double(point.getLatitude());
        latitudePoint.setText(temp1.toString());
        table.setWidget(4, 1, latitudePoint);
        //TextBox durationPoint = new TextBox();
        //Double duration = (point.getDuration().getHours() + (double)point.getDuration().getMinutes()/60);
        //durationPoint.setText(duration.toString());
        //table.setWidget(5,1, durationPoint);
        ListBox startTime = new ListBox();
        ListBox endTime = new ListBox();
        String[] items = WorkerDialog.createHoursForTextBox();
        for (int i = 0; i < items.length; i++) {
            startTime.addItem(items[i]);
            endTime.addItem(items[i]);
        }
        int indexStart = point.getAvailableStartTime().getHours() * 2 + point.getAvailableStartTime().getMinutes() / 30;
        startTime.setSelectedIndex(indexStart);
        int indexEnd = point.getAvailavleEndTime().getHours() * 2 + point.getAvailavleEndTime().getMinutes() / 30;
        endTime.setSelectedIndex(indexEnd);
        table.setWidget(5,1, startTime);
        table.setWidget(6,1, endTime);
        Button save = new Button("Save");
        save.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                point.setName(namePoint.getText());
                point.setDescription(descPoint.getText());
                point.setDate(new java.sql.Date(dateBox.getValue().getTime()));
                point.setLatitude(Double.valueOf(latitudePoint.getText()));
                point.setLongtide(Double.valueOf(longtidePoint.getText()));
                //int hours = Double.valueOf(durationPoint.getText()).intValue();
                //int minutes = (int)((Double.valueOf(durationPoint.getText()) - hours) * 60);
                //point.setDuration(new HoursAndMinutes(hours, minutes));
                point.setAvailableStartTime(new HoursAndMinutes
                        (Integer.valueOf(startTime.getSelectedValue().substring(0,2)),
                                Integer.valueOf(startTime.getSelectedValue().substring(5))));
                point.setAvailavleEndTime(new HoursAndMinutes
                        (Integer.valueOf(endTime.getSelectedValue().substring(0,2)),
                                Integer.valueOf(endTime.getSelectedValue().substring(5))));
                if (button == null){
                    Date date = GlobalSettings.currentDate;
                    Date pointDate = point.getDate();
                    if(pointDate.getYear() == date.getYear()
                            && pointDate.getMonth() == date.getMonth() && pointDate.getDate() == date.getDate())
                        panelController.addPointToPanel(point);
                    //panelController.addPointToPanel(point);
                }else
                    button.setHTML(point.getName());

                PointDialog.this.hide();
            }
        });
        Button cancel = new Button("Cancel");
        cancel.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                PointDialog.this.hide();
            }
        });

        table.setWidget(7,0, save);
        table.setWidget(7,1, cancel);
    }
}
