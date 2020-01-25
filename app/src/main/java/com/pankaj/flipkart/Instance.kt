package com.pankaj.flipkart

object Instance {

    private var appRepository: AppRepository? = null

    fun getAppRepository(): AppRepository {
        appRepository?.let {
            return it
        }

        return AppRepository(ApiService()).also {
            appRepository = it
        }
    }
}