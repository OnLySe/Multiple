package com.minew.base.interfaces

abstract class DialogCallback {
    abstract fun confirm()
    open fun cancel(){}
}