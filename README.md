# xfinity-test

The project is built with Android Studio and is tested on Samsung galaxy S3 (phone) and Google Nexus 9 (Tablet Simulator)
All the requirements are taken from this document:
https://docs.google.com/document/d/1V-qxEX_CNNBLBILWXocuH78U5ZRQA26iVOl1Wo58CDo/edit

The project makes use of the following 3rd party packages:

     'com.squareup.retrofit2:retrofit:2.0.0-beta4' // Rest client
     'com.squareup.retrofit2:converter-gson:2.0.0-beta4' // Json parser
     'com.squareup.picasso:picasso:2.5.2' // Image downloading API
     'com.github.siyamed:android-shape-imageview:0.9.+@aar' // Circular Image View library
     'com.github.satyan:sugar:1.4' // SQlite manager.
     
There are two flavors of the application namely 'simpsons' and 'got' Both flavors share a 'main' codbase.
Android studio can toggle between these two flavors by selecting 'simpsonsDebug' or 'gotDebug' from the Build Variant view.
