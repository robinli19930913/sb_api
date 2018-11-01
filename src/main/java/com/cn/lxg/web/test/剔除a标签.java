package com.cn.lxg.web.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by robin on 2018/9/14.
 */
public class 剔除a标签 {
    public static void main(String[] args) {
        String text = "<a href=\"https://m.weibo.cn/p/searchall?containerid=231522type%3D1%26q%3D%23%E9%B2%9C%E6%A9%99%E6%9D%A5%E4%BA%86%23%26t%3D10&isnewpage=1&luicode=10000011&lfid=1076036182376050\" data-hide=\"\"><span class=\"surl-text\">#鲜橙来了#</span></a> 鲜橙派 X <a href=\"https://m.weibo.cn/p/index?containerid=10080825f2a1c25c0c072cf16c3e7f39e97e5b&extparam=%E7%8E%8B%E5%86%A0%E9%80%B8&luicode=10000011&lfid=1076036182376050\" data-hide=\"\"><span class='url-icon'><img style='width: 1rem;height: 1rem' src='https://h5.sinaimg.cn/upload/1000/8/2018/04/20/super_default.png'></span><span class=\"surl-text\">王冠逸</span></a>大片儿拍摄花絮上线！拥有治愈系笑容的<a href='/n/王冠逸Lawrence'>@王冠逸Lawrence</a> ，可爱的你们有木有被他的笑容暖到~ <a data-url=\"http://t.cn/Rs8FUpw\" href=\"https://m.weibo.cn/p/index?containerid=2304444282752938537248&url_type=39&object_type=video&pos=1&luicode=10000011&lfid=1076036182376050\" data-hide=\"\"><span class='url-icon'><img style='width: 1rem;height: 1rem' src='https://h5.sinaimg.cn/upload/2015/09/25/3/timeline_card_small_video_default.png'></span><span class=\"surl-text\">鲜橙派的秒拍视频</span></a>";
        System.out.println(text.replaceAll("<a.*?</a>", ""));
    }
}
