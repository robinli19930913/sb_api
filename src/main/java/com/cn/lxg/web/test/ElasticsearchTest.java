package com.cn.lxg.web.test;

import com.alibaba.fastjson.JSONObject;
import com.cn.lxg.web.es.JestService;
import com.cn.lxg.web.es.RestHttp;
import io.searchbox.core.SearchResult;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;


/**
 * es 测试类
 * https://www.cnblogs.com/wenbronk/p/6432990.html
 */
public class ElasticsearchTest {
    public static void main(String[] args) {
        JSONObject json = new JSONObject();
        json.put("id",4);
        json.put("name","李四");
        json.put("country","费率普");
        json.put("keyword","哈哈哈");
        json.put("age",25);
        String result =  RestHttp.getInstance().post("http://127.0.0.1:9200/people/man/4?pretty",json.toString());//插入ES
        System.out.println(result);

        JestService jestService = new JestService("http://127.0.0.1:9200");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        searchSourceBuilder.query(boolQueryBuilder);
        /**
         * 使用QueryBuilder
         * termQuery("key", obj) 完全匹配
         * termsQuery("key", obj1, obj2..)   一次匹配多个值
         * matchQuery("key", Obj) 单个匹配, field不支持通配符, 前缀具高级特性
         * matchPhraseQuery("key",Obj) 多个匹配
         * multiMatchQuery("text", "field1", "field2"..);  匹配多个字段, field有通配符忒行
         * matchAllQuery();         匹配所有文件
         */
        boolQueryBuilder.must(QueryBuilders.matchPhraseQuery("name","张"));
        searchSourceBuilder.from(0);//页数 从0 开始
        searchSourceBuilder.size(10);//每页数
        searchSourceBuilder.sort("id", SortOrder.ASC);

        SearchResult searchResult = null;
        try {
            searchResult = jestService.search("people","man",searchSourceBuilder.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(searchResult.getSourceAsStringList());

    }
}
