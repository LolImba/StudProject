package com.mySampleApplication.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.*;

public class WorkerDialog extends DialogBox {
    //private Worker worker;

    public WorkerDialog(MainTableController mainController, MainTableController.MainTableRow mainTableRow) {
        Worker worker = mainTableRow.getWorker();
        Button button = mainTableRow.getWorkerEditor();
        setText("Edit worker");
        setAnimationEnabled(true);
        setGlassEnabled(true);
        FlexTable table = new FlexTable();
        table.setText(0, 0, "Name: ");
        table.setText(1, 0, "Info: ");
        table.setText(2, 0, "Starting work at:");
        table.setText(3, 0, "Finishing work at:");
        table.setCellPadding(10);
        //VerticalPanel panel = new VerticalPanel();
        //panel.setSpacing(4);
        this.setWidget(table);
        //this.setWidget(panel);
        TextBox workerName = new TextBox();
        workerName.setText(worker.getName());
        //panel.add(workerName);
        table.setWidget(0, 1, workerName);
        TextArea workerInfo = new TextArea();
        workerInfo.setText(worker.getInfo());
        //panel.add(workerInfo);
        table.setWidget(1, 1, workerInfo);
        ListBox startTime = new ListBox();
        ListBox endTime = new ListBox();
        String [] timeZone = createHoursForTextBox();
        for (int i = 0; i < timeZone.length; i++) {
            startTime.addItem(timeZone[i]);
            endTime.addItem(timeZone[i]);
        }
        int index = worker.getStartWorkingTime().getHours() * 2 + worker.getStartWorkingTime().getMinutes()/30;
        startTime.setSelectedIndex(index);
        int indexEnd = worker.getEndWorkingTime().getHours() * 2 + worker.getEndWorkingTime().getMinutes()/30;
        endTime.setSelectedIndex(indexEnd);
//        panel.add(startHour);
//        panel.add(endHour);
        table.setWidget(2, 1, startTime);
        table.setWidget(3, 1, endTime);
        //HorizontalPanel horizontalPanel = new HorizontalPanel();
        Button ok = new Button("Save");
        ok.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                worker.setName(workerName.getValue());
                worker.setInfo(workerInfo.getValue());
                worker.setStartWorkingTime(new HoursAndMinutes
                        (Integer.valueOf(startTime.getSelectedValue().substring(0,2)),
                                Integer.valueOf(startTime.getSelectedValue().substring(5))));
                worker.setEndWorkingTime(new HoursAndMinutes
                        (Integer.valueOf(endTime.getSelectedValue().substring(0,2)),
                                Integer.valueOf(endTime.getSelectedValue().substring(5))));
                button.setHTML(worker.getName());
                WorkerDialog.this.hide();
            }
        });
        Button cancel = new Button("Cancel");
        cancel.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                WorkerDialog.this.hide();
            }
        });
        Button removeWorker = new Button("Remove worker");
        removeWorker.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                int index = MainTableController.getWorkerPlan().indexOf(mainTableRow);
                mainController.getMaintable().removeRow(index + 1);
                MainTableController.getWorkerPlan().remove(index);
                WorkerDialog.this.hide();
            }
        });
        table.setWidget(4, 0, ok);
        table.setWidget(4, 1, cancel);
        table.setWidget(4,2, removeWorker);
    }

    public static String [] createHoursForTextBox() {
        String [] out = new String[48];
        String minutes;
        int hours = 0;
        for (int i = 0; i < 48; i++) {
            if (i % 2 == 0){
                minutes = "00";
            }else{
                minutes = "30";
            }
            hours = i/2;
            if(hours > 9)
                out[i] = hours + " : " + minutes;
            else
                out[i] = "0" + hours + " : " + minutes;
        }
        return out;
    }
    public static String [] createHoursForTextBox(HoursAndMinutes startTime, HoursAndMinutes endTime){
        int arrSize = endTime.getHours() * 2 + endTime.getMinutes()/30 - startTime.getHours() * 2 + startTime.getMinutes()/30;
        String [] out = new String[arrSize];
        String minutes;
        int hours = startTime.getHours();
        for (int i = startTime.getHours() * 2 + startTime.getMinutes()/30;
             i < endTime.getHours() * 2 + endTime.getMinutes()/30 + 1; i++) {
            if (i % 2 == 0){
                minutes = "00";
            }else{
                minutes = "30";
            }
            hours = i/2;
            if(hours > 9)
                out[i - startTime.getHours() * 2 + startTime.getMinutes()/30] = hours + " : " + minutes;
            else
                out[i - startTime.getHours() * 2 + startTime.getMinutes()/30] = "0" + hours + " : " + minutes;
        }
        return out;
    }
}
