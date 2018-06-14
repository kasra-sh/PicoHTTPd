# PicoHTTPd
[![](https://jitpack.io/v/kasra-sh/nanoserver.svg)](https://jitpack.io/#kasra-sh/nanoserver)
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
	compile 'com.github.kasra-sh:nanoserver:0.8.0'
}
```

#### Usage :
```java
// Use default config
Nano n = Nano.getDefault();

// or custom config
n = Nano.get().setExecutor(Executors.newWorkStealingPool(4));
// or
n = Nano.get().setExecutor(new ForkJoinPool(4));

// Set handler
n.setHandler(new HTTPHandler() {
    @Override
    public void handleRequest(Request r, ResponseWriter w) {
        // ResponseWriter accepts Response/byte[] or an InputStream with a byte-array as header
        w.write(Response.makeText(200,"Welcome to NanoServer!\n".getBytes()));
    }
});

// Listen for connections on port 8000
n.start(8000);
```
#### TODO :
- WebSocket support.