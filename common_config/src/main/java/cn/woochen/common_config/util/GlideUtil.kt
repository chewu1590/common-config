package cn.woochen.common_config.util

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.support.annotation.DrawableRes
import android.widget.ImageView
import cn.woochen.common_config.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

/**
 *
 * 类描述：
 * 创建人：woochen
 * 创建时间：2019-12-21 14:26
 * 修改备注：
 **/
object GlideUtil {


    /**
     * 加载普通图片
     * @param placeholderId 加载中资源图片
     * @param errorId 加载错误资源图片
     * @param transformations 图片变化
     * @see <a href="https://github.com/wasabeef/glide-transformations">transformations</a>
     */
    @SuppressLint("CheckResult")
    fun load(context: Context, url: Any?, imageView: ImageView?, @DrawableRes placeholderId: Int? = 0, @DrawableRes errorId: Int? = 0, transformations: MultiTransformation<Bitmap>? = null) {
        if (imageView == null) return
        val requestOptions = RequestOptions()
        if (transformations != null) requestOptions.transform(transformations)
        if (placeholderId != null) requestOptions.placeholder(placeholderId)
        if (errorId != null) requestOptions.error(errorId)
        Glide.with(context).load(url).apply(requestOptions).into(imageView)
    }


    /**
     * 加载圆型图片
     */
    @SuppressLint("CheckResult")
    fun loadCircle(context: Context, url: Any?, imageView: ImageView?, @DrawableRes placeholderId: Int? = 0, @DrawableRes errorId: Int? = 0) {
        if (imageView == null) return
        val requestOptions = RequestOptions.circleCropTransform().diskCacheStrategy(DiskCacheStrategy.ALL)
        if (placeholderId != null) requestOptions.placeholder(placeholderId)
        if (errorId != null) requestOptions.error(errorId)
        Glide.with(context).load(url).apply(requestOptions).into(imageView)
    }

    /**
     * 加载圆角图片
     * @param angleValue 弧度(dp)
     */
    @SuppressLint("CheckResult")
    fun loadRec(context: Context, url: Any?, imageView: ImageView?, angleValue: Float, @DrawableRes placeholderId: Int? = 0, @DrawableRes errorId: Int? = 0) {
        if (imageView == null) return
        val roundedCorners = RoundedCorners(UiUtils.dp2px(context, angleValue))
        val requestOptions = RequestOptions.bitmapTransform(roundedCorners)
        if (placeholderId != null) requestOptions.placeholder(placeholderId)
        if (errorId != null) requestOptions.error(errorId)
        Glide.with(context).load(url).apply(requestOptions).into(imageView)
    }

}

