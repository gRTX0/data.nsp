package database.spring.boot.nsp.data.nsp.entity

import javax.persistence.*

@Entity
@Table(name = "SUBSCRIBER")
data class Subscriber(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "code", nullable = false)
    var code: String? = null,
    @Column(name = "name")
    val name:String,
    @Column(name = "surname")
    val surname: String,
    @Column(name = "debt")
    var debt:Double
)