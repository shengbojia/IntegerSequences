<h1 align="center"> Integer Sequences </h1><br>
<p align="center">
  Access to the Online Encyclopedia of Integer Sequences (OEIS) at your fingertips.
</p>

## Table of Contents
* [Introduction](#introduction)
* [Features](#features)
* [Showcase](#showcase)
* [Libraries Used](#libraries-used)
* [License](*license)


## Introduction
Simply type a couple of integers into the search box, and this app will sift through the largest online database of recorded
sequences and find likely matches. Found sequences will also contain some useful data such as a short explanation, a glimpse 
into the sequence, and some formulas/generating functions.

## Features
Things you can do with this app:
* Search for sequences based on a snippet/subsequence of numbers
* Display a list of matching results (if any)
* Upon clicking a sequence, shows more useful details
* Information such as the common name of a sequence, formulas, and the beginning of a sequence
* Data is paged in when needed, to reduce network costs

## Showcase

## Libraries Used

* [Android Jetpack][0] - Collection of libraries that reduce boilerplate code and simplifies complex tasks.
  * Foundation - Components for backwards compatibility, testing and Kotlin language support.
    * [AppCompat][1] - AppCompatActivity offers support for ActionBar.
    * [Android KTX][2] - Can write more concise, idiomatic Kotlin code.
    * [Test][3] - An Android testing framework for unit and runtime UI tests.
  * [Android Architecture][4] - Keeps app more robust, maintainable, and testable.
    * [Data Binding][5] - Declaratively bind observable data sources to UI components.
    * [Lifecycles][6] - Components respond automatically to lifecycle events.
    * [LiveData][7] - Data objects that notify views when the underlying database changes.
    * [Navigation][8] - Keeps navigation between fragments simple.
    * [Room][9] - Provides layer of abstraction over SQLite for more robust database access.
    * [ViewModel][10] - Store UI-related data between state changes and handles fragment to fragment communication. Easily schedule asynchronous tasks.
  * UI - Widgets and helpers to make app presentable and clean.
    * [Animations & Transitions][11] - Transition between fragments and events.
    * [Fragment][12] - A basic, reusable piece of composable UI.
    * [Layout][13] - Organize widgets in a variety of ways. 
* [Kotlin][20]
  * [Kotlin Coroutines][21] for managing background threads with simplified code and reducing needs for callbacks

[0]: https://developer.android.com/jetpack
[1]: https://developer.android.com/topic/libraries/support-library/packages#v7-appcompat
[2]: https://developer.android.com/kotlin/ktx
[3]: https://developer.android.com/training/testing/
[4]: https://developer.android.com/topic/libraries/architecture
[5]: https://developer.android.com/topic/libraries/data-binding/
[6]: https://developer.android.com/topic/libraries/architecture/lifecycle
[7]: https://developer.android.com/topic/libraries/architecture/livedata
[8]: https://developer.android.com/topic/libraries/architecture/navigation/
[9]: https://developer.android.com/topic/libraries/architecture/room
[10]: https://developer.android.com/topic/libraries/architecture/viewmodel
[11]: https://developer.android.com/training/animation/
[12]: https://developer.android.com/guide/components/fragments
[13]: https://developer.android.com/guide/topics/ui/declaring-layout
[20]: https://kotlinlang.org/
[21]: https://kotlinlang.org/docs/reference/coroutines-overview.html

## License
Notes is under the MIT license. See the [LICENSE](LICENSE) for more information.
