package io.github.chase22.telegram.bot

import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Value
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand
import org.telegram.telegrambots.extensions.bots.commandbot.commands.CommandRegistry
import javax.inject.Singleton

@Factory
class CommandRegistryFactory {
    @Singleton
    fun commandRegistry(
        @Value("\${bot.username}") username: String,
        commands: List<BotCommand>
    ): CommandRegistry {
        val registry = CommandRegistry(true) { username }
        commands.parallelStream().forEach {
            LOGGER.info("Registered command {}", it.commandIdentifier)
            registry.register(it)
        }
        return registry
    }

    companion object {
        private val LOGGER: Logger = LoggerFactory.getLogger(CommandRegistryFactory::class.java)
    }
}