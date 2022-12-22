package com.usd.eur.currency.allcurrencyconverter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.EditText
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

import com.usd.eur.currency.allcurrencyconverter.databinding.ActivityMainBinding
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)






        binding.getResult.setOnClickListener {

            binding.progressBar.visibility=View.VISIBLE
            val fromSpin=binding.spinner.selectedItem.toString()

            Log.d("selectedItem1",fromSpin.toString())

            val toSpin=binding.spinner2.selectedItem.toString()

            Log.d("selectedItem2",toSpin.toString())

            val fromCur=binding.fromText.text.toString().toDouble()

            Log.d("selectedItem3",fromCur.toString())
            val toCur=binding.fromText.text.toString().toDouble()

            getResult(fromSpin,toSpin,fromCur)

        }
    }



    private fun getResult(fromSpin:String,toSpin:String,amount:Double) {

        val queue = Volley.newRequestQueue(this)

        if(fromSpin!=toSpin) {
            val url =
                "https://api.frankfurter.app/latest?amount=${amount}&from=${fromSpin}&to=${toSpin}"

            val stringRequest = StringRequest(
                Request.Method.GET, url,
                Response.Listener<String> { response ->
                    // Display the first 500 characters of the response string.
                    val res=JSONObject(response)

                    Log.d("curr",res.getJSONObject("rates")["${toSpin}"].toString())
                    val showText=res.getJSONObject("rates")["${toSpin}"].toString()

                    binding.toText.setText(showText)
                    binding.progressBar.visibility=View.GONE
                },
                Response.ErrorListener { Log.d("Error","error")})


            queue.add(stringRequest)

        }else{
            Toast.makeText(applicationContext, "Please Select Two Different Currencies", Toast.LENGTH_SHORT).show()
        }
// Request a string response from the provided URL.

    }
}