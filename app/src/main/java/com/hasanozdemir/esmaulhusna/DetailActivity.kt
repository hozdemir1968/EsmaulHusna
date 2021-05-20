package com.hasanozdemir.esmaulhusna

import android.content.Context
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_detail.*
import java.lang.Exception

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val intent= intent
        val selId= intent.getIntExtra("id",1)

        try {
            val database= this.openOrCreateDatabase("esmaulhusna", Context.MODE_PRIVATE,null)
            //database.execSQL("CREATE TABLE IF NOT EXISTS isimler (id INTEGER PRIMARY KEY, isim VARCHAR, detay VARCHAR, isimeng VARCHAR, detayeng VARCHAR, isimisp VARCHAR, detayesp VARCHAR, resim BLOB)")
            val cursor= database.rawQuery("SELECT * FROM isimler WHERE id= ?", arrayOf(selId.toString()) )
            val idIx= cursor.getColumnIndex("id")
            val isimIx= cursor.getColumnIndex("isim")
            val detayIx= cursor.getColumnIndex("detay")
            val resimIx= cursor.getColumnIndex("resim")
            while (cursor.moveToNext()) {
                textView1.text = cursor.getString(isimIx)
                textView2.text = cursor.getString(detayIx)
                val byteArray= cursor.getBlob(resimIx)
                val bitmap= BitmapFactory.decodeByteArray(byteArray,0,byteArray.size)
                imageView.setImageBitmap(bitmap)
            }

            cursor.close()

        } catch (e: Exception){
            e.printStackTrace()

        }


    }
}