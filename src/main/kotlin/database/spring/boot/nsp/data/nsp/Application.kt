package database.spring.boot.nsp.data.nsp

import database.spring.boot.nsp.data.nsp.controller.PayLogicController
import database.spring.boot.nsp.data.nsp.entity.Subscriber
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class Application

fun main(args: Array<String>) {
	runApplication<Application>(*args)

}
