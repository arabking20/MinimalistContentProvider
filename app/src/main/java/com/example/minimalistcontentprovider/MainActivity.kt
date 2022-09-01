package com.example.minimalistcontentprovider

import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity





class MainActivity : AppCompatActivity() {


    private val TAG = "MainActivity"
    private lateinit var mTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mTextView = findViewById<TextView>(R.id.textview)
        findViewById<Button>(R.id.button_display_all).setOnClickListener(this::onClickDisplayEntries)
        findViewById<Button>(R.id.button_display_first).setOnClickListener(this::onClickDisplayEntries)

    }

    private fun onClickDisplayEntries(view: View) {
        val queryUri: String = CONTENT_URI.toString()
        val projection = arrayOf<String>(CONTENT_PATH)
        val selectionClause: String?
        val selectionArgs: Array<String>?
        val sortOrder: String? = null

        when(view.id) {
            R.id.button_display_all -> {
                selectionClause = null
                selectionArgs = null
            }
            R.id.button_display_first -> {
                selectionClause = "$WORD_ID=?"
                selectionArgs = arrayOf("0")
            }
            else-> {
                selectionClause = null
                selectionArgs = null
            }
        }

        val cursor: Cursor? = contentResolver.query(
            Uri.parse(queryUri), projection, selectionClause,
            selectionArgs, sortOrder
        )

        if (cursor != null ) {
            if (cursor.count > 0 ) {
                cursor.moveToFirst()
                val columnIndex = cursor.getColumnIndex(projection[0])
                do {
                    val word = cursor.getString(columnIndex)
                    mTextView.append(word.trimIndent())
                } while (cursor.moveToNext())
            } else {
                Log.d(TAG, "onClickDisplayEntries: " + " no data returned")
                mTextView.append("No data returned".trimIndent())
            }
            cursor.close()
        } else {
            Log.d(TAG, "onClickDisplayEntries: " + "Cursor is null")
            mTextView.append("Cursor is null".trimIndent())
        }

    }

}