package CardNotPresent.Home.ui.ChangePin;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import CardNotPresent.Home.HomePanel;
import CardNotPresent.R;
import CardNotPresent.Settings.Settings_Activity;

public class ChangePasswordFragment extends Fragment
{

    Button btnChangePIN;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        ChangePasswordViewModel changePasswordViewModel =
                ViewModelProviders.of(this).get(ChangePasswordViewModel.class);
        View root = inflater.inflate(R.layout.fragment_change_password, container, false);
        changePasswordViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });
        btnChangePIN = root.findViewById(R.id.frag_change_pin_btn_change_pin);
        btnChangePIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),Settings_Activity.class));
            }
        });
        HomePanel.ON_BACK_CLICK_FOR_EXIT_0_1 = 0;
        return root;
    }
}
