package cam.alperez.samples.onlineonlyapi.rest;

import java.util.concurrent.TimeUnit;

import cam.alperez.samples.onlineonlyapi.BuildConfig;
import cam.alperez.samples.onlineonlyapi.rest.utils.LiveDataRetrofitCallAdapterFactory;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

public interface ApplicationRestService {

    HttpClientSetupParams httpClientParams = HttpClientSetupParams.builder()
            .setMaxIdleConnections(3)
            .setKeepAliveDuration(60)
            .setKeepAliveTimeUnit(TimeUnit.SECONDS)
            .setConnectTimeout(15)
            .setConnectTimeoutTimeUnit(TimeUnit.SECONDS)
            .setReadTimeout(20)
            .setReadTimeoutTimeUnit(TimeUnit.SECONDS)
            .setWriteTimeout(10)
            .setWriteTimeoutTimeUnit(TimeUnit.SECONDS)
            .setHttpLoggingLevelOnDebug(HttpLoggingInterceptor.Level.BODY)
            .setUpstreamUseGzip(!BuildConfig.DEBUG)
            .setHeaderUserAgent("Stanislav.Perchenko")
            .setHeaderAccept("application/json")
            .setHeaderConnection("keep-alive")
            .build();

    /*static final*/
    ApplicationRestService INSTANCE = new Retrofit.Builder()
            .baseUrl(BuildConfig.BACKEND_API_URL)
            .client(HttpClientProvider.forParameters(httpClientParams).getHttpClient())
            .addConverterFactory(null) //TODO Provide proper converter factory with all adapters
            .addCallAdapterFactory(new LiveDataRetrofitCallAdapterFactory())
            .build()
            .create(ApplicationRestService.class);
}
