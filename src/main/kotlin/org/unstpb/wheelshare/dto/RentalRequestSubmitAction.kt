package org.unstpb.wheelshare.dto

import org.springframework.validation.annotation.Validated
import org.unstpb.wheelshare.entity.enums.RentalAction
import java.util.UUID

@Validated
data class RentalRequestSubmitAction(
    val rentalId: UUID,
    val action: RentalAction,
)
