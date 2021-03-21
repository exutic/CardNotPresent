package CardNotPresent.permission;

import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public abstract class RuntimePermissionsActivity extends AppCompatActivity
{
    int sendResult;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public int getSendResult()
    {
        return sendResult;
    }

    public void setSendResult(int sendResult)
    {
        this.sendResult = sendResult;
    }

    @Override

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)

    {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        int permissionCheck = PackageManager.PERMISSION_GRANTED;

        for (int permission : grantResults)

        {
            permissionCheck = permissionCheck + permission;

        }

        if ((grantResults.length > 0) && permissionCheck == PackageManager.PERMISSION_GRANTED)

        {

            onPermissionsGranted(requestCode);
            setSendResult(10);

        }

        else

        {

            onPermissionsDeny(requestCode);

        }

    }


    public void requestAppPermissions(final String[] requestedPermissions, final int requestCode)

    {

        int permissionCheck = PackageManager.PERMISSION_GRANTED;

        boolean shouldShowRequestPermissionRationale = false;

        for (String permission : requestedPermissions)

        {

            permissionCheck = permissionCheck + ContextCompat.
                    checkSelfPermission(this, permission);

            shouldShowRequestPermissionRationale = shouldShowRequestPermissionRationale || ActivityCompat
                    .shouldShowRequestPermissionRationale(this, permission);

        }

        if (permissionCheck != PackageManager.PERMISSION_GRANTED)

        {

            ActivityCompat.requestPermissions(this, requestedPermissions, requestCode);

        }

        else

        {

            onPermissionsGranted(requestCode);

        }

    }


    public abstract void onPermissionsGranted(int requestCode);

    public abstract void onPermissionsDeny(int requestCode);


}