package cn.woochen.commonconfig.glide

import android.util.Log
import okhttp3.MediaType
import okhttp3.ResponseBody
import okio.*
import java.io.IOException

/**
 * 类描述：
 * 创建人：woochen
 * 创建时间：2020/9/17 2:05 PM
 * 修改备注：
 */
class ProgressResponseBody(private val responseBody: ResponseBody, private val url: String) : ResponseBody() {
    private var bufferedSource: BufferedSource? = null
    private var listener: ProgressListener?
    override fun contentType(): MediaType? {
        return responseBody.contentType()
    }

    override fun contentLength(): Long {
        return responseBody.contentLength()
    }

    override fun source(): BufferedSource {
        if (bufferedSource == null) {
            bufferedSource = Okio.buffer(ProgressSource(responseBody.source()))
        }
        return bufferedSource!!
    }

    private inner class ProgressSource internal constructor(source: Source?) : ForwardingSource(source) {
        var totalBytesRead: Long = 0
        var currentProgress = 0

        @Throws(IOException::class)
        override fun read(sink: Buffer, byteCount: Long): Long {
            val bytesRead = super.read(sink, byteCount)
            val fullLength = responseBody.contentLength()
            if (bytesRead == -1L) {
                totalBytesRead = fullLength
            } else {
                totalBytesRead += bytesRead
            }
            val progress = (100f * totalBytesRead / fullLength).toInt()
            Log.i("imageview","progress:$progress")
            if (listener != null && progress != currentProgress) {
                listener!!.onProgress(progress)
            }
            if (listener != null && totalBytesRead == fullLength) {
                listener = null
            }
            currentProgress = progress
            return bytesRead
        }
    }

    init {
        listener = ProgressInterceptor.LISTENER_MAP[url]
    }
}