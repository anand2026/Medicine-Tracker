package com.invictus.pillstracker.utils.displayUnitConverter


import android.content.res.Resources
import androidx.compose.runtime.Stable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

object UnitConverter {

    private val widthDp : Dp = Resources.getSystem().displayMetrics.run { widthPixels / density }.dp

    //private var widthDp : Dp = 5f.dp

    @Stable
    inline val Int.DP: Dp get() = Dp(value = getDP(this))
    @Stable
    inline val Float.DP: Dp get() = Dp(value = getDP(this))
    @Stable
    inline val Int.SP: TextUnit get() = getSP(this).sp
    @Stable
    inline val Float.SP: TextUnit get() = getSP(this).sp


    /*@JvmName("setWidthDp1")
    fun setWidthDp(wd: Int){
        widthDp = wd.dp
    }*/

    fun getDP(dp: Int): Float{
        val width = (widthDp.value / 412f)
        return  (dp * width)
    }
    fun getDP(dp: Float): Float{
        val width = (widthDp.value / 412f)
        return  (dp * width)
    }

    fun getSP(sp: Int): Float {
        val width = widthDp / 412f
        return (sp * width.value)
    }
    fun getSP(sp: Float): Float {
        val width = widthDp / 412f
        return (sp * width.value)
    }

}