package com.hasanozdemir.esmaulhusna

import android.content.Context
import android.content.Intent
import android.database.SQLException
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_main.*
import java.io.IOException

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val myDbHelper = DataBaseHelper(this)
        try {
            myDbHelper.createDataBase()
        } catch (ioe: IOException) {
            throw Error("Unable to create database")
        }
        try {
            myDbHelper.openDataBase()
        } catch (sqle: SQLException) {
            throw sqle
        }

        val idList= ArrayList<Int>()
        val isimList= ArrayList<String>()
        val adapter= ArrayAdapter(this, android.R.layout.simple_list_item_1, isimList)
        listView.adapter= adapter

        try {
            val database= this.openOrCreateDatabase("esmaulhusna", Context.MODE_PRIVATE,null)
            //database.execSQL("CREATE TABLE IF NOT EXISTS isimler (id INTEGER PRIMARY KEY, isim VARCHAR, detay VARCHAR, isimeng VARCHAR, detayeng VARCHAR, isimisp VARCHAR, detayesp VARCHAR, resim BLOB)")
            val cursor= database.rawQuery("SELECT * FROM isimler", null)
            val idIx= cursor.getColumnIndex("id")
            val isimIx= cursor.getColumnIndex("isim")
            while (cursor.moveToNext()) {
                idList.add(cursor.getInt(idIx))
                isimList.add(cursor.getString(isimIx))
            }
            adapter.notifyDataSetChanged()
            cursor.close()

        } catch (e:Exception){
            e.printStackTrace()

        }

        listView.onItemClickListener= AdapterView.OnItemClickListener { parent, view, position, id ->
            val intent= Intent(this,DetailActivity::class.java)
            intent.putExtra("id",idList[position])
            startActivity(intent)

        }
    }
}