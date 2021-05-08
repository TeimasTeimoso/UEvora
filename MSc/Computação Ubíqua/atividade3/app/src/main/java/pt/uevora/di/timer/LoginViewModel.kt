package pt.uevora.di.timer

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel: ViewModel() {

    private val _loginResult = MutableLiveData<Boolean>()

    val loginResultLiveData: MutableLiveData<Boolean> = _loginResult

    fun areCredtentialsValid(username: String, password: String) {
        loginResultLiveData.postValue(username != password)
    }
}