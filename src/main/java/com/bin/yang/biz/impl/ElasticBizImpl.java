package com.bin.yang.biz.impl;

import com.bin.yang.biz.ElasticBiz;
import com.bin.yang.entity.Elastic;
import com.bin.yang.rest.client.RestFastClient;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.support.replication.ReplicationResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.Strings;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: bin.yang
 * @Date: 2019/3/1 15:34
 * @Description:
 */
@Service
@Slf4j
public  class ElasticBizImpl implements ElasticBiz {

    @Override
    public void insertDataInfo(Elastic elastic) {
        System.out.println(elastic);
        try (RestHighLevelClient client = new RestFastClient().getClient()) {


            // 1、创建索引请求
            IndexRequest request = new IndexRequest(
                    elastic.getIndex(),   //索引
                    elastic.getDocType(),     // mapping type
                    elastic.getMap().get("id").toString());     //文档id



            System.out.println("-------> after :  获取数据操作ES库");

            // 方式二：以map对象来表示文档
           /* Map<String, Object> jsonMap = new HashMap<>();
            jsonMap.put("user", "kimchy");
            jsonMap.put("postDate", new Date());
            jsonMap.put("message", "trying out Elasticsearch");*/
            elastic.setId(elastic.getMap().get("id").toString());
            HashMap<String, Object> map = elastic.getMap();
            map.remove("id");
            request.source(map);

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

                // 接着查询出来
                GetDocument(elastic);

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

    @Override
    public void testInterface() {
        System.out.println(10001);
    }


    /**
     * @Description: (查询文档)
     * @param
     * @param elastic
     * @[param] []
     * @return void
     * @author:  bin.yang
     * @date:  2019/3/4 11:43 AM
     */
    public void GetDocument(Elastic elastic) {

        try (RestHighLevelClient client = new RestFastClient().getClient()) {
            // 1、创建获取文档请求
            GetRequest request = new GetRequest(
                    elastic.getIndex(),   //索引
                    elastic.getDocType(),     // mapping type
                    elastic.getId());     //文档id

            // 2、可选的设置
            //request.routing("routing");
            //request.version(2);

            System.out.println("******** : 查询操作的数据是否成功....");

            //request.fetchSourceContext(new FetchSourceContext(false)); //是否获取_source字段
            //选择返回的字段
            String[] includes = new String[]{"*"};
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
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void addDocType(Elastic elastic) {
        System.out.println(elastic);
        try (RestHighLevelClient client = new RestFastClient().getClient()) {

            PutMappingRequest request = new PutMappingRequest(elastic.getIndex());
            request.type(elastic.getDocType());

            // 2、设置索引的settings
            Map<String, Object> jsonMap = new HashMap<>();
            Map<String, Object> message = new HashMap<>();
            message.put("type", "text");
            Map<String, Object> properties = new HashMap<>();
            properties.put("message", message);
            jsonMap.put("properties", properties);
            request.source(jsonMap);

            AcknowledgedResponse response = client.indices().putMapping(request);

            boolean acknowledged = response.isAcknowledged();
            boolean responseAcknowledged = response.isAcknowledged();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
