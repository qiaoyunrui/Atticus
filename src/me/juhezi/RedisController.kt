package me.juhezi

import redis.clients.jedis.Jedis

class RedisController {
}

fun main(args: Array<String>) {
    val jedis = Jedis("localhost")
    jedis.append("hello", "world")
    println(jedis["hello"])
}