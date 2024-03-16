package dog.beepboop.velvet.controlServer

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication()
class VelvetControlServerApplication

fun main(args: Array<String>) {
    runApplication<VelvetControlServerApplication>(*args)
}
