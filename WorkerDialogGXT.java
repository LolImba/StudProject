package com.mySampleApplication.client.GXTClient;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.mySampleApplication.client.Worker;
import com.sencha.gxt.cell.core.client.TextButtonCell;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.core.client.util.Rectangle;
import com.sencha.gxt.data.shared.Store;
import com.sencha.gxt.widget.core.client.Dialog;
import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.CssFloatLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.HtmlEditor;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.info.Info;

public class WorkerDialogGXT extends Dialog {

    public WorkerDialogGXT(Grid<Worker> grid, int rowIndex) {
        Worker worker;
        if (rowIndex >= 0)
            worker = grid.getStore().get(rowIndex);
        else
            worker = new Worker();
        setHeading("Worker dialog");
        //autoSize();
        setPixelSize(600,300);
        //setAnimCollapse(true);
        setResizable(false);
        setBodyBorder(false);
        setHideOnButtonClick(true);
        setPredefinedButtons();
        setShadow(true);
        CssFloatLayoutContainer columns = new CssFloatLayoutContainer();
        TextField name = new TextField();
        //name.setValue(worker.getName());
        name.setValue(worker.getName());
        HtmlEditor htmlEditor = new HtmlEditor();
        htmlEditor.setValue(worker.getInfo());
        columns.add(new FieldLabel(name, "Name"), new CssFloatLayoutContainer.CssFloatData(0.5,
                new Margins(0, 7, 0, 0)));
        VerticalLayoutContainer container = new VerticalLayoutContainer();
        container.add(columns, new VerticalLayoutContainer.VerticalLayoutData(1, -1, new Margins(15, 15, 0, 15)));
        container.add(new FieldLabel(htmlEditor, "Info"),
                new VerticalLayoutContainer.VerticalLayoutData(1, 1, new Margins(0, 15, 0, 15)));
        TextButton save = new TextButton("Save");
        save.addSelectHandler(new SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {
                worker.setName(name.getValue());
                worker.setInfo(htmlEditor.getValue());

                if(rowIndex >= 0){
                    grid.getStore().remove(rowIndex);
                    worker.setSecretIndex();
                    grid.getStore().add(rowIndex, worker);
//                    WorkerDialogGXT newDialog = new WorkerDialogGXT(worker, grid, rowIndex, false);
//                    newDialog.show();
                }else{
                    worker.setSecretIndex();
                    grid.getStore().add(worker);
                    MainEntry.appServiceAsync.sendWorker(worker, new AsyncCallback<Void>() {
                        @Override
                        public void onFailure(Throwable caught) {
                            Info.display("Fail","Send worker");
                        }

                        @Override
                        public void onSuccess(Void result) {
                            Info.display("Successful","Send worker");
                        }
                    });
                }
                hide();
            }
        });
        TextButton cancel = new TextButton("Cancel");
        cancel.addSelectHandler(new SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {
                hide();
            }
        });
        //VerticalLayoutContainer container = new VerticalLayoutContainer();
        add(container);
        //container.add(columns, new VerticalLayoutContainer.VerticalLayoutData(1, -1, new Margins(15, 15, 0, 15)));
        //FramedPanel panel = new FramedPanel();
        //setWidget(panel);
        addButton(save);
        addButton(cancel);
    }
}
