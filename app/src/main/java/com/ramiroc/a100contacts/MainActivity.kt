package com.ramiroc.a100contacts

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Environment
import android.os.Environment.DIRECTORY_DOWNLOADS
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import java.io.BufferedReader
import java.io.File

class MainActivity : AppCompatActivity() {
    private  val TAG = "MyActivity"
    private val EXTERNAL_STORAGE_PERMISSION_CODE = 23
    var firstName: TextView? = null
    var lastName: TextView? = null
    var company: TextView? = null
    var address: TextView? = null
    var city: TextView? = null
    var county: TextView? = null
    var state: TextView? = null
    var zip: TextView? = null
    var phone1: TextView? = null
    var phone: TextView? = null
    var email: TextView? = null
    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val idText = findViewById<EditText>(R.id.enterID)
        firstName = findViewById(R.id.firstName) as TextView
        lastName = findViewById(R.id.lastName) as TextView
        company = findViewById(R.id.companyName) as TextView
        address = findViewById(R.id.address) as TextView
        city = findViewById(R.id.city) as TextView
        county = findViewById(R.id.county) as TextView
        state = findViewById(R.id.state) as TextView
        zip = findViewById(R.id.zip) as TextView
        phone1 = findViewById(R.id.phone1) as TextView
        phone = findViewById(R.id.phone) as TextView
        email = findViewById(R.id.email) as TextView



            val downloadDirectory = Environment.getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS)
            val file = File(downloadDirectory, "100-contacts.csv")

            val db = DBHelper(this, null)



            val bufferedReader: BufferedReader = file.bufferedReader()

            val csvParser = CSVParser(bufferedReader, CSVFormat.DEFAULT
                .withFirstRecordAsHeader()
                .withIgnoreHeaderCase()
                .withTrim());


            for (csvRecord in csvParser) {
                Log.i(TAG, "Attempting to add record")
                db.addName(csvRecord.get(0), csvRecord.get(1), csvRecord.get(2),
                csvRecord.get(3), csvRecord.get(4), csvRecord.get(5), csvRecord.get(6),
                csvRecord.get(7), csvRecord.get(8), csvRecord.get(9), csvRecord.get(10))

            }


        val findButton = findViewById<Button>(R.id.find)

        findButton.setOnClickListener {

            val db = DBHelper(this, null)
            val id = idText.text.toString()

            if(id.toInt() in 1..99) {
                Log.i("finding id", "Finding ID")
                //val cursor = db.getName()
                //cursor!!.moveToFirst()
                val cursor = db.getContact(id)
                cursor!!.moveToFirst()

                if (cursor != null) {
                    firstName!!.text =
                        cursor.getString(cursor.getColumnIndex(DBHelper.FIRST_NAME_COl))
                    lastName!!.text =
                        cursor.getString(cursor.getColumnIndex(DBHelper.LAST_NAME_COL))
                    company!!.text = cursor.getString(cursor.getColumnIndex(DBHelper.COMPANY_NAME))
                    county!!.text = cursor.getString(cursor.getColumnIndex(DBHelper.COUNTY_COL))
                    address!!.text = cursor.getString(cursor.getColumnIndex(DBHelper.ADDRESS))
                    state!!.text = cursor.getString(cursor.getColumnIndex(DBHelper.STATE))
                    city!!.text = cursor.getString(cursor.getColumnIndex(DBHelper.STATE))
                    zip!!.text = cursor.getString(cursor.getColumnIndex(DBHelper.ZIP))
                    phone1!!.text = cursor.getString(cursor.getColumnIndex(DBHelper.PHONE1))
                    phone!!.text = cursor.getString(cursor.getColumnIndex(DBHelper.PHONE))
                    email!!.text = cursor.getString(cursor.getColumnIndex(DBHelper.EMAIL))
                }
            } else {
                Toast.makeText(this, "Please input valid id (1-99)", Toast.LENGTH_LONG).show()
            }
        }
    }
}