# StarGen
Pseudo random star scene generation with Java Swing.
## What
Generates a night sky scene filled with a natural-looking/alien distribution of stars using Swing.

## How
Using OpenSimplexNoise it creates clusters of 'stars' over a matrix of pixels and renders them on a JFrame. At this stage it's simple, bigger stars are brighter and a slightly different shade than smaller stars, they also attract more smaller stars. Clusters are built iteratively by adjusting a mean and standard deviation to determine distance between stars.

## Results
![screenshot](https://raw.githubusercontent.com/mathewharrington/StarGen/master/Stars/results/StarGen_1.png)


![screenshot](https://raw.githubusercontent.com/mathewharrington/StarGen/master/Stars/results/StarGen_2.png)

## TODO
Increase number of pixels used to represent a star, for example a large star could have a glow effect by using more than one pixel and multiple colours to represent it, this would affect cluster generation as it is now though.
