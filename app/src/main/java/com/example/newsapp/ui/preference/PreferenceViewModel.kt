package com.example.newsapp.ui.preference

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newsapp.domain.history

class PreferenceViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is preference Fragment"
    }

}