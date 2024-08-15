package com.example.cardiomobilev2

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.cardiomobilev2.databinding.ActivityMainBinding
import android.widget.Toast
import com.example.cardiomobilev2.Constant.BASE_URL
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.annotations.SerializedName

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.savebtn.setOnClickListener {
            sendUserData()
        }

        binding.calculatebtn.setOnClickListener {
            getMyData()
        }
    }

    private fun getMyData() {
        val userId = intent.getStringExtra("userId")
        val db = Firebase.firestore

        db.collection("MydataBase")
            .document(userId!!)
            .get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    Log.d(TAG, "DocumentSnapshot data: ${document.data}")

                    val user = User(
                        id = document.getString("id")!!,
                        age = document.getString("age")!!,
                        gender = document.getString("gender")!!,
                        height = document.getString("height")!!,
                        weight = document.getString("weight")!!,
                        aphi = document.getString("aphi")!!,
                        aplo = document.getString("aplo")!!,
                        choles = document.getString("choles")!!,
                        glucose = document.getString("glucose")!!,
                        smoke = document.getString("smoke")!!,
                        alco = document.getString("alco")!!,
                        active = document.getString("active")!!
                    )

                    Log.e("TAG", user.toString())

                    sendUserDataToApi(user)
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
            }
    }
    private fun sendUserDataToApi(user: User) {
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(ApiInterface::class.java)
        val maintitlestr = binding.maintitletext
        val retrofitData = retrofitBuilder.getDeseaseData(user)
        retrofitData.enqueue(object : Callback<RateResponse> {
            override fun onResponse(
                call: Call<RateResponse>,
                response: Response<RateResponse>
            ) {
                Log.e("Responsebody", response.body().toString())
                maintitlestr.text = buildString {
                    append("Your cardiovascular desease rate ")
                    append(response.body()?.cardisrate.toString())
                }
            }

            override fun onFailure(call: Call<RateResponse>, t: Throwable) {
                Log.e("Responsebodyerror", t.message.toString())
            }
        })
    }

    private fun sendUserData() {
        val userId = intent.getStringExtra("userId")

        val genderRadioGroup = findViewById<LinearLayout>(R.id.genderRadioGroup)
        var selectedGenderId: Int? = null
        for (i in 0 until genderRadioGroup.childCount) {
            val child = genderRadioGroup.getChildAt(i)
            if (child is RadioButton && child.isChecked) {
                selectedGenderId = child.id
                break
            }
        }
        val gender: String = when (selectedGenderId) {
            R.id.active0 -> "0"
            R.id.active1 -> "1"
            else -> "Unknown" // default value
        }

        val cholesterolRadioGroup = findViewById<LinearLayout>(R.id.cholesterolRadioGroup)
        var selectedCholesterolId: Int? = null
        for (i in 0 until cholesterolRadioGroup.childCount) {
            val child = cholesterolRadioGroup.getChildAt(i)
            if (child is RadioButton && child.isChecked) {
                selectedCholesterolId = child.id
                break
            }
        }

        val cholesterol: String = when (selectedCholesterolId) {
            R.id.cholesterol0 -> "1"
            R.id.cholesterol1 -> "2"
            R.id.cholesterol2 -> "3"
            else -> "Unknown" // default value
        }

        val glucoseRadioGroup = findViewById<LinearLayout>(R.id.glucoseRadioGroup)
        var selectedGlucoseId: Int? = null
        for (i in 0 until glucoseRadioGroup.childCount) {
            val child = glucoseRadioGroup.getChildAt(i)
            if (child is RadioButton && child.isChecked) {
                selectedGlucoseId = child.id
                break
            }
        }

        val glucose: String = when (selectedGlucoseId) {
            R.id.glucose0 -> "1"
            R.id.glucose1 -> "2"
            R.id.glucose2 -> "3"
            else -> "Unknown" // default value
        }

        val smokeRadioGroup = findViewById<LinearLayout>(R.id.smokeRadioGroup)
        var selectedSmokeId: Int? = null
        for (i in 0 until smokeRadioGroup.childCount) {
            val child = smokeRadioGroup.getChildAt(i)
            if (child is RadioButton && child.isChecked) {
                selectedSmokeId = child.id
                break
            }
        }

        val smoke: String = when (selectedSmokeId) {
            R.id.smoke0 -> "0"
            R.id.smoke1 -> "1"

            else -> "Unknown" // default value
        }

        val alcoRadioGroup = findViewById<LinearLayout>(R.id.alcoRadioGroup)
        var selectedAlcoId: Int? = null
        for (i in 0 until alcoRadioGroup.childCount) {
            val child = alcoRadioGroup.getChildAt(i)
            if (child is RadioButton && child.isChecked) {
                selectedAlcoId = child.id
                break
            }
        }

        val alco: String = when (selectedAlcoId) {
            R.id.alco0 -> "0"
            R.id.alco1 -> "1"

            else -> "Unknown" // default value
        }

        val activeRadioGroup = findViewById<LinearLayout>(R.id.activeRadioGroup)
        var selectedActiveId: Int? = null
        for (i in 0 until activeRadioGroup.childCount) {
            val child = activeRadioGroup.getChildAt(i)
            if (child is RadioButton && child.isChecked) {
                selectedActiveId = child.id
                break
            }
        }
        val active: String = when (selectedActiveId) {
            R.id.active0 -> "0"
            R.id.active1 -> "1"
            else -> "Unknown" // default value
        }

        val user = hashMapOf(
            "id" to userId,
            "age" to binding.age.text.toString(),
            "gender" to gender,
            "height" to binding.height.text.toString(),
            "weight" to binding.weight.text.toString(),
            "aphi" to binding.aphi.text.toString(),
            "aplo" to binding.aplo.text.toString(),
            "choles" to cholesterol,
            "glucose" to glucose,
            "smoke" to smoke,
            "alco" to alco,
            "active" to active
        )

        val db = Firebase.firestore
        userId?.let { id ->
            db.collection("MydataBase")
                .document(id)
                .set(user)
                .addOnSuccessListener {
                    Log.d(TAG, "DocumentSnapshot successfully written!")
                    Toast.makeText(this, "Data saved successfully", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Error writing document", e)
                    Toast.makeText(this, "Data could not be saved", Toast.LENGTH_SHORT).show()
                }
        }
    }


}



   

