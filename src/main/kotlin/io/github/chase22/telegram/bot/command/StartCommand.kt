package io.github.chase22.telegram.bot.command

import io.github.chase22.telegram.lib.util.send
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand
import org.telegram.telegrambots.meta.api.objects.Chat
import org.telegram.telegrambots.meta.api.objects.User
import org.telegram.telegrambots.meta.bots.AbsSender
import javax.inject.Singleton

@Singleton
class StartCommand: BotCommand("start", "Starts the bot") {
    override fun execute(absSender: AbsSender, user: User, chat: Chat, arguments: Array<out String>) {
        absSender.send("Bot started", chat.id)
    }
}