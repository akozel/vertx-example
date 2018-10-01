package by.akozel.consensus.personservice.registration

import reactor.core.publisher.Mono

object RegistrationService {

   // @JvmStatic
    //private val options = RegistrationOptions
    //private var option :Int = 2

    fun doWork(option: Int):Mono<String> {
        var result = 1
        for(i in 1..10000000) {
            result += option
        }
        return Mono.just(result.toString())
    }

}