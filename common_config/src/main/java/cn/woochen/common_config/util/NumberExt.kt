package cn.woochen.common_config.util

import android.content.res.Resources
import android.util.TypedValue


/**
 * dp转px(float)
 */
val Float.dp
    get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,this,Resources.getSystem().displayMetrics)

/**
 * dp转px(int)
 */
val Int.dp
    get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,this.toFloat(),Resources.getSystem().displayMetrics)

/**
 * sp转px
 */
val Float.sp
    get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,this,Resources.getSystem().displayMetrics)
