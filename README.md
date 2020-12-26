# Ekho
Simple [almost]multiplatform Kotlin logging library. 

![Automated tests](https://github.com/bomiyr/ekho/workflows/Automated%20tests/badge.svg?event=push)

# Available targets:
- [x] Android
- [x] Jvm

# Usage

## Common
### Configuration
The main class of this library is `Ekho`. 

You need to create instance of `Ekho` and configure with reflections.

`val ekhoInstance = Ekho()`

If you don't want to provide this instance to all classes in your application, it is possible to create singleton object and use it everywhere:

`object EkhoLogger: Ekho()`

Ekho has reflections - target classes for your logs. Ekho can have many reflections for diffeent porpouses. For example, one reflection logs messages to system logs, second sends log messages to server, third writes logs to local file etc.
You can manage reflections by methods 
```
ekho.addReflection(reflection: EkhoReflection)
ekho.addReflections(reflections: List<EkhoReflection>)
ekho.removeReflection(reflection: EkhoReflection) 
ekho.removeReflections(reflections: List<EkhoReflection>)
```
Be aware that adding/removing reflections is NOT THREAD SAFE

To create custom reflection just implement `EkhoReflection` interface and add this reflection to Ekho. Every reflection should provide `EkhoFilter`. 
Standard filters in ekho:
- `LogAll` logs all messages
- `MinLogLevel` logs only messages with provided `EkhoLevel` and higher priority (e.g. `MinLogLevel(INFO)` will log messages with INFO, WARN, ERROR and ASSERT)
- `NoLogs` skips all messages


### Logging
Use overloaded log methods:

- `ekho.v(...)` for VERBOSE messages
- `ekho.d(...)` for DEBUG messages
- `ekho.i(...)` for INFO messages
- `ekho.w(...)` for WARN messages
- `ekho.e(...)` for ERROR messages
- `ekho.wtf(...)` for ASSERT messages (What a Terrible Failure)
- `ekho.log(level: EkhoLevel, tag: String?, error: Throwable?, message: String?, lazyMessage: (() -> String)?)` for fully custom message. Generally, you don't need to use this, but it's not forbiden. Actually, all log messages from above methods will call this method at the end.

It is possible to create Ekho wrapper whith constant tag by calling `ekho.tagged("MyTag")`


## Android
Android version of Ekho provides `AndroidLogReflection` class for log into logcat. It is possible to configure logs filter and enable/disable debug logs, generated from current stacktrace.

## Jvm
Jvm version of Ekho provides `SystemOutReflection` class for log into System.out. It is possible to configure logs filter and enable/disable debug logs, generated from current stacktrace.


Mainly inspired by [Timber](https://github.com/JakeWharton/timber)
