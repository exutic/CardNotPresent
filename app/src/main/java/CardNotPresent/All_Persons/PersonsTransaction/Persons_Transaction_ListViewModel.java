package CardNotPresent.All_Persons.PersonsTransaction;

import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import CardNotPresent.DateConverter;
import CardNotPresent.Main.Post;
import CardNotPresent.webservice.HeaderInterceptor;
import CardNotPresent.webservice.JsonPlaceHolderApi;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
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

public class Persons_Transaction_ListViewModel extends ViewModel {

    private MutableLiveData<List<PersonsTransModel>> perTransModelList;
    private String receivedToken = "";
    LinearLayout linearLayout;


    public Persons_Transaction_ListViewModel() {

    }

    public void findLivedatas() {
        linearLayout.setVisibility(View.VISIBLE);
        perTransModelList = new MutableLiveData<>();
        retrieveAccessTokenMethod();
    }


    private void retrieveAccessTokenMethod() {
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
                    receivedToken = tokenPost.getAccessToken();
                    if (receivedToken.length() == 32) {
                        retrievePersonsDataWithAccessToken(receivedToken);
                    }
                    String s = "";
                }

            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {

                Log.d("Response Failure", "Failure Code: " + t.getMessage());

            }
        });


    }

    private void retrievePersonsDataWithAccessToken(String receivedToken) {

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

        Model_ResultStatus_Per_Trans model_resultStatus_per_trans = new Model_ResultStatus_Per_Trans(USER_ID);

        Call<Model_ResultStatus_Per_Trans> callPost = jsonPlaceHolderApi.getPersonalTransactionListData(model_resultStatus_per_trans);
        callPost.enqueue(new Callback<Model_ResultStatus_Per_Trans>() {
            @Override
            public void onResponse(Call<Model_ResultStatus_Per_Trans> call, @NotNull Response<Model_ResultStatus_Per_Trans> response) {
                if (!response.isSuccessful()) {
                    Log.d("Response Not Successful", "Response Code: " + response.code());
                    return;
                }

                Log.d("Response Successful", "Response Code: " + response.code());


                Model_ResultStatus_Per_Trans userData = response.body();

                assert userData != null;
                if (userData.getUsernameValidation().equals("true")) {
                    List<PersonsTransModel> pModelList = new ArrayList<>();
                    for (int i = 0; i < userData.getPerTransModelList().size(); i++) {
                        PersonsTransModel perModel = userData.getPerTransModelList().get(i);

                        if (perModel.getTransactionDateTime() != null) {
                            String date = miladiDate_ConvertTO_PerisanDate(perModel.getTransactionDateTime());
                            perModel.setTransactionDateTime(date);
                        }

                        if (perModel.getIpgResponseCode() != null) {
                            String strTrim = perModel.getIpgResponseCode().trim();
                            if (strTrim.equals("00")) {
                                perModel.setIpgResponseCode("موفق");
                            } else {
                                perModel.setIpgResponseCode("ناموفق");
                            }
                        }
                        pModelList.add(perModel);
                    }
                    perTransModelList.setValue(pModelList);
                }
                linearLayout.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<Model_ResultStatus_Per_Trans> call, Throwable t) {
                Log.d("Response Failure", "Failure Code: " + t.getMessage());
            }
        });
    }

    public MutableLiveData<List<PersonsTransModel>> getPerTransModelList() {
        return perTransModelList;
    }

    public LinearLayout getLinearLayout() {
        return linearLayout;
    }

    public void setLinearLayout(LinearLayout linearLayout) {
        this.linearLayout = linearLayout;
    }

    private String miladiDate_ConvertTO_PerisanDate(String date) {
        String[] parts = date.split("T");
        String part1 = parts[0];
        String part2 = parts[1];

        String numberDate = part1.replaceAll("[^0-9]", "");

        String year = numberDate.substring(0, 4);
        String month = numberDate.substring(4, 6);
        String day = numberDate.substring(6, 8);

        DateConverter dateConverter = new DateConverter();
        dateConverter.gregorianToPersian(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));

        int PersianYear = dateConverter.getYear();
        int PersianMonth = dateConverter.getMonth();
        int PersianDay = dateConverter.getDay();

        year = String.valueOf(PersianYear);
        month = String.valueOf(PersianMonth);
        day = String.valueOf(PersianDay);

        StringBuilder builder = new StringBuilder();

        builder.append(part2 + "_");
        builder.append(year + "/");
        builder.append(month + "/");
        builder.append(day);

        return builder.toString();
    }
}