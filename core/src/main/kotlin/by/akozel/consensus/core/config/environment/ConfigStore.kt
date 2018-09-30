package by.akozel.consensus.core.config.environment

import io.vertx.config.ConfigStoreOptions
import io.vertx.core.json.JsonObject

object ConfigStore {

    // by default vertx reference to the '.vertx' cache, not classpath
    private val resourcePath = this::class.java.classLoader.getResource("")?.path

    val defaultFile: ConfigStoreOptions = ConfigStoreOptions()
            .setType("file")
            .setFormat("yaml")
            .setConfig(JsonObject().put("path", "$resourcePath/default.yaml"))

    val developmentFile: ConfigStoreOptions = ConfigStoreOptions()
            .setType("file")
            .setFormat("yaml")
            .setConfig(JsonObject().put("path", "$resourcePath/development.yaml"))

    val stageFile: ConfigStoreOptions = ConfigStoreOptions()
            .setType("file")
            .setFormat("yaml")
            .setConfig(JsonObject().put("path", "$resourcePath/stage.yaml"))

    val testFile: ConfigStoreOptions = ConfigStoreOptions()
            .setType("file")
            .setFormat("yaml")
            .setConfig(JsonObject().put("path", "$resourcePath/test.yaml"))

    val envConfig: ConfigStoreOptions = ConfigStoreOptions()
            .setType("env")
            .setConfig(JsonObject().put("raw-data", true))

}