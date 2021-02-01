package io.github.chase22.telegram.extensions

import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.bots.AbsSender

fun Message.deleteMessage(absSender: AbsSender) = absSender.execute(
    DeleteMessage(this.chatId.toString(), this.messageId)
)