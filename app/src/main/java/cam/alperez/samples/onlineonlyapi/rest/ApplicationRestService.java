package cam.alperez.samples.onlineonlyapi.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import cam.alperez.samples.onlineonlyapi.BuildConfig;
import cam.alperez.samples.onlineonlyapi.rest.json.ApiGsonTypeAdapterFactory;
import cam.alperez.samples.onlineonlyapi.rest.utils.LiveDataRetrofitCallAdapterFactory;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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

    Gson customGson = new GsonBuilder()
            .registerTypeAdapterFactory(new ApiGsonTypeAdapterFactory())
            .create();

    /*static final*/
    ApplicationRestService INSTANCE = new Retrofit.Builder()
            .baseUrl(BuildConfig.BACKEND_API_URL)
            .client(HttpClientProvider.forParameters(httpClientParams).getHttpClient())
            .addConverterFactory(GsonConverterFactory.create(customGson))
            .addCallAdapterFactory(new LiveDataRetrofitCallAdapterFactory())
            .build()
            .create(ApplicationRestService.class);
}
