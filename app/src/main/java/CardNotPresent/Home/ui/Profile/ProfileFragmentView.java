package CardNotPresent.Home.ui.Profile;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import CardNotPresent.Home.HomePanel;

import CardNotPresent.R;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.Objects;

public class ProfileFragmentView extends Fragment {

    View root;
    private ProfileViewModel profileViewModel;

    private TextView txtName, txtNationalID, txtMobileNumber, txtHomeNumber, txtEmailAddress, txtCompanyCode, txtHomeAddress;
    Button btn_refresh;
    CardView cardView_parent;
    AVLoadingIndicatorView progressBar;
    TextView textViewAlertWaiting;
    boolean online = false;
    LinearLayout linearLayout;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @SuppressLint("FragmentLiveDataObserve")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_profile, container, false);

        HomePanel.ON_BACK_CLICK_FOR_EXIT_0_1 = 0;

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

        return root;
    }

    public void connecting_True() {
        btn_refresh.setVisibility(View.GONE);
        cardView_parent.setVisibility(View.VISIBLE);


        profileViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);
        profileViewModel.setLinearLayout(linearLayout);
        profileViewModel.findLiveDatas();
        profileViewModel.getFullName().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                txtName.setText(s);
            }
        });

        profileViewModel.getNationalID().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                txtNationalID.setText(s);
            }
        });

        profileViewModel.getMobile().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                txtMobileNumber.setText(s);
            }
        });

        profileViewModel.getPhoneNumber().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                txtHomeNumber.setText(s);
            }
        });

        profileViewModel.getEmail().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                txtEmailAddress.setText(s);
            }
        });

        profileViewModel.getHomeAddress().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                txtHomeAddress.setText(s);
            }
        });


        profileViewModel.getCompanyName().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                txtCompanyCode.setText(s);
            }
        });

    }

    public void connection_False() {
        btn_refresh.setVisibility(View.VISIBLE);
        cardView_parent.setVisibility(View.GONE);
        Toast.makeText(getActivity(), "عدم اتصال به ابنترنت", Toast.LENGTH_SHORT).show();
    }

    public void findViews() {

        txtName = root.findViewById(R.id.frag_profile_txt_full_name_body);
        txtNationalID = root.findViewById(R.id.frag_profile_txt_national_id__body);
        txtMobileNumber = root.findViewById(R.id.frag_profile_txt_phone_number__body);
        txtHomeNumber = root.findViewById(R.id.frag_profile_txt_home_phone_body);
        txtEmailAddress = root.findViewById(R.id.frag_profile_txt_email_body);
        txtHomeAddress = root.findViewById(R.id.frag_profile_txt_address_body);
        txtCompanyCode = root.findViewById(R.id.frag_profile_txt_company_code_body);
        btn_refresh = root.findViewById(R.id.frag_profile_btn_refresh);
        cardView_parent = root.findViewById(R.id.frag_profile_cardView);
//        progressBar = root.findViewById(R.id.frag_progress_profile);
        linearLayout = root.findViewById(R.id.frag_profile_include__waiting_dialog);
    }

    public static boolean isOnline(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        return (netInfo != null && netInfo.isConnected());
    }
}