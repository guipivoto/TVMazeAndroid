# Jobsity Android Challenge

This project was created as part of the Jobsity selection process.
It consists of an Android application that should list TV series using the [TVMaze API](https://www.tvmaze.com/api "TVMaze API")

## Requisites

### Mandatory Features
- List all of the series contained in the API.
- Allow users to search series by name.
- The listing and search views must show at least the name and poster image of the
series.
- After clicking on a series, the application should show its details
- After clicking on an episode, the application should show the episode’s information,

### Non-Mandatory Features
- Allow the user to set a PIN number to secure the application
- For supported phones, the user must be able to choose if they want to enable Biometrics
- Allow the user to save/delete a series as a favorite.
- Allow the user to browse their favorite series in alphabetical order
- Create a people search by listing the name and image of the person.
- After clicking on a person, the application should show the details of that person

## Project Details

### Tools and Libraries used
- Jetpack Compose and Navigation
- Volley (REST API)
- Dagger/Hilt (Dependency Injection)
- Kotlin, Kotlin Flow and Coroutines
- Espresso, Robolectric, Mockito, JUnit
- Coil (Image download and cache)

### Project Structure
The source code is divided into several modules in order to encapsulate each of their implementations and usually, only an interface is exported to other modules.
Those modules are divided as follows:
- **app** : Main app module containing the main "app" module only (Activity, Application classes)
- **features**: Contains a module for each app screen
- **repository**: Contains any module that provides data to View/ViewModel layer
- **services**: Modules for APIs that support other modules such as Rest API, Biometrics API
- **common**: Common modules to whole app (Resources, Themes, Base classes/interfaces)

### Tested Devices
Samsung SM-F700 - API 31 (Android 12)
Samsung SM-N986 - API 30 (Android 11)
Samsung SM-G930 - API 26 (Android Oreo)

### Current State/Result
Below is the current state of the app.

##### PIN Code Screen
###### Supports
- User is able to set a 4-characters long numeric password
- User is asked about the possibility of using biometrics APIs to unlock the app.

###### Limitations
- There's no Settings screen yet. Thus, the user can not change the PIN Code options unless it clears the app data (`adb shell pm clear com.jobsity.challenge`)
- Need to refine situations where the app was moved to background and when the PIN Code should be requested again
- No Tests created for this screen yet

##### Main Screen
###### Supports
- Display the list of TV Shows (Image and Title)
- The number of columns in the grid changes from landscape to portrait
- User can select an Series to see its details

###### Limitations
- There's no loading feedback just yet
- Number of columns should be further improved according to screen size etc
- No Tests created for this screen yet

##### TV Show Details
###### Supports
- It displays some basic info regarding the series
- It displays the episode list
- User can select an episode to see its details
- There are some tests created for this screen

###### Limitations
- There's no loading feedback just yet

##### Episode Details
###### Supports
- It displays some basic info regarding the episode
- It displays the episode picture

###### Limitations
- Screen layout is still a bit poor
- There's no loading feedback just yet
- No Tests created for this screen yet

##### Overall
###### Supports
- An Icon was created for the app
- Some unit tests were created to validate the TV Show repository
- It supports Dark Mode

###### Limitations
- User can not set a show as favorite (lack of time)
- User can not search for a actor/actress
- There's no Settings screen to change the PIN Code configuration

Mostly, the reason behind those limitations is the fact of the time to complete the task. There wasn't enough time to develop all the features and also refine all the screens and features.
There's room for several improvements.