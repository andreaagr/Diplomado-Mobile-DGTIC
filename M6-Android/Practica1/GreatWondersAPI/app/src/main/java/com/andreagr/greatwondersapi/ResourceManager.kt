package com.andreagr.greatwondersapi

import android.content.Context

class ResourceManager(private val context: Context) {
    val emptyFieldsError get() = context.getString(R.string.empty_fields_error)
    val badPasswordError get() = context.getString(R.string.password_size_error)
    val unknownError get() = context.getString(R.string.unknow_error)
    val recoverySuccess get() = context.getString(R.string.success_send_password_recovery)
    val createSucess get() = context.getString(R.string.success_create_account)
}