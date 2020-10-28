package cn.woochen.common_config.util


/**
 * dp转px
 */
fun Float.dp2px(): Int {
    val scale = UiUtils.context.resources.displayMetrics.density
    return (this * scale + 0.5f).toInt()
}


/**
 * px转dp
 */
fun Float.px2dp(): Float {
    val scale = UiUtils.context.resources.displayMetrics.density
    return (this / scale + 0.5f).toInt().toFloat()
}

/**
 * sp转px
 */
fun Float.sp2px(): Int {
    val fontScale = UiUtils.context.resources.displayMetrics.scaledDensity
    return (this * fontScale + 0.5f).toInt()
}

/**
 * px转sp
 */
fun Float.px2sp(): Float {
    val fontScale = UiUtils.context.resources.displayMetrics.scaledDensity
    return (this / fontScale + 0.5f).toInt().toFloat()
}