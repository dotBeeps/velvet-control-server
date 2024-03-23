package dog.beepboop.velvet.controlServer

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class VelvetControlServerApplication

fun main(args: Array<String>) {
    runApplication<VelvetControlServerApplication>(*args)
}
