package cam.alperez.samples.onlineonlyapi.rest;

import java.util.ArrayList;
import java.util.List;

import cam.alperez.samples.onlineonlyapi.BuildConfig;
import okhttp3.ConnectionPool;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;

public class HttpClientProvider {

    public static HttpClientProvider forParameters(HttpClientSetupParams parameters) {
        return new HttpClientProvider(parameters);
    }

    private HttpClientSetupParams params;

    private HttpClientProvider(HttpClientSetupParams parameters) {
        this.params = parameters;
    }

    private final Interceptor mDefaultHeadersInterceptor = (chain) -> {
        Request r = chain.request();

        Request.Builder rBld = r.newBuilder();

        final Headers currHeaders = r.headers();
        if (currHeaders.get("Accept") == null) {
            rBld.header("Accept", params.headerAccept());
        }
        if (currHeaders.get("Accept-Encoding") == null) {
            rBld.header("Accept-Encoding", params.headerAcceptEncoding());
        }
        if (currHeaders.get("Connection") == null) {
            rBld.header("Connection", params.headerConnection());
        }
        if (currHeaders.get("Content-Type") == null) {
            rBld.header("Content-Type", params.headerContentType());
        }

        return chain.proceed(rBld.build());
    };

    public OkHttpClient getHttpClient() {
        List<Protocol> protocols = new ArrayList<Protocol>(1);
        protocols.add(Protocol.HTTP_1_1);

        ConnectionPool cPool = new ConnectionPool(
                params.maxIdleConnections(),
                params.keepAliveDuration(),
                params.keepAliveTimeUnit());

        return new OkHttpClient.Builder()
                .connectionPool(cPool)
                .protocols(protocols)
                .connectTimeout(params.connectTimeout(), params.connectTimeoutTimeUnit())
                .readTimeout(params.readTimeout(), params.readTimeoutTimeUnit())
                .writeTimeout(params.writeTimeout(), params.writeTimeoutTimeUnit())
                .addNetworkInterceptor((chain) -> chain.proceed(
                        chain.request().newBuilder()
                                .removeHeader("User-Agent")
                                .header("User-Agent", params.headerUserAgent())
                                .build()
                ))
                .addNetworkInterceptor(mDefaultHeadersInterceptor)
                .addNetworkInterceptor(new HttpLoggingInterceptor().setLevel(
                        BuildConfig.DEBUG ? params.httpLoggingLevelOnDebug() : HttpLoggingInterceptor.Level.NONE))
                .build();
    }
}
