//package com.bin.yang.rest;
//
//import com.alibaba.otter.canal.client.CanalConnector;
//import com.alibaba.otter.canal.client.CanalConnectors;
//import com.alibaba.otter.canal.protocol.CanalEntry;
//import com.alibaba.otter.canal.protocol.CanalEntry.*;
//import com.alibaba.otter.canal.protocol.Message;
//import com.bin.yang.biz.impl.ElasticBizImpl;
//import com.bin.yang.entity.Elastic;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//
//import java.net.InetSocketAddress;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//
///**
// * @Auther: bin.yang
// * @Date: 2019/2/25 16:50
// * @Description:
// */
//@Component
//public class CanalController {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(CanalController.class);
//
//    public void runCanal() {
//        // 创建链接
//        CanalConnector connector = CanalConnectors.newSingleConnector(new InetSocketAddress("10.0.0.12", 11125),
//                "example", "", "");
//
////        // zk 集群HA模式
////        CanalConnector connector = CanalConnectors.newClusterConnector("10.0.0.41:2181,10.0.0.41:2182,10.0.0.41:2183",
////                "example", "", "");
//
//        int batchSize = 1000;
//        int emptyCount = 0;
//        try {
//            connector.connect();
//            connector.subscribe(".*\\..*");
//            connector.rollback();
//            int totalEmtryCount = 1200;
//            while (emptyCount < totalEmtryCount) {
//                Message message = connector.getWithoutAck(batchSize); // 获取指定数量的数据
//                long batchId = message.getId();
//                int size = message.getEntries().size();
//                if (batchId == -1 || size == 0) {
//                    emptyCount++;
////                    System.out.println("empty count : " + emptyCount);
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                } else {
//                    emptyCount = 0;
//                    // System.out.printf("message[batchId=%s,size=%s] \n", batchId, size);
//
//                    System.out.println(message.getEntries());
//
//                    printEntry(message.getEntries());
//
//                }
//
//                connector.ack(batchId); // 提交确认
//                // connector.rollback(batchId); // 处理失败, 回滚数据
//            }
//
//            System.out.println("empty too many times, exit");
//        } finally {
//            connector.disconnect();
//        }
//    }
//
//    public void printEntry(List<Entry> entrys) {
//        for (Entry entry : entrys) {
//            if (entry.getEntryType() == EntryType.TRANSACTIONBEGIN
//                    || entry.getEntryType() == EntryType.TRANSACTIONEND
//                    || entry.getHeader().getEventType() == CanalEntry.EventType.QUERY) {
//                continue;
//            }
//
//            RowChange rowChage = null;
//            try {
//                rowChage = RowChange.parseFrom(entry.getStoreValue());
//            } catch (Exception e) {
//                throw new RuntimeException("ERROR ## parser of eromanga-event has an error , data:" + entry.toString(),
//                        e);
//            }
//
//            EventType eventType = rowChage.getEventType();
//
//            // 创建 索引
////            if (eventType == EventType.CREATE) {
////                Elastic elastic = new Elastic()
////                        .index(entry.getHeader().getSchemaName())
////                        .eventType(eventType.toString())
////                        .docType(entry.getHeader().getTableName());
////                new ElasticBizImpl().addDocType(elastic);
////            }
//            System.err.println(String.format("================> binlog[%s:%s] , name[%s,%s] , eventType : %s",
//                    entry.getHeader().getLogfileName(), entry.getHeader().getLogfileOffset(),
//                    entry.getHeader().getSchemaName(), entry.getHeader().getTableName(), eventType));
//
//
//            for (RowData rowData : rowChage.getRowDatasList()) {
//                if (eventType == EventType.DELETE) {
//                    printColumn(rowData.getBeforeColumnsList());
////                    elasticBiz.testInterface();
//                } else if (eventType == EventType.INSERT) {
//                    HashMap<String, Object> map = printColumn(rowData.getAfterColumnsList());
//                    Elastic elastic = new Elastic().map(map)
//                            .index(entry.getHeader().getSchemaName())
//                            .eventType(eventType.toString())
//                            .docType(entry.getHeader().getTableName());
////                    new ElasticBizImpl().insertDataInfo(elastic);
//                } else {
////                    System.out.println("-------> before");
////                    printColumn(rowData.getBeforeColumnsList());
////                    System.out.println("-------> after");
//                    HashMap<String, Object> map = printColumn(rowData.getAfterColumnsList());
//                    Elastic elastic = new Elastic().map(map)
//                            .index(entry.getHeader().getSchemaName())
//                            .eventType(eventType.toString())
//                            .docType(entry.getHeader().getTableName());
////                    new ElasticBizImpl().insertDataInfo(elastic);
//                }
//            }
//        }
//    }
//
//    public HashMap<String, Object> printColumn(List<Column> columns) {
//        // 将数据转成map对象
//        HashMap<String, Object> map = new HashMap<>();
//        if (columns != null && columns.size() > 0) {
//            for (CanalEntry.Column column : columns) {
//                map.put(column.getName(), column.getValue());
//            }
//        }
//        System.err.println(map);
//        for (Column column : columns) {
//            System.out.println(column.getName() + " : " + column.getValue() + "    update=" + column.getUpdated());
//        }
//
//        return map;
//
//    }
//
//}
