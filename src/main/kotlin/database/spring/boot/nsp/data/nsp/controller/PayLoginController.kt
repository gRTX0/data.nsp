package database.spring.boot.nsp.data.nsp.controller

import database.spring.boot.nsp.data.nsp.entity.ApiResponse
import database.spring.boot.nsp.data.nsp.entity.Payment
import database.spring.boot.nsp.data.nsp.entity.Subscriber
import database.spring.boot.nsp.data.nsp.repository.SubscriberRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.ArrayList
import kotlin.math.roundToInt


@RestController
@RequestMapping("/api")
class PayLogicController(private val subscriberRepository : SubscriberRepository ) {
    @GetMapping("/subscriber")
    fun subscribers() : ResponseEntity<List<Subscriber>> {
        val subscribers = subscriberRepository.findAll()
        if (subscribers.isEmpty()) {
            return ResponseEntity<List<Subscriber>>(HttpStatus.NO_CONTENT)
        }
        return ResponseEntity<List<Subscriber>>(subscribers, HttpStatus.OK)
    }

    @GetMapping("/subscriber/{code}")
    fun subscriberByCode(@PathVariable("code") SubscriberCode: String): ResponseEntity<Subscriber> {
        val subscriber = subscriberRepository.findById(SubscriberCode)
        if (subscriber.isPresent) {
            return ResponseEntity<Subscriber>(subscriber.get(), HttpStatus.OK)
        }
        return ResponseEntity<Subscriber>(HttpStatus.NOT_FOUND)
    }

    @PostMapping("/subscribers")
    fun addNewSubscriber(@RequestBody subscriber: Subscriber): ApiResponse {
        return ApiResponse(200, "Ok", subscriberRepository.save(subscriber))
    }
    @PutMapping("/subscriber/{code}")
    fun updateSubscriberByCode(@PathVariable("code") subscriberCode: String, @RequestBody subscriber: Subscriber): ResponseEntity<Subscriber> {
        return subscriberRepository.findById(subscriberCode).map {
                subscriberDetails ->
            val updatedGadget: Subscriber = subscriberDetails.copy(
                code = subscriber.code,
                name = subscriber.name,
                surname = subscriber.surname,
                debt = subscriber.debt
            )
            ResponseEntity(subscriberRepository.save(updatedGadget), HttpStatus.OK)
        }.orElse(ResponseEntity<Subscriber>(HttpStatus.INTERNAL_SERVER_ERROR))
    }
    @GetMapping("/debt/{code}")
    fun debt(@PathVariable code: String): ApiResponse {
        //subscribers.takeWhile { it.code == code }
        val subscriber = subscriberRepository.findById(code)

        val response = ApiResponse(
            if (subscriber == null) 404 else 200, if (subscriber == null) "NOT FOUND" else "Success", subscriber
        )

        return response
    }
    @PostMapping("/pay")
    fun pay(@RequestBody payment: Payment): ApiResponse {
        val subscriber = subscriberRepository.findById(payment.code)
        if (subscriber == null) {
            val response = ApiResponse(
                404, "NOT FOUND", subscriber
            )
            return response
        }

        subscriber.get().debt -= payment.amount
        subscriber.get().debt = (subscriber.get().debt * 100).roundToInt() / 100.00

        val response = ApiResponse(
            200, "Success", subscriber
        )

        return response
    }
}