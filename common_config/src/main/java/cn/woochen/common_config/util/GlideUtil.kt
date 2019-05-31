package cn.woochen.common_config.util

import android.annotation.SuppressLint
import android.content.Context
import android.support.annotation.DrawableRes
import android.widget.ImageView
import cn.woochen.common_config.R
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
        @SuppressLint("CheckResult")
        fun load(context: Context, url: Any?, imageView: ImageView) {
            val requestOptions = RequestOptions()
            requestOptions.placeholder(R.drawable.ic_default_pic).error(R.drawable.ic_default_pic)
            Glide.with(context)
                .load(url)
                .apply(requestOptions)
                .into(imageView)
        }

        /**
         * 加载普通图片
         */
        @SuppressLint("CheckResult")
        fun load(context: Context, url: Any?, imageView: ImageView, @DrawableRes  placeholderId:Int, @DrawableRes  errorId:Int) {
            val requestOptions = RequestOptions()
            requestOptions.placeholder(placeholderId).error(errorId)
            Glide.with(context)
                .load(url)
                .apply(requestOptions)
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
         * 加载圆型图片
         */
        @SuppressLint("CheckResult")
        fun loadCircle(context: Context, url: Any?, imageView: ImageView, @DrawableRes  placeholderId:Int, @DrawableRes  errorId:Int) {
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
        fun loadRec(context: Context, url: Any?, imageView: ImageView, angleValue: Float) {
            val roundedCorners = RoundedCorners(UiUtils.dp2px(context, angleValue))
            val options = RequestOptions.bitmapTransform(roundedCorners)
            Glide.with(context)
                .load(url)
                .apply(options)
                .into(imageView)
        }

        /**
         * 加载圆角图片
         * @param angleValue 弧度(dp)
         */
        @SuppressLint("CheckResult")
        fun loadRec(context: Context, url: Any?, imageView: ImageView, angleValue: Float, @DrawableRes  placeholderId:Int, @DrawableRes  errorId:Int) {
            val roundedCorners = RoundedCorners(UiUtils.dp2px(context, angleValue))
            val options = RequestOptions.bitmapTransform(roundedCorners)
            options.placeholder(placeholderId).error(errorId)
            Glide.with(context)
                .load(url)
                .apply(options)
                .into(imageView)
        }

    }


}
