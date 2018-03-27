package com.example.daniel.eqnc_test1

import android.content.ClipData.newIntent
import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_ecr_info.*
import kotlinx.android.synthetic.main.content_ecr_info.*

class EcrInfo : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ecr_info)
        //setSupportActionBar(toolbar)
        btn_validerLicence.setOnClickListener { onValidateLicenceClick() }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate( R.menu.menu_ecr_info, menu)
        return true
    }

    private fun onValidateLicenceClick(){
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val sharedPreferencesHelper = SharedPreferencesHelper(sharedPreferences)
        val sharedPreferenceEntry = sharedPreferencesHelper.getPersonalInfo()
        sharedPreferenceEntry.accpetationCLUF=chk_valideerlicence.isChecked
        sharedPreferencesHelper.savePersonalInfo(sharedPreferenceEntry)
        this.finish()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        when (item.itemId) {

            R.id.action_settings ->{
                startActivity(Intent(this@EcrInfo, ECRLogin::class.java))
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
        return true
    }
}

