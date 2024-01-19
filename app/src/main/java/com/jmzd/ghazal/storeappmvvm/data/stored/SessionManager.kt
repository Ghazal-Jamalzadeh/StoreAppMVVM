package com.jmzd.ghazal.storeappmvvm.data.stored

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.jmzd.ghazal.storeappmvvm.utils.constants.SESSION_AUTH_DATA
import com.jmzd.ghazal.storeappmvvm.utils.constants.USER_TOKEN_DATA
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SessionManager @Inject constructor(@ApplicationContext private val context : Context){

    private val appContext = context.applicationContext

    companion object{
        /* دقت کنید روی کلاس کانتکس تعریف کنید نه آبجکت کانتکس */
        /* این خط کد میاد میاد خود فایل اون چیزی که داره درست میشه رو برامون درست میکنه
        * شبیه اسم دیتابیس توی روم */
        private val Context.dataStore : DataStore<Preferences> by  preferencesDataStore(SESSION_AUTH_DATA)
        /* این خط کد میاد دیتای داخل اون فایل رو برامون ذخیره میکنه
        * شبیه اسم تیبل توی روم */
        private val tokenKey = stringPreferencesKey(USER_TOKEN_DATA)
    }

    suspend fun saveToken(token: String){
        appContext.dataStore.edit { data : MutablePreferences ->
            data[tokenKey] = token
        }
    }

    val getToken : Flow<String?> = appContext.dataStore.data.map { data: Preferences ->
       data[tokenKey]
    }

    suspend fun clearToken(){
        appContext.dataStore.edit { data: MutablePreferences ->
            data.clear()
        }
    }
}
