package com.woofy.core

import android.content.Context
import android.content.res.Resources

class ResourcesRepository private constructor(context: Context) {

    init {
        resources = context.resources
    }

    companion object {
        @Volatile
        private var sdk: ResourcesRepository? = null
        lateinit var resources: Resources

        @Throws(SecurityException::class)
        fun with(context: Context) {
            if (sdk == null) {
                synchronized(ResourcesRepository::class.java) {
                    if (sdk == null) {
                        sdk = ResourcesRepository(context)
                    }
                }
            }
        }
    }
}
