package com.bin.yang.rest;

import com.alibaba.fastjson.JSONObject;
import com.bin.yang.rest.client.RestFastClient;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.ShardSearchFailure;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.document.DocumentField;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.suggest.Suggest;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.SuggestBuilders;
import org.elasticsearch.search.suggest.SuggestionBuilder;
import org.elasticsearch.search.suggest.completion.CompletionSuggestion;
import org.elasticsearch.search.suggest.term.TermSuggestion;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: bin.yang
 * @Date: 2019/2/27 16:35
 * @Description:
 */
@Component
@Slf4j
public class ElasticSeniorController {

    
    /**
     * @Description: (批量索引文档，即批量往索引里面放入文档数据.类似于数据库里面批量向表里面插入多行数据，一行数据就是一个文档)
     * @param 	 
     * @[param] []
     * @return void
     * @author:  bin.yang
     * @date:  2019/2/27 4:49 PM
     */
    public void BulkCreateDocument(){

        try (RestHighLevelClient client = new RestFastClient().getClient()) {

            // 1、创建批量操作请求
            BulkRequest request = new BulkRequest();
            request.add(new IndexRequest("sunny", "_doc", "7")
                    .source(XContentType.JSON,"field", "foo").source(XContentType.JSON,"user","999"));
            request.add(new IndexRequest("sunny", "_doc", "8")
                    .source(XContentType.JSON,"field", "bar"));
            request.add(new IndexRequest("sunny", "_doc", "9")
                    .source(XContentType.JSON,"field", "baz"));

            /*
            request.add(new DeleteRequest("mess", "_doc", "3"));
            request.add(new UpdateRequest("mess", "_doc", "2")
                    .doc(XContentType.JSON,"other", "test"));
            request.add(new IndexRequest("mess", "_doc", "4")
                    .source(XContentType.JSON,"field", "baz"));
            */

            // 2、可选的设置
            /*
            request.timeout("2m");
            request.setRefreshPolicy("wait_for");
            request.waitForActiveShards(2);
            */


            //3、发送请求

            // 同步请求
            BulkResponse bulkResponse = client.bulk(request);


            //4、处理响应
            if(bulkResponse != null) {
                System.err.println("开始执行");
                for (BulkItemResponse bulkItemResponse : bulkResponse) {
                    DocWriteResponse itemResponse = bulkItemResponse.getResponse();

                    if (bulkItemResponse.getOpType() == DocWriteRequest.OpType.INDEX
                            || bulkItemResponse.getOpType() == DocWriteRequest.OpType.CREATE) {
                        IndexResponse indexResponse = (IndexResponse) itemResponse;
                        //TODO 新增成功的处理
                        System.err.println("批量插入成功 : "+indexResponse.getShardId().getIndexName());
                    } else if (bulkItemResponse.getOpType() == DocWriteRequest.OpType.UPDATE) {
                        UpdateResponse updateResponse = (UpdateResponse) itemResponse;
                        //TODO 修改成功的处理
                        System.err.println("批量插入成功 : "+updateResponse.getGetResult().sourceAsString());
                    } else if (bulkItemResponse.getOpType() == DocWriteRequest.OpType.DELETE) {
                        DeleteResponse deleteResponse = (DeleteResponse) itemResponse;
                        //TODO 删除成功的处理
                        System.err.println("批量插入成功 : "+deleteResponse);
                    }
                }
            }


            //异步方式发送批量操作请求
            /*
            ActionListener<BulkResponse> listener = new ActionListener<BulkResponse>() {
                @Override
                public void onResponse(BulkResponse bulkResponse) {

                }

                @Override
                public void onFailure(Exception e) {

                }
            };
            client.bulkAsync(request, listener);
            */

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @Description: (搜索数据)
     * @param 	 
     * @[param] []
     * @return void
     * @author:  bin.yang
     * @date:  2019/2/27 4:59 PM
     */
    public List<Object> searchDocument(){

        try (RestHighLevelClient client = new RestFastClient().getClient()) {

            // 1、创建search请求
            //SearchRequest searchRequest = new SearchRequest();
            SearchRequest searchRequest = new SearchRequest("dev_data_sou_user");
            searchRequest.types("t_user");

            // 2、用SearchSourceBuilder来构造查询请求体 ,请仔细查看它的方法，构造各种查询的方法都在这。
            SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

            //构造QueryBuilder
            /*QueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("user", "kimchy")
                    .fuzziness(Fuzziness.AUTO)
                    .prefixLength(3)
                    .maxExpansions(10);
            sourceBuilder.query(matchQueryBuilder);*/

//            sourceBuilder.query(QueryBuilders.termQuery("age", 24));
            sourceBuilder.from(0);
            sourceBuilder.size(10);
            sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));

            //是否返回_source字段
            //sourceBuilder.fetchSource(false);

            //设置返回哪些字段
            /*String[] includeFields = new String[] {"title", "user", "innerObject.*"};
            String[] excludeFields = new String[] {"_type"};
            sourceBuilder.fetchSource(includeFields, excludeFields);*/

            //指定排序
            //sourceBuilder.sort(new ScoreSortBuilder().order(SortOrder.DESC)); 
            //sourceBuilder.sort(new FieldSortBuilder("_uid").order(SortOrder.ASC));

            // 设置返回 profile 
            //sourceBuilder.profile(true);

            //将请求体加入到请求中
            searchRequest.source(sourceBuilder);

            // 可选的设置
            //searchRequest.routing("routing");

            // 高亮设置
            /*
            HighlightBuilder highlightBuilder = new HighlightBuilder(); 
            HighlightBuilder.Field highlightTitle =
                    new HighlightBuilder.Field("title"); 
            highlightTitle.highlighterType("unified");  
            highlightBuilder.field(highlightTitle);  
            HighlightBuilder.Field highlightUser = new HighlightBuilder.Field("user");
            highlightBuilder.field(highlightUser);
            sourceBuilder.highlighter(highlightBuilder);*/


            //加入聚合
            /*TermsAggregationBuilder aggregation = AggregationBuilders.terms("by_company")
                    .field("company.keyword");
            aggregation.subAggregation(AggregationBuilders.avg("average_age")
                    .field("age"));
            sourceBuilder.aggregation(aggregation);*/

            //做查询建议
            /*SuggestionBuilder termSuggestionBuilder =
                    SuggestBuilders.termSuggestion("user").text("kmichy"); 
                SuggestBuilder suggestBuilder = new SuggestBuilder();
                suggestBuilder.addSuggestion("suggest_user", termSuggestionBuilder); 
            sourceBuilder.suggest(suggestBuilder);*/

            //3、发送请求        
            SearchResponse searchResponse = client.search(searchRequest);


            //4、处理响应
            //搜索结果状态信息
            RestStatus status = searchResponse.status();
            TimeValue took = searchResponse.getTook();
            Boolean terminatedEarly = searchResponse.isTerminatedEarly();
            boolean timedOut = searchResponse.isTimedOut();

            //分片搜索情况
            int totalShards = searchResponse.getTotalShards();
            int successfulShards = searchResponse.getSuccessfulShards();
            int failedShards = searchResponse.getFailedShards();
            for (ShardSearchFailure failure : searchResponse.getShardFailures()) {
                // failures should be handled here
            }

            System.err.println("检索结束");
            //处理搜索命中文档结果
            SearchHits hits = searchResponse.getHits();

            long totalHits = hits.getTotalHits();
            float maxScore = hits.getMaxScore();

            List<Object> objects = new ArrayList<>();

            SearchHit[] searchHits = hits.getHits();
            for (SearchHit hit : searchHits) {
                // do something with the SearchHit

                String index = hit.getIndex();
                String type = hit.getType();
                String id = hit.getId();
                float score = hit.getScore();

                //取_source字段值
                String sourceAsString = hit.getSourceAsString(); //取成json串
//                System.err.println(sourceAsString);
                Map<String, Object> sourceAsMap = hit.getSourceAsMap(); // 取成map对象
                System.err.println(sourceAsMap);
                //从map中取字段值
              /*  String documentTitle = (String) sourceAsMap.get("title");
                List<Object> users = (List<Object>) sourceAsMap.get("user");
                Map<String, Object> innerObject = (Map<String, Object>) sourceAsMap.get("innerObject");
                System.err.println(innerObject);*/
                log.info("index:" + index + "  type:" + type + "  id:" + id);
                System.err.println("index:" + index + "  type:" + type + "  id:" + id);
                log.info(sourceAsString);
                sourceAsMap.put("id",id);
                objects.add(sourceAsMap);

                //取高亮结果
                /*Map<String, HighlightField> highlightFields = hit.getHighlightFields();
                HighlightField highlight = highlightFields.get("title");
                Text[] fragments = highlight.fragments();
                String fragmentString = fragments[0].string();*/
            }

            // 获取聚合结果
            /*
            Aggregations aggregations = searchResponse.getAggregations();
            Terms byCompanyAggregation = aggregations.get("by_company");
            Bucket elasticBucket = byCompanyAggregation.getBucketByKey("Elastic");
            Avg averageAge = elasticBucket.getAggregations().get("average_age");
            double avg = averageAge.getValue();
            */

            // 获取建议结果
            /*Suggest suggest = searchResponse.getSuggest();
            TermSuggestion termSuggestion = suggest.getSuggestion("suggest_user");
            for (TermSuggestion.Entry entry : termSuggestion.getEntries()) {
                for (TermSuggestion.Entry.Option option : entry) {
                    String suggestText = option.getText().string();
                }
            }
            */

            //异步方式发送获查询请求
            /*
            ActionListener<SearchResponse> listener = new ActionListener<SearchResponse>() {
                @Override
                public void onResponse(SearchResponse getResponse) {
                    //结果获取
                }

                @Override
                public void onFailure(Exception e) {
                    //失败处理
                }
            };
            client.searchAsync(searchRequest, listener);
            */

            return objects;
        } catch (IOException e) {
            log.error(e.getMessage());
            return null;
        }

    }

    /**
     * @Description: (高亮)
     * @param
     * @[param] []
     * @return void
     * @author:  bin.yang
     * @date:  2019/2/27 5:14 PM
     */
    public void HighlightDocument(){

        try (RestHighLevelClient client = new RestFastClient().getClient()) {

            // 1、创建search请求
            SearchRequest searchRequest = new SearchRequest("sunny");

            // 2、用SearchSourceBuilder来构造查询请求体 ,请仔细查看它的方法，构造各种查询的方法都在这。
            SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

            //构造QueryBuilder
            QueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("user", "kimchy");
            sourceBuilder.query(matchQueryBuilder);

            //分页设置
            /*sourceBuilder.from(0);
            sourceBuilder.size(5); ;*/


            // 高亮设置
            HighlightBuilder highlightBuilder = new HighlightBuilder();
            highlightBuilder.requireFieldMatch(false).field("title").field("content")
                    .preTags("<strong>").postTags("</strong>");
            //不同字段可有不同设置，如不同标签
            /*HighlightBuilder.Field highlightTitle = new HighlightBuilder.Field("title");
            highlightTitle.preTags("<strong>").postTags("</strong>");
            highlightBuilder.field(highlightTitle);
            HighlightBuilder.Field highlightContent = new HighlightBuilder.Field("content");
            highlightContent.preTags("<b>").postTags("</b>");
            highlightBuilder.field(highlightContent).requireFieldMatch(false);*/

            sourceBuilder.highlighter(highlightBuilder);

            searchRequest.source(sourceBuilder);

            //3、发送请求
            SearchResponse searchResponse = client.search(searchRequest);


            //4、处理响应
            if(RestStatus.OK.equals(searchResponse.status())) {
                //处理搜索命中文档结果
                SearchHits hits = searchResponse.getHits();
                long totalHits = hits.getTotalHits();

                SearchHit[] searchHits = hits.getHits();
                for (SearchHit hit : searchHits) {
                    String index = hit.getIndex();
                    String type = hit.getType();
                    String id = hit.getId();
                    float score = hit.getScore();

                    //取_source字段值
                    //String sourceAsString = hit.getSourceAsString(); //取成json串
                    Map<String, Object> sourceAsMap = hit.getSourceAsMap(); // 取成map对象
                    System.err.println(sourceAsMap);
                    //从map中取字段值
                    /*String title = (String) sourceAsMap.get("title");
                    String content  = (String) sourceAsMap.get("content"); */
                    log.info("index:" + index + "  type:" + type + "  id:" + id);
                    log.info("sourceMap : " +  sourceAsMap);
                    //取高亮结果
                    Map<String, HighlightField> highlightFields = hit.getHighlightFields();
                    System.err.println(highlightFields);
                    HighlightField highlight = highlightFields.get("title");
                    if(highlight != null) {
                        Text[] fragments = highlight.fragments();  //多值的字段会有多个值
                        if(fragments != null) {
                            String fragmentString = fragments[0].string();
                            System.err.println(fragmentString);
                            log.info("title highlight : " +  fragmentString);
                            //可用高亮字符串替换上面sourceAsMap中的对应字段返回到上一级调用
                            //sourceAsMap.put("title", fragmentString);
                        }
                    }

                    highlight = highlightFields.get("content");
                    if(highlight != null) {
                        Text[] fragments = highlight.fragments();  //多值的字段会有多个值
                        if(fragments != null) {
                            String fragmentString = fragments[0].string();
                            System.err.println(fragmentString);
                            log.info("content highlight : " +  fragmentString);
                            //可用高亮字符串替换上面sourceAsMap中的对应字段返回到上一级调用
                            //sourceAsMap.put("content", fragmentString);
                        }
                    }
                }
            }

        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    /**
     * @Description: (查询建议)
     * @param 	 
     * @[param] []
     * @return void
     * @author:  bin.yang
     * @date:  2019/2/27 6:05 PM
     */
    public void SuggestDocument(){

        try (RestHighLevelClient client = new RestFastClient().getClient()) {

            // 1、创建search请求
            //SearchRequest searchRequest = new SearchRequest();
            SearchRequest searchRequest = new SearchRequest("sunny");

            // 2、用SearchSourceBuilder来构造查询请求体 ,请仔细查看它的方法，构造各种查询的方法都在这。
            SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

            sourceBuilder.size(0);

            //做查询建议
            //词项建议
            SuggestionBuilder termSuggestionBuilder =
                    SuggestBuilders.termSuggestion("user").text("kmichy");
            SuggestBuilder suggestBuilder = new SuggestBuilder();
            suggestBuilder.addSuggestion("suggest_user", termSuggestionBuilder);
            sourceBuilder.suggest(suggestBuilder);

            searchRequest.source(sourceBuilder);

            //3、发送请求
            SearchResponse searchResponse = client.search(searchRequest);


            //4、处理响应
            //搜索结果状态信息
            if(RestStatus.OK.equals(searchResponse.status())) {
                // 获取建议结果
                Suggest suggest = searchResponse.getSuggest();
                TermSuggestion termSuggestion = suggest.getSuggestion("suggest_user");
                for (TermSuggestion.Entry entry : termSuggestion.getEntries()) {
                    log.info("text: " + entry.getText().string());
                    for (TermSuggestion.Entry.Option option : entry) {
                        String suggestText = option.getText().string();
                        log.info("   suggest option : " + suggestText);
                    }
                }
            }
            /*
              "suggest": {
                "my-suggestion": [
                  {
                    "text": "tring",
                    "offset": 0,
                    "length": 5,
                    "options": [
                      {
                        "text": "trying",
                        "score": 0.8,
                        "freq": 1
                      }
                    ]
                  },
                  {
                    "text": "out",
                    "offset": 6,
                    "length": 3,
                    "options": []
                  },
                  {
                    "text": "elasticsearch",
                    "offset": 10,
                    "length": 13,
                    "options": []
                  }
                ]
              }*/

        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    /**
     * @Description: (自动补全，根据用户的输入联想到可能的词或者短语)
     * @param
     * @[param] []
     * @return void
     * @author:  bin.yang
     * @date:  2019/2/27 6:06 PM
     */
    public static void completionSuggester() {
        try (RestHighLevelClient client = new RestFastClient().getClient()) {

            // 1、创建search请求
            //SearchRequest searchRequest = new SearchRequest();
            SearchRequest searchRequest = new SearchRequest("sunny");

            // 2、用SearchSourceBuilder来构造查询请求体 ,请仔细查看它的方法，构造各种查询的方法都在这。
            SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

            sourceBuilder.size(0);

            //做查询建议
            //自动补全
            /*POST music/_search?pretty
                    {
                        "suggest": {
                            "song-suggest" : {
                                "prefix" : "lucene s",
                                "completion" : {
                                    "field" : "suggest" ,
                                    "skip_duplicates": true
                                }
                            }
                        }
                    }*/

            SuggestionBuilder termSuggestionBuilder =
                    SuggestBuilders.completionSuggestion("suggest").prefix("lucene s")
                            .skipDuplicates(true);
            SuggestBuilder suggestBuilder = new SuggestBuilder();
            suggestBuilder.addSuggestion("song-suggest", termSuggestionBuilder);
            sourceBuilder.suggest(suggestBuilder);

            searchRequest.source(sourceBuilder);

            //3、发送请求
            SearchResponse searchResponse = client.search(searchRequest);


            //4、处理响应
            //搜索结果状态信息
            if(RestStatus.OK.equals(searchResponse.status())) {
                // 获取建议结果
                Suggest suggest = searchResponse.getSuggest();
                CompletionSuggestion termSuggestion = suggest.getSuggestion("song-suggest");
                for (CompletionSuggestion.Entry entry : termSuggestion.getEntries()) {
                    log.info("text: " + entry.getText().string());
                    for (CompletionSuggestion.Entry.Option option : entry) {
                        String suggestText = option.getText().string();
                        log.info("   suggest option : " + suggestText);
                    }
                }
            }

        } catch (IOException e) {
            log.error(e.getMessage());
        }
 
    }

//    public static void main(String[] args) {
//        new ElasticSeniorController().completionSuggester();
//    }

}
