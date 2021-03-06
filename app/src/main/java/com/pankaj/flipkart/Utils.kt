package com.pankaj.flipkart

import android.content.Context
import com.google.gson.reflect.TypeToken
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.net.ConnectivityManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson


object Utils {

    val json = "[ { \"id\": 1, \"imageUrl\": \"https://acdn.list25.com/wp-content/uploads/2015/03/australian-cattle-dog.jpg\", \"difficulty\": 1, \"answer\": \"DOG\" }, { \"id\": 2, \"imageUrl\": \"https://acdn.list25.com/wp-content/uploads/2015/03/Tortoise-shell-cat.jpg\", \"difficulty\": 1, \"answer\": \"CAT\" }, { \"id\": 3, \"imageUrl\": \"https://acdn.list25.com/wp-content/uploads/2015/03/horse.jpg\", \"difficulty\": 1, \"answer\": \"HORSE\" }, { \"id\": 4, \"imageUrl\": \"https://acdn.list25.com/wp-content/uploads/2015/03/poltava-chicken.jpg\", \"difficulty\": 1, \"answer\": \"HEN\" }, { \"id\": 5, \"imageUrl\": \"https://acdn.list25.com/wp-content/uploads/2015/03/largemouth-bass.jpg\", \"difficulty\": 1, \"answer\": \"FISH\" }, { \"id\": 6, \"imageUrl\": \"https://acdn.list25.com/wp-content/uploads/2015/03/eurasian-brown-bear.jpg\", \"difficulty\": 2, \"answer\": \"BEAR\" }, { \"id\": 7, \"imageUrl\": \"https://acdn.list25.com/wp-content/uploads/2015/03/bluebird-of-happiness.jpg\", \"difficulty\": 2, \"answer\": \"BIRD\" }, { \"id\": 8, \"imageUrl\": \"https://acdn.list25.com/wp-content/uploads/2015/03/hammerhead-shark.jpg\", \"difficulty\": 2, \"answer\": \"SHARK\" }, { \"id\": 9, \"imageUrl\": \"https://acdn.list25.com/wp-content/uploads/2015/03/mad-purple-snake.jpg\", \"difficulty\": 2, \"answer\": \"SNAKE\" }, { \"id\": 10, \"imageUrl\": \"https://acdn.list25.com/wp-content/uploads/2015/03/pig.jpg\", \"difficulty\": 2, \"answer\": \"PIG\" }, { \"id\": 11, \"imageUrl\": \"https://acdn.list25.com/wp-content/uploads/2015/03/male-lion.jpg\", \"difficulty\": 3, \"answer\": \"LION\" }, { \"id\": 12, \"imageUrl\": \"https://acdn.list25.com/wp-content/uploads/2015/03/wild-turkey.jpg\", \"difficulty\": 3, \"answer\": \"TURKEY\" }, { \"id\": 13, \"imageUrl\": \"https://acdn.list25.com/wp-content/uploads/2015/03/gray-wolf.jpg\", \"difficulty\": 3, \"answer\": \"WOLF\" }, { \"id\": 14, \"imageUrl\": \"https://acdn.list25.com/wp-content/uploads/2015/03/zebra-spider.jpg\", \"difficulty\": 3, \"answer\": \"SPIDER\" }, { \"id\": 15, \"imageUrl\": \"https://acdn.list25.com/wp-content/uploads/2015/03/rabbit.jpg\", \"difficulty\": 3, \"answer\": \"RABBIT\" }, { \"id\": 16, \"imageUrl\": \"https://acdn.list25.com/wp-content/uploads/2015/03/male-mandarin-duck.jpg\", \"difficulty\": 4, \"answer\": \"DUCK\" }, { \"id\": 17, \"imageUrl\": \"https://acdn.list25.com/wp-content/uploads/2015/03/red-deer-stag.jpg\", \"difficulty\": 4, \"answer\": \"DEER\" }, { \"id\": 18, \"imageUrl\": \"https://acdn.list25.com/wp-content/uploads/2015/03/cow-and-calf.jpg\", \"difficulty\": 4, \"answer\": \"COW\" }, { \"id\": 19, \"imageUrl\": \"https://acdn.list25.com/wp-content/uploads/2015/03/wolfs-mona-monkey.jpg\", \"difficulty\": 4, \"answer\": \"MONKEY\" }, { \"id\": 20, \"imageUrl\": \"https://acdn.list25.com/wp-content/uploads/2015/03/european-lobster.jpg\", \"difficulty\": 4, \"answer\": \"LOBSTER\" }, { \"id\": 21, \"imageUrl\": \"https://acdn.list25.com/wp-content/uploads/2015/03/ape.jpg\", \"difficulty\": 5, \"answer\": \"APE\" }, { \"id\": 22, \"imageUrl\": \"https://acdn.list25.com/wp-content/uploads/2015/03/shetland-pony.jpg\", \"difficulty\": 5, \"answer\": \"PONY\" }, { \"id\": 23, \"imageUrl\": \"https://acdn.list25.com/wp-content/uploads/2015/03/bald-eagle.jpg\", \"difficulty\": 5, \"answer\": \"EAGLE\" }, { \"id\": 24, \"imageUrl\": \"https://acdn.list25.com/wp-content/uploads/2015/03/spinner-dolphin-pod.jpg\", \"difficulty\": 5, \"answer\": \"DOLPHIN\" }, { \"id\": 25, \"imageUrl\": \"https://acdn.list25.com/wp-content/uploads/2015/03/american-bison.jpg\", \"difficulty\": 5, \"answer\": \"BISON\" } ]"

    fun getListLiveData(): LiveData<List<Pictionary>> {
        val type = object: TypeToken<List<Pictionary>>() {}.type
        val list: List<Pictionary> = Gson().fromJson(json, type)
        val liveData = MutableLiveData<List<Pictionary>>()
        liveData.postValue(list)
        return liveData
    }

    fun isInternetConnected(context: Context?): Boolean =
        context?.let {
            try {
                val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                return@let cm.activeNetworkInfo?.isConnectedOrConnecting ?: false
            } catch (e: Exception) {
                return@let false
            }
        } ?: false
}