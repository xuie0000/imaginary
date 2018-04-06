package com.xuie.imaginaryandroid.gui.settings


import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.app.Fragment
import android.support.v7.preference.CheckBoxPreference
import android.support.v7.preference.EditTextPreference
import android.support.v7.preference.ListPreference
import android.support.v7.preference.Preference
import android.support.v7.preference.PreferenceFragmentCompat

import com.xuie.imaginaryandroid.R

/**
 * A simple [Fragment] subclass.
 * https://developer.android.com/guide/topics/ui/settings.html
 */
class SettingsFragment : PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener {

    @Override
    fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {
        val preference = findPreference(key)
        if (preference is ListPreference) {
            val listPreference = preference as ListPreference
            val prefIndex = listPreference.findIndexOfValue(sharedPreferences.getString(key, ""))
            if (prefIndex >= 0) {
                preference.setSummary(listPreference.getEntries()[prefIndex])
            }
        } else if (preference is EditTextPreference) {
            preference.setSummary(sharedPreferences.getString(key, ""))
        }

    }

    @Override
    fun onCreatePreferences(savedInstanceState: Bundle, rootKey: String) {
        addPreferencesFromResource(R.xml.fragment_settings)
        bindPreferenceSummaryToValue(findPreference(KEY_LIST_PRE))
        bindPreferenceSummaryToValue(findPreference(KEY_PET_PRE))
        bindPreferenceSummaryToValue(findPreference(KEY_CACHED_PRE))
    }

    @Override
    fun onResume() {
        super.onResume()
        //        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    fun onPause() {
        super.onPause()
        //        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

    companion object {
        private val TAG = "SettingsFragment"

        val instance: SettingsFragment
            get() = SettingsFragment()


        /**
         * A preference value change listener that updates the preference's summary
         * to reflect its new value.
         */
        private val sBindPreferenceSummaryToValueListener = object : Preference.OnPreferenceChangeListener() {
            @Override
            fun onPreferenceChange(preference: Preference, value: Object): Boolean {
                val stringValue = value.toString()

                if (preference is ListPreference) {
                    // For list preferences, look up the correct display value in
                    // the preference's 'entries' list.
                    val listPreference = preference as ListPreference
                    val index = listPreference.findIndexOfValue(stringValue)

                    // Set the summary to reflect the new value.
                    preference.setSummary(
                            if (index >= 0)
                                listPreference.getEntries()[index]
                            else
                                null)
                } else {
                    // For all other preferences, set the summary to the value's
                    // simple string representation.
                    preference.setSummary(stringValue)
                }
                return true
            }
        }

        /**
         * Binds a preference's summary to its value. More specifically, when the
         * preference's value is changed, its summary (line of text below the
         * preference title) is updated to reflect the value. The summary is also
         * immediately updated upon calling this method. The exact display format is
         * dependent on the type of preference.
         *
         * @see .sBindPreferenceSummaryToValueListener
         */
        private fun bindPreferenceSummaryToValue(preference: Preference) {
            // Set the listener to watch for value changes.
            preference.setOnPreferenceChangeListener(sBindPreferenceSummaryToValueListener)

            // Trigger the listener immediately with the preference's
            // current value.

            if (preference is CheckBoxPreference) {
                (preference as CheckBoxPreference).setChecked(
                        PreferenceManager.getDefaultSharedPreferences(
                                preference.getContext()).getBoolean(preference.getKey(), false))
                return
            }

            sBindPreferenceSummaryToValueListener.onPreferenceChange(preference,
                    PreferenceManager
                            .getDefaultSharedPreferences(preference.getContext())
                            .getString(preference.getKey(), ""))
        }

        val KEY_LIST_PRE = "list_preference"
        val KEY_PET_PRE = "edit_text_preference"
        val KEY_CACHED_PRE = "checkbox_preference"
    }

}
