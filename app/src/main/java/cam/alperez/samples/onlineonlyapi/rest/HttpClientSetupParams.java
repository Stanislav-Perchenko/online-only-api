package cam.alperez.samples.onlineonlyapi.rest;

import androidx.annotation.Nullable;

import com.google.auto.value.AutoValue;

import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.logging.HttpLoggingInterceptor;

@AutoValue
public abstract class HttpClientSetupParams {
    public abstract Integer maxIdleConnections();
    public abstract Integer keepAliveDuration();
    public abstract TimeUnit keepAliveTimeUnit();
    public abstract Integer connectTimeout();
    public abstract TimeUnit connectTimeoutTimeUnit();
    public abstract Integer readTimeout();
    public abstract TimeUnit readTimeoutTimeUnit();
    public abstract Integer writeTimeout();
    public abstract TimeUnit writeTimeoutTimeUnit();
    public abstract HttpLoggingInterceptor.Level httpLoggingLevelOnDebug(); //HttpLoggingInterceptor.Level.BODY
    public abstract Boolean upstreamUseGzip();

    public abstract String headerUserAgent();
    public abstract String headerAccept();
    public abstract String headerAcceptEncoding();
    public abstract String headerConnection();
    public abstract String headerContentType();

    @Nullable
    public abstract Interceptor authInterceptor();

    public static Builder builder() {
        return new AutoValue_HttpClientSetupParams.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder setMaxIdleConnections(Integer maxIdleConnections);
        public abstract Builder setKeepAliveDuration(Integer keepAliveDuration);
        public abstract Builder setKeepAliveTimeUnit(TimeUnit keepAliveTimeUnit);
        public abstract Builder setConnectTimeout(Integer connectTimeout);
        public abstract Builder setConnectTimeoutTimeUnit(TimeUnit connectTimeoutTimeUnit);
        public abstract Builder setReadTimeout(Integer readTimeout);
        public abstract Builder setReadTimeoutTimeUnit(TimeUnit readTimeoutTimeUnit);
        public abstract Builder setWriteTimeout(Integer writeTimeout);
        public abstract Builder setWriteTimeoutTimeUnit(TimeUnit writeTimeoutTimeUnit);
        public abstract Builder setHttpLoggingLevelOnDebug(HttpLoggingInterceptor.Level httpLoggingLevelOnDebug);
        public abstract Builder setUpstreamUseGzip(Boolean upstreamUseGzip);
        public abstract Builder setHeaderUserAgent(String headerUserAgent);
        public abstract Builder setHeaderAccept(String headerAccept);
        public abstract Builder setHeaderAcceptEncoding(String headerAcceptEncoding);
        public abstract Builder setHeaderConnection(String headerConnection);
        public abstract Builder setHeaderContentType(String headerContentType);
        public abstract Builder setAuthInterceptor(Interceptor authInterceptor);

        public abstract HttpClientSetupParams build();
    }
}
