/*
 * 创建日期 2008-10-27
 * 
 * The  tech Software License v1.0 content.
 * Copyright (c) 2006  
 * All rights reserved
 * For more information on , please
 * see 
 */

package com.samtech.piv;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

//import com.footmarktech.robot.database.Rbt2Signification;
import com.samtech.database.Rbt2Wordorphrase;

/**
 * @author ympeng
 * 
 * 简单中文姓名抽取.
 */

public class ChinaNameTool {

    private static Log log = LogFactory.getLog(ChinaNameTool.class);

    private static ChinaNameTool tool = null;

    private ChinaNameTool() {

    }

    /**
     * 
     * @param name
     *            姓氏
     * @return
     */
    public boolean containSurName(String name) {
        if (name == null)
            return false;
        List ls = WordTool.getWordsFromSentence(name, SurNameCharNodeTree
                .getCharNodeTree());
        return ls != null && ls.size() > 0;
    }

    /**
     * 查找第一个姓名
     * 
     * @param str
     * @return
     */

    public String findChinaName(String str) {
        try {
            if (str == null)
                return null;
            List ls = WordTool.getWordsFromSentence(str, SurNameCharNodeTree
                    .getCharNodeTree());
            Map area = findFliterData(str);
            String sur = null;
            String reg = null;
            String resule = null;
            for (int i = 0; i < ls.size(); i++) {
                sur = ((Rbt2Wordorphrase) ls.get(i)).getContent();
                reg = sur + "[\u4E00-\u9FA5]{1,2}[^\u4E00-\u9FA5]";
                resule = null;
                Pattern p = Pattern.compile(reg);
                Matcher matcher = p.matcher(str);
                boolean f = false;
                while (matcher.find()) {
                    f = true;
                    resule = matcher.group().substring(0,
                            matcher.group().length() - 1);
                    if (area.containsKey(resule)) {// 为地名
                        resule = null;
                    } else {
                        break;
                    }
                }
                if (!f) {
                    reg = sur + "[\u4E00-\u9FA5]{1,2}";
                    p = Pattern.compile(reg);
                    matcher = p.matcher(str);
                    if (matcher.find()) {
                        String k = matcher.group();
                        if (str.endsWith(k) && !area.containsKey(k)) {// 以姓名结束
                            resule = k;
                        }
                    }
                }
                if (resule != null)
                    return resule;
            }
        } catch (Exception e) {
            log.error(e);
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取地名
     * 
     * @param src
     * @return
     */
    public Map findArea(String src) {
        List ls = SmsWordTool.getWordsFromSentence(src);
        Map areas = new HashMap();
        if (ls != null && ls.size() > 0) {
            for (int i = 0; i < ls.size(); i++) {
            	Rbt2Wordorphrase word = (Rbt2Wordorphrase) ls.get(i);
            	//FIXME
            	/*if(word.getSignificList()!=null){
            		for(Rbt2Signification sign:word.getSignificList()){
                        if (sign != null
                                && sign.getType().intValue() == Rbt2Signification.CHAR_NODE_TYPE_AERA) {
                            areas.put(word.getContent(), null);
                        }
            		}
            	}*/

            }
        }
        return areas;

    }

    /**
     * 需过滤的数据
     * 
     * @param src
     * @return
     */
    public Map findFliterData(String src) {
        Map mp = findArea(src);
        mp.put("机票", null);
        mp.put("多少", null);
        return mp;
    }

    /**
     * 查找多个姓名
     * 
     * @REVIEW:建议不要使用该方法，当名字中包含多个姓氏的情况会返回多个姓名。
     * @param str
     * @return
     */

    public String[] findChinaNames(String str) {
        try {
            if (str == null)
                return null;
            List ls = WordTool.getWordsFromSentence(str, SurNameCharNodeTree
                    .getCharNodeTree());
            String sur = null;
            String reg = null;
            List names = new ArrayList();
            for (int i = 0; i < ls.size(); i++) {
                sur = ((Rbt2Wordorphrase) ls.get(i)).getContent();
                reg = sur + "[\u4E00-\u9FA5]{1,2}[^\u4E00-\u9FA5]";
                Pattern p = Pattern.compile(reg);
                Matcher matcher = p.matcher(str);
                boolean flag = false;
                while (matcher.find()) {
                    flag = true;
                    names.add(matcher.group().substring(0,
                            matcher.group().length() - 1));
                }
                if (!flag) {
                    reg = sur + "[\u4E00-\u9FA5]{1,2}";
                    p = Pattern.compile(reg);
                    matcher = p.matcher(str);
                    if (matcher.find()) {
                        String k = matcher.group();
                        if (str.endsWith(k)) {// 以姓名结束
                            names.add(k);
                        }
                    }
                }

            }
            return (String[]) names.toArray(new String[names.size()]);
        } catch (Exception e) {
            log.error(e);
            e.printStackTrace();
        }
        return null;
    }

    public synchronized static ChinaNameTool getInstance() {
        if (tool == null) {
            tool = new ChinaNameTool();
        }
        return tool;
    }

    public static void main(String[] args) {
        ChinaNameTool tool = ChinaNameTool.getInstance();
        System.out.println(tool.containSurName("彭"));
        List ls = new ArrayList();
        ls.add("1机票 你好彭艳 12344  彭剑11111");
        ls.add("2你好彭艳或呵呵vds");
        ls.add("3你好彭艳或呵vds");
        ls.add("4你好彭艳或1111");
        ls.add("5你好彭艳或sdfs");
        ls.add("6你好彭艳或-好好");
        ls.add("7你好彭艳或,好好");
        ls.add("8你好彭艳或*");
        ls.add("9你好彭艳或");
        ls.add("10你好彭艳或!");

        ls.add("你好欧阳剑test");
        ls.add("你好欧阳剑");
        ls.add("你好欧阳十客");
        ls.add("你好欧阳十客狗");
        ls.add("你好，帮我检查下机票的正确性，姓名李白，票号123456789");
        ls.add("你好，帮我检查下机票的正确性，姓名李建，票号123456789");
        ls.add("YZ杨泽李,7842423921250");
        ls.add("谢谢温馨提示！我就是赵汉生，票号为7842423461567的旅客。现在能告诉我具体变更到何时吗");
        ls.add("YZ荣长明");
        ls.add("YZ姜丹，C0906090012009");

        for (int i = 0; i < ls.size(); i++) {
            String input = (String) ls.get(i);
            System.out.println(input + "=====" + tool.findChinaName(input));
        }
        System.out.println("*************************");
        for (int i = 0; i < ls.size(); i++) {
            String input = (String) ls.get(i);
            String[] names = tool.findChinaNames(input);
            if (names != null && names.length > 0) {
                String r = input + "=====";
                for (int j = 0; j < names.length; j++) {
                    r = r + "," + names[j];
                }
                System.out.println(r);
            } else {
                System.out.println(input + "=====null");
            }

        }
    }
}
