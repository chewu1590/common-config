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
     */
    @SuppressLint("CheckResult")
    fun load(context: Context, url: Any?, imageView: ImageView, @DrawableRes placeholderId:Int = R.drawable.ic_default_pic, @DrawableRes  errorId:Int = R.drawable.ic_default_pic, transformations:MultiTransformation<Bitmap>?= null) {
        val requestOptions = RequestOptions()
        if (transformations != null) requestOptions.transform(transformations)
        requestOptions
            .placeholder(placeholderId)
            .error(errorId)
        Glide.with(context)
            .load(url)
            .apply(requestOptions)
            .into(imageView)
    }



    /**
     * 加载圆型图片
     */
    @SuppressLint("CheckResult")
    fun loadCircle(context: Context, url: Any?, imageView: ImageView, @DrawableRes  placeholderId:Int = R.drawable.ic_default_pic, @DrawableRes  errorId:Int = R.drawable.ic_default_pic) {
        val requestOptions = RequestOptions.circleCropTransform().diskCacheStrategy(DiskCacheStrategy.ALL)
        requestOptions.placeholder(placeholderId).error(errorId)
        Glide.with(context)
            .load(url)
            .apply(requestOptions)
            .into(imageView)
    }

    /**
     * 加载圆角图片
     * @param angleValue 弧度(dp)
     */
    @SuppressLint("CheckResult")
    fun loadRec(context: Context, url: Any?, imageView: ImageView, angleValue: Float, @DrawableRes placeholderId:Int = R.drawable.ic_default_pic, @DrawableRes  errorId:Int = R.drawable.ic_default_pic) {
        val roundedCorners = RoundedCorners(UiUtils.dp2px(context, angleValue))
        val options = RequestOptions.bitmapTransform(roundedCorners)
        options.placeholder(placeholderId).error(errorId)
        Glide.with(context)
            .load(url)
            .apply(options)
            .into(imageView)
    }

}

