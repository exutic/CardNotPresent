package CardNotPresent.Home.ui.ContactUs;

import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import CardNotPresent.Home.HomePanel;
import CardNotPresent.R;

public class ContactUsFragment extends Fragment {

    private ContactUsViewModel mViewModel;

    public static ContactUsFragment newInstance() {
        return new ContactUsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        HomePanel.ON_BACK_CLICK_FOR_EXIT_0_1 = 0;

        View view = inflater.inflate(R.layout.fragment_contact_us, container, false);


        CardView cardViewIran;

        cardViewIran = view.findViewById(R.id.card_view_google_map_iran);
        cardViewIran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse("https://www.google.com/maps/place/Totan+Group/@35.713514,51.410075,16z/data=!4m5!3m4!1s0x0:0xfa682c948e1b314b!8m2!3d35.713514!4d51.4100748?hl=en"));
                startActivity(intent);
            }
        });





        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ContactUsViewModel.class);
        // TODO: Use the ViewModel
    }

}
