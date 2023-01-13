package it.univaq.se4gd.rec.marketplace.api

import it.univaq.se4gd.rec.marketplace.domain.InventoryItem
import it.univaq.se4gd.rec.marketplace.production.ProductionService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/productions")
class ProductionController(
        val productionService: ProductionService
) {
    @PostMapping
    fun sendProduce(@RequestBody request: InventoryItem): Double {
        return productionService.logProduction(request.communityId, request.houseId, request.energyProduced, request.productionTime)
    }

    @GetMapping("/{communityId}/{houseId}")
    fun getProductions(@PathVariable("communityId") communityId: Int, @PathVariable("houseId") houseId: Int, @RequestParam("numberOfTransactions", required = false) numberOfTransactions: Int = 5): List<InventoryItem> {
        return productionService.getTransactions(communityId, houseId, numberOfTransactions)
    }
}