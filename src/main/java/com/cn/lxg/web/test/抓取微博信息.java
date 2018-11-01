package com.cn.lxg.web.test;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cn.lxg.web.es.RestHttp;

/**
 * Created by robin on 2018/9/14.
 */
public class 抓取微博信息 {
    public static void main(String[] args) {
        String result =  RestHttp.getInstance().get("https://m.weibo.cn/api/container/getIndex?type=uid&value=6182376050&containerid=1076036182376050&page=2");
        JSONObject jsonObject = JSONObject.parseObject(result);
        JSONObject data = jsonObject.getJSONObject("data");
        JSONArray cards = data.getJSONArray("cards");
        for(Object card : cards){
            JSONObject json = (JSONObject) card;
            int card_type = json.getInteger("card_type");
            JSONObject mblog = json.getJSONObject("mblog");
            if(card_type == 9 && mblog.getInteger("weibo_position") == 1){
                String idstr = mblog.getString("idstr");
                System.out.println("自增标识："+idstr);
                JSONObject page_info = mblog.getJSONObject("page_info");
                //String text = mblog.getString("text");
                //System.out.println("文本内容："+text.replaceAll("<a.*?</a>", "").replaceAll("<span.*?</span>", "").replaceAll(" +","").replaceAll("<br/>",""));
                String type = page_info.getString("type");
                String page_title = page_info.getString("page_title");
                System.out.println("标题："+page_title);
                if("webpage".equals(type) || "topic".equals(type)){
                    JSONArray pics = mblog.getJSONArray("pics");
                    for(Object pic : pics){
                        JSONObject json1 = (JSONObject) pic;
                        String url = json1.getString("url");
                        System.out.println(url);
                    }
                }
                else if("video".equals(type)){
                    String video = page_info.getJSONObject("media_info").getString("stream_url");
                    System.out.println(video);
                }
            }
        }
    }
}
