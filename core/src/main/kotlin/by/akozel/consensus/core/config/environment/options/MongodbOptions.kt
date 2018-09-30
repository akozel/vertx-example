package by.akozel.consensus.core.config.environment.options

import io.vertx.core.json.JsonObject

object MongodbOptions : Options() {

    private const val sectionName = "mongodb"

    var url: String = ""
        private set(value) {
            field = value
        }

    override fun load(conf: JsonObject) {
        url = getString(conf, sectionName, "url")
    }

}