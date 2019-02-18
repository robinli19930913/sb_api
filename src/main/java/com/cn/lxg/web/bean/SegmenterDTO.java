package com.cn.lxg.web.bean;

/**
 * @Description 分词实体类
 * @Author robin
 * @Date 2019/2/18 17:00
 */
public class SegmenterDTO {
    /**
     * 词语
     */
    private String word;
    /**
     * 词频
     */
    private Integer frequency;
    /**
     * 权重
     */
    private Double weight;
    /**
     * 权重
     */
    private String weightStr;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Integer getFrequency() {
        return frequency;
    }

    public void setFrequency(Integer frequency) {
        this.frequency = frequency;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getWeightStr() {
        return weightStr;
    }

    public void setWeightStr(String weightStr) {
        this.weightStr = weightStr;
    }
}
