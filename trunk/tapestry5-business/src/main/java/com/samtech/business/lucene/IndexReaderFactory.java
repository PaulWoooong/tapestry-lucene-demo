package com.samtech.business.lucene;

import java.io.File;

import org.apache.commons.pool.PoolableObjectFactory;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import com.samtech.business.lucene.LucensIndexService;

public class IndexReaderFactory implements PoolableObjectFactory {

	public void activateObject(Object obj) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void destroyObject(Object obj) throws Exception {
		((IndexReadCom)obj).getReader().close();
		
	}

	public Object makeObject() throws Exception {
		Directory dir=FSDirectory.open(new File(LucensIndexService.DEFAULT_INDEX_FILE));
		IndexReader reader = IndexReader.open(dir,true);
		IndexReadCom rc=new IndexReadCom(reader);
		return rc;
	}

	public void passivateObject(Object obj) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public boolean validateObject(Object obj) {
		return true;
	}
	
	public static IIndexReadPool getIndexReadPool(){
		return IndexReadPoolApacheImpl.getInstance();
	}

}
