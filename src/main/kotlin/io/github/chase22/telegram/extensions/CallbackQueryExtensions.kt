package io.github.chase22.telegram.extensions

import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery
import org.telegram.telegrambots.meta.api.objects.CallbackQuery
import org.telegram.telegrambots.meta.bots.AbsSender

fun CallbackQuery.replyTo(absSender: AbsSender, text: String, showAlert: Boolean = true) {
    absSender.execute(
        AnswerCallbackQuery.builder()
            .callbackQueryId(this.id)
            .text(text)
            .showAlert(showAlert)
            .build()
    )
}