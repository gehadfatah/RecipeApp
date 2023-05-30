package com.goda.elmenus.util.models

class VolatileCache<T> {

    private var cache = HashMap<String, T>()

    fun add(key: String, element: T): VolatileCache<T> {
        cache[key] = element
        return this
    }

    fun clearCacheByKey(key: String): VolatileCache<T> {
        cache.remove(key)
        return this
    }

    fun clearCache(): VolatileCache<T> {
        cache.clear()
        return this
    }

    fun find(key: String): T? {
        return cache[key]
    }

    fun has(key: String): Boolean {
        return cache.containsKey(key)
    }

}
