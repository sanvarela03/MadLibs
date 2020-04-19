package com.example.madlibs

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_fill_words.*
import java.util.*
import kotlin.collections.ArrayList


class FillWords : AppCompatActivity() {

    private var listOfTokens = ArrayList<String>()
    private var listaPalabras = ArrayList<String>()
    private var count = 0
    private var indexRandom = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fill_words)

        indexRandom = (0..4).random()
        listOfTokens = findTokens(readRandomStoryFile())

        Log.d("wtf!","${listOfTokens.toString()}")

        the_word_to_fill.hint = listOfTokens.get(count)
        the_words_left.setText("${listOfTokens.size} word(s) left")
        ok_button.setOnClickListener { okButtonClick() }
    }

    fun okButtonClick() {

        val word = the_word_to_fill.text.toString()

        listaPalabras.add(word)

        count++

        the_word_to_fill.setText("")

        if (count != listOfTokens.size) {
            the_word_to_fill.hint = listOfTokens.get(count)
            the_words_left.setText("${listOfTokens.size-count} word(s) left")
        } else {
            val myIntent = Intent(this, theMadLabStory::class.java)
            myIntent.putExtra("historia", getStoryWithFormat())
            startActivity(myIntent)
            finish()
        }
    }


    private fun readRandomStoryFile(): String {

        lateinit var reader:Scanner

        if (indexRandom == 0){
            reader = Scanner(resources.openRawResource(R.raw.madlib0_simple))

        }else if (indexRandom == 1){
            reader = Scanner(resources.openRawResource(R.raw.madlib1_tarzan))

        }else if (indexRandom == 2){
            reader = Scanner(resources.openRawResource(R.raw.madlib2_university))

        }else if (indexRandom == 3){
            reader = Scanner(resources.openRawResource(R.raw.madlib3_clothes))

        }else if (indexRandom == 4){
            reader = Scanner(resources.openRawResource(R.raw.madlib4_dance))
        }

        var content: String = ""
        while (reader.hasNextLine()) {
            val line = reader.nextLine()
            content = content + line + "\n"
        }

        return content
    }

    fun getStoryWithFormat(): String {
        val parts = readRandomStoryFile().replace("\n"," ").split(" ").toMutableList()

        var j = 0
        Log.d("partes","${parts.toString()}")

        for (i in parts.indices) {
            if (j != listOfTokens.size && parts[i].equals("<" + listOfTokens.get(j) + ">")) {
                parts[i] = "<b>" + listaPalabras.get(j++) + "</b>"
                Log.d("now","parte = ${parts[i]}")
            }
        }

        var story: String = ""

        for (i in parts.indices) {
            story = story + parts[i] + " "
        }

        return story
    }


    fun findTokens(content: String): ArrayList<String> {
        val pices = content.toCharArray()
        var b = false
        var token = ""
        val listOfTokens = ArrayList<String>()
        for (i in pices.indices) {

            if (b) {
                if (pices[i] != '>') {
                    token = token + pices[i]
                } else {
                    listOfTokens.add(token)
                    b = false
                    token = ""
                }
            }
            if (pices[i] == '<') {
                b = true
            }

        }
        return listOfTokens
    }
}
