package com.zzq.common.utils

import java.util.*

object ByteUtil {

    /**
     * 将字节数组转换成十六进制字符串
     */
    fun bytesToHexString(bArray: ByteArray): String {
        val sb = StringBuffer(bArray.size)
        var sTemp: String
        for (i in bArray.indices) {
            sTemp = Integer.toHexString(0xFF and bArray[i].toInt())
            if (sTemp.length < 2) {
                sb.append(0)
            }
            sb.append(sTemp.toUpperCase())
        }
        return sb.toString()
    }

    /**
     * 根据给定长度，生成指定数量的随机数字，并将它们保存到数组中
     */
    fun ranDomBytes(length: Int): ByteArray {
        val random = Random()
        val randomBytes = ByteArray(length)
        random.nextBytes(randomBytes)
        return randomBytes
    }
}