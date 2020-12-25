plugins {
    kotlin("multiplatform")
    id("com.android.library")
}

group = "com.bomiyr.ekho"
version = "0.1"

kotlin {
    explicitApi()

    android()
//    ios {
//        binaries {
//            framework {
//                baseName = "ekho-lib"
//            }
//        }
//    }
    jvm()

    sourceSets {
        val commonMain by getting
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(project(mapOf("path" to ":jvmmessage")))
            }
        }
        val androidTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
                implementation("junit:junit:4.13.1")
                implementation("org.robolectric:robolectric:4.4")
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation(project(mapOf("path" to ":jvmmessage")))
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
                implementation("junit:junit:4.13.1")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.4.1")
            }
        }
//        val iosMain by getting
//        val iosTest by getting
    }
}

android {
    compileSdkVersion(30)
    defaultConfig {
        minSdkVersion(16)
        targetSdkVersion(30)
    }
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    compileOptions {
        sourceCompatibility(1.8)
        targetCompatibility(1.8)
    }
}

//val packForXcode by tasks.creating(Sync::class) {
//    group = "build"
//    val mode = System.getenv("CONFIGURATION") ?: "DEBUG"
//    val sdkName = System.getenv("SDK_NAME") ?: "iphonesimulator"
//    val targetName = "ios" + if (sdkName.startsWith("iphoneos")) "Arm64" else "X64"
//    val framework =
//        kotlin.targets.getByName<KotlinNativeTarget>(targetName).binaries.getFramework(mode)
//    inputs.property("mode", mode)
//    dependsOn(framework.linkTask)
//    val targetDir = File(buildDir, "xcode-frameworks")
//    from({ framework.outputDirectory })
//    into(targetDir)
//}
//tasks.getByName("build").dependsOn(packForXcode)