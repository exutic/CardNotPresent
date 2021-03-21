package CardNotPresent.All_Persons.Persons;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

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

public class Persons_Payment_LinkViewModel extends ViewModel {

    //    private MutableLiveData<String> mText;
    private MutableLiveData<List<PersonsModel>> modelArrayList;
    private String receivedToken = "";
    private int request_Add_Person = 0;
    private PersonsModel personsModel;
//    AVLoadingIndicatorView progressBar;

    LinearLayout linearLayout;

    private Persons_Payment_LinkFragment persons_payment_linkFragment;

    public Persons_Payment_LinkViewModel() {

    }

    public void findLiveDatas() {
        linearLayout.setVisibility(View.VISIBLE);
        modelArrayList = new MutableLiveData<>();
        retrieveAccessTokenMethod();
    }

    public void requestAddPerson(PersonsModel personsModel) {
        setPersonsModel(personsModel);
        linearLayout.setVisibility(View.VISIBLE);
        retrieveAccessTokenMethod();
        request_Add_Person = 1;
    }

    public void requestDeletePerson(PersonsModel personsModel) {
        setPersonsModel(personsModel);
        linearLayout.setVisibility(View.VISIBLE);
        retrieveAccessTokenMethod();
        request_Add_Person = 2;
    }

    private void retrievePersonsDataWithAccessToken_DeletePerson(String receivedToken) {

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

        Call<PersonsModel> callPost = jsonPlaceHolderApi.deleteUserData(personsModel);
        callPost.enqueue(new Callback<PersonsModel>() {
            @Override
            public void onResponse(Call<PersonsModel> call, @NotNull Response<PersonsModel> response) {
                if (!response.isSuccessful()) {
                    Log.d("Response Not Successful", "Response Code: " + response.code());
                    return;
                }

                Log.d("Response Successful", "Response Code: " + response.code());


                PersonsModel userData = response.body();
                assert userData != null;
                if (userData.getResultStatus().equals("True")) {
                    Toast.makeText(persons_payment_linkFragment.getActivity(), "فایل حذف شد", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<PersonsModel> call, Throwable t) {
                Log.d("Response Failure", "Failure Code: " + t.getMessage());
            }
        });
    }

    private void retrievePersonsDataWithAccessToken_AddPerson(String receivedToken) {

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

        Call<PersonsModel> callPost = jsonPlaceHolderApi.addPerson(getPersonsModel());
        callPost.enqueue(new Callback<PersonsModel>() {
            @Override
            public void onResponse(Call<PersonsModel> call, @NotNull Response<PersonsModel> response) {
                if (!response.isSuccessful()) {
                    Log.d("Response Not Successful", "Response Code: " + response.code());
                    return;
                }

                Log.d("Response Successful", "Response Code: " + response.code());


                PersonsModel userData = response.body();
                assert userData != null;
                if (userData.getResultStatus().equals("True")) {
                    Toast.makeText(persons_payment_linkFragment.getActivity(), "اطلاعات ثبت شد", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<PersonsModel> call, Throwable t) {
                Log.d("Response Failure", "Failure Code: " + t.getMessage());
            }
        });
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
            public void onResponse(Call<Post> call, @NotNull Response<Post> response) {
                if (!response.isSuccessful()) {
                    Log.d("Response Not Successful", "Response Code: " + response.code());
                    return;
                }

                Log.d("Response Successful", "Response Code: " + response.code());

                Post tokenPost = response.body();

                assert tokenPost != null;
                receivedToken = tokenPost.getAccessToken();
                if (receivedToken.length() == 32) {
                    switch (request_Add_Person) {
                        case 0:
                            retrievePersonsDataWithAccessToken(receivedToken);
                            linearLayout.setVisibility(View.GONE);
                            break;

                        case 1:
                            retrievePersonsDataWithAccessToken_AddPerson(receivedToken);
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    retrievePersonsDataWithAccessToken(receivedToken);
                                    linearLayout.setVisibility(View.GONE);
                                }
                            }, 2000);
                            request_Add_Person = 0;
                            break;

                        case 2:
                            retrievePersonsDataWithAccessToken_DeletePerson(receivedToken);
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    retrievePersonsDataWithAccessToken(receivedToken);
                                    linearLayout.setVisibility(View.GONE);
                                }
                            }, 2000);
                            request_Add_Person = 0;
                            break;
                    }
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

        Model_ResultStatus model_resultStatus = new Model_ResultStatus(USER_ID);

        Call<Model_ResultStatus> callPost = jsonPlaceHolderApi.getPersonalListData(model_resultStatus);
        callPost.enqueue(new Callback<Model_ResultStatus>() {
            @Override
            public void onResponse(@NotNull Call<Model_ResultStatus> call, @NotNull Response<Model_ResultStatus> response) {
                if (!response.isSuccessful()) {
                    Log.d("Response Not Successful", "Response Code: " + response.code());
                    return;
                }

                Log.d("Response Successful", "Response Code: " + response.code());


                Model_ResultStatus userData = response.body();


                assert userData != null;
                if (userData.getUsernameValidation().equals("true"))
                {
                    List<PersonsModel> list = new ArrayList<>();

                    for (int i = 0; i <userData.getPersonsModelList().size() ; i++)
                    {
                        PersonsModel personsModel  = userData.getPersonsModelList().get(i);
                        if (personsModel.getInsertDateTime()!=null)
                        {
                            String date = miladiDate_ConvertTO_PerisanDate(personsModel.getInsertDateTime());
                            personsModel.setInsertDateTime(date);
                        }
                        list.add(personsModel);
                    }
                    modelArrayList.setValue(list);
                    String s = "";
                }

            }

            @Override
            public void onFailure(@NotNull Call<Model_ResultStatus> call, Throwable t) {
                Log.d("Response Failure", "Failure Code: " + t.getMessage());
            }
        });
    }


    public MutableLiveData<List<PersonsModel>> getModelArrayList() {
        return modelArrayList;
    }

    public PersonsModel getPersonsModel() {
        return personsModel;
    }

    public void setPersonsModel(PersonsModel personsModel) {
        this.personsModel = personsModel;
    }

    public void setPersons_payment_linkFragment(Persons_Payment_LinkFragment persons_payment_linkFragment) {
        this.persons_payment_linkFragment = persons_payment_linkFragment;
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

        builder.append(part2+"_");
        builder.append(year + "/");
        builder.append(month + "/");
        builder.append(day);

        return builder.toString();
    }

    public LinearLayout getLinearLayout() {
        return linearLayout;
    }

    public void setLinearLayout(LinearLayout linearLayout) {
        this.linearLayout = linearLayout;
    }
}