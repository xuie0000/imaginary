package xuk.imaginary.gui.settings

import android.content.SharedPreferences
import android.preference.*


/**
 * A simple [Fragment] subclass.
 * https://developer.android.com/guide/topics/ui/settings.html
 *
 * @author Jie Xu
 */
class SettingsFragment : PreferenceFragment(), SharedPreferences.OnSharedPreferenceChangeListener {

  override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {
    val preference = findPreference(key)
    if (preference is ListPreference) {
      val prefIndex = preference.findIndexOfValue(sharedPreferences.getString(key, ""))
      if (prefIndex >= 0) {
        preference.setSummary(preference.entries[prefIndex])
      }
    } else if (preference is EditTextPreference) {
      preference.setSummary(sharedPreferences.getString(key, ""))
    }

  }

  //    @Override
  //    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
  //        addPreferencesFromResource(R.xml.fragment_settings);
  //        bindPreferenceSummaryToValue(findPreference(KEY_LIST_PRE));
  //        bindPreferenceSummaryToValue(findPreference(KEY_PET_PRE));
  //        bindPreferenceSummaryToValue(findPreference(KEY_CACHED_PRE));
  //    }

  override fun onResume() {
    super.onResume()
    //        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
  }

  override fun onPause() {
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
    private val sBindPreferenceSummaryToValueListener = Preference.OnPreferenceChangeListener { preference, value ->
      val stringValue = value.toString()

      if (preference is ListPreference) {
        // For list preferences, look up the correct display value in
        // the preference's 'entries' list.
        val index = preference.findIndexOfValue(stringValue)

        // Set the summary to reflect the new value.
        preference.setSummary(
            if (index >= 0)
              preference.entries[index]
            else
              null)
      } else {
        // For all other preferences, set the summary to the value's
        // simple string representation.
        preference.summary = stringValue
      }
      true
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
      preference.onPreferenceChangeListener = sBindPreferenceSummaryToValueListener

      // Trigger the listener immediately with the preference's
      // current value.

      if (preference is CheckBoxPreference) {
        preference.isChecked = PreferenceManager.getDefaultSharedPreferences(
            preference.getContext()).getBoolean(preference.getKey(), false)
        return
      }

      sBindPreferenceSummaryToValueListener.onPreferenceChange(preference,
          PreferenceManager
              .getDefaultSharedPreferences(preference.context)
              .getString(preference.key, ""))
    }

    val KEY_LIST_PRE = "list_preference"
    val KEY_PET_PRE = "edit_text_preference"
    val KEY_CACHED_PRE = "checkbox_preference"
  }

}
