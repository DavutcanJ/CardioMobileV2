package com.example.cardiomobilev2

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.cardiomobilev2.databinding.ActivityLogInBinding
import com.example.cardiomobilev2.databinding.ActivitySignInBinding
import com.google.firebase.auth.FirebaseAuth

class LogInActivity : AppCompatActivity() {


private lateinit var  binding: ActivityLogInBinding
private lateinit var  firebaseAuth : FirebaseAuth

override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    binding =ActivityLogInBinding.inflate(layoutInflater)
    setContentView(binding.root)
    firebaseAuth = FirebaseAuth.getInstance()
    binding.registertext.setOnClickListener{

        val intent = Intent(this,SignInActivity::class.java)
        startActivity(intent)
    }

    binding.loginbuton.setOnClickListener {
        var email = binding.email.text.toString()
        var password = binding.password.text.toString()


        if(email.isNotEmpty() && password.isNotEmpty() )
        {
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener{

            if(it.isSuccessful){
                val intent = Intent(this,MainActivity::class.java)
                val user = firebaseAuth.currentUser
                val userId = user?.uid
                intent.putExtra("userId", userId)
                startActivity(intent)
            }else
            {
                Toast.makeText(this,"Email or password wrong,not in system", Toast.LENGTH_SHORT).show()
            }
            }
        }else
        {
            Toast.makeText(this,"Fill the blanks!", Toast.LENGTH_SHORT).show()
        }
    }

}

}