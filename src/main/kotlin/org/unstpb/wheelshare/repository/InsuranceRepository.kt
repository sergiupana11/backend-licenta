package org.unstpb.wheelshare.repository

import org.springframework.data.repository.CrudRepository
import org.unstpb.wheelshare.entity.Insurance
import java.util.*

interface InsuranceRepository : CrudRepository<Insurance, UUID> {
    fun findByBeneficiaryId(beneficiaryId: UUID): Insurance?
}
