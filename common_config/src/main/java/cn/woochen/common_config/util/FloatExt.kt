package cn.woochen.common_config.util

import android.content.res.Resources
import android.util.TypedValue


/**
 * dp转px
 */
val Float.dp
    get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,this,Resources.getSystem().displayMetrics)

/**
 * sp转px
 */
val Float.sp
    get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,this,Resources.getSystem().displayMetrics)
