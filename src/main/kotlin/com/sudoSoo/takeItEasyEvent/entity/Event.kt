package com.sudoSoo.takeItEasyEvent.entity

import com.sudoSoo.takeItEasyEvent.dto.CreateEventRequestDto
import jakarta.persistence.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Entity
class Event(
    var eventName: String,
    var eventDeadline: LocalDateTime,
    @OneToMany(mappedBy = "event")
    var coupon : MutableList<Coupon> = mutableListOf(),
) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private val id: Long? = null

    fun isDeadlineExpired() : Boolean {
        val currentDateTime = LocalDateTime.now()
        return eventDeadline.isBefore(currentDateTime)
        }


    companion object {
        fun of(requestDto: CreateEventRequestDto): Event {
            return Event(
                eventName = requestDto.eventName,
                eventDeadline = LocalDateTime.parse(requestDto.eventDeadline, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
                )
        }
    }
    fun addCoupon(coupon: Coupon){
        this.coupon.add(coupon)
        coupon.event = this
    }
}
