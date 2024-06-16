package org.unstpb.wheelshare.entity

import org.springframework.data.cassandra.core.mapping.PrimaryKey
import org.springframework.data.cassandra.core.mapping.Table
import java.util.UUID

@Table("insurance_company")
data class InsuranceCompany(
    @PrimaryKey
    var id: UUID,
    var name: String,
    var priceModifier: Double,
)
