package com.example.daniel.eqnc_test1

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextView

import kotlinx.android.synthetic.main.activity_ecr_get_data.*

class EcrGetData : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ecr_get_data)
        setSupportActionBar(toolb_menu)

        val lstParticipants = findViewById<ListView>(R.id.EcreGetData_lstParticipants)
        lstParticipants.adapter= LstParticipantsAdapt(this)
    }

    private class LstParticipantsAdapt(context: Context): BaseAdapter() {

        private val mContext: Context

        init {
            mContext=context
        }

        override fun getCount(): Int {
            return 5
        }

        override fun getItem(position: Int): Any {
            return "text"
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View?, viewGroup: ViewGroup?): View {
            val textView = TextView(mContext)
            textView.text = "Ici ma ligne)"
            return textView
        }
    }
}
