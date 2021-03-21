package CardNotPresent.All_AbsentPayment.AbsentPaymentTransaction;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import CardNotPresent.All_AbsentPayment.AbsentPaymentLinkList.MerchantModel;
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

public class LinkViewModel extends ViewModel {

    private MutableLiveData<List<LinkModel>> perTransModelList;
    private String receivedToken = "";
    @SuppressLint("StaticFieldLeak")
    LinearLayout linearLayout;
    MerchantModel merchantModelTemp;
    Context context;


    public LinkViewModel() {
    }

    public LinkViewModel(Context context) {
        this.context=context;
    }

    public void findLiveDatas() {
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

//                createAndSendLinkWithAccessToken();

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

        Link_ResultStatus_Model linkResultStatusModel = new Link_ResultStatus_Model(USER_ID);

        Call<Link_ResultStatus_Model> callPost = jsonPlaceHolderApi.getLinkTransactionData(linkResultStatusModel);
        callPost.enqueue(new Callback<Link_ResultStatus_Model>() {
            @Override
            public void onResponse(Call<Link_ResultStatus_Model> call, @NotNull Response<Link_ResultStatus_Model> response) {
                if (!response.isSuccessful()) {
                    Log.d("Response Not Successful", "Response Code: " + response.code());
                    return;
                }

                Log.d("Response Successful", "Response Code: " + response.code());


                Link_ResultStatus_Model userData = response.body();

                assert userData != null;
                if (userData.getUsernameValidation().equals("true")) {
                    List<LinkModel> pModelList = new ArrayList<>();
                    for (int i = 0; i < userData.getLinkModelList().size(); i++)
                    {
                        LinkModel perModel = userData.getLinkModelList().get(i);

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
            public void onFailure(Call<Link_ResultStatus_Model> call, Throwable t) {
                Log.d("Response Failure", "Failure Code: " + t.getMessage());
            }
        });
    }

//    public void createAndSendLinkWithAccessToken() {
//
//        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .connectTimeout(30, TimeUnit.SECONDS)
//                .writeTimeout(30, TimeUnit.SECONDS)
//                .readTimeout(30, TimeUnit.SECONDS)
//                .retryOnConnectionFailure(true)
//                .addNetworkInterceptor(new HeaderInterceptor(receivedToken, 2))
//                .build();
//
//        //TODO API updated
//        //This is the last api for getting token from the server http://178.252.189.82:8173/api/api/WebApi/ it has changed to the one below
//        //test --> http://178.252.189.83/api/api/WebApi/GetToken
//        //New api https://bcard.totanservice.com/API/api/WebApi
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("https://bcard.totanservice.com/API/api/WebApi/")
//                .client(okHttpClient)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
//
//        Link_ResultStatus_Model merchantModel = new Link_ResultStatus_Model(USER_ID);
//
//        Call<Link_ResultStatus_Model> callPost = jsonPlaceHolderApi.getAllLinks(merchantModel);
//        callPost.enqueue(new Callback<Link_ResultStatus_Model>() {
//            @Override
//            public void onResponse(Call<Link_ResultStatus_Model> call, @NotNull Response<Link_ResultStatus_Model> response) {
//                if (!response.isSuccessful()) {
//                    Log.d("Response Not Successful", "Response Code: " + response.code());
//                    return;
//                }
//
//                Log.d("Response Successful", "Response Code: " + response.code());
//
//
//                Link_ResultStatus_Model userData = response.body();
//
//                if (userData != null && receivedToken.length() == 32) {
//                    for (int i = 0; i < userData.getMerchantModels().size(); i++) {
//                        MerchantModel merchantModels = userData.getMerchantModels().get(i);
//
//                        STATIC_SHARE_BODY =
//                                "نام PSP : " + merchantModels.getPspName() + "\n" +
//                                        "لینک پرداخت : " + merchantModels.getPaymentLink() + "\n";
//                    }
//
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<Link_ResultStatus_Model> call, Throwable t) {
//                Log.d("Response Failure", "Failure Code: " + t.getMessage());
//            }
//        });
//
//    }

    public MutableLiveData<List<LinkModel>> getPerTransModelList() {
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