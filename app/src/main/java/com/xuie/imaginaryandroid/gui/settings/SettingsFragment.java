package com.xuie.imaginaryandroid.gui.settings;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.preference.EditTextPreference;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;

import com.xuie.imaginaryandroid.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends PreferenceFragmentCompat
        implements SharedPreferences.OnSharedPreferenceChangeListener {
    private static final String TAG = "SettingsFragment";

    public static SettingsFragment getInstance() {
        return new SettingsFragment();
    }

    public static final String KEY_LIST_PRE = "list_preference";

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Preference preference = findPreference(key);
        if (preference instanceof ListPreference) {
            ListPreference listPreference = (ListPreference) preference;
            int prefIndex = listPreference.findIndexOfValue(sharedPreferences.getString(key, ""));
            if (prefIndex >= 0) {
                preference.setSummary(listPreference.getEntries()[prefIndex]);
            }
        } else if (preference instanceof EditTextPreference) {
            preference.setSummary(sharedPreferences.getString(key, ""));
        }

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.fragment_settings);
        // 选项监听 ...
        // findPreference("child_checkbox_preference").setOnPreferenceClickListener(...);
        // 选项监听 当 Preference 的值发生改变时触发该事件，true则以新值更新控件的状态，false 则 不保存
        findPreference("child_checkbox_preference").setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                // 返回 false 修改不会生效
                return false;
            }
        });
        // 获取 Preferences Manager
//        PreferenceManager manager = getPreferenceManager();
        // 获取 选项状态
//        CheckBoxPreference child_checkbox_preference = (CheckBoxPreference) manager.findPreference("child_checkbox_preference");
        // ...
//        Toast.makeText(getActivity(), String.valueOf(child_checkbox_preference.isChecked()), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

}
