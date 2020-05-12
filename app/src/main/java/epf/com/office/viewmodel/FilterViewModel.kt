package epf.com.office.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import epf.com.office.Constant
import epf.com.office.model.StateCode
import epf.com.office.utils.AppPreferences
import epf.com.office.utils.Utils

class FilterViewModel(application: Application) : AndroidViewModel(application)  {

    val stateCode = MutableLiveData<List<StateCode>>()

    fun getCurrentSort(): String {
        return AppPreferences.sortBy!!
    }

    fun setSort(string: String) {
        AppPreferences.sortBy = string
    }

    fun getState() {
        var data = Utils().getJsonDataFromAsset(getApplication(), Constant.STATE_CODE_JSON_NAME)
        val stateType = object : TypeToken<List<StateCode>>() {}.type
        var list = Gson().fromJson<List<StateCode>>(data, stateType)
        for ((index, value) in list.withIndex()) {
            list[index].isSelected = list[index].key.equals(AppPreferences.filterBy)
        }
        stateCode.value = list

    }

    fun updateSelectedState(key: String) {
        var data = stateCode.value
        if (data != null) {
            for ((index, value) in data.withIndex()) {
                data[index].isSelected = data[index].key.equals(key)
            }

            stateCode.value = data
        }
        AppPreferences.filterBy = key
    }


}