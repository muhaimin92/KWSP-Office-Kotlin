package epf.com.office.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.annotation.StringDef
import epf.com.office.Constant.Companion.SORT_BY_NAME

object AppPreferences {

    private const val NAME = "SpinKotlin"
    private const val MODE = Context.MODE_PRIVATE
    private lateinit var preferences: SharedPreferences

    const val SORT_BY = "sort_by"
    const val FILTER_BY = "filter_by"
    const val IS_DATA_FETCH_FROM_API = "fetch_api"

    fun init(context: Context) {
        preferences = context.getSharedPreferences(NAME, MODE)
        if(sortBy.equals("")){
            sortBy = SORT_BY_NAME
        }
        if(filterBy.equals("")){
            filterBy = "00"
        }
    }

    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = edit()
        operation(editor)
        editor.apply()
    }

    var sortBy
        get() = preferences.getString(SORT_BY, "")
        set(value) = preferences.edit {
            it.putString(SORT_BY, value)
        }

    var filterBy
        get() = preferences.getString(FILTER_BY, "")
        set(value) = preferences.edit {
            it.putString(FILTER_BY, value)
        }

    var isDataFecthFromApi:Boolean
        get() = preferences.getBoolean(IS_DATA_FETCH_FROM_API, false)
        set(value) = preferences.edit {
            it.putBoolean(IS_DATA_FETCH_FROM_API, value)
        }

}