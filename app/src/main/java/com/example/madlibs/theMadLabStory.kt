package com.example.madlibs

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import kotlinx.android.synthetic.main.activity_the_mad_lab_story.*
import java.util.*
import kotlin.collections.ArrayList

class theMadLabStory : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_the_mad_lab_story)


        val extra = intent.getStringExtra("historia")

        the_story.setText(Html.fromHtml(extra,0))

        another_history_button.setOnClickListener { anotherHistoryClick() }

    }

    fun anotherHistoryClick(){
        val myIntent = Intent(this, MainActivity ::class.java)
        startActivity(myIntent)
    }


}
