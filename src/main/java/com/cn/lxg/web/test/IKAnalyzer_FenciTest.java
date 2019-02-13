package com.cn.lxg.web.test;

import com.alibaba.fastjson.JSONObject;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import java.io.IOException;
import java.io.StringReader;
import java.util.*;

/**
 * @Description 【中文分词】使用 IKAnalyzer 分词统计词频
 * @Author robin
 * @Date 2019/2/13 16:30
 */
public class IKAnalyzer_FenciTest {
    public static void main(String[] args) {
        try {
            long l = System.currentTimeMillis();
            String text = "我是中国人，我爱中国。我是中国人，我爱中国。我是中国人，我爱中国。我是中国人，我爱中国。我是中国人，我爱中国。王者荣耀是最好玩的游戏，中华人名共和国国旗";
            Map<String, Integer> textDef = getTextDef(text);
            System.out.println(textDef);
            List<Map.Entry<String, Integer>> entries = sortSegmenterResult(textDef);
            System.out.println(JSONObject.toJSONString(entries));
            System.out.println((System.currentTimeMillis() - l) + " ms");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 分词/词频统计
     * @param text
     * @return
     * @throws IOException
     */
    public static Map<String,Integer> getTextDef(String text) throws IOException {
        Map<String,Integer> wordsFren = new HashMap<>();
        IKSegmenter ikSegmenter = new IKSegmenter(new StringReader(text),true);
        Lexeme lexeme;
        while ((lexeme = ikSegmenter.next()) != null){
            if(lexeme.getLexemeText().length()>1){
                if(wordsFren.containsKey(lexeme.getLexemeText())){
                    wordsFren.put(lexeme.getLexemeText(),wordsFren.get(lexeme.getLexemeText()) + 1);
                }else{
                    wordsFren.put(lexeme.getLexemeText(),1);
                }
            }
        }
        return wordsFren;
    }

    /**
     * 按照词频由高到低排序
     * @param wordsFrenMaps
     * @return
     */
    public static List<Map.Entry<String,Integer>> sortSegmenterResult(Map<String,Integer> wordsFrenMaps){
        List<Map.Entry<String,Integer>> wordsFrenList = new ArrayList<>(wordsFrenMaps.entrySet());
        Collections.sort(wordsFrenList, new Comparator<Map.Entry<String, Integer>>() {
                    @Override
                    public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                        return o2.getValue() - o1.getValue();
                    }
                }
        );
        return wordsFrenList;
    }
}
