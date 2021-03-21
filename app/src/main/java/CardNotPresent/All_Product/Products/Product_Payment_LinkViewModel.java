package CardNotPresent.All_Product.Products;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
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

public class Product_Payment_LinkViewModel extends ViewModel {

    private MutableLiveData<List<ProductModel>> modelArrayList;
    private String receivedToken = "";
    private int request_Add_Product = 0;
    ProductModel productModel;
//    AVLoadingIndicatorView progressBar;
    private Product_Payment_linkFragment product_payment_linkFragment;

    LinearLayout linearLayout;

    public Product_Payment_LinkViewModel() {

    }

    public void findLiveData()
    {
        linearLayout.setVisibility(View.VISIBLE);
        modelArrayList = new MutableLiveData<>();
        retrieveAccessTokenMethod();
    }


    public void requestAddProduct(ProductModel productModel) {

        linearLayout.setVisibility(View.VISIBLE);
        setProductModel(productModel);
        retrieveAccessTokenMethod();
        request_Add_Product = 1;
    }

    public void requestDeleteProduct(ProductModel productModel) {
        linearLayout.setVisibility(View.VISIBLE);
        setProductModel(productModel);
        retrieveAccessTokenMethod();
        request_Add_Product = 2;
    }

    public void retrieveAccessTokenMethod() {

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
                if (tokenPost != null) {
                    receivedToken = tokenPost.getAccessToken();
                    if (receivedToken.length() == 32) {
                        switch (request_Add_Product) {
                            case 0:
                                retrievePersonsDataWithAccessToken(receivedToken);
                                linearLayout.setVisibility(View.GONE);
                                break;

                            case 1:

                                linearLayout.setVisibility(View.VISIBLE);
                                retrievePersonsDataWithAccessToken_AddProduct(receivedToken);
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        retrievePersonsDataWithAccessToken(receivedToken);
                                        linearLayout.setVisibility(View.GONE);
                                    }
                                }, 2000);
                                request_Add_Product = 0;
                                break;

                            case 2:
                                linearLayout.setVisibility(View.VISIBLE);
                                retrievePersonsDataWithAccessToken_DeleteProduct(receivedToken);
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        retrievePersonsDataWithAccessToken(receivedToken);
                                        linearLayout.setVisibility(View.GONE);
                                    }
                                }, 2000);
                                request_Add_Product = 0;
                                break;
                        }
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

        Model_ResulrStatus_product model_resultStatus = new Model_ResulrStatus_product(USER_ID);

        Call<Model_ResulrStatus_product> callPost = jsonPlaceHolderApi.getProductListData(model_resultStatus);
        callPost.enqueue(new Callback<Model_ResulrStatus_product>() {
            @Override
            public void onResponse(Call<Model_ResulrStatus_product> call, @NotNull Response<Model_ResulrStatus_product> response) {
                if (!response.isSuccessful()) {
                    Log.d("Response Not Successful", "Response Code: " + response.code());
                    return;
                }

                Log.d("Response Successful", "Response Code: " + response.code());


                Model_ResulrStatus_product userData = response.body();
                assert userData != null;
                if (userData.getUsernameValidation().equals("true"))
                {
                    List<ProductModel> list = new ArrayList<>();
                    for (int i = 0; i < userData.getProductModelList().size(); i++)
                    {
                        ProductModel model = userData.getProductModelList().get(i);
                        if (model.getInsertDateTime()!=null)
                        {
                            String date = miladiDate_ConvertTO_PerisanDate(model.getInsertDateTime());
                            model.setInsertDateTime(date);
                        }
                        list.add(model);

                    }
                    modelArrayList.setValue(list);
                    String s = "";
                }

            }

            @Override
            public void onFailure(Call<Model_ResulrStatus_product> call, Throwable t) {
                Log.d("Response Failure", "Failure Code: " + t.getMessage());
            }
        });
    }

    private void retrievePersonsDataWithAccessToken_AddProduct(String receivedToken) {

        linearLayout.setVisibility(View.VISIBLE);
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

        Call<ProductModel> callPost = jsonPlaceHolderApi.addProduct(getProductModel());
        callPost.enqueue(new Callback<ProductModel>() {
            @Override
            public void onResponse(Call<ProductModel> call, @NotNull Response<ProductModel> response) {
                if (!response.isSuccessful()) {
                    Log.d("Response Not Successful", "Response Code: " + response.code());
                    return;
                }

                Log.d("Response Successful", "Response Code: " + response.code());


                ProductModel userData = response.body();
                assert userData != null;
                if (userData.getResultStatus().equals("True")) {
                    Toast.makeText(product_payment_linkFragment.getActivity(), "اطلاعات ثبت شد", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ProductModel> call, Throwable t) {
                Log.d("Response Failure", "Failure Code: " + t.getMessage());
            }
        });
    }

    private void retrievePersonsDataWithAccessToken_DeleteProduct(String receivedToken) {
        linearLayout.setVisibility(View.VISIBLE);
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

        Call<ProductModel> callPost = jsonPlaceHolderApi.deleteProductrData(productModel);
        callPost.enqueue(new Callback<ProductModel>() {
            @Override
            public void onResponse(Call<ProductModel> call, @NotNull Response<ProductModel> response) {
                if (!response.isSuccessful()) {
                    Log.d("Response Not Successful", "Response Code: " + response.code());
                    return;
                }

                Log.d("Response Successful", "Response Code: " + response.code());


                ProductModel userData = response.body();
                assert userData != null;
                if (userData.getResultStatus().equals("True"))
                {
                    Toast.makeText(product_payment_linkFragment.getActivity(), "فایل حذف شد", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ProductModel> call, Throwable t) {
                Log.d("Response Failure", "Failure Code: " + t.getMessage());
            }
        });
    }


    public LiveData<List<ProductModel>> getModelArrayList() {
        return modelArrayList;
    }

    public ProductModel getProductModel() {
        return productModel;
    }

    public void setProductModel(ProductModel productModel) {
        this.productModel = productModel;
    }


    public void setProduct_payment_linkFragment(Product_Payment_linkFragment product_payment_linkFragment) {
        this.product_payment_linkFragment = product_payment_linkFragment;
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

        builder.append(part2 +"_");
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
