package com.samtech.piv;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

//import com.footmarktech.robot.business.IRobotWordService;
import com.samtech.database.Rbt2Wordorphrase;

public class EnWordMapFactory {
   /* private IRobotWordService robotService;

    public IRobotWordService getRobotService() {
        return robotService;
    }

    public void setRobotService(IRobotWordService robotService) {
        this.robotService = robotService;
    }*/

    public Map getEnWordMap() throws Exception {
    	Map resultMap = new HashMap(500);
    	//FIXME
       /* List list = getRobotService().findAllEnWords();
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
            	Rbt2Wordorphrase smWord = (Rbt2Wordorphrase) list.get(i);
                String key = StringUtils.trim(smWord.getContent());
                if(key!=null) {
                	key=key.toUpperCase();
                	resultMap.put(key, smWord);
                }
            }
        }*/
        return resultMap;
    }
}
