package com.xzg.cn.configure;

import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSocketFactory;
import java.time.Duration;
import java.util.Optional;

public class JedisClientConfig implements JedisClientConfiguration {
    @Override
    public boolean isUseSsl() {
        return false;
    }

    @Override
    public Optional<SSLSocketFactory> getSslSocketFactory() {
        return null;
    }

    @Override
    public Optional<SSLParameters> getSslParameters() {
        return null;
    }

    @Override
    public Optional<HostnameVerifier> getHostnameVerifier() {
        return null;
    }

    @Override
    public boolean isUsePooling() {
        return false;
    }

    @Override
    public Optional<org.apache.commons.pool2.impl.GenericObjectPoolConfig> getPoolConfig() {
        return null;
    }


    @Override
    public Optional<String> getClientName() {
        return Optional.of("");
    }

    @Override
    public Duration getConnectTimeout() {
        return Duration.ofMillis(1000);
    }

    @Override
    public Duration getReadTimeout() {
        return null;
    }
}
