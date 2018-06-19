package com.cn.lxg.web.apiversion;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.mvc.condition.RequestCondition;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author wszhang
 *
 * @create 2017年4月9日 上午12:57:39

 * @version 1.0

 * @description 版本号匹配筛选器
 */
public class ApiVesrsionCondition implements RequestCondition<ApiVesrsionCondition> {
    private static final ThreadLocal<String> activevLocal = new ThreadLocal<String>();
    private static final ThreadLocal<String> requestvLocal = new ThreadLocal<String>();
    // 路径中版本的前缀， 这里用 /v[1-9]/的形式
//    private final static Pattern VERSION_PREFIX_PATTERN = Pattern.compile("v(\\d+)/");

    private String apiVersion;

    public ApiVesrsionCondition(String apiVersion) {
        this.apiVersion = apiVersion;
    }

    @Override
    public ApiVesrsionCondition combine(ApiVesrsionCondition other) {
        // 采用最后定义优先原则，则方法上的定义覆盖类上面的定义
        return new ApiVesrsionCondition(other.getApiVersion());
    }

    @Override
    public int compareTo(ApiVesrsionCondition other, HttpServletRequest request) {
        // 优先匹配最新的版本号
//        return 0;
        return compare(v2int(other.getApiVersion()),v2int(this.apiVersion));
    }

    public String getApiVersion() {
        return apiVersion;
    }

    @Override
    public ApiVesrsionCondition getMatchingCondition(HttpServletRequest request) {
        //request.getPathInfo()

//        Matcher m = VERSION_PREFIX_PATTERN.matcher(request.getServletPath());
//        if (m.find()) {
//            Integer version = Integer.valueOf(m.group(1));
//            if (version >= this.apiVersion)
//                // 如果请求的版本号大于等于配置版本号， 则满足
//                return this;
//        } else {
//            // 如果版本号错误返回最新版本号
//            return this;
//        }
//
        if(this.apiVersion==null)
        {
            return this;
        }
        String v = request.getParameter("version");
        if(StringUtils.isBlank(v))return null;

        if(compare(v2int(v),v2int(this.apiVersion))>=0){
            activevLocal.set(this.apiVersion);
            requestvLocal.set(v);
            return this;
        }
        return null;
    }
    public int compare(Integer[] i1,Integer[] i2){
        if(i1[0]!=i2[0])
        {
            return i1[0]-i2[0];
        }
        else if(i1[0]!=i2[0])
        {
            return i1[1]-i2[1];
        }else {
            return i1[2]-i2[2];
        }
    }
    private Integer[] v2int(String v){
        String[] s = v.split("\\.");
        Integer[] integers = {Integer.parseInt(s[0])
                ,Integer.parseInt(s[1])
                ,Integer.parseInt(s[2])};
        return integers;
    }

    public static String getActiveV(){
        return activevLocal.get();
    }
    public static String getQuestionV(){
        return requestvLocal.get();
    }
    public static void main(String[] args) {

        System.out.println("4.1.1".split("\\.")[1]);

    }
}