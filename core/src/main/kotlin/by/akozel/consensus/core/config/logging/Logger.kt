package by.akozel.consensus.core.config.logging

import org.slf4j.Logger
import org.slf4j.LoggerFactory

interface Loggable

fun <R : Loggable> R.loggable(): Lazy<Logger> {
    return lazy { LoggerFactory.getLogger(getClassName(this.javaClass)) }
}
fun <T : Loggable> getClassName(clazz: Class<T>): String {
    return clazz.name.removeSuffix("\$Companion")
}