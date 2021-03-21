package CardNotPresent.All_Product.Products;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
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

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import ir.hamsaa.persiandatepicker.Listener;
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog;
import ir.hamsaa.persiandatepicker.util.PersianCalendar;

import static CardNotPresent.Main.MainActivity.USER_ID;


public class Product_Payment_linkFragment extends Fragment {

    private Product_Payment_LinkViewModel productPaymentLinkViewModel;
    private View root;

    private Button btnAddProduct, btnSearch, btn_refresh;
    private RecyclerView recyclerView;


    private ProductAdapter adapter;
    AlertDialog.Builder builder;
    //    AVLoadingIndicatorView progressBar;
    boolean online = false;
    public static int REQUEST_CODE = 200;

    EditText edt_ProductName_s, edt_ProductCode_s, edt_ProductAmountStart_s, edt_ProductAmountEnd_s;
    String stringProductName, stringProductCode, stringProductAmountStart, stringProductAmountEnd, stringProductStartDate, stringProductEndDate;
    Button btn_submit, btn_CloseDialog, btnDateStart_s, btnDateEnd_s;

    List<ProductModel> parentFilteredList;
    List<ProductModel> filteredListResult;
    int firstField = 0;
    LinearLayout linearLayout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_product_payment_link, container, false);

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

        btnSearch.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                setOnClickSearch();
            }
        });


        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add_Product();
            }
        });
        return root;
    }

    private void setOnClickSearch() {
        recyclerView.setVisibility(View.VISIBLE);
        btnAddProduct.setVisibility(View.VISIBLE);
        btnSearch.setVisibility(View.VISIBLE);
        filteredListResult = new ArrayList<>();

        if (firstField == 1)
            adapter.filterList(parentFilteredList);

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

    private void config_search() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.alert_search_product, null);

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

        builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(false)
                .setView(view);

        final AlertDialog dialog = builder.create();
        dialog.show();

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edt_ProductName_s.length() > 0 || edt_ProductAmountEnd_s.length() > 0 ||
                        edt_ProductAmountStart_s.length() > 0 || edt_ProductCode_s.length() > 0 ||
                        stringProductEndDate!=null  || stringProductStartDate!=null ) {
                    filter();
                    dialog.dismiss();
                } else {
                    Toast.makeText(getActivity(), "حداقل یک قسمت پر شود", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_CloseDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    public void connecting_True() {
        btnAddProduct.setVisibility(View.VISIBLE);
        btnSearch.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
        btn_refresh.setVisibility(View.GONE);
        productPaymentLinkViewModel =
                ViewModelProviders.of(this).get(Product_Payment_LinkViewModel.class);
        productPaymentLinkViewModel.setLinearLayout(linearLayout);
        productPaymentLinkViewModel.findLiveData();
        productPaymentLinkViewModel.getModelArrayList().observe(getViewLifecycleOwner(), new Observer<List<ProductModel>>() {
            @Override
            public void onChanged(@Nullable List<ProductModel> s) {
                if (s != null) {
                    adapter = new ProductAdapter(s, getActivity(), Product_Payment_linkFragment.this);
                    recyclerView.setAdapter(adapter);
                    parentFilteredList = s;
                } else {
                    Toast.makeText(getActivity(), "هیچ محصولی یافت نشد", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void connection_False() {
        btnAddProduct.setVisibility(View.GONE);
        btnSearch.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        btn_refresh.setVisibility(View.VISIBLE);
        Toast.makeText(getActivity(), "عدم اتصال به اینترنت", Toast.LENGTH_SHORT).show();
    }

    public static boolean isOnline(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        return (netInfo != null && netInfo.isConnected());
    }

    private void add_Product() {

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.alert_addproduct_productlink, null);
        final EditText edt_NameProduct, edt_CodeProduct, edt_AmountProduct, edt_DescriptionProduct;
        Button btn_submit, btn_CloseDialog;

        edt_NameProduct = view.findViewById(R.id.edt_name_addProduct_productlink);
        edt_CodeProduct = view.findViewById(R.id.edt_code_addProduct_productlink);

        edt_AmountProduct = view.findViewById(R.id.edt_amount_addProduct_productlink);
        edt_AmountProduct.addTextChangedListener(new MyNumberWatcher(edt_AmountProduct));

        edt_DescriptionProduct = view.findViewById(R.id.edt_description_addProduct_productlink);
        btn_submit = view.findViewById(R.id.btn_submit_addproduct_productlink);
        btn_CloseDialog = view.findViewById(R.id.btn_closedialog_addproduct_productlink);

        builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(false)
                .setView(view);

        final AlertDialog dialog = builder.create();
        dialog.show();


        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                online = isOnline(requireActivity());
                if (online) {
                    if (edt_NameProduct.length() > 0 && edt_CodeProduct.length() > 0
                            && edt_AmountProduct.length() > 0) {


                        ProductModel productModel = new ProductModel();
                        String amountTemp = edt_AmountProduct.getText().toString();
                        String temp = DC.convertFNumToENum(amountTemp);
                        amountTemp = temp.replaceAll("[^0-9]", "");
                        if (Integer.parseInt(amountTemp) <= 500000000 && Integer.parseInt(amountTemp) >= 10000) {
                            productModel.setProductName(edt_NameProduct.getText().toString());
                            productModel.setProductCode(edt_CodeProduct.getText().toString());
                            productModel.setProductPrice(amountTemp);
                            productModel.setDescription(edt_DescriptionProduct.getText().toString());
                            productModel.setUserId(USER_ID);

                            productPaymentLinkViewModel.setProduct_payment_linkFragment(Product_Payment_linkFragment.this);
                            productPaymentLinkViewModel.requestAddProduct(productModel);
                            dialog.dismiss();
                        } else {
                            Toast.makeText(getContext(), "حداقل مبلغ10,000 و حداکثر مبلغ 500,000,000 ریال میباشد", Toast.LENGTH_LONG).show();
                        }

                    } else {
                        Toast.makeText(getContext(), "لطفا فیلد ها را پر کنید", Toast.LENGTH_SHORT).show();
                    }


                } else {
                    Toast.makeText(getContext(), "اتصال شما به اینترنت قطع میباشد", Toast.LENGTH_SHORT).show();
                }

            }
        });
        btn_CloseDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    private void findViews() {
        btnAddProduct = root.findViewById(R.id.frag_product_payment_link_btn_add_new_product);
        btnSearch = root.findViewById(R.id.frag_product_payment_link_btn_search);
        recyclerView = root.findViewById(R.id.frag_product_payment_link_recycler);
        btn_refresh = root.findViewById(R.id.frag_poduct_payment_link_btn_refresh);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        linearLayout = root.findViewById(R.id.frag_product_payLink_condition_include__waiting_dialog);

    }

    public void deleteProduct(final ProductModel productModel) {

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
                productPaymentLinkViewModel.requestDeleteProduct(productModel);
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
                    productPaymentLinkViewModel.findLiveData();
                    productPaymentLinkViewModel.getModelArrayList().observe(getViewLifecycleOwner(), new Observer<List<ProductModel>>() {
                        @Override
                        public void onChanged(@Nullable List<ProductModel> s) {
                            adapter = new ProductAdapter(s, getActivity(), Product_Payment_linkFragment.this);
                            recyclerView.setAdapter(adapter);
                            parentFilteredList = s;
                        }
                    });
                } else {
                    Toast.makeText(getActivity(), "خطا در صدور لینک پرداخت", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getActivity(), "لغو عملیات", Toast.LENGTH_SHORT).show();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void filter() {

        if (parentFilteredList!=null)
        {



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


            if (stringProductAmountStart != null || stringProductAmountEnd != null)
            {
                int amountStartInteger = 0;
                if (stringProductAmountStart != null)
                {
                    if (stringProductAmountStart.equals(""))
                    {
                        amountStartInteger=0;
                    }
                    else
                    {
                        String temp = DC.convertFNumToENum(stringProductAmountStart);
                        stringProductAmountStart = temp.replaceAll("[^0-9]", "");
                        amountStartInteger = Integer.parseInt(stringProductAmountStart);
                    }
                }

                int amountEndInteger = 0;
                if (stringProductAmountEnd != null)
                {
                    if (stringProductAmountEnd.equals(""))
                    {
                        amountEndInteger=0;
                    }
                    else
                    {
                        String temp1 = DC.convertFNumToENum(stringProductAmountEnd);
                        stringProductAmountEnd = temp1.replaceAll("[^0-9]", "");
                        amountEndInteger = Integer.parseInt(stringProductAmountEnd);
                    }
                }
                else
                {
                    amountEndInteger = 500000000;
                }




                if (firstField == 0) {
                    for (ProductModel item : parentFilteredList)
                    {
                        if (item.getProductPrice() != null) {
                            int productAmountInteger = Integer.parseInt(item.getProductPrice());

                            if (productAmountInteger >= amountStartInteger && productAmountInteger <= amountEndInteger)
                            {
                                filteredListResult.add(item);
                            }
//                            else if (productAmountInteger >=amountStartInteger && amountEndInteger==500000000)
//                            {
//                                filteredListResult.add(item);
//                            }
                            else if (productAmountInteger <= amountEndInteger && amountStartInteger==0)
                            {
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
                            }
                            else if (productAmountInteger <= amountEndInteger && amountStartInteger==0)
                            {
                                filteredListResult.add(item);
                            }
                        }
                    }
                    filteredListResult = new ArrayList<>();
                    filteredListResult = tempList;
                }
            }




            if (stringProductStartDate != null || stringProductEndDate != null)
            {
                if (firstField == 0)
                {
                    for (ProductModel item : parentFilteredList)
                    {
                        if (item.getInsertDateTime() != null)
                        {
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
                            if (flag)
                            {
                                tempList.add(item);
                            }
                        }
                    }
                    filteredListResult = new ArrayList<>();
                    filteredListResult = tempList;
                }
            }



        } else {
            Toast.makeText(getActivity(), "گزینه ای برای جستجو وجود ندارد", Toast.LENGTH_SHORT).show();
        }

        if (filteredListResult.size() == 0) {
            recordNotFoundDialog();
        } else adapter.filterList(filteredListResult);

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
        btnAddProduct.setVisibility(View.GONE);
        btnSearch.setVisibility(View.GONE);

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

}

