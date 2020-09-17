package xuk.imaginary.gui.gank.show;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;

import xuk.imaginary.R;
import xuk.imaginary.ViewModelFactory;
import xuk.imaginary.base.BaseActivity;
import xuk.imaginary.databinding.ActivityGankBinding;

/**
 * @author xuie
 */
public class GankActivity extends BaseActivity {
    private static final String TAG = "GankActivity";

    private ExpandableItemAdapter adapter = new ExpandableItemAdapter(new ArrayList<>());

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityGankBinding mBinding = DataBindingUtil.setContentView(this, R.layout.activity_gank);

        String date = getIntent().getStringExtra("date");
        String imageUrl = getIntent().getStringExtra("image");

        Log.d(TAG, "onCreate: data - " + date + ", image - " + imageUrl);

        GankViewModule gankViewModule = obtainViewModel(this);
        gankViewModule.dateString.set(date);
        gankViewModule.imageUrl.set(imageUrl);
        mBinding.setViewmodule(gankViewModule);

        mBinding.recyclerView.setAdapter(adapter);
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mBinding.recyclerView.setNestedScrollingEnabled(false);
        adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);

        gankViewModule.getGank(date);
    }

    public static GankViewModule obtainViewModel(FragmentActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        return ViewModelProviders.of(activity, factory).get(GankViewModule.class);
    }
}
