package CardNotPresent.Home.ui.Home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import CardNotPresent.All_AbsentPayment.AbsentPaymentLinkList.AbsentLinkListActivity;
import CardNotPresent.All_AbsentPayment.AbsentPaymentTransaction.LinkContainerActivity;
import CardNotPresent.All_AbsentPayment.AbsentPaymentStatus.AbsentLinkStatusActivity;
import CardNotPresent.All_Persons.PersonStatusList.PersonConditionContainerActivity;
import CardNotPresent.All_Persons.Person_link.PersonLinkActivity;
import CardNotPresent.All_Persons.Persons.PersonContainerActivity;
import CardNotPresent.All_Persons.PersonsTransaction.PersonTransactionContainerActivity;
import CardNotPresent.All_Product.ProductStatusList.ProductConditionContainerActivity;
import CardNotPresent.All_Product.ProductTransaction.ProductTransactionContainerActivity;
import CardNotPresent.All_Product.Product_link.ProductLinkActivity;
import CardNotPresent.All_Product.Products.ProductContainerActivity;
import CardNotPresent.R;

import static CardNotPresent.Home.HomePanel.ON_BACK_CLICK_FOR_EXIT_0_1;

public class HomeFragment extends Fragment {


    public static int PERSON_CONDITION_METHOD = 0;
    public static int PRODUCT_CONDITION_METHOD = 0;
    public static int PAYMENT_CONDITION_METHOD = 0;

    private LinearLayout linearLayout1;
    private LinearLayout linearLayout2;
    private View root;

    private LinearLayout ll_Inner1;
    private LinearLayout ll_Inner2;
    private LinearLayout ll_Inner3;
    private LinearLayout ll_Inner4;
    private LinearLayout ll_Inner5;
    private LinearLayout ll_Inner6;

    private FrameLayout frameLayoutHome2;
    private FrameLayout frameLayoutHome3;
    private FrameLayout frameLayoutHome4;
    private FrameLayout frameLayoutHome5;
    private FrameLayout frameLayoutHome6;

    private FrameLayout btnBack_4_to_3;
    private FrameLayout btnBack_5_to_4;
    private FrameLayout btnBack_6_to_4;


    private CardView cardView2_1, cardView2_2, cardView2_3,
            cardView2_4, cardView2_5, cardView2_6,
            cardView2_7, cardView2_8, cardView2_9,
            cardView2_10, cardView2_11;
    private CardView cardView3_1, cardView3_2, cardView3_3, cardView3_4, cardView3_5;

    private CardView cardView4_1, cardView4_2, cardView4_3, cardView4_4, cardView4_5;

    private CardView cardView5_4, cardView5_7, cardView5_5, cardView5_8, cardView5_10;
    private CardView cardView5_6, cardView5_1, cardView5_2, cardView5_9, cardView5_3;
    private CardView cardView5_11, cardView5_12;

    private CardView cardView6_1, cardView6_2, cardView6_3;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        root = inflater.inflate(R.layout.fragment_home, container, false);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });

        findViews();
        setClicks();


        ON_BACK_CLICK_FOR_EXIT_0_1 = 1;
        return root;
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        OnBackPressedCallback callback = new OnBackPressedCallback(
                true // default to enabled
        ) {
            @Override
            public void handleOnBackPressed() {

                switch (ON_BACK_CLICK_FOR_EXIT_0_1) {
                    case 0:
//                        Toast.makeText(getActivity(), "Count: "+ON_BACK_CLICK_FOR_EXIT_0_1, Toast.LENGTH_SHORT).show();
                        requireActivity().onBackPressed();
                        break;
                    case 1:
//                        Toast.makeText(getActivity(), "Count: "+ON_BACK_CLICK_FOR_EXIT_0_1, Toast.LENGTH_SHORT).show();
                        exitDialog();
                        break;
                    case 2:
//                        Toast.makeText(getActivity(), "Count: "+ON_BACK_CLICK_FOR_EXIT_0_1, Toast.LENGTH_SHORT).show();
                        methodBackCall_2_to_1();
                        break;
                    case 3:
//                        Toast.makeText(getActivity(), "Count: "+ON_BACK_CLICK_FOR_EXIT_0_1, Toast.LENGTH_SHORT).show();
                        methodBackCall_3_to_1();
                        break;
                    case 4:
//                        Toast.makeText(getActivity(), "Count: "+ON_BACK_CLICK_FOR_EXIT_0_1, Toast.LENGTH_SHORT).show();
                        methodBackCall_4_to_3();
                        break;
                    case 5:
//                        Toast.makeText(getActivity(), "Count: "+ON_BACK_CLICK_FOR_EXIT_0_1, Toast.LENGTH_SHORT).show();
                        methodBackCall_5_to_4();
                        methodBackCall_6_to_4();
                        break;
                }
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(
                this, // LifecycleOwner
                callback);
    }

    void findViews() {
        linearLayout1 = root.findViewById(R.id.frag_home_outer_layout_1);//parent layout  ثبت تقاضای پذیرندگی
        linearLayout2 = root.findViewById(R.id.frag_home_outer_layout_1_2);//parent layout  خدمات پذیرندگان


        ll_Inner1 = root.findViewById(R.id.frag_home_inner_1);
        ll_Inner2 = root.findViewById(R.id.frag_home_inner_2);
        ll_Inner3 = root.findViewById(R.id.frag_home_inner_3);
        ll_Inner4 = root.findViewById(R.id.frag_home_inner_4);
        ll_Inner5 = root.findViewById(R.id.frag_home_inner_5);
        ll_Inner6 = root.findViewById(R.id.frag_home_inner_6);


        frameLayoutHome2 = root.findViewById(R.id.frag_home_home_btn_2);
        frameLayoutHome3 = root.findViewById(R.id.frag_home_home_btn_3);
        frameLayoutHome4 = root.findViewById(R.id.frag_home_home_btn_4);
        frameLayoutHome5 = root.findViewById(R.id.frag_home_home_btn_5);
        frameLayoutHome6 = root.findViewById(R.id.frag_home_home_btn_6);

        btnBack_4_to_3 = root.findViewById(R.id.f_layout_back_4_to_3);
        btnBack_5_to_4 = root.findViewById(R.id.f_layout_back_5_to_4);
        btnBack_6_to_4 = root.findViewById(R.id.f_layout_back_6_to_4);

//        //Inner layout 2
//        cardView3_1 = root.findViewById(R.id.card_view_1);//لینک مبلغ معین
//        cardView3_2 = root.findViewById(R.id.card_view_2);//لینک پرداخت
//        cardView3_3 = root.findViewById(R.id.card_view_3);//گزارش تراکنش ها
//        cardView3_4 = root.findViewById(R.id.card_view_4);//لینک محصول
//        cardView3_5 = root.findViewById(R.id.card_view_5);//گزارش لینک ها

        //Inner layout 3
        cardView3_1 = root.findViewById(R.id.card_view_3_1);//لینک مبلغ معین
        cardView3_2 = root.findViewById(R.id.card_view_3_2);//لینک پرداخت
        cardView3_3 = root.findViewById(R.id.card_view_3_3);//گزارش تراکنش ها
        cardView3_4 = root.findViewById(R.id.card_view_3_4);//لینک محصول
        cardView3_5 = root.findViewById(R.id.card_view_3_5);//گزارش لینک ها

        //Inner layout 4
        cardView4_1 = root.findViewById(R.id.card_view_4_1);//لینک مبلغ معین
        cardView4_2 = root.findViewById(R.id.card_view_4_2);//گزارش تراکنش ها
        cardView4_3 = root.findViewById(R.id.card_view_4_3);//لینک محصول
        cardView4_4 = root.findViewById(R.id.card_view_4_4);//گزارش لینک ها
        cardView4_5 = root.findViewById(R.id.card_view_4_5);//مشاهده لینک ها

        //Inner layout 5
        cardView5_1 = root.findViewById(R.id.card_view_5_1);//تراکنش های کل
        cardView5_2 = root.findViewById(R.id.card_view_5_2);//تراکنش های کل
        cardView5_3 = root.findViewById(R.id.card_view_5_3);//تراکنش های کل

        cardView5_4 = root.findViewById(R.id.card_view_5_4);//تراکنش های اشخاص
        cardView5_5 = root.findViewById(R.id.card_view_5_5);//تراکنش های اشخاص
        cardView5_6 = root.findViewById(R.id.card_view_5_6);//تراکنش های اشخاص

        cardView5_7 = root.findViewById(R.id.card_view_5_7);//تراکنش های محصولات
        cardView5_8 = root.findViewById(R.id.card_view_5_8);//تراکنش های محصولات
        cardView5_9 = root.findViewById(R.id.card_view_5_9);//تراکنش های محصولات

        cardView5_10 = root.findViewById(R.id.card_view_5_10);//تراکنش های پرداخت
        cardView5_11 = root.findViewById(R.id.card_view_5_11);//تراکنش های پرداخت
        cardView5_12 = root.findViewById(R.id.card_view_5_12);//تراکنش های پرداخت

//        cardView5_6 = root.findViewById(R.id.card_view_5_6);//وضعیت تسویه تراکنش های محصولات
//        cardView5_7 = root.findViewById(R.id.card_view_5_7);//وضعیت تسویه تراکنش های محصولات
//        cardView5_8 = root.findViewById(R.id.card_view_5_8);//وضعیت تسویه تراکنش های محصولات
//        cardView5_9 = root.findViewById(R.id.card_view_5_9);//وضعیت تسویه تراکنش های محصولات
//        cardView5_10 = root.findViewById(R.id.card_view_5_10);//وضعیت تسویه تراکنش های محصولات
//        cardView5_11 = root.findViewById(R.id.card_view_5_11);//وضعیت تسویه تراکنش های محصولات
//        cardView5_12 = root.findViewById(R.id.card_view_5_12);//وضعیت تسویه تراکنش های محصولات

        //Inner layout 6
        cardView6_1 = root.findViewById(R.id.card_view_6_1);//لینک های اشخاص
        cardView6_2 = root.findViewById(R.id.card_view_6_2);//لینک های محصولات
        cardView6_3 = root.findViewById(R.id.card_view_6_3);//لینک پرداخت


        methodHomeCall();
    }

    void setClicks() {

        //1st inner buttons
        linearLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                methodGo_1_to_2();
                Toast.makeText(requireActivity(), "این سرویس فعال نمی باشد", Toast.LENGTH_SHORT).show();

            }
        });

        linearLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                methodGo_1_to_3();
//                خدمات پذیرندگان
            }
        });

        //2st inner buttons

        //3st inner buttons
        cardView3_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                methodGo_3_to_4();
            }
        });

        //4st inner buttons
        cardView4_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), PersonContainerActivity.class));
            }
        });

        cardView4_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                methodGo_4_to_5();
            }
        });

        cardView4_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ProductContainerActivity.class));
            }
        });

        cardView4_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                methodGo_4_to_6();
            }
        });

        cardView4_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(requireActivity(), AbsentLinkListActivity.class));
            }
        });


        //5th inner buttons
        cardView5_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), PersonTransactionContainerActivity.class));
            }
        });
        cardView5_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PERSON_CONDITION_METHOD = 1;
                startActivity(new Intent(getActivity(), PersonConditionContainerActivity.class));
            }
        });

        cardView5_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PERSON_CONDITION_METHOD = 2;
                startActivity(new Intent(getActivity(), PersonConditionContainerActivity.class));
            }
        });

        cardView5_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ProductTransactionContainerActivity.class));
            }
        });


        cardView5_8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PRODUCT_CONDITION_METHOD = 1;
                startActivity(new Intent(getActivity(), ProductConditionContainerActivity.class));
            }
        });

        cardView5_9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PRODUCT_CONDITION_METHOD = 2;
                startActivity(new Intent(getActivity(), ProductConditionContainerActivity.class));
            }
        });


        cardView5_10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PAYMENT_CONDITION_METHOD = 1;
                startActivity(new Intent(getActivity(), AbsentLinkStatusActivity.class));
            }
        });

        cardView5_11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PAYMENT_CONDITION_METHOD = 2;
                startActivity(new Intent(getActivity(), AbsentLinkStatusActivity.class));
            }
        });

        cardView5_12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), LinkContainerActivity.class));
            }
        });


        //6th inner buttons
        cardView6_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), PersonLinkActivity.class));
            }
        });

        cardView6_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ProductLinkActivity.class));
            }
        });

        cardView6_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));
//                builder.setMessage("بزودی این سرویس ارائه خواهد شد");
//
//                final AlertDialog dialog = builder.create();
//                dialog.show();
//
//                builder.setPositiveButton("باشه", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        dialog.dismiss();
//                    }
//                });
//                Toast.makeText(getActivity(), "بزودی این سرویس ارایه خواهد شد", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getActivity(), LinkContainerActivity.class));
            }
        });


        //back buttons
        btnBack_4_to_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                methodBackCall_4_to_3();
            }
        });
        btnBack_5_to_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                methodBackCall_5_to_4();
            }
        });
        btnBack_6_to_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                methodBackCall_6_to_4();
            }
        });

    }

    public void methodGo_1_to_2() {
        ll_Inner1.setVisibility(View.GONE);
        ll_Inner2.setVisibility(View.VISIBLE);
        ll_Inner3.setVisibility(View.GONE);
        ll_Inner4.setVisibility(View.GONE);
        ll_Inner5.setVisibility(View.GONE);
        ll_Inner6.setVisibility(View.GONE);
        ON_BACK_CLICK_FOR_EXIT_0_1 = 2;
    }

    public void methodGo_1_to_3() {
        ll_Inner1.setVisibility(View.GONE);
        ll_Inner2.setVisibility(View.GONE);
        ll_Inner3.setVisibility(View.VISIBLE);
        ll_Inner4.setVisibility(View.GONE);
        ll_Inner5.setVisibility(View.GONE);
        ll_Inner6.setVisibility(View.GONE);
        ON_BACK_CLICK_FOR_EXIT_0_1 = 3;
    }

    public void methodGo_3_to_4() {
        ll_Inner1.setVisibility(View.GONE);
        ll_Inner2.setVisibility(View.GONE);
        ll_Inner3.setVisibility(View.GONE);
        ll_Inner4.setVisibility(View.VISIBLE);
        ll_Inner5.setVisibility(View.GONE);
        ll_Inner6.setVisibility(View.GONE);
        ON_BACK_CLICK_FOR_EXIT_0_1 = 4;
    }

    public void methodGo_4_to_5() {
        ll_Inner1.setVisibility(View.GONE);
        ll_Inner2.setVisibility(View.GONE);
        ll_Inner3.setVisibility(View.GONE);
        ll_Inner4.setVisibility(View.GONE);
        ll_Inner5.setVisibility(View.VISIBLE);
        ll_Inner6.setVisibility(View.GONE);
        ON_BACK_CLICK_FOR_EXIT_0_1 = 5;
    }

    public void methodGo_4_to_6() {
        ll_Inner1.setVisibility(View.GONE);
        ll_Inner2.setVisibility(View.GONE);
        ll_Inner3.setVisibility(View.GONE);
        ll_Inner4.setVisibility(View.GONE);
        ll_Inner5.setVisibility(View.GONE);
        ll_Inner6.setVisibility(View.VISIBLE);
        ON_BACK_CLICK_FOR_EXIT_0_1 = 5;
    }

    public void methodBack_All_to_1() {
        ll_Inner1.setVisibility(View.VISIBLE);
        ll_Inner2.setVisibility(View.GONE);
        ll_Inner3.setVisibility(View.GONE);
        ll_Inner4.setVisibility(View.GONE);
        ll_Inner5.setVisibility(View.GONE);
        ll_Inner6.setVisibility(View.GONE);
        ON_BACK_CLICK_FOR_EXIT_0_1 = 1;
    }

    public void methodHomeCall() {
        frameLayoutHome2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                methodBack_All_to_1();
            }
        });
        frameLayoutHome3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                methodBack_All_to_1();
            }
        });
        frameLayoutHome4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                methodBack_All_to_1();
            }
        });
        frameLayoutHome5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                methodBack_All_to_1();
            }
        });
        frameLayoutHome6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                methodBack_All_to_1();
            }
        });
    }

    public void methodBackCall_2_to_1() {
        ll_Inner1.setVisibility(View.VISIBLE);
        ll_Inner2.setVisibility(View.GONE);
        ll_Inner3.setVisibility(View.GONE);
        ll_Inner4.setVisibility(View.GONE);
        ll_Inner5.setVisibility(View.GONE);
        ll_Inner6.setVisibility(View.GONE);
        ON_BACK_CLICK_FOR_EXIT_0_1 = 1;
    }

    public void methodBackCall_3_to_1() {
        ll_Inner1.setVisibility(View.VISIBLE);
        ll_Inner2.setVisibility(View.GONE);
        ll_Inner3.setVisibility(View.GONE);
        ll_Inner4.setVisibility(View.GONE);
        ll_Inner5.setVisibility(View.GONE);
        ll_Inner6.setVisibility(View.GONE);
        ON_BACK_CLICK_FOR_EXIT_0_1 = 1;
    }

    public void methodBackCall_4_to_3() {
        ll_Inner1.setVisibility(View.GONE);
        ll_Inner2.setVisibility(View.GONE);
        ll_Inner3.setVisibility(View.VISIBLE);
        ll_Inner4.setVisibility(View.GONE);
        ll_Inner5.setVisibility(View.GONE);
        ll_Inner6.setVisibility(View.GONE);
        ON_BACK_CLICK_FOR_EXIT_0_1 = 3;
    }

    public void methodBackCall_5_to_4() {

        ll_Inner1.setVisibility(View.GONE);
        ll_Inner2.setVisibility(View.GONE);
        ll_Inner3.setVisibility(View.GONE);
        ll_Inner4.setVisibility(View.VISIBLE);
        ll_Inner5.setVisibility(View.GONE);
        ll_Inner6.setVisibility(View.GONE);
        ON_BACK_CLICK_FOR_EXIT_0_1 = 4;

    }

    public void methodBackCall_6_to_4() {
        ll_Inner1.setVisibility(View.GONE);
        ll_Inner2.setVisibility(View.GONE);
        ll_Inner3.setVisibility(View.GONE);
        ll_Inner4.setVisibility(View.VISIBLE);
        ll_Inner5.setVisibility(View.GONE);
        ll_Inner6.setVisibility(View.GONE);
        ON_BACK_CLICK_FOR_EXIT_0_1 = 4;
    }

    public void exitDialog() {

        View view = getLayoutInflater().inflate(R.layout.alert_delete, null);
        Button btnSubmit, btnClose;
        btnSubmit = view.findViewById(R.id.alert_delete_btnSubmit);
        btnClose = view.findViewById(R.id.alert_delete_btnClose);

        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        builder.setCancelable(false);
        builder.setView(view);

        final AlertDialog dialog = builder.create();
        dialog.show();


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().finish();
                dialog.dismiss();
            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

//        builder.setPositiveButton("بله", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//            }
//        });
//        builder.setPositiveButtonIcon(getResources().getDrawable(R.drawable.icon_agree));
//        builder.setNegativeButtonIcon(getResources().getDrawable(R.drawable.icon_disagree));
//
//        builder.setNegativeButton("خیر", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.cancel();
//            }
//        });


    }
}
