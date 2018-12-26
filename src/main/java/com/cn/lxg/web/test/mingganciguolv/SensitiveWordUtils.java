package com.cn.lxg.web.test.mingganciguolv;

import java.util.*;

/**
 * @Description 敏感词过滤工具类
 * @Author robin
 * @Date 2018/12/26 9:54
 */
public class SensitiveWordUtils {

    //最小匹配规则
    public static final Integer MIN_MATCH_TYPE = 1;
    //最大匹配规则
    public static final Integer MAX_MATCH_TYPE = 2;

    /**
     * 判断文字是否包含敏感字符
     * @author lixuegen
     * @date 2018年12月25日 下午4:28:30
     * @param txt  文字
     * @param list  敏感词
     * @return 若包含返回true，否则返回false
     * @version 1.0
     */
    public static boolean isContaintSensitiveWord(String txt,List<String> list){
        Set<String> set = readSensitiveWordFile(list);
        HashMap sensitiveWordMap = addSensitiveWordToHashMap(set);
        return isContaintSensitiveWord(txt,sensitiveWordMap,1);
    }

    /**
     * 判断文字是否包含敏感字符
     * @author lixuegen
     * @date 2018年12月25日 下午4:28:30
     * @param txt  文字
     * @param matchType
     * @param list  敏感词
     * @return 若包含返回true，否则返回false
     * @version 1.0
     */
    public static boolean isContaintSensitiveWord(String txt,int matchType,List<String> list){
        Set<String> set = readSensitiveWordFile(list);
        HashMap sensitiveWordMap = addSensitiveWordToHashMap(set);
        return isContaintSensitiveWord(txt,sensitiveWordMap,matchType);
    }

    /**
     * 判断文字是否包含敏感字符
     * @author lixuegen
     * @date 2018年12月25日 下午4:28:30
     * @param txt  文字
     * @param sensitiveWordMap  敏感词库
     * @return 若包含返回true，否则返回false
     * @version 1.0
     */
    private static boolean isContaintSensitiveWord(String txt,HashMap sensitiveWordMap){
        return isContaintSensitiveWord(txt,sensitiveWordMap,1);
    }

    /**
     * 判断文字是否包含敏感字符
     * @author lixuegen
     * @date 2018年12月25日 下午4:28:30
     * @param txt  文字
     * @param sensitiveWordMap  敏感词库
     * @param matchType  匹配规则 1：最小匹配规则，2：最大匹配规则
     * @return 若包含返回true，否则返回false
     * @version 1.0
     */
    private static boolean isContaintSensitiveWord(String txt,HashMap sensitiveWordMap,int matchType){
        boolean flag = false;
        for(int i = 0,length = txt.length(); i < length; i++){
            //判断是否包含敏感字符
            int matchFlag = checkSensitiveWord(txt, i, matchType,sensitiveWordMap);
            //大于0存在，返回true
            if(matchFlag > 0){
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 获取文字中的敏感词
     * @author lixuegen
     * @date 2018年12月25日 下午5:10:52
     * @param txt 文字
     * @param list 敏感词
     * @return
     * @version 1.0
     */
    public static Set<String> getSensitiveWord(String txt,List<String> list){
        Set<String> set = readSensitiveWordFile(list);
        HashMap sensitiveWordMap = addSensitiveWordToHashMap(set);
        return getSensitiveWord(txt ,1,sensitiveWordMap);
    }

    /**
     * 获取文字中的敏感词
     * @author lixuegen
     * @date 2018年12月25日 下午5:10:52
     * @param txt 文字
     * @param set 敏感词
     * @return
     * @version 1.0
     */
    public static Set<String> getSensitiveWord(String txt,Set<String> set){
        HashMap sensitiveWordMap = addSensitiveWordToHashMap(set);
        return getSensitiveWord(txt ,1,sensitiveWordMap);
    }
    /**
     * 获取文字中的敏感词
     * @author lixuegen
     * @date 2018年12月25日 下午5:10:52
     * @param txt 文字
     * @param matchType 匹配规则&nbsp;1：最小匹配规则，2：最大匹配规则
     * @param list 敏感词
     * @return
     * @version 1.0
     */
    public static Set<String> getSensitiveWord(String txt,int matchType,List<String> list){
        Set<String> set = readSensitiveWordFile(list);
        HashMap sensitiveWordMap = addSensitiveWordToHashMap(set);
        return getSensitiveWord(txt ,matchType,sensitiveWordMap);
    }


    /**
     * 获取文字中的敏感词
     * @author lixuegen
     * @date 2018年12月25日 下午5:10:52
     * @param txt 文字
     * @param matchType 匹配规则&nbsp;1：最小匹配规则，2：最大匹配规则
     * @return
     * @version 1.0
     */
    private static Set<String> getSensitiveWord(String txt , int matchType,HashMap sensitiveWordMap){
        Set<String> sensitiveWordList = new HashSet<String>();
        for(int i = 0 , length = txt.length(); i < length ; i++){
            //判断是否包含敏感字符
            int index = checkSensitiveWord(txt, i, matchType,sensitiveWordMap);
            //存在,加入list中
            if(index > 0){
                sensitiveWordList.add(txt.substring(i, i+index));
                //减1的原因，是因为for会自增
                i = i + index - 1;
            }
        }
        return sensitiveWordList;
    }
    /**
     * 替换敏感字字符
     * @author lixuegen
     * @date 2018年12月25日 下午5:12:07
     * @param txt
     * @param replaceChar 替换字符，默认*
     * @param list 敏感词
     * @version 1.0
     */
    public static String replaceSensitiveWord(String txt,String replaceChar,List<String> list){
        Set<String> set = readSensitiveWordFile(list);
        HashMap sensitiveWordMap = addSensitiveWordToHashMap(set);
        return replaceSensitiveWord(txt,1,replaceChar,sensitiveWordMap);
    }

    /**
     * 替换敏感字字符
     * @author lixuegen
     * @date 2018年12月25日 下午5:12:07
     * @param txt
     * @param matchType
     * @param replaceChar 替换字符，默认*
     * @param list 敏感词
     * @version 1.0
     */
    public static String replaceSensitiveWord(String txt,int matchType,String replaceChar,List<String> list){
        Set<String> set = readSensitiveWordFile(list);
        HashMap sensitiveWordMap = addSensitiveWordToHashMap(set);
        return replaceSensitiveWord(txt,matchType,replaceChar,sensitiveWordMap);
    }


    /**
     * 替换敏感字字符
     * @author lixuegen
     * @date 2018年12月25日 下午5:12:07
     * @param txt
     * @param matchType
     * @param replaceChar 替换字符，默认*
     * @param sensitiveWordMap 敏感词库
     * @version 1.0
     */
    private static String replaceSensitiveWord(String txt,int matchType,String replaceChar,HashMap sensitiveWordMap){
        String resultTxt = txt;
        //获取所有的敏感词
        Set<String> set = getSensitiveWord(txt, matchType,sensitiveWordMap);
        Iterator<String> iterator = set.iterator();
        String word = null;
        String replaceString = null;
        while (iterator.hasNext()) {
            word = iterator.next();
            replaceString = getReplaceChars(replaceChar, word.length());
            resultTxt = resultTxt.replaceAll(word, replaceString);
        }
        return resultTxt;
    }

    /**
     * 获取替换字符串
     * @author lixuegen
     * @date 2018年12月25日 下午5:21:19
     * @param replaceChar
     * @param length
     * @return
     * @version 1.0
     */
    private static String getReplaceChars(String replaceChar,int length){
        String resultReplace = replaceChar;
        for(int i = 1 ; i < length ; i++){
            resultReplace += replaceChar;
        }
        return resultReplace;
    }

    /**
     * 检查文字中是否包含敏感字符，检查规则如下：<br>
     * @author lixuegen
     * @date 2018年12月25日 下午4:31:03
     * @param txt
     * @param beginIndex
     * @param matchType
     * @return，如果存在，则返回敏感词字符的长度，不存在返回0
     * @version 1.0
     */
    private static int checkSensitiveWord(String txt,int beginIndex,int matchType,HashMap sensitiveWordMap){
        //敏感词结束标识位：用于敏感词只有1位的情况
        boolean  flag = false;
        //匹配标识数默认为0
        int matchFlag = 0;
        char word = 0;
        Map nowMap = sensitiveWordMap;
        for(int i = beginIndex , length = txt.length(); i < length ; i++){
            word = txt.charAt(i);
            //获取指定key
            nowMap = (Map) nowMap.get(word);
            //存在，则判断是否为最后一个
            if(nowMap != null){
                //找到相应key，匹配标识+1
                matchFlag++;
                //如果为最后一个匹配规则,结束循环，返回匹配标识数
                if("1".equals(nowMap.get("isEnd"))){
                    //结束标志位为true
                    flag = true;
                    //最小规则，直接返回,最大规则还需继续查找
                    if(MIN_MATCH_TYPE.intValue() == matchType){
                        break;
                    }
                }
            }
            //不存在，直接返回
            else{
                break;
            }
        }
        //长度必须大于等于1，为词
        if(matchFlag < 2 || !flag){
            matchFlag = 0;
        }
        return matchFlag;
    }


    /**
     * 初始化数据
     * list 转 set
     * @param list
     * @return
     */
    private static Set<String> readSensitiveWordFile(List<String> list){
        Set<String> keyWordSet = null;
        if(list == null || list.isEmpty()){
            return keyWordSet;
        }
        keyWordSet = new HashSet<String>(list);
        return keyWordSet;
    }

    /**
     * 读取敏感词库，将敏感词放入HashSet中，构建一个DFA算法模型：<br>
     * 中 = {
     *      isEnd = 0
     *      国 = {<br>
     *      	 isEnd = 1
     *           人 = {isEnd = 0
     *                民 = {isEnd = 1}
     *                }
     *           男  = {
     *           	   isEnd = 0
     *           		人 = {
     *           			 isEnd = 1
     *           			}
     *           	}
     *           }
     *      }
     *  五 = {
     *      isEnd = 0
     *      星 = {
     *      	isEnd = 0
     *      	红 = {
     *              isEnd = 0
     *              旗 = {
     *                   isEnd = 1
     *                  }
     *              }
     *      	}
     *      }
     * @param keyWordSet  敏感词库
     * @return
     */
    private static HashMap addSensitiveWordToHashMap(Set<String> keyWordSet) {
        HashMap sensitiveWordMap = null;
        if(keyWordSet == null || keyWordSet.isEmpty()){
            return sensitiveWordMap;
        }
        //初始化敏感词容器，减少扩容操作
        sensitiveWordMap = new HashMap(keyWordSet.size());
        String key = null;
        Map nowMap = null;
        Map<String, String> newWorMap = null;
        //迭代keyWordSet
        Iterator<String> iterator = keyWordSet.iterator();
        while(iterator.hasNext()){
            //关键字
            key = iterator.next();
            nowMap = sensitiveWordMap;
            for(int i = 0 , length = key.length(); i < length; i++){
                //转换成char型
                char keyChar = key.charAt(i);
                //获取
                Object wordMap = nowMap.get(keyChar);
                //如果存在该key，直接赋值
                if(wordMap != null){
                    nowMap = (Map) wordMap;
                }
                else{
                    //不存在则，则构建一个map，同时将isEnd设置为0，因为他不是最后一个
                    newWorMap = new HashMap<String,String>();
                    //不是最后一个
                    newWorMap.put("isEnd", "0");
                    nowMap.put(keyChar, newWorMap);
                    nowMap = newWorMap;
                }

                if(i == key.length() - 1){
                    //最后一个
                    nowMap.put("isEnd", "1");
                }
            }
        }
        return sensitiveWordMap;
    }
}
