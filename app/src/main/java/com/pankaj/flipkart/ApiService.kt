package com.pankaj.flipkart

import androidx.lifecycle.LiveData

class ApiService : ApiContract {

    override fun fetchList(): LiveData<List<Pictionary>> {
        return Utils.getListLiveData()
    }
}