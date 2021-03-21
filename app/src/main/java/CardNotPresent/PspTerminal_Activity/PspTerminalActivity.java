package CardNotPresent.PspTerminal_Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import CardNotPresent.Main.Post;
import CardNotPresent.R;
import CardNotPresent.webservice.HeaderInterceptor;
import CardNotPresent.webservice.JsonPlaceHolderApi;
import CardNotPresent.webservice.generate_links_inner_models.ModelParent;
import CardNotPresent.webservice.generate_links_inner_models.ModelSendIntegerTerminal;
import CardNotPresent.webservice.generate_links_inner_models.Model_PSP_List;
import CardNotPresent.webservice.generate_links_inner_models.Model_Terminal_List;
import org.jetbrains.annotations.NotNull;
import java.util.List;
import java.util.concurrent.TimeUnit;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import static CardNotPresent.Main.MainActivity.USER_ID;
import static CardNotPresent.before_main.Splash_Activity.WEB_API;


//TODO Page for showing user how many PSP he/she has.
public class PspTerminalActivity extends AppCompatActivity {
    List<Model_PSP_List> personModelList;
    List<Model_Terminal_List> modelTerminalLists, modelTerminalListsTemp;

    RecyclerView recyclerView;
    CardView cardView;
    PspTerminalAdapter adapter;
    Button btn_Close, btn_refresh;

    String id = "";
    int turn;
    boolean online = false;
    ModelParent modelParentTemp;
    boolean flag = false;
    ModelSendIntegerTerminal modelSendIntegerTerminal;

    int pspID = 0;

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
        setContentView(R.layout.activity_psp_terminal);

        Intent intent = getIntent();
        turn = intent.getIntExtra("turn", 0);
        switch (turn) {
            case 1:
                id = intent.getStringExtra("personId");
                break;
            case 2:
                id = intent.getStringExtra("productId");
                break;
        }

        findVIEWS_INIT();


        online = isOnline();
        if (online) {
            connecting_True();
        } else {
            connecting_False();
        }

        btn_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                online = isOnline();
                if (online) {
                    connecting_True();
                } else {
                    connecting_False();
                }
            }
        });

    }

    private void connecting_True() {

        btn_refresh.setVisibility(View.GONE);
        retrieveAccessTokenMethod_1();
    }

    private void connecting_False() {
        btn_refresh.setVisibility(View.VISIBLE);
        cardView.setVisibility(View.GONE);
        Toast.makeText(this, "عدم اتصال به اینترنت", Toast.LENGTH_SHORT).show();
    }

    private boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(PspTerminalActivity.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    void creatingButtonDynamically() {
        if (modelParentTemp.getModelTerminalLists_list() != null) {
            adapter = new PspTerminalAdapter(modelTerminalLists, this, PspTerminalActivity.this);
            recyclerView.setAdapter(adapter);
        }
        int i = 0;
        for (i = 0; i < personModelList.size(); i++) {
            final Button myButton = new Button(this);
            final int counter = i;
            myButton.setId(i);
            myButton.setTag("psp_button" + i);
            String name = personModelList.get(i).getPspName();
            myButton.setText(name);
//            myButton.setText("Psp : " + i+1);
            myButton.setGravity(Gravity.CENTER);
            myButton.setTextColor(Color.WHITE);
            myButton.setBackground(getResources().getDrawable(R.drawable.psp_button_click));
//            myButton.setTextColor(Color.BLACK);

//            if (i > personModelList.size()) {
//                myButton.setClickable(false);
//            }
            myButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (modelTerminalLists != null) {
                        cardView.setVisibility(View.VISIBLE);
                        pspID = Integer.parseInt(personModelList.get(counter).getId());
                    } else {
                        Toast.makeText(PspTerminalActivity.this, "لیست ترمینال موجود نیست", Toast.LENGTH_SHORT).show();
                        cardView.setVisibility(View.GONE);
                    }


                }
            });

            LinearLayout ll = (LinearLayout) findViewById(R.id.actv_psp_terminal_main_layout);

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.setMargins(5, 5, 5, 5);
            ll.addView(myButton, lp);

        }

    }

    void findVIEWS_INIT() {
        btn_Close = findViewById(R.id.btn_closedialog_terminalList);
        btn_refresh = findViewById(R.id.actv_psp_terminal_btn_refresh);
        cardView = findViewById(R.id.actv_psp_terminal_cardview);
        recyclerView = findViewById(R.id.actv_psp_terminal_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        btn_Close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cardView.setVisibility(View.GONE);
            }
        });
    }

    public void retrieveAccessTokenMethod_1() {
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
                        if (!flag)
                            retrieveGeneratedLinksForPersonsWithAccessToken_1(receivedToken, id);
                        else
                            generateLinkForPerson(receivedToken);
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<Post> call, @NotNull Throwable t) {

                Log.d("Response Failure", "Failure Code: " + t.getMessage());

            }
        });


    }

    public void retrieveGeneratedLinksForPersonsWithAccessToken_1(String receivedToken, String id) {

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
                modelParentTemp = response.body();

                assert modelParentTemp != null;
                if (modelParentTemp.getResultStatus().equals("true")) {
                    personModelList = modelParentTemp.getModelPspLists_list();
                    modelTerminalLists = modelParentTemp.getModelTerminalLists_list();
                }
                if (modelParentTemp.getModelPspLists_list().size() != 0) {
                    creatingButtonDynamically();
                }

            }

            @Override
            public void onFailure(@NotNull Call<ModelParent> call, @NotNull Throwable t) {
                Log.d("Response Failure", "Failure Code: " + t.getMessage());
            }
        });
    }

    public void generateLinkForPerson(String receivedToken) {

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

        Call<ModelParent> callPost;
        if (turn == 1) {
            callPost = jsonPlaceHolderApi.generatePersonalPaymentLink(getModelSendIntegerTerminal());
        } else if (turn == 2) {
            callPost = jsonPlaceHolderApi.generateProductPaymentLink(getModelSendIntegerTerminal());
        } else {
            return;
        }

        callPost.enqueue(new Callback<ModelParent>() {
            @Override
            public void onResponse(@NotNull Call<ModelParent> call, @NotNull Response<ModelParent> response) {
                if (!response.isSuccessful()) {
                    Log.d("Response Not Successful", "Response Code: " + response.code());
                    return;
                }
                Log.d("Response Successful", "Response Code: " + response.code() + "Link Generated");
                ModelParent modelTemp = response.body();

                assert modelTemp != null;
                if (modelTemp.getResultStatus().equals("true")) {
                    Intent intent = new Intent();
                    intent.putExtra("Result", "true");
                    setResult(200, intent);

                } else {
                    Log.d("PspTerminalActivity : ", "EXCEPTION :" + modelTemp.getException());
                    Intent intent = new Intent();
                    intent.putExtra("Result", "false");
                    setResult(200, intent);
                }
                finish();
            }

            @Override
            public void onFailure(@NotNull Call<ModelParent> call, @NotNull Throwable t) {
                Log.d("Response Failure", "Failure Code: " + t.getMessage());
            }
        });
    }

    public void generatePersonalPaymentLinkAfterTerminalClick(Model_Terminal_List model)
    {
        online = isOnline();
        if (online)
        {
            flag = true;
            if (model.getPspType() != null)// && modelParentTemp.getUserMerchantInfoId() != null
            {
                int _userId = Integer.parseInt(USER_ID);
                int _id = Integer.parseInt(id);
                int _pspType = pspID;
//                int _pspType = Integer.parseInt(model.getPspType());
//                int _pspType = Integer.parseInt(personModelList.get(0).getId());
                int _merchantInfoId = Integer.parseInt(model.getId());

                ModelSendIntegerTerminal modelSendIntegerTerminalTemp
                        = new ModelSendIntegerTerminal(_userId, _id, _pspType, _merchantInfoId);
                setModelSendIntegerTerminal(modelSendIntegerTerminalTemp);
                retrieveAccessTokenMethod_1();
            } else {
                Toast.makeText(this, "اختلال در تولید لینک", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "عدم اتصال یه اینترنت", Toast.LENGTH_SHORT).show();
        }
    }





    public ModelSendIntegerTerminal getModelSendIntegerTerminal() {
        return modelSendIntegerTerminal;
    }

    public void setModelSendIntegerTerminal(ModelSendIntegerTerminal modelSendIntegerTerminal) {
        this.modelSendIntegerTerminal = modelSendIntegerTerminal;
    }
}
