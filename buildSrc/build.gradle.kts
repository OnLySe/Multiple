/*plugins {
    id 'java-library'
    id 'kotlin'
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_7
    targetCompatibility = JavaVersion.VERSION_1_7
}

dependencies {
    implementation "org.jetbrains.kotlin:kotlin-stdlib:${versions.kotlin_version}"
}*/

plugins {
    `kotlin-dsl`
}


//dependencies {
//    implementation "org.jetbrains.kotlin:kotlin-stdlib:${versions.kotlin_version}"
//}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}
repositories {
    google()
    maven("https://maven.aliyun.com/repository/public")
}
