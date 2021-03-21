package CardNotPresent.Home;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import CardNotPresent.R;
import com.google.android.material.navigation.NavigationView;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class HomePanel extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    public static int changePasswordFinishPage = 0;
    public static int ON_BACK_CLICK_FOR_EXIT_0_1 = 0;


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
        setContentView(R.layout.activity_home_panel);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.actv_home_panel_nav_item_home,
                R.id.actv_home_panel_nav_item_profile,
                R.id.actv_home_panel_nav_item_change_password_link,
                R.id.actv_home_panel_nav_item_contact_us_link,
                R.id.actv_home_panel_nav_item_about_us_link,
                R.id.actv_home_panel_nav_item_exitApp)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


        MenuItem logOutItem = navigationView.getMenu().findItem(R.id.actv_home_panel_nav_item_exitApp);
        logOutItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener()
        {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                exitDialog();
                return true;
            }
        });
    }

    public void exitDialog() {

        View view = getLayoutInflater().inflate(R.layout.alert_delete, null);
        Button btnSubmit, btnClose;
        btnSubmit = view.findViewById(R.id.alert_delete_btnSubmit);
        btnClose = view.findViewById(R.id.alert_delete_btnClose);

        AlertDialog.Builder builder = new AlertDialog.Builder(HomePanel.this);
        builder.setCancelable(false);
        builder.setView(view);

        final AlertDialog dialogg = builder.create();
        dialogg.show();


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                finish();
                dialogg.dismiss();
            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogg.cancel();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_panel, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        int id = item.getItemId();
        if (id == R.id.menu_goto_website) {
            Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse("https://totangroup.com/"));
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
