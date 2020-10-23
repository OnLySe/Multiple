package com.zzq.saf.utils

import android.net.Uri
import android.util.Log
import androidx.documentfile.provider.DocumentFile
import androidx.fragment.app.Fragment
import com.zzq.common.utils.showToast

/**
 * 创建文件，如果创建失败，即通过[DocumentFile.createFile]创建文件失败，则会返回根目录
 */
fun Fragment.createFile(uri: Uri?, foldName: String, fileName: String, action: (DocumentFile) -> Unit = {}):DocumentFile {
    //返回的路径是：content://com.android.externalstorage.documents/tree/primary%3A这样形式
    Log.e("tetetetete", "obtainFilePath uri: ${uri.toString()}")
    val fromSingleUri = DocumentFile.fromSingleUri(requireContext(), uri!!)
    Log.e("tetetetete", "obtainFilePath DocumentFile uri: ${fromSingleUri!!.name}")

    val fromTreeUri = DocumentFile.fromTreeUri(requireContext(), uri)!!
    //用来标记是否已经存在指定文件夹
    var hasNeedFolder = false
    //标记已经创建文件夹，但是有没有创建文件
    var hasNeedFile = false

    var foldFileUri :DocumentFile? = null
    var childFileUri = fromTreeUri
    fromTreeUri.listFiles().forEach {
        Log.e("tetetetete", "onActivityResult forEach: ${it.name}")
        if (it.name.equals(foldName)) {
            hasNeedFolder = true
            foldFileUri = it
        }
    }
    if (hasNeedFolder) {
        //已经创建文件夹，判断有没有创建文件
        if (!hasNeedFile) {
            //没有文件，可能未创建，或者被删除，那么需要创建文件
            //创建纯文本类型文件
            //createFile返回的正式我们需要的子目录，所以在这里赋值并把值传出去
            childFileUri = foldFileUri!!.createFile("text/plain", fileName)!!
        }
        action(childFileUri)
        return childFileUri
    } else {
        //不存在指定文件夹就创建
        val foldUri = fromTreeUri.createDirectory(foldName)
        if (foldUri == null) {
            showToast("创建子目录${foldName}失败")
        } else {
            //创建纯文本类型文件
            //createFile返回的正式我们需要的子目录，所以在这里赋值并把值传出去
            childFileUri = foldUri.createFile("text/plain", fileName)!!
            action(childFileUri)
        }
        return childFileUri
    }

    //在已经存在zzqSaf文件夹的情况下，重复调用createDirectory()创建文件夹也会成功，生成如zzqSAF(1)这样的文件夹
    //DocumentFile.fromTreeUri(requireContext(),uri)!!.createDirectory("zzqSAF")
}

/**
 * 向着指定路径写入数据，即希望传入的[uri]是一个有效的文件路径，而非文件夹。如果文件路径不存在，则创建！
 */
fun Fragment.writeData(uri: DocumentFile, content: String) {

    if (!uri.exists()) {
//        showToast("该路径不存在")
        if (!uri.parentFile?.exists()!!) {
            throw IllegalArgumentException("指定路径上的文件不存在")
        }
    }
    if (uri.isDirectory) {
        showToast("写入错误，该路径为文件夹，而非文件")
        return
    }

}
