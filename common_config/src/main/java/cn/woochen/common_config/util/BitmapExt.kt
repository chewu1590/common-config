package cn.woochen.common_config.util

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

/**
 *
 * 类描述：
 * 创建人：woochen
 * 创建时间：2020/10/28 3:12 PM
 * 修改备注：
 **/

/**
 * 把当前bitmap保存到指定路径
 * @param storePath 存储路径
 */
fun Bitmap.saveImageToGallery(context: Context,storePath:String = Environment.getExternalStorageDirectory().absolutePath + File.separator + "xuanshangke"): Boolean {
    val appDir = File(storePath)
    if (!appDir.exists()) {
        appDir.mkdir()
    }
    val fileName = System.currentTimeMillis().toString() + ".jpg"
    val file = File(appDir, fileName)
    try {
        val fos = FileOutputStream(file)
        //通过io流的方式来压缩保存图片
        val isSuccess = this.compress(Bitmap.CompressFormat.JPEG, 60, fos)
        fos.flush()
        fos.close()
        //把文件插入到系统图库
        //MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), fileName, null);
        //保存图片后发送广播通知更新数据库
        val uri = Uri.fromFile(file)
        context.sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri))
        return isSuccess
    } catch (e: IOException) {
        e.printStackTrace()
    }

    return false
}