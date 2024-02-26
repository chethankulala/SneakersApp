package com.example.sneakersapp.features.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sneakersapp.core.constant.AppConstant
import com.example.sneakersapp.features.model.Sneaker
import com.example.sneakersapp.features.repository.DataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: DataRepository): ViewModel() {

    private var _sneakers: MutableLiveData<List<Sneaker>>? = null
    val sneaker: LiveData<List<Sneaker>>
        get() {
            if (_sneakers == null) {
                _sneakers = MutableLiveData()
            }
            return _sneakers!!
        }
    var insertSuccessOrFailLiveData: MutableLiveData<Int>? = MutableLiveData()
    var deleteSuccessOrFailLiveData: MutableLiveData<Int>? = MutableLiveData()
    var subTotalLiveData: MutableLiveData<Int>? = MutableLiveData()
    private var _sneakersFromCart: MutableLiveData<List<Sneaker>>? = null
    val sneakersFromCart: LiveData<List<Sneaker>>
        get() {
            if (_sneakersFromCart == null) {
                _sneakersFromCart = MutableLiveData()
            }
            return _sneakersFromCart!!
        }

    fun getSneakersDetails() {
        viewModelScope.launch(Dispatchers.IO) {
            val res = repository.getSneakersDetails()
            _sneakers?.postValue(res)
        }
    }

    fun getSneakersAddedToCart() {
        viewModelScope.launch(Dispatchers.IO) {
            val res = repository.getSneakersAddedToCart()
            _sneakersFromCart?.postValue(res)
            findSubTotal(res)
        }
    }
     fun findSubTotal(list: List<Sneaker>) {
         var subTotal = 0
         list.forEach {
             subTotal += it.retailPrice
         }
         subTotalLiveData?.postValue(subTotal)
     }

    fun addSneakersDetailsToCart(sneaker: Sneaker) {
        viewModelScope.launch(Dispatchers.IO) {
            val res = repository.addSneakersDetailsToCart(sneaker)
            if (res > 0) {
                insertSuccessOrFailLiveData?.postValue(AppConstant.INSERT_ITEM_SUCCESS)
            } else {
                insertSuccessOrFailLiveData?.postValue(AppConstant.INSERT_ITEM_FAIL)
            }
        }
    }

    fun deleteSneakerItem(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val res = repository.deleteSneakerItem(id)
            if (res > 0) {
                deleteSuccessOrFailLiveData?.postValue(AppConstant.DELETE_ITEM_SUCCESS)
            } else {
                deleteSuccessOrFailLiveData?.postValue(AppConstant.DELETE_ITEM_FAIL)
            }
        }
    }

}