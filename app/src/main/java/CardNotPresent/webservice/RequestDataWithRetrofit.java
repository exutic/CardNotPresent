package CardNotPresent.webservice;

import android.content.Context;
import android.util.Log;

import CardNotPresent.Main.Post;
import CardNotPresent.webservice.generate_links_inner_models.Model_PSP_List;
import CardNotPresent.webservice.generate_links_inner_models.Model_Terminal_List;
import CardNotPresent.webservice.generate_links_inner_models.ModelParent;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static CardNotPresent.Main.MainActivity.USER_ID;
import static CardNotPresent.before_main.Splash_Activity.WEB_API;

public class RequestDataWithRetrofit {
    private String username;
    private String password;
    private int methodWay;
    private Context context;
    private String receivedToken = "";
    private String id;

    public ModelParent userDataPerson;
    public ModelParent userDataProduct;


    List<Model_PSP_List> modelPspList;
    List<Model_Terminal_List> modelTerminalLists;

    public RequestDataWithRetrofit(ModelParent userDataPerson) {
        this.userDataPerson = userDataPerson;
    }
    public RequestDataWithRetrofit(String receivedToken, String id) {
        this.id = id;
        this.receivedToken = receivedToken;
    }
    public RequestDataWithRetrofit(int methodWay, Context context, String receivedToken) {
        this.methodWay = methodWay;
        this.context = context;
        this.receivedToken = receivedToken;
    }


    public void getGeneratedLinksForProduct_2(String id) {
        retrieveAccessTokenMethod_2(id);
    }
    private void retrieveAccessTokenMethod_2(final String id) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .addNetworkInterceptor(new HeaderInterceptor("", 0))
                .build();

        //TODO API updated
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(WEB_API)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        Post post = new Post("admin", "123456");
        Call<Post> callPost = jsonPlaceHolderApi.retrieveTokenInterface(post);
        callPost.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(@NotNull Call<Post> call, @NotNull Response<Post> response) {
                if (!response.isSuccessful()) {
                    Log.d("Response Not Successful", "Response Code: " + response.code());
                    return;
                }

                Log.d("Response Successful", "Response Code: " + response.code());

                Post tokenPost = response.body();
                if (tokenPost != null) {
                    String receivedToken = tokenPost.getAccessToken();
                    if (receivedToken.length() == 32) {
                        retrieveGeneratedLinksForPersonsWithAccessToken_2(receivedToken, id);
                    }
                    String s = "";
                }
            }

            @Override
            public void onFailure(@NotNull Call<Post> call, @NotNull Throwable t) {

                Log.d("Response Failure", "Failure Code: " + t.getMessage());

            }
        });


    }
    private void retrieveGeneratedLinksForPersonsWithAccessToken_2(String receivedToken, String id) {

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .addNetworkInterceptor(new HeaderInterceptor(receivedToken, 2))
                .build();

        //TODO API updated
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(WEB_API)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        ModelParent modelParent = new ModelParent(USER_ID, id);

        Call<ModelParent> callPost = jsonPlaceHolderApi.generateLink(modelParent);
        callPost.enqueue(new Callback<ModelParent>() {
            @Override
            public void onResponse(@NotNull Call<ModelParent> call, @NotNull Response<ModelParent> response) {
                if (!response.isSuccessful()) {
                    Log.d("Response Not Successful", "Response Code: " + response.code());
                    return;
                }

                Log.d("Response Successful", "Response Code: " + response.code() + "Link Generated");


                userDataProduct = response.body();

                assert userDataProduct != null;
                List<Model_PSP_List> listPsp = userDataProduct.getModelPspLists_list();
                List<Model_Terminal_List> listTerminal = userDataProduct.getModelTerminalLists_list();

            }

            @Override
            public void onFailure(@NotNull Call<ModelParent> call, @NotNull Throwable t) {
                Log.d("Response Failure", "Failure Code: " + t.getMessage());
            }
        });
    }

}
