package io.gryteck.bonusservice.domain

import io.gryteck.bonus_service_api.common.PrivilegeStatus
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("privilege")
data class PrivilegeEntity(
    @field:Id
    @Column("id")
    val id: Int,
    @Column("username")
    val username: String,
    @Column("status")
    val status: PrivilegeStatus,
    @Column("balance")
    val balance: Int
)
