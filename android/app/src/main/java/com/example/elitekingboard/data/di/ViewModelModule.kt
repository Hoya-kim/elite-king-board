package com.example.elitekingboard.data.di

import com.example.elitekingboard.data.repository.RemoteRepository
import com.example.elitekingboard.ui.main.view.viewmodel.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { LoginViewModel(get()) }
}
val repositoryModule = module {
    single { RemoteRepository() }
}
