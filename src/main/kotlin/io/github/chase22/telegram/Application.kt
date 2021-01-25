package io.github.chase22.telegram

import io.micronaut.runtime.Micronaut.*

fun main(args: Array<String>) {
    build()
            .args(*args)
            .packages("io.github.chase22.telegram")
            .start()
}

