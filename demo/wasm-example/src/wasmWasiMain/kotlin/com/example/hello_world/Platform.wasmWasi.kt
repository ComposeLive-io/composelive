package com.example.hello_world

@WasmImport("env", "log_message")
external fun log_message()

@WasmExport("hello")
fun hello(a: Int, b: Int): Int {
    return 53 + a + b
}

//@OptIn(UnsafeWasmMemoryApi::class)
//@WasmExport("world")
//fun world(a: Int, b: Int) = withScopedMemoryAllocator {
//
//}

@WasmExport("run")
fun run() {
    log_message()
}

actual fun platform(): String = "I'm wasm wasi and I came with peace"
