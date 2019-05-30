package com.mySampleApplication.client.GXTClient;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.editor.client.Editor.Path;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.shared.DateTimeFormat;
import com.google.gwt.i18n.shared.DateTimeFormat.PredefinedFormat;
import com.google.gwt.safecss.shared.SafeStyles;
import com.google.gwt.safecss.shared.SafeStylesUtils;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.*;
import com.sencha.gxt.cell.core.client.ProgressBarCell;
import com.sencha.gxt.cell.core.client.ResizeCell;
import com.sencha.gxt.cell.core.client.SliderCell;
import com.sencha.gxt.cell.core.client.TextButtonCell;
import com.sencha.gxt.cell.core.client.form.ComboBoxCell;
import com.sencha.gxt.cell.core.client.form.ComboBoxCell.TriggerAction;
import com.sencha.gxt.cell.core.client.form.DateCell;
import com.sencha.gxt.core.client.Style.Side;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.core.client.dom.XElement;
import com.sencha.gxt.core.client.resources.CommonStyles;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.core.client.util.Scroll;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
//import com.sencha.gxt.examples.resources.client.TestData;
//import com.sencha.gxt.examples.resources.client.model.Plant;
//import com.sencha.gxt.explorer.client.app.ui.ExampleContainer;
//import com.sencha.gxt.explorer.client.model.Example.Detail;
import com.sencha.gxt.dnd.core.client.DragSource;
import com.sencha.gxt.theme.base.client.colorpalette.ColorPaletteBaseAppearance;
import com.sencha.gxt.widget.core.client.ColorPaletteCell;
import com.sencha.gxt.widget.core.client.ColorPaletteCell.ColorPaletteAppearance;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.Header;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.*;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutPack;
import com.sencha.gxt.widget.core.client.event.CellSelectionEvent;
import com.sencha.gxt.widget.core.client.event.ColumnWidthChangeEvent;
import com.sencha.gxt.widget.core.client.event.ColumnWidthChangeEvent.ColumnWidthChangeHandler;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.DateTimePropertyEditor;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnHeader;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.info.Info;
import com.sencha.gxt.widget.core.client.menu.ColorMenu;
import com.sencha.gxt.widget.core.client.tips.ToolTipConfig;

public class CellGridExample implements IsWidget {

    private static final String[] COLORS = new String[] { "161616", "002241", "006874", "82a700", "bbc039", "f3f1cd" };

    public static List<Plant> getPlants() {
        List<Plant> plants = new ArrayList<Plant>();
        plants.add(new Plant("1", "Mostly Shady", 2.44, "03/15/2012", true));
        plants.add(new Plant("Columbine", "Shade", 9.37, "03/15/2009", true));
        plants.add(new Plant("Marsh Marigold", "Mostly Sunny", 6.81, "05/17/2011", false));
        plants.add(new Plant("Cowslip", "Mostly Shady", 9.90, "03/06/2006", true));
        plants.add(new Plant("Dutchman's-Breeches", "Mostly Shady", 6.44, "01/20/2009", true));
        plants.add(new Plant("Ginger, Wild", "Mostly Shady", 9.03, "04/18/2011", true));
        plants.add(new Plant("Hepatica", "Sunny", 4.45, "01/26/2006", true));
        plants.add(new Plant("Liverleaf", "Mostly Sunny", 3.99, "01/02/2006", true));
        plants.add(new Plant("Jack-In-The-Pulpit", "Mostly Shady", 3.23, "02/01/2011", true));
        plants.add(new Plant("Mayapple", "Mostly Shady", 2.98, "06/05/2006", true));
        plants.add(new Plant("Phlox, Woodland", "Sun or Shade", 2.80, "01/22/2009", false));
        plants.add(new Plant("Phlox, Blue", "Sun or Shade", 5.59, "02/16/2008", false));
        plants.add(new Plant("Spring-Beauty", "Mostly Shady", 6.59, "02/01/2012", true));
        plants.add(new Plant("Trillium", "Sun or Shade", 3.90, "04/29/2006", false));
        plants.add(new Plant("Wake Robin", "Sun or Shade", 3.20, "02/21/20012", false));
        plants.add(new Plant("Violet, Dog-Tooth", "Shade", 9.04, "02/01/20011", true));
        plants.add(new Plant("Trout Lily", "Shade", 6.94, "03/24/2009", true));
        plants.add(new Plant("Adder's-Tongue", "Mostly Shady", 6.59, "02/01/2006", true));
        plants.add(new Plant("Anemone", "Mostly Shady", 8.86, "12/26/2011", true));
        plants.add(new Plant("Grecian Windflower", "Mostly Shady", 9.16, "07/10/2011", true));
        plants.add(new Plant("Bee Balm", "Shade", 4.59, "05/03/2009", true));
        plants.add(new Plant("Bergamot", "Shade", 7.16, "04/27/2010", true));
        plants.add(new Plant("Black-Eyed Susan", "Sunny", 9.80, "06/18/2010", false));
        plants.add(new Plant("Buttercup", "Shade", 2.57, "06/10/2009", true));
        plants.add(new Plant("Crowfoot", "Shade", 9.34, "04/03/2012", true));
        plants.add(new Plant("Butterfly Weed", "Sunny", 2.78, "06/30/2011", false));
        plants.add(new Plant("Cinquefoil", "Shade", 7.06, "05/25/2008", true));
        plants.add(new Plant("Primrose", "Sunny", 6.56, "01/30/2012", false));
        plants.add(new Plant("Gentian", "Sun or Shade", 7.81, "05/18/2008", false));
        plants.add(new Plant("Greek Valerian", "Shade", 3.41, "04/03/2009", true));
        plants.add(new Plant("California Poppy", "Mostly Shady", 2.78, "05/13/2011", false));
        plants.add(new Plant("Shooting Star", "Shade", 7.06, "07/11/2008", true));
        plants.add(new Plant("Snakeroot", "Sunny", 6.56, "02/22/2008", false));
        plants.add(new Plant("Cardinal Flower", "Shade", 7.81, "05/18/2006", false));
        return plants;
    }

    interface PlaceProperties extends PropertyAccess<Plant> {
        ValueProvider<Plant, Date> available();

        @Path("name")
        ModelKeyProvider<Plant> key();

        ValueProvider<Plant, String> name();

        ValueProvider<Plant, Integer> difficulty();

        ValueProvider<Plant, Double> progress();

        ValueProvider<Plant, String> color();

        ValueProvider<Plant, String> light();
    }

    protected static final int MAX_HEIGHT = 600;
    protected static final int MAX_WIDTH = 900;
    protected static final int MIN_HEIGHT = 320;
    protected static final int MIN_WIDTH = 900;

    private static final PlaceProperties properties = GWT.create(PlaceProperties.class);

    private ListStore<Plant> store;
    private ContentPanel panel;

    public class CellColumnResizer<M, T> implements ColumnWidthChangeHandler {

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
                        cell.render(new Context(i, col, store.getKeyProvider().getKey(p)), column.getValueProvider().getValue(p),
                                sb);
                        parent.setInnerSafeHtml(sb.toSafeHtml());
                    }
                }
            }
        }
    }

    @Override
    public Widget asWidget() {
            // reduce the padding on text element as we have widgets in the cells
            SafeStyles btnPaddingStyle = SafeStylesUtils.fromTrustedString("padding: 1px 3px 0;");
            SafeStyles fieldPaddingStyle = SafeStylesUtils.fromTrustedString("padding: 2px 3px;");

            ColumnConfig<Plant, String> nameColumn = new ColumnConfig<Plant, String>(properties.name(), 150, "Name");
            // IMPORTANT we want the text element (cell parent) to only be as wide as
            // the cell and not fill the cell
            nameColumn.setColumnTextClassName(CommonStyles.get().inlineBlock());
            nameColumn.setColumnTextStyle(btnPaddingStyle);

            TextButtonCell button = new TextButtonCell() {
                // Override this to control the row selection
                @Override
                public boolean handlesSelection() {
                    return true;
                }
            };
            button.setWidth(1);
            //new DragSource()
            button.addSelectHandler(new SelectHandler() {
                @Override
                public void onSelect(SelectEvent event) {
                    Context c = event.getContext();
                    int row = c.getIndex();
                    Plant p = store.get(row);
                    Info.display("Event", "The " + p.getName() + " was clicked.");
                }
            });
            nameColumn.setCell(button);

            DateCell dateCell = new DateCell() {
                // Override this to control the row selection
                @Override
                public boolean handlesSelection() {
                    return false;
                }

                @Override
                protected void onClick(final Context context, final XElement parent, final NativeEvent event, final Date value,
                                       final ValueUpdater<Date> updater) {
                    super.onClick(context, parent, event, value, updater);
                    GWT.log("DateCell onClick");
                }

                @Override
                protected void onMouseDown(final XElement parent, final NativeEvent event) {

                    final String dateCellId = parent.getId();

                    Timer t = new Timer() {
                        @Override
                        public void run() {
                            GWT.log("DateCell onMouseDown id=" + dateCellId);

                            XElement newParent = Document.get().getElementById(dateCellId).cast();

                            laterOnMouseDown(newParent, event);

                        }
                    };
                    t.schedule(1000);
                }

                void laterOnMouseDown(XElement parent, NativeEvent event) {
                    super.onMouseDown(parent, event);
                }

                @Override
                protected void onFocus(Context context, XElement parent, Date value, NativeEvent event,
                                       ValueUpdater<Date> valueUpdater) {
                    super.onFocus(context, parent, value, event, valueUpdater);
                    GWT.log("DateCell onFocus");
                }

                @Override
                protected void onBlur(Context context, XElement parent, Date value, NativeEvent event,
                                      ValueUpdater<Date> valueUpdater) {
                    super.onBlur(context, parent, value, event, valueUpdater);
                    GWT.log("DateCell onBlur");
                }

                @Override
                protected void modifyRenderTriggerFieldOptions(Context context, Date value, FieldAppearanceOptions options,
                                                               FieldViewData viewData) {
                    super.modifyRenderTriggerFieldOptions(context, value, options, viewData);
                    String dateCellId = "dateCell_" + context.getIndex() + "_" + context.getColumn();
                    options.setId(dateCellId);
                }
            };
            dateCell.getDatePicker().addValueChangeHandler(new ValueChangeHandler<Date>() {
                @Override
                public void onValueChange(ValueChangeEvent<Date> event) {
                    Info.display("Date Selected",
                            "You selected " + DateTimeFormat.getFormat(PredefinedFormat.DATE_SHORT).format(event.getValue()));
                }
            });
            dateCell.setPropertyEditor(new DateTimePropertyEditor(DateTimeFormat.getFormat(PredefinedFormat.DATE_SHORT)));

            ColumnConfig<Plant, Date> availableColumn = new ColumnConfig<Plant, Date>(properties.available(), 160, "Date");
            availableColumn.setColumnTextStyle(fieldPaddingStyle);
            availableColumn.setCell(dateCell);

            ListStore<String> lights = new ListStore<String>(new ModelKeyProvider<String>() {
                @Override
                public String getKey(String item) {
                    return item;
                }
            });
            lights.add("Mostly Shady");
            lights.add("Mostly Sunny");
            lights.add("Shade");
            lights.add("Sunny");
            lights.add("Sun or Shade");

            ColumnConfig<Plant, String> lightColumn = new ColumnConfig<Plant, String>(properties.light(), 130, "Light");
            lightColumn.setColumnTextStyle(fieldPaddingStyle);

            ComboBoxCell<String> lightCombo = new ComboBoxCell<String>(lights, new LabelProvider<String>() {
                @Override
                public String getLabel(String item) {
                    return item;
                }
            }) {
                // Override this to control the row selection
                @Override
                public boolean handlesSelection() {
                    return true;
                }

                @Override
                protected void onClick(final Context context, final XElement parent, final NativeEvent event,
                                       final String value, final ValueUpdater<String> updater) {
                    super.onClick(context, parent, event, value, updater);

                    GWT.log("Combo onClick");
                }

                @Override
                protected void onMouseDown(XElement parent, NativeEvent event) {
                    super.onMouseDown(parent, event);
                    GWT.log("Combo onMouseDown");
                }

                @Override
                protected void onTriggerClick(Context context, XElement parent, NativeEvent event, String value,
                                              ValueUpdater<String> updater) {
                    super.onTriggerClick(context, parent, event, value, updater);
                    GWT.log("Combo onTriggerClick");
                }

                @Override
                protected void onFocus(Context context, XElement parent, String value, NativeEvent event,
                                       ValueUpdater<String> valueUpdater) {
                    super.onFocus(context, parent, value, event, valueUpdater);
                    GWT.log("Combo onFocus");
                }

                @Override
                protected void onBlur(Context context, XElement parent, String value, NativeEvent event,
                                      ValueUpdater<String> valueUpdater) {
                    super.onBlur(context, parent, value, event, valueUpdater);
                    GWT.log("Combo onBlur");
                }

                private void later(Context context, XElement parent, NativeEvent event, String value,
                                   ValueUpdater<String> updater) {
                    super.onClick(context, parent, event, value, updater);
                }
            };
            lightCombo.addSelectionHandler(new SelectionHandler<String>() {
                @Override
                public void onSelection(SelectionEvent<String> event) {
                    CellSelectionEvent<String> sel = (CellSelectionEvent<String>) event;
                    Plant p = store.get(sel.getContext().getIndex());
                    Info.display("Lightness Selected", p.getName() + " selected " + event.getSelectedItem());
                }
            });
            lightCombo.setTriggerAction(TriggerAction.ALL);
            lightCombo.setForceSelection(true);
            lightCombo.setWidth(120);
            lightColumn.setCell(lightCombo);

            ColumnConfig<Plant, String> colorColumn = new ColumnConfig<Plant, String>(properties.color(), 150, "Color");
            colorColumn.setColumnTextStyle(fieldPaddingStyle);

            // This next line only works with any appearance that extends from Base
            ColorPaletteBaseAppearance appearance = GWT.create(ColorPaletteAppearance.class);
            appearance.setColumnCount(6);

            ColorPaletteCell colorPalette = new ColorPaletteCell(appearance, COLORS, COLORS) {
                // Override this to control the row selection
                @Override
                public boolean handlesSelection() {
                    return false;
                }
            };
            colorPalette.addSelectionHandler(new SelectionHandler<String>() {
                @Override
                public void onSelection(SelectionEvent<String> event) {
                    Info.display("Color Selected", "You selected " + event.getSelectedItem());
                }
            });
            colorColumn.setCell(colorPalette);

            // Custom slider tooltip configuration, which displays the tooltip to the right of the control.
            ToolTipConfig tooltipConfig = new ToolTipConfig();
            tooltipConfig.setAnchor(Side.LEFT);
            tooltipConfig.setAnchorArrow(false);
            tooltipConfig.setMouseOffsetX(25);
            tooltipConfig.setMouseOffsetY(0);
            tooltipConfig.setDismissDelay(1000);

            SliderCell slider = new SliderCell() {
                // Override this to control the row selection
                @Override
                public boolean handlesSelection() {
                    return false;
                }
            };
            slider.setToolTipConfig(tooltipConfig);
            slider.setWidth(140);

            ColumnConfig<Plant, Integer> difficultyColumn = new ColumnConfig<Plant, Integer>(properties.difficulty(), 150,
                    "Durability");
            difficultyColumn.setColumnTextStyle(fieldPaddingStyle);
            difficultyColumn.setCell(slider);

            final ColumnConfig<Plant, Double> progressColumn = new ColumnConfig<Plant, Double>(properties.progress(), 150,
                    "Progress");
            progressColumn.setColumnTextStyle(fieldPaddingStyle);
            final ProgressBarCell progress = new ProgressBarCell() {
                // Override this to control the row selection
                @Override
                public boolean handlesSelection() {
                    return false;
                }
            };
            progress.setProgressText("{0}% Complete");
            progress.setWidth(140);
            progressColumn.setCell(progress);

            nameColumn.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
            availableColumn.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
            lightColumn.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
            colorColumn.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
            difficultyColumn.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
            progressColumn.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);

            List<ColumnConfig<Plant, ?>> columns = new ArrayList<ColumnConfig<Plant, ?>>();
            columns.add(nameColumn);
            columns.add(availableColumn);
            columns.add(lightColumn);
            columns.add(colorColumn);
            columns.add(difficultyColumn);
            columns.add(progressColumn);

            ColumnModel<Plant> cm = new ColumnModel<Plant>(columns);
//            cm.getColumn(0).getCell()


            List<Plant> plants = new ArrayList<Plant>(getPlants());
            for (Plant p : plants) {
                p.setColor(COLORS[Random.nextInt(4)]);
            }

            store = new ListStore<>(properties.key());
            store.addAll(plants);

            final Grid<Plant> grid = new Grid<Plant>(store, cm);
            grid.getView().setAutoExpandColumn(nameColumn);
            grid.getView().setTrackMouseOver(false);
            grid.getColumnModel()
                    .addColumnWidthChangeHandler(new CellColumnResizer<Plant, Double>(grid, progressColumn, progress));

            panel = new ContentPanel();
            panel.setHeading("Cell Grid");
            panel.add(grid);
            panel.setButtonAlign(BoxLayoutPack.CENTER);
            panel.addButton(new TextButton("Reset", new SelectHandler() {
                @Override
                public void onSelect(SelectEvent event) {
                    store.rejectChanges();
                }
            }));
            panel.addButton(new TextButton("Save", new SelectHandler() {
                @Override
                public void onSelect(SelectEvent event) {
                    store.commitChanges();
                }
            }));
        return panel;
    }

//    @Override
//    public void onModuleLoad() {
//        //SimpleContainer container = new SimpleContainer();
////        MainGridController gridController = new MainGridController();
////        ContentPanel pnl = gridController.getPanel();
//        BorderLayoutContainer container = new BorderLayoutContainer();
//        ContentPanel cp = new ContentPanel();
//        cp.setHeading("North");
//        cp.add(new Label("North Content"));
//        BorderLayoutContainer.BorderLayoutData d = new BorderLayoutContainer.BorderLayoutData(.20);
//        d.setMargins(new Margins(5));
//        d.setCollapsible(true);
//        d.setSplit(true);
//        container.setNorthWidget(cp, d);
//
//        cp = new ContentPanel();
//        cp.setHeading("West");
//        HorizontalLayoutContainer hC = new HorizontalLayoutContainer();
////        ArrayList<VerticalLayoutContainer> vC = new ArrayList<>();
//        VerticalLayoutContainer containerForBtn1 = new VerticalLayoutContainer();
//
//        containerForBtn1.add(new Label("8:00"));
//        containerForBtn1.add(new TextButton("Asd"));
////        vC.add(containerForBtn1);
//        VerticalLayoutContainer containerForBtn2 = new VerticalLayoutContainer();
//        containerForBtn2.add(new Label("Col2"));
//        containerForBtn2.add(new TextButton("Dsa"));
////        vC.add(containerForBtn2);
//        hC.add(containerForBtn1);
//        hC.add(containerForBtn2);
//        cp.add(hC);
//        d = new BorderLayoutContainer.BorderLayoutData(.20);
//        d.setMargins(new Margins(0, 5, 5, 5));
//        d.setCollapsible(true);
//        d.setSplit(true);
//        d.setCollapseMini(true);
//        container.setWestWidget(cp, d);
//
//        //cp = new ContentPanel();
//        //cp.setHeading("Center");
//        //cp.add(this);
//        d = new BorderLayoutContainer.BorderLayoutData();
//        d.setMargins(new Margins(0, 5, 5, 0));
//        container.setCenterWidget(this, d);
//        //container.setCenterWidget(this);
//        //container.add(this);
//        //container.setWidth(900);
//        //container.setHeight(600);
//        Viewport v = new Viewport();
//        //v.setTouchKeyboardAdjustPan(true);
//        v.add(container);
//        RootPanel.get().add(v);
//        //RootPanel.get().add(container);
//        //new ExampleContainer(this).setMaxHeight(MAX_HEIGHT).setMaxWidth(MAX_WIDTH).setMinHeight(MIN_HEIGHT)
//               // .setMinWidth(MIN_WIDTH).doStandalone();
//
//
//    }

}

