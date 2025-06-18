package com.cymply

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication


@SpringBootApplication
class CymplyApplication

fun main(args: Array<String>) {
    runApplication<CymplyApplication>(*args)
}
