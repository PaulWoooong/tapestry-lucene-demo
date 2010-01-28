package com.samtech.business.lucene;


public interface IIndexReadPool {
		
	  public IndexReadCom getIndexRead() throws Exception;

	  public void closeIndexRead(IndexReadCom p) throws Exception;
	  
	  
	  public void closePool() throws Exception;
	  
	  /**
	   * 重新打开IndexReader,对于正在使用中的，则在关闭<closeIndexRead>的时候，重新打开。
	   * 对于处于空闲状态的，则立即重新打开。
	   */
	  public void reOpen();
}
