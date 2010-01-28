package com.samtech.business.lucene;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

//import com.footmarktech.robot.Word;
import com.samtech.database.Rbt2Business;
//import com.footmarktech.robot.participle.RobotParticiple;
import com.samtech.piv.Word;
import com.samtech.piv.participle.RobotParticiple;

public class LuceneServiceFace {
	
	/**
	 * 根据输入信息查询索引信息
	 * @param input
	 * @return
	 * @throws Exception
	 */
	public List<String> queryIndex(String input) throws Exception {
		RobotParticiple participle= new RobotParticiple();
		LuceneSearchService search = new LuceneSearchService();
		Word[] words = participle.participlePivotal(input);
		List<String> indexWords=new ArrayList<String>();
		if(words!=null){
			for(int i=0;i<words.length;i++)
				indexWords.add(words[i].getKey());
		}
		List<String> result=new ArrayList<String>();
		if(indexWords.size()>0){
			List<Rbt2Business> si = search.searchIndex(indexWords);
			for (Rbt2Business rbt2Business : si) {
				//WS
				String que=rbt2Business.getBizQuestion();
				if(StringUtils.isNotBlank(que) && Word.BusinessType_Normal.equals(rbt2Business.getBizType()))
					result.add(que);
			}
			for (Rbt2Business rbt2Business : si) {
				//非WS
				String que=rbt2Business.getBizQuestion();
				if(StringUtils.isNotBlank(que) && !Word.BusinessType_Normal.equals(rbt2Business.getBizType()))
					result.add(que);
			}
		}
		return result;
	}
}
