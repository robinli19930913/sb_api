package com.cn.lxg.web.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author guozijian
 * @date 2016/11/9 21:13
 */
@Slf4j
public class Tools {
    /**
     * 获取富文本里面所有<img> url 属性值的集合
     * @param value 富文本内容
     * @return
     */
    public static List<String> getRichTextImgUrls(String value) {
        List<String> pics = new ArrayList<>();
        if(StringUtils.isBlank(value)){
            return pics;
        }
        String img = "";
        Pattern p_image;
        Matcher m_image;
        String regEx_img = "<img.*src\\s*=\\s*(.*?)[^>]*?>";
        p_image = Pattern.compile
                (regEx_img, Pattern.CASE_INSENSITIVE);
        m_image = p_image.matcher(value);
        while (m_image.find()) {
            // 得到<img />数据
            img = m_image.group();
            // 匹配<img>中的src数据
            Matcher m = Pattern.compile("src\\s*=\\s*\"?(.*?)(\"|>|\\s+)").matcher(img);
            while (m.find()) {
                pics.add(m.group(1));
            }
        }
        return pics;
    }

    /**
     * get地址拼接参数
     * @param url
     * @param param
     * @return
     */
    public static String addUrlParam(String url, String param) {
        String result = "";
        if (StringUtils.isNotBlank(url)) {
            result = url;
            if (StringUtils.isNotBlank(param)) {
                if (result.indexOf("?") > 0) {
                    result += "&" + param;
                } else {
                    result += "?" + param;
                }
            }
        }
        return result;
    }

    /**
     * 校验用户密码格式
     *
     * @param password
     * @return
     */
    public static boolean isPassword(String password) {
        return StringUtils.isNotBlank(password) && Pattern.matches("^[A-Za-z0-9_]{6,16}$", password);
    }

    /**
     * Integer 是否相同
     *
     * @param int1 已判null
     * @param int2 已判null
     * @return boolean
     */
    public static boolean intEquals(Integer int1, Integer int2) {
        return int1 != null && int2 != null && int1.equals(int2);
    }

    /**
     * Integer 是否相同
     *
     * @param long1 已判null
     * @param long2 已判null
     * @return boolean
     */
    public static boolean longEquals(Long long1, Long long2) {
        return long1 != null && long2 != null && long1.equals(long2);
    }

    /**
     * 修剪字符串
     * 1.去掉字符串前后的空白字符
     * 2.去掉 换页符,换行符,回车符,制表符,垂直制表符
     *
     * @param str
     * @return
     */
    public static String trim(String str) {
        return StringUtils.removeAll(StringUtils.trim(str), "\\f\\n\\r\\t\\v");
    }

    /**
     * 获取html字符串
     * @param inputString
     * @return
     */
    public static String toNoHtml(String inputString) {
        if (StringUtils.isBlank(inputString)) {
            return inputString;
        }
        String htmlStr = inputString;
        String textStr = "";
        Pattern p_script;
        Matcher m_script;
        Pattern p_style;
        Matcher m_style;
        Pattern p_html;
        Matcher m_html;

        Pattern p_html1;
        Matcher m_html1;

        try {
            String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; //定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script> }
            String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; //定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style> }
            String regEx_html = "<[^>]+>"; //定义HTML标签的正则表达式
            String regEx_html1 = "<[^>]+";
            p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
            m_script = p_script.matcher(htmlStr);
            htmlStr = m_script.replaceAll(""); //过滤script标签

            p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
            m_style = p_style.matcher(htmlStr);
            htmlStr = m_style.replaceAll(""); //过滤style标签

            p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
            m_html = p_html.matcher(htmlStr);
            htmlStr = m_html.replaceAll(""); //过滤html标签

            p_html1 = Pattern.compile(regEx_html1, Pattern.CASE_INSENSITIVE);
            m_html1 = p_html1.matcher(htmlStr);
            htmlStr = m_html1.replaceAll(""); //过滤html标签

            textStr = htmlStr;
        } catch (Exception e) {
            log.error("", e);
        }
        return textStr;//返回文本字符串
    }

    /**
     * 转格式
     * @param date
     * @return
     */
    public static Date getDateYYYYMMDD(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取最大值
     * @param a
     * @param b
     * @return
     */
    public static BigDecimal max(BigDecimal a, BigDecimal b) {
        BigDecimal max = null;
        if (a == null) {
            max = b;
        } else if (b == null) {
            max = a;
        } else {
            switch (a.compareTo(b)) {
                case 1:
                    max = a;
                    break;
                case -1:
                    max = b;
                    break;
                default:
                    max = a;
            }
        }
        return max;
    }

    /**
     * 获取小值
     * @param a
     * @param b
     * @return
     */
    public static BigDecimal min(BigDecimal a, BigDecimal b) {
        BigDecimal min = null;
        if (a == null) {
            min = b;
        } else if (b == null) {
            min = a;
        } else {
            switch (a.compareTo(b)) {
                case 1:
                    min = b;
                    break;
                case -1:
                    min = a;
                    break;
                default:
                    min = a;
            }
        }
        return min;
    }

    /**
     * 获取指定时间是当前时间多长时间前
     * @param date
     * @return
     */
    public static String getDateStrDistanceNow(Date date) {
        String str = "";
        if (date != null) {
            long d = System.currentTimeMillis() - date.getTime();
            if (d < 60 * 1000) {
                str = d / 1000 + "秒前";
            } else if (d < 60 * 60 * 1000) {
                str = d / 60 / 1000 + "分钟前";
            } else if (d < 24 * 60 * 60 * 1000) {
                str = d / 60 / 60 / 1000 + "小时前";
            } else if (d < 7 * 24 * 60 * 60 * 1000) {
                str = d / 24 / 60 / 60 / 1000 + "天前";
            } else {
                Calendar now = Calendar.getInstance();
                Calendar param = Calendar.getInstance();
                param.setTime(date);
                if (now.get(Calendar.YEAR) > param.get(Calendar.YEAR)) {
                    str = DateFormatUtils.format(date, "yyyy/MM/dd");
                } else {
                    str = DateFormatUtils.format(date, "MM/dd");
                }
            }
        }
        return str;
    }

    /**
     * createAt 转为 create_at
     * @param dtoField .
     * @return .
     */
    public static String dtoFieldChangeToDbField(String dtoField) {
        // 大写字母转_小写字母
        Pattern p = Pattern.compile("[A-Z]");
        StringBuffer buffer = new StringBuffer(dtoField);
        Matcher mc = p.matcher(dtoField);
        int i = 0;
        while (mc.find()) {
            buffer.replace(mc.start() + i, mc.end() + i, "_" + mc.group().toLowerCase());
            i++;
        }
        if ('_' == buffer.charAt(0)) {
            buffer.deleteCharAt(0);
        }
        return buffer.toString();
    }

    /**
     * 判断list 是否为null 并且长度大于0
     * @param list
     * @return
     */
    public static Boolean listIsNotBlank(List list){
        return list != null && !list.isEmpty();
    }

    /**
     * 求百分比 - 向下取整
     * @param molecule 分子
     * @param denominator 分母
     * @param scale 小数点位数
     * @return
     */
    public static BigDecimal percentDown(Integer molecule,Integer denominator,Integer scale){
        if(molecule == null || denominator == null || denominator.intValue() == 0){
            return BigDecimal.ZERO;
        }
        if(scale == null){
            scale = 0;
        }
        BigDecimal m = new BigDecimal(molecule).multiply(new BigDecimal(100));
        BigDecimal d = new BigDecimal(denominator);
        return m.divide(d,scale,BigDecimal.ROUND_DOWN);
    }

    /**
     * 求百分比 - 向上取整
     * @param molecule 分子
     * @param denominator 分母
     * @param scale 小数点位数
     * @return
     */
    public static BigDecimal percentUp(Integer molecule,Integer denominator,Integer scale){
        if(molecule == null || denominator == null || denominator.intValue() == 0){
            return BigDecimal.ZERO;
        }
        if(scale == null){
            scale = 0;
        }
        BigDecimal m = new BigDecimal(molecule).multiply(new BigDecimal(100));
        BigDecimal d = new BigDecimal(denominator);
        return m.divide(d,scale,BigDecimal.ROUND_UP);
    }

    /**
     * 判断list
     * @param list
     * @return
     */
    public static Boolean listIsBlank(List list){
        return list == null || list.isEmpty();
    }

    /**
     * 判断key 是否存在map中
     * @param map
     * @param key
     * @return
     */
    public static Boolean mapContainsKey(Map map,Object key){
        return map != null && key != null && map.containsKey(key);
    }

    /**
     * 判断数组是否为空
     * @param object
     * @return
     */
    public static Boolean arrayIsNotBlank(Object[] object){
        return object != null && object.length > 0;
    }

    /**
     * 判断数组是否为空
     * @param object
     * @return
     */
    public static Boolean arrayIsBlank(Object[] object){
        return object == null && object.length < 1;
    }

    public static void main(String[] args) {
        char[] chars = {'1','@','$'};
        char c = '@';
        boolean contains = ArrayUtils.contains(chars, c);
        System.out.println(contains);
    }
}
