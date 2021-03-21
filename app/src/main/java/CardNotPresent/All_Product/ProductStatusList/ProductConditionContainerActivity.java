package CardNotPresent.All_Product.ProductStatusList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.os.Bundle;
import android.widget.FrameLayout;

import CardNotPresent.R;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class ProductConditionContainerActivity extends AppCompatActivity {
    @Override
    protected void attachBaseContext(Context newBase) {
        try {
            super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ProductConditionContainerActivity toastObject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_condition_conteiner);
        toastObject = new ProductConditionContainerActivity();

        FrameLayout frameLayout = findViewById(R.id.frame_product_condition_container_layout);

        Product_Transaction_ConditionFragment fragment = new Product_Transaction_ConditionFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_product_condition_container_layout, fragment);
        transaction.commit();
    }
}
