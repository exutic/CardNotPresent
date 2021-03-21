package CardNotPresent.Main;

import androidx.appcompat.app.AlertDialog;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import CardNotPresent.Home.HomePanel;
import CardNotPresent.R;
import CardNotPresent.permission.RuntimePermissionsActivity;
import CardNotPresent.webservice.HeaderInterceptor;
import CardNotPresent.webservice.JsonPlaceHolderApi;

import org.jetbrains.annotations.NotNull;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static CardNotPresent.before_main.Splash_Activity.WEB_API;


public class MainActivity extends RuntimePermissionsActivity {
    Animation animFadeOut, animFadeIn, animFadeIn500, animFadeOut500;
    EditText edt_userName, edt_password;
    Button btnLoginIntoPortal, btnPasswordRecovery, btnSignUp;

    private JsonPlaceHolderApi jsonPlaceHolderApi;
    private String receivedToken = "";
    private Retrofit retrofit;
    private OkHttpClient okHttpClient;
    public static String USER_ID = "";
    public static String USER_FULL_NAME = "";
    public static String USERNAME = "";
    public static String LOGIN_VALIDATION = "";
    public static int PERMISSION_VALIDATOR = 0;

    LinearLayout linearLayout;

    int backButtonCount = 0;
    AlertDialog dialog, dialog2;
    Forget_Password_Model forget_password_model;

    boolean online = false;
    boolean googleFlag;
    boolean checkInternet;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkInternet = isNetworkAvailable(getApplication());
        if (!checkInternet) {
            Toast.makeText(this, "عدم اتصال به اینترنت", Toast.LENGTH_SHORT).show();
        } else {
            retrieveAccessTokenMethod(1);
            Log.d("isNetworkAvailable", "onCreate: Internet is Connected");
        }

//        googleFlag = getPingGoogle();
//        if (!googleFlag)
//        {
//            Log.d("google flag", "onCreate: " +googleFlag);
//            Toast.makeText(MainActivity.this, "عدم اتصال به اینترنت", Toast.LENGTH_SHORT).show();
//
//        }

        try {
            findViews_SetInit();
            permissionManage();
            edt_userName.setEnabled(true);
            edt_password.setEnabled(true);
            btnLoginIntoPortal.setEnabled(true);
            btnPasswordRecovery.setEnabled(true);
            btnSignUp.setEnabled(true);

            edt_userName.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    String temp = s.toString();
                    List<String> persianNumbers = new ArrayList<>();
                    persianNumbers.add("۰");
                    persianNumbers.add("۱");
                    persianNumbers.add("۲");
                    persianNumbers.add("۴");
                    persianNumbers.add("۵");
                    persianNumbers.add("۶");
                    persianNumbers.add("۷");
                    persianNumbers.add("۸");
                    persianNumbers.add("۹");
                    persianNumbers.add("ا");
                    persianNumbers.add("ب");
                    persianNumbers.add("پ");
                    persianNumbers.add("ث");
                    persianNumbers.add("ج");
                    persianNumbers.add("چ");
                    persianNumbers.add("خ");
                    persianNumbers.add("ح");
                    persianNumbers.add("د");
                    persianNumbers.add("ذ");
                    persianNumbers.add("ر");
                    persianNumbers.add("ز");
                    persianNumbers.add("ژ");
                    persianNumbers.add("س");
                    persianNumbers.add("ش");
                    persianNumbers.add("ص");
                    persianNumbers.add("ض");
                    persianNumbers.add("ظ");
                    persianNumbers.add("ع");
                    persianNumbers.add("غ");
                    persianNumbers.add("ف");
                    persianNumbers.add("ق");
                    persianNumbers.add("ک");
                    persianNumbers.add("گ");
                    persianNumbers.add("ل");
                    persianNumbers.add("م");
                    persianNumbers.add("ن");
                    persianNumbers.add("و");
                    persianNumbers.add("ه");
                    persianNumbers.add("ی");

                    for (int i = 0; i < persianNumbers.size(); i++) {
                        if (temp.compareTo(persianNumbers.get(i)) == 0) {
                            Toast.makeText(MainActivity.this, "زبان فارسی را به انگلیسی تغییر بده", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            });
            edt_password.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    String temp = s.toString();

                    List<String> persianNumbers = new ArrayList<>();
                    persianNumbers.add("۰");
                    persianNumbers.add("۱");
                    persianNumbers.add("۲");
                    persianNumbers.add("۴");
                    persianNumbers.add("۵");
                    persianNumbers.add("۶");
                    persianNumbers.add("۷");
                    persianNumbers.add("۸");
                    persianNumbers.add("۹");
                    persianNumbers.add("ا");
                    persianNumbers.add("ب");
                    persianNumbers.add("پ");
                    persianNumbers.add("ث");
                    persianNumbers.add("ج");
                    persianNumbers.add("چ");
                    persianNumbers.add("خ");
                    persianNumbers.add("ح");
                    persianNumbers.add("د");
                    persianNumbers.add("ذ");
                    persianNumbers.add("ر");
                    persianNumbers.add("ز");
                    persianNumbers.add("ژ");
                    persianNumbers.add("س");
                    persianNumbers.add("ش");
                    persianNumbers.add("ص");
                    persianNumbers.add("ض");
                    persianNumbers.add("ظ");
                    persianNumbers.add("ع");
                    persianNumbers.add("غ");
                    persianNumbers.add("ف");
                    persianNumbers.add("ق");
                    persianNumbers.add("ک");
                    persianNumbers.add("گ");
                    persianNumbers.add("ل");
                    persianNumbers.add("م");
                    persianNumbers.add("ن");
                    persianNumbers.add("و");
                    persianNumbers.add("ه");
                    persianNumbers.add("ی");

                    for (int i = 0; i < persianNumbers.size(); i++) {
                        if (temp.compareTo(persianNumbers.get(i)) == 0) {
                            Toast.makeText(MainActivity.this, "زبان فارسی را به انگلیسی تغییر بده", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            });

        } catch (Exception e) {
            Toast.makeText(this, "با عرض پوزش سامانه پاسخگو نمیباشد", Toast.LENGTH_SHORT).show();
        }


    }

    public void ACTION_BUTTONS_MAIN(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.actv_main_btn_enter_portal_id:
                checkInternet = isNetworkAvailable(getApplication());
                if (checkInternet) {
                    if (edt_userName.length() > 0 && edt_password.length() >= 0 && edt_password.length() <= 20) {

                        if (edt_userName.length() >= 10 && edt_password.length() >= 6 && edt_password.length() <= 20) {
                            btnLoginIntoPortal.setEnabled(false);
                            loginToPortalWithAccessToken(edt_userName.getText().toString(), edt_password.getText().toString());
                        } else {
                            Toast.makeText(MainActivity.this, "نام کاربری یا رمز مغایرت دارد", Toast.LENGTH_SHORT).show();
                            btnLoginIntoPortal.setEnabled(true);

                        }
                    } else {
                        Toast.makeText(MainActivity.this, "فاقد نام کاربری یا رمز عبور", Toast.LENGTH_SHORT).show();
                        btnLoginIntoPortal.setEnabled(true);
                    }

                } else {
                    Toast.makeText(MainActivity.this, "عدم اتصال به اینترنت", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.actv_main_btn_password_recovery_id:
                show_AlertForgetPassword();
                break;
            case R.id.actv_main_btn_sign_up_id:
                goToSignUp();
                break;
        }
    }

    private void goToSignUp() {
        Uri uriUrl = Uri.parse("https://totanpay.com/pay-online-form/");
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }

    public void findViews_SetInit() {

        animFadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in_layout);
        animFadeIn500 = AnimationUtils.loadAnimation(this, R.anim.fade_in_layout_500ms);

        animFadeOut = AnimationUtils.loadAnimation(this, R.anim.fade_out_layout);
        animFadeOut500 = AnimationUtils.loadAnimation(this, R.anim.fade_out_layout_500ms);

        edt_userName = findViewById(R.id.actv_main_edt_username_id);
        edt_password = findViewById(R.id.actv_main_edt_password_id);
        edt_userName.setEnabled(false);
        edt_password.setEnabled(false);

        btnLoginIntoPortal = findViewById(R.id.actv_main_btn_enter_portal_id);
        btnLoginIntoPortal.setEnabled(false);
        btnPasswordRecovery = findViewById(R.id.actv_main_btn_password_recovery_id);
        btnPasswordRecovery.setEnabled(false);
        btnSignUp = findViewById(R.id.actv_main_btn_sign_up_id);
        btnSignUp.setEnabled(false);
//        online = isOnline();
        checkInternet = isNetworkAvailable(getApplication());
        if (checkInternet) {
            retrieveAccessTokenMethod(1);
        } else {
            Toast.makeText(MainActivity.this, "عدم اتصال به اینترنت", Toast.LENGTH_SHORT).show();
        }


    }

    private boolean getPingGoogle() {
        try {
            InetAddress address = InetAddress.getByName("www.google.com");
            return !address.equals("");
        } catch (Exception e) {
            // Log error
        }
        return false;
    }

    private boolean isOnline() {

        ConnectivityManager cm = (ConnectivityManager) getSystemService(MainActivity.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    private Boolean isNetworkAvailable(Application application) {
        ConnectivityManager connectivityManager = (ConnectivityManager) application.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Network nw = Objects.requireNonNull(connectivityManager).getActiveNetwork();
            if (nw == null) return false;
            NetworkCapabilities actNw = connectivityManager.getNetworkCapabilities(nw);
            return actNw != null && (actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                    actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                    actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) ||
                    actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH));
        } else {
            NetworkInfo nwInfo = Objects.requireNonNull(connectivityManager).getActiveNetworkInfo();
            return nwInfo != null && nwInfo.isConnected();
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode) {
        PERMISSION_VALIDATOR = 1;
    }

    @Override
    public void onPermissionsDeny(int requestCode) {
        PERMISSION_VALIDATOR = 0;
    }

    public void permissionManage() {
        try {
            MainActivity.super.requestAppPermissions(new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.ACCESS_NETWORK_STATE,
                    Manifest.permission.INTERNET
            }, 1);
        } catch (Exception e) {
            Toast.makeText(MainActivity.this, "با عرض پوزش سامانه پاسخگو نمیباشد", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        if (backButtonCount < 2) {
            if (backButtonCount == 1) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "برای خروج از برنامه یکبار دیگر کلیک کن", Toast.LENGTH_SHORT).show();
                backButtonCount = 1;
            }

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        retrieveAccessTokenMethod(1);
    }

    private void retrofitCall(int methodWay) {
        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .addNetworkInterceptor(new HeaderInterceptor(receivedToken, methodWay))
                .build();

        //TODO API updated WEB_API
        retrofit = new Retrofit.Builder()
                .baseUrl(WEB_API)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
    }

    private void retrieveAccessTokenMethod(final int type) {

        retrofitCall(0);
        Post post = new Post("admin", "123456");
        Call<Post> callPost = jsonPlaceHolderApi.retrieveTokenInterface(post);
        callPost.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, @NotNull Response<Post> response) {
                if (!response.isSuccessful()) {
                    Log.d("Response Not Successful", "Response Code: " + response.code());
                    Toast.makeText(MainActivity.this, "اینترنت گوشی را چک کنید", Toast.LENGTH_SHORT).show();
                    return;
                }

                Log.d("Response Successful", "Response Code: " + response.code());

                Post tokenPost = response.body();
                if (tokenPost != null) {

                    switch (type) {
                        case 1:
                            receivedToken = tokenPost.getAccessToken();


                            break;

                        case 2:
                            forgetPasswordWithAccessToken();
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


    private void loginToPortalWithAccessToken(String username, String password) {
// SharedPreferences pref = new SharedPreferences();

        retrieveAccessTokenMethod(1);

        retrofitCall(2);
        Post post = new Post(username, password);
        Call<Post> callPost = jsonPlaceHolderApi.loginWithAccessToken(post);
        callPost.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, @NotNull Response<Post> response) {
                if (!response.isSuccessful()) {
                    Log.d("Response Not Successful", "Response Code: " + response.code());
                    btnLoginIntoPortal.setEnabled(true);
                    return;
                }

                Log.d("Response Successful", "Response Code: " + response.code());
                Post userData = response.body();

                assert userData != null;
                if (userData.getUsernameValidation().equals("true")) {
                    USER_ID = userData.getUserID();
                    USER_FULL_NAME = userData.getFullName();
                    USERNAME = userData.getUsername();
                    LOGIN_VALIDATION = userData.getUsernameValidation();
                    startActivity(new Intent(MainActivity.this, HomePanel.class));
                    edt_userName.setText("");
                    edt_password.setText("");
                    btnLoginIntoPortal.setEnabled(true);
//                    finish();

                } else if (userData.getUsernameValidation().equals("false")) {
                    Toast.makeText(MainActivity.this, "نام کاربری یا رمز نامعتبر است", Toast.LENGTH_SHORT).show();
                    btnLoginIntoPortal.setEnabled(true);
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Log.d("Response Failure", "Failure Code: " + t.getMessage());
                btnLoginIntoPortal.setEnabled(true);
            }
        });


    }

    private void forgetPasswordWithAccessToken() {
        retrofitCall(2);
        Forget_Password_Model forgetPasswordModel = forget_password_model;
        Call<Forget_Password_Model> callPost = jsonPlaceHolderApi.forgetPasswordWithAccessToken(forget_password_model);
        callPost.enqueue(new Callback<Forget_Password_Model>() {
            @Override
            public void onResponse(Call<Forget_Password_Model> call, @NotNull Response<Forget_Password_Model> response) {
                if (!response.isSuccessful()) {
                    Log.d("Response Not Successful", "Response Code: " + response.code());
                    btnLoginIntoPortal.setEnabled(true);
                    return;
                }

                Log.d("Response Successful", "Response Code: " + response.code());
                Forget_Password_Model userData = response.body();

                assert userData != null;
                if (userData.getResultStatus().equals("true")) {
                    linearLayout.setVisibility(View.VISIBLE);
                    Toast.makeText(MainActivity.this, "رمز جدید به ایمیل شما ارسال خواهد شد", Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                } else if (userData.getResultStatus().equals("false")) {
                    Toast.makeText(MainActivity.this, userData.getDescription(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Forget_Password_Model> call, Throwable t) {
                Log.d("Response Failure", "Failure Code: " + t.getMessage());
                btnLoginIntoPortal.setEnabled(true);
            }
        });
    }

    private void show_AlertForgetPassword() {
        View view = getLayoutInflater().inflate(R.layout.alert_forget_password, null);

        final EditText edt_email, edt_nationalCode;
        Button btn_submit, btn_close;


        edt_email = view.findViewById(R.id.alert_forget_pass_edt_email);
        edt_nationalCode = view.findViewById(R.id.alert_forget_pass_edt_national_code);
        btn_submit = view.findViewById(R.id.alert_forget_pass_btn_submit_data);
        btn_close = view.findViewById(R.id.alert_forget_pass_btn_close_dialog);
        linearLayout = view.findViewById(R.id.alert_forget_pass_waiting_dialog);


        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setView(view);
        builder.setCancelable(false);

        dialog = builder.create();
        dialog.show();

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nationalCode = edt_nationalCode.getText().toString();
                String email = edt_email.getText().toString();
                if (!email.isEmpty() && !nationalCode.isEmpty()) {
                    if (edt_email.length() >= 16 && edt_nationalCode.length() == 10) {
//                        online = isOnline();
                        checkInternet = isNetworkAvailable(getApplication());
                        if (checkInternet) {
                            linearLayout.setVisibility(View.VISIBLE);
                            forget_password_model = new Forget_Password_Model();
                            forget_password_model.setEmail(edt_email.getText().toString());
                            forget_password_model.setNationalCode(edt_nationalCode.getText().toString());
                            retrieveAccessTokenMethod(2);
                        } else {
                            Toast.makeText(MainActivity.this, "عدم اتصال به اینترنت", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(MainActivity.this, "ایمیل یا کدملی مغایرت دارد", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "تکمیل فرم الزامیست", Toast.LENGTH_SHORT).show();
                }


            }
        });
        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


    }

//    private void show_AlertNoNetworkDialog() {
//        View view = getLayoutInflater().inflate(R.layout.alert_network_check, null);
//
//        Button btnReconnect = view.findViewById(R.id.alert_network_check_btn_reconnect);
//        Button btnExitApp = view.findViewById(R.id.alert_network_check_btn_exit);
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
//        builder.setView(view);
//        builder.setCancelable(false);
//
//        dialog2 = builder.create();
//        dialog2.show();
//
//        online = isOnline();
//        googleFlag = getPingGoogle();
//
//        btnReconnect.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (online && googleFlag) {
//                    retrieveAccessTokenMethod(1);
//                    dialog2.dismiss();
//                } else {
//                    Toast.makeText(MainActivity.this, "عدم اتصال به اینترنت", Toast.LENGTH_SHORT).show();
//                    show_AlertNoNetworkDialog();
//                }
//            }
//        });
//
//        btnExitApp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_MAIN);
//                intent.addCategory(Intent.CATEGORY_HOME);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
//                finish();
//            }
//        });
//
//
//    }


}
