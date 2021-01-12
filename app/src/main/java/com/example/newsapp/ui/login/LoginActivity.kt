package com.example.newsapp.ui.login

import android.content.Context
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
import com.example.newsapp.domain.user
import com.example.newsapp.net.AppService
import com.example.newsapp.session.Session
import com.example.newsapp.ui.register.RegisterActivity
import kotlinx.android.synthetic.main.activity_login.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        var username = findViewById<EditText>(R.id.username)
        var password = findViewById<EditText>(R.id.password)
        var login_btn = findViewById<Button>(R.id.login)



        class TextChange : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                if (username.length() > 0 && password.length() > 0) {
                    login_btn.isEnabled = true
                }
                else
                    login_btn.isEnabled = false
            }
        }
        var textChange = TextChange()
        username.addTextChangedListener(textChange)
        password.addTextChangedListener(textChange)

        Toregister.setOnClickListener{
            val Intent = Intent(this, RegisterActivity::class.java)
            startActivity(Intent)
        }

        login.setOnClickListener{
            val retrofit = Retrofit.Builder()
                .baseUrl("https://www.dorado.host/android/public/index.php/index/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val appService = retrofit.create(AppService::class.java)

            appService.login(username.text.toString(), password.text.toString()).enqueue(object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    val responser = response.body()?.string()
                    if (responser == "false") {
                        Session.loginFlag = false
                        logerror()
                    }else{
                        Session.loginFlag = true
                        Session.session_key = responser!!
                    }
                    get_user()
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    t.printStackTrace()
                    logerror()
                }

            })
        }


    }

    fun get_user(){
        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.dorado.host/android/public/index.php/index/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val appService = retrofit.create(AppService::class.java)
        appService.get(Session.session_key).enqueue(object : Callback<user> {
            override fun onResponse(call: Call<user>, response: Response<user>) {
                val response = response.body()
                Session.username = response!!.name
                Session.favor_1 = response!!.favor_1
                Session.favor_2 = response!!.favor_2
                Session.favor_3 = response!!.favor_3
                Session.favor_4 = response!!.favor_4
                Session.favor_5 = response!!.favor_5
                moveToMain()

            }

            override fun onFailure(call: Call<user>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }

    fun logerror(){
        Toast.makeText(this, "登录失败！", Toast.LENGTH_SHORT).show()
    }

    fun moveToMain(){
        val Intent = Intent(this, MainActivity::class.java)
        startActivity(Intent)
        finish()
    }


}
