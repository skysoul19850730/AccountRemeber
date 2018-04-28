package com.jscoolstar.jscoolstarlibrary.widgets.dialog

import android.content.DialogInterface

/**
 * Created by Administrator on 2018/4/24.
 */
interface JSMaterialDialogClickListerner {
    fun onCancelClick(dialogInterface: DialogInterface,which:Int)
    fun onConfirmClick(dialogInterface: DialogInterface,which:Int)
}