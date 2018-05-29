package com.jscoolstar.jscoolstarlibrary.widgets.dialog

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.support.v7.app.AlertDialog
import android.view.View
import com.jscoolstar.jscoolstarlibrary.R

/**
 * Created by Administrator on 2018/4/24.
 */
class JSMaterialDialogUtil {

    private constructor()

    private constructor(context: Context, styleId: Int) {
        mContext = context
        mStyle =
                if (styleId <= 0) MDefaultStyle
                else styleId
    }

    private var mContext: Context? = null

    private val MDefaultStyle = R.style.JSMaterialDialogStyle

    private var mStyle = MDefaultStyle

    private fun BuildDialog(): AlertDialog.Builder {
        return AlertDialog.Builder(mContext!!, mStyle)
    }


    object INSTANCE {
        fun build(context: Context): JSMaterialDialogUtil {
            return JSMaterialDialogUtil(context, 0)
        }

        fun build(context: Context, styleId: Int): JSMaterialDialogUtil {
            return JSMaterialDialogUtil(context, styleId)
        }
    }

    private fun preCheck(): Boolean {
        if (mContext == null) {
            return false
        }
        if (mContext is Activity) {
            if ((mContext as Activity).isFinishing) {
                return false
            }
        }
        return true
    }

    fun showDialog(title: String,v:View?, message: String, cancelText: String, confirmText: String, dialogClickListerner: JSMaterialDialogClickListerner?): AlertDialog? {
        if (!preCheck()) return null
        var builder = BuildDialog()
        builder.setTitle(title)
        if(v!=null){
            builder.setView(v)
        }
        return builder.setMessage(message)
                .setNegativeButton(cancelText, DialogInterface.OnClickListener { dialog, which -> dialogClickListerner?.onCancelClick(dialog, which) }).setPositiveButton(confirmText, DialogInterface.OnClickListener { dialog, which -> dialogClickListerner?.onConfirmClick(dialog, which) })
                .show()
    }

}