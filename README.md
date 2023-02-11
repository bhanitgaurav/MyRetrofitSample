# MyRetrofitSample
A retrofit sample example in kotlin andoird 

Dependencies

Need to add internet permission in the manifest file.
<uses-permission android:name="android.permission.INTERNET"/>

Enabled data binding to the build.Gradle(:app) file.
buildFeatures {
        dataBinding true
    }

Added some dependencies to the build.Gradle(:app) file.
implementation 'com.google.code.gson:gson:2.8.9'
implementation 'com.squareup.retrofit2:retrofit:2.9.0'
implementation 'com.squareup.retrofit2:converter-gson:2.9.0'

implementation 'com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.1'
implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.2-native-mt'
implementation 'androidx.lifecycle:lifecycle-runtime-ktx:2.4.0'

Created models from JSON files. For creating models I am using https://reqres.in/ website for API response.
