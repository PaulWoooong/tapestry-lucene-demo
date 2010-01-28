package com.samtech.business.lucene;

/**
 * lucene建立索引服务
 * @author ympeng
 *
 */
public interface ILuceneIndexService {
    
	 public static final String DEFAULT_INDEX_FILE="D:\\lucene\\RobotIndex";
	
	 /**
	  * 建立索引,如果indexFile为空，则使用默认目录
	  * 如果索引存在，则增量添加、删除、修改索引
	  * @throws Exception
	  */
	 public void buildIndex(String indexFile) throws Exception ;
	 
	 /**
	  * 强制重新建立索引，如果indexFile为空，则使用默认目录
	  * @param indexFile
	  * @throws Exception
	  */
	 public void reBuildIndex(String indexFile) throws Exception ;
	 
	 /**
	  * 添加索引
	  * @param indexFile
	  * @throws Exception
	  */
	 public void addIndex(String indexFile) throws Exception ;
	 
	 /**
	  * 修改索引
	  * @param indexFile
	  * @throws Exception
	  */
	 public void updateIndex(String indexFile) throws Exception ;
	 
	 
	 /**
	  * 删除索引
	  * @param indexFile
	  * @throws Exception
	  */
	 public void deleteIndex(String indexFile) throws Exception ;
}
