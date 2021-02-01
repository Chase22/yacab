package io.github.chase22.telegram.bot

import io.github.chase22.telegram.lib.ui.CallbackMessageService
import io.micronaut.context.annotation.Factory
import io.micronaut.context.event.ApplicationEventListener
import javax.inject.Singleton

@Factory
class CallbackMessageServiceFactory {
    @Singleton
    fun callbackMessageService(callbackMessageService: CallbackMessageService): ApplicationEventListener<CallbackQueryApplicationEvent> {
        return ApplicationEventListener { callbackMessageService.processCallback(it.body) }
    }
}