package org.unstpb.wheelshare.entity

import org.springframework.data.cassandra.core.mapping.Indexed
import org.springframework.data.cassandra.core.mapping.PrimaryKey
import org.springframework.data.cassandra.core.mapping.Table
import java.util.UUID

// TODO: I think I can delete this
@Table("admin")
data class Admin(
    @PrimaryKey
    val id: UUID,
    @Indexed
    var email: String,
    var password: String,
    var firstName: String,
    var lastName: String,
)
