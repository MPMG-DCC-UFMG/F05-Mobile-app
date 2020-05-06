[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=MPMG-DCC-UFMG_Mobile&metric=alert_status)](https://sonarcloud.io/dashboard?id=MPMG-DCC-UFMG_Mobile)

# Mobile

Project of an application for the MP

## Necessary steps


### Google Maps

Before you run your application, you need a Google Maps API key.

follow the directions here:
https://developers.google.com/maps/documentation/android/start#get-key

Once you have your key (it starts with "AIza"), create a file "services_keys.xml" with the following  
content inside the folder **app/src/debug/res/values**. If the folder don't exist, create it. 

```bash
<resources>
    <string name="google_maps_key" templateMergeStrategy="preserve" translatable="false">YOUR_API_KEY</string>
</resources>
```

### Sentry

To track all errors this application it's configure to use [Sentry](https://sentry.io) services.

To enable it, go to the same file you created in the previous section and add the following entry:

```bash
<string name="sentry_key" templateMergeStrategy="preserve" translatable="false">SENTRY_DSN_FOUND_IN_DOCUMENTATION</string>
```

### Firebase

To authenticate in Twitter and Facebook the Firebase was used for this application. First it's necessary to create a project
in Firebase and download the file **google_services.json**. All the service setup can be done following the steps in
[Setup firebase](https://firebase.google.com/docs/android/setup)

Follow this steps to enable [Sign In with Twitter](https://firebase.google.com/docs/auth/android/twitter-login)

## Third Party Libraries

The third party libraries used in the project were as follows:

* [Android Jetpack](https://developer.android.com/jetpack): set of libraries created by Google to accelerate the development of Android applications.
* [Koin](https://insert-koin.io/): dependency injection library.
* [Retrofit](https://square.github.io/retrofit/): HTTP client used for REST calls.
* [Glide](https://bumptech.github.io/glide/): image caching and loading library for Android.
* [Material Design](https://material.io/): library of components based on Material Design

## Services

The services used in this application were:

* [OpenCage](https://opencagedata.com/api#forward): service used to decode latitude and longitude to address.
* [ViaCEP](https://viacep.com.br/): API used to identify the address via zip code

## App Architecture (Project Structure)

The design of Protector Scout V4 mobile app is based on SOLID principles and on Clean architecture model. Also, many of the project dependencies come from Android Jetpack.

Broadly speaking, the app is organized into three layers: Presentation (or UI), Domain (business logic) and Models.
The app code itself is actually divided into more packages, but they all fit to this organization.

The following diagram represents how most of the app components relate to each other.

<h1 align="center">
  <br>
  <a href="https://developer.android.com/jetpack/docs/guide"><img src="https://developer.android.com/topic/libraries/architecture/images/final-architecture.png" alt="App architecture"></a>
  <br>
</h1>

* Views (activities and fragments) should only handle interactions, like clicks or animations. 
* ViewModels are responsible for handling business logic.
* Repositories abstract the complexities of loading and storing data.

## Sponsors

<h1 align="center">
  <a href="https://www.mpmg.mp.br/"><img src="../Mobile/app/src/main/res/drawable/mmpg_logo.png" alt="App architecture"></a>
  <br>
  <a href="https://sentry.io/"><img src="../Mobile/assets/sentry-logo-black.png" alt="App architecture"></a>
  <br>
</h1>