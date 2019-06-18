package com.woofy.ui.base

import android.content.Context
import android.content.DialogInterface
import android.support.annotation.ColorRes
import android.support.annotation.StringRes
import android.support.annotation.StyleRes
import android.support.v4.content.res.ResourcesCompat
import android.text.SpannableString
import android.text.TextUtils
import android.widget.Button
import com.woofy.R

abstract class BaseAlertDialog : android.app.AlertDialog {

    protected constructor(context: Context) : super(context)

    protected constructor(
        context: Context,
        cancelable: Boolean,
        cancelListener: DialogInterface.OnCancelListener
    ) : super(context, cancelable, cancelListener)

    protected constructor(context: Context, @StyleRes themeResId: Int) : super(context, themeResId)

    class Builder : android.app.AlertDialog.Builder {

        private var buttonColor = -1

        constructor(context: Context) : super(context)

        constructor(context: Context, @ColorRes buttonColor: Int) : super(context) {
            this.buttonColor = buttonColor
        }

        override fun setTitle(title: CharSequence): android.app.AlertDialog.Builder {
            val string = SpannableString(title)
            return super.setTitle(string)
        }

        override fun setTitle(@StringRes titleId: Int): android.app.AlertDialog.Builder {
            val title = context.getString(titleId)
            return setTitle(title)
        }

        override fun setMessage(message: CharSequence?): android.app.AlertDialog.Builder {
            if (TextUtils.isEmpty(message)) {
                return super.setMessage("")
            }
            val string = SpannableString(message)
            return super.setMessage(string)
        }

        override fun setMessage(@StringRes messageId: Int): android.app.AlertDialog.Builder {
            val message = context.getString(messageId)
            return setMessage(message)
        }

        override fun show(): android.app.AlertDialog {
            val alertDialog = create()
            alertDialog.setOnShowListener {
                val color = ResourcesCompat.getColor(
                    context.resources,
                    if (buttonColor == -1) R.color.colorAccent else buttonColor,
                    context.theme
                )
                var button: Button?
                val buttons = intArrayOf(
                    DialogInterface.BUTTON_NEGATIVE,
                    DialogInterface.BUTTON_NEUTRAL,
                    DialogInterface.BUTTON_POSITIVE
                )
                for (buttonType in buttons) {
                    button = alertDialog.getButton(buttonType)
                    button?.setTextColor(color)
                }
            }
            alertDialog.show()
            return alertDialog
        }
    }
}