package com.virtusatask.common

import androidx.appcompat.app.AppCompatActivity

open class BaseActivity  : AppCompatActivity() {

    fun showLoader(mess: String) {
        runOnUiThread {
            LoaderFragment.newInstance(mess, supportFragmentManager)
        }
    }

    fun dismissLoader() {
        runOnUiThread {
            LoaderFragment.dismissLoader(supportFragmentManager)
        }
    }
}