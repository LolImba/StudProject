package com.mySampleApplication.client.GXTClient;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.mySampleApplication.client.GlobalSettings;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.DateField;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.info.Info;

import java.util.Date;

public class NorthWidgetController {
    ContentPanel contentPanel;
    UnplannedPointsController pointsController;
    MainGridController gridController;
    GlobalSettingsWindowController globalSettingsWindowController;

    public NorthWidgetController() {
        contentPanel = new ContentPanel();
        DateField dateField = new DateField();
        dateField.getDatePicker().addValueChangeHandler(new ValueChangeHandler<Date>() {
            @Override
            public void onValueChange(ValueChangeEvent<Date> event) {
                onChangeDate(event.getValue());
            }
        });
//        dateField.addValueChangeHandler(new ValueChangeHandler<Date>() {
//            @Override
//            public void onValueChange(ValueChangeEvent<Date> event) {
//                onChangeDate(event.getValue());
//            }
//        });
        dateField.setValue(GlobalSettings.getCurrentDate());
        FieldLabel dateLabel = new FieldLabel(dateField, "Select date");
        HorizontalLayoutContainer container = new HorizontalLayoutContainer();
        container.add(dateLabel);
        TextButton mode = new TextButton("Manual mode");
        mode.addSelectHandler(new SelectEvent.SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {
                if(mode.getHTML() == "Manual mode"){
                    mode.setHTML("Auto mode");
                    GlobalSettings.setManualMode(true);
                }
                else{
                    mode.setHTML("Manual mode");
                    GlobalSettings.setManualMode(false);
                }
            }
        });
        container.add(mode);
        contentPanel.add(container);
    }

    private void onChangeDate(Date date){
        GlobalSettings.setCurrentDate(date);
//        pointsController.container.clear();
//        pointsController.getUnplannedPoints().clear();
        //gridController.getGrid().getStore().remove(0);
        globalSettingsWindowController.setUpInfoOnCurDate(pointsController, gridController);
        Info.display("Success","Date changed");

        //pointsController.sortPointOnCurDate();
        //pointsController.setUpUnplannedPointsOnCurDate();
       // Info.display("Successful","Get points on current date");
        //int gridSize = gridController.getGrid().getStore().size();
//        for (int i = 0; i < gridSize; i++) {
//            gridController.getGrid().getStore().get(i).getWorkPlanByCurrentDate();
//        }
    }

    public GlobalSettingsWindowController getGlobalSettingsWindowController() {
        return globalSettingsWindowController;
    }

    public void setGlobalSettingsWindowController(GlobalSettingsWindowController globalSettingsWindowController) {
        this.globalSettingsWindowController = globalSettingsWindowController;
    }

    public MainGridController getGridController() {
        return gridController;
    }

    public void setGridController(MainGridController gridController) {
        this.gridController = gridController;
    }

    public ContentPanel getContentPanel() {
        return contentPanel;
    }


    public void setContentPanel(ContentPanel contentPanel) {
        this.contentPanel = contentPanel;
    }

    public UnplannedPointsController getPointsController() {
        return pointsController;
    }

    public void setPointsController(UnplannedPointsController pointsController) {
        this.pointsController = pointsController;
    }
}
