package database.spring.boot.nsp.data.nsp.entity

import javax.persistence.*

@Entity
@Table(name = "SUBSCRIBER")
data class Subscriber(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long,
    val code: String,
    val name:String,
    val surname: String,
    var debt:Double
)