package backendsquid.buckpal

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BuckpalApplication

fun main(args: Array<String>) {
	runApplication<BuckpalApplication>(*args)
}
