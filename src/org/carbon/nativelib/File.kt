package org.carbon.nativelib

import org.carbon.runtime.*
import java.nio.file.Files
import java.nio.file.Path

object FileConstructor : Callable() {
    override fun apply(arguments: List<CarbonObject>): CarbonObject {
        assert(arguments.size == 1)

        val path = arguments[0] as CarbonString

        return FileResource(path.value)
    }
}

class FileResource(val path: String) : Composite() {
    // Just assume the file exists for now
    val body: String = Files.readString(Path.of(path))

    override fun getMember(name: String): CarbonObject? =
        when (name) {
            // This should probably be some kind of nullable type
            "Contents" -> CarbonString(body)
            else -> null
        }
}
