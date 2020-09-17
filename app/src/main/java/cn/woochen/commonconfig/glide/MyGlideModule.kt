package cn.woochen.commonconfig.glide

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.module.AppGlideModule
import okhttp3.OkHttpClient
import java.io.InputStream

/**
 * 类描述：
 * 创建人：woochen
 * 创建时间：2020/9/17 2:07 PM
 * 修改备注：
 */
@GlideModule
class MyGlideModule : AppGlideModule() {

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        super.registerComponents(context, glide, registry)
        val builder = OkHttpClient.Builder()
        builder.addInterceptor(ProgressInterceptor())
        val okHttpClient = builder.build()
        registry.replace(GlideUrl::class.java, InputStream::class.java, OkHttpGlideUrlLoader.Factory(okHttpClient))
    }
}