package io.github.chase22.telegram.group

import com.github.jasync.sql.db.Connection
import com.github.jasync.sql.db.RowData
import io.github.chase22.telegram.Entity
import io.github.chase22.telegram.Repository
import javax.inject.Inject
import javax.inject.Singleton

data class TelegramGroup(
    val chatId: Long,
    val paused: Boolean
) : Entity<Long> {
    override fun getKey(): Long = chatId
}

interface TelegramGroupRepository : Repository<Long, TelegramGroup>

@Singleton
class JasyncTelegramGroupRepository @Inject constructor(
    private val client: Connection
) : TelegramGroupRepository {
    override fun getClient(): Connection = client
    override fun getTableName(): String = "telegram_group"

    override fun save(entity: TelegramGroup) = mapRowToEntity(
        client.sendPreparedStatement("INSERT INTO telegram_group values (?, ?)", listOf(entity.chatId, entity.paused))
            .get().rows[0]
    )

    override fun mapRowToEntity(rowData: RowData): TelegramGroup = TelegramGroup(
        rowData.getLong(0)!!,
        rowData.getBoolean(1)!!
    )

}
