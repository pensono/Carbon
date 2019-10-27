package org.carbon.nativelib

import org.carbon.runtime.Callable
import org.carbon.runtime.CarbonObject
import org.carbon.runtime.CarbonString
import org.carbon.runtime.Composite
import java.net.HttpURLConnection
import java.net.URL

object HttpResourceConstructor : Callable() {
    override fun apply(arguments: List<CarbonObject>): CarbonObject {
        assert(arguments.size == 2 || arguments.size == 3)

        val method = arguments[0] as CarbonString
        val url = arguments[1] as CarbonString

        return HttpResource(method.value, url.value)
    }
}

class HttpResource(val method: String, val url: String) : Composite() {
    val body: String

    init {
        val url = URL(url)
        val connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = method
        val bytes = connection.inputStream.readAllBytes()
        body = String(bytes)
    }

    override fun getMember(name: String): CarbonObject? =
        when (name) {
            "ResponseBody" -> CarbonString(body)
            else -> null
        }
}
