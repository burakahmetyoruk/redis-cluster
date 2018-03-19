package com.burak.redis.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

import java.util.ArrayList;
import java.util.List;

@Configuration
@Profile("redis-sentinel")
public class RedisSentinelConfig {

    @Value("${redis.sentinel.master.name}")
    private String sentinelMasterName;

    @Value("#{'${redis.sentinel.ip.list}'.split(';')}")
    private List<String> sentinelList;

    @Bean
    public JedisConnectionFactory redisConnectionFactory() {
        JedisConnectionFactory redisConnectionFactory = new JedisConnectionFactory(redisSentinelConfiguration());
        return redisConnectionFactory;
    }

    @Bean
    public RedisSentinelConfiguration redisSentinelConfiguration() {
        RedisSentinelConfiguration redisSentinelConfiguration = new RedisSentinelConfiguration();
        redisSentinelConfiguration.master(sentinelMasterName);

        List<RedisNode> redisNodeList = getSentinelIpList(sentinelList);
        for(RedisNode redisNode : redisNodeList) {
            redisSentinelConfiguration.sentinel(redisNode);
        }
        return redisSentinelConfiguration;
    }

    public List<RedisNode> getSentinelIpList(List<String> sentinelList) {

        List<RedisNode> redisNodeList = new ArrayList<>();

        for(String sentinel : sentinelList) {

            String[] sentinelArray = sentinel.split(":");

            try {
                RedisNode redisNode = new RedisNode(sentinelArray[0], Integer.valueOf(sentinelArray[1]));
                redisNodeList.add(redisNode);
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            }
        }

        return redisNodeList;
    }
}
