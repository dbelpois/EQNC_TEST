package com.example.daniel.eqnc_test1

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import org.w3c.dom.Document
import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory
import android.Manifest.permission.INTERNET
import android.os.Build
import android.content.pm.PackageManager
import android.os.AsyncTask
import android.support.design.widget.Snackbar
import android.widget.ArrayAdapter
import android.widget.ListView
import kotlinx.android.synthetic.main.activity_ecrlogin.*
import kotlinx.android.synthetic.main.concour_list.*
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.xmlpull.v1.XmlPullParser

/**
 * Created by Daniel on 10/03/2018.
 */
class MainActivity :  AppCompatActivity(){

    lateinit var sharedPreferencesHelper: SharedPreferencesHelper
    lateinit var sharedPreferenceEntry:  SharedPreferenceEntry

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadApplication()
        chargementDashboard()
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
    /*******************************************
     *  Chargement du dashboard
     *
     *  TODO: Charger les icones sur internet ?
     */
    private fun chargementDashboard() {
        var DashBoardMenu :LstMenuDashboard
        val l_xmlBuilder : DocumentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder()
        mayRequestInternet()
        //var xmlMenu: Document = l_xmlBuilder.parse("https://www.equinumeric.fr/Applis/menu_dashboard.xml")
        var xmlMenuDB: Document = l_xmlBuilder.newDocument() //l_xmlBuilder.parse ("https://www.equinumeric.fr/Applis/menu_dashboard.xml")

        xmlMenuDB.documentURI = "https://www.equinumeric.fr/Applis/menu_dashboard.xml"
        xmlMenuDB.normalizeDocument()

        for ( i in 0 until xmlMenuDB.getElementsByTagName("MenuDashBoards").length -1) {
            var MenuNode: Node = xmlMenuDB.getElementsByTagName("MenuDashBoards").item(i)
            val elem = MenuNode as Element
            println("ID: ${elem.getElementsByTagName("ID").item(0).textContent}")
        }



    }
    private fun mayRequestInternet(): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true
        }
        if (checkSelfPermission(INTERNET) == PackageManager.PERMISSION_GRANTED) {
            return true
        }
        if (shouldShowRequestPermissionRationale(INTERNET)) {
            //TODO
        } else {
            requestPermissions(arrayOf(INTERNET), 0)
        }
        return false
    }
}


