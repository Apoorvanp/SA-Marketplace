package it.univaq.se4gd.rec.marketplace.inventory

import it.univaq.se4gd.rec.marketplace.domain.InventoryItem
import org.springframework.stereotype.Component
import java.sql.ResultSet
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import java.sql.Timestamp

@Component
class InventoryService(val db: JdbcTemplate) {
    fun getProductionData(communityId: Int?, houseId: Int?): List<InventoryItem> {
        if(communityId == null && houseId == null) {
            val results = db.query("select * from inventory", inventoryItemRowMapper)
            return results
        } else {
            val results = db.query("select * from inventory where communityId=$communityId and houseId=$houseId", inventoryItemRowMapper)
            return results
        }
    }

    val inventoryItemRowMapper: RowMapper<InventoryItem> = RowMapper<InventoryItem> { resultSet: ResultSet, _: Int ->
        InventoryItem(resultSet.getInt("communityId"), resultSet.getInt("houseId"), resultSet.getDouble("energyProduced"), resultSet.getTimestamp("productionTime"))
    }
}