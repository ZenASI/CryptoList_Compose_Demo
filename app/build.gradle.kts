plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id ("kotlin-kapt")
    id ("dagger.hilt.android.plugin")
    id ("kotlin-parcelize")
}

android {
    namespace = "com.zenasi.cryptolist_compose_demo"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.zenasi.cryptolist_compose_demo"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    signingConfigs {
        create("release") {
            keyAlias = "releasekey"
            keyPassword = "releasekey"
            storeFile = file("../gradle/release.jks")
            storePassword = "releasekey"
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            signingConfig = signingConfigs.getByName("release")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
//        debug {
//            isMinifyEnabled = true
//            proguardFiles(
//                getDefaultProguardFile("proguard-android-optimize.txt"),
//                "proguard-rules.pro"
//            )
//        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation("androidx.activity:activity-compose:1.7.2")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation ("androidx.compose.material:material-icons-extended:1.5.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    implementation ("androidx.compose.runtime:runtime-livedata:1.5.4")

    // okhttp
    implementation ("com.squareup.okhttp3:okhttp:4.11.0")
    implementation ("com.squareup.okhttp3:logging-interceptor:4.11.0")
    testImplementation ("com.squareup.okhttp3:mockwebserver:4.11.0")
    // retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    // retrofit & moshi gson convert
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.squareup.retrofit2:converter-moshi:2.9.0")
    // gson
    implementation ("com.google.code.gson:gson:2.9.0")
    // hilt
    implementation ("com.google.dagger:hilt-android:2.44")
    kapt ("com.google.dagger:hilt-android-compiler:2.44")

    // paging
    implementation ("androidx.paging:paging-runtime-ktx:3.1.1")
    // optional - RxJava3 support
    implementation ("androidx.paging:paging-rxjava3:3.1.1")
    // optional - Jetpack Compose integration
    implementation ("androidx.paging:paging-compose:1.0.0-alpha15")

    // coroutines
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.2")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.2")

    // android lifecycle
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.1")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.5.1")
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.5.1")
    implementation ("androidx.lifecycle:lifecycle-process:2.5.1")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.0-alpha01")

    // moshi
    implementation ("com.squareup.moshi:moshi:1.14.0")
    implementation ("com.squareup.moshi:moshi-kotlin:1.14.0")
    kapt ("com.squareup.moshi:moshi-kotlin-codegen:1.14.0")

    // coil
    implementation ("io.coil-kt:coil-compose:2.1.0")

    // navigation
    implementation ("androidx.navigation:navigation-compose:2.5.1")

    // dataStore
    implementation ("androidx.datastore:datastore-preferences:1.0.0")
    implementation ("androidx.datastore:datastore-preferences-core:1.0.0")
    implementation ("androidx.datastore:datastore:1.0.0")
    implementation ("androidx.datastore:datastore-core:1.0.0")

    // Room components
    implementation ("androidx.room:room-ktx:2.4.3")
    androidTestImplementation ("androidx.room:room-testing:2.4.3")
    kapt ("androidx.room:room-compiler:2.4.3")

    implementation ("com.github.PhilJay:MPAndroidChart:v3.1.0")
}