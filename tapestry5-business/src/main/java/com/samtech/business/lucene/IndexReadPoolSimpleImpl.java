package com.samtech.business.lucene;

import java.io.File;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import com.samtech.business.lucene.LucensIndexService;

public class IndexReadPoolSimpleImpl implements IIndexReadPool {
 
   private static IndexReadPoolSimpleImpl  pool=new IndexReadPoolSimpleImpl();	
	
   public  static IndexReadPoolSimpleImpl getInstance(){
	   return pool;
   }
   private IndexReadPoolSimpleImpl(){
	   
   }
	
	public void closeIndexRead(IndexReadCom p) throws Exception {
		p.getReader().close();

	}

	public void closePool() throws Exception {
		

	}

	public IndexReadCom getIndexRead() throws Exception {
		Directory dir=FSDirectory.open(new File(LucensIndexService.DEFAULT_INDEX_FILE));
		IndexReader reader = IndexReader.open(dir,true);
		IndexReadCom rc=new IndexReadCom(reader);
		return rc;
	}
	
	 public void reOpen(){
		 
	 }

}
