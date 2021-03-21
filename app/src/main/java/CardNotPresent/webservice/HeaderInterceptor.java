package CardNotPresent.webservice;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HeaderInterceptor implements Interceptor {

    //    SharedPreferences prefs;
    private String token;
    private int methodWay;

    public HeaderInterceptor(String token, int methodWay) {
        this.token = token;
        this.methodWay = methodWay;
    }

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {

        //TODO Header switch(Header Interceptor) for Retrofit requests
        switch (methodWay) {
            case 0:
                Request request0 = chain.request();
                Request.Builder requestBuilder0 = request0.newBuilder();
                requestBuilder0.header("Content-Type", "application/json");
                request0 = requestBuilder0.build();
                return chain.proceed(request0);

            case 1:
                Request request1 = chain.request();
                Request.Builder requestBuilder1 = request1.newBuilder();
                requestBuilder1.header("X-CLIENT-TOKEN", token);
                request1 = requestBuilder1.build();
                return chain.proceed(request1);

            case 2:
                Request request2 = chain.request();
                Request.Builder requestBuilder2 = request2.newBuilder();
                requestBuilder2
                        .header("X-CLIENT-TOKEN", token)
                        .header("Authorization", "Basic YWRtaW46MTIzNDU2") //YWRtaW46MTIzNDU2
                        .header("Content-Type", "application/json");//Authorization  BasicAuth
                request2 = requestBuilder2.build();
                return chain.proceed(request2);

        }


        return null;

    }
}
