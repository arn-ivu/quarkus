package io.quarkus.vertx.http.runtime.management;

import java.time.Duration;
import java.util.Map;
import java.util.Optional;

import io.quarkus.runtime.LaunchMode;
import io.quarkus.runtime.annotations.ConfigItem;
import io.quarkus.runtime.annotations.ConfigPhase;
import io.quarkus.runtime.annotations.ConfigRoot;
import io.quarkus.vertx.http.runtime.BodyConfig;
import io.quarkus.vertx.http.runtime.FilterConfig;
import io.quarkus.vertx.http.runtime.HeaderConfig;
import io.quarkus.vertx.http.runtime.ProxyConfig;
import io.quarkus.vertx.http.runtime.ServerLimitsConfig;
import io.quarkus.vertx.http.runtime.ServerSslConfig;

/**
 * Configures the management interface.
 * Note that the management interface must be enabled using the
 * {@link ManagementInterfaceBuildTimeConfig#enabled} build-time property.
 */
@ConfigRoot(phase = ConfigPhase.RUN_TIME, name = "management")
public class ManagementInterfaceConfiguration {

    /**
     * Authentication configuration
     */
    public ManagementRuntimeAuthConfig auth;

    /**
     * The HTTP port
     */
    @ConfigItem(defaultValue = "9000")
    public int port;

    /**
     * The HTTP port
     */
    @ConfigItem(defaultValue = "9001")
    public int testPort;

    /**
     * The HTTP host
     * <p>
     * In dev/test mode this defaults to localhost, in prod mode this defaults to 0.0.0.0
     * <p>
     * Defaulting to 0.0.0.0 makes it easier to deploy Quarkus to container, however it
     * is not suitable for dev/test mode as other people on the network can connect to your
     * development machine.
     * <p>
     * As an exception, when running in Windows Subsystem for Linux (WSL), the HTTP host
     * defaults to 0.0.0.0 even in dev/test mode since using localhost makes the application
     * inaccessible.
     */
    @ConfigItem
    public String host;

    /**
     * Enable listening to host:port
     */
    @ConfigItem(defaultValue = "true")
    public boolean hostEnabled;

    /**
     * The SSL config
     */
    public ServerSslConfig ssl;

    /**
     * The name of the TLS configuration to use.
     * <p>
     * If not set and the default TLS configuration is configured ({@code quarkus.tls.*}) then that will be used.
     * If a name is configured, it uses the configuration from {@code quarkus.tls.<name>.*}
     * If a name is configured, but no TLS configuration is found with that name then an error will be thrown.
     * <p>
     * If no TLS configuration is set, and {@code quarkus.tls.*} is not configured, then, `quarkus.management.ssl` will be used.
     */
    @ConfigItem
    public Optional<String> tlsConfigurationName;

    /**
     * When set to {@code true}, the HTTP server automatically sends `100 CONTINUE`
     * response when the request expects it (with the `Expect: 100-Continue` header).
     */
    @ConfigItem(defaultValue = "false", name = "handle-100-continue-automatically")
    public boolean handle100ContinueAutomatically;

    /**
     * Server limits configuration
     */
    public ServerLimitsConfig limits;

    /**
     * Http connection idle timeout
     */
    @ConfigItem(defaultValue = "30M", name = "idle-timeout")
    public Duration idleTimeout;

    /**
     * Request body related settings
     */
    public BodyConfig body;

    /**
     * The accept backlog, this is how many connections can be waiting to be accepted before connections start being rejected
     */
    @ConfigItem(defaultValue = "-1")
    public int acceptBacklog;

    /**
     * Path to a unix domain socket
     */
    @ConfigItem(defaultValue = "/var/run/io.quarkus.management.socket")
    public String domainSocket;

    /**
     * Enable listening to host:port
     */
    @ConfigItem
    public boolean domainSocketEnabled;

    /**
     * Additional HTTP Headers always sent in the response
     */
    @ConfigItem
    public Map<String, HeaderConfig> header;

    /**
     * Additional HTTP configuration per path
     */
    @ConfigItem
    public Map<String, FilterConfig> filter;

    public ProxyConfig proxy;

    public int determinePort(LaunchMode launchMode) {
        return launchMode == LaunchMode.TEST ? testPort : port;
    }

}
