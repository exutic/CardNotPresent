package CardNotPresent.All_Persons.PersonStatusList;

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
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import CardNotPresent.DC;
import CardNotPresent.DateConverter;
import CardNotPresent.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import ir.hamsaa.persiandatepicker.Listener;
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog;
import ir.hamsaa.persiandatepicker.util.PersianCalendar;

import static CardNotPresent.Main.MainActivity.PERMISSION_VALIDATOR;

public class Persons_Transaction_ConditionFragment extends Fragment {

    private Persons_Transaction_ConditionViewModel personsTransactionConditionViewModel;
    private View root;
    private RecyclerView recyclerView;
    private Button btn_search;
    private Button btn_refresh, btn_CreatePdf;
    private LinearLayout linearLayout;
    private boolean online = false;
    private PerCondition_Adapter adapter;
    private LinearLayout linearLayoutWaiting;

    private EditText edtName_s, edtFamily_s, edtNationalCode_s,
            edtMobileNumber, edtRefNum, edtResNum, edtTerminalId, edtMerchantId,
            edtAmountStart, edtAmountEnd;

    private TextView tv_TransStatus;
    private String stringName, stringFamilyName, stringNationalCode, stringMobile,
            stringMerchantId, stringTerminalId, stringRefNum, stringResNum,
            stringTransaction, stringAmountStart, stringAmountEnd, stringPersonStartDate,
            stringPersonEndDate;

    private Button btnStartDate, btnEndDate;
    private List<PersonsCondition_Model> parentFilteredList;
    private List<PersonsCondition_Model> filteredListResult;
    private int firstField = 0;
    int checkListBoolean = 0;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_persons__transaction__condition, container, false);


        findViews();
        online = isOnline(requireActivity()); //online = isOnline(Objects.requireNonNull(getActivity()));
        if (online) {
            connecting_True();
        } else {
            connection_False();
        }

        btn_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                online = isOnline(requireActivity());   //online = isOnline(Objects.requireNonNull(getActivity()));
                if (online) {
                    connecting_True();
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
                    Toast.makeText(getActivity(), "دسترسی به حافطه مسدود میباشد", Toast.LENGTH_SHORT).show();
                    Toast.makeText(getActivity(), getResources().getText(R.string.permission_validator_toast), Toast.LENGTH_SHORT).show();
                }
            }
        });

        return root;
    }

    private void create_Pdf() {
        List<PersonsCondition_Model> resultPdfList = new ArrayList<>();


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

        if (resultPdfList != null && resultPdfList.size()>0) {
            Toast.makeText(getActivity(), "در حال ذخیره فایل، لطفا صبر کنید", Toast.LENGTH_SHORT).show();
            linearLayoutWaiting.setVisibility(View.VISIBLE);
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
                String transaction_Status = "";
                if (resultPdfList.get(i).getTransactionStatus() != null) {
                    if (resultPdfList.get(i).getTransactionStatus().equals("True")) {
                        transaction_Status = "تشویه شده";
                    } else {
                        transaction_Status = "تشویه نشده";
                    }
                }
                int p = (pageInfo.getPageWidth() / 2);
                canvas.drawText(name, p, 30 + counter, paint);
                canvas.drawText("مبلغ : " + resultPdfList.get(i).getAmount(), p, 50 + counter, paint);
                canvas.drawText("تاریخ تراکنش : " + resultPdfList.get(i).getTransactionDateTime(), p, 70 + counter, paint);
                canvas.drawText("شماره پیگیری : " + resultPdfList.get(i).getResNum(), p, 90 + counter, paint);
                canvas.drawText("نتیجه تراکنش : " + resultPdfList.get(i).getIpgResponseCode(), p, 110 + counter, paint);
                canvas.drawText("وضعیت تراکنش : " + transaction_Status, p, 130 + counter, paint);
                canvas.drawLine(0, 145 + counter, 800, 145 + counter, paint);

                counter += 145;
                temp++;
                if (temp == 4 || i == resultPdfList.size() - 1) {
                    document.finishPage(page);
                }
            }

            File mediaStorageDir = new File("/sdcard/TokaPay", "وضعیت تراکنش های اشخاص");
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
                Toast.makeText(getActivity(), "ذخیره شد", Toast.LENGTH_LONG).show();
                document.close();

            } catch (IOException e) {
                Log.e("main", "error " + e.toString());
                Toast.makeText(getActivity(), "Something wrong: " + e.toString(), Toast.LENGTH_LONG).show();
            }

            linearLayoutWaiting.setVisibility(View.GONE);

        } else {
            Toast.makeText(getActivity(), "لیست خالی است", Toast.LENGTH_SHORT).show();
        }

    }

    private void connecting_True() {
        recyclerView.setVisibility(View.VISIBLE);
        linearLayout.setVisibility(View.VISIBLE);
        btn_refresh.setVisibility(View.GONE);
        personsTransactionConditionViewModel =
                ViewModelProviders.of(this).get(Persons_Transaction_ConditionViewModel.class);
        personsTransactionConditionViewModel.setLinearLayout(linearLayoutWaiting);
        personsTransactionConditionViewModel.findLiveDatas();
        personsTransactionConditionViewModel.getPerConditionModelList().observe(requireActivity(), new Observer<List<PersonsCondition_Model>>() {
            @Override
            public void onChanged(List<PersonsCondition_Model> personsCondition_models) {
                if (personsCondition_models != null) {
                    adapter = new PerCondition_Adapter(personsCondition_models, getActivity(), Persons_Transaction_ConditionFragment.this);
                    recyclerView.setAdapter(adapter);
                    parentFilteredList = personsCondition_models;

                } else {
                    Toast.makeText(getActivity(), "هیچ موردی یافت نشد", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void connection_False() {
        recyclerView.setVisibility(View.GONE);
        linearLayout.setVisibility(View.GONE);
        btn_refresh.setVisibility(View.VISIBLE);
        Toast.makeText(getActivity(), "عدم اتصال به اینترنت", Toast.LENGTH_SHORT).show();
    }

    public static boolean isOnline(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        return (netInfo != null && netInfo.isConnected());
    }

    private void findViews() {
        btn_CreatePdf = root.findViewById(R.id.frag_pre_condition_btn_create_pdf);
        linearLayoutWaiting = root.findViewById(R.id.frag_person_trans_condition_include__waiting_dialog);
        btn_refresh = root.findViewById(R.id.frag_person_transaction_condition_btn_refresh);
        linearLayout = root.findViewById(R.id.frag_person_transaction_condition_linear);
        recyclerView = root.findViewById(R.id.frag_person_transaction_condition_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        btn_search = root.findViewById(R.id.frag_person_transaction_condition_search_search);
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_CreatePdf.setEnabled(false);
                btn_search.setEnabled(false);
                setOnClickSearch();
            }
        });
        parentFilteredList = new ArrayList<>();
    }

    private void filter() {

        if (parentFilteredList != null) {

            if (stringName != null) {
                for (PersonsCondition_Model item : parentFilteredList) {
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
                    for (PersonsCondition_Model item : parentFilteredList) {
                        if (item.getLastName() != null) {
                            if (item.getLastName().toLowerCase().contains(stringFamilyName.toLowerCase())) {
                                filteredListResult.add(item);
                            }
                        }
                    }
                    firstField = 1;
                } else {
                    List<PersonsCondition_Model> tempList = new ArrayList<>();
                    for (PersonsCondition_Model item : filteredListResult) {
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

            if (stringMobile != null) {
                if (firstField == 0) {
                    for (PersonsCondition_Model item : parentFilteredList) {
                        if (item.getMobileNumber() != null) {
                            if (item.getMobileNumber().toLowerCase().contains(stringMobile.toLowerCase())) {
                                filteredListResult.add(item);
                            }
                        }
                    }
                    firstField = 1;
                } else {
                    List<PersonsCondition_Model> tempList = new ArrayList<>();
                    for (PersonsCondition_Model item : filteredListResult) {
                        if (item.getMobileNumber() != null) {
                            if (item.getMobileNumber().toLowerCase().contains(stringMobile.toLowerCase())) {
                                tempList.add(item);
                            }
                        }
                    }
                    filteredListResult = new ArrayList<>();
                    filteredListResult = tempList;
                }
            }

            if (stringMerchantId != null) {
                if (firstField == 0) {
                    for (PersonsCondition_Model item : parentFilteredList) {
                        if (item.getMerchantId() != null) {
                            if (item.getMerchantId().toLowerCase().contains(stringMerchantId.toLowerCase())) {
                                filteredListResult.add(item);
                            }
                        }
                    }
                    firstField = 1;
                } else {
                    List<PersonsCondition_Model> tempList = new ArrayList<>();
                    for (PersonsCondition_Model item : filteredListResult) {
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

            if (stringTerminalId != null) {
                if (firstField == 0) {
                    for (PersonsCondition_Model item : parentFilteredList) {
                        if (item.getTerminalId() != null) {
                            if (item.getTerminalId().toLowerCase().contains(stringTerminalId.toLowerCase())) {
                                filteredListResult.add(item);
                            }
                        }
                    }
                    firstField = 1;
                } else {
                    List<PersonsCondition_Model> tempList = new ArrayList<>();
                    for (PersonsCondition_Model item : filteredListResult) {
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

            if (stringRefNum != null) {
                if (firstField == 0) {
                    for (PersonsCondition_Model item : parentFilteredList) {
                        if (item.getRefNum() != null) {
                            if (item.getRefNum().toLowerCase().contains(stringRefNum.toLowerCase())) {
                                filteredListResult.add(item);
                            }
                        }
                    }
                    firstField = 1;
                } else {
                    List<PersonsCondition_Model> tempList = new ArrayList<>();
                    for (PersonsCondition_Model item : filteredListResult) {
                        if (item.getRefNum() != null) {
                            if (item.getRefNum().toLowerCase().contains(stringRefNum.toLowerCase())) {
                                tempList.add(item);
                            }
                        }
                    }
                    filteredListResult = new ArrayList<>();
                    filteredListResult = tempList;
                }
            }

            if (stringResNum != null) {
                if (firstField == 0) {
                    for (PersonsCondition_Model item : parentFilteredList) {
                        if (item.getResNum() != null) {
                            if (item.getResNum().toLowerCase().contains(stringResNum.toLowerCase())) {
                                filteredListResult.add(item);
                            }
                        }
                    }
                    firstField = 1;
                } else {
                    List<PersonsCondition_Model> tempList = new ArrayList<>();
                    for (PersonsCondition_Model item : filteredListResult) {
                        if (item.getResNum() != null) {
                            if (item.getResNum().toLowerCase().contains(stringResNum.toLowerCase())) {
                                tempList.add(item);
                            }
                        }
                    }
                    filteredListResult = new ArrayList<>();
                    filteredListResult = tempList;
                }
            }

            if (stringTransaction != null) {
                if (stringTransaction.compareTo("2") == 0) {
                    if (firstField == 0) {
                        for (PersonsCondition_Model item : parentFilteredList) {
                            if (item.getTransactionStatus() != null) {
                                filteredListResult.add(item);
                            }
                        }
                        firstField = 1;
                    } else {
                        List<PersonsCondition_Model> tempList = new ArrayList<>();
                        for (PersonsCondition_Model item : filteredListResult) {
                            if (item.getTransactionStatus() != null) {
                                tempList.add(item);
                            }
                        }
                        filteredListResult = new ArrayList<>();
                        filteredListResult = tempList;
                    }
                } else {
                    if (firstField == 0) {
                        for (PersonsCondition_Model item : parentFilteredList) {
                            if (item.getTransactionStatus() != null) {
                                if (item.getTransactionStatus().toLowerCase().contains(stringTransaction.toLowerCase())) {
                                    filteredListResult.add(item);
                                }
                            }
                        }
                        firstField = 1;
                    } else {
                        List<PersonsCondition_Model> tempList = new ArrayList<>();
                        for (PersonsCondition_Model item : filteredListResult) {
                            if (item.getTransactionStatus() != null) {
                                if (item.getTransactionStatus().toLowerCase().contains(stringTransaction.toLowerCase())) {
                                    tempList.add(item);
                                }
                            }
                        }
                        filteredListResult = new ArrayList<>();
                        filteredListResult = tempList;
                    }
                }

            }

            if (stringNationalCode != null) {
                if (firstField == 0) {
                    for (PersonsCondition_Model item : parentFilteredList) {
                        if (item.getNationalCode() != null) {
                            if (item.getNationalCode().toLowerCase().contains(stringNationalCode.toLowerCase())) {
                                filteredListResult.add(item);
                            }
                        }
                    }
                    firstField = 1;
                } else {
                    List<PersonsCondition_Model> tempList = new ArrayList<>();
                    for (PersonsCondition_Model item : filteredListResult) {
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
                    for (PersonsCondition_Model item : parentFilteredList) {
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
                    List<PersonsCondition_Model> tempList = new ArrayList<>();
                    for (PersonsCondition_Model item : filteredListResult) {
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
                    for (PersonsCondition_Model item : parentFilteredList) {
                        if (item.getTransactionDateTime() != null) {
                            boolean flag = dateAndTimeSeparator(stringPersonStartDate, stringPersonEndDate, item.getTransactionDateTime());
                            if (flag) {
                                filteredListResult.add(item);
                            }
                        }
                    }
                    firstField = 1;
                } else {
                    List<PersonsCondition_Model> tempList = new ArrayList<>();
                    for (PersonsCondition_Model item : filteredListResult) {
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


            if (filteredListResult.size() == 0) {
                recordNotFoundDialog();
                checkListBoolean = 0;
            } else {
                adapter.filterList(filteredListResult);
                checkListBoolean = 1;
                btn_CreatePdf.setEnabled(true);
                btn_search.setEnabled(true);
            }


        } else {
            Toast.makeText(getActivity(), "گزینه ای برای جستجو وجود ندارد", Toast.LENGTH_SHORT).show();
        }


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
        edtTerminalId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                stringTerminalId = editable.toString();
            }
        });
        edtMobileNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                stringMobile = editable.toString();
            }
        });
        edtRefNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                stringRefNum = editable.toString();
            }
        });
        edtResNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                stringResNum = editable.toString();
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
            public void afterTextChanged(Editable editable) {
                stringMerchantId = editable.toString();
            }
        });

    }

    private void dateSelect_For_Search(final int type) {

        // type moshakhas mikone ke meghdar dar dateStart save beshe ya dateEnd
        PersianDatePickerDialog picker = new PersianDatePickerDialog(getActivity())
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
        btn_search.setVisibility(View.GONE);
        btn_CreatePdf.setVisibility(View.GONE);

        View view = getLayoutInflater().inflate(R.layout.alert_false_search, null);

        Button goHome, goSearch;

        goHome = view.findViewById(R.id.alert_false_search_btn_homepage);
        goSearch = view.findViewById(R.id.alert_false_search_btn_show_search);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        builder.setCancelable(false);

        final AlertDialog dialog = builder.create();
        dialog.show();

        goHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                getActivity().onBackPressed();
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
        btn_search.setVisibility(View.VISIBLE);
        btn_CreatePdf.setVisibility(View.VISIBLE);

        filteredListResult = new ArrayList<>();

        if (firstField == 1)
            adapter.filterList(parentFilteredList);

        stringName = null;
        stringFamilyName = null;
        stringNationalCode = null;
        stringMobile = null;
        stringMerchantId = null;
        stringTerminalId = null;
        stringRefNum = null;
        stringResNum = null;
        stringTransaction = null;
        stringAmountStart = null;
        stringAmountEnd = null;
        stringPersonStartDate = null;
        stringPersonEndDate = null;
        firstField = 0;
        config_Search();
    }

    private void config_Search() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.alert_search_persons_condition, null);

        final Button btn_Submit, btn_Close, btn_Movafagh, btn_NaMovafagh, btn_TwoResult;
        edtName_s = view.findViewById(R.id.edt_name_search_person_condition);
        edtFamily_s = view.findViewById(R.id.edt_family_search_person_condition);
        edtNationalCode_s = view.findViewById(R.id.edt_national_code_search_person_condition);
        edtMobileNumber = view.findViewById(R.id.edt_mobile_number_search_person_condition);
        edtMerchantId = view.findViewById(R.id.edt_merchant_id_search_person_condition);
        edtTerminalId = view.findViewById(R.id.edt_terminal_id_search_person_condition);
        edtRefNum = view.findViewById(R.id.edt_ref_num_search_person_condition);
        edtResNum = view.findViewById(R.id.edt_res_num_search_person_condition);
        tv_TransStatus = view.findViewById(R.id.edt_transaction_status_search_person_condition);
        tv_TransStatus.setText("انتخاب وضعیت تراکنش");
        edtAmountStart = view.findViewById(R.id.edt_start_amount_search_person_condition);
        edtAmountEnd = view.findViewById(R.id.edt_end_amount_search_person_condition);

//        if (parentFilteredList != null)
//        {
//            String merchantId = parentFilteredList.get(0).getMerchantId();
//
//            final List<String> itemsMerchantId = new ArrayList<>();
//            itemsMerchantId.add(merchantId);
//            ArrayAdapter<String> adapterMerchantID = new ArrayAdapter<String>(Objects.requireNonNull(getActivity()),
//                    android.R.layout.simple_spinner_item, itemsMerchantId);
//            SMerchantId.setAdapter(adapterMerchantID);
//            SMerchantId.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                @Override
//                public void onItemSelected(AdapterView<?> parent, View view,
//                                           int position, long id) {
//                    Log.v("item", (String) parent.getItemAtPosition(position));
//                    stringMerchantId = itemsMerchantId.get(position);
//                }
//
//                @Override
//                public void onNothingSelected(AdapterView<?> parent) {
//                    // TODO Auto-generated method stub
//                }
//            });
//
//
//            String terminalId = parentFilteredList.get(0).getTerminalId();
//            final List<String> itemsTerminalId = new ArrayList<>();
//            itemsTerminalId.add(terminalId);
//            ArrayAdapter<String> adapterTerminalId = new ArrayAdapter<String>(Objects.requireNonNull(getActivity()),
//                    android.R.layout.simple_spinner_item, itemsTerminalId);
//            STerminalId.setAdapter(adapterTerminalId);
//            STerminalId.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                @Override
//                public void onItemSelected(AdapterView<?> parent, View view,
//                                           int position, long id) {
//                    Log.v("item", (String) parent.getItemAtPosition(position));
//                    stringTerminalId = itemsTerminalId.get(position);
//                }
//
//                @Override
//                public void onNothingSelected(AdapterView<?> parent) {
//                    // TODO Auto-generated method stub
//                }
//            });
//        }


        btnStartDate = view.findViewById(R.id.btn_start_date_search_person_condition);
        btnStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateSelect_For_Search(1);
            }
        });
        btnEndDate = view.findViewById(R.id.btn_end_date_search_person_condition);
        btnEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateSelect_For_Search(2);
            }
        });


        btn_TwoResult = view.findViewById(R.id.btn_transaction_status_two_result_search_person_condition);
        btn_NaMovafagh = view.findViewById(R.id.btn_transaction_status_not_ok_search_person_condition);
        btn_Movafagh = view.findViewById(R.id.btn_transaction_status_ok_search_person_condition);
        btn_Movafagh.setText("تسویه شده");
        btn_NaMovafagh.setText("تسویه نشده");
        btn_Movafagh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btn_NaMovafagh.setBackgroundResource(R.drawable.round_search);
                btn_TwoResult.setBackgroundResource(R.drawable.round_search);
                btn_Movafagh.setBackgroundResource(R.drawable.round_btn_status_movafagh);
                tv_TransStatus.setText("تسویه شده");
                stringTransaction = "true";
            }
        });

        btn_NaMovafagh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btn_Movafagh.setBackgroundResource(R.drawable.round_search);
                btn_TwoResult.setBackgroundResource(R.drawable.round_search);
                btn_NaMovafagh.setBackgroundResource(R.drawable.round_btn_status_namovafagh);
                tv_TransStatus.setText("تسویه نشده");
                stringTransaction = "false";
            }
        });

        btn_TwoResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btn_Movafagh.setBackgroundResource(R.drawable.round_search);
                btn_NaMovafagh.setBackgroundResource(R.drawable.round_search);
                btn_TwoResult.setBackgroundResource(R.drawable.round_btn_status_two_result);
                tv_TransStatus.setText("هر دو وضعیت انتخاب شد");
                stringTransaction = "2";
            }
        });

        advanceSearch();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(false)
                .setView(view);

        final AlertDialog dialog = builder.create();
        dialog.show();

        btn_Submit = view.findViewById(R.id.btn_submit_search_person_condition);
        btn_Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtName_s.length() > 0 || edtAmountStart.length() > 0 || edtAmountEnd.length() > 0 ||
                        edtNationalCode_s.length() > 0 || edtFamily_s.length() > 0 || stringPersonStartDate != null ||
                        stringPersonEndDate != null || edtTerminalId.length() > 0 || edtMerchantId.length() > 0 ||
                        edtMobileNumber.length() > 0 || edtRefNum.length() > 0 || edtResNum.length() > 0 ||
                        stringTransaction != null) {
                    filter();
                    dialog.dismiss();
                } else {
                    Toast.makeText(getActivity(), "حداقل یک قسمت پر شود", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn_Close = view.findViewById(R.id.btn_closedialog_search_person_link);
        btn_Close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_CreatePdf.setEnabled(true);
                btn_search.setEnabled(true);
                checkListBoolean = 0;
                dialog.dismiss();
            }
        });


    }

}
