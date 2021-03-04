package com.assingment.android.model

class ScoreRepository {

    private val scores = HashMap<String, Int>()

    fun saveScore(name: String, score: Int) {
        scores[name] = score
    }

    fun getScores(): HashMap<String, Int> {
        return scores
    }
}
