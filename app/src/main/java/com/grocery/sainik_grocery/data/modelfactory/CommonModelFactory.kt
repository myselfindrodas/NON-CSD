package com.grocery.sainik_grocery.data.modelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.grocery.sainik_grocery.data.ApiHelper
import com.grocery.sainik_grocery.data.repository.MainRepository
import com.grocery.sainik_grocery.viewmodel.CommonViewModel

class CommonModelFactory(private val apiHelper: ApiHelper) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        CommonViewModel(MainRepository(apiHelper)) as T
}