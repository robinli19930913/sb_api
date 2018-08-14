package com.cn.lxg.web.es;

import com.google.gson.GsonBuilder;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.JestResult;
import io.searchbox.client.config.HttpClientConfig;
import io.searchbox.core.*;
import io.searchbox.indices.CreateIndex;
import io.searchbox.indices.DeleteIndex;
import io.searchbox.indices.mapping.GetMapping;
import io.searchbox.indices.mapping.PutMapping;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.util.List;

/**
 * es 工具类
 */
public class JestService {
    private JestClient jestClient;
    public JestService(String esServer){
        JestClientFactory factory = new JestClientFactory();

        factory.setHttpClientConfig(new HttpClientConfig.Builder(esServer)
                .gson(new GsonBuilder().setDateFormat("yyyy-MM-dd'T'hh:mm:ss").create())
                .connTimeout(1500)
                .readTimeout(3000)
                .multiThreaded(true)
                .build());
        jestClient = factory.getObject();
    }

    /**
     * 创建索引
     * @param indexName
     * @return
     * @throws Exception
     */
    public boolean createIndex(String indexName) throws Exception {

        JestResult jr = jestClient.execute(new CreateIndex.Builder(indexName).build());
        return jr.isSucceeded();
    }

    /**
     * Put映射
     * @param indexName
     * @param typeName
     * @param source
     * @return
     * @throws Exception
     */
    public boolean createIndexMapping(String indexName, String typeName, String source) throws Exception {

        PutMapping putMapping = new PutMapping.Builder(indexName, typeName, source).build();
        JestResult jr = jestClient.execute(putMapping);
        return jr.isSucceeded();
    }

    /**
     * Get映射
     * @param indexName
     * @param typeName
     * @return
     * @throws Exception
     */
    public String getIndexMapping(String indexName, String typeName) throws Exception {

        GetMapping getMapping = new GetMapping.Builder().addIndex(indexName).addType(typeName).build();
        JestResult jr = jestClient.execute(getMapping);
        return jr.getJsonString();
    }

    /**
     * 索引文档
     * @param indexName
     * @param typeName
     * @param objs
     * @return
     * @throws Exception
     */
    public boolean index(String indexName, String typeName, List<Object> objs) throws Exception {

        Bulk.Builder bulk = new Bulk.Builder().defaultIndex(indexName).defaultType(typeName);
        for (Object obj : objs) {
            Index index = new Index.Builder(obj).build();
            bulk.addAction(index);
        }
        BulkResult br = jestClient.execute(bulk.build());
        return br.isSucceeded();
    }

    /**
     * 搜索文档
     * @param indexName
     * @param typeName
     * @param query
     * @return
     * @throws Exception
     */
    public SearchResult search( String indexName, String typeName, String query) throws Exception {
        Search search = new Search.Builder(query)
                .addIndex(indexName)
                .addType(typeName)
                .build();

        return jestClient.execute(search);
    }

    /**
     * Count文档
     * @param indexName
     * @param typeName
     * @param query
     * @return
     * @throws Exception
     */
    public Double count(String indexName, String typeName, String query) throws Exception {

        Count count = new Count.Builder()
                .addIndex(indexName)
                .addType(typeName)
                .query(query)
                .build();
        CountResult results = jestClient.execute(count);
        return results.getCount();
    }

    /**
     * Get文档
     * @param indexName
     * @param typeName
     * @param id
     * @return
     * @throws Exception
     */
    public JestResult get( String indexName, String typeName, String id) throws Exception {

        Get get = new Get.Builder(indexName, id).type(typeName).build();
        return jestClient.execute(get);
    }

    /**
     * Delete索引
     * @param indexName
     * @return
     * @throws Exception
     */
    public boolean delete( String indexName) throws Exception {

        JestResult jr = jestClient.execute(new DeleteIndex.Builder(indexName).build());
        return jr.isSucceeded();
    }

    /**
     * Delete文档
     * @param indexName
     * @param typeName
     * @param id
     * @return
     * @throws Exception
     */
    public boolean delete(String indexName, String typeName, String id) throws Exception {

        DocumentResult dr = jestClient.execute(new Delete.Builder(id).index(indexName).type(typeName).build());
        return dr.isSucceeded();
    }

    /**
     * 关闭JestClient客户端
     * @throws Exception
     */
    public void closeJestClient() throws Exception {

        if (jestClient != null) {
            jestClient.shutdownClient();
        }
    }

    public static void main(String[] args) {
        JestService jestService = new JestService("http://127.0.0.1:9200");
        try {
//            System.out.println(jestService.get("owhat","article","642").getSourceAsString());
//
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//
////          QueryBuilders.functionScoreQuery(ScoreFunctionBuilders.gaussDecayFunction("createAt",System.currentTimeMillis(),6*24*60*60*1000,6*24*60*60*1000,6*24*60*60*1000));
            BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
            searchSourceBuilder.query(boolQueryBuilder);
            boolQueryBuilder.must(QueryBuilders.termQuery("id",1));
//            GaussDecayFunctionBuilder gaussDecayFunctionBuilder = ScoreFunctionBuilders.gaussDecayFunction("createAt",1482233465000l,7*24*60*60*1000l,6*24*60*60*1000l,0.5);
//            FunctionScoreQueryBuilder functionScoreQueryBuilder = QueryBuilders.functionScoreQuery(QueryBuilders.matchQuery("title","李坤"),gaussDecayFunctionBuilder);
//            functionScoreQueryBuilder.boostMode(CombineFunction.MULTIPLY).maxBoost(5f);
//
//            searchSourceBuilder.query(functionScoreQueryBuilder);
////            List<QueryBuilder> queryBuilders = boolQueryBuilder.must();
////            queryBuilders.add(QueryBuilders.matchQuery("title","张艺兴"));
////            queryBuilders.add(QueryBuilders.termsQuery("active","0","1"));
//
//            System.out.println(System.currentTimeMillis());
////            System.out.println(QueryBuilders.termsQuery("active","0","1").toString());
            SearchResult searchResult = jestService.search("people","man",searchSourceBuilder.toString());
            System.out.println(searchResult.getSourceAsStringList());
//            System.out.println(searchResult.getJsonObject());
//            for (JsonElement hits : searchResult.getJsonObject().getAsJsonObject("hits").getAsJsonArray("hits")) {
//                System.out.println(hits);
//            }
           /* for (String s : searchResult.getJsonObject()) {
                System.out.println(s);
            }*/


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}