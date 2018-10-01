package by.akozel.consensus.personservice

import by.akozel.consensus.core.config.environment.options.BasicOptions
import by.akozel.consensus.core.config.environment.Environment
import by.akozel.consensus.core.config.environment.options.MongodbOptions
import by.akozel.consensus.core.config.environment.options.Options
import by.akozel.consensus.core.config.server.ServerVerticle
import by.akozel.consensus.core.exception.ExceptionHandler
import by.akozel.consensus.personservice.registration.RegistrationOptions
import by.akozel.consensus.personservice.registration.RegistrationResource
import by.akozel.consensus.personservice.registration.RegistrationWorker
import io.vertx.core.Vertx
import io.vertx.ext.web.Router
import io.vertx.core.VertxOptions


object Context {

    internal val serverVertx = Vertx.vertx(VertxOptions())
    internal val eventBus = serverVertx.eventBus()
    internal val server = serverVertx.createHttpServer()

    //internal val routerVertx = Vertx.vertx()
    internal val router: Router = Router.router(Context.serverVertx) // routerVertx
    internal val environment = Environment(Context.serverVertx,
            setOf(BasicOptions, RegistrationOptions, MongodbOptions)
    )

}

fun main(args: Array<String>) {
    Context.environment.future.thenApply { _ ->
        ExceptionHandler(Context.router)
        RegistrationResource(Context.router, RegistrationOptions)
        val serverVerticle = ServerVerticle(Context.router, Context.server, BasicOptions.serverPort)
        val worker = RegistrationWorker()

        Context.serverVertx
                .deployVerticle(serverVerticle)
        Context.serverVertx
                .deployVerticle(worker)
    }

}