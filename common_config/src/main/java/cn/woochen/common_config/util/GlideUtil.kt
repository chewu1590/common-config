package cn.woochen.common_config.util

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

/**
 * 图片加载工具类(可通过继承进行扩展)
 * @author woochen
 * @time 2018/8/23 15:21
 */

open class GlideUtil {

    companion object {

        /**
         * 加载普通图片
         */
        fun load(context: Context, url: Any?, imageView: ImageView) {
            Glide.with(context)
                .load(url)
                .into(imageView)
        }

        /**
         * 加载圆型图片
         */
        fun loadCircle(context: Context, url: Any?, imageView: ImageView) {
            val mRequestOptions = RequestOptions.circleCropTransform().diskCacheStrategy(DiskCacheStrategy.ALL)
            Glide.with(context)
                .load(url)
                .apply(mRequestOptions)
                .into(imageView)
        }

        /**
         * 加载圆角图片
         * @param angleValue 弧度(dp)
         */
        fun loadRec(context: Context, url: Any?, imageView: ImageView, angleValue: Float) {
            val roundedCorners = RoundedCorners(UiUtils.dp2px(context, angleValue))
            val options = RequestOptions.bitmapTransform(roundedCorners)
            Glide.with(context)
                .load(url)
                .apply(options)
                .into(imageView)
        }

    }


}
