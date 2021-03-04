package com.assingment.android.model

class ScoreRepository {

    private val scores = HashMap<String, Int>()

    fun saveScore(name: String, score: Int) {
        scores[name] = score
    }

    fun getScore(name: String): Int? {
        return scores[name]
    }
}
