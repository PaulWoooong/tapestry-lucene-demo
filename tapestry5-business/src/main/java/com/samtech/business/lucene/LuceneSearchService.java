package com.samtech.business.lucene;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.SimpleAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.util.Version;

import com.samtech.business.lucene.IIndexReadPool;
import com.samtech.business.lucene.IndexReadCom;
import com.samtech.business.lucene.IndexReadPoolApacheImpl;
import com.samtech.business.lucene.IndexReaderFactory;
import com.samtech.database.Rbt2Business;

public class LuceneSearchService implements ILuceneSearchService {

	private static  Log log=LogFactory.getLog(LuceneSearchService.class);
	
	public List<Rbt2Business> searchIndex(List<String> indexWords) throws Exception {
		
		IndexReadCom indexRead = null;
		List<Rbt2Business> result=new ArrayList<Rbt2Business>();
		try{
			indexRead=getPool().getIndexRead();
			IndexReader reader = indexRead.getReader();
			IndexSearcher search=new IndexSearcher(reader);
			Analyzer analyzer = new SimpleAnalyzer();
			QueryParser parser = new QueryParser(Version.LUCENE_CURRENT, "IndexWords", analyzer);
			StringBuffer keys=new StringBuffer();
			for (String key : indexWords) {
				keys.append(key).append(" ");
			}
			Query query = parser.parse(keys.toString().trim());
			TopDocs td = search.search(query, 50);
			int length = td.scoreDocs.length;
			for(int i=0;i<length;i++){
				ScoreDoc d = td.scoreDocs[i];
				Document document = reader.document(d.doc);
				Rbt2Business bus = docToBus(document);
				bus.setScore(d.score);
				result.add(bus);
			}
			search.close();
		}catch(Exception e){	
			log.error("",e);
			throw e;
		}finally{
			if(indexRead!=null){
				getPool().closeIndexRead(indexRead);
			}
		}
		return result;
	}
	
	private Rbt2Business docToBus(Document doc){
		Rbt2Business bus=new Rbt2Business();
		bus.setId(format(doc.getValues("Id")));
		bus.setBizType(format(doc.getValues("BizType")));
		bus.setBizDes(format(doc.getValues("BizDes")));
		bus.setBizUrl(format(doc.getValues("BizUrl")));
		bus.setIndexWords(format(doc.getValues("IndexWords")));
		bus.setIncludeWords(format(doc.getValues("IncludeWords")));
		bus.setBizQuestion(format(doc.getValues("BizQuestion")));

		return bus;
	}
	private String format(String[] s){
		if(s==null || s.length==0) return null;
		return s[0];
	}
	private IIndexReadPool getPool(){
		return IndexReaderFactory.getIndexReadPool();
	}
	

}
