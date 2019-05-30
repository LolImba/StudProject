package com.mySampleApplication.client.GXTClient;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.editor.client.Editor.Path;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.safecss.shared.SafeStyles;
import com.google.gwt.safecss.shared.SafeStylesUtils;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.mySampleApplication.client.*;
import com.sencha.gxt.cell.core.client.ResizeCell;
import com.sencha.gxt.cell.core.client.TextButtonCell;
import com.sencha.gxt.core.client.IdentityValueProvider;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.core.client.resources.CommonStyles;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import com.sencha.gxt.dnd.core.client.DragSource;
import com.sencha.gxt.dnd.core.client.DropTarget;
import com.sencha.gxt.dnd.core.client.GridDropTarget;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer;
import com.sencha.gxt.widget.core.client.event.CellClickEvent;
import com.sencha.gxt.widget.core.client.event.ColumnWidthChangeEvent;
import com.sencha.gxt.widget.core.client.event.RowClickEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.grid.*;
import com.sencha.gxt.widget.core.client.info.Info;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainGridController {

    interface PlaceProperties extends PropertyAccess<Worker> {

        @Path("name")
        LabelProvider<Worker> nameLabel();
        @Path("name")
        ModelKeyProvider<Worker> key();

        ValueProvider<Worker, String> name();

        //ValueProvider<Worker, TradePoint>[] tradePoints();

        //ValueProvider<Worker, String[]> namesTradePoints();

        //ValueProvider<Worker, HoursAndMinutes> startWorkingTime();

        ValueProvider<Worker, String> info();

        ValueProvider<Worker, String> secretPointName();
    }

    private static final PlaceProperties properties = GWT.create(PlaceProperties.class);

    public static PlaceProperties getProperties() {
        return properties;
    }

    private ListStore<Worker> store;
    private ContentPanel panel;
    private UnplannedPointsController unplannedPointsPanel;
    private Grid<Worker> grid;

    public ContentPanel getPanel() {
        return panel;
    }

    public class CellColumnResizer<M, T> implements ColumnWidthChangeEvent.ColumnWidthChangeHandler {

        private Grid<M> grid;
        private ColumnConfig<M, T> column;
        private ResizeCell<T> cell;

        public CellColumnResizer(Grid<M> grid, ColumnConfig<M, T> column, ResizeCell<T> cell) {
            this.grid = grid;
            this.column = column;
            this.cell = cell;
        }



        @Override
        public void onColumnWidthChange(ColumnWidthChangeEvent event) {
            if (column == event.getColumnConfig()) {
                int w = event.getColumnConfig().getWidth();
                int rows = store.size();

                int col = grid.getColumnModel().indexOf(column);

                cell.setWidth(w - 20);

                ListStore<M> store = grid.getStore();

                for (int i = 0; i < rows; i++) {
                    M p = grid.getStore().get(i);

                    // option 1
                    // could be better for force fit where all columns are resized
                    // would need to run deferred using DelayedTask to ensure only run once
                    // grid.getStore().update(p);

                    // option 2
                    Element parent = grid.getView().getCell(i, col);
                    if (parent != null) {
                        parent = parent.getFirstChildElement();
                        SafeHtmlBuilder sb = new SafeHtmlBuilder();
                        cell.render(new Cell.Context(i, col, store.getKeyProvider().getKey(p)), column.getValueProvider().getValue(p),
                                sb);
                        parent.setInnerSafeHtml(sb.toSafeHtml());
                    }
                }
            }
        }
    }

    public UnplannedPointsController getUnplannedPointsPanel() {
        return unplannedPointsPanel;
    }

    public void setUnplannedPointsPanel(UnplannedPointsController unplannedPointsPanel) {
        this.unplannedPointsPanel = unplannedPointsPanel;
    }

    public MainGridController(){
        panel = new ContentPanel();
        List<ColumnConfig<Worker, ?>> columns = new ArrayList<>();
        SafeStyles btnPaddingStyle = SafeStylesUtils.fromTrustedString("padding: 1px 3px 0;");
        SafeStyles fieldPaddingStyle = SafeStylesUtils.fromTrustedString("padding: 2px 3px;");
        SafeStyles style = SafeStylesUtils.fromTrustedString("background-color:yellow;");
        //SafeStyles div = SafeStylesUtils.fromTrustedString("<div style='width: 100%; height: 100%'></div>;");

//        RowExpander<Worker> rowExpander = new RowExpander<>(new AbstractCell<Worker>() {
//            @Override
//            public void render(Context context, Worker value, SafeHtmlBuilder sb) {
//                TradePoint[] points = value.getPoints().get((new java.sql.Date(GlobalSettings.getCurrentDate().getTime())));
//                for (int i = 0; i < points.length; i++) {
//                    if (points[i] != null)
//                    sb.appendHtmlConstant("<p style='margin: 5px 5px 10px'><b>Point Name:</b> " + points[i].getName() + "</p>");
//                }
//            }
//        });
        IdentityValueProvider<Worker> identity = new IdentityValueProvider<Worker>();
        CheckBoxSelectionModel<Worker> selectionModel = new CheckBoxSelectionModel<Worker>(identity);
        columns.add(selectionModel.getColumn());

        ColumnConfig<Worker, String> nameColumn = new ColumnConfig<>(properties.name(), 200, "Name");
        nameColumn.setSortable(false);
        nameColumn.setColumnTextClassName(CommonStyles.get().inlineBlock());
        nameColumn.setColumnTextStyle(btnPaddingStyle);
        //nameColumn.setColumnStyle(style);

//        TextButton button = new TextButton();
//        //button.getCell();
//        button.getCell().addSelectHandler(new SelectEvent.SelectHandler() {
//            @Override
//            public void onSelect(SelectEvent event) {
//                Cell.Context c = event.getContext();
//                int row = c.getIndex();
//                Worker worker = store.get(row);
//                WorkerDialogGXT dialog = new WorkerDialogGXT(worker);
//                dialog.show();
//            }
//        });

        TextButtonCell button = new TextButtonCell() {
            // Override this to control the row selection
            @Override
            public boolean handlesSelection() {
                return true;
            }
        };
        //new DragSource()
        //columns.add(rowExpander);
        nameColumn.setCell(button);
        columns.add(nameColumn);
        nameColumn.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
        String[] times = GlobalSettings.createHoursForTextBox();

        for (int i = 0; i < 48; i++) {
            ColumnConfig<Worker, String> workPlanColumn =
                    new ColumnConfig<>(properties.secretPointName(), 80, times[i]);
            workPlanColumn.setColumnTextClassName(CommonStyles.get().inlineBlock());
            //workPlanColumn.setColumnTextStyle(btnPaddingStyle);
            //workPlanColumn.setColumnStyle(style);
            workPlanColumn.setSortable(false);
            //workPlanColumn.setColumnStyle(SafeStylesUtils.fromTrustedString("background-color: blue;"));
            //ColumnData columnData = new ColumnData();

            TextButtonCell btn = new TextButtonCell() {
                // Override this to control the row selection
                @Override
                public boolean handlesSelection() {
                    return true;
                }
            };
            DragSource dragSource = new DragSource(new TextButton());
            //new DragSource()
            btn.addSelectHandler(new SelectEvent.SelectHandler() {
                @Override
                public void onSelect(SelectEvent event) {
                    Cell.Context c = event.getContext();
                    int row = c.getIndex();
                    Worker worker = store.get(row);
                    Info.display("Event", "The " + worker.getName() + " was clicked.");
                }
            });
            TextCell text = new TextCell();
            //workPlanColumn.setCell(text);
            AbstractCell<String> textCell = new AbstractCell<String>() {
                @Override
                public void render(Context context, String value, SafeHtmlBuilder sb) {
                    //sb.appendHtmlConstant("<span style='background-color:yellow'>" + value + "</span>");
                    sb.appendHtmlConstant(value);
                }
            };
            workPlanColumn.setCell(textCell);
            workPlanColumn.setCellPadding(false);
            //workPlanColumn.setCell(btn);
            columns.add(workPlanColumn);
            workPlanColumn.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
        }
        ColumnModel<Worker> cm = new ColumnModel<>(columns);
//            cm.getColumn(0).getCell()
        List<Worker> workers = new ArrayList<>();
        Worker worker = new Worker();
        worker.setName("First");
        Worker worker1 = new Worker();
        worker1.setName("Second");
        //worker.setNamesTradePoints(new String[48]);
       // worker.setInfo("1");
//        workers.add(worker);
//        workers.add(worker1);

        store = new ListStore<>(properties.key());
        store.addAll(workers);
        GridView<Worker> view = new GridView<>();
        GridViewConfig<Worker> viewConfig = new GridViewConfig<Worker>() {
            @Override
            public String getColStyle(Worker model, ValueProvider<? super Worker, ?> valueProvider, int rowIndex, int colIndex) {
                //model.
                return "background-color: blue;";
            }

            @Override
            public String getRowStyle(Worker model, int rowIndex) {
                return "background-color: blue;";
            }
        };
        view.setViewConfig(viewConfig);

        grid = new Grid<>(store, cm, view);
        grid.getView().setAutoExpandColumn(nameColumn);
        grid.getView().setTrackMouseOver(false);
        grid.getView().setSortingEnabled(false);
        //grid.getView().setAutoExpandColumn(nameCol);
        grid.setBorders(false);
        grid.getView().setStripeRows(true);
        grid.getView().setColumnLines(true);
        GlobalSettings.setMainGrid(grid);
        grid.setSelectionModel(selectionModel);
        //grid.getView().getCell(0,0).getStyle().setBackgroundColor("red");
        //Info.display("Event",grid.getView().cell);
        //grid.getView().getCell(0,0).getStyle().getBackgroundColor();
        //grid.getView().getScroller().getStyle().setBackgroundColor("style='background-color: red'");
        //rowExpander.initPlugin(grid);
//        grid.getColumnModel()
//                .addColumnWidthChangeHandler(new CellGridExample.CellColumnResizer<Plant, Double>(grid, progressColumn, progress));

        //panel = new ContentPanel();
        panel.setHeading("Cell Grid");
        panel.add(grid);
        panel.setButtonAlign(BoxLayoutContainer.BoxLayoutPack.CENTER);
//        panel.addButton(new TextButton("Reset", new SelectEvent.SelectHandler() {
//            @Override
//            public void onSelect(SelectEvent event) {
//                store.rejectChanges();
//            }
//        }));
//        panel.addButton(new TextButton("Save", new SelectEvent.SelectHandler() {
//            @Override
//            public void onSelect(SelectEvent event) {
//                store.commitChanges();
//            }
//        }));
        panel.addButton(new TextButton("Add worker", new SelectEvent.SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {
                WorkerDialogGXT dialog = new WorkerDialogGXT(grid, -1);
                dialog.show();
            }
        }));
        TextButton removeWorkerBtn = new TextButton("Remove selected worker");
        removeWorkerBtn.setEnabled(false);
        removeWorkerBtn.addSelectHandler(new SelectEvent.SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {
                for (Worker selectedWorker : grid.getSelectionModel().getSelectedItems()) {
                    grid.getStore().remove(selectedWorker);
                }
                removeWorkerBtn.setEnabled(false);
            }
        });
        grid.getSelectionModel().addSelectionHandler(new SelectionHandler<Worker>() {
            @Override
            public void onSelection(SelectionEvent<Worker> event) {
                removeWorkerBtn.setEnabled(true);
            }
        });
        panel.addButton(removeWorkerBtn);

        TextButton optimize = new TextButton("Optimize");
        optimize.addSelectHandler(new SelectEvent.SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {
                TradePoint start = GlobalSettings.getStartPoint();
//                start.setLongtide(49.932707);
//                start.setLatitude(11.588051);
//                start.setDate(new java.sql.Date(GlobalSettings.getCurrentDate().getTime()));
                List<Worker> workerList = grid.getSelectionModel().getSelectedItems();
                //int arrSize = grid.getStore().size();
                WorkerWithBool[] workers = new WorkerWithBool[grid.getStore().size()];
                for (int i = 0; i < grid.getStore().size(); i++) {
                    workers[i] = new WorkerWithBool(grid.getStore().get(i), false);
                }
                for (int i = 0; i < grid.getStore().size(); i++) {
                    for (int j = 0; j < workerList.size(); j++) {
                        if(grid.getStore().get(i) == workerList.get(j)){
                            workers[i].marker = true;
                            break;
                        }
                    }
                }
                int arrSize = workerList.size();
                //Worker[] workers = new Worker[arrSize];
                for (int i = 0; i < arrSize; i++) {
                    //workers[i] = grid.getStore().get(i);
                  //  workers[i] = workerList.get(i);
                    //workers[i].getPoints().put(new java.sql.Date(GlobalSettings.getCurrentDate().getTime()), new TradePoint[48]);
                }
//                if(worker.getPoints().get(new java.sql.Date(GlobalSettings.getCurrentDate().getTime())) == null)
////                    Info.display("Event", "Null");
                try {
                    MainEntry.appServiceAsync.optimize(workers,
                            unplannedPointsPanel.getUnplannedPoints().toArray
                                    (new TradePoint[unplannedPointsPanel.getUnplannedPoints().size()]),
                            start, 30000, new java.sql.Date(GlobalSettings.getCurrentDate().getTime()),  new AsyncCallback<WorkersAndPointsStruct>() {
                                @Override
                                public void onFailure(Throwable caught) {
                                    Info.display("Event", "Fail");
                                }

                                @Override
                                public void onSuccess(WorkersAndPointsStruct result) {
                                    Worker[] outWorkers = result.workers;
                                    grid.getStore().clear();
                                    for (int i = 0; i < outWorkers.length; i++) {
//                                        if(outWorkers[i].getPoints().get(new java.sql.Date(GlobalSettings.getCurrentDate().getTime()))[0] != null)
//                                            Info.display("Event", outWorkers[i].getPoints().get(new java.sql.Date(GlobalSettings.getCurrentDate().getTime()))[0].getName());
                                        outWorkers[i].setSecretIndex();
                                        grid.getStore().add(outWorkers[i]);
                                    }
                                    Info.display("Event", "Success");
//                                    for (int i = 0; i < 48; i++) {
//                                        Info.display("Event", outWorkers[0].getWorkPlanByCurrentDate()[i].getName());
//                                    }
                                }
                            });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        panel.addButton(optimize);

        button.addSelectHandler(new SelectEvent.SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {
                //button.getText()
                Cell.Context c = event.getContext();
                int row = c.getIndex();
                Worker worker = store.get(row);
                Info.display("Event", "The " + worker.getName() + " was clicked.");
                WorkerDialogGXT dialog = new WorkerDialogGXT(grid, row);
                dialog.show();
            }
        });

    }

    public Grid<Worker> getGrid() {
        return grid;
    }

    public void setGrid(Grid<Worker> grid) {
        this.grid = grid;
    }

    private ColumnConfig<Worker, TradePoint>[] CreateTimeColumns(){
        //ColumnConfig<Worker, TradePoint> workPlanColumn = new ColumnConfig<Worker, TradePoint>(properties.tradePoint());
        return null;
    }
    
    
}
