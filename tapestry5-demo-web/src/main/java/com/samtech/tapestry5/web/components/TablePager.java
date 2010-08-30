package com.samtech.tapestry5.web.components;

import java.util.ArrayList;
import java.util.List;

import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.Events;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.dom.Element;
import org.apache.tapestry5.grid.GridDataSource;
import org.apache.tapestry5.internal.InternalConstants;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.ClientBehaviorSupport;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

@Events(InternalConstants.GRID_INPLACE_UPDATE + " (internal event)")
public class TablePager {
    /**
     * The source of the data displayed by the grid (this is used to determine {@link GridDataSource#getAvailableRows()
     * how many rows are available}, which in turn determines the page count).
     */
    @Parameter(required = true)
    private GridDataSource source;

    /**
     * The number of rows displayed per page.
     */
    @Parameter(required = true)
    private int rowsPerPage;

    /**
     * The current page number (indexed from 1).
     */
    @Parameter(required = true)
    private int currentPage;
    
    /**
     * Number of pages before and after the current page in the range. The pager always displays links for 2 * range + 1
     * pages, unless that's more than the total number of available pages.
     */
    @Parameter("3")
    private int range;

    /**
     * If not null, then each link is output as a link to update the specified zone.
     */
    @Parameter
    private String zone;
    @Property
    private int lastIndex;
    
    @Property
    private int maxPages;
    @Property
    private Integer ipage;
    @Inject
    private ComponentResources resources;

    @Inject
    private Messages messages;

    @Environmental
    private ClientBehaviorSupport clientBehaviorSupport;

    /*Environmental
    private RenderSupport renderSupport;*/
    @Environmental
    private JavaScriptSupport javascriptSupport;
    
    private boolean isshow;
    private int totalRows;
    @SuppressWarnings("unused")
	private boolean firstcom,lastcom;
    private List<Integer> pages;
    
    public boolean isShowPager( ){
    	return isshow;
    }
    
    public String getTotalRowMsg( ){
    	return this.messages.format("totalRows-msg", totalRows);
    	
    }
    public String getPagesMsg( ){
    	return this.messages.format("maxPages-msg", maxPages);
    }
    
    public List<Integer> getPages(){
    	return pages;
    }
	void beginRender(MarkupWriter writer)
    {
        totalRows = source.getAvailableRows();
        isshow=false;
        maxPages = ((totalRows - 1) / rowsPerPage) + 1;
        pages=new ArrayList<Integer>();
        if (maxPages < 2) return;
        isshow=true;
        
        //writer.element("div", "class", "t-data-grid-pager");

        lastIndex = 0;

        for (int i = 1; i <= 1; i++){
        	pages.add(i);// writePageLink(writer, i);
        	lastIndex=i;
        }

        int low = currentPage - range;
        int high = currentPage + range;

        if (low < 1)
        {
            low = 1;
            high = 2 * range + 1;
        }
        
        if (high > maxPages)
        {
            high = maxPages;
        }
        if(low>(lastIndex+1))firstcom=true;
        for (int i = low; i <= high; i++)
        	if(i>lastIndex ){
         		pages.add(i);
        	}
        
            //writePageLink(writer, i);
       
        if(high<maxPages){
        	pages.add(maxPages);
        }
        

        //writer.end();*/
    }
	public boolean isCurPage(){
		if(this.ipage!=null)return this.ipage.intValue()==currentPage;
		return false;
	}
	public boolean isShowComment(){
		if(firstcom && this.ipage.intValue()==1)return true;
		if(firstcom && lastIndex==(pages.size()-1))return true;
		return false;
	}
	public Object[] getLinkContext(){
		int pageIndex=this.ipage;
		Object[] context = zone == null
        ? new Object[] { pageIndex }
        : new Object[] { pageIndex, zone };
        return context;
	}
	public String getLinkTitle() {
	return messages.format("goto-page", this.ipage);
	}
    @SuppressWarnings("unused")
	private void writePageLink(MarkupWriter writer, int pageIndex)
    {
        if (pageIndex < 1 || pageIndex > maxPages) return;

        if (pageIndex <= lastIndex) return;

        if (pageIndex != lastIndex + 1) writer.write(" ... ");

        lastIndex = pageIndex;

        if (pageIndex == currentPage)
        {
            writer.element("span", "class", "current");
            writer.write(Integer.toString(pageIndex));
            writer.end();
            return;
        }

        Object[] context = zone == null
                           ? new Object[] { pageIndex }
                           : new Object[] { pageIndex, zone };

        Link link = resources.createEventLink(EventConstants.ACTION, context);

        Element element = writer.element("a",
                                         "href", zone == null ? link : "#",
                                         "title", messages.format("goto-page", pageIndex));

        writer.write(Integer.toString(pageIndex));
        writer.end();

        if (zone != null)
        {
            String id = javascriptSupport.allocateClientId(resources);// renderSupport.allocateClientId(resources);

            element.attribute("id", id);

            clientBehaviorSupport.linkZone(id, zone, link);
        }
    }

    /**
     * Normal, non-Ajax event handler.
     */
    void onAction(int newPage)
    {
        // TODO: Validate newPage in range

        currentPage = newPage;
    }

    /**
     * Akjax event handler, passing the zone along.
     */
    boolean onAction(int newPage, String zone)
    {
        onAction(newPage);

        resources.triggerEvent(InternalConstants.GRID_INPLACE_UPDATE, new Object[] { zone }, null);

        return true; // abort event
    }
}
