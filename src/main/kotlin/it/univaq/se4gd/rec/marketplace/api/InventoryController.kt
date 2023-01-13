package it.univaq.se4gd.rec.marketplace.api

import it.univaq.se4gd.rec.marketplace.domain.InventoryItem
import it.univaq.se4gd.rec.marketplace.inventory.InventoryService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/inventory")
class InventoryController(
        val inventoryService: InventoryService
) {
    @PostMapping("/current")
    fun getInventory(@RequestBody request: ProductionDataRequest): List<InventoryItem> {
        return inventoryService.getProductionData(request.communityId, request.houseId)
    }
}

data class ProductionDataRequest(val communityId: Int?, val houseId: Int?)