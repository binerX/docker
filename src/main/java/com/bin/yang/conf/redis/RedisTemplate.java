package com.bin.yang.conf.redis;

import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnection;

/**
 * @Auther: bin.yang
 * @Date: 2018/12/29 10:13
 * @Description:
 */
public class RedisTemplate extends org.springframework.data.redis.core.RedisTemplate {



    public static ThreadLocal<Integer> REDIS_DB_INDEX = new ThreadLocal<Integer>(){
        @Override
        protected Integer initialValue() {
            return 5;   // 这里才是配置数据库的地方 ....
        }
    };

    @Override
    protected RedisConnection preProcessConnection(RedisConnection connection, boolean existingConnection) {
        try {
            Integer dbIndex = REDIS_DB_INDEX.get();
            //如果设置了dbIndex
            if (dbIndex != null) {
                if (connection instanceof JedisConnection) {

                    if (((JedisConnection) connection).getNativeConnection().getDB().intValue() != dbIndex) {
                        connection.select(dbIndex);
                    }


                } else {
                    connection.select(dbIndex);
                }
            } else {
                connection.select(5);  // 同上 初始数据库
            }
        } finally {
            REDIS_DB_INDEX.remove();
        }
        return super.preProcessConnection(connection, existingConnection);
    }

}
