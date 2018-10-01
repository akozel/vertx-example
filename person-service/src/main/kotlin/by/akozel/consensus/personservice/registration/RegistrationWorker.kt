package by.akozel.consensus.personservice.registration

import by.akozel.consensus.core.config.logging.Loggable
import by.akozel.consensus.core.config.logging.loggable
import by.akozel.consensus.personservice.Context
import io.vertx.core.AbstractVerticle

class RegistrationWorker : AbstractVerticle(), Loggable {

    private val logger by loggable()
    private val consumer = Context.eventBus.consumer<String>("registration.worker")
    private val options = RegistrationOptions

    override fun start() {
        consumer.handler { message ->
            //System.out.println("I have received a message: " + message.body())
            var result = 1
            for(i in 1..10000000) {
                result += options.counter
            }
            message.reply(result.toString())
        }
    }

}