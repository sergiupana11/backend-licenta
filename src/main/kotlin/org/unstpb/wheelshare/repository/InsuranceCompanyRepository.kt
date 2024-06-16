package org.unstpb.wheelshare.repository

import org.springframework.data.repository.CrudRepository
import org.unstpb.wheelshare.entity.InsuranceCompany
import java.util.*

interface InsuranceCompanyRepository : CrudRepository<InsuranceCompany, UUID>
