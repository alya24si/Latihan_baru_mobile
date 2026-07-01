plugins {
    alias(libs.plugins.android.application)
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.example.alya_love"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.alya_love"
        minSdk = 27
        targetSdk = 36

        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    implementation("de.hdodenhof:circleimageview:3.1.0")

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.cardview:cardview:1.0.0")

    implementation("androidx.recyclerview:recyclerview:1.3.2")
    implementation("androidx.viewpager2:viewpager2:1.0.0")
    implementation("com.github.bumptech.glide:glide:4.16.0")
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")


    val room_version = "2.7.0"
    implementation("androidx.room:room-runtime:$room_version")
    implementation("androidx.room:room-ktx:$room_version")
    ksp("androidx.room:room-compiler:$room_version")

    // ✅ DEPENDENCIES BARU UNTUK KAMERA & QR CODE
    implementation("com.google.zxing:core:3.5.2")
    implementation("com.journeyapps:zxing-android-embedded:4.3.0")
    implementation("com.google.mlkit:barcode-scanning:17.2.0")

    val cameraxVersion = "1.3.1"
    implementation("androidx.camera:camera-core:${cameraxVersion}")
    implementation("androidx.camera:camera-camera2:${cameraxVersion}")
    implementation("androidx.camera:camera-lifecycle:${cameraxVersion}")
    implementation("androidx.camera:camera-view:${cameraxVersion}")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
}