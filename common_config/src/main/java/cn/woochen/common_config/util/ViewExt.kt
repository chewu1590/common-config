package cn.woochen.common_config.util

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.View
import androidx.annotation.DrawableRes
import cn.woochen.common_config.helper.OnSingleClickListener
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomViewTarget
import com.bumptech.glide.request.transition.Transition
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

/**
 * 单机事件(默认间隔为1秒)
 */
@SuppressLint("CheckResult")
fun View.setOnSingleClickListener(singleClickListener: OnSingleClickListener?, timeSecond:Long = 1){
    RxView.clicks(this)
        .throttleFirst(timeSecond, TimeUnit.SECONDS)
        .subscribeOn(AndroidSchedulers.mainThread())
        .subscribe {
            singleClickListener?.onSingleClick(this)
        }
}

/**
 * 视图可见
 */
fun View.visible(){
    this.visibility = View.VISIBLE
}

/**
 * 视图不可见
 */
fun View.gone(){
    this.visibility = View.GONE
}

/**
 * 视图不可见
 */
fun View.invisible(){
    this.visibility = View.INVISIBLE
}

/**
 * 把url加载为view的背景
 */
@SuppressLint("CheckResult")
fun View.loadBackground(context: Context, url: Any?, @DrawableRes placeholderId:Int ? = null, @DrawableRes errorId:Int? = null, transformations: MultiTransformation<Bitmap>?= null) {
    val requestOptions = RequestOptions()
    if (transformations != null) requestOptions.transform(transformations)
    if (placeholderId != null) requestOptions.placeholder(placeholderId)
    if (errorId != null) requestOptions.error(errorId)
    val viewTarget =object : CustomViewTarget<View, Drawable?>(this) {

        override fun onLoadFailed(errorDrawable: Drawable?) {

        }

        override fun onResourceCleared(placeholder: Drawable?) {

        }

        override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable?>?) {
            view.background = resource
        }

    }
    Glide.with(context)
        .load(url)
        .apply(requestOptions)
        .into(viewTarget)
}


/**
 * 把当前view转化为bitmap
 */
fun View.toBitmap(): Bitmap {
    val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val c = Canvas(bmp)
    c.drawColor(Color.WHITE)
    draw(c)
    return bmp
}