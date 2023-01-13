package it.univaq.se4gd.rec.marketplace.production

import it.univaq.se4gd.rec.marketplace.domain.InventoryItem
import org.springframework.stereotype.Component
import java.sql.ResultSet
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import java.sql.Timestamp

@Component
class ProductionService(val db: JdbcTemplate) {
    fun logProduction(communityId: Int, houseId: Int, energyProduced: Double, productionTime: Timestamp?): Double {
        val results = db.query("select * from inventory where houseId=$houseId and communityId=$communityId", inventoryItemRowMapper)
        if(results.size > 0) {
            val newCapacity = (results.first()?.energyProduced ?: 0.0) + energyProduced
            db.update("update inventory set energyProduced=$newCapacity where communityId=$communityId and houseId=$houseId")
        } else {
            db.update("insert into inventory values (?, ?, ?, ?)", communityId, houseId, energyProduced, Timestamp(System.currentTimeMillis()))
        }
        db.update("insert into productions values (?, ?, ?, ?)", communityId, houseId, energyProduced, Timestamp(System.currentTimeMillis()))
        return energyProduced
    }

    fun getTransactions(communityId: Int, houseId: Int, numberOfTransactions: Int): List<InventoryItem> {
            return db.query("select * from productions where communityId=$communityId and houseId=$houseId ORDER BY productionTime DESC limit $numberOfTransactions", inventoryItemRowMapper)
    }
    val inventoryItemRowMapper: RowMapper<InventoryItem> = RowMapper<InventoryItem> { resultSet: ResultSet, _: Int ->
        InventoryItem(resultSet.getInt("communityId"), resultSet.getInt("houseId"), resultSet.getDouble("energyProduced"), resultSet.getTimestamp("productionTime"))
    }
}