package CardNotPresent.Settings;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import CardNotPresent.Main.Post;
import CardNotPresent.R;
import CardNotPresent.webservice.HeaderInterceptor;
import CardNotPresent.webservice.JsonPlaceHolderApi;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static CardNotPresent.Home.HomePanel.changePasswordFinishPage;
import static CardNotPresent.Main.MainActivity.USER_ID;
import static CardNotPresent.before_main.Splash_Activity.WEB_API;

public class Settings_Activity extends AppCompatActivity {

    EditText edt_current_pass, edt_new_pass, edt_again_new_pass;
    Button btn_submit;
    boolean online = false;
    String receivedToken = "";
    Change_Password_Model change_password_model;
    LinearLayout linearLayout;

    @Override
    protected void attachBaseContext(Context newBase) {
        try {
            super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_);

        set_Init();
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                getDataFromEditText();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void getDataFromEditText() {
        if (edt_current_pass.length() >= 6 && edt_new_pass.length() >= 6 && edt_again_new_pass.length() >= 6) {
            if (edt_new_pass.getText().toString().compareTo(edt_again_new_pass.getText().toString()) == 0) {
                change_password_model = new Change_Password_Model();
                change_password_model.setCurrentPassword(edt_current_pass.getText().toString());
                change_password_model.setNewPassword(edt_new_pass.getText().toString());
                change_password_model.setConfirmPassword(edt_again_new_pass.getText().toString());
                change_password_model.setUserId(USER_ID);

                online = isOnline(Settings_Activity.this);
                if (online) {
                    linearLayout.setVisibility(View.VISIBLE);
                    btn_submit.setEnabled(false);
                    retrieveAccessTokenMethod();
                } else {
                    btn_submit.setEnabled(true);
                    Toast.makeText(Settings_Activity.this, "عدم اتصال به اینترنت", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "در تکرار رمز جدید مغایرت وجود دارد", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "حداقل رمز عبور 6 حرف میباشد", Toast.LENGTH_SHORT).show();
        }
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
                    btn_submit.setEnabled(true);
                    return;
                }

                Log.d("Response Successful", "Response Code: " + response.code());

                Post tokenPost = response.body();
                if (tokenPost != null) {
                    receivedToken = tokenPost.getAccessToken();
                    if (receivedToken.length() == 32) {
                        change_Password_WithAccessToken(receivedToken);
                    }
                }

            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {

                Log.d("Response Failure", "Failure Code: " + t.getMessage());

            }
        });
    }

    private void change_Password_WithAccessToken(String receivedToken) {
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


        Call<Change_Password_Model> modelCall = jsonPlaceHolderApi.changePasswordWithAccessToken(change_password_model);
        modelCall.enqueue(new Callback<Change_Password_Model>() {
            @Override
            public void onResponse(@NotNull Call<Change_Password_Model> call, @NotNull Response<Change_Password_Model> response) {
                if (!response.isSuccessful()) {
                    Log.d("Response Not Successful", "Response Code: " + response.code());
                    btn_submit.setEnabled(true);
                    return;
                }

                Log.d("Response Successful", "Response Code: " + response.code());
                Change_Password_Model userData = response.body();


                assert userData != null;
                if (userData.getResultStatus().equals("true")) {
                    linearLayout.setVisibility(View.GONE);
                    Toast.makeText(Settings_Activity.this, "تغییر رمز با موفقیت انجام شد", Toast.LENGTH_SHORT).show();
                    changePasswordFinishPage = 1;
                    Intent intent = new Intent();
//                    intent.putExtra("Result","true");
                    setResult(200, intent);
                    finish();
                } else {
                    Toast.makeText(Settings_Activity.this, userData.getDescription(), Toast.LENGTH_SHORT).show();
                    linearLayout.setVisibility(View.GONE);
                    changePasswordFinishPage = 1;
                }
                btn_submit.setEnabled(true);
            }

            @Override
            public void onFailure(@NotNull Call<Change_Password_Model> call, @NotNull Throwable t) {
                Log.d("onFailure ProfileVModel", "onFailure: " + t.getMessage());
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static boolean isOnline(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        @SuppressLint("MissingPermission") NetworkInfo netInfo = Objects.requireNonNull(cm).getActiveNetworkInfo();

        return (netInfo != null && netInfo.isConnected());
    }

    private void set_Init() {
        linearLayout = findViewById(R.id.actv_setting_include__waiting_dialog);
        btn_submit = findViewById(R.id.btn_submit_change_pass);
        edt_current_pass = findViewById(R.id.edt_current_password_change_pass);
        edt_new_pass = findViewById(R.id.edt_new_password_change_pass);
        edt_again_new_pass = findViewById(R.id.edt_again_new_password_change_pass);
    }
}
