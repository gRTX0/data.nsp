package database.spring.boot.nsp.data.nsp.repository

import database.spring.boot.nsp.data.nsp.entity.Subscriber
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository

interface SubscriberRepository: CrudRepository<Subscriber, String>, JpaRepository<Subscriber, String>