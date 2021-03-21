package CardNotPresent.All_Persons.PersonStatusList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.os.Bundle;
import android.widget.FrameLayout;

import CardNotPresent.R;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class PersonConditionContainerActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_person_condition_container);


        FrameLayout frameLayout = findViewById(R.id.frame_person_condition_container_layout);

        Persons_Transaction_ConditionFragment fragment = new Persons_Transaction_ConditionFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_person_condition_container_layout, fragment);
        transaction.commit();

    }
}
