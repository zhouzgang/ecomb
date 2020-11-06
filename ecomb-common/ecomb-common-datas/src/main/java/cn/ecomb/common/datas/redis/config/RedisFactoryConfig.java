package cn.ecomb.common.datas.redis.config;

import io.lettuce.core.TimeoutOptions;
import io.lettuce.core.cluster.ClusterClientOptions;
import io.lettuce.core.cluster.ClusterTopologyRefreshOptions;
import io.lettuce.core.resource.ClientResources;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Configuration
@AutoConfigureAfter(RedisAutoConfiguration.class)
public class RedisFactoryConfig {

    @Autowired
    private RedisProperties redisProperties;

    @Autowired
    private ClientResources clientResources;

    @Bean(name = "lettuceConnectionFactory")
    public LettuceConnectionFactory myLettuceConnectionFactory() {
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        poolConfig.setMaxIdle(redisProperties.getLettuce().getPool().getMaxIdle());
        poolConfig.setMaxTotal(redisProperties.getLettuce().getPool().getMaxActive());
        poolConfig.setMinIdle(redisProperties.getLettuce().getPool().getMinIdle());
        poolConfig.setMaxWaitMillis(redisProperties.getLettuce().getPool().getMaxWait().toMillis());
        LettuceClientConfiguration lettuceClientConfiguration = LettucePoolingClientConfiguration.builder()
                .commandTimeout(redisProperties.getTimeout())
                .poolConfig(poolConfig)
                .shutdownTimeout(Duration.ZERO)
                .build();

        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration(redisProperties.getHost(),
                redisProperties.getPort());
        if(!StringUtils.isEmpty(redisProperties.getPassword())) {
            configuration.setPassword(RedisPassword.of(redisProperties.getPassword()));
        }
        configuration.setDatabase(redisProperties.getDatabase());
        return new LettuceConnectionFactory(configuration,lettuceClientConfiguration);
    }

    /**
     * 配置 cluster
     * @return
     */
//    @Bean(name = "clusterLettuceConnectionFactory")
    public LettuceConnectionFactory clusterLettuceConnectionFactory() {

        RedisClusterConfiguration clusterConfiguration = new RedisClusterConfiguration(redisProperties.getCluster().getNodes());
        clusterConfiguration.setPassword(RedisPassword.of(redisProperties.getPassword()));
        clusterConfiguration.setMaxRedirects(redisProperties.getCluster().getMaxRedirects());

        // 设置自动刷新集群拓扑
        ClusterTopologyRefreshOptions topologyRefreshOptions =
                ClusterTopologyRefreshOptions.builder()
                        .enableAllAdaptiveRefreshTriggers()
                        .adaptiveRefreshTriggersTimeout(Duration.ofSeconds(25))
                        .enablePeriodicRefresh(Duration.ofSeconds(20))
                        .build();
        LettuceClientConfiguration lettuceClientConfiguration = LettucePoolingClientConfiguration.builder()
                .commandTimeout(redisProperties.getTimeout())
                .clientResources(clientResources)
                .clientOptions(ClusterClientOptions.builder()
                        .timeoutOptions(TimeoutOptions.enabled(Duration.ofSeconds(10)))
                        .topologyRefreshOptions(topologyRefreshOptions).build())
                .shutdownTimeout(Duration.ZERO)
                .build();

        return new LettuceConnectionFactory(clusterConfiguration, lettuceClientConfiguration);
    }

}