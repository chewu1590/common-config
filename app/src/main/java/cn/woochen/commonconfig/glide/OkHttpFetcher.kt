package cn.woochen.commonconfig.glide

import com.bumptech.glide.Priority
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.data.DataFetcher
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.util.ContentLengthInputStream
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.ResponseBody
import java.io.IOException
import java.io.InputStream

class OkHttpFetcher(private val okHttpClient: OkHttpClient, private val glideUrl: GlideUrl) : DataFetcher<InputStream?> {

    @Volatile
    private var isCancelled = false
    private var stream: InputStream? = null
    private var responseBody: ResponseBody? = null
    override fun loadData(priority: Priority, callback: DataFetcher.DataCallback<in InputStream?>) {
        val builder = Request.Builder().url(glideUrl.toStringUrl())
        for ((key, value) in glideUrl.headers) {
            builder.addHeader(key, value)
        }
        val request = builder.build()
        if (isCancelled) {
            callback.onDataReady(null)
            return
        }
        try {
            val response = okHttpClient.newCall(request).execute()
            //            responseBody = new ProgressResponseBody(response.body(),glideUrl.toStringUrl());
            responseBody = response.body()
            if (!response.isSuccessful || responseBody == null) {
                throw IOException("Request failed with code: " + response.code())
            }
            stream = ContentLengthInputStream.obtain(responseBody!!.byteStream(), responseBody?.contentLength()!!)
            callback.onDataReady(stream)
            //            cleanup();
        } catch (e: IOException) {
            e.printStackTrace()
            callback.onLoadFailed(e)
        }
    }

    override fun cleanup() {
        try {
            if (stream != null) {
                stream?.close()
            }
            if (responseBody != null) {
                responseBody?.close()
            }
        } catch (e: Exception) {
        }
    }

    override fun cancel() {
        isCancelled = true
    }

    override fun getDataClass(): Class<InputStream?> {
        return InputStream::class.java as Class<InputStream?>
    }

    override fun getDataSource(): DataSource {
        return DataSource.REMOTE
    }

}