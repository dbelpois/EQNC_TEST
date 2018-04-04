package com.example.daniel.eqnc_test1

/**
 * Created by daniel on 10/03/2018.
 */
import android.Manifest
import android.app.Activity
import android.text.Editable
import android.text.TextWatcher
import java.util.regex.Pattern
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.AsyncTask
import android.os.Build
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import org.w3c.dom.Document
import org.w3c.dom.Node
import java.util.*
import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory
import kotlin.collections.ArrayList

/**
 * Class qui contient les préférenes sauvegardées.
 */
class SharedPreferenceEntry(
            var accpetationCLUF: Boolean,
            var name: String,
            var dateNaissance: Calendar,
            var emaillogin: String,
            var passlogin: String,
            var autologin: Boolean
)
/**
 * Assistant class pour gérer l'accès au préférences sauvegardées[SharedPreferences].
 *
 * @param sharedPreferences The injected [SharedPreferences] that will be used in this DAO.
 */
class SharedPreferencesHelper(private val sharedPreferences: SharedPreferences) {

    /**
     * Lecture écriture des [SharedPreferenceEntry] dans [SharedPreferences].
     */

    // Get data from the SharedPreferences.
    // Create and fill a SharedPreferenceEntry model object.
    fun getPersonalInfo(): SharedPreferenceEntry {
        val accpetationCLUF = sharedPreferences.getBoolean(KEY_ACCEPT_CLUF,false)
        val name = sharedPreferences.getString(KEY_NAME, "")
        val dateNaissanceMiliSecondes = sharedPreferences.getLong(KEY_DOB, Calendar.getInstance().timeInMillis)
        val dateNaissance = Calendar.getInstance().apply { timeInMillis = dateNaissanceMiliSecondes }
        val emaillogin = sharedPreferences.getString(KEY_EMAIL, "")
        val passlogin = sharedPreferences.getString(KEY_PASSWD, "")
        val autologin = sharedPreferences.getBoolean(KEY_AUTOLOGIN, false)

        return SharedPreferenceEntry(accpetationCLUF, name, dateNaissance, emaillogin, passlogin, autologin)
    }

    fun savePersonalInfo(sharedPreferenceEntry: SharedPreferenceEntry): Boolean {
        // Start a SharedPreferences transaction.
        val editor = sharedPreferences.edit().apply() {
            putBoolean(KEY_ACCEPT_CLUF, sharedPreferenceEntry.accpetationCLUF)
            putString(KEY_NAME, sharedPreferenceEntry.name)
            putLong(KEY_DOB, sharedPreferenceEntry.dateNaissance.timeInMillis)
            putString(KEY_EMAIL, sharedPreferenceEntry.emaillogin)
            putString(KEY_PASSWD, sharedPreferenceEntry.passlogin)
            putBoolean(KEY_AUTOLOGIN, sharedPreferenceEntry.autologin)
        }

        // Commit changes to SharedPreferences.
        return editor.commit()
    }

    companion object {
        // Keys for saving values in SharedPreferences.
        internal val KEY_ACCEPT_CLUF = "key_cluf"
        internal val KEY_NAME = "key_name"
        internal val KEY_DOB = "key_dob_millis"
        internal val KEY_EMAIL = "key_emaillogin"
        internal val KEY_PASSWD = "key_passwd"
        internal val KEY_AUTOLOGIN = "key_autologin"

    }
}
/************************************************************************************************
 * An Email format validator for [android.widget.EditText].
 ***********************************************************************************************/
class EmailValidator : TextWatcher {

    internal var isValid = false

    fun isValid(txt_mail: String): Boolean{
        return isValidEmail(txt_mail)
    }
    override fun afterTextChanged(editableText: Editable) {
        isValid = isValidEmail(editableText)
    }

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) = Unit

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) = Unit

    companion object {

        /**
         * Email validation pattern.
         */
        private val EMAIL_PATTERN = Pattern.compile(
                "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                        "\\@" +
                        "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                        "(" +
                        "\\." +
                        "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                        ")+"
        )

        /**
         * Validates if the given input is a valid email address.
         *
         * @param email    The email to validate.
         * @return `true`  if the input is a valid email, `false` otherwise.
         */
        fun isValidEmail(email: CharSequence?): Boolean {
            return email != null && EMAIL_PATTERN.matcher(email).matches()
        }
    }
}

/**
 * Class qui défini les données d'un concours
 */
class Concours {
    var id: String
    var Nom: String
    var Lieu: String
    var Organisateur: String
    var date: Date
    var refConcour: String
    var imgTypeConcours: String

    constructor(mid: String, Nom: String, Lieu: String, Organisateur: String, date: Date, refConcour: String, imgTypeConcours: String) {
        id=mid
        this.Nom = Nom
        this.Lieu = Lieu
        this.Organisateur = Organisateur
        this.date = date
        this.refConcour = refConcour
        this.imgTypeConcours =  imgTypeConcours
    }
}

class ListConcours {
    var lstConcours: ArrayList<Concours>? = null

    fun add(concours: Concours) {
        if (lstConcours == null) {
            lstConcours = arrayListOf(concours)
        }
        else
            lstConcours?.add(concours)
    }

    fun add(mId: String, Nom: String, Lieu: String, Organisateur: String, date: Date, refConcour: String, imgTypeConcours: String){
        if (lstConcours == null) {
            lstConcours = arrayListOf(Concours(mId, Nom, Lieu, Organisateur, date, refConcour, imgTypeConcours))
        }
        lstConcours?.add(Concours(mId, Nom, Lieu, Organisateur, date, refConcour, imgTypeConcours))
    }
}


class MenuDashboard{
    var id: String
    var ordre: Int
    var Nom: String
    var imgMenu: String
    var description: String

    constructor(mId : String, mOrdre: Int, mNom: String, mImgMenu: String, mDescript: String) {
        this.id = mId
        this.ordre= mOrdre
        this.Nom= mNom
        this.imgMenu = mImgMenu
        this.description = mDescript
    }
}

class LstMenuDashboard {
    var ListMenus: ArrayList<MenuDashboard>? = null

    fun add(mId: String, mOrdre: Int, mNom: String, mImgMenu: String, mDescription: String) {
        if (ListMenus==null)
            ListMenus = arrayListOf(MenuDashboard(mId, mOrdre, mNom, mImgMenu, mDescription))
        else
            ListMenus?.add(MenuDashboard(mId, mOrdre, mNom, mImgMenu, mDescription))
    }

    fun add(mMenuDashboard: MenuDashboard) {
        if (ListMenus==null)
            arrayListOf(mMenuDashboard)
        else
            this.ListMenus?.add(mMenuDashboard)
    }

}



fun mayRequestInternet(context: Activity): Boolean {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
        return true
    }
    if (ContextCompat.checkSelfPermission(context, Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED) {
        ActivityCompat.requestPermissions(context, arrayOf(Manifest.permission.INTERNET), 0)

        return true
    }
    if (ActivityCompat.shouldShowRequestPermissionRationale(context, Manifest.permission.INTERNET)) {
        //TODO
    } else {
        ActivityCompat.requestPermissions(context, arrayOf(Manifest.permission.INTERNET), 0)
    }
    return false
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

}