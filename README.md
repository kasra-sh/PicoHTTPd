# PicoHTTPd
[![](https://jitpack.io/v/kasra-sh/picohttpd.svg)](https://jitpack.io/#kasra-sh/nanoserver)
### Minimal Embeddable NIO HTTP Server

#### Import :
Include these in your project's build.gradle file
```java
repositories {
	...
	maven { url 'https://jitpack.io' }
}
```
```java
dependencies {
	...
	compile 'com.github.kasra-sh:picohttpd:0.8.4'
}
```

#### Usage :
```java
// Use default config
Pico pico = Pico.getDefault();

// or custom config
pico = Pico.get().setExecutor(Executors.newWorkStealingPool(4));
// or
pico = Pico.get().setExecutor(new ForkJoinPool(4));

// Set handler
pico.setHandler(new HTTPHandler() {
    @Override
    public void handleRequest(Request r, ResponseWriter w) {
        // ResponseWriter accepts Response/byte[] or an InputStream with a byte-array as header
        w.write(Response.makeText(200,"Welcome to NanoServer!\n".getBytes()));
    }
});

// Listen for connections on port 8000
pico.start(8000);
```
#### TODO :
- WebSocket support.