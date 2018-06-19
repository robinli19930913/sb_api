package com.cn.lxg.web.bean;

import lombok.Data;

@Data
public class BaseRequest {
	/**当前页数 从1开始 */
    private Integer page = 1;
    /**每页显示个数 */
    private Integer rows = 20;
    
    private String startTime;
    
    private String endTime;
    
}
