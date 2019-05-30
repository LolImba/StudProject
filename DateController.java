package com.mySampleApplication.client;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.datepicker.client.DatePicker;

import java.util.Date;

public class DateController {
    private DatePicker datePicker;

    public DateController(PointsPanelController pointsPanelController) {
        datePicker = new DatePicker();
        datePicker.setValue(GlobalSettings.currentDate);
        datePicker.addValueChangeHandler(new ValueChangeHandler<Date>() {
            @Override
            public void onValueChange(ValueChangeEvent<Date> event) {
                GlobalSettings.currentDate = datePicker.getValue();
                pointsPanelController.sortPoints();
            }
        });
    }

    public DatePicker getDatePicker() {
        return datePicker;
    }

    public void setDatePicker(DatePicker datePicker) {
        this.datePicker = datePicker;
    }
}
