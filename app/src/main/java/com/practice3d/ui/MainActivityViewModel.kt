package com.practice3d.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(application: Application) :
    AndroidViewModel(application) {
}

class ViewState() {
    var locked: Boolean = false
}