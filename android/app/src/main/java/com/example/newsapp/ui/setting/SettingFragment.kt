package com.example.newsapp.ui.setting

import android.content.Intent
import android.os.Bundle
import android.se.omapi.Session
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.newsapp.R
import com.example.newsapp.net.AppService
import com.example.newsapp.session.Session.favor_1
import com.example.newsapp.session.Session.favor_2
import com.example.newsapp.session.Session.favor_3
import com.example.newsapp.session.Session.favor_4
import com.example.newsapp.session.Session.favor_5
import com.example.newsapp.session.Session.loginFlag
import com.example.newsapp.session.Session.session_key
import com.example.newsapp.session.Session.username
import com.example.newsapp.session.Session.BaseUrl
import com.example.newsapp.ui.login.LoginActivity
import com.example.newsapp.ui.register.RegisterActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_setting.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SettingFragment : Fragment() {

    private lateinit var settingViewModel: SettingViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        settingViewModel =
                ViewModelProviders.of(this).get(SettingViewModel::class.java)
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fab = activity?.findViewById<FloatingActionButton>(R.id.fab)
        fab?.hide()

        if(loginFlag){
            setting_username.text = username
            news_count.hint = favor_1.toString()
            fiance_count.hint = favor_2.toString()
            sports_count.hint = favor_3.toString()
            play_count.hint = favor_4.toString()
            army_count.hint = favor_5.toString()
        }else
        {
            news_count.isEnabled = false
            fiance_count.isEnabled = false
            sports_count.isEnabled = false
            play_count.isEnabled = false
            army_count.isEnabled = false
            setting_username.text = "尚未登陆，功能禁用"
            setting_edit_btn.isEnabled = false
            out_login_btn.isEnabled = false
        }

        setting_edit_btn.setOnClickListener {
            val retrofit = Retrofit.Builder()
                .baseUrl(BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val appService = retrofit.create(AppService::class.java)

            try{
                appService.edit_favor(session_key, news_count.text.toString().toInt(),
                    fiance_count.text.toString().toInt(),
                    sports_count.text.toString().toInt(),
                    play_count.text.toString().toInt(),
                    army_count.text.toString().toInt()).enqueue(object :
                    Callback<ResponseBody> {
                    override fun onResponse(
                        call: Call<ResponseBody>,
                        response: Response<ResponseBody>
                    ) {
                        Toast.makeText(requireContext(), "修改成功！", Toast.LENGTH_SHORT).show()
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        t.printStackTrace()
                    }

                })
            }catch (e : Exception)
            {
                Toast.makeText(requireContext(), "参数配置出错, 请重新配置", Toast.LENGTH_LONG).show()
            }


        }

        out_login_btn.setOnClickListener {
            loginFlag = false
            session_key = ""
            favor_1 = 0
            favor_2 = 0
            favor_3 = 0
            favor_4 = 0
            favor_5 = 0
            username = ""
            Toast.makeText(requireContext(), "退出登录！", Toast.LENGTH_SHORT).show()
            val Intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(Intent)
            activity?.finish()
        }
    }
}