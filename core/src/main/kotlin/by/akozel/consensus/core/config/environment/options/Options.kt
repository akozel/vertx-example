package by.akozel.consensus.core.config.environment.options

import by.akozel.consensus.core.config.logging.Loggable
import by.akozel.consensus.core.config.logging.loggable
import io.vertx.core.json.JsonObject
import java.lang.RuntimeException

abstract class Options : Loggable {

    private val logger by loggable()

    abstract fun load(conf: JsonObject)

    fun getString(conf: JsonObject, sectionName: String, optionName: String): String {
        val result = resolve(conf, sectionName, optionName)
        return when (result) {
            is String -> result
            else -> {
                logger.error("Not expected value for configSource: ${sectionName.toUpperCase()}.${optionName.toUpperCase()} = " + result)
                throw RuntimeException("Not expected value for configSource: ${sectionName.toUpperCase()}.${optionName.toUpperCase()} = " + result)
            }
        }
    }

    fun getString(conf: JsonObject, sectionName: String, subSectionName: String, optionName: String): String {
        val result = resolve(conf, sectionName, subSectionName, optionName)
        return when (result) {
            is String -> result
            else -> {
                logger.error("Not expected value for configSource: ${sectionName.toUpperCase()}.${subSectionName.toUpperCase()}.${optionName.toUpperCase()} = " + result)
                throw RuntimeException("Not expected value for configSource: ${sectionName.toUpperCase()}.${subSectionName.toUpperCase()}.${optionName.toUpperCase()} = " + result)
            }
        }
    }

    fun getInt(conf: JsonObject, sectionName: String, optionName: String): Int {
        val result = resolve(conf, sectionName, optionName)
        return when (result) {
            is String -> result.toInt()
            is Int -> result
            else -> {
                logger.error("Not expected value for configSource: ${sectionName.toUpperCase()}.${optionName.toUpperCase()} = " + result)
                throw RuntimeException("Not expected value for configSource: ${sectionName.toUpperCase()}.${optionName.toUpperCase()} = " + result)
            }
        }
    }

    fun getInt(conf: JsonObject, sectionName: String, subSectionName: String, optionName: String): Int {
        val result = resolve(conf, sectionName, subSectionName, optionName)
        return when (result) {
            is String -> result.toInt()
            is Int -> result
            else -> {
                logger.error("Not expected value for configSource: ${sectionName.toUpperCase()}.${subSectionName.toUpperCase()}.${optionName.toUpperCase()} = " + result)
                throw RuntimeException("Not expected value for configSource: ${sectionName.toUpperCase()}.${subSectionName.toUpperCase()}.${optionName.toUpperCase()} = " + result)
            }
        }
    }

    fun getDouble(conf: JsonObject, sectionName: String, optionName: String): Double {
        val result = resolve(conf, sectionName, optionName)
        return when (result) {
            is String -> result.toDouble()
            is Double -> result
            else -> {
                logger.error("Not expected value for configSource: ${sectionName.toUpperCase()}.${optionName.toUpperCase()} = " + result)
                throw RuntimeException("Not expected value for configSource: ${sectionName.toUpperCase()}.${optionName.toUpperCase()} = " + result)
            }
        }
    }

    fun getDouble(conf: JsonObject, sectionName: String, subSectionName: String, optionName: String): Double {
        val result = resolve(conf, sectionName, subSectionName, optionName)
        return when (result) {
            is String -> result.toDouble()
            is Double -> result
            else -> {
                logger.error("Not expected value for configSource: ${sectionName.toUpperCase()}.${subSectionName.toUpperCase()}.${optionName.toUpperCase()} = " + result)
                throw RuntimeException("Not expected value for configSource: ${sectionName.toUpperCase()}.${subSectionName.toUpperCase()}.${optionName.toUpperCase()} = " + result)
            }
        }
    }

    /**
     * Get EnvVariable with priority otherwise get value from configSource file
     */
    private fun resolve(conf: JsonObject, sectionName: String, optionName: String): Any? {
        return conf.getString(sectionName + "_" + optionName)
                ?: conf.getJsonObject(sectionName)?.getValue(optionName)
    }

    private fun resolve(conf: JsonObject, sectionName: String, subSectionName: String, optionName: String): Any? {
        return conf.getString(sectionName + "_" + subSectionName + "_" + optionName)
                ?: conf.getJsonObject(sectionName).getJsonObject(subSectionName)?.getValue(optionName)
    }


}