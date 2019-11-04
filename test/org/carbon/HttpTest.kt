package org.carbon

import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class HttpTest {
    @Tag("external")
    @Test
    fun echoTest() {
        // TODO make this not rely on an external dependency
        val result = evaluate("""
            Content = HttpResource("GET", "https://httpstat.us/200").ResponseBody
        """)
        assertEquals(wrap("200 OK"), result.getMember("Content"))
    }
}