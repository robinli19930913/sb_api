package com.cn.lxg.web.test;

/**
 * Created by robin on 2018/6/19.
 */
public class Consts {
    public static final class 订单状态{
        public static final Integer 未支付 = 1;
    }

    public enum myEnum{
        TEST(2008,"错误信息");
        private Integer code;
        private String msg;

        public Integer getCode() {
            return this.code;
        }

        public String getMsg() {
            return this.msg;
        }

        myEnum(Integer code, String msg){
            this.code = code;
            this.msg = msg;
        }

    }

    public static void main(String[] args) {
        System.out.println(订单状态.未支付);
        System.out.println(myEnum.TEST.getCode());
        System.out.println(myEnum.TEST.getMsg());
    }
}
