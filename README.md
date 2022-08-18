# CurrenciesInfo

Android application that delivers to user latest currencies info depending on Belarussian Ruble.

## Description

There is the project that simply retrieving latest curriecies data which value depending on Belarussian Ruble and deploy it. \
Provided features of caching and manually arranging currencies order as you wish.


#### Technologies

CurrenciesInfo is currently extended with the following libraries. \
Instructions on how to use them in your own application are linked below.

| Technology | Source |
| ---------- | ------ |
| Kotlin | https://github.com/JetBrains/kotlin |
| Retrofit | https://square.github.io/retrofit/ |
| Kotlin Coroutines | https://developer.android.com/kotlin/coroutines |
| Dagger-Hilt | https://dagger.dev/hilt/ |
| Room | https://developer.android.com/training/data-storage/room |
| Jetpack Compose with View | https://developer.android.com/jetpack/compose/interop/interop-apis |


#### Difficulties

It was not without difficulties, but how else. \
First of all is working with xml as retrieved format in Kotlin, i had many hours working with xml until correctly. \
Because there're two main things that needed to correctly do it in kotlin:
  1. Empty constructor by default
  2. Data Class Fields initialized by default
######
And check correctness of response class as well :) \
 \
Second is arranging list items using AsyncListDiffer Class and implementing SimpleCallback interface. Anyway i did it, but its working not good as i would wish(You can watch this in gif i attached below).


#### Usage Process
https://user-images.githubusercontent.com/75251154/185439214-e1415283-5a13-4f67-9f8c-37c67e9b345c.mp4




