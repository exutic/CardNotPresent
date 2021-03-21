package CardNotPresent.All_Persons.Persons;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
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
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import CardNotPresent.DC;
import CardNotPresent.DateConverter;
import CardNotPresent.MyNumberWatcher;
import CardNotPresent.R;
import com.wang.avi.AVLoadingIndicatorView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import ir.hamsaa.persiandatepicker.Listener;
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog;
import ir.hamsaa.persiandatepicker.util.PersianCalendar;

import static CardNotPresent.Main.MainActivity.USER_ID;


public class Persons_Payment_LinkFragment extends Fragment {

    private Persons_Payment_LinkViewModel personsPaymentLinkViewModel;
    private View root;
    private Button btnAddPerson, btn_search, btn_refresh;
    private RecyclerView recyclerView;
    private AlertDialog.Builder builder;
    private boolean online = false;
    PersonAdapter adapter;
    AVLoadingIndicatorView progressBar;
    EditText edtName_s, edtFamily_s, edtNationalCode_s, edtAmountStart, edtAmountEnd;
    String stringName, stringFamilyName, stringNationalCode, stringAmountStart, stringAmountEnd, stringPersonStartDate, stringPersonEndDate;
    Button btnStartDate, btnEndDate;

    List<PersonsModel> parentFilteredList;
    List<PersonsModel> filteredListResult;
    int firstField = 0;

    LinearLayout linearLayout;

    public static int REQUEST_CODE = 200;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_persons_payment_link, container, false);
        findViews();
        online = isOnline(requireActivity());
        if (online) {

            connecting_True();
        } else {
            connection_False();
        }

        btn_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                online = isOnline(requireActivity());
                if (online) {
                    connecting_True();
                } else {
                    connection_False();
                }
            }
        });
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setOnClickSearch();
            }
        });
        btnAddPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add_Person();
            }
        });

        return root;
    }

    private void setOnClickSearch() {
        recyclerView.setVisibility(View.VISIBLE);
        btn_search.setVisibility(View.VISIBLE);
        btnAddPerson.setVisibility(View.VISIBLE);

        filteredListResult = new ArrayList<>();

        if (firstField == 1)
            adapter.filterList(parentFilteredList);

        stringName = null;
        stringFamilyName = null;
        stringNationalCode = null;
        stringAmountStart = null;
        stringAmountEnd = null;
        stringPersonStartDate = null;
        stringPersonEndDate=null;
        firstField = 0;
        config_Search();
    }

    private void config_Search() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.alert_search_persons, null);


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

        builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(false)
                .setView(view);

        final AlertDialog dialog = builder.create();
        dialog.show();

        btn_Submit = view.findViewById(R.id.btn_submit_search_person_link);
        btn_Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtName_s.length() > 0 || edtAmountStart.length() > 0 || edtAmountEnd.length() > 0 || edtNationalCode_s.length() > 0 || edtFamily_s.length() > 0
                || stringPersonStartDate!=null || stringPersonEndDate!=null ) {
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
                dialog.dismiss();
            }
        });


    }

    public void connecting_True() {
        recyclerView.setVisibility(View.VISIBLE);
        btn_search.setVisibility(View.VISIBLE);
        btnAddPerson.setVisibility(View.VISIBLE);
        btn_refresh.setVisibility(View.GONE);

        personsPaymentLinkViewModel =
                ViewModelProviders.of(this).get(Persons_Payment_LinkViewModel.class);
        personsPaymentLinkViewModel.setLinearLayout(linearLayout);
        personsPaymentLinkViewModel.findLiveDatas();
        personsPaymentLinkViewModel.getModelArrayList().observe(getViewLifecycleOwner(), new Observer<List<PersonsModel>>() {
            @Override
            public void onChanged(List<PersonsModel> personsModelList) {
                if (personsModelList != null) {
                    adapter = new PersonAdapter(personsModelList, getActivity(), Persons_Payment_LinkFragment.this);
                    recyclerView.setAdapter(adapter);
                    parentFilteredList = personsModelList;
                } else {
                    Toast.makeText(getActivity(), "هیچ تراکنشی یافت نشد", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void connection_False() {
        recyclerView.setVisibility(View.GONE);
        btn_search.setVisibility(View.GONE);
        btnAddPerson.setVisibility(View.GONE);
        btn_refresh.setVisibility(View.VISIBLE);
        Toast.makeText(getActivity(), "عدم اتصال به اینترنت", Toast.LENGTH_SHORT).show();
    }

    public static boolean isOnline(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        @SuppressLint("MissingPermission") NetworkInfo netInfo = Objects.requireNonNull(cm).getActiveNetworkInfo();

        return (netInfo != null && netInfo.isConnected());
    }

    private void findViews() {

        btn_refresh = root.findViewById(R.id.frag_person_payment_link_btn_refresh);
        linearLayout = root.findViewById(R.id.frag_person_payLink_include__waiting_dialog);
        btn_search = root.findViewById(R.id.frag_persons_payment_link_btn_search);
        btnAddPerson = root.findViewById(R.id.frag_person_payment_link_btn_add);
        recyclerView = root.findViewById(R.id.frag_person_payment_link_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);

    }

    private void add_Person() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.alert_addperson_personlinkk, null);

        final EditText edtName, edtFamily, edtNationalCode, edtAmount, edtPhonenumber, edtDescription;
        Button btn_Submit, btn_Close;

        edtName = view.findViewById(R.id.alert_add_person_edt_name);
        edtFamily = view.findViewById(R.id.alert_add_person_edt_family);
        edtNationalCode = view.findViewById(R.id.alert_add_person_edt_national_code);
        edtAmount = view.findViewById(R.id.alert_add_person_edt_amount);
        edtAmount.addTextChangedListener(new MyNumberWatcher(edtAmount));

        edtPhonenumber = view.findViewById(R.id.alert_add_person_edt_phone_number);
        edtDescription = view.findViewById(R.id.alert_add_person_edt_description);

        btn_Submit = view.findViewById(R.id.alert_add_person_btn_submit_data);
        btn_Close = view.findViewById(R.id.alert_add_person_btn_close_dialog);

        builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(false).setView(view);

        final AlertDialog dialog = builder.create();
        dialog.show();

        btn_Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                online = isOnline(requireActivity());
                if (online)
                {
                    if (edtName.length() >= 3 && edtFamily.length() >= 3 && edtAmount.length() >= 5 &&
                            edtPhonenumber.length() == 11)
                    {
                        String check = edtPhonenumber.getText().toString();
                        if (!check.matches("(\\+98|0)?9\\d{9}"))
                        {
                            Toast.makeText(getActivity(), "شماره موبایل نامعتبر هست", Toast.LENGTH_SHORT).show();
                            edtPhonenumber.setText(null);
                        }
                        else
                            {
                                if(edtNationalCode.length()>0)
                                {
                                    if (edtNationalCode.length() == 10)
                                    {
                                        PersonsModel personsModel = new PersonsModel();

                                        String amountTemp = edtAmount.getText().toString();
                                        String temp = DC.convertFNumToENum(amountTemp);
                                        amountTemp = temp.replaceAll("[^0-9]", "");

                                        if (Integer.parseInt(amountTemp) <= 500000000 && Integer.parseInt(amountTemp) >= 10000)
                                        {

                                            personsModel.setAmount(amountTemp);//inja farsi number ro farsi set text mikonad.
                                            personsModel.setFirstName(edtName.getText().toString());
                                            personsModel.setLastName(edtFamily.getText().toString());
                                            personsModel.setNationalCode(edtNationalCode.getText().toString());
                                            personsModel.setMobileNumber(edtPhonenumber.getText().toString());
                                            personsModel.setDescription(edtDescription.getText().toString());
                                            personsModel.setUserId(USER_ID);
                                            personsPaymentLinkViewModel.setPersons_payment_linkFragment(Persons_Payment_LinkFragment.this);
                                            personsPaymentLinkViewModel.requestAddPerson(personsModel);
                                            dialog.dismiss();
                                        } else {
                                            Toast.makeText(getActivity(), "حداقل مبلغ10,000 و حداکثر مبلغ 500,000,000 ریال میباشد", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                    else
                                        {
                                            Toast.makeText(getActivity(), "کد ملی 10 رقم میباشد", Toast.LENGTH_SHORT).show();
                                        }
                                }
                                else
                                    {

                                        PersonsModel personsModel = new PersonsModel();

                                        String amountTemp = edtAmount.getText().toString();
                                        String temp = DC.convertFNumToENum(amountTemp);
                                        amountTemp = temp.replaceAll("[^0-9]", "");

                                        if (Integer.parseInt(amountTemp) <= 500000000 && Integer.parseInt(amountTemp) >= 10000)
                                        {
                                            personsModel.setAmount(amountTemp);//inja farsi number ro farsi set text mikonad.
                                            personsModel.setFirstName(edtName.getText().toString());
                                            personsModel.setLastName(edtFamily.getText().toString());
                                            personsModel.setNationalCode(edtNationalCode.getText().toString());
                                            personsModel.setMobileNumber(edtPhonenumber.getText().toString());
                                            personsModel.setDescription(edtDescription.getText().toString());
                                            personsModel.setUserId(USER_ID);
                                            personsPaymentLinkViewModel.setPersons_payment_linkFragment(Persons_Payment_LinkFragment.this);
                                            personsPaymentLinkViewModel.requestAddPerson(personsModel);
                                            dialog.dismiss();
                                        } else {
                                            Toast.makeText(getActivity(), "حداقل مبلغ10,000 و حداکثر مبلغ 500,000,000 ریال میباشد", Toast.LENGTH_LONG).show();
                                        }
                                    }
                        }
                    } else {
                        Toast.makeText(getContext(), "لطفا فیلد ها را پر کنید", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getContext(), "عدم اتصال به اینترنت", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn_Close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }

    void deletePerson(final PersonsModel personsModel) {

        View view = getLayoutInflater().inflate(R.layout.alert_delete, null);
        Button btnSubmit, btnClose;
        TextView textView;
        btnSubmit = view.findViewById(R.id.alert_delete_btnSubmit);
        btnClose = view.findViewById(R.id.alert_delete_btnClose);
        textView = view.findViewById(R.id.alert_delete_tv_header);

        textView.setText("میخوای حذف کنی؟");

        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(requireActivity());
        builder.setCancelable(false);
        builder.setView(view);

        final androidx.appcompat.app.AlertDialog alertDialog = builder.create();
        alertDialog.show();


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                personsPaymentLinkViewModel.requestDeletePerson(personsModel);
                alertDialog.dismiss();
            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.cancel();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == REQUEST_CODE) {
            if (data != null) {
                if (data.getStringExtra("Result").equals("true")) {

                    Toast.makeText(getActivity(), "لینک پرداخت صادر شد", Toast.LENGTH_SHORT).show();
                    personsPaymentLinkViewModel.findLiveDatas();
                    personsPaymentLinkViewModel.getModelArrayList().observe(getViewLifecycleOwner(), new Observer<List<PersonsModel>>() {
                        @Override
                        public void onChanged(List<PersonsModel> s) {
                            adapter = new PersonAdapter(s, getActivity(), Persons_Payment_LinkFragment.this);
                            recyclerView.setAdapter(adapter);
                            parentFilteredList = s;
                        }
                    });
                }
            } else {
                Toast.makeText(getActivity(), "لغو عملیات", Toast.LENGTH_SHORT).show();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void filter() {

        if (parentFilteredList!=null) {

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


            if (stringAmountStart != null || stringAmountEnd != null)
            {
                int amountStartInteger = 0;
                if (stringAmountStart != null)
                {
                    if (stringAmountStart.equals(""))
                    {
                        amountStartInteger=0;
                    }
                    else
                    {
                        String temp = DC.convertFNumToENum(stringAmountStart);
                        stringAmountStart = temp.replaceAll("[^0-9]", "");
                        amountStartInteger = Integer.parseInt(stringAmountStart);
                    }
                }

                int amountEndInteger = 0;
                if (stringAmountEnd != null)
                {
                    if (stringAmountEnd.equals(""))
                    {
                        amountEndInteger=0;
                    }
                    else
                    {
                        String temp1 = DC.convertFNumToENum(stringAmountEnd);
                        stringAmountEnd = temp1.replaceAll("[^0-9]", "");
                        amountEndInteger = Integer.parseInt(stringAmountEnd);
                    }
                }
                else
                {
                    amountEndInteger = 500000000;
                }



                if (firstField == 0) {
                    for (PersonsModel item : parentFilteredList)
                    {
                        if (item.getAmount() != null) {
                            int productAmountInteger = Integer.parseInt(item.getAmount());

                            if (productAmountInteger >= amountStartInteger && productAmountInteger <= amountEndInteger)
                            {
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
                        if (item.getInsertDateTime() != null) {
                            boolean flag = dateAndTimeSeparator(stringPersonStartDate, stringPersonEndDate, item.getInsertDateTime());
                            if (flag) {
                                filteredListResult.add(item);
                            }
                        }
                    }
                    firstField = 1;
                } else {
                    List<PersonsModel> tempList = new ArrayList<>();
                    for (PersonsModel item : filteredListResult) {
                        if (item.getInsertDateTime() != null) {
                            boolean flag = dateAndTimeSeparator(stringPersonStartDate, stringPersonEndDate, item.getInsertDateTime());
                            if (flag) {
                                tempList.add(item);
                            }
                        }
                    }
                    filteredListResult = new ArrayList<>();
                    filteredListResult = tempList;
                }
            }



            if (filteredListResult.size()==0) {
                recordNotFoundDialog();
            } else adapter.filterList(filteredListResult);


        }

        else {
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

    }

    public void dateSelect_For_Search(final int type) {

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
        if (dateStart!=null)
        {
            String numberStartDate = dateStart.replaceAll("[^0-9]", "");
            intStartDate = Integer.parseInt(numberStartDate);
        }
        else
        {
            intStartDate = 139011;
        }




        int intEndDate =0;
        if (dateEnd!=null)
        {
            String numberEndDate = dateEnd.replaceAll("[^0-9]", "");
            intEndDate = Integer.parseInt(numberEndDate);
        }
        else
        {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH) + 1;
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DateConverter converter = new DateConverter();
            converter.gregorianToPersian(year, month, day);
            year = converter.getYear();
            month = converter.getMonth();
            day = converter.getDay();

            String string = String.valueOf(year)+String.valueOf(month)+String.valueOf(day);

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
        btnAddPerson.setVisibility(View.GONE);
        btn_search.setVisibility(View.GONE);

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

    private String convert_PersianNumber_To_Englishnumber(String str) {
        try {
            int d = Integer.parseInt(str);
            String result = String.valueOf(d);
            return result;
        } catch (Exception e) {
            Log.d("Pro_Trans_ConditionFrag", Objects.requireNonNull(e.getMessage()));
            return str;
        }
    }
}
