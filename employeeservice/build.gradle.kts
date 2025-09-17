extra["springCloudVersion"] = libs.versions.spring.cloud.get()

dependencies {
	implementation(project(":commonservice"))
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")
	implementation("org.axonframework:axon-spring-boot-starter:4.12.1")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.13")
	implementation("org.springframework.cloud:spring-cloud-starter-config")
	compileOnly("org.projectlombok:lombok")
	runtimeOnly("com.h2database:h2")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

dependencyManagement {
	imports {
		mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
	}
}