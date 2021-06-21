package pt.uevora.di.atividade5

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel: ViewModel() {

    private val _loginResult = MutableLiveData<Boolean>()
    val _loginResultLiveData = _loginResult

    fun areCredentialsValid(username: String, password: String) {
        _loginResultLiveData.postValue(username != password)
    }
}