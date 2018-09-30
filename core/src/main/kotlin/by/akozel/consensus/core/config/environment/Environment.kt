package by.akozel.consensus.core.config.environment

import by.akozel.consensus.core.config.environment.options.Options
import by.akozel.consensus.core.config.logging.Loggable
import by.akozel.consensus.core.config.logging.loggable
import io.vertx.config.ConfigChange
import io.vertx.config.ConfigRetriever
import io.vertx.config.ConfigRetrieverOptions
import io.vertx.core.AsyncResult
import io.vertx.core.Vertx
import io.vertx.core.json.JsonObject
import java.util.concurrent.CompletableFuture

class Environment(vertx: Vertx, options: Set<Options>) {

    private val logger = Environment.logger
    private val configRetriever = ConfigRetriever.create(vertx,
            ConfigRetrieverOptions()
                    .setScanPeriod(60 * 1000) // 1 minute
                    .addStore(ConfigStore.defaultFile)
                    .addStore(Environment.configSource)
                    .addStore(ConfigStore.envConfig)
    )
    val value = Environment.value
    val future = CompletableFuture<JsonObject>()

    init {
        ConfigRetriever.getConfigAsFuture(configRetriever).setHandler { event: AsyncResult<JsonObject> ->
            if (event.succeeded()) {
                options.forEach { option: Options -> option.load(event.result()) }
                future.obtrudeValue(event.result())
            }
        }

        configRetriever.configStream()
                .endHandler { end ->
                    logger.warn("[configSource retriever] has been closed")
                }
                .exceptionHandler { throwable ->
                    logger.error("[configSource retriever] got an error: ${throwable.message}")
                }

        configRetriever.listen { change: ConfigChange ->
            val previousConfig = change.previousConfiguration
            val newConfig = change.newConfiguration

            logger.info("[configSource retriever] got updated configuration: $newConfig")
            options.forEach { option: Options -> option.load(newConfig) }
        }
    }

    companion object : Loggable {
        private val logger by loggable()
        val value: String = System.getenv("env") ?: "development"

        val configSource = when (value) {
            in EnvType.development -> ConfigStore.developmentFile
            in EnvType.stage -> ConfigStore.stageFile
            in EnvType.prod -> ConfigStore.envConfig
            in EnvType.test -> ConfigStore.testFile
            else -> {
                logger.error("Error unknown environment: ${value.toUpperCase()}")
                throw RuntimeException("Error unknown environment: ${value.toUpperCase()}")
            }
        }

        init {
            logger.info("Running with environment: ${value.toUpperCase()}")
        }
    }

}
