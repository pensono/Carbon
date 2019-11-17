package org.carbon.stdlib

import org.carbon.evaluate
import org.carbon.runtime.CarbonInteger
import org.junit.jupiter.api.Test

class TimeTest {
    @Test
    fun currentTimeTest() {
        // TODO make this not rely on an external dependency
        val result = evaluate(
        """
            // Just throw CurrentTime in the root scope for now
            Time = CurrentTime.UnixMillis
        """
        )
        val currentTime = System.currentTimeMillis()
        assert(currentTime <= (result.getMember("Time") as CarbonInteger).value)
    }
}