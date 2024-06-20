package com.sicredi.assembleia.infraestructure.redis.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@EnableRedisRepositories(basePackages = "com.sicredi.assembleia.core.repository")
@Configuration
public class RedisConfig {
}
