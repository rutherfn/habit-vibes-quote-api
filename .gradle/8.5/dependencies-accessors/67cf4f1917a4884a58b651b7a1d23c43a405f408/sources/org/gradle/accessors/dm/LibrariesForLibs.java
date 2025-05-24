package org.gradle.accessors.dm;

import org.gradle.api.NonNullApi;
import org.gradle.api.artifacts.MinimalExternalModuleDependency;
import org.gradle.plugin.use.PluginDependency;
import org.gradle.api.artifacts.ExternalModuleDependencyBundle;
import org.gradle.api.artifacts.MutableVersionConstraint;
import org.gradle.api.provider.Provider;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.provider.ProviderFactory;
import org.gradle.api.internal.catalog.AbstractExternalDependencyFactory;
import org.gradle.api.internal.catalog.DefaultVersionCatalog;
import java.util.Map;
import org.gradle.api.internal.attributes.ImmutableAttributesFactory;
import org.gradle.api.internal.artifacts.dsl.CapabilityNotationParser;
import javax.inject.Inject;

/**
 * A catalog of dependencies accessible via the `libs` extension.
 */
@NonNullApi
public class LibrariesForLibs extends AbstractExternalDependencyFactory {

    private final AbstractExternalDependencyFactory owner = this;
    private final DotenvLibraryAccessors laccForDotenvLibraryAccessors = new DotenvLibraryAccessors(owner);
    private final ExposedLibraryAccessors laccForExposedLibraryAccessors = new ExposedLibraryAccessors(owner);
    private final JaywayLibraryAccessors laccForJaywayLibraryAccessors = new JaywayLibraryAccessors(owner);
    private final KotlinLibraryAccessors laccForKotlinLibraryAccessors = new KotlinLibraryAccessors(owner);
    private final KotlinxLibraryAccessors laccForKotlinxLibraryAccessors = new KotlinxLibraryAccessors(owner);
    private final KtorLibraryAccessors laccForKtorLibraryAccessors = new KtorLibraryAccessors(owner);
    private final LogbackLibraryAccessors laccForLogbackLibraryAccessors = new LogbackLibraryAccessors(owner);
    private final VersionAccessors vaccForVersionAccessors = new VersionAccessors(providers, config);
    private final BundleAccessors baccForBundleAccessors = new BundleAccessors(objects, providers, config, attributesFactory, capabilityNotationParser);
    private final PluginAccessors paccForPluginAccessors = new PluginAccessors(providers, config);

    @Inject
    public LibrariesForLibs(DefaultVersionCatalog config, ProviderFactory providers, ObjectFactory objects, ImmutableAttributesFactory attributesFactory, CapabilityNotationParser capabilityNotationParser) {
        super(config, providers, objects, attributesFactory, capabilityNotationParser);
    }

        /**
         * Creates a dependency provider for h2 (com.h2database:h2)
     * with versionRef 'h2.version'.
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getH2() {
            return create("h2");
    }

        /**
         * Creates a dependency provider for postgresql (org.postgresql:postgresql)
     * with versionRef 'postgres.version'.
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getPostgresql() {
            return create("postgresql");
    }

        /**
         * Creates a dependency provider for swagger (io.ktor:ktor-server-swagger)
     * with versionRef 'ktor.server.swagger.version'.
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getSwagger() {
            return create("swagger");
    }

    /**
     * Returns the group of libraries at dotenv
     */
    public DotenvLibraryAccessors getDotenv() {
        return laccForDotenvLibraryAccessors;
    }

    /**
     * Returns the group of libraries at exposed
     */
    public ExposedLibraryAccessors getExposed() {
        return laccForExposedLibraryAccessors;
    }

    /**
     * Returns the group of libraries at jayway
     */
    public JaywayLibraryAccessors getJayway() {
        return laccForJaywayLibraryAccessors;
    }

    /**
     * Returns the group of libraries at kotlin
     */
    public KotlinLibraryAccessors getKotlin() {
        return laccForKotlinLibraryAccessors;
    }

    /**
     * Returns the group of libraries at kotlinx
     */
    public KotlinxLibraryAccessors getKotlinx() {
        return laccForKotlinxLibraryAccessors;
    }

    /**
     * Returns the group of libraries at ktor
     */
    public KtorLibraryAccessors getKtor() {
        return laccForKtorLibraryAccessors;
    }

    /**
     * Returns the group of libraries at logback
     */
    public LogbackLibraryAccessors getLogback() {
        return laccForLogbackLibraryAccessors;
    }

    /**
     * Returns the group of versions at versions
     */
    public VersionAccessors getVersions() {
        return vaccForVersionAccessors;
    }

    /**
     * Returns the group of bundles at bundles
     */
    public BundleAccessors getBundles() {
        return baccForBundleAccessors;
    }

    /**
     * Returns the group of plugins at plugins
     */
    public PluginAccessors getPlugins() {
        return paccForPluginAccessors;
    }

    public static class DotenvLibraryAccessors extends SubDependencyFactory {

        public DotenvLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

            /**
             * Creates a dependency provider for kotlin (io.github.cdimascio:dotenv-kotlin)
         * with versionRef 'dotenv.kotlin.version'.
             * This dependency was declared in catalog libs.versions.toml
             */
            public Provider<MinimalExternalModuleDependency> getKotlin() {
                return create("dotenv.kotlin");
        }

    }

    public static class ExposedLibraryAccessors extends SubDependencyFactory {

        public ExposedLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

            /**
             * Creates a dependency provider for core (org.jetbrains.exposed:exposed-core)
         * with versionRef 'exposed.version'.
             * This dependency was declared in catalog libs.versions.toml
             */
            public Provider<MinimalExternalModuleDependency> getCore() {
                return create("exposed.core");
        }

            /**
             * Creates a dependency provider for dao (org.jetbrains.exposed:exposed-dao)
         * with versionRef 'exposed.version'.
             * This dependency was declared in catalog libs.versions.toml
             */
            public Provider<MinimalExternalModuleDependency> getDao() {
                return create("exposed.dao");
        }

            /**
             * Creates a dependency provider for jdbc (org.jetbrains.exposed:exposed-jdbc)
         * with versionRef 'exposed.version'.
             * This dependency was declared in catalog libs.versions.toml
             */
            public Provider<MinimalExternalModuleDependency> getJdbc() {
                return create("exposed.jdbc");
        }

    }

    public static class JaywayLibraryAccessors extends SubDependencyFactory {

        public JaywayLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

            /**
             * Creates a dependency provider for jsonpath (com.jayway.jsonpath:json-path)
         * with versionRef 'jayway.jsonpath.version'.
             * This dependency was declared in catalog libs.versions.toml
             */
            public Provider<MinimalExternalModuleDependency> getJsonpath() {
                return create("jayway.jsonpath");
        }

    }

    public static class KotlinLibraryAccessors extends SubDependencyFactory {
        private final KotlinTestLibraryAccessors laccForKotlinTestLibraryAccessors = new KotlinTestLibraryAccessors(owner);

        public KotlinLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Returns the group of libraries at kotlin.test
         */
        public KotlinTestLibraryAccessors getTest() {
            return laccForKotlinTestLibraryAccessors;
        }

    }

    public static class KotlinTestLibraryAccessors extends SubDependencyFactory {

        public KotlinTestLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

            /**
             * Creates a dependency provider for junit (org.jetbrains.kotlin:kotlin-test-junit)
         * with versionRef 'kotlin.version'.
             * This dependency was declared in catalog libs.versions.toml
             */
            public Provider<MinimalExternalModuleDependency> getJunit() {
                return create("kotlin.test.junit");
        }

    }

    public static class KotlinxLibraryAccessors extends SubDependencyFactory {
        private final KotlinxSerializationLibraryAccessors laccForKotlinxSerializationLibraryAccessors = new KotlinxSerializationLibraryAccessors(owner);

        public KotlinxLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Returns the group of libraries at kotlinx.serialization
         */
        public KotlinxSerializationLibraryAccessors getSerialization() {
            return laccForKotlinxSerializationLibraryAccessors;
        }

    }

    public static class KotlinxSerializationLibraryAccessors extends SubDependencyFactory {

        public KotlinxSerializationLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

            /**
             * Creates a dependency provider for json (org.jetbrains.kotlinx:kotlinx-serialization-json)
         * with versionRef 'serialization.json.version'.
             * This dependency was declared in catalog libs.versions.toml
             */
            public Provider<MinimalExternalModuleDependency> getJson() {
                return create("kotlinx.serialization.json");
        }

    }

    public static class KtorLibraryAccessors extends SubDependencyFactory {
        private final KtorSerializationLibraryAccessors laccForKtorSerializationLibraryAccessors = new KtorSerializationLibraryAccessors(owner);
        private final KtorServerLibraryAccessors laccForKtorServerLibraryAccessors = new KtorServerLibraryAccessors(owner);

        public KtorLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Returns the group of libraries at ktor.serialization
         */
        public KtorSerializationLibraryAccessors getSerialization() {
            return laccForKtorSerializationLibraryAccessors;
        }

        /**
         * Returns the group of libraries at ktor.server
         */
        public KtorServerLibraryAccessors getServer() {
            return laccForKtorServerLibraryAccessors;
        }

    }

    public static class KtorSerializationLibraryAccessors extends SubDependencyFactory {
        private final KtorSerializationKotlinxLibraryAccessors laccForKtorSerializationKotlinxLibraryAccessors = new KtorSerializationKotlinxLibraryAccessors(owner);

        public KtorSerializationLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Returns the group of libraries at ktor.serialization.kotlinx
         */
        public KtorSerializationKotlinxLibraryAccessors getKotlinx() {
            return laccForKtorSerializationKotlinxLibraryAccessors;
        }

    }

    public static class KtorSerializationKotlinxLibraryAccessors extends SubDependencyFactory {

        public KtorSerializationKotlinxLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

            /**
             * Creates a dependency provider for json (io.ktor:ktor-serialization-kotlinx-json)
         * with versionRef 'ktor.version'.
             * This dependency was declared in catalog libs.versions.toml
             */
            public Provider<MinimalExternalModuleDependency> getJson() {
                return create("ktor.serialization.kotlinx.json");
        }

    }

    public static class KtorServerLibraryAccessors extends SubDependencyFactory {
        private final KtorServerConfigLibraryAccessors laccForKtorServerConfigLibraryAccessors = new KtorServerConfigLibraryAccessors(owner);
        private final KtorServerContentLibraryAccessors laccForKtorServerContentLibraryAccessors = new KtorServerContentLibraryAccessors(owner);
        private final KtorServerHostLibraryAccessors laccForKtorServerHostLibraryAccessors = new KtorServerHostLibraryAccessors(owner);
        private final KtorServerStatusLibraryAccessors laccForKtorServerStatusLibraryAccessors = new KtorServerStatusLibraryAccessors(owner);
        private final KtorServerTestLibraryAccessors laccForKtorServerTestLibraryAccessors = new KtorServerTestLibraryAccessors(owner);

        public KtorServerLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

            /**
             * Creates a dependency provider for auth (io.ktor:ktor-server-auth)
         * with versionRef 'ktor.server.auth.version'.
             * This dependency was declared in catalog libs.versions.toml
             */
            public Provider<MinimalExternalModuleDependency> getAuth() {
                return create("ktor.server.auth");
        }

            /**
             * Creates a dependency provider for core (io.ktor:ktor-server-core)
         * with versionRef 'ktor.version'.
             * This dependency was declared in catalog libs.versions.toml
             */
            public Provider<MinimalExternalModuleDependency> getCore() {
                return create("ktor.server.core");
        }

            /**
             * Creates a dependency provider for netty (io.ktor:ktor-server-netty)
         * with versionRef 'ktor.version'.
             * This dependency was declared in catalog libs.versions.toml
             */
            public Provider<MinimalExternalModuleDependency> getNetty() {
                return create("ktor.server.netty");
        }

        /**
         * Returns the group of libraries at ktor.server.config
         */
        public KtorServerConfigLibraryAccessors getConfig() {
            return laccForKtorServerConfigLibraryAccessors;
        }

        /**
         * Returns the group of libraries at ktor.server.content
         */
        public KtorServerContentLibraryAccessors getContent() {
            return laccForKtorServerContentLibraryAccessors;
        }

        /**
         * Returns the group of libraries at ktor.server.host
         */
        public KtorServerHostLibraryAccessors getHost() {
            return laccForKtorServerHostLibraryAccessors;
        }

        /**
         * Returns the group of libraries at ktor.server.status
         */
        public KtorServerStatusLibraryAccessors getStatus() {
            return laccForKtorServerStatusLibraryAccessors;
        }

        /**
         * Returns the group of libraries at ktor.server.test
         */
        public KtorServerTestLibraryAccessors getTest() {
            return laccForKtorServerTestLibraryAccessors;
        }

    }

    public static class KtorServerConfigLibraryAccessors extends SubDependencyFactory {

        public KtorServerConfigLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

            /**
             * Creates a dependency provider for yaml (io.ktor:ktor-server-config-yaml)
         * with versionRef 'ktor.version'.
             * This dependency was declared in catalog libs.versions.toml
             */
            public Provider<MinimalExternalModuleDependency> getYaml() {
                return create("ktor.server.config.yaml");
        }

    }

    public static class KtorServerContentLibraryAccessors extends SubDependencyFactory {

        public KtorServerContentLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

            /**
             * Creates a dependency provider for negotiation (io.ktor:ktor-server-content-negotiation)
         * with versionRef 'ktor.version'.
             * This dependency was declared in catalog libs.versions.toml
             */
            public Provider<MinimalExternalModuleDependency> getNegotiation() {
                return create("ktor.server.content.negotiation");
        }

    }

    public static class KtorServerHostLibraryAccessors extends SubDependencyFactory {

        public KtorServerHostLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

            /**
             * Creates a dependency provider for common (io.ktor:ktor-server-host-common)
         * with versionRef 'ktor.version'.
             * This dependency was declared in catalog libs.versions.toml
             */
            public Provider<MinimalExternalModuleDependency> getCommon() {
                return create("ktor.server.host.common");
        }

    }

    public static class KtorServerStatusLibraryAccessors extends SubDependencyFactory {

        public KtorServerStatusLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

            /**
             * Creates a dependency provider for pages (io.ktor:ktor-server-status-pages)
         * with versionRef 'ktor.version'.
             * This dependency was declared in catalog libs.versions.toml
             */
            public Provider<MinimalExternalModuleDependency> getPages() {
                return create("ktor.server.status.pages");
        }

    }

    public static class KtorServerTestLibraryAccessors extends SubDependencyFactory {

        public KtorServerTestLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

            /**
             * Creates a dependency provider for host (io.ktor:ktor-server-test-host)
         * with versionRef 'ktor.version'.
             * This dependency was declared in catalog libs.versions.toml
             */
            public Provider<MinimalExternalModuleDependency> getHost() {
                return create("ktor.server.test.host");
        }

    }

    public static class LogbackLibraryAccessors extends SubDependencyFactory {

        public LogbackLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

            /**
             * Creates a dependency provider for classic (ch.qos.logback:logback-classic)
         * with versionRef 'logback.version'.
             * This dependency was declared in catalog libs.versions.toml
             */
            public Provider<MinimalExternalModuleDependency> getClassic() {
                return create("logback.classic");
        }

    }

    public static class VersionAccessors extends VersionFactory  {

        private final DotenvVersionAccessors vaccForDotenvVersionAccessors = new DotenvVersionAccessors(providers, config);
        private final ExposedVersionAccessors vaccForExposedVersionAccessors = new ExposedVersionAccessors(providers, config);
        private final H2VersionAccessors vaccForH2VersionAccessors = new H2VersionAccessors(providers, config);
        private final JaywayVersionAccessors vaccForJaywayVersionAccessors = new JaywayVersionAccessors(providers, config);
        private final KotlinVersionAccessors vaccForKotlinVersionAccessors = new KotlinVersionAccessors(providers, config);
        private final KtlintVersionAccessors vaccForKtlintVersionAccessors = new KtlintVersionAccessors(providers, config);
        private final KtorVersionAccessors vaccForKtorVersionAccessors = new KtorVersionAccessors(providers, config);
        private final LogbackVersionAccessors vaccForLogbackVersionAccessors = new LogbackVersionAccessors(providers, config);
        private final PostgresVersionAccessors vaccForPostgresVersionAccessors = new PostgresVersionAccessors(providers, config);
        private final SerializationVersionAccessors vaccForSerializationVersionAccessors = new SerializationVersionAccessors(providers, config);
        public VersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Returns the group of versions at versions.dotenv
         */
        public DotenvVersionAccessors getDotenv() {
            return vaccForDotenvVersionAccessors;
        }

        /**
         * Returns the group of versions at versions.exposed
         */
        public ExposedVersionAccessors getExposed() {
            return vaccForExposedVersionAccessors;
        }

        /**
         * Returns the group of versions at versions.h2
         */
        public H2VersionAccessors getH2() {
            return vaccForH2VersionAccessors;
        }

        /**
         * Returns the group of versions at versions.jayway
         */
        public JaywayVersionAccessors getJayway() {
            return vaccForJaywayVersionAccessors;
        }

        /**
         * Returns the group of versions at versions.kotlin
         */
        public KotlinVersionAccessors getKotlin() {
            return vaccForKotlinVersionAccessors;
        }

        /**
         * Returns the group of versions at versions.ktlint
         */
        public KtlintVersionAccessors getKtlint() {
            return vaccForKtlintVersionAccessors;
        }

        /**
         * Returns the group of versions at versions.ktor
         */
        public KtorVersionAccessors getKtor() {
            return vaccForKtorVersionAccessors;
        }

        /**
         * Returns the group of versions at versions.logback
         */
        public LogbackVersionAccessors getLogback() {
            return vaccForLogbackVersionAccessors;
        }

        /**
         * Returns the group of versions at versions.postgres
         */
        public PostgresVersionAccessors getPostgres() {
            return vaccForPostgresVersionAccessors;
        }

        /**
         * Returns the group of versions at versions.serialization
         */
        public SerializationVersionAccessors getSerialization() {
            return vaccForSerializationVersionAccessors;
        }

    }

    public static class DotenvVersionAccessors extends VersionFactory  {

        private final DotenvKotlinVersionAccessors vaccForDotenvKotlinVersionAccessors = new DotenvKotlinVersionAccessors(providers, config);
        public DotenvVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Returns the group of versions at versions.dotenv.kotlin
         */
        public DotenvKotlinVersionAccessors getKotlin() {
            return vaccForDotenvKotlinVersionAccessors;
        }

    }

    public static class DotenvKotlinVersionAccessors extends VersionFactory  {

        public DotenvKotlinVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

            /**
             * Returns the version associated to this alias: dotenv.kotlin.version (6.4.1)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libs.versions.toml
             */
            public Provider<String> getVersion() { return getVersion("dotenv.kotlin.version"); }

    }

    public static class ExposedVersionAccessors extends VersionFactory  {

        public ExposedVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

            /**
             * Returns the version associated to this alias: exposed.version (0.61.0)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libs.versions.toml
             */
            public Provider<String> getVersion() { return getVersion("exposed.version"); }

    }

    public static class H2VersionAccessors extends VersionFactory  {

        public H2VersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

            /**
             * Returns the version associated to this alias: h2.version (2.3.232)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libs.versions.toml
             */
            public Provider<String> getVersion() { return getVersion("h2.version"); }

    }

    public static class JaywayVersionAccessors extends VersionFactory  {

        private final JaywayJsonpathVersionAccessors vaccForJaywayJsonpathVersionAccessors = new JaywayJsonpathVersionAccessors(providers, config);
        public JaywayVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Returns the group of versions at versions.jayway.jsonpath
         */
        public JaywayJsonpathVersionAccessors getJsonpath() {
            return vaccForJaywayJsonpathVersionAccessors;
        }

    }

    public static class JaywayJsonpathVersionAccessors extends VersionFactory  {

        public JaywayJsonpathVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

            /**
             * Returns the version associated to this alias: jayway.jsonpath.version (2.9.0)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libs.versions.toml
             */
            public Provider<String> getVersion() { return getVersion("jayway.jsonpath.version"); }

    }

    public static class KotlinVersionAccessors extends VersionFactory  {

        public KotlinVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

            /**
             * Returns the version associated to this alias: kotlin.version (2.0.0)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libs.versions.toml
             */
            public Provider<String> getVersion() { return getVersion("kotlin.version"); }

    }

    public static class KtlintVersionAccessors extends VersionFactory  {

        public KtlintVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

            /**
             * Returns the version associated to this alias: ktlint.version (12.2.0)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libs.versions.toml
             */
            public Provider<String> getVersion() { return getVersion("ktlint.version"); }

    }

    public static class KtorVersionAccessors extends VersionFactory  {

        private final KtorServerVersionAccessors vaccForKtorServerVersionAccessors = new KtorServerVersionAccessors(providers, config);
        public KtorVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

            /**
             * Returns the version associated to this alias: ktor.version (3.0.3)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libs.versions.toml
             */
            public Provider<String> getVersion() { return getVersion("ktor.version"); }

        /**
         * Returns the group of versions at versions.ktor.server
         */
        public KtorServerVersionAccessors getServer() {
            return vaccForKtorServerVersionAccessors;
        }

    }

    public static class KtorServerVersionAccessors extends VersionFactory  {

        private final KtorServerAuthVersionAccessors vaccForKtorServerAuthVersionAccessors = new KtorServerAuthVersionAccessors(providers, config);
        private final KtorServerSwaggerVersionAccessors vaccForKtorServerSwaggerVersionAccessors = new KtorServerSwaggerVersionAccessors(providers, config);
        public KtorServerVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Returns the group of versions at versions.ktor.server.auth
         */
        public KtorServerAuthVersionAccessors getAuth() {
            return vaccForKtorServerAuthVersionAccessors;
        }

        /**
         * Returns the group of versions at versions.ktor.server.swagger
         */
        public KtorServerSwaggerVersionAccessors getSwagger() {
            return vaccForKtorServerSwaggerVersionAccessors;
        }

    }

    public static class KtorServerAuthVersionAccessors extends VersionFactory  {

        public KtorServerAuthVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

            /**
             * Returns the version associated to this alias: ktor.server.auth.version (3.1.2)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libs.versions.toml
             */
            public Provider<String> getVersion() { return getVersion("ktor.server.auth.version"); }

    }

    public static class KtorServerSwaggerVersionAccessors extends VersionFactory  {

        public KtorServerSwaggerVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

            /**
             * Returns the version associated to this alias: ktor.server.swagger.version (2.3.4)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libs.versions.toml
             */
            public Provider<String> getVersion() { return getVersion("ktor.server.swagger.version"); }

    }

    public static class LogbackVersionAccessors extends VersionFactory  {

        public LogbackVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

            /**
             * Returns the version associated to this alias: logback.version (1.5.13)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libs.versions.toml
             */
            public Provider<String> getVersion() { return getVersion("logback.version"); }

    }

    public static class PostgresVersionAccessors extends VersionFactory  {

        public PostgresVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

            /**
             * Returns the version associated to this alias: postgres.version (42.7.5)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libs.versions.toml
             */
            public Provider<String> getVersion() { return getVersion("postgres.version"); }

    }

    public static class SerializationVersionAccessors extends VersionFactory  {

        private final SerializationJsonVersionAccessors vaccForSerializationJsonVersionAccessors = new SerializationJsonVersionAccessors(providers, config);
        public SerializationVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Returns the group of versions at versions.serialization.json
         */
        public SerializationJsonVersionAccessors getJson() {
            return vaccForSerializationJsonVersionAccessors;
        }

    }

    public static class SerializationJsonVersionAccessors extends VersionFactory  {

        public SerializationJsonVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

            /**
             * Returns the version associated to this alias: serialization.json.version (1.6.2)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libs.versions.toml
             */
            public Provider<String> getVersion() { return getVersion("serialization.json.version"); }

    }

    public static class BundleAccessors extends BundleFactory {

        public BundleAccessors(ObjectFactory objects, ProviderFactory providers, DefaultVersionCatalog config, ImmutableAttributesFactory attributesFactory, CapabilityNotationParser capabilityNotationParser) { super(objects, providers, config, attributesFactory, capabilityNotationParser); }

    }

    public static class PluginAccessors extends PluginFactory {
        private final KotlinPluginAccessors paccForKotlinPluginAccessors = new KotlinPluginAccessors(providers, config);

        public PluginAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

            /**
             * Creates a plugin provider for ktlint to the plugin id 'org.jlleitschuh.gradle.ktlint'
             * with versionRef 'ktlint.version'.
             * This plugin was declared in catalog libs.versions.toml
             */
            public Provider<PluginDependency> getKtlint() { return createPlugin("ktlint"); }

            /**
             * Creates a plugin provider for ktor to the plugin id 'io.ktor.plugin'
             * with versionRef 'ktor.version'.
             * This plugin was declared in catalog libs.versions.toml
             */
            public Provider<PluginDependency> getKtor() { return createPlugin("ktor"); }

        /**
         * Returns the group of plugins at plugins.kotlin
         */
        public KotlinPluginAccessors getKotlin() {
            return paccForKotlinPluginAccessors;
        }

    }

    public static class KotlinPluginAccessors extends PluginFactory {
        private final KotlinPluginPluginAccessors paccForKotlinPluginPluginAccessors = new KotlinPluginPluginAccessors(providers, config);

        public KotlinPluginAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

            /**
             * Creates a plugin provider for kotlin.jvm to the plugin id 'org.jetbrains.kotlin.jvm'
             * with versionRef 'kotlin.version'.
             * This plugin was declared in catalog libs.versions.toml
             */
            public Provider<PluginDependency> getJvm() { return createPlugin("kotlin.jvm"); }

        /**
         * Returns the group of plugins at plugins.kotlin.plugin
         */
        public KotlinPluginPluginAccessors getPlugin() {
            return paccForKotlinPluginPluginAccessors;
        }

    }

    public static class KotlinPluginPluginAccessors extends PluginFactory {

        public KotlinPluginPluginAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

            /**
             * Creates a plugin provider for kotlin.plugin.serialization to the plugin id 'org.jetbrains.kotlin.plugin.serialization'
             * with versionRef 'kotlin.version'.
             * This plugin was declared in catalog libs.versions.toml
             */
            public Provider<PluginDependency> getSerialization() { return createPlugin("kotlin.plugin.serialization"); }

    }

}
