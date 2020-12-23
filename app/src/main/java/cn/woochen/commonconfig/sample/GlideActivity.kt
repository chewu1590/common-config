package cn.woochen.commonconfig.sample

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import cn.woochen.commonconfig.R
import cn.woochen.commonconfig.glide.ProgressInterceptor
import cn.woochen.commonconfig.glide.ProgressListener
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.CustomViewTarget
import com.bumptech.glide.request.target.DrawableImageViewTarget
import com.bumptech.glide.request.transition.Transition
import kotlinx.android.synthetic.main.activity_glide.*
import java.security.Signature


class GlideActivity : AppCompatActivity() {

    private  val mUrl = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1600335262440&di=abef87f7bdf926aca904cf86be247587&imgtype=0&src=http%3A%2F%2Fimg1.juimg.com%2F170312%2F330836-1F3120S52126.jpg"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_glide)
        initView()
    }

    private fun initView() {
        ProgressInterceptor.addListener(mUrl,object : ProgressListener {
            override fun onProgress(progress: Int) {
                runOnUiThread {
                    tv_progress.text = "${progress}%"
                }
            }
        })
        val viewTarget =object : DrawableImageViewTarget(iv_img){
            override fun onLoadStarted(placeholder: Drawable?) {
                super.onLoadStarted(placeholder)
                tv_progress.visibility = View.VISIBLE
            }

            override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                super.onResourceReady(resource, transition)
                tv_progress.visibility = View.GONE
                ProgressInterceptor.removeListener(mUrl)
            }
        }
        Glide.with(this)
            .load(mUrl)
            //.diskCacheStrategy(DiskCacheStrategy.NONE)
            .into(viewTarget)
    }
}