plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("io.gitlab.arturbosch.detekt") version "1.22.0"
    id("com.google.dagger.hilt.android")
    kotlin("kapt")
}

detekt {
    toolVersion = Versions.detekt
    config = files("$rootDir/gradle/detekt-config.yml")
    parallel = true

    buildUponDefaultConfig = true
}

android {
    namespace = AppData.applicationId

    composeOptions {
        kotlinCompilerExtensionVersion = AppData.kotlinCompilerExtensionVersion
    }

    defaultConfig.applicationId = AppData.applicationId
}

dependencies {
    implementation(project(":core:ui"))
    implementation(project(":core:common"))
    implementation(project(":presentation:events"))
    implementation(project(":presentation:schedule"))

    implementation(Dependencies.coreKtx)
    implementation(Dependencies.lifecycleRuntimeKtx)
    implementation(Dependencies.activityCompose)

    implementation(Dependencies.Compose.ui)
    implementation(Dependencies.Compose.uiGraphics)
    implementation(Dependencies.Compose.uiToolingPreview)
    implementation(Dependencies.Compose.material3)
    implementation(Dependencies.Compose.runtime)
    implementation(Dependencies.Compose.systemUiController)
    implementation(Dependencies.Compose.navigation)
    implementation(Dependencies.Compose.navigationAnimation)

    implementation(Dependencies.DI.hilt)

    kapt(Dependencies.DI.hiltCompiler)
    kapt(Dependencies.DI.hiltCoreCompiler)

    testImplementation(Dependencies.Test.junit)
}

kapt {
    correctErrorTypes = true
}
