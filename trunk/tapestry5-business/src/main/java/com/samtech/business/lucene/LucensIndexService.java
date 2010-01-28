package com.samtech.business.lucene;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.SimpleAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

/*import com.footmarktech.robot.business.IBusinessService;
import com.footmarktech.robot.business.impl.Rbt2SynonymousWordsService;*/
import com.samtech.database.Rbt2Business;

public class LucensIndexService implements ILuceneIndexService {
   
	//IBusinessService businessService;
	//Rbt2SynonymousWordsService synWordService;
	private static Log log=LogFactory.getLog(LucensIndexService.class);
	private static Object lock=new Object();//锁
	public LucensIndexService(){
		
	}
	 
	public void buildIndex(String indexFile) throws Exception {
		synchronized (lock) {
			IndexWriter writer =null;
			try{
				if(indexFile==null) indexFile=DEFAULT_INDEX_FILE;
				File ifile=new File(indexFile);
				if(!ifile.exists())
			      ifile.mkdirs();
				Directory dir=FSDirectory.open(ifile);
				boolean exist=IndexReader.indexExists(dir);
				if(exist){
					//FIXME
					/*writer = new IndexWriter(dir, new SimpleAnalyzer(), false,IndexWriter.MaxFieldLength.LIMITED);
					List<Rbt2Business> updateBuss = businessService.findAllBusinessNoIndexOrDeleted();	
					boolean needOptimize=false;
					for (Rbt2Business bus : updateBuss) {
						if("Y".equals(bus.getDeleted())){
							//删除
							needOptimize=true;
							Term term=new Term("Id", bus.getId());
							writer.deleteDocuments(term);
							
						}else if(!"Y".equals(bus.getIndexed())){
							//没有被索引，新增或修改情况
							needOptimize=true;
							Term term=new Term("Id", bus.getId());
							Document doc=busToDoc(bus);
							if(doc!=null)
							  writer.updateDocument(term, doc);
							businessService.updateIndexed(bus);
						}
					}
					if(needOptimize)
					  writer.optimize();*/
	
				}else{
					dir.close();
					reBuildIndex(indexFile);
				}
			}catch(Exception e){
				log.error("", e);
				throw e;
			}finally{
				if(writer!=null)try{ writer.close();}catch(Exception e1){}
			}
		}
	}

	public void reBuildIndex(String indexFile) throws Exception {
		synchronized (lock) {
			IndexWriter writer =null;
			try{
				if(indexFile==null) indexFile=DEFAULT_INDEX_FILE;
				
				File ifile=new File(indexFile);
				if(!ifile.exists())
			       ifile.mkdirs();
				
				Directory dir=FSDirectory.open(ifile);
				writer = new IndexWriter(dir, new SimpleAnalyzer(), true,IndexWriter.MaxFieldLength.LIMITED);
				//FIXME
				/*List<Rbt2Business> findAllBusinessNoDeleted = businessService.findAllBusinessNoDeleted();
				if(findAllBusinessNoDeleted!=null){
					for (Rbt2Business bus : findAllBusinessNoDeleted) {
						Document doc=busToDoc(bus);
						if(doc!=null){
						   writer.addDocument(doc);
						   if(!"Y".equals(bus.getIndexed()))
						      businessService.updateIndexed(bus);
						}
					}
				}*/
				writer.optimize();
			}catch(Exception e){
				log.error("", e);
				throw e;
			}finally{
				if(writer!=null)try{ writer.close();}catch(Exception e1){}
			}
		}
	
	}

	public void addIndex(String indexFile) throws Exception {

		throw new  Exception("the addIndex not Implement");

	}
	
	public void deleteIndex(String indexFile) throws Exception {
		throw new  Exception("the deleteIndex not Implement");
	}

	public void updateIndex(String indexFile) throws Exception {
		throw new  Exception("the updateIndex not Implement");

	}
	
	private Document busToDoc(Rbt2Business bus){
		String indexWords = bus.getIndexWords();
		if(StringUtils.isBlank(indexWords)) return null;
/*		indexWords=indexWords.trim();
		indexWords=indexWords.replace('|', ' ');*/
		indexWords=dealIndexWord(indexWords);
		indexWords=indexWords.toLowerCase();//小写;
		
		Document doc=new Document();
		doc.add(new Field("Id",bus.getId(),Field.Store.YES,Field.Index.NOT_ANALYZED));
		doc.add(new Field("IndexWords",indexWords,Field.Store.YES,Field.Index.ANALYZED));
		
		if(StringUtils.isNotBlank(bus.getBizType()))
		   doc.add(new Field("BizType",bus.getBizType(),Field.Store.YES,Field.Index.NO));
		if(StringUtils.isNotBlank(bus.getBizDes()))
			doc.add(new Field("BizDes",bus.getBizDes(),Field.Store.YES,Field.Index.NO));
		if(StringUtils.isNotBlank(bus.getBizUrl()))
			doc.add(new Field("BizUrl",bus.getBizUrl(),Field.Store.YES,Field.Index.NO));
		
		if(StringUtils.isNotBlank(bus.getExcludeWords()))
			doc.add(new Field("ExcludeWords",bus.getExcludeWords(),Field.Store.YES,Field.Index.NO));
		if(StringUtils.isNotBlank(bus.getIncludeWords()))
			doc.add(new Field("IncludeWords",bus.getIncludeWords(),Field.Store.YES,Field.Index.NO));
		if(StringUtils.isNotBlank(bus.getBizName()))
			doc.add(new Field("BizName",bus.getBizName(),Field.Store.YES,Field.Index.NO));
		if(StringUtils.isNotBlank(bus.getBizQuestion()))
			doc.add(new Field("BizQuestion",bus.getBizQuestion(),Field.Store.YES,Field.Index.NO));


		return doc;
	}

	private String dealIndexWord(String indexWords){
		indexWords=indexWords.trim();
		//indexWords=indexWords.replace('|', ' ');
		String[] split = indexWords.split("\\|");
		StringBuffer result=new StringBuffer();
		//FIXME
		/*Map<String, String> synWords = synWordService.findAllWords(true);
		for (String key : split) {
			if(StringUtils.isNotBlank(key)){
				String y = synWords.get(key);
				if(y!=null) result.append(y); else result.append(key);
				result.append(" ");
			}
		}*/
		return result.toString().trim();
	}
	
	/*public IBusinessService getBusinessService() {
		return businessService;
	}

	public void setBusinessService(IBusinessService businessService) {
		this.businessService = businessService;
	}

	public Rbt2SynonymousWordsService getSynWordService() {
		return synWordService;
	}

	public void setSynWordService(Rbt2SynonymousWordsService synWordService) {
		this.synWordService = synWordService;
	}*/

}
