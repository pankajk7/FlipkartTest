package com.pankaj.flipkart

import androidx.lifecycle.LiveData

interface ApiContract {

    fun fetchList(): LiveData<List<Pictionary>>
}