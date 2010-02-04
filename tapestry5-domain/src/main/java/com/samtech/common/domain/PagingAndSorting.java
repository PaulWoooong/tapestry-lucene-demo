package com.samtech.common.domain;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;



public class PagingAndSorting implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	Collection<SortColumn> sorts=new ArrayList<SortColumn>(5) ;
	private int end=Integer.MAX_VALUE;
	private int start=-1;
	private String alias;
	

	public PagingAndSorting() {
		
	}
	
	
	public PagingAndSorting(int start, int end) {
		super();
		this.start = start;
		this.end = end;
	}


	public int getEnd() {
		return end;
	}
	public void setEnd(int end) {
		this.end = end;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public String getAlias() {
		return alias;
	}


	public void setAlias(String alias) {
		this.alias = alias;
	}
	
	public Collection<SortColumn> getSorts(){
		return Collections.unmodifiableCollection(sorts);
	}
	
	public void addSortColumn(SortColumn c){
		sorts.add(c);
	}


	public static String buildOrderBy(String query, PagingAndSorting pg) {
		 if (pg != null && pg.getSorts() != null && !pg.getSorts().isEmpty()) {
		    	StringBuffer orderBy = new StringBuffer();
		    	int j = query.toLowerCase().indexOf(" order ");
		    	if(j>0){
		    		int kk = query.toLowerCase().indexOf(" by ", j-1);
		    		
		    		if(kk>=0)
		    		{
		    			orderBy.append(query.substring(0, j));
		    		}
		    		else 
		    			orderBy.append(new String(query));
		    	}else
		    		orderBy.append(new String(query));
		        orderBy.append(" order by ");
		        SortColumn[] sorts =new SortColumn[0];
		        sorts= pg.getSorts().toArray(sorts);
		       
		        //.getOrderProperties().split(",");
		        for (int i = 0; i < sorts.length; i++) {
		            if (i > 0) {
		                orderBy.append(", ");
		            }
		            if (pg.getAlias() != null )
		                
		                orderBy.append(pg.getAlias() + ".");
		            orderBy.append(sorts[i].getProperty());
		            if (sorts[i].getOrder().equals(Order.DESC)) {
		                orderBy.append(" desc");
		            }
		        }
		        //System.out.println("return="+orderBy.toString());
		        return orderBy.toString();
		    }
		    return query;
		
	}
	
	
}
