package io.github.chase22.telegram.bot

import io.micronaut.context.event.ApplicationEvent
import org.telegram.telegrambots.meta.api.objects.CallbackQuery
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.User
import org.telegram.telegrambots.meta.bots.AbsSender

abstract class TelegramApplicationEvents<T>(source: AbsSender, val body: T) : ApplicationEvent(source) {
    override fun getSource(): AbsSender = super.getSource() as AbsSender
}

class UpdateApplicationEvent(source: AbsSender, body: Update) : TelegramApplicationEvents<Update>(source, body)
class MessageApplicationEvent(source: AbsSender, body: Message) : TelegramApplicationEvents<Message>(source, body)
class CallbackQueryApplicationEvent(source: AbsSender, body: CallbackQuery) :
    TelegramApplicationEvents<CallbackQuery>(source, body)

class NewChatMemberApplicationEvent(source: AbsSender, body: User, val message: Message) :
    TelegramApplicationEvents<User>(source, body)