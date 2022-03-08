package dev.vladyour.smayb

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SmaybApplication

fun main(args: Array<String>) {
    runApplication<SmaybApplication>(*args)
}
