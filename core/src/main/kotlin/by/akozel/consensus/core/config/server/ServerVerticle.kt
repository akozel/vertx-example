package by.akozel.consensus.core.config.server

import by.akozel.consensus.core.config.logging.Loggable
import by.akozel.consensus.core.config.logging.loggable
import io.vertx.core.AbstractVerticle
import io.vertx.core.Future
import io.vertx.core.http.HttpServer
import io.vertx.ext.web.Router


class ServerVerticle(private val router: Router,
                     private val server: HttpServer,
                     private val port: Int) : AbstractVerticle(), Loggable {

    private val logger by loggable()

    override fun start(/*startFuture: Future<Void>*/) {
        server
                .requestHandler { router.accept(it) }
                .listen(port) { res ->
                    if (res.succeeded()) {
                        logger.info("server started at port ${res.result().actualPort()}")
                        //startFuture.complete()
                    } else {
                        logger.error("server start failed at port $port")
                        //startFuture.fail(res.cause())
                    }
                }
    }

}
