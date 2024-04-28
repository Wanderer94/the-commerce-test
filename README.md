# the-commerce-test
더 커머스 백엔드 개발자 기술 과제

## Installation
Clone repository and install dependencies.
```bash
$ git clone https://github.com/Wanderer94/the-commerce-test.git
$ cd the-commerce-test
```
build.Gradle은 다음과 같습니다
```bash
plugins {
	id 'java'
	id 'org.springframework.boot' version '3.2.5'
	id 'io.spring.dependency-management' version '1.1.4'
}

group = 'the-commerce'
version = '0.0.1-SNAPSHOT'

java {
	sourceCompatibility = '17'
}

repositories {
	mavenCentral()
}

dependencies {
	implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
	implementation 'org.springframework.boot:spring-boot-starter-thymeleaf'
	implementation 'org.springframework.boot:spring-boot-starter-web'
	runtimeOnly 'com.h2database:h2'
	testImplementation ('org.springframework.boot:spring-boot-starter-test'){
		exclude group: 'org.junit.vintage', module: 'junit-vintage-engine'
	}
}

tasks.named('test') {
	useJUnitPlatform()
}
```

# API 명세
