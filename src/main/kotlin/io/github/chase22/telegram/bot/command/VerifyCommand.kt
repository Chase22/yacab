package io.github.chase22.telegram.bot.command

import io.github.chase22.telegram.bot.NewChatMemberApplicationEvent
import io.micronaut.context.event.ApplicationEventPublisher
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand
import org.telegram.telegrambots.meta.api.objects.Chat
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.api.objects.User
import org.telegram.telegrambots.meta.bots.AbsSender
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VerifyCommand @Inject constructor(
    private val applicationEventPublisher: ApplicationEventPublisher
) : BotCommand("verify", "Tests the verification") {
    override fun execute(absSender: AbsSender, user: User, chat: Chat, arguments: Array<out String>) {}

    override fun processMessage(absSender: AbsSender, message: Message, arguments: Array<out String>) {
        applicationEventPublisher.publishEventAsync(NewChatMemberApplicationEvent(absSender, message.from, message))
    }

}