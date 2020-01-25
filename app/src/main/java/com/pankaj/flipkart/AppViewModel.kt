package com.pankaj.flipkart

import android.os.SystemClock
import android.util.Log
import androidx.lifecycle.*
import java.util.*
import kotlin.collections.HashMap

class AppViewModel(private val repo: AppRepository) : ViewModel() {

    private var roundCount = 1
    private val totalRound = 5
    private var totalScore = 0
    private var nextDifficulty = 3 // Default is 3
    private var answer = ""
    private val ONE_SECOND = 1000
    private val timeLimit = 31
    private val map = HashMap<Int, MutableList<Pictionary>>()
    private val mediatorLiveData = MediatorLiveData<List<Pictionary>>()
    private val questionLiveData = MutableLiveData<Pictionary>()
    private val endOfRoundLiveData = MutableLiveData<Int>()
    private val mElapsedTime = MutableLiveData<Long>()
    private lateinit var timer: Timer

    val liveData: LiveData<List<Pictionary>>
        get() = mediatorLiveData

    val quesData: LiveData<Pictionary>
        get() = questionLiveData

    val endRound: LiveData<Int>
        get() = endOfRoundLiveData

    val elapsedTime: LiveData<Long>
        get() = mElapsedTime

    fun getList() {
        val liveData = repo.fetchList()
        mediatorLiveData.addSource(liveData) {
            createMap(it)
            totalScore = 0
            nextDifficulty = 3
            roundCount = 1
            mediatorLiveData.postValue(it)
            mediatorLiveData.removeSource(liveData)
        }
    }

    private fun createMap(list: List<Pictionary>) {
        list.forEach {
            addToList(it.difficulty, it)
        }
    }

    private fun addToList(key: Int, obj: Pictionary) {
        val list: MutableList<Pictionary>? = if (map.containsKey(key)) {
            map[key]
        } else {
            mutableListOf()
        }
        list?.let {
            it.add(obj)
            map.put(key, it)
        }
    }

    fun getQuestionBasedOnDifficulty() {
        roundCount++
        val list = map[nextDifficulty]
        list?.let {
            val obj = getObject(it)
            if (!obj.isShown) {
                obj.isShown = true
                map.put(nextDifficulty, list)
                answer = obj.answer
                questionLiveData.postValue(obj)
                return
            }
        }
    }

    fun getObject(list: List<Pictionary>): Pictionary {
        val randomValue = (0..4).random()
        val obj = list[randomValue]
        if (obj.isShown) getObject(list)
        Log.d("Answer Object", "${obj.id}")
        return obj
    }

    fun checkForNextRoundAndUpdateDifficulty(userAnswer: String) {
        if (userAnswer.equals(answer, true)) {
            totalScore += 1
            nextDifficulty += 1
            Log.d("Correct Answer", "$totalScore , $nextDifficulty, $roundCount")
        } else {
            totalScore -= 1
            nextDifficulty -= 1
            Log.d("Wrong Answer", "$totalScore , $nextDifficulty, $roundCount")
        }
        if (nextDifficulty >=5)
            nextDifficulty = 5
        if (roundCount == totalRound || nextDifficulty == 0) {
            cancelTimer()
            endOfRoundLiveData.postValue(totalScore)
            return
        }
        getQuestionBasedOnDifficulty()
    }

    fun startTimer() {
        cancelTimer()
        val mInitialTime = SystemClock.elapsedRealtime()
        timer = Timer()

        // Update the elapsed time every second.
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                val newValue = (SystemClock.elapsedRealtime() - mInitialTime) / 1000
                // setValue() cannot be called from a background thread so post to main thread.
                mElapsedTime.postValue(timeLimit - newValue)
            }
        }, ONE_SECOND.toLong(), ONE_SECOND.toLong())
    }

    fun cancelTimer() {
        if (::timer.isInitialized) {
            timer.cancel()
            timer.purge()
        }
    }

    override fun onCleared() {
        cancelTimer()
        super.onCleared()
    }

    class AppViewModelFactory(private val repo: AppRepository) :
        ViewModelProvider.NewInstanceFactory() {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return AppViewModel(repo) as T
        }
    }
}