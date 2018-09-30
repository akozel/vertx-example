package by.akozel.consensus.core.config.environment.options

import io.vertx.core.json.JsonObject

object BasicOptions : Options() {

    private const val sectionName = "basic"

    var serverPort: Int = 0
        private set(value) {
            field = value
        }

    override fun load(conf: JsonObject) {
        serverPort = getInt(conf, sectionName, "server_port")
    }
}
