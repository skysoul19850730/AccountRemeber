package com.jscoolstar.accountremeber.apps

import com.jscoolstar.accountremeber.activities.login.LoginViewModel
import com.jscoolstar.accountremeber.activities.register.RegisterViewModel
import com.jscoolstar.accountremeber.dbroom.AccountDatabase
import com.jscoolstar.accountremeber.model.repository.local.UserRepositoryLocal
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { LoginViewModel() }
    viewModel { RegisterViewModel(get()) }
}

val repositoryModule = module {
    single { UserRepositoryLocal(get()) }
}
var daosModule = module {
    single { AccountDatabase.getInstance(androidContext()).userDao()}
    single { AccountDatabase.getInstance(androidContext()).cateDao() }
    single { AccountDatabase.getInstance(androidContext()).accountDao() }
    single { AccountDatabase.getInstance(androidContext()).extrasDao() }
}

val appModule = listOf(viewModelModule, repositoryModule, daosModule)