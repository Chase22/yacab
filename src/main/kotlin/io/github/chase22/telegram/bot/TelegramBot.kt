package io.github.chase22.telegram.bot

import io.micronaut.context.annotation.Context
import io.micronaut.context.annotation.Value
import io.micronaut.context.event.ApplicationEventPublisher
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession
import javax.annotation.PostConstruct
import javax.inject.Inject
import javax.inject.Singleton

@Context
class TelegramBot @Inject constructor(
    @Value("\${bot.token}") private val token: String,
    @Value("\${bot.username}") private val username: String,
    private val eventPublisher: ApplicationEventPublisher
) : TelegramLongPollingBot() {
    override fun getBotToken(): String = token

    override fun getBotUsername(): String = username

    override fun onUpdateReceived(update: Update) {
        eventPublisher.publishEventAsync(UpdateApplicationEvent(this, update))
        if (update.hasMessage()) {
            eventPublisher.publishEventAsync(MessageApplicationEvent(this, update.message))
        }
        if (update.hasCallbackQuery()) {
            eventPublisher.publishEventAsync(CallbackQueryApplicationEvent(this, update.callbackQuery))
        }
    }

    @PostConstruct
    fun initialize() {
        TelegramBotsApi(DefaultBotSession::class.java).registerBot(this)
    }
}