package CardNotPresent.All_AbsentPayment.AbsentPaymentStatus;

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

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import CardNotPresent.DC;
import CardNotPresent.DateConverter;
import CardNotPresent.Main.Post;
import CardNotPresent.All_Persons.Persons.PersonsModel;
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

import static CardNotPresent.Home.ui.Home.HomeFragment.PAYMENT_CONDITION_METHOD;
import static CardNotPresent.Main.MainActivity.PERMISSION_VALIDATOR;
import static CardNotPresent.Main.MainActivity.USER_ID;
import static CardNotPresent.before_main.Splash_Activity.WEB_API;

public class AbsentLinkStatusActivity extends AppCompatActivity {


    public static int show_IPG_TransactionStatus = 0;

    RecyclerView recyclerView;
    Button btn_refresh;
    Button btn_CreatePdf;
    AbsentLinkStatusAdapter absentLinkStatusAdapter;
    private String receivedToken = "";
    LinearLayout linearLayout;
    LinearLayout linearLayoutSearch;

    boolean online = false;
    List<PersonsModel> modelArrayList = new ArrayList<>();

    Button btnSearch;

    EditText edtName_s, edtFamily_s, edtNationalCode_s, edtAmountStart, edtAmountEnd;
    String stringName, stringFamilyName, stringNationalCode, stringAmountStart, stringAmountEnd, stringPersonStartDate, stringPersonEndDate;
    Button btnStartDate, btnEndDate;
    List<PersonsModel> parentFilteredList;
    List<PersonsModel> filteredListResult;
    int firstField = 0;
    private AlertDialog.Builder builder;

    int checkListBoolean = 0;
    List<PersonsModel> resultPdfList;

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
        show_IPG_TransactionStatus = 1;
        findViews();

        online = isOnline(AbsentLinkStatusActivity.this);
        if (online) {
            coonection_True();
        } else {
            coonection_False();
        }

        btn_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                online = isOnline(AbsentLinkStatusActivity.this);
                if (online) {
                    coonection_True();
                } else {
                    coonection_False();
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
                    Toast.makeText(AbsentLinkStatusActivity.this, "دسترسی به حافطه مسدود میباشد", Toast.LENGTH_SHORT).show();
                    Toast.makeText(AbsentLinkStatusActivity.this, getResources().getText(R.string.permission_validator_toast), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void create_Pdf() {
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

            Toast.makeText(AbsentLinkStatusActivity.this, "در حال ذخیره فایل، لطفا صبر کنید", Toast.LENGTH_SHORT).show();

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

                String name = "نام و نام خانوادگی : " + resultPdfList.get(i).getFirstName() + " " + resultPdfList.get(i).getLastName();
                int p = (pageInfo.getPageWidth() / 2);
                canvas.drawText(name, p, 30 + counter, paint);
                canvas.drawText("مبلغ : " + resultPdfList.get(i).getAmount(), p, 50 + counter, paint);
                canvas.drawText("تاریخ ثبت : " + resultPdfList.get(i).getInsertDateTime(), p, 70 + counter, paint);
                canvas.drawText("کد ملی : " + resultPdfList.get(i).getNationalCode(), p, 90 + counter, paint);
                canvas.drawText("شماره همراه : " + resultPdfList.get(i).getMobileNumber(), p, 110 + counter, paint);
                canvas.drawText("توضیحات : " + resultPdfList.get(i).getDescription(), p, 130 + counter, paint);
                canvas.drawLine(0, 145 + counter, 800, 145 + counter, paint);

                counter += 145;
                temp++;
                if (temp == 4 || i == resultPdfList.size() - 1) {
                    document.finishPage(page);
                }
            }

            File mediaStorageDir = new File("/sdcard/TokaPay", "تراکنش های لینک پرداخت");
            if (!mediaStorageDir.exists()) {
                if (!mediaStorageDir.mkdirs()) {
                    mediaStorageDir = new File("/sdcard/TokaPay", "تراکنش های لینک پرداخت");
                    mediaStorageDir.mkdirs();
                }
            }

            File mediaFile;
            Date date = new Date();
            date.getTime();
            mediaFile = new File(mediaStorageDir.getPath() + File.separator + date.toString().trim() + ".pdf");

            try {
                document.writeTo(new FileOutputStream(mediaFile));
                Toast.makeText(AbsentLinkStatusActivity.this, "ذخیره شد", Toast.LENGTH_LONG).show();
                document.close();
            } catch (IOException e) {
                Log.e("main", "error " + e.toString());
                Toast.makeText(AbsentLinkStatusActivity.this, "Something wrong: " + e.toString(), Toast.LENGTH_LONG).show();
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
        recyclerView.setLayoutManager(new LinearLayoutManager(AbsentLinkStatusActivity.this));
        btnSearch = findViewById(R.id.actv_persons_link_btn_search);
        btn_CreatePdf = findViewById(R.id.frag_absent_link_btn_create_pdf);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_CreatePdf.setEnabled(false);
                btnSearch.setEnabled(false);
                setOnClickSearch();
            }
        });
    }

    private void config_Search() {
        View view = LayoutInflater.from(this).inflate(R.layout.alert_search_persons, null);


        final Button btn_Submit, btn_Close;

        edtName_s = view.findViewById(R.id.edt_name_search_person_link);
        edtFamily_s = view.findViewById(R.id.edt_family_search_person_link);
        edtNationalCode_s = view.findViewById(R.id.edt_national_code_search_person_link);
        edtAmountStart = view.findViewById(R.id.edt_start_amount_search_person);
        edtAmountEnd = view.findViewById(R.id.edt_end_amount_search_person);

        btnStartDate = view.findViewById(R.id.btn_start_date_search_person);
        btnStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateSelect_For_Search(1);
            }
        });
        btnEndDate = view.findViewById(R.id.btn_end_date_search_person);
        btnEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateSelect_For_Search(2);
            }
        });

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
                if (edtName_s.length() > 0 || edtAmountStart.length() > 0 || edtAmountEnd.length() > 0 || edtNationalCode_s.length() > 0 || edtFamily_s.length() > 0
                        || stringPersonStartDate != null || stringPersonEndDate != null) {
                    filter();
                    btn_CreatePdf.setEnabled(true);
                    btnSearch.setEnabled(true);
                    dialog.dismiss();
                } else {
                    Toast.makeText(AbsentLinkStatusActivity.this, "حداقل یک قسمت پر شود", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn_Close = view.findViewById(R.id.btn_closedialog_search_person_link);
        btn_Close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_CreatePdf.setEnabled(true);
                btnSearch.setEnabled(true);
                checkListBoolean = 0;
                dialog.dismiss();
            }
        });


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
                                btnStartDate.setText(persianCalendar.getPersianYear() + "/" + persianCalendar.getPersianMonth() + "/" + persianCalendar.getPersianDay());
                                stringPersonStartDate = btnStartDate.getText().toString();
                                break;

                            case 2:
                                btnEndDate.setText(persianCalendar.getPersianYear() + "/" + persianCalendar.getPersianMonth() + "/" + persianCalendar.getPersianDay());
                                stringPersonEndDate = btnEndDate.getText().toString();
                                break;
                        }
                    }

                    @Override
                    public void onDismissed() {

                    }
                });

        picker.show();
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
                AbsentLinkStatusActivity.super.onBackPressed();
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
        btn_CreatePdf.setVisibility(View.VISIBLE);

        filteredListResult = new ArrayList<>();

        if (firstField == 1)
            absentLinkStatusAdapter.filterList(parentFilteredList);

        stringName = null;
        stringFamilyName = null;
        stringNationalCode = null;
        stringAmountStart = null;
        stringAmountEnd = null;
        stringPersonStartDate = null;
        stringPersonEndDate = null;
        firstField = 0;
        config_Search();
    }

    private void advanceSearch() {
        edtName_s.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                stringName = s.toString();
            }
        });
        edtFamily_s.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                stringFamilyName = s.toString();
            }
        });
        edtNationalCode_s.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                stringNationalCode = s.toString();
            }
        });
        edtAmountStart.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                edtAmountStart.removeTextChangedListener(this);
                String string = edtAmountStart.getText().toString();

                String temp = DC.convertFNumToENum(string);
                String numberDate = temp.replaceAll("[^0-9]", "");

                if (numberDate.length() > 0) {
                    DecimalFormat sdd = new DecimalFormat("#,###");
                    Double doubleNumber = Double.parseDouble(numberDate);

                    String format = sdd.format(doubleNumber);
                    edtAmountStart.setText(format);
                    edtAmountStart.setSelection(format.length());

                }
                edtAmountStart.addTextChangedListener(this);
                stringAmountStart = editable.toString();
            }
        });

        edtAmountEnd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                edtAmountEnd.removeTextChangedListener(this);
                String string = edtAmountEnd.getText().toString();

                String temp = DC.convertFNumToENum(string);
                String numberDate = temp.replaceAll("[^0-9]", "");

                if (numberDate.length() > 0) {
                    DecimalFormat sdd = new DecimalFormat("#,###");
                    Double doubleNumber = Double.parseDouble(numberDate);

                    String format = sdd.format(doubleNumber);
                    edtAmountEnd.setText(format);
                    edtAmountEnd.setSelection(format.length());

                }
                edtAmountEnd.addTextChangedListener(this);
                stringAmountEnd = editable.toString();
            }
        });

    }

    private void filter() {

        if (parentFilteredList != null) {


            if (stringName != null) {
                for (PersonsModel item : parentFilteredList) {
                    if (item.getFirstName() != null) {
                        if (item.getFirstName().toLowerCase().contains(stringName.toLowerCase())) {
                            filteredListResult.add(item);
                        }
                    }
                }
                firstField = 1;
            }

            if (stringFamilyName != null) {
                if (firstField == 0) {
                    for (PersonsModel item : parentFilteredList) {
                        if (item.getLastName() != null) {
                            if (item.getLastName().toLowerCase().contains(stringFamilyName.toLowerCase())) {
                                filteredListResult.add(item);
                            }
                        }
                    }
                    firstField = 1;
                } else {
                    List<PersonsModel> tempList = new ArrayList<>();
                    for (PersonsModel item : filteredListResult) {
                        if (item.getLastName() != null) {
                            if (item.getLastName().toLowerCase().contains(stringFamilyName.toLowerCase())) {
                                tempList.add(item);
                            }
                        }
                    }
                    filteredListResult = new ArrayList<>();
                    filteredListResult = tempList;
                }
            }


//        stringNationalCode = convert_PersianNumber_To_Englishnumber(stringNationalCode);
            if (stringNationalCode != null) {
                if (firstField == 0) {
                    for (PersonsModel item : parentFilteredList) {
                        if (item.getNationalCode() != null) {
                            if (item.getNationalCode().toLowerCase().contains(stringNationalCode.toLowerCase())) {
                                filteredListResult.add(item);
                            }
                        }
                    }
                    firstField = 1;
                } else {
                    List<PersonsModel> tempList = new ArrayList<>();
                    for (PersonsModel item : filteredListResult) {
                        if (item.getNationalCode() != null) {
                            if (item.getNationalCode().toLowerCase().contains(stringNationalCode.toLowerCase())) {
                                tempList.add(item);
                            }
                        }
                    }
                    filteredListResult = new ArrayList<>();
                    filteredListResult = tempList;
                }

            }


            if (stringAmountStart != null || stringAmountEnd != null) {

                int amountStartInteger = 0;
                if (stringAmountStart != null) {
                    if (stringAmountStart.equals("")) {
                        amountStartInteger = 0;
                    } else {
                        String temp = DC.convertFNumToENum(stringAmountStart);
                        stringAmountStart = temp.replaceAll("[^0-9]", "");
                        amountStartInteger = Integer.parseInt(stringAmountStart);
                    }
                }

                int amountEndInteger = 0;
                if (stringAmountEnd != null) {
                    if (stringAmountEnd.equals("")) {
                        amountEndInteger = 0;
                    } else {
                        String temp1 = DC.convertFNumToENum(stringAmountEnd);
                        stringAmountEnd = temp1.replaceAll("[^0-9]", "");
                        amountEndInteger = Integer.parseInt(stringAmountEnd);
                    }
                } else {
                    amountEndInteger = 500000000;
                }


                if (firstField == 0) {
                    for (PersonsModel item : parentFilteredList) {
                        if (item.getAmount() != null) {


                            int productAmountInteger = Integer.parseInt(item.getAmount());

                            if (productAmountInteger >= amountStartInteger && productAmountInteger <= amountEndInteger) {
                                filteredListResult.add(item);
                            } else if (productAmountInteger <= amountEndInteger && amountStartInteger == 0) {
                                filteredListResult.add(item);
                            }
                        }
                    }
                    firstField = 1;
                } else {
                    List<PersonsModel> tempList = new ArrayList<>();
                    for (PersonsModel item : filteredListResult) {
                        if (item.getAmount() != null) {
                            int productAmountInteger = Integer.parseInt(item.getAmount());

                            if (productAmountInteger >= amountStartInteger && productAmountInteger <= amountEndInteger) {
                                tempList.add(item);
                            }
                        }
                    }
                    filteredListResult = new ArrayList<>();
                    filteredListResult = tempList;
                }
            }


            if (stringPersonStartDate != null || stringPersonEndDate != null) {
                if (firstField == 0) {
                    for (PersonsModel item : parentFilteredList) {
                        if (item.getTransactionDateTime() != null) {
                            boolean flag = dateAndTimeSeparator(stringPersonStartDate, stringPersonEndDate, item.getTransactionDateTime());
                            if (flag) {
                                filteredListResult.add(item);
                            }
                        }
                    }
                    firstField = 1;
                } else {
                    List<PersonsModel> tempList = new ArrayList<>();
                    for (PersonsModel item : filteredListResult) {
                        if (item.getTransactionDateTime() != null) {
                            boolean flag = dateAndTimeSeparator(stringPersonStartDate, stringPersonEndDate, item.getTransactionDateTime());
                            if (flag) {
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
                absentLinkStatusAdapter.filterList(filteredListResult);
                checkListBoolean = 1;
            }


        } else {
            Toast.makeText(AbsentLinkStatusActivity.this, "گزینه ای برای جستجو وجود ندارد", Toast.LENGTH_SHORT).show();
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

        AbsentStatusModel absentStatusModel = new AbsentStatusModel(USER_ID);

        Call<AbsentStatusModel> callPost = jsonPlaceHolderApi.getPaymentLinkStatus(absentStatusModel);
        callPost.enqueue(new Callback<AbsentStatusModel>() {
            @Override
            public void onResponse(@NotNull Call<AbsentStatusModel> call, @NotNull Response<AbsentStatusModel> response) {
                if (!response.isSuccessful()) {
                    Log.d("Response Not Successful", "Response Code: " + response.code());
                    return;
                }

                Log.d("Response Successful", "Response Code: " + response.code());


                AbsentStatusModel userData = response.body();

                assert userData != null;
                if (userData.getUsernameValidation().equals("true")) {
                    for (int i = 0; i < userData.getPersonsModelList().size(); i++) {
                        PersonsModel personsModel = userData.getPersonsModelList().get(i);

                        if (personsModel.getTransactionDateTime() != null)
                        {
                            String date = miladiDate_ConvertTO_PerisanDate(personsModel.getTransactionDateTime());
                            personsModel.setTransactionDateTime(date);
//                            personsModel.setTransactionDateTime(miladiDate_ConvertTO_PerisanDate(personsModel.getTransactionDateTime()));
                        }

                        

                        String strIPG = personsModel.getIpgResponseCode().trim().toLowerCase();
                        String strTransactionStatus = personsModel.getTransactionStatus().trim().toLowerCase();

                        if (strIPG.equals("00")) {
                            personsModel.setIpgResponseCode("موفق");
                        } else {
                            personsModel.setIpgResponseCode("ناموفق");
                        }

                        if (strTransactionStatus.equals("true") && PAYMENT_CONDITION_METHOD == 2) {
                            if (personsModel.getPaymentLink() != null) {
                                modelArrayList.add(personsModel);
                            }
                        } else {
                            if (strTransactionStatus.equals("false") && PAYMENT_CONDITION_METHOD == 1) {
                                if (personsModel.getPaymentLink() != null) {
                                    modelArrayList.add(personsModel);
                                }
                            }
                        }


                    }

                    if (modelArrayList != null) {
                        absentLinkStatusAdapter = new AbsentLinkStatusAdapter(modelArrayList, AbsentLinkStatusActivity.this);
                        recyclerView.setAdapter(absentLinkStatusAdapter);
                        parentFilteredList = modelArrayList;
                    } else {
                        Toast.makeText(AbsentLinkStatusActivity.this, userData.getDescription(), Toast.LENGTH_SHORT).show();
                    }
                    linearLayout.setVisibility(View.GONE);
                } else {
                    Toast.makeText(AbsentLinkStatusActivity.this, userData.getDescription(), Toast.LENGTH_SHORT).show();
                    linearLayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(@NotNull Call<AbsentStatusModel> call, Throwable t) {
                Log.d("Response Failure", "Failure Code: " + t.getMessage());
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

}
