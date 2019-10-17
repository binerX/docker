package com.bin.yang.rest;

import com.bin.yang.rest.client.RestFastClient;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.admin.indices.alias.Alias;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.replication.ReplicationResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.Strings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: bin.yang
 * @Date: 2019/1/25 16:52
 * @Description:
 */
@Component
@Slf4j
public class ElasticController {


    /**
     * @Description: (创建索引)
     * @param 	 
     * @[param] []
     * @return void
     * @author:  bin.yang
     * @date:  2019/2/27 4:07 PM
     */
    public void CreateIndex(){

        RestHighLevelClient client = new RestFastClient().getClient();

        // 1、创建 创建索引request 参数：索引名mess
        CreateIndexRequest request = new CreateIndexRequest("dev_data_sou_user");

        // 2、设置索引的settings
        request.settings(Settings.builder()
                // 分片数
                .put("index.number_of_shards", 3)
                // 副本数
                .put("index.number_of_replicas", 2)
        );


        Map<String, Object> jsonMap = new HashMap<>();

        Map<String, Object> name = new HashMap<>();
        name.put("type", "text");
        name.put("analyzer", "ik_max_word");

        Map<String, Object> age = new HashMap<>();
        age.put("type", "integer");

        Map<String, Object> phone = new HashMap<>();
        phone.put("type", "text");
        phone.put("index", "not_analyzed");

        Map<String, Object> properties = new HashMap<>();
        properties.put("name", name);
        properties.put("age", age);
        properties.put("phone", phone);

        Map<String, Object> mapping = new HashMap<>();
        mapping.put("properties", properties);

        jsonMap.put("t_user", mapping);
        request.mapping("t_user", jsonMap);

        // 3、设置索引的mappings
        /*request.mapping("_t_user",
                "  {\n" +
                        "    \"_doc\": {\n" +

                        "      \"properties\": {\n" +

                        "        \"name\": {\n" +
                        "          \"type\": \"text\"\n" +
                        "        }\n" +
                        ","+
                        " \"sex\":{   \n \"type\":\"text\"    \n  } \n "+

                        "      }\n" +



                        "    }\n" +
                        "  }",
                XContentType.JSON);
*/

        try {
            // 4、 设置索引的别名
            request.alias(new Alias("esd"));

            // 5、 发送请求
            // 5.1 同步方式发送请求
            CreateIndexResponse createIndexResponse = client.indices().create(request);

            // 6、处理响应
            boolean acknowledged = createIndexResponse.isAcknowledged();
            boolean shardsAcknowledged = createIndexResponse.isAcknowledged();
            System.out.println("acknowledged = " + acknowledged);
            System.out.println("shardsAcknowledged = " + shardsAcknowledged);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @Description: (索引文档，即往索引里面放入文档数据.类似于数据库里面向表里面插入一行数据，一行数据就是一个文档)
     * @param 	 
     * @[param] []
     * @return void
     * @author:  bin.yang
     * @date:  2019/2/27 4:07 PM
     */
    public void CreateDocument() {

        try (RestHighLevelClient client = new RestFastClient().getClient()) {
            // 1、创建索引请求
            IndexRequest request = new IndexRequest(
                    "data_sou_t_user",   //索引
                    "t_user",     // mapping type
                    "1");     //文档id

            // 2、准备文档数据
            // 方式一：直接给JSON串
           /* String jsonString = "{" +
                    "\"user\":\"kimchy\"," +
                    "\"postDate\":\"2013-01-30\"," +
                    "\"message\":\"trying out Elasticsearch\"" +
                    "}";
            request.source(jsonString, XContentType.JSON);*/

            // 方式二：以map对象来表示文档

            Map<String, Object> jsonMap = new HashMap<>();
            jsonMap.put("name", "zhangsan");
            jsonMap.put("age", 18);
            request.source(jsonMap);

            // 方式三：用XContentBuilder来构建文档
            /*
            XContentBuilder builder = XContentFactory.jsonBuilder();
            builder.startObject();
            {
                builder.field("user", "kimchy");
                builder.field("postDate", new Date());
                builder.field("message", "trying out Elasticsearch");
            }
            builder.endObject();
            request.source(builder);
            */

            // 方式四：直接用key-value对给出
            /*
            request.source("user", "kimchy",
                            "postDate", new Date(),
                            "message", "trying out Elasticsearch");
            */

            //3、其他的一些可选设置
            /*
            request.routing("routing");  //设置routing值
            request.timeout(TimeValue.timeValueSeconds(1));  //设置主分片等待时长
            request.setRefreshPolicy("wait_for");  //设置重刷新策略
            request.version(2);  //设置版本号
            request.opType(DocWriteRequest.OpType.CREATE);  //操作类别
            */

            //4、发送请求
            IndexResponse indexResponse = null;
            try {
                // 同步方式
                indexResponse = client.index(request);
            } catch (ElasticsearchException e) {
                // 捕获，并处理异常
                //判断是否版本冲突、create但文档已存在冲突
                if (e.status() == RestStatus.CONFLICT) {
                    log.error("冲突了，请在此写冲突处理逻辑！\n" + e.getDetailedMessage());
                }

                log.error("索引异常", e);
            }

            //5、处理响应
            if (indexResponse != null) {
                String index = indexResponse.getIndex();
                String type = indexResponse.getType();
                String id = indexResponse.getId();
                long version = indexResponse.getVersion();
                if (indexResponse.getResult() == DocWriteResponse.Result.CREATED) {
                    System.out.println("新增文档成功，处理逻辑代码写到这里。");
                } else if (indexResponse.getResult() == DocWriteResponse.Result.UPDATED) {
                    System.out.println("修改文档成功，处理逻辑代码写到这里。");
                }
                // 分片处理信息
                ReplicationResponse.ShardInfo shardInfo = indexResponse.getShardInfo();
                if (shardInfo.getTotal() != shardInfo.getSuccessful()) {

                }
                // 如果有分片副本失败，可以获得失败原因信息
                if (shardInfo.getFailed() > 0) {
                    for (ReplicationResponse.ShardInfo.Failure failure : shardInfo.getFailures()) {
                        String reason = failure.reason();
                        System.out.println("副本失败原因：" + reason);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @Description: (获取文档数据)
     * @param 	 
     * @[param] []
     * @return void
     * @author:  bin.yang
     * @date:  2019/2/27 4:08 PM
     */
    public void GetDocument(String did) {

        try (RestHighLevelClient client = new RestFastClient().getClient()) {
            // 1、创建获取文档请求
            GetRequest request = new GetRequest(
                    "data_sou",   //索引
                    "t_user",     // mapping type
                          did);     //文档id

            // 2、可选的设置
            //request.routing("routing");
            //request.version(2);

            //request.fetchSourceContext(new FetchSourceContext(false)); //是否获取_source字段
            //选择返回的字段
            String[] includes = new String[]{"user","message", "*Date"};
            String[] excludes = Strings.EMPTY_ARRAY;
            FetchSourceContext fetchSourceContext = new FetchSourceContext(true, includes, excludes);
            request.fetchSourceContext(fetchSourceContext);

            //也可写成这样
            /*String[] includes = Strings.EMPTY_ARRAY;
            String[] excludes = new String[]{"message"};
            FetchSourceContext fetchSourceContext = new FetchSourceContext(true, includes, excludes);
            request.fetchSourceContext(fetchSourceContext);*/


            // 取stored字段
            /*request.storedFields("message");
            GetResponse getResponse = client.get(request);
            String message = getResponse.getField("message").getValue();*/


            //3、发送请求
            GetResponse getResponse = null;
            try {
                // 同步请求
                getResponse = client.get(request);
            } catch (ElasticsearchException e) {
                if (e.status() == RestStatus.NOT_FOUND) {
                    log.error("没有找到该id的文档" );
                }
                if (e.status() == RestStatus.CONFLICT) {
                    log.error("获取时版本冲突了，请在此写冲突处理逻辑！" );
                }
                log.error("获取文档异常", e);
            }

            //4、处理响应
            if(getResponse != null) {
                String index = getResponse.getIndex();
                String type = getResponse.getType();
                String id = getResponse.getId();
                if (getResponse.isExists()) { // 文档存在
                    long version = getResponse.getVersion();
                    String sourceAsString = getResponse.getSourceAsString(); //结果取成 String
                    System.err.println(sourceAsString);
                    Map<String, Object> sourceAsMap = getResponse.getSourceAsMap();  // 结果取成Map
                    byte[] sourceAsBytes = getResponse.getSourceAsBytes();    //结果取成字节数组

                    log.info("index:" + index + "  type:" + type + "  id:" + id);
                    log.info(sourceAsString);

                } else {
                    log.error("没有找到该id的文档" );
                }
            }


            //异步方式发送获取文档请求
            /*
            ActionListener<GetResponse> listener = new ActionListener<GetResponse>() {
                @Override
                public void onResponse(GetResponse getResponse) {

                }

                @Override
                public void onFailure(Exception e) {

                }
            };
            client.getAsync(request, listener);
            */

        } catch (IOException e) {
            e.printStackTrace();
        }
       
    }

    /**
     * @Description: (删除文档)
     * @param
     * @[param] []
     * @return void
     * @author:  bin.yang
     * @date:  2019/2/27 4:37 PM
     */
    public void DelDocument(String zid) {

        try (RestHighLevelClient client = new RestFastClient().getClient()) {
            // 1、创建获取文档请求
            DeleteRequest request = new DeleteRequest(
                    "dev_data_sou_user",   //索引
                    "t_user",     // mapping type
                    zid);     //文档id



            //3、发送请求
            DeleteResponse deleteResponse = null;
            try {
                // 同步请求
                deleteResponse = client.delete(request);
            } catch (ElasticsearchException e) {
                if (e.status() == RestStatus.NOT_FOUND) {
                    log.error("没有找到该id的文档" );
                }
                if (e.status() == RestStatus.CONFLICT) {
                    log.error("获取时版本冲突了，请在此写冲突处理逻辑！" );
                }
                log.error("获取文档异常", e);
            }

            //4、处理响应
            if(deleteResponse != null) {
                String index = deleteResponse.getIndex();
                String type = deleteResponse.getType();
                String id = deleteResponse.getId();
                    log.error("文档"+ id+" : 删除成功");
                    long version = deleteResponse.getVersion();
                    System.err.println(deleteResponse.toString());
            }


            //异步方式发送获取文档请求
            /*
            ActionListener<GetResponse> listener = new ActionListener<GetResponse>() {
                @Override
                public void onResponse(GetResponse getResponse) {

                }

                @Override
                public void onFailure(Exception e) {

                }
            };
            client.getAsync(request, listener);
            */

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void testClient(){

        RestHighLevelClient client = new RestFastClient().getClient();
        new IndexRequest();

    }

}
