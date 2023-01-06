package io.gryteck.bonusservice.domain

import io.gryteck.bonus_service_api.common.OperationType
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime
import java.util.*

@Table("privilege_history")
data class PrivilegeHistoryEntity(
    @field:Id
    @field:Column("id")
    val id: Int,
    @field:Column("privilege_id")
    val privilegeId: Int,
    @field:Column("ticket_uid")
    val ticketUid: UUID,
    @field:Column("datetime")
    val dateTime: LocalDateTime,
    @field:Column("balance_diff")
    val balanceDiff: Int,
    @field:Column("operation_type")
    val operationType: OperationType
)
