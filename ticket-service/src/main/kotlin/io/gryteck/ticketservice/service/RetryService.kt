package io.gryteck.ticketservice.service

import io.gryteck.bonus_service_api.CancelBonusesRequest
import kotlinx.coroutines.runBlocking
import org.springframework.scheduling.annotation.Async
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.util.*
import java.util.concurrent.ConcurrentLinkedQueue

// TODO add logic and implement commands (move to microservice)
@Service
class RetryService(
    private val bonusService: BonusService
) {
    val queue: Queue<CancelBonusData> = ConcurrentLinkedQueue()

    @Scheduled(fixedDelay = 10_000, initialDelay = 10_000)
    @Async
    fun resendRequests() {
        val size = queue.size // prevent infinity loop
        (0 until size).forEach { _ ->
            val curr = queue.poll() ?: return@forEach
            try {
                runBlocking {
                    bonusService.cancelBonusOperation(curr.username, curr.request)
                }
            } catch (_: Exception) {
                // TODO add logging
                queue.add(curr)
            }
        }
    }
}

data class CancelBonusData(
    val username: String,
    val request: CancelBonusesRequest
)