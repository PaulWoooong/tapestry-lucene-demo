package com.samtech.business.lucene;

import java.util.List;

import com.samtech.database.Rbt2Business;



/**
 *查询索引服务
 * @author ympeng
 *
 */
public interface ILuceneSearchService {
	/**
	 * 根据索引关键字，使用或关系查找索引
	 * @param indexWords
	 * @return
	 */
	public List<Rbt2Business>  searchIndex(List<String> indexWords) throws Exception;
}
