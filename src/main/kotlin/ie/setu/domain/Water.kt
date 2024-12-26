package ie.setu.domain

import org.joda.time.DateTime

data class Water(
    var id: Int?,
    var amount: Float,
    var date: DateTime,
    var userId: Int
)