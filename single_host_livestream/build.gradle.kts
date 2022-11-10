// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "7.3.1" apply false
    id("com.android.library") version "7.3.1" apply false
    id("org.jetbrains.kotlin.android") version "1.6.10" apply false
    id("org.jlleitschuh.gradle.ktlint") version "10.3.0" apply false
    id("io.gitlab.arturbosch.detekt") version "1.20.0" apply false
}

task<Delete>("clean") {
    delete = setOf(rootProject.buildDir)
}
