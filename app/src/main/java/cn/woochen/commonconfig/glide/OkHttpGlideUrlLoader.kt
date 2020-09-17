package cn.woochen.commonconfig.glide

import com.bumptech.glide.load.Option
import com.bumptech.glide.load.Options
import com.bumptech.glide.load.model.*
import com.bumptech.glide.load.model.ModelLoader.LoadData
import okhttp3.OkHttpClient
import java.io.InputStream

/**
 * 类描述：
 * 创建人：woochen
 * 创建时间：2020/9/17 2:06 PM
 * 修改备注：
 */
class OkHttpGlideUrlLoader(private val modelCache: ModelCache<GlideUrl, GlideUrl>?, private val okHttpClient: OkHttpClient) :
    ModelLoader<GlideUrl, InputStream> {
    override fun buildLoadData(model: GlideUrl, width: Int, height: Int, options: Options): LoadData<InputStream>? {
        var url: GlideUrl? = model
        if (modelCache != null) {
            url = modelCache[model, 0, 0]
            if (url == null) {
                modelCache.put(model, 0, 0, model)
                url = model
            }
        }
        val timeout = options.get(TIMEOUT)!!
        return LoadData<InputStream>(url!!, OkHttpFetcher(okHttpClient, url))
    }

    override fun handles(glideUrl: GlideUrl): Boolean {
        return true
    }

    class Factory : ModelLoaderFactory<GlideUrl, InputStream> {
        private val modelCache = ModelCache<GlideUrl, GlideUrl>(500)
        private var client: OkHttpClient? = null

        constructor() {}
        constructor(client: OkHttpClient?) {
            this.client = client
        }

        @Synchronized
        private fun getOkHttpClient(): OkHttpClient {
            if (client == null) {
                client = OkHttpClient()
            }
            return client!!
        }

        override fun build(multiFactory: MultiModelLoaderFactory): ModelLoader<GlideUrl, InputStream> {
            return OkHttpGlideUrlLoader(modelCache, getOkHttpClient())
        }

        override fun teardown() {
            // Do nothing.
        }
    }

    companion object {
        val TIMEOUT = Option.memory("com.bumptech.glide.load.model.stream.HttpGlideUrlLoader.Timeout", 2500)
    }

}