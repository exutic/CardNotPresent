package CardNotPresent.Home.ui.Profile;

import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import CardNotPresent.Main.Post;
import CardNotPresent.webservice.HeaderInterceptor;
import CardNotPresent.webservice.JsonPlaceHolderApi;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static CardNotPresent.Main.MainActivity.USER_ID;
import static CardNotPresent.before_main.Splash_Activity.WEB_API;

public class ProfileViewModel extends ViewModel {

    private MutableLiveData<String> customerTypeId;
    private MutableLiveData<String> fullName;
    private MutableLiveData<String> nationalID;
    private MutableLiveData<String> Mobile;
    private MutableLiveData<String> phoneNumber;
    private MutableLiveData<String> Email;
    private MutableLiveData<String> homeAddress;
    private MutableLiveData<String> CompanyName;
    private LinearLayout linearLayout;
    String receivedToken = "";


    public ProfileViewModel() {

    }


    public void findLiveDatas() {
//        progressBar.setVisibility(View.VISIBLE);
        linearLayout.setVisibility(View.VISIBLE);
        customerTypeId = new MutableLiveData<>();
        fullName = new MutableLiveData<>();
        nationalID = new MutableLiveData<>();
        Mobile = new MutableLiveData<>();
        phoneNumber = new MutableLiveData<>();
        Email = new MutableLiveData<>();
        homeAddress = new MutableLiveData<>();
        CompanyName = new MutableLiveData<>();

        retrieveAccessTokenMethod();

    }

    private void retrieveAccessTokenMethod() {


        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .addNetworkInterceptor(new HeaderInterceptor(receivedToken, 0))
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
                        loginToPortalWithAccessToken(receivedToken);
                    }
                }

            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {

                Log.d("Response Failure", "Failure Code: " + t.getMessage());

            }
        });
    }

    private void loginToPortalWithAccessToken(String receivedToken) {


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
        ModelParentProfile profileModel = new ModelParentProfile(USER_ID);
        Call<ModelParentProfile> modelCall = jsonPlaceHolderApi.retrieveProfileData(profileModel);
        modelCall.enqueue(new Callback<ModelParentProfile>() {
            @Override
            public void onResponse(@NotNull Call<ModelParentProfile> call, @NotNull Response<ModelParentProfile> response) {
                if (!response.isSuccessful()) {
                    Log.d("Response Not Successful", "Response Code: " + response.code());
                    return;
                }

                Log.d("Response Successful", "Response Code: " + response.code());
                ModelParentProfile userData = response.body();


                assert userData != null;
                if (userData.getResultStatus().equals("true")) {
                    ProfileModel profileModel1 = userData.getProfileModel();
                    if (profileModel1.getCustomerTypeId().equals("1305")) {
                        nationalID.setValue(profileModel1.getNationalID());
                        CompanyName.setValue("");
                    } else if (profileModel1.getCustomerTypeId().equals("1306")) {
                        CompanyName.setValue(profileModel1.getCompanyCode());
                        nationalID.setValue("");
                    }

                    fullName.setValue(profileModel1.getFullName());
                    Mobile.setValue(profileModel1.getMobile());
                    phoneNumber.setValue(profileModel1.getPhoneNumber());
                    Email.setValue(profileModel1.getEmail());
                    homeAddress.setValue(profileModel1.getHomeAddress());
//                    progressBar.setVisibility(View.GONE);
                    linearLayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(@NotNull Call<ModelParentProfile> call, @NotNull Throwable t) {
                String s = "";
                Log.d("onFailure ProfileVModel", "onFailure: " + t.getMessage());
            }
        });
    }

    public MutableLiveData<String> getCustomerTypeId() {
        return customerTypeId;
    }

    public MutableLiveData<String> getFullName() {
        return fullName;
    }

    public MutableLiveData<String> getNationalID() {
        return nationalID;
    }

    public MutableLiveData<String> getMobile() {
        return Mobile;
    }

    public MutableLiveData<String> getPhoneNumber() {
        return phoneNumber;
    }

    public MutableLiveData<String> getEmail() {
        return Email;
    }

    public MutableLiveData<String> getHomeAddress() {
        return homeAddress;
    }

    public MutableLiveData<String> getCompanyName() {
        return CompanyName;
    }

    public LinearLayout getLinearLayout() {
        return linearLayout;
    }

    public void setLinearLayout(LinearLayout linearLayout) {
        this.linearLayout = linearLayout;
    }
}
