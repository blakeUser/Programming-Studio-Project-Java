package com.example.udemyshopping.textView
import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

class BoldTextView(context: Context, attributes: AttributeSet)
    : AppCompatTextView(context, attributes) {

    init {
        applyFont()
    }

    private fun applyFont() {
        val boldTypeface : Typeface =
            Typeface.createFromAsset(context.assets,"Montserrat-Bold.ttf")  //如果把这个assets文件夹改名会怎样
        typeface = boldTypeface

    }

}