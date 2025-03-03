plugins {
	kotlin("jvm") version "1.9.25"
	kotlin("plugin.spring") version "1.9.25"
	id("org.springframework.boot") version "3.2.2"
	id("io.spring.dependency-management") version "1.1.6"
	kotlin("plugin.jpa") version "1.9.25"
}

group = "lima.wallyson"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	// Spring Boot
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-web")

	// Kotlin + Jackson
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")

	// PostgreSQL Driver
	runtimeOnly("org.postgresql:postgresql")

	// DevTools (Apenas para desenvolvimento)
	developmentOnly("org.springframework.boot:spring-boot-devtools")

	// SWAGGER
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.2.0")

	// Web3J -- SmartContract
	implementation("org.web3j:core:4.9.6")
	implementation("org.web3j:crypto:4.9.6")
	implementation("org.web3j:contracts:4.9.6")

	// Testes
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testImplementation("org.springframework.security:spring-security-test")
	testImplementation("org.mockito:mockito-core:5.8.0") // 🔄 Versão atualizada
	testImplementation("org.mockito.kotlin:mockito-kotlin:5.1.0") // 🔄 Versão atualizada
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
	jvmToolchain(21) // ✅ Melhor forma de configurar o Java 21
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
