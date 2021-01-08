package wonyong.by.moviezoa

import android.content.Context
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.WindowManager

class DisplayInfo(var context: Context) {
    var dm = DisplayMetrics()
    val deviceWidthMM: Float
        get() = dm.widthPixels / TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_MM, 1f, dm)
    val deviceDPI: Float
        get() = dm.xdpi
    val deviceWidthPixel: Int
        get() = dm.widthPixels
    val deviceWidthDp: Int
        get() = (dm.widthPixels / (dm.xdpi / 160)).toInt()
    val deiveHeightDp: Int
        get() = (dm.heightPixels / (dm.ydpi / 160)).toInt()
    val deiveHeightPixel: Int
        get() = dm.heightPixels

    //dp = pixel/(dpi/160)
    //pixel = dp*(dpi/160)
    fun getDpfromPixel(pixel: Int): Int {
        return (pixel.toFloat() / dm.xdpi).toInt()
    }

    fun getPixelfromDp(dp: Int): Int {
        return (dp.toFloat() * (dm.xdpi / 160)).toInt()
    }

    init {
        (context.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay.getMetrics(dm)
    }
}