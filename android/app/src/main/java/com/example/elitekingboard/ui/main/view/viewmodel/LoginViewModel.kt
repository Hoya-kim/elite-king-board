package com.example.elitekingboard.ui.main.view.viewmodel

import androidx.lifecycle.*
import com.example.elitekingboard.data.dto.request.LoginRequest
import com.example.elitekingboard.data.dto.request.SignUpRequest
import com.example.elitekingboard.data.dto.response.LoginResponse
import com.example.elitekingboard.data.dto.response.SignUpResponse
import com.example.elitekingboard.data.repository.RemoteRepository
import com.example.elitekingboard.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class LoginViewModel(private val remoteRepository: RemoteRepository) : ViewModel() {
    private val _signUpResponse = MutableLiveData<Resource<Response<SignUpResponse>>>()
    val signUpResponse: LiveData<Resource<Response<SignUpResponse>>>
        get() = _signUpResponse
    private val _loginResponse = MutableLiveData<Resource<Response<LoginResponse>>>()
    val loginResponse: LiveData<Resource<Response<LoginResponse>>>
        get() = _loginResponse

    fun requestSignUp(singUpRequest: SignUpRequest) = liveData {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(remoteRepository.requestSignUp(singUpRequest)))
        } catch (e: Exception) {
            emit(Resource.error(null, e.message ?: "Error Occured"))
        }
    }

    fun requestLogin(loginRequest: LoginRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            _loginResponse.postValue(Resource.loading(null))
            try {
                _loginResponse.postValue(Resource.success(remoteRepository.requestLogin(loginRequest)))
            } catch (e: Exception) {
                _loginResponse.postValue(Resource.error(null, e.message ?: "Error"))
            }
        }
    }
}
