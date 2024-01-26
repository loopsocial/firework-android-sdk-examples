// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.2.2" apply false
    id("com.android.library") version "8.2.2" apply false
    id("org.jetbrains.kotlin.android") version "1.9.21" apply false
    id("org.jlleitschuh.gradle.ktlint") version "12.0.3" apply false
    id("io.gitlab.arturbosch.detekt") version "1.23.4" apply false
}

task<Delete>("clean") {
    delete = setOf(rootProject.buildDir)
}
