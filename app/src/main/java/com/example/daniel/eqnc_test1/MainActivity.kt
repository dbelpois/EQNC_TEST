package com.example.daniel.eqnc_test1

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import org.w3c.dom.Document
import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory
import android.app.Activity
import android.os.AsyncTask
import com.example.daniel.eqnc_test1.dummy.DummyContent
import org.w3c.dom.Node

/**
 * Created by Daniel on 10/03/2018.
 */
class MainActivity : AppCompatActivity() {

    lateinit var sharedPreferencesHelper: SharedPreferencesHelper
    lateinit var sharedPreferenceEntry:  SharedPreferenceEntry

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadApplication()
        chargementDashboard(this).execute()
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .add(R.id.container, MyDashBoardRecyclerViewAdapter())
                    .commit()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_lstconcours, menu)
        return true
    }
    override fun onOptionsMenuClosed(item: Menu?) {

        super.onOptionsMenuClosed(item)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return super.onOptionsItemSelected(item)
    }

    /*******************************************
     *  Chargement de l'application
     *  TODO: attendre l'affichage complète de l'Activity ?
     */
    private fun loadApplication() {
        // Chargement des préférences
        sharedPreferencesHelper = SharedPreferencesHelper(PreferenceManager.getDefaultSharedPreferences(this))
        sharedPreferenceEntry = sharedPreferencesHelper.getPersonalInfo()

        /*
        sharedPreferenceEntry.accpetationCLUF=false
        sharedPreferencesHelper.savePersonalInfo(sharedPreferenceEntry)
        */

        if (!sharedPreferenceEntry.accpetationCLUF) {
            startActivity(Intent(this, EcrInfo::class.java))
        }

        val gotoLogin: Intent = Intent( this, ECRLogin::class.java)
        if (sharedPreferenceEntry.emaillogin !="" && sharedPreferenceEntry.passlogin !="") {
            gotoLogin.putExtra("prm_userName", sharedPreferenceEntry.emaillogin)
            gotoLogin.putExtra("prm_userPassword", sharedPreferenceEntry.passlogin)
            gotoLogin.putExtra("prm_autoLogin", true)
        }
        startActivityForResult(Intent(gotoLogin),0)
    }
    interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onListFragmentInteraction(item: DummyContent.DummyItem?)
    }
}



