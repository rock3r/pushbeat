plugins {
    `kotlin-dsl`
}

repositories {
    mavenLocal()
    mavenCentral()
    jcenter()
}

dependencies {
    implementation("com.atlassian.commonmark:commonmark:0.13.0")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.3.72")
    implementation("org.eclipse.jgit:org.eclipse.jgit:5.5.1.201910021850-r")
    implementation("com.squareup.okhttp3:okhttp:4.2.0")
    implementation(gradleApi())
}
