Copied from the udacity tutorial on JSON parsing.

UPDATE: 7/18/2017
The udacity tutorial required me to modify the app to use loaders to prevent memory leaks. Unfortunately, loaders are being
"deprecated" and I have been recommended (by android) to use ViewModels and LiveData instead and I have modified the app to use
these tools.

UPDATE: 7/17/2018
I made the below modification before continuing with the other tutorials in the series which I have now learned add this 
functionality in a much more "graceful" way than I did. I will still leave this up for my own reference since it returns the data 
using a callback interface instead of a nested class.

UPDATE: 7/5/2018
I have modified the project to pull the data directly from the usgs website instead of using fabricated data provided by Udacity.
