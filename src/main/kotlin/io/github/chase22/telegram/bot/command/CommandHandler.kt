package io.github.chase22.telegram.bot.command

import io.github.chase22.telegram.bot.MessageApplicationEvent
import io.micronaut.context.event.ApplicationEventListener
import org.telegram.telegrambots.extensions.bots.commandbot.commands.CommandRegistry
import org.telegram.telegrambots.meta.bots.AbsSender
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CommandHandler @Inject constructor(
    private val absSender: AbsSender,
    private val commandRegistry: CommandRegistry
) : ApplicationEventListener<MessageApplicationEvent> {
    override fun onApplicationEvent(event: MessageApplicationEvent) {
        if (event.body.isCommand) {
            commandRegistry.executeCommand(absSender, event.body)
        }
    }
}