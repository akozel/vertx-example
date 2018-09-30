package by.akozel.consensus.personservice.registration

import by.akozel.consensus.core.config.environment.options.Options
import io.vertx.core.json.JsonObject

object RegistrationOptions : Options() {

    var counter: Int = 0
        private set(value) {
            field = value
        }

    override fun load(conf: JsonObject) {
        counter = getInt(conf,"option", "count")
    }
}