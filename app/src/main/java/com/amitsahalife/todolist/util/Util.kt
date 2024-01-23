package com.amitsahalife.todolist.util

import android.graphics.Color
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.core.content.getSystemService
import androidx.fragment.app.Fragment
import com.google.firebase.database.core.Context

object Util  {

    fun statusBarUtil(f: Fragment, colorId:Int){
       val activity = f.requireActivity()
val window: Window = activity.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor= activity.resources.getColor(colorId,activity.theme)
        window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
          //  requireActivity().window.statusBarColor = Color.parseColor("#cdbe32")
            //  window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            //window.statusBarColor = Color.BLUE

        }


    fun hapticFeedback ( context: android.content.Context) {
        val vib = context.getSystemService(android.content.Context.VIBRATOR_SERVICE) as Vibrator?
        if (vib!=null && vib.hasVibrator()){
           // vib.vibrate(VibrationEffect.createOneShot(50,VibrationEffect.DEFAULT_AMPLITUDE))
            vib.vibrate(50)
        }
        else{

        }
    }

}