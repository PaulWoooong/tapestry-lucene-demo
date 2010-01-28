package com.samtech.business.lucene;

import java.util.Date;

import org.apache.commons.pool.ObjectPool;
import org.apache.commons.pool.impl.GenericObjectPool;

public class IndexReadPoolApacheImpl implements IIndexReadPool {

   private ObjectPool  pool;
   private static IndexReadPoolApacheImpl instance;
   private Date lastOpenDate;//对象最后创建时间
   int maxActive=10;
   int initActive=3;
	 
   private IndexReadPoolApacheImpl(){ 
	   pool = new GenericObjectPool(new IndexReaderFactory(), maxActive,
				GenericObjectPool.WHEN_EXHAUSTED_BLOCK, 5000, maxActive,initActive,false,false,1000*60*10L,3,1000*60*30L,false);
   }
   public static IndexReadPoolApacheImpl getInstance(){
	   if(instance==null){
		   instance=new IndexReadPoolApacheImpl();
	   }
	   return instance;
   }
	public void closeIndexRead(IndexReadCom p) throws Exception {
		if(needReOpen(p)){
			p.getReader().close();
		    pool.returnObject(new IndexReaderFactory().makeObject());
		}else{
			pool.returnObject(p);
		}
	}

	public void closePool() throws Exception {
		pool.close();
	}

	public IndexReadCom getIndexRead() throws Exception {
		IndexReadCom read= (IndexReadCom) pool.borrowObject();
		if(needReOpen(read)){
			read.getReader().close();
			return (IndexReadCom) new IndexReaderFactory().makeObject();
		}else{
			return read;
		}
	}

	public void reOpen() {
		lastOpenDate=new Date();
	}
	
	private boolean needReOpen(IndexReadCom p){
		if(lastOpenDate!=null){
			if(p.getCreateDate().getTime()<lastOpenDate.getTime())
				 return true;
		}		
		return false;
	}

}
