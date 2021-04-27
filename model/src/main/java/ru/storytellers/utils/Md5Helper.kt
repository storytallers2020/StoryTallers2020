package ru.storytellers.utils

import timber.log.Timber
import java.security.MessageDigest

fun String.toMD5(): String {
    val bytes = MessageDigest.getInstance("MD5").digest(this.toByteArray())
    val res = bytes.toHex()
    Timber.d("Url to MD5 $this -> $res")
    return res
}

fun ByteArray.toHex(): String {
    return joinToString("") { "%02x".format(it) }
}