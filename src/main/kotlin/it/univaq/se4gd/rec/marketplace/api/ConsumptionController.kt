package it.univaq.se4gd.rec.marketplace.api

import it.univaq.se4gd.rec.marketplace.consumption.Consumption
import it.univaq.se4gd.rec.marketplace.consumption.ConsumptionHistory
import it.univaq.se4gd.rec.marketplace.consumption.ConsumptionService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/consumptions")
class ConsumptionController(
        val consumptionService: ConsumptionService
) {

    @PostMapping
    fun consume(@RequestBody request: ConsumptionRequest): ResponseEntity<List<Consumption>> {
        val consumptions = consumptionService.consume(request.communityId, request.houseId, request.energyNeed)
        return ResponseEntity(consumptions, HttpStatus.OK)
    }

    @GetMapping("/{communityId}/{houseId}")
    fun getConsumptions(@PathVariable("communityId") communityId: Int, @PathVariable("houseId") houseId: Int): ResponseEntity<List<ConsumptionHistory>> {
        val consumptions = consumptionService.getConsumptionHistory(communityId, houseId)
        return ResponseEntity(consumptions, HttpStatus.OK)
    }

    @GetMapping("/credits/{communityId}/{houseId}")
    fun getCredits(@PathVariable("communityId") communityId: Int, @PathVariable("houseId") houseId: Int): ResponseEntity<List<ConsumptionHistory>> {
        val credits = consumptionService.getCreditHistory(communityId, houseId)
        return ResponseEntity(credits, HttpStatus.OK)
    }
}

data class ConsumptionRequest(val communityId: Int, val houseId: Int, val energyNeed: Double)