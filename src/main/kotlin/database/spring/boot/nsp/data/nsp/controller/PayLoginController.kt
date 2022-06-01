package database.spring.boot.nsp.data.nsp.controller

import database.spring.boot.nsp.data.nsp.entity.ApiResponse
import database.spring.boot.nsp.data.nsp.entity.Subscriber
import database.spring.boot.nsp.data.nsp.repository.SubscriberRepository
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.util.ObjectUtils
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder
import javax.annotation.PostConstruct



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
    fun addNewSubscriber(@RequestBody subscriber: Subscriber): ResponseEntity<Subscriber> {
        val persistedSubscriber = subscriberRepository.save(subscriber)
        return if (ObjectUtils.isEmpty(persistedSubscriber)) {
            ResponseEntity<Subscriber>(HttpStatus.BAD_REQUEST)
        } else ResponseEntity(HttpStatus.CREATED)
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

}