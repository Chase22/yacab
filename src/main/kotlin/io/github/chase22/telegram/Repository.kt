package io.github.chase22.telegram

import com.github.jasync.sql.db.Connection
import com.github.jasync.sql.db.RowData

interface Repository<KeyType, EntityType : Entity<KeyType>> {
    fun getTableName(): String
    fun getIdColumn(): String = "id"
    fun getClient(): Connection

    fun findById(key: KeyType): EntityType? {
        val rows = getClient()
            .sendPreparedStatement("SELECT * FROM ${getTableName()} WHERE ${getIdColumn()} = ?", listOf(key))
            .get()
            .rows
        return if (rows.size > 0) {
            mapRowToEntity(rows[0])
        } else {
            null
        }
    }

    fun save(entity: EntityType): EntityType

    fun existsById(key: KeyType): Boolean = findById(key) != null
    fun exists(entity: EntityType) = existsById(entity.getKey())

    fun deleteById(key: KeyType) {
        getClient()
            .sendPreparedStatement("DELETE FROM ${getTableName()} where ${getIdColumn()}=?", listOf(key))
            .get()
    }

    fun delete(entity: EntityType) = deleteById(entity.getKey())

    fun mapRowToEntity(rowData: RowData): EntityType
}

interface Entity<KeyType> {
    fun getKey(): KeyType
}