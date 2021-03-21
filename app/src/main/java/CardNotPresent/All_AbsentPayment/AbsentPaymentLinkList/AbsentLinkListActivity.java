package CardNotPresent.All_AbsentPayment.AbsentPaymentLinkList;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import CardNotPresent.DC;
import CardNotPresent.Main.Post;
import CardNotPresent.R;
import CardNotPresent.webservice.HeaderInterceptor;
import CardNotPresent.webservice.JsonPlaceHolderApi;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
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

public class AbsentLinkListActivity extends AppCompatActivity {


    RecyclerView recyclerView;
    Button btn_refresh;
//    Button btn_CreatePdf;
    AbsentLinkListAdapter absentLinkListAdapter;
    private String receivedToken = "";
    LinearLayout linearLayout;
    LinearLayout linearLayoutSearch;

    boolean online = false;
    List<MerchantModel> modelArrayList = new ArrayList<>();

    Button btnSearch;

    EditText edtPspName, edtTerminalId, edtMerchantId, edtPspType;
    String stringPspName, stringTerminalId, stringMerchantId, stringPspType;
    List<MerchantModel> parentFilteredList;
    List<MerchantModel> filteredListResult;
    int firstField = 0;
    private AlertDialog.Builder builder;


    int checkListBoolean = 0;
    List<MerchantModel> resultPdfList;

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
        setContentView(R.layout.activity_absent_link);

        findViews();

        online = isOnline(AbsentLinkListActivity.this);
        if (online) {
            coonection_True();
        } else {
            coonection_False();
        }

        btn_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                online = isOnline(AbsentLinkListActivity.this);
                if (online) {
                    coonection_True();
                } else {
                    coonection_False();
                }
            }
        });

//        btn_CreatePdf.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (PERMISSION_VALIDATOR == 1) {
//
//                    create_Pdf();
//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            btn_CreatePdf.setEnabled(true);
//                        }
//                    }, 1500);
//                } else {
//                    Toast.makeText(AbsentLinkListActivity.this, "دسترسی به حافطه مسدود میباشد", Toast.LENGTH_SHORT).show();
//                    Toast.makeText(AbsentLinkListActivity.this, getResources().getText(R.string.permission_validator_toast), Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

    }

    private void create_Pdf() {
//        btn_CreatePdf.setEnabled(false);
        switch (checkListBoolean) {
            case 0:
                resultPdfList = parentFilteredList;
                break;

            case 1:
                resultPdfList = filteredListResult;
                break;
        }

        if (resultPdfList != null) {

            Toast.makeText(AbsentLinkListActivity.this, "در حال ذخیره فایل، لطفا صبر کنید", Toast.LENGTH_SHORT).show();

            linearLayout.setVisibility(View.VISIBLE);
            PdfDocument document = new PdfDocument();
            // crate a page description
            PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(300, 600, 1).create();

            PdfDocument.Page page = document.startPage(pageInfo);
            Canvas canvas = page.getCanvas();
            Paint paint = new Paint();
            paint.setTextAlign(Paint.Align.CENTER);
            paint.setColor(Color.BLACK);


            int counter = 0;
            int temp = 0;

            for (int i = 0; i < resultPdfList.size(); i++) {
                if (temp == 4) {
                    page = document.startPage(pageInfo);
                    canvas = page.getCanvas();
                    paint = new Paint();
                    paint.setColor(Color.BLACK);
                    paint.setTextAlign(Paint.Align.CENTER);


                    temp = 0;
                    counter = 0;
                }

                String name = "نام PSP : " + resultPdfList.get(i).getPspName();
                int p = (pageInfo.getPageWidth() / 2);
                canvas.drawText(name, p, 30 + counter, paint);
                canvas.drawText("لینک پرداخت : " + resultPdfList.get(i).getPaymentLink(), p, 50 + counter, paint);
                canvas.drawText("شماره پذیرنده : " + resultPdfList.get(i).getMerchantId(), p, 70 + counter, paint);
                canvas.drawText("شماره ترمینال : " + resultPdfList.get(i).getTerminalId(), p, 90 + counter, paint);
                canvas.drawText("نوع PSP : " + resultPdfList.get(i).getPspType(), p, 110 + counter, paint);
                canvas.drawLine(0, 145 + counter, 800, 145 + counter, paint);

                counter += 145;
                temp++;
                if (temp == 4 || i == resultPdfList.size() - 1) {
                    document.finishPage(page);
                }
            }

            File mediaStorageDir = new File("/sdcard/TokaPay", "لینک های ارسالی که تا به حال ساخته اید");
            if (!mediaStorageDir.exists()) {
                if (!mediaStorageDir.mkdirs()) {

                }
            }

            File mediaFile;
            Date date = new Date();
            date.getTime();
            mediaFile = new File(mediaStorageDir.getPath() + File.separator + date.toString().trim() + ".pdf");

            try {
                document.writeTo(new FileOutputStream(mediaFile));
                Toast.makeText(AbsentLinkListActivity.this, "ذخیره شد", Toast.LENGTH_LONG).show();
                document.close();
            } catch (IOException e) {
                Log.e("main", "error " + e.toString());
                Toast.makeText(AbsentLinkListActivity.this, "Something wrong: " + e.toString(), Toast.LENGTH_LONG).show();
            }

            // close the document


            linearLayout.setVisibility(View.GONE);

        } else {
            Toast.makeText(this, "لیست خالی است", Toast.LENGTH_SHORT).show();
        }


    }

    public void coonection_True() {
        linearLayoutSearch.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
        btn_refresh.setVisibility(View.GONE);
        retrieveAccessTokenMethod();
    }

    public void coonection_False() {
        linearLayoutSearch.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        btn_refresh.setVisibility(View.VISIBLE);
        Toast.makeText(this, "عدم اتصال به اینترنت", Toast.LENGTH_SHORT).show();
    }

    public static boolean isOnline(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        return (netInfo != null && netInfo.isConnected());
    }

    private void findViews() {
        btn_refresh = findViewById(R.id.actv_person_link_btn_refresh);
        linearLayout = findViewById(R.id.actv_personLink_include__waiting_dialog);
        linearLayoutSearch = findViewById(R.id.frag_person_link_list_linear);
        recyclerView = findViewById(R.id.actv_person_link_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(AbsentLinkListActivity.this));
        btnSearch = findViewById(R.id.actv_persons_link_btn_search);
//        btn_CreatePdf = findViewById(R.id.frag_pre_link_btn_create_pdf);
//        btn_CreatePdf.setVisibility(View.GONE);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                btn_CreatePdf.setEnabled(false);
                btnSearch.setEnabled(false);
                setOnClickSearch();
            }
        });
    }

    private void config_Search() {
        View view = LayoutInflater.from(this).inflate(R.layout.alert_search_links, null);

        final Button btn_Submit, btn_Close;

        edtPspName = view.findViewById(R.id.edt_psp_name_absent_link);
        edtTerminalId = view.findViewById(R.id.edt_terminal_absent_link);
        edtMerchantId = view.findViewById(R.id.edt_merchant_absent_link);
        edtPspType = view.findViewById(R.id.edt_psp_type_absent_link);

        advanceSearch();

        builder = new AlertDialog.Builder(this);
        builder.setCancelable(false)
                .setView(view);

        final AlertDialog dialog = builder.create();
        dialog.show();


        btn_Submit = view.findViewById(R.id.btn_submit_search_person_link);
        btn_Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtPspName.length() > 0 || edtPspType.length() > 0 || edtMerchantId.length() > 0 || edtTerminalId.length() > 0) {
                    filter();
//                    btn_CreatePdf.setEnabled(true);
                    btnSearch.setEnabled(true);
                    dialog.dismiss();
                } else {
                    Toast.makeText(AbsentLinkListActivity.this, "حداقل یک قسمت پر شود", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn_Close = view.findViewById(R.id.btn_closedialog_search_person_link);
        btn_Close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                btn_CreatePdf.setEnabled(true);
                btnSearch.setEnabled(true);
                checkListBoolean = 0;
                dialog.dismiss();
            }
        });


    }

    private void recordNotFoundDialog() {
        recyclerView.setVisibility(View.GONE);
        btnSearch.setVisibility(View.GONE);
//        btn_CreatePdf.setVisibility(View.GONE);

        View view = getLayoutInflater().inflate(R.layout.alert_false_search, null);

        Button goHome, goSearch;

        goHome = view.findViewById(R.id.alert_false_search_btn_homepage);
        goSearch = view.findViewById(R.id.alert_false_search_btn_show_search);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);
        builder.setCancelable(false);

        final AlertDialog dialog = builder.create();
        dialog.show();

        goHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                AbsentLinkListActivity.super.onBackPressed();
            }
        });

        goSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setOnClickSearch();
                dialog.dismiss();
            }
        });

    }

    private void setOnClickSearch() {
        recyclerView.setVisibility(View.VISIBLE);
        btnSearch.setVisibility(View.VISIBLE);
//        btn_CreatePdf.setVisibility(View.VISIBLE);

        filteredListResult = new ArrayList<>();

        if (firstField == 1)
            absentLinkListAdapter.filterList(parentFilteredList);

        stringPspName = null;
        stringTerminalId = null;
        stringMerchantId = null;
        stringPspType = null;
        firstField = 0;
        config_Search();
    }

    private void advanceSearch() {
        edtPspName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                stringPspName = s.toString();
            }
        });
        edtTerminalId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                stringTerminalId = s.toString();
            }
        });
        edtMerchantId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                stringMerchantId = s.toString();
            }
        });
        edtPspType.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                edtPspType.removeTextChangedListener(this);
                String string = edtPspType.getText().toString();

                String temp = DC.convertFNumToENum(string);
                String numberDate = temp.replaceAll("[^0-9]", "");

                if (numberDate.length() > 0) {
                    DecimalFormat sdd = new DecimalFormat("#,###");
                    Double doubleNumber = Double.parseDouble(numberDate);

                    String format = sdd.format(doubleNumber);
                    edtPspType.setText(format);
                    edtPspType.setSelection(format.length());

                }
                edtPspType.addTextChangedListener(this);
                stringPspType = editable.toString();
            }
        });

    }

    private void filter() {

        if (parentFilteredList != null) {


            if (stringPspName != null) {
                for (MerchantModel item : parentFilteredList) {
                    if (item.getPspName() != null) {
                        if (item.getPspName().toLowerCase().contains(stringPspName.toLowerCase())) {
                            filteredListResult.add(item);
                        }
                    }
                }
                firstField = 1;
            }

            if (stringTerminalId != null) {
                if (firstField == 0) {
                    for (MerchantModel item : parentFilteredList) {
                        if (item.getTerminalId() != null) {
                            if (item.getTerminalId().toLowerCase().contains(stringTerminalId.toLowerCase())) {
                                filteredListResult.add(item);
                            }
                        }
                    }
                    firstField = 1;
                } else {
                    List<MerchantModel> tempList = new ArrayList<>();
                    for (MerchantModel item : filteredListResult) {
                        if (item.getTerminalId() != null) {
                            if (item.getTerminalId().toLowerCase().contains(stringTerminalId.toLowerCase())) {
                                tempList.add(item);
                            }
                        }
                    }
                    filteredListResult = new ArrayList<>();
                    filteredListResult = tempList;
                }
            }


//        stringMerchantId = convert_PersianNumber_To_Englishnumber(stringMerchantId);
            if (stringMerchantId != null) {
                if (firstField == 0) {
                    for (MerchantModel item : parentFilteredList) {
                        if (item.getMerchantId() != null) {
                            if (item.getMerchantId().toLowerCase().contains(stringMerchantId.toLowerCase())) {
                                filteredListResult.add(item);
                            }
                        }
                    }
                    firstField = 1;
                } else {
                    List<MerchantModel> tempList = new ArrayList<>();
                    for (MerchantModel item : filteredListResult) {
                        if (item.getMerchantId() != null) {
                            if (item.getMerchantId().toLowerCase().contains(stringMerchantId.toLowerCase())) {
                                tempList.add(item);
                            }
                        }
                    }
                    filteredListResult = new ArrayList<>();
                    filteredListResult = tempList;
                }

            }

            if (stringPspType != null) {
                if (firstField == 0) {
                    for (MerchantModel item : parentFilteredList) {
                        if (item.getPspType() != null) {
                            if (item.getPspType().toLowerCase().contains(stringPspType.toLowerCase())) {
                                filteredListResult.add(item);
                            }
                        }
                    }
                    firstField = 1;
                } else {
                    List<MerchantModel> tempList = new ArrayList<>();
                    for (MerchantModel item : filteredListResult) {
                        if (item.getPspType() != null) {
                            if (item.getPspType().toLowerCase().contains(stringPspType.toLowerCase())) {
                                tempList.add(item);
                            }
                        }
                    }
                    filteredListResult = new ArrayList<>();
                    filteredListResult = tempList;
                }
            }

            //adapter
            if (filteredListResult.size() == 0) {
                recordNotFoundDialog();
                checkListBoolean = 0;
            } else {
                absentLinkListAdapter.filterList(filteredListResult);
                checkListBoolean = 1;
            }


        } else {
            Toast.makeText(AbsentLinkListActivity.this, "گزینه ای برای جستجو وجود ندارد", Toast.LENGTH_SHORT).show();
        }

    }

    private void retrieveAccessTokenMethod() {
        linearLayout.setVisibility(View.VISIBLE);
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
                    retrievePersonsDataWithAccessToken(receivedToken);
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

        AbsentLinkListModel absentLinkListModel = new AbsentLinkListModel(USER_ID);

        Call<AbsentLinkListModel> callPost = jsonPlaceHolderApi.getAllLinks(absentLinkListModel);
        callPost.enqueue(new Callback<AbsentLinkListModel>() {
            @Override
            public void onResponse(@NotNull Call<AbsentLinkListModel> call, @NotNull Response<AbsentLinkListModel> response) {
                if (!response.isSuccessful()) {
                    Log.d("Response Not Successful", "Response Code: " + response.code());
                    return;
                }

                Log.d("Response Successful", "Response Code: " + response.code());


                AbsentLinkListModel userData = response.body();


                assert userData != null;
                if (userData.getUsernameValidation().equals("true")) {
                    for (int i = 0; i < userData.getMerchantModels().size(); i++) {
                        MerchantModel merchantModel = userData.getMerchantModels().get(i);

                        if (merchantModel.getPaymentLink() != null) {
                            modelArrayList.add(merchantModel);
                        }
                    }

                    if (modelArrayList != null) {
                        absentLinkListAdapter = new AbsentLinkListAdapter(modelArrayList, AbsentLinkListActivity.this);
                        recyclerView.setAdapter(absentLinkListAdapter);
                        parentFilteredList = modelArrayList;
                    } else {
                        Toast.makeText(AbsentLinkListActivity.this, "خطا", Toast.LENGTH_SHORT).show();
                    }
                    linearLayout.setVisibility(View.GONE);
                } else {
                    Toast.makeText(AbsentLinkListActivity.this, "اطلاعاتی دریافت نشد", Toast.LENGTH_SHORT).show();
                    linearLayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(@NotNull Call<AbsentLinkListModel> call, Throwable t) {
                Log.d("Response Failure", "Failure Code: " + t.getMessage());
            }
        });
    }

}
