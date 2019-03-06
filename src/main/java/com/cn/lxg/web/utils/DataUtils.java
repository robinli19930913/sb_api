package com.cn.lxg.web.utils;

import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * @Description TODO
 * @Author robin
 * @Date 2019/3/6 10:56
 */
public class DataUtils {
    /** 编码格式 */
    private final static String ENCODE = "UTF-8";
    /** 密钥 */
    private final static String API_SECRET = "UTF-8";
    /** 签名超时时间 */
    private final static long TIMEOUT = 1 * 60 * 1000;

    public static void main(String[] args) {
        try{
            //String appKey = getApiKey();
            String appKey = "ac7fa12b-a8bc-44af-8120-03556293677d";
            System.out.println(appKey);
            String apiScrect = getApiScrect(appKey);//1dc3033a98479001fef9ee30645d1dac
            System.out.println(apiScrect);

            Map<String,String> map = new HashMap<>();
            map.put("a","1");
            map.put("b","2");
            map.put("api_key","ac7fa12b-a8bc-44af-8120-03556293677d");
            //map.put("api_secret","1dc3033a98479001fef9ee30645d1dac");
            map.put("api_sign","90958b130213bc1366463071e7df21a1");
            System.out.println(String.valueOf(System.currentTimeMillis()));
            map.put("time_stamp","1551855878761");
//            String apiSign = getApiSign(map);
//            System.out.println(apiSign);
            System.out.println(isApiSign("90958b130213bc1366463071e7df21a1",map));
            System.out.println(isApiSignTimeOut(map));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 签名是否超时
     * @param map
     * @return
     */
    public static boolean isApiSignTimeOut(Map<String,String> map){
        if(map == null || map.isEmpty()){
            return true;
        }
        String time_stamp = map.get("time_stamp");
        return System.currentTimeMillis() > (TIMEOUT + Long.parseLong(time_stamp));
    }

    /**
     * 签名校验
     * @param spiSign
     * @param map
     * @return
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    public static boolean isApiSign(String spiSign,Map<String,String> map) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return StringUtils.isNotBlank(spiSign) && map != null && !map.isEmpty() && spiSign.equals(getApiSign(map));
    }

    public static String getApiKey(){
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
    public static String getApiScrect(String appKey) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return MD5(appKey);
    }

    public static String getApiSign(Map<String, String> paramMap) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        List<String> paramNames = Arrays.asList("api_key","api_sign","time_stamp");
        return getAppSign(paramMap,true,paramNames);
    }

    public static String getAppSign(Map<String, String> paramMap, boolean isLower,List<String> paramNames) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String result = formatUrlParam(paramMap, isLower, paramNames);
        if(paramNames != null && !paramNames.isEmpty()){
            StringBuffer sb = new StringBuffer(result);
            for(String paramName : paramNames){
                if("api_sign".equals(paramName)){
                    continue;
                }
                if (isLower) {
                    sb.append(paramName.toLowerCase() + "=" + paramMap.get(paramMap));
                } else {
                    sb.append(paramName + "=" + paramMap.get(paramMap));
                }
                sb.append("&");
            }
            sb.append("api_secret="+API_SECRET);
            result = sb.toString();
        }
        return MD5(result);
    }

    /**
     * 参数名根据字母排序(过滤值为空字段)
     * @param paramMap 参数
     * @param isLower 是否小写
     * @param paramNames 要过滤掉的字段名
     * @return
     */
    private static String formatUrlParam(Map<String, String> paramMap, boolean isLower,List<String> paramNames) throws UnsupportedEncodingException {
        String params = "";
        Map<String, String> map = paramMap;
        List<Map.Entry<String, String>> itmes = new ArrayList<Map.Entry<String, String>>(map.entrySet());

        //对所有传入的参数按照字段名从小到大排序
        //Collections.sort(items); 默认正序
        //可通过实现Comparator接口的compare方法来完成自定义排序
        Collections.sort(itmes, new Comparator<Map.Entry<String, String>>() {
            @Override
            public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
                return (o1.getKey().toString().compareTo(o2.getKey()));
            }
        });

        //构造URL 键值对的形式
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String> item : itmes) {
            if (StringUtils.isNotBlank(item.getKey())) {
                String key = item.getKey();
                String val = item.getValue();

                /*过滤掉无用的字符串*/
                if(paramNames != null && paramNames.contains(key)){
                    continue;
                }
                /*过滤掉没有值字符串*/
                if(StringUtils.isBlank(val)){
                    continue;
                }

                val = URLEncoder.encode(val, ENCODE);
                if (isLower) {
                    sb.append(key.toLowerCase() + "=" + val);
                } else {
                    sb.append(key + "=" + val);
                }
                sb.append("&");
            }
        }

        params = sb.toString();
        if (!params.isEmpty()) {
            params = params.substring(0, params.length() - 1);
        }
        return params;
    }

    /**
     * md5 加密
     * @param input
     * @return
     */
    private static String MD5(String input) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        // 拿到一个MD5转换器（如果想要SHA1参数换成”SHA1”）
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        // 输入的字符串转换成字节数组
        byte[] inputByteArray = input.getBytes(ENCODE);
        // inputByteArray是输入字符串转换得到的字节数组
        messageDigest.update(inputByteArray);
        // 转换并返回结果，也是字节数组，包含16个元素
        byte[] resultByteArray = messageDigest.digest();
        // 字符数组转换成字符串返回

        int i;
        StringBuffer buf = new StringBuffer("");
        for (int offset = 0; offset < resultByteArray.length; offset++) {
            i = resultByteArray[offset];
            if (i < 0) {
                i += 256;
            }
            if (i < 16) {
                buf.append("0");
            }
            buf.append(Integer.toHexString(i));
        }
        return buf.toString();
    }
}
