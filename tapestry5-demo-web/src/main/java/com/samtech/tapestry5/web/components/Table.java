package com.samtech.tapestry5.web.components;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.apache.tapestry5.Binding;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.Block;
import org.apache.tapestry5.ComponentAction;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.PropertyOverrides;
import org.apache.tapestry5.RenderSupport;
import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SupportsInformalParameters;
import org.apache.tapestry5.beaneditor.BeanModel;
import org.apache.tapestry5.beaneditor.PropertyModel;
import org.apache.tapestry5.corelib.components.Any;
import org.apache.tapestry5.corelib.components.Delegate;
import org.apache.tapestry5.corelib.components.GridColumns;
import org.apache.tapestry5.corelib.components.GridRows;
import org.apache.tapestry5.corelib.data.GridPagerPosition;
import org.apache.tapestry5.grid.ColumnSort;
import org.apache.tapestry5.grid.GridDataSource;
import org.apache.tapestry5.grid.GridModel;
import org.apache.tapestry5.grid.GridSortModel;
import org.apache.tapestry5.grid.SortConstraint;
import org.apache.tapestry5.internal.TapestryInternalUtils;
import org.apache.tapestry5.internal.beaneditor.BeanModelUtils;
import org.apache.tapestry5.internal.bindings.AbstractBinding;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.internal.util.Defense;
import org.apache.tapestry5.services.BeanModelSource;
import org.apache.tapestry5.services.ClientBehaviorSupport;
import org.apache.tapestry5.services.ComponentDefaultProvider;
import org.apache.tapestry5.services.ComponentEventResultProcessor;
import org.apache.tapestry5.services.FormSupport;
import org.apache.tapestry5.services.Request;
@SupportsInformalParameters
public class Table implements GridModel{
    /**
     * The source of data for the Grid to display. This will usually be a List or array but can also be an explicit
     * {@link GridDataSource}. For Lists and object arrays, a GridDataSource is created automatically as a wrapper
     * around the underlying List.
     */
    @Parameter(required = true, autoconnect = true)
    private GridDataSource source;

    /**
     * A wrapper around the provided GridDataSource that caches access to the availableRows property. This is the source
     * provided to sub-components.
     */
    private GridDataSource cachingSource;

    /**
     * The number of rows of data displayed on each page. If there are more rows than will fit, the Grid will divide up
     * the rows into "pages" and (normally) provide a pager to allow the user to navigate within the overall result
     * set.
     */
    @Parameter("25")
    private int rowsPerPage;

    /**
     * Defines where the pager (used to navigate within the "pages" of results) should be displayed: "top", "bottom",
     * "both" or "none".
     */
    @Parameter(value = "top", defaultPrefix = BindingConstants.LITERAL)
    private GridPagerPosition pagerPosition;

    /**
     * Used to store the current object being rendered (for the current row). This is used when parameter blocks are
     * provided to override the default cell renderer for a particular column ... the components within the block can
     * use the property bound to the row parameter to know what they should render.
     */
    @Parameter(principal = true)
    private Object row;

    /**
     * Optional output parmeter used to identify the index of the column being rendered.
     */
    @Parameter
    private int columnIndex;

    /**
     * The model used to identify the properties to be presented and the order of presentation. The model may be
     * omitted, in which case a default model is generated from the first object in the data source (this implies that
     * the objects provided by the source are uniform). The model may be explicitly specified to override the default
     * behavior, say to reorder or rename columns or add additional columns.
     */
    @Parameter
    private BeanModel model;

    /**
     * The model parameter after modification due to the add, include, exclude and reorder parameters.
     */
    private BeanModel dataModel;

    /**
     * The model used to handle sorting of the Grid. This is generally not specified, and the built-in model supports
     * only single column sorting. The sort constraints (the column that is sorted, and ascending vs. descending) is
     * stored as persistent fields of the Grid component.
     */
    @Parameter
    private GridSortModel sortModel;

    /**
     * A comma-seperated list of property names to be added to the {@link org.apache.tapestry5.beaneditor.BeanModel}.
     * Cells for added columns will be blank unless a cell override is provided.
     */
    @Parameter(defaultPrefix = BindingConstants.LITERAL)
    private String add;

    /**
     * A comma-separated list of property names to be retained from the {@link org.apache.tapestry5.beaneditor.BeanModel}.
     * Only these properties will be retained, and the properties will also be reordered. The names are
     * case-insensitive.
     */
    @SuppressWarnings("unused")
    @Parameter(defaultPrefix = BindingConstants.LITERAL)
    private String include;

    /**
     * A comma-separated list of property names to be removed from the {@link org.apache.tapestry5.beaneditor.BeanModel}.
     * The names are case-insensitive.
     */
    @Parameter(defaultPrefix = BindingConstants.LITERAL)
    private String exclude;

    /**
     * A comma-separated list of property names indicating the order in which the properties should be presented. The
     * names are case insensitive. Any properties not indicated in the list will be appended to the end of the display
     * order.
     */
    @Parameter(defaultPrefix = BindingConstants.LITERAL)
    private String reorder;

    /**
     * A Block to render instead of the table (and pager, etc.) when the source is empty. The default is simply the text
     * "There is no data to display". This parameter is used to customize that message, possibly including components to
     * allow the user to create new objects.
     */
    @Parameter(value = "block:empty", defaultPrefix = BindingConstants.LITERAL)
    private Block empty;

    /**
     * CSS class for the &lt;table&gt; element.  In addition, informal parameters to the Grid are rendered in the table
     * element.
     */
    @Parameter(name = "class", defaultPrefix = BindingConstants.LITERAL, value = "t-data-grid")
    @Property(write = false)
    private String tableClass;

    /**
     * If true, then the Grid will be wrapped in an element that acts like a {@link
     * org.apache.tapestry5.corelib.components.Zone}; all the paging and sorting links will refresh the zone, repainting
     * the entire grid within it, but leaving the rest of the page (outside the zone) unchanged.
     */
    @Parameter
    private boolean inPlace;

    /**
     * The name of the psuedo-zone that encloses the Grid.
     */
    @Property(write = false)
    private String zone;

    private boolean didRenderZoneDiv;

    @Persist
    private Integer currentPage;

    @Persist
    private String sortColumnId;

    @Persist
    private Boolean sortAscending;


    @Inject
    private ComponentResources resources;

    @Inject
    private BeanModelSource modelSource;

    @Environmental
    private ClientBehaviorSupport clientBehaviorSupport;

    @Component(
            parameters = {
                    "index=inherit:columnIndex",
                    "lean=inherit:lean",
                    "overrides=overrides",
                    "zone=zone" })
    private GridColumns columns;

    @Component(
            parameters = {
                    "columnIndex=inherit:columnIndex",
                    "rowsPerPage=rowsPerPage",
                    "currentPage=currentPage",
                    "row=row",
                    "overrides=overrides" },
            publishParameters = "rowIndex,rowClass,volatile,encoder,lean")
    private GridRows rows;

    @Component(parameters = {
            "source=dataSource",
            "rowsPerPage=rowsPerPage",
            "currentPage=currentPage",
            "zone=zone" })
    private TablePager pager;

    @Component(parameters = "to=pagerTop")
    private Delegate pagerTop;

    @Component(parameters = "to=pagerBottom")
    private Delegate pagerBottom;

    @Component(parameters = "class=tableClass", inheritInformalParameters = true)
    private Any table;

    @Environmental(false)
    private FormSupport formSupport;

    @Inject
    private Request request;

    @Environmental
    private RenderSupport renderSupport;

    /**
     * Defines where block and label overrides are obtained from. By default, the Grid component provides block
     * overrides (from its block parameters).
     */
    @Parameter(value = "this", allowNull = false)
    @Property(write = false)
    private PropertyOverrides overrides;

    /**
     * Set up via the traditional or Ajax component event request handler
     */
    @Environmental
    private ComponentEventResultProcessor componentEventResultProcessor;

    @Inject
    private ComponentDefaultProvider defaultsProvider;

    ValueEncoder defaultEncoder()
    {
        return defaultsProvider.defaultValueEncoder("row", resources);
    }

    /**
     * A version of GridDataSource that caches the availableRows property. This addresses TAPESTRY-2245.
     */
    static class CachingDataSource implements GridDataSource
    {
        private final GridDataSource delegate;

        private boolean availableRowsCached;

        private int availableRows;

        CachingDataSource(GridDataSource delegate)
        {
            this.delegate = delegate;
        }

        public int getAvailableRows()
        {
            if (!availableRowsCached)
            {
                availableRows = delegate.getAvailableRows();
                availableRowsCached = true;
            }

            return availableRows;
        }

        public void prepare(int startIndex, int endIndex, List<SortConstraint> sortConstraints)
        {
            delegate.prepare(startIndex, endIndex, sortConstraints);
        }

        public Object getRowValue(int index)
        {
            return delegate.getRowValue(index);
        }

        public Class getRowType()
        {
            return delegate.getRowType();
        }
    }

    /**
     * Default implementation that only allows a single column to be the sort column, and stores the sort information as
     * persistent fields of the Grid component.
     */
    class DefaultGridSortModel implements GridSortModel
    {
        public ColumnSort getColumnSort(String columnId)
        {
            if (!TapestryInternalUtils.isEqual(columnId, sortColumnId))
                return ColumnSort.UNSORTED;

            return getColumnSort();
        }

        private ColumnSort getColumnSort()
        {
            return getSortAscending() ? ColumnSort.ASCENDING : ColumnSort.DESCENDING;
        }


        public void updateSort(String columnId)
        {
            Defense.notBlank(columnId, "columnId");

            if (columnId.equals(sortColumnId))
            {
                setSortAscending(!getSortAscending());
                return;
            }

            sortColumnId = columnId;
            setSortAscending(true);
        }

        public List<SortConstraint> getSortConstraints()
        {
            if (sortColumnId == null)
                return Collections.emptyList();

            PropertyModel sortModel = getDataModel().getById(sortColumnId);

            SortConstraint constraint = new SortConstraint(sortModel, getColumnSort());

            return Collections.singletonList(constraint);
        }

        public void clear()
        {
            sortColumnId = null;
        }
    }

    GridSortModel defaultSortModel()
    {
        return new DefaultGridSortModel();
    }

    /**
     * Returns a {@link org.apache.tapestry5.Binding} instance that attempts to identify the model from the source
     * parameter (via {@link org.apache.tapestry5.grid.GridDataSource#getRowType()}. Subclasses may override to provide
     * a different mechanism.  The returning binding is variant (not invariant).
     *
     * @see BeanModelSource#createDisplayModel(Class, org.apache.tapestry5.ioc.Messages)
     */
    protected Binding defaultModel()
    {
        return new AbstractBinding()
        {
            public Object get()
            {
                // Get the default row type from the data source

                GridDataSource gridDataSource = source;

                Class rowType = gridDataSource.getRowType();

                if (rowType == null)
                    throw new RuntimeException(
                            String.format(
                                    "Unable to determine the bean type for rows from %s. You should bind the model parameter explicitly.",
                                    gridDataSource));

                // Properties do not have to be read/write

                return modelSource.createDisplayModel(rowType, overrides.getOverrideMessages());
            }

            /**
             * Returns false. This may be overkill, but it basically exists because the model is
             * inherently mutable and therefore may contain client-specific state and needs to be
             * discarded at the end of the request. If the model were immutable, then we could leave
             * invariant as true.
             */
            @Override
            public boolean isInvariant()
            {
                return false;
            }
        };
    }

    static final ComponentAction<Table> SETUP_DATA_SOURCE = new ComponentAction<Table>()
    {
        private static final long serialVersionUID = 8545187927995722789L;

        public void execute(Table component)
        {
            component.setupDataSource();
        }

        @Override
        public String toString()
        {
            return "Table.SetupDataSource";
        }
    };

    Object setupRender()
    {
        if (formSupport != null) formSupport.store(this, SETUP_DATA_SOURCE);

        setupDataSource();

        // If there's no rows, display the empty block placeholder.

        return cachingSource.getAvailableRows() == 0 ? empty : null;
    }

    void setupDataSource()
    {
        // TAP5-34: We pass the source into the CachingDataSource now; previously
        // we were accessing source directly, but during submit the value wasn't
        // cached, and therefore access was very inefficient, and sorting was
        // very inconsistent during the processing of the form submission.

        cachingSource = new CachingDataSource(source);

        int availableRows = cachingSource.getAvailableRows();

        if (availableRows == 0) return;

        int maxPage = ((availableRows - 1) / rowsPerPage) + 1;

        // This captures when the number of rows has decreased, typically due to deletions.

        int effectiveCurrentPage = getCurrentPage();

        if (effectiveCurrentPage > maxPage)
            effectiveCurrentPage = maxPage;

        int startIndex = (effectiveCurrentPage - 1) * rowsPerPage;

        int endIndex = Math.min(startIndex + rowsPerPage - 1, availableRows - 1);

        dataModel = null;

        cachingSource.prepare(startIndex, endIndex, sortModel.getSortConstraints());
    }

    Object beginRender(MarkupWriter writer)
    {
        // Skip rendering of component (template, body, etc.) when there's nothing to display.
        // The empty placeholder will already have rendered.

        if (cachingSource.getAvailableRows() == 0) return false;

        if (inPlace && zone == null)
        {
            zone = renderSupport.allocateClientId(resources);

            writer.element("div", "id", zone);

            clientBehaviorSupport.addZone(zone, null, "show");

            didRenderZoneDiv = true;
        }

        return null;
    }

    void afterRender(MarkupWriter writer)
    {
        if (didRenderZoneDiv)
        {
            writer.end(); // div
            didRenderZoneDiv = false;
        }
    }

    public BeanModel getDataModel()
    {
        if (dataModel == null)
        {
            dataModel = model;

            BeanModelUtils.modify(dataModel, add, include, exclude, reorder);
        }

        return dataModel;
    }

    public GridDataSource getDataSource()
    {
        return cachingSource;
    }

    public GridSortModel getSortModel()
    {
        return sortModel;
    }

    public Object getPagerTop()
    {
        return pagerPosition.isMatchTop() ? pager : null;
    }

    public Object getPagerBottom()
    {
        return pagerPosition.isMatchBottom() ? pager : null;
    }

    public int getCurrentPage()
    {
        return currentPage == null ? 1 : currentPage;
    }

    public void setCurrentPage(int currentPage)
    {
        this.currentPage = currentPage;
    }

    private boolean getSortAscending()
    {
        return sortAscending != null && sortAscending.booleanValue();
    }

    private void setSortAscending(boolean sortAscending)
    {
        this.sortAscending = sortAscending;
    }

    public int getRowsPerPage()
    {
        return rowsPerPage;
    }

    public Object getRow()
    {
        return row;
    }

    public void setRow(Object row)
    {
        this.row = row;
    }

    /**
     * Resets the Grid to inital settings; this sets the current page to one, and {@linkplain
     * org.apache.tapestry5.grid.GridSortModel#clear() clears the sort model}.
     */
    public void reset()
    {
        setCurrentPage(1);
        sortModel.clear();
    }

    /**
     * Event handler for inplaceupdate event triggered from nested components when an Ajax update occurs. The event
     * context will carry the zone, which is recorded here, to allow the Grid and its sub-components to properly
     * re-render themselves.  Invokes {@link org.apache.tapestry5.services.ComponentEventResultProcessor#processResultValue(Object)}
     * passing this (the Grid component) as the content provider for the update.
     */
    void onInPlaceUpdate(String zone) throws IOException
    {
        this.zone = zone;

        componentEventResultProcessor.processResultValue(this);
    }
}
