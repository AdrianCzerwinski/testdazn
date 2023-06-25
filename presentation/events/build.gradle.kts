plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    kotlin("kapt")
}

android {
    namespace = "pl.adrianczerwinski.prostafaktura.features.main"

    composeOptions {
        kotlinCompilerExtensionVersion = AppData.kotlinCompilerExtensionVersion
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    kotlinOptions {
        jvmTarget = AppData.jvmTarget
    }

    tasks.withType<io.gitlab.arturbosch.detekt.Detekt>().configureEach {
        jvmTarget = AppData.jvmTarget
    }
}

dependencies {
    implementation(project(":core:ui"))
    implementation(project(":core:common"))
    implementation(project(":domain:events"))
    implementation(project(":data:events"))

    implementation(Dependencies.coreKtx)
    implementation(Dependencies.lifecycleRuntimeKtx)
    implementation(Dependencies.activityCompose)

    implementation(Dependencies.Compose.ui)
    implementation(Dependencies.Compose.uiGraphics)
    implementation(Dependencies.Compose.uiToolingPreview)
    implementation(Dependencies.Compose.material3)
    implementation(Dependencies.Compose.lifeCycle)
    implementation(Dependencies.Compose.viewModel)
    implementation(Dependencies.Compose.coil)

    implementation(Dependencies.DI.hiltComposeNavigation)
    implementation(Dependencies.DI.hilt)
    kapt(Dependencies.DI.hiltCompiler)
    kapt(Dependencies.DI.hiltCoreCompiler)

    testImplementation(Dependencies.Test.junit)
    testImplementation(Dependencies.Test.mockk)
    testImplementation(Dependencies.Test.coroutinesTest)
    testImplementation(Dependencies.Test.turbine)
    testImplementation(Dependencies.Test.truth)
}