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
import android.Manifest.permission.INTERNET
import android.app.Activity
import android.os.Build
import android.content.pm.PackageManager
import android.os.AsyncTask
import android.support.v4.app.ActivityCompat.requestPermissions
import android.support.v4.app.ActivityCompat.shouldShowRequestPermissionRationale
import android.support.v4.content.ContextCompat
import org.w3c.dom.Node

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
        chargementDashboard(this).execute()
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

}


/*******************************************
 *  Chargement du dashboard
 *
 *  TODO: Charger les icones sur internet ?
 */
class chargementDashboard(val context: Activity): AsyncTask<Unit, Unit, String>() {
    //lateinit var xmlMenu: Document
    val l_xmlBuilder: DocumentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder()
    var xmlMenuDB: Document = l_xmlBuilder.newDocument() //l_xmlBuilder.parse ("https://www.equinumeric.fr/Applis/menu_dashboard.xml")

    override fun doInBackground(vararg p0: Unit?): String? {

        mayRequestInternet(context)
        xmlMenuDB = l_xmlBuilder.parse("https://www.equinumeric.fr/Applis/menu_dashboard.xml")
        return null
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
        xmlMenuDB.documentElement.normalize()
        var DashBoardMenu: LstMenuDashboard = LstMenuDashboard()

        var id: String = ""
        var ordre: Int = 0
        var Nom: String = ""
        var imgMenu: String = ""
        var description: String = ""

        println("${xmlMenuDB.documentElement.nodeName}")

        if (xmlMenuDB.documentElement.hasChildNodes()) {
            var menuDashboard = xmlMenuDB.getElementsByTagName("MenuDashboard")

            for (i in 0..menuDashboard.length - 1) {
                val elem = menuDashboard.item(i)
                if (elem.hasChildNodes()) {
                    for (j in 0..elem.childNodes.length - 1) {
                        if (elem.childNodes.item(j).nodeType == Node.ELEMENT_NODE) {
                            when (elem.childNodes.item(j).nodeName) {
                                "ID" -> id = elem.childNodes.item(j).textContent
                                "Ordre" -> ordre = elem.childNodes.item(j).textContent.toInt()
                                "Name" -> Nom = elem.childNodes.item(j).textContent
                                "ImgURI" -> imgMenu = elem.childNodes.item(j).textContent
                                "Description" -> description = elem.childNodes.item(j).textContent
                            }
                        }
                    }
                    DashBoardMenu.add(id, ordre, Nom, imgMenu, description)
                }
            }
        }
        //Affichage
        DashBoardMenu.ListMenus?.sortBy { it.ordre }
        var nbItem =  DashBoardMenu.ListMenus?.count()
        var i=0
        while (nbItem!=null && i < nbItem) {

        }


    }


    fun mayRequestInternet(context: Activity): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true
        }
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED) {
            requestPermissions(context, arrayOf(INTERNET), 0)

            return true
        }
        if (shouldShowRequestPermissionRationale(context, INTERNET)) {
            //TODO
        } else {
            requestPermissions(context, arrayOf(INTERNET), 0)
        }
        return false
    }
}


