package com.goda.elmenus.util

interface Mapper<T, E> {
    fun from(e: E): T
    fun to(t: T): E
}
