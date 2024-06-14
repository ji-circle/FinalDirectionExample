plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("kotlin-kapt")
    id("kotlin-parcelize")
    //firebase sdk
    //id("com.google.gms.google-services")
    //id("kotlin-android-extensions")



}


android {
    namespace = "com.example.finaldirectionexample01"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.finaldirectionexample01"
        minSdk = 33
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
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
    implementation(libs.google.material)
    implementation(libs.androidx.navigation.fragment)
    implementation(libs.google.material)
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.navigation.runtime.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //coil
    implementation(libs.coil)

    // firebase - firestore, FCM
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.storage)
    implementation(libs.firebase.messaging)
    implementation(libs.firebase.auth)
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.androidx.activity.ktx)

    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.navigation.runtime.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //viewpager2
    implementation(libs.androidx.viewpager2)
    implementation("com.tbuonomo:dotsindicator:5.0")

    //recyclerview
    implementation(libs.androidx.recyclerview)

    //fragment
    implementation(libs.androidx.fragment)
    implementation(libs.androidx.fragment.ktx)

    //retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)

    //room
    implementation(libs.room.runtime)
    implementation(libs.androidx.room.paging)
    kapt(libs.room.compiler)

    //google map
    implementation ("com.google.android.gms:play-services-maps:18.1.0")
    implementation ("com.google.android.gms:play-services-location:21.0.1")

    //chip
    implementation("com.google.android.material:material:1.4.0")

    //json
    implementation("com.google.code.gson:gson:2.8.9")

    //google places
    implementation(platform("org.jetbrains.kotlin:kotlin-bom:1.9.0"))
    implementation ("com.google.android.libraries.places:places:3.3.0")
    implementation(libs.volley)

    val lifecycle_version = "2.8.1"
    // ViewModel
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version")
    // LiveData
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version")
    implementation("androidx.fragment:fragment-ktx:1.4.1")
    //firebase bom
    implementation(platform("com.google.firebase:firebase-bom:33.1.0"))
    implementation("com.google.firebase:firebase-analytics")

    //프로필 이미지뷰
    implementation("de.hdodenhof:circleimageview:3.1.0")


    implementation("com.google.firebase:firebase-auth-ktx")

    implementation ("androidx.fragment:fragment-ktx:1.3.6")


    //polyline?
    implementation("com.google.maps.android:android-maps-utils:2.2.3")

}