package com.lokarz.gameforview.model.repository.google

import com.lokarz.gameforview.pojo.google.GoogleAccount
import com.lokarz.gameforview.util.Constant
import com.lokarz.gameforview.util.GsonUtil
import com.lokarz.gameforview.util.Preference
import com.lokarz.gameforview.util.PreferenceUtil
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GoogleLocalRepository @Inject constructor(
    private val preferenceUtil: Preference
) {

    fun getData(): Single<GoogleAccount> {
        return Single.create {
            val googleAccount = GsonUtil.getGson(preferenceUtil, GoogleAccount::class.java)
            if (googleAccount != null) {
                it.onSuccess(googleAccount)
            } else {
                it.onError(Throwable(Constant.Error.SOMETHING_WENT_WRONG))
            }
        }
    }

    fun clearData() {
        preferenceUtil.clearData(GoogleAccount::class.simpleName).subscribe()
    }

    fun saveData(googleAccount: GoogleAccount): Single<Boolean> {
        return preferenceUtil.saveDataRx(
            googleAccount::class.simpleName,
            GsonUtil.getGsonString(googleAccount)
        )

    }
}