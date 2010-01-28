package com.samtech.piv;

import java.io.Serializable;
import java.util.List;

import com.samtech.database.Rbt2Business;
//import com.samtech.database.Rbt2Signification;

public class Word  implements Serializable{
    /** 
     * <code>serialVersionUID</code> 的注释
     */
    private static final long serialVersionUID = -205010881238820663L;
    public static final char CHAR_NODE_TYPE_TIME = 1;//时间
    public static final char CHAR_NODE_TYPE_AERA = 2;//地区
    public static final char CHAR_NODE_TYPE_COMMAND = 3;//业务命令
    
    public static final String BusinessType_Normal="WS";//常用业务,比如票价等
    public static final String BusinessType_Question="INFO";//问答业务
    String source;//源串
    String javaSrc;
    boolean isUsed;//是否已经配对上
    int type=-1;
    
    /**业务集合*/
    List<Rbt2Business> businessList;
    /**signifcation集合*/
    //List<Rbt2Signification> significList;
    
    Rbt2Business business;
    //Rbt2Signification singification;
    String key; //关键词
    
    public boolean equals(Object obj) {
        if (null == obj) return false;
        if (!(obj instanceof Word)) return false;
        
        Word word = (Word) obj;
        if(this.getWord()==null || word.getWord()==null) return false;
        return this.getWord().equals(word.getWord());
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getWord() {
       /* if(getSingification()!=null)
            return getSingification().getSignification();*/
        if(getBusiness()!=null)
            return getBusiness().getId();
        return null;
    }


    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getJavaSrc() {
        return javaSrc;
    }

    public void setJavaSrc(String javaSrc) {
        this.javaSrc = javaSrc;
    }

    public boolean isUsed() {
        return isUsed;
    }

    public void setUsed(boolean isUsed) {
        this.isUsed = isUsed;
    }


	public List<Rbt2Business> getBusinessList() {
		return businessList;
	}

	public void setBusinessList(List<Rbt2Business> businessList) {
		this.businessList = businessList;
	}

	/*public List<Rbt2Signification> getSignificList() {
		return significList;
	}

	public void setSignificList(List<Rbt2Signification> significList) {
		this.significList = significList;
	}*/

	public Rbt2Business getBusiness() {
		if(businessList!=null && business==null){
			business=businessList.get(0);
		}
		return business;
	}

	public void setBusiness(Rbt2Business business) {
		this.business = business;
	}

	/*public Rbt2Signification getSingification() {
		if(significList!=null && singification==null){
			Rbt2Signification ss=null;
			for(Rbt2Signification sign:significList){
				if(ss==null){
					ss=sign;
				}else if(ss.getSeqNum()>sign.getSeqNum()){
					ss=sign;
				}		
			}
			singification=ss;
		}
		
		return singification;
	}

	public void setSingification(Rbt2Signification singification) {
		this.singification = singification;
	}
*/
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	@Override
	public int hashCode() {
		StringBuffer buf=new StringBuffer();
		buf.append("key="+this.getKey()).append(";word="+this.getWord());
		return buf.toString().hashCode();
		//return super.hashCode();
	}
}
