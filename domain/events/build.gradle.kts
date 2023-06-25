plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    kotlin("kapt")
}

android {
    namespace = "pl.adrianczerwinski.testdazn.domain.events"

    composeOptions {
        kotlinCompilerExtensionVersion = AppData.kotlinCompilerExtensionVersion
    }
    kotlinOptions {
        jvmTarget = AppData.jvmTarget
    }

    tasks.withType<io.gitlab.arturbosch.detekt.Detekt>().configureEach {
        jvmTarget = AppData.jvmTarget
    }

    java {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
        kotlinOptions {
            jvmTarget = AppData.jvmTarget
        }
    }
}

dependencies {
    implementation(project(":data:events"))
    implementation(project(":core:common"))

    implementation(Dependencies.coreKtx)
    implementation(Dependencies.lifecycleRuntimeKtx)

    implementation(Dependencies.DI.hilt)
    kapt(Dependencies.DI.hiltCompiler)
    kapt(Dependencies.DI.hiltCoreCompiler)

    implementation(Dependencies.Compose.runtime)
}

kapt {
    correctErrorTypes = true
}

hilt {
    enableAggregatingTask = true
}