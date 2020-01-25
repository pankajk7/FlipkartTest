package com.pankaj.flipkart

import androidx.lifecycle.LiveData

open class AppRepository (val apiContract: ApiContract) {

    fun fetchList(): LiveData<List<Pictionary>> {
        return apiContract.fetchList()
    }
}