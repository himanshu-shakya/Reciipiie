package com.reciipiie.app.di

import com.google.gson.Gson
import com.reciipiie.app.common.utils.Constants
import com.reciipiie.app.common.utils.UsernamePreferences
import com.reciipiie.app.data.remote.api.RecipeApiService
import com.reciipiie.app.data.repository.AuthenticationRepositoryImpl
import com.reciipiie.app.data.repository.RecipeRepositoryImpl
import com.reciipiie.app.domain.repository.AuthenticationRepository
import com.reciipiie.app.domain.repository.RecipeRepository
import com.reciipiie.app.presantation.viewmodel.AuthenticationViewModel
import com.reciipiie.app.presantation.viewmodel.RecipeViewModel
import io.appwrite.Client
import io.appwrite.services.Account
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appwriteModules = module {
    single {
        Client(androidContext())
            .setEndpoint(Constants.APPWRITE_URL_ENDPOINT)
            .setProject(Constants.PROJECTID)
    }
    single{
        Account(get())
    }
}

val viewModelModules = module {
    single{
        AuthenticationViewModel(get(),get())
    }
    single{
        RecipeViewModel(get())
    }
}

val repositoryModules = module {
    single<AuthenticationRepository> {
        AuthenticationRepositoryImpl(get())
    }
    single<RecipeRepository> {
        RecipeRepositoryImpl(get(),Constants.API_KEY)
    }
}

val otherModules = module{
    single{
        UsernamePreferences(androidContext())
    }
}
val networkModule = module {
    single {
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single { get<Retrofit>().create(RecipeApiService::class.java) }
}
val appModules = listOf(
    appwriteModules,
    viewModelModules,
    repositoryModules,
    otherModules,
    networkModule

)