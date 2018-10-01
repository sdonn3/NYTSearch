# Project 2 - NYTSearch

NYTSearch is an android app that allows a user to search for articles on web using simple filters. The app utilizes [New York Times Search API](http://developer.nytimes.com/docs/read/article_search_api_v2).

Time spent: *X* hours spent in total

## User Stories

The following *required* functionality is completed:

* [X] User can *search for news article* by specifying a query and launching a search. Search displays a grid of image results from the New York Times Search API.
* [X] User can click on "settings" which allows selection of *advanced search options* to filter results
* [X] User can configure advanced search filters such as:
  * [X] Begin Date (using a date picker)
  * [X] News desk values (Arts, Fashion & Style, Sports)
  * [X] Sort order (oldest or newest)
* [X] Subsequent searches have any filters applied to the search results
* [X] User can tap on any article in results to view the contents in an embedded browser.
* [X] User can *scroll down to see more articles*. The maximum number of articles is limited by the API search.

The following *optional* features are implemented:

* [ ] Implements robust error handling, [check if internet is available](http://guides.codepath.com/android/Sending-and-Managing-Network-Requests#checking-for-network-connectivity), handle error cases, network failures
* [ ] Used the *ActionBar SearchView* or custom layout as the query box instead of an EditText
* [ ] User can *share an article link* to their friends or email it to themselves
* [X] Replaced Filter Settings Activity with a lightweight modal overlay

The following *bonus* features are implemented:

* [X] Use the [RecyclerView](http://guides.codepath.com/android/Using-the-RecyclerView) with the StaggeredGridLayoutManager to display improve the grid of image results
* [ ] For different news articles that only have text or only have images, use [Heterogenous Layouts](http://guides.codepath.com/android/Heterogenous-Layouts-inside-RecyclerView) with RecyclerView
* [X] Use Parcelable instead of Serializable using the popular [Parceler library](http://guides.codepath.com/android/Using-Parceler).
* [ ] Leverages the [data binding support module](http://guides.codepath.com/android/Applying-Data-Binding-for-Views) to bind data into layout templates.
* [ ] Replace all icon drawables and other static image assets with [vector drawables](http://guides.codepath.com/android/Drawables#vector-drawables) where appropriate.
* [X] Replace Picasso with [Glide](http://inthecheesefactory.com/blog/get-to-know-glide-recommended-by-google/en) for more efficient image rendering.
* [X] Uses [retrolambda expressions](http://guides.codepath.com/android/Lambda-Expressions) to cleanup event handling blocks.
* [X] Leverages the popular [GSON library](http://guides.codepath.com/android/Using-Android-Async-Http-Client#decoding-with-gson-library) to streamline the parsing of JSON data.
* [X] Leverages the [Retrofit networking library](http://guides.codepath.com/android/Consuming-APIs-with-Retrofit) to access the New York Times API.
* [ ] Replace the embedded WebView with [Chrome Custom Tabs](http://guides.codepath.com/android/Chrome-Custom-Tabs) using a custom action button for sharing. (*2 points*)

The following *additional* features are implemented:

* [ ] List anything else that you can get done to improve the app functionality!

## Video Walkthrough

Here's a walkthrough of implemented user stories:

<img src='https://i.imgur.com/2WKmmrT.gifv' title='Video Walkthrough' width='' alt='Video Walkthrough' />

GIF created with [LiceCap](http://www.cockos.com/licecap/).

## Notes

Describe any challenges encountered while building the app.

I thought I was being clever with my util functions around the search parameters, but I ended up creating too many bugs. Ended up simplifying it a lot, and were I to do it again, I'd keep it even simpler.
Also, the endless scroll listener didn't play nice when I rewrite it in Kotlin.

## Open-source libraries used

- Retrofit
- Glide
- RxJava
- RxKotlin
- RxBindings
- GSON

## License

    Copyright [2018] [Steven Donnelly]

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.