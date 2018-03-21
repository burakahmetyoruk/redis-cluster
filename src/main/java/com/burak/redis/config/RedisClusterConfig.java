package com.burak.redis.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

@Configuration
@RequiredArgsConstructor
public class RedisClusterConfig {

        private final ClusterConfigProperties clusterConfigurationProperties;

        @Bean
        public JedisConnectionFactory redisConnectionFactory() {
            JedisConnectionFactory redisConnectionFactory = new JedisConnectionFactory(
                    new RedisClusterConfiguration(clusterConfigurationProperties.getNodes()));
            return redisConnectionFactory;
        }

        @Bean
        public RedisTemplate<String, String> redisTemplate() {
            RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
            redisTemplate.setConnectionFactory(redisConnectionFactory());
            redisTemplate.setKeySerializer(jackson2JsonRedisSerializer());
            redisTemplate.setValueSerializer(jackson2JsonRedisSerializer());
            return redisTemplate;
        }

        @Bean
        public Jackson2JsonRedisSerializer jackson2JsonRedisSerializer() {
            return new Jackson2JsonRedisSerializer(String.class);
        }
}
