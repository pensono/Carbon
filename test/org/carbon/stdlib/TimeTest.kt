package org.carbon.stdlib

import org.carbon.evaluate
import org.carbon.intrepreter.unwrap
import org.carbon.runtime.CarbonInteger
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import java.util.concurrent.TimeUnit

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

    @Test
    fun timeChanges() {
        val exprs = evaluate(
            """
            Time = CurrentTime.UnixMillis
        """
        )

        val firstTime = unwrap(exprs.getMember("Time") as CarbonInteger)
        TimeUnit.MILLISECONDS.sleep(15)
        val secondTime = unwrap(exprs.getMember("Time") as CarbonInteger)

        assert(firstTime < secondTime)
    }
}