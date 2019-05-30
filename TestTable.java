package com.mySampleApplication.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;

import java.util.ArrayList;

public class TestTable {
    VerticalPanel mainPanel;
    ArrayList<HorizontalPanel> rows;

    public TestTable() {
        mainPanel = new VerticalPanel();
        rows = new ArrayList<>();
        HorizontalPanel timeRow = new HorizontalPanel();
        String[] times = WorkerDialog.createHoursForTextBox(
                new HoursAndMinutes(8, 0), new HoursAndMinutes(18, 0));
        //timeRows = times.length;
        for (int i = 0; i < times.length; i++) {
            Label time = new Label(times[i]);
            time.setWidth("60px");
            //time.setHorizontalAlignment(new Anchor());
            timeRow.add(time);
            //maintable.setText(0, i + 1, times[i]);
        }
        rows.add(timeRow);
        mainPanel.add(rows.get(0));
        mainPanel.setBorderWidth(1);
        Button b1 = new Button("1");
        b1.setWidth("5px");
        Button b2 = new Button("2");
        b2.setWidth("10px");
        Button b3 = new Button("3");
        b3.setWidth("20px");
        Button b4 = new Button("4");
        b4.setWidth("25px");
//        Button b5 = new Button("5");
//        b5.setWidth("1200");
        HorizontalPanel worker1Plan = new HorizontalPanel();
//        worker1Plan.add(b1);
//        worker1Plan.add(b2);
//        worker1Plan.add(b3);
//        worker1Plan.add(b4);
        //worker1Plan.add(b5);
        for (int i = 0; i < 63; i++) {
            Button b5 = new Button("1");
            b5.setWidth("20px");
            //b5.setSize("5px", "40px");
            worker1Plan.add(b5);
//            Label l1 = new Label();
//            l1.setText("t");
//            l1.setWidth("5px");
            b5.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    DialogBox dialog = new DialogBox();
                    Button close = new Button("Close");
                    close.addClickHandler(new ClickHandler() {
                        @Override
                        public void onClick(ClickEvent event) {
                            dialog.hide();
                        }
                    });
                    dialog.setWidget(close);
                    dialog.setPopupPosition(Window.getClientWidth()/ 2, Window.getClientHeight()/ 2);
                    dialog.show();
                }
            });
            //worker1Plan.add(l1);
        }
        rows.add(worker1Plan);
        mainPanel.add(rows.get(1));
    }
}
