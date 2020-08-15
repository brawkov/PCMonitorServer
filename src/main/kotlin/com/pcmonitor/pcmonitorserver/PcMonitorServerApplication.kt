package com.pcmonitor.pcmonitorserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PcMonitorServerApplication

fun main(args: Array<String>) {
    runApplication<PcMonitorServerApplication>(*args)
}
