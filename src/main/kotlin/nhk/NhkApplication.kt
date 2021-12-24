package nhk

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class NhkApplication

fun main(args: Array<String>) {
	runApplication<NhkApplication>(*args)
}
