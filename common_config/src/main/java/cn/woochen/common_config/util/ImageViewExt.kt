package cn.woochen.common_config.util

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomViewTarget
import com.bumptech.glide.request.transition.Transition

/**
 *
 * 类描述：
 * 创建人：woochen
 * 创建时间：2020/10/28 3:00 PM
 * 修改备注：
 **/

/**
 * 加载普通图片
 * @param placeholderId 加载中资源图片
 * @param errorId 加载错误资源图片
 * @param transformations 图片变化
 * @see <a href="https://github.com/wasabeef/glide-transformations">transformations</a>
 */
@SuppressLint("CheckResult")
fun ImageView.load(context: Context, url: Any?, @DrawableRes placeholderId: Int? = null, @DrawableRes errorId: Int? = null, transformations: MultiTransformation<Bitmap>? = null) {
    val requestOptions = RequestOptions()
    if (transformations != null) requestOptions.transform(transformations)
    if (placeholderId != null) requestOptions.placeholder(placeholderId)
    if (errorId != null) requestOptions.error(errorId)
    Glide.with(context).load(url).apply(requestOptions).into(this)
}


/**
 * 加载圆型图片
 */
@SuppressLint("CheckResult")
fun ImageView.loadCircle(context: Context, url: Any?, @DrawableRes placeholderId: Int? = null, @DrawableRes errorId: Int? = null) {
    val requestOptions = RequestOptions.circleCropTransform().diskCacheStrategy(DiskCacheStrategy.ALL)
    if (placeholderId != null) requestOptions.placeholder(placeholderId)
    if (errorId != null) requestOptions.error(errorId)
    Glide.with(context).load(url).apply(requestOptions).into(this)
}

/**
 * 加载圆角图片
 * @param angleValue 弧度(dp)
 */
@SuppressLint("CheckResult")
fun ImageView.loadRec(context: Context, url: Any?, angleValue: Float, @DrawableRes placeholderId: Int? = null, @DrawableRes errorId: Int? = null) {
    val roundedCorners = RoundedCorners(angleValue.dp2px())
    val requestOptions = RequestOptions.bitmapTransform(roundedCorners)
    if (placeholderId != null) requestOptions.placeholder(placeholderId)
    if (errorId != null) requestOptions.error(errorId)
    Glide.with(context).load(url).apply(requestOptions).into(this)
}

