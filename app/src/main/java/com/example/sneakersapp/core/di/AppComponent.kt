package com.example.sneakersapp.core.di

import com.example.sneakersapp.MyApplication
import com.example.sneakersapp.features.view.CartFragment
import com.example.sneakersapp.features.view.HomeFragment
import com.example.sneakersapp.features.view.MainActivity
import com.example.sneakersapp.features.view.SneakerDetailsFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    //fun inject(fragment: MyApplication)
    fun inject(fragment: HomeFragment)
    fun inject(fragment: SneakerDetailsFragment)
    fun inject(fragment: CartFragment)
}