# StarGen
Pseudo random star scene generation with Java Swing.
## What
Generates a night sky scene filled with a natural-looking, albeit alien, distribution of stars using vanilla Swing.

## How
Using OpenSimplexNoise it creates clusters of 'stars' over a matrix of pixels and renders them on a JFrame. At this stage it's simple, bigger stars are brighter and a slightly different shade than smaller stars, they also attract more smaller stars. Clusters are built using noise along with a mean and standard deviation to determine distance between stars.

## Results
![screenshot](https://raw.githubusercontent.com/mathewharrington/StarGen/master/Stars/results/StarGen_1.png)


![screenshot](https://raw.githubusercontent.com/mathewharrington/StarGen/master/Stars/results/StarGen_2.png)

