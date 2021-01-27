package io.github.chase22.telegram.bot.command

import io.github.chase22.telegram.group.TelegramGroup
import io.github.chase22.telegram.group.TelegramGroupRepository
import io.github.chase22.telegram.lib.util.send
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand
import org.telegram.telegrambots.meta.api.objects.Chat
import org.telegram.telegrambots.meta.api.objects.User
import org.telegram.telegrambots.meta.bots.AbsSender
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StartCommand @Inject constructor(
    private val telegramGroupRepository: TelegramGroupRepository
) : BotCommand("start", "Starts the bot") {
    override fun execute(absSender: AbsSender, user: User, chat: Chat, arguments: Array<out String>) {
        if (telegramGroupRepository.existsById(chat.id)) {
            absSender.send("Bot is already started", chat.id);
        } else {
            absSender.send("Bot started", chat.id)
            telegramGroupRepository.save(TelegramGroup(chat.id))
        }
    }
}