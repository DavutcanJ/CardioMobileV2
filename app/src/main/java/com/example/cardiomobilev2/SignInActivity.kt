package com.example.cardiomobilev2

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText

import com.google.firebase.auth.auth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import com.example.cardiomobilev2.databinding.ActivitySignInBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.firestore.ktx.firestore


class SignInActivity : AppCompatActivity() {


    private lateinit var    binding: ActivitySignInBinding
    private lateinit var  firebaseAuth :FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()

        binding.signlogbutton.setOnClickListener {

            val intent = Intent(this,LogInActivity::class.java)
            startActivity(intent)
        }
        binding.signupbutton.setOnClickListener {

            var username = binding.username.text.toString()
            var email = binding.email.text.toString()
            var password = binding.password.text.toString()
            var repassword = binding.repassword.text.toString()



            if(email.isNotEmpty() && password.isNotEmpty() && repassword.isNotEmpty() && username.isNotEmpty())
            {
                if(repassword == password){

                    firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener{

                        if(it.isSuccessful){
                            val userId = firebaseAuth.currentUser?.uid
                            val db = Firebase.firestore
                            val user = hashMapOf(
                                "id" to userId,
                                "username" to username
                            )

                            userId?.let { id ->
                                db.collection("MydataBase")
                                    .document(id)
                                    .set(user)
                                    .addOnSuccessListener {
                                        Log.d(TAG, "DocumentSnapshot successfully written!")
                                    }
                                    .addOnFailureListener { e ->
                                        Log.w(TAG, "Error writing document", e)
                                    }
                            }

                            val intent = Intent(this,LogInActivity::class.java)
                            startActivity(intent)
                        }else
                        {
                            Toast.makeText(this,it.exception.toString(),Toast.LENGTH_SHORT).show()
                        }
                    }
                }else
                {
                        Toast.makeText(this,"Passwords is not matching ! ",Toast.LENGTH_SHORT).show()
                }
            }else
            {
                Toast.makeText(this,"Fill the blanks ! ",Toast.LENGTH_SHORT).show()
            }
        }


        }







}