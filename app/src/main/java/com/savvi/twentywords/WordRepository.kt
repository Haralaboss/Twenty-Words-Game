package com.savvi.twentywords

import android.content.Context
import com.google.gson.Gson

object WordRepository {

    private var cachedWords: Map<String, List<String>>? = null

    fun load(context: Context) {
        if (cachedWords != null) return // already loaded
        val json = context.assets.open("words.json")
            .bufferedReader()
            .use { it.readText() }


        val root = Gson().fromJson(json, AllWords::class.java)
        cachedWords = root.categories
    }

    fun getRandomWords(
        count: Int,
        categories: List<String>? = null
    ): List<String> {

        val wordsMap = cachedWords ?: return emptyList()

        val pool = if (categories == null) {
            wordsMap.values.flatten()
        } else {
            categories.flatMap { wordsMap[it].orEmpty() }
        }
        return pool.shuffled().take(count)
    }
}