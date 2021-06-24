package com.example.newsapp.ui.register

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.newsapp.MainActivity
import com.example.newsapp.R
import com.example.newsapp.net.AppService
import com.example.newsapp.ui.login.LoginActivity
import kotlinx.android.synthetic.main.activity_login.view.*
import kotlinx.android.synthetic.main.activity_register.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.newsapp.session.Session

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        var register_username = findViewById<EditText>(R.id.register_username)
        var register_password = findViewById<EditText>(R.id.register_password)
        var register_btn = findViewById<Button>(R.id.register)

        class TextChange : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                if (register_username.length() > 0 && register_password.length() > 0) {
                    register_btn.isEnabled = true
                }
                else
                    register_btn.isEnabled = false
            }
        }
        var textChange = TextChange()
        register_username.addTextChangedListener(textChange)
        register_password.addTextChangedListener(textChange)

        register_btn.setOnClickListener() {
            val retrofit = Retrofit.Builder()
                .baseUrl(Session.BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val appService = retrofit.create(AppService::class.java)
            appService.register(register_username.text.toString(), register_password.text.toString()).enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    val response = response.body()
                    if(response == null || response == "false"){
                        logerror()
                    }else{
                        moveToLogin()
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    t.printStackTrace()
                }

            })
        }


    }

    fun logerror(){
        Toast.makeText(this, "注册失败！", Toast.LENGTH_SHORT).show()
    }

    fun moveToLogin(){
        Toast.makeText(this, "注册成功！", Toast.LENGTH_SHORT).show()
        val Intent = Intent(this, LoginActivity::class.java)
        startActivity(Intent)
        finish()
    }
}
