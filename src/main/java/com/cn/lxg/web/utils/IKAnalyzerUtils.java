package com.cn.lxg.web.utils;

import com.cn.lxg.web.bean.SegmenterDTO;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import java.io.IOException;
import java.io.StringReader;
import java.util.*;

/**
 * @Description IKAnalyzer 分词
 * @Author robin
 * @Date 2019/2/18 10:54
 */
public class IKAnalyzerUtils {

    /**
     * 获取文本词频倒序分词列表
     * @param text 文本
     * @return
     * @throws IOException
     */
    public static List<Map.Entry<String,Integer>> getSegmenterList(String text) throws IOException {
        Map<String, Integer> textDef = getTextDef(text);
        if(textDef == null || textDef.isEmpty()){
            return new ArrayList<>(0);
        }
        return sortSegmenterResult(textDef);
    }

    /**
     * 获取文本词频倒序分词列表
     * @param text 文本
     * @return
     * @throws IOException
     */
    public static List<SegmenterDTO> getSegmenterDTOList(String text) throws IOException {
        Map<String, Integer> textDef = getTextDef(text);
        if(textDef != null && !textDef.isEmpty()){
            List<Map.Entry<String, Integer>> entries = sortSegmenterResult(textDef);
            if(entries != null && !entries.isEmpty()){
                List<SegmenterDTO> list = new ArrayList<>();
                SegmenterDTO segmenterDTO = null;
                for(Map.Entry<String, Integer> map : entries){
                    segmenterDTO = new SegmenterDTO();
                    segmenterDTO.setWord(map.getKey());
                    segmenterDTO.setFrequency(map.getValue());
                    list.add(segmenterDTO);
                }
            }
        }
        return new ArrayList<>(0);
    }


    /**
     * 分词/词频统计
     * @param text 文本内容
     * @return
     * @throws IOException
     */
    private static Map<String,Integer> getTextDef(String text) throws IOException {
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
     * @param wordsFrenMaps 词语，词频 map集合
     * @return
     */
    private static List<Map.Entry<String,Integer>> sortSegmenterResult(Map<String,Integer> wordsFrenMaps){
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
