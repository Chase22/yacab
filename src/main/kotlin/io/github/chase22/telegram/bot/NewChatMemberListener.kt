package io.github.chase22.telegram.bot

import io.github.chase22.telegram.extensions.deleteMessage
import io.github.chase22.telegram.extensions.replyTo
import io.github.chase22.telegram.lib.GroupAdminService
import io.github.chase22.telegram.lib.ui.CallbackMessage
import io.github.chase22.telegram.lib.ui.CallbackMessageService
import io.github.chase22.telegram.lib.util.keyboardRow
import io.micronaut.context.annotation.Context
import io.micronaut.context.event.ApplicationEventListener
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.CallbackQuery
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import org.telegram.telegrambots.meta.bots.AbsSender
import javax.inject.Inject
import javax.inject.Singleton

@Context
class NewChatMemberListener @Inject constructor(
    private val callbackMessageService: CallbackMessageService,
) :
    ApplicationEventListener<NewChatMemberApplicationEvent> {
    override fun onApplicationEvent(event: NewChatMemberApplicationEvent) {
        callbackMessageService.sendCallbackMessage(
            VerificationCallbackMessage(
                SendMessage.builder()
                    .chatId(event.message.chatId.toString())
                    .replyToMessageId(event.message.messageId)
                    .text("Welcome ${event.body.firstName}. Please verify yourself"),
                event.body.id
            )
        )
    }
}

class VerificationCallbackMessage(
    private val sendMessage: SendMessage.SendMessageBuilder,
    private val userId: Int
) : CallbackMessage {
    override fun getSendMessage(): SendMessage =
        sendMessage.replyMarkup(
            InlineKeyboardMarkup.builder()
                .keyboardRow(
                    InlineKeyboardButton
                        .builder()
                        .text("Verify")
                        .callbackData("verify-${userId}")
                        .build()
                ).build()
        ).build()

    override fun processCallback(
        absSender: AbsSender,
        callbackQuery: CallbackQuery,
        adminService: GroupAdminService
    ): Boolean {
        return if (adminService.isAdmin(callbackQuery.message.chatId, callbackQuery.from.id)
            || callbackQuery.from.id == callbackQuery.data.substringAfter('-').toInt()
        ) {
            callbackQuery.replyTo(absSender, "User verified")
            callbackQuery.message.deleteMessage(absSender)
            false
        } else {
            callbackQuery.replyTo(absSender, "Action not allowed")
            true
        }
    }
}