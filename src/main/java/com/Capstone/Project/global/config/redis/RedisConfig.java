package com.Capstone.Project.global.config.redis;

import lombok.RequiredArgsConstructor;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.Optional;

@Configuration
@RequiredArgsConstructor
public class RedisConfig {
    private static final String REDIS_PROTOCOL_PREFIX = "redis://";
    private static final String REDISS_PROTOCOL_PREFIX = "rediss://";




    @Bean
    public RedissonClient redissonClient(RedisProperties redisProperties) {
        Config config = new Config();
        int timeout = Optional.ofNullable(redisProperties.getTimeout())
                .map(x -> Long.valueOf(x.toMillis()).intValue())
                .orElse(10000);

        String prefix = redisProperties.isSsl() ? REDISS_PROTOCOL_PREFIX : REDIS_PROTOCOL_PREFIX;

        SingleServerConfig singleServerConfig = config.useSingleServer()
                .setAddress(prefix + redisProperties.getHost() + ":" + redisProperties.getPort())
                .setConnectTimeout(timeout)
                .setDatabase(redisProperties.getDatabase());

        String password = redisProperties.getPassword();
        if (password != null && !password.isBlank()) {
            singleServerConfig.setPassword(redisProperties.getPassword());
        }

        return Redisson.create(config);
    }

    @Bean
    public LettuceConnectionFactory redisConnectionFactory(RedisProperties rp) {
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration(rp.getHost(), rp.getPort());
        String password = rp.getPassword();
        if (!password.isBlank()) {
            configuration.setPassword(password);
        }

        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(configuration);
        lettuceConnectionFactory.afterPropertiesSet();
        return lettuceConnectionFactory;
    }

    @Bean
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory){
        return new StringRedisTemplate(redisConnectionFactory);
    }
}
