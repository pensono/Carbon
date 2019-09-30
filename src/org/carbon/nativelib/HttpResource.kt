package org.carbon.nativelib

import org.carbon.runtime.Callable
import org.carbon.runtime.CarbonInteger
import org.carbon.runtime.CarbonObject
import org.carbon.runtime.CarbonString
import java.net.HttpURLConnection
import java.net.URL

object HttpResourceConstructor : CarbonObject(), Callable {
    override fun lookupName(name: String): CarbonObject? = getMember(name) // Should this actually do some work with the scope?
    override fun getMember(name: String): CarbonObject? = null

    override fun call(arguments: List<CarbonObject>): CarbonObject {
        assert(arguments.size == 2 || arguments.size == 3)

        val method = arguments[0] as CarbonString
        val url = arguments[1] as CarbonString

        return HttpResource(method.value, url.value)
    }
}

class HttpResource(val method: String, val url: String) : CarbonObject() {
    val body: String

    init {
        val url = URL(url)
        val connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = method
        val bytes = connection.inputStream.readAllBytes()
        body = String(bytes)
    }

    override fun lookupName(name: String): CarbonObject? = getMember(name) // Should this actually do some work with the scope?
    override fun getMember(name: String): CarbonObject? =
        when (name) {
            "ResponseBody" -> CarbonString(body)
            else -> null
        }
}
