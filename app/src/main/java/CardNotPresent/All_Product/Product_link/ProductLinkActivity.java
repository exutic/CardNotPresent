package CardNotPresent.All_Product.Product_link;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import CardNotPresent.DC;
import CardNotPresent.DateConverter;
import CardNotPresent.Main.Post;
import CardNotPresent.All_Product.Products.Model_ResulrStatus_product;
import CardNotPresent.All_Product.Products.ProductModel;
import CardNotPresent.R;
import CardNotPresent.webservice.HeaderInterceptor;
import CardNotPresent.webservice.JsonPlaceHolderApi;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import ir.hamsaa.persiandatepicker.Listener;
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog;
import ir.hamsaa.persiandatepicker.util.PersianCalendar;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static CardNotPresent.Main.MainActivity.PERMISSION_VALIDATOR;
import static CardNotPresent.Main.MainActivity.USER_ID;
import static CardNotPresent.before_main.Splash_Activity.WEB_API;

public class ProductLinkActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Button btnSearch, btn_refresh, btn_CreatePdf;
    ProductLinkAdapter productAdapter;
    String receivedToken = "";

    LinearLayout linearLayout;
    LinearLayout linearLayoutSearch;
    boolean online = false;

    List<ProductModel> modelArrayList = new ArrayList<>();


    AlertDialog.Builder builder;
    EditText edt_ProductName_s, edt_ProductCode_s, edt_ProductAmountStart_s, edt_ProductAmountEnd_s;
    String stringProductName, stringProductCode, stringProductAmountStart, stringProductAmountEnd, stringProductStartDate, stringProductEndDate;
    Button btn_submit, btn_CloseDialog, btnDateStart_s, btnDateEnd_s;

    List<ProductModel> parentFilteredList;
    List<ProductModel> filteredListResult;
    int firstField = 0;
    int checkListBoolean = 0;


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
        setContentView(R.layout.activity_product_link);

        findViews();


        online = isOnline(ProductLinkActivity.this);
        if (online) {
            connection_True();
        } else {
            connection_False();
        }

        btn_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                online = isOnline(ProductLinkActivity.this);
                if (online) {
                    connection_True();
                } else {
                    connection_False();
                }
            }
        });

        btn_CreatePdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (PERMISSION_VALIDATOR == 1) {
                    create_Pdf();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            btn_CreatePdf.setEnabled(true);
                        }
                    }, 1500);
                } else {
                    Toast.makeText(ProductLinkActivity.this, "دسترسی به حافطه مسدود میباشد", Toast.LENGTH_SHORT).show();
                    Toast.makeText(ProductLinkActivity.this, getResources().getText(R.string.permission_validator_toast), Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void create_Pdf() {
        List<ProductModel> resultPdfList = new ArrayList<>();


        btn_CreatePdf.setEnabled(false);
        switch (checkListBoolean) {
            case 0:
                resultPdfList = parentFilteredList;
                break;

            case 1:
                resultPdfList = filteredListResult;
                break;

            default:
                resultPdfList = null;
                break;
        }


        if (resultPdfList != null && resultPdfList.size()>0)
        {
            Toast.makeText(ProductLinkActivity.this, "در حال ذخیره فایل، لطفا صبر کنید", Toast.LENGTH_SHORT).show();
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

                String name = "نام محصول: " + resultPdfList.get(i).getProductName();
                int p = (pageInfo.getPageWidth() / 2);
                canvas.drawText(name, p, 30 + counter, paint);
                canvas.drawText("کد محصول : " + resultPdfList.get(i).getProductCode(), p, 50 + counter, paint);
                canvas.drawText("قیمت : " + resultPdfList.get(i).getProductPrice(), p, 70 + counter, paint);
                canvas.drawText("تاریخ ثبت : " + resultPdfList.get(i).getInsertDateTime(), p, 90 + counter, paint);
                canvas.drawText("توضیحات : " + resultPdfList.get(i).getDescription(), p, 110 + counter, paint);
                canvas.drawLine(0, 115 + counter, 800, 115 + counter, paint);

                counter += 115;
                temp++;
                if (temp == 4 || i == resultPdfList.size() - 1) {
                    document.finishPage(page);
                }
            }

            File mediaStorageDir = new File("/sdcard/TokaPay", "لینک های محصول که تا به حال ساخته اید");
            if (!mediaStorageDir.exists()) {
                if (!mediaStorageDir.mkdirs()) {

                }
            }

            File mediaFile;
            Date date = new Date();
            date.getTime();
            mediaFile = new File(mediaStorageDir.getPath() + File.separator + date.toString() + ".pdf");

            try {
                document.writeTo(new FileOutputStream(mediaFile));
                Toast.makeText(ProductLinkActivity.this, "ذخیره شد", Toast.LENGTH_LONG).show();
                document.close();
            } catch (IOException e) {
                Log.e("main", "error " + e.toString());
                Toast.makeText(ProductLinkActivity.this, "Something wrong: " + e.toString(), Toast.LENGTH_LONG).show();
            }

            linearLayout.setVisibility(View.GONE);

        } else {
            Toast.makeText(this, "لیست خالی است", Toast.LENGTH_SHORT).show();
        }


    }

    public void connection_True() {
        linearLayoutSearch.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
        btn_refresh.setVisibility(View.GONE);
        retrieveAccessTokenMethod();
    }

    public void connection_False() {
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
                if (userData.getUsernameValidation().equals("true")) {
                    for (int i = 0; i < userData.getProductModelList().size(); i++) {
                        ProductModel model = userData.getProductModelList().get(i);
                        if (model.getInsertDateTime() != null) {
                            String date = miladiDate_ConvertTO_PerisanDate(model.getInsertDateTime());
                            model.setInsertDateTime(date);
                        }
                        if (model.getPaymentLink() != null) {
                            modelArrayList.add(model);
                        }
                    }
                    if (modelArrayList != null) {
                        productAdapter = new ProductLinkAdapter(modelArrayList, ProductLinkActivity.this);
                        recyclerView.setAdapter(productAdapter);
                        parentFilteredList = modelArrayList;
                    } else {
                        Toast.makeText(ProductLinkActivity.this, "هیچ لینک فعالی برای محصولات  موجود نمیباشد", Toast.LENGTH_SHORT).show();
                    }
                    linearLayout.setVisibility(View.GONE);

                }
            }

            @Override
            public void onFailure(Call<Model_ResulrStatus_product> call, Throwable t) {
                Log.d("Response Failure", "Failure Code: " + t.getMessage());
            }
        });
    }

    public void retrieveAccessTokenMethod() {

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
                if (tokenPost != null) {
                    receivedToken = tokenPost.getAccessToken();
                    if (receivedToken.length() == 32) {
                        retrievePersonsDataWithAccessToken(receivedToken);
                    }
                }

            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {

                Log.d("Response Failure", "Failure Code: " + t.getMessage());

            }
        });


    }

    private void findViews() {

        btn_refresh = findViewById(R.id.actv_poduct_link_btn_refresh);
        btn_CreatePdf = findViewById(R.id.frag_prroduct_link_btn_create_pdf);
        linearLayoutSearch = findViewById(R.id.frag_product_link_list_linear);
        linearLayout = findViewById(R.id.actv_productLink_include__waiting_dialog);
        recyclerView = findViewById(R.id.actv_poduct_link_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(ProductLinkActivity.this));

        btnSearch = findViewById(R.id.actv_product_link_btn_search);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_CreatePdf.setEnabled(false);
                btnSearch.setEnabled(false);
                setOnClickSearch();
            }
        });
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

    private boolean dateAndTimeSeparator(String dateStart, String dateEnd, String realDate) {
        String[] part1 = realDate.split("_");
//        String partDate = part1[0];
        String partDate1 = part1[1];

        int intStartDate = 0;
        if (dateStart != null) {
            String numberStartDate = dateStart.replaceAll("[^0-9]", "");
            intStartDate = Integer.parseInt(numberStartDate);
        } else {
            intStartDate = 139011;
        }


        int intEndDate = 0;
        if (dateEnd != null) {
            String numberEndDate = dateEnd.replaceAll("[^0-9]", "");
            intEndDate = Integer.parseInt(numberEndDate);
        } else {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH) + 1;
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DateConverter converter = new DateConverter();
            converter.gregorianToPersian(year, month, day);
            year = converter.getYear();
            month = converter.getMonth();
            day = converter.getDay();

            String string = String.valueOf(year) + String.valueOf(month) + String.valueOf(day);

            intEndDate = Integer.parseInt(string);
        }


        String numberDate = partDate1.replaceAll("[^0-9]", "");
        int intDate = Integer.parseInt(numberDate);


        if (intDate <= intEndDate && intDate >= intStartDate) {
            return true;
        }


        return false;
    }

    private void config_search() {
        View view = LayoutInflater.from(this).inflate(R.layout.alert_search_product, null);

        edt_ProductName_s = view.findViewById(R.id.edt_name_search_product);
        edt_ProductCode_s = view.findViewById(R.id.edt_product_code_search_product);
        edt_ProductAmountStart_s = view.findViewById(R.id.edt_start_amount_search_product);
        edt_ProductAmountEnd_s = view.findViewById(R.id.edt_end_amount_search_product);
        advanceSearch();


        btnDateStart_s = view.findViewById(R.id.btn_start_date_search_product);
        btnDateStart_s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateSelect_For_Search(1);
            }
        });
        btnDateEnd_s = view.findViewById(R.id.btn_end_date_search_product);
        btnDateEnd_s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateSelect_For_Search(2);
            }
        });

        btn_submit = view.findViewById(R.id.btn_submit_search_product);
        btn_CloseDialog = view.findViewById(R.id.btn_closedialog_search_product);

        builder = new AlertDialog.Builder(this);
        builder.setCancelable(false)
                .setView(view);

        final AlertDialog dialog = builder.create();
        dialog.show();

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if (edt_ProductName_s.length() > 0 || edt_ProductAmountEnd_s.length() > 0 ||
                        edt_ProductAmountStart_s.length() > 0 || edt_ProductCode_s.length() > 0 ||
                        stringProductEndDate != null || stringProductStartDate != null) {
                    filter();
                    dialog.dismiss();
                } else {
                    Toast.makeText(ProductLinkActivity.this, "حداقل یک قسمت پر شود", Toast.LENGTH_SHORT).show();
                }


                btnSearch.setEnabled(true);
            }
        });

        btn_CloseDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_CreatePdf.setEnabled(true);
                btnSearch.setEnabled(true);
                checkListBoolean = 0;
                dialog.dismiss();
            }
        });
    }

    private void recordNotFoundDialog() {
        recyclerView.setVisibility(View.GONE);
        btnSearch.setVisibility(View.GONE);
        btn_CreatePdf.setVisibility(View.GONE);

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
                ProductLinkActivity.super.onBackPressed();
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

    public void advanceSearch() {

        edt_ProductName_s.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                stringProductName = s.toString();
            }
        });
        edt_ProductCode_s.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                stringProductCode = s.toString();
            }
        });
        edt_ProductAmountStart_s.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                edt_ProductAmountStart_s.removeTextChangedListener(this);
                String string = edt_ProductAmountStart_s.getText().toString();

                String temp = DC.convertFNumToENum(string);
                String numberDate = temp.replaceAll("[^0-9]", "");

                if (numberDate.length() > 0) {
                    DecimalFormat sdd = new DecimalFormat("#,###");
                    Double doubleNumber = Double.parseDouble(numberDate);

                    String format = sdd.format(doubleNumber);
                    edt_ProductAmountStart_s.setText(format);
                    edt_ProductAmountStart_s.setSelection(format.length());

                }
                edt_ProductAmountStart_s.addTextChangedListener(this);
                stringProductAmountStart = editable.toString();
            }
        });
        edt_ProductAmountEnd_s.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                edt_ProductAmountEnd_s.removeTextChangedListener(this);
                String string = edt_ProductAmountEnd_s.getText().toString();

                String temp = DC.convertFNumToENum(string);
                String numberDate = temp.replaceAll("[^0-9]", "");

                if (numberDate.length() > 0) {
                    DecimalFormat sdd = new DecimalFormat("#,###");

                    Double doubleNumber = Double.parseDouble(numberDate);
                    String format = sdd.format(doubleNumber);
                    edt_ProductAmountEnd_s.setText(format);
                    edt_ProductAmountEnd_s.setSelection(format.length());

                }
                edt_ProductAmountEnd_s.addTextChangedListener(this);

                stringProductAmountEnd = editable.toString();
            }
        });
    }

    private void filter() {

        if (parentFilteredList != null) {

            if (stringProductName != null) {
                for (ProductModel item : parentFilteredList) {
                    if (item.getProductName() != null) {
                        if (item.getProductName().toLowerCase().contains(stringProductName.toLowerCase())) {
                            filteredListResult.add(item);
                        }
                    }
                }
                firstField = 1;
            }

            if (stringProductCode != null) {
                if (firstField == 0) {
                    for (ProductModel item : parentFilteredList) {
                        if (item.getProductCode() != null) {
                            if (item.getProductCode().toLowerCase().contains(stringProductCode.toLowerCase())) {
                                filteredListResult.add(item);
                            }
                        }
                    }
                    firstField = 1;
                } else {
                    List<ProductModel> tempList = new ArrayList<>();
                    for (ProductModel item : filteredListResult) {
                        if (item.getProductCode() != null) {
                            if (item.getProductCode().toLowerCase().contains(stringProductCode.toLowerCase())) {
                                tempList.add(item);
                            }
                        }
                    }
                    filteredListResult = new ArrayList<>();
                    filteredListResult = tempList;
                }
            }


            if (stringProductAmountStart != null || stringProductAmountEnd != null) {
                int amountStartInteger = 0;
                if (stringProductAmountStart != null) {
                    if (stringProductAmountStart.equals("")) {
                        amountStartInteger = 0;
                    } else {
                        String temp = DC.convertFNumToENum(stringProductAmountStart);
                        stringProductAmountStart = temp.replaceAll("[^0-9]", "");
                        amountStartInteger = Integer.parseInt(stringProductAmountStart);
                    }
                }

                int amountEndInteger = 0;
                if (stringProductAmountEnd != null) {
                    if (stringProductAmountEnd.equals("")) {
                        amountEndInteger = 0;
                    } else {
                        String temp1 = DC.convertFNumToENum(stringProductAmountEnd);
                        stringProductAmountEnd = temp1.replaceAll("[^0-9]", "");
                        amountEndInteger = Integer.parseInt(stringProductAmountEnd);
                    }
                } else {
                    amountEndInteger = 500000000;
                }


                if (firstField == 0) {
                    for (ProductModel item : parentFilteredList) {
                        if (item.getProductPrice() != null) {
                            int productAmountInteger = Integer.parseInt(item.getProductPrice());

                            if (productAmountInteger >= amountStartInteger && productAmountInteger <= amountEndInteger) {
                                filteredListResult.add(item);
                            }
                        }
                    }
                    firstField = 1;
                } else {
                    List<ProductModel> tempList = new ArrayList<>();
                    for (ProductModel item : filteredListResult) {
                        if (item.getProductPrice() != null) {
                            int productAmountInteger = Integer.parseInt(item.getProductPrice());

                            if (productAmountInteger >= amountStartInteger && productAmountInteger <= amountEndInteger) {
                                tempList.add(item);
                            } else if (productAmountInteger <= amountEndInteger && amountStartInteger == 0) {
                                filteredListResult.add(item);
                            }
                        }
                    }
                    filteredListResult = new ArrayList<>();
                    filteredListResult = tempList;
                }
            }


            if (stringProductStartDate != null || stringProductEndDate != null) {
                if (firstField == 0) {
                    for (ProductModel item : parentFilteredList) {
                        if (item.getInsertDateTime() != null) {
                            boolean flag = dateAndTimeSeparator(stringProductStartDate, stringProductEndDate, item.getInsertDateTime());
                            if (flag) {
                                filteredListResult.add(item);
                            }
                        }
                    }
                    firstField = 1;
                } else {
                    List<ProductModel> tempList = new ArrayList<>();
                    for (ProductModel item : filteredListResult) {
                        if (item.getInsertDateTime() != null) {
                            boolean flag = dateAndTimeSeparator(stringProductStartDate, stringProductEndDate, item.getInsertDateTime());
                            if (flag) {
                                tempList.add(item);
                            }
                        }
                    }
                    filteredListResult = new ArrayList<>();
                    filteredListResult = tempList;
                }
            }

            if (filteredListResult.size() == 0) {
                recordNotFoundDialog();
                checkListBoolean = 0;

            } else {
                productAdapter.filterList(filteredListResult);
                checkListBoolean = 1;
            }


        } else {
            Toast.makeText(ProductLinkActivity.this, "گزینه ای برای جستجو وجود ندارد", Toast.LENGTH_SHORT).show();
        }


    }

    private void setOnClickSearch() {
        recyclerView.setVisibility(View.VISIBLE);
        btnSearch.setVisibility(View.VISIBLE);
        btn_CreatePdf.setVisibility(View.VISIBLE);
        filteredListResult = new ArrayList<>();

        if (firstField == 1)
            productAdapter.filterList(parentFilteredList);

        stringProductName = null;
        stringProductCode = null;
        stringProductAmountStart = null;
        stringProductAmountEnd = null;
        stringProductStartDate = null;
        stringProductEndDate = null;

        firstField = 0;
        config_search();
    }

    public void dateSelect_For_Search(final int type) {

        // type moshakhas mikone ke meghdar dar dateStart save beshe ya dateEnd
        PersianDatePickerDialog picker = new PersianDatePickerDialog(this)
                .setPositiveButtonString("تائید")
                .setNegativeButton("انصراف")
                .setTodayButton("امروز")
                .setTodayButtonVisible(true)
                .setMinYear(1350)
                .setMaxYear(PersianDatePickerDialog.THIS_YEAR)
//                        .setInitDate(initDate)
                .setActionTextColor(Color.BLUE)
//                        .setTypeFace(typeface)
                .setTitleType(PersianDatePickerDialog.WEEKDAY_DAY_MONTH_YEAR)
                .setShowInBottomSheet(true)
                .setListener(new Listener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDateSelected(PersianCalendar persianCalendar) {
//                                Log.d(TAG, "onDateSelected: "+persianCalendar.getGregorianChange());//Fri Oct 15 03:25:44 GMT+04:30 1582
//                                Log.d(TAG, "onDateSelected: "+persianCalendar.getTimeInMillis());//1583253636577
//                                Log.d(TAG, "onDateSelected: "+persianCalendar.getTime());//Tue Mar 03 20:10:36 GMT+03:30 2020
//                                Log.d(TAG, "onDateSelected: "+persianCalendar.getDelimiter());//  /
//                                Log.d(TAG, "onDateSelected: "+persianCalendar.getPersianLongDate());// سه‌شنبه  13  اسفند  1398
//                                Log.d(TAG, "onDateSelected: "+persianCalendar.getPersianLongDateAndTime()); //سه‌شنبه  13  اسفند  1398 ساعت 20:10:36
//                                Log.d(TAG, "onDateSelected: "+persianCalendar.getPersianMonthName()); //اسفند
//                                Log.d(TAG, "onDateSelected: "+persianCalendar.isPersianLeapYear());//false
//                        Toast.makeText(getActivity(), persianCalendar.getPersianYear() + "/" + persianCalendar.getPersianMonth() + "/" + persianCalendar.getPersianDay(), Toast.LENGTH_SHORT).show();
                        switch (type) {
                            case 1:
                                btnDateStart_s.setText(persianCalendar.getPersianYear() + "/" + persianCalendar.getPersianMonth() + "/" + persianCalendar.getPersianDay());
                                stringProductStartDate = btnDateStart_s.getText().toString();
                                break;

                            case 2:
                                btnDateEnd_s.setText(persianCalendar.getPersianYear() + "/" + persianCalendar.getPersianMonth() + "/" + persianCalendar.getPersianDay());
                                stringProductEndDate = btnDateEnd_s.getText().toString();
                                break;
                        }
                    }

                    @Override
                    public void onDismissed() {

                    }
                });

        picker.show();
    }
}
