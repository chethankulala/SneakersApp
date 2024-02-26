package com.example.sneakersapp.core.util

import android.view.View
import com.example.sneakersapp.features.model.Sneaker

interface ActionListnerCallback {
    fun onClick(view: View, data: Sneaker? = null)
}