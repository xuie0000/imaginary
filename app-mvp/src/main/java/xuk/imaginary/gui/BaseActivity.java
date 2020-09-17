package xuk.imaginary.gui;

import android.graphics.Color;
import android.os.Build;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

/**
 * @author xuie
 * @date 2018/8/9
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onResume() {
        super.onResume();
        View decorView = getWindow().getDecorView();
        int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        decorView.setSystemUiVisibility(option);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
//        getWindow().setStatusBarColor(getColor(R.color.colorPrimary))
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            );
        }
    }
}
