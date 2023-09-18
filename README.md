# ComposeFetchImages-
An application that searches for the top images of the week from the Imgur gallery and displays them in a list.
This project is written in Kotlin language, which uses the MVVM architecture pattern and Dagger Hilt for Dependency Injection for better performance and ease of understanding of code.
The API is hit, which gets an array of images with URLs in response. These URLs are loaded in the GridView/List View as per user selection. The API is hit using Retrofit for better performance handling for the API.

API failures are also handled even with no internet connection part.
The UI is designed using Android Jetpack Compose.

**Features**

    • Search top images of the week
    • Toggle from Grid to List view
	
**Instructions to Run the App**

    • Download Zip File
    • Unzip the code
    • Import Code to Android Studio  and download all the required gradle dependencies
    • Build Project 
    • Select Android Device/Emulator
	
**Assumptions**

    • Have handled scenarios like no network, no response from API
    • Have included data which has only images and excluded videos and other file formats
    • Have tried to maintain UI/UX using low user clicks 


**Note :**
To compile and run the application, replace  the Imgur Client ID in the ApiService class at the retrofit package.
 

