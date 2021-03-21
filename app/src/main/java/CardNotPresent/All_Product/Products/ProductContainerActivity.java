package CardNotPresent.All_Product.Products;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.os.Bundle;
import android.widget.FrameLayout;

import CardNotPresent.R;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class ProductContainerActivity extends AppCompatActivity {
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
        setContentView(R.layout.activity_product_container);



        FrameLayout frameLayout = findViewById(R.id.frame_product_container_layout);

        Product_Payment_linkFragment fragment = new Product_Payment_linkFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_product_container_layout, fragment);
        transaction.commit();
    }
}
