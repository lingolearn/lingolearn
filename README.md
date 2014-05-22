# lingolearn

## Introduction
Lingolearn is the final project for ALM IT capstone for Team Two.

## Project Dependences
In order to build the project locally, you must provide the configuration 
which is dependent upon the IDE that you use. The following instructions 
are for Eclipse Juno.

1. Make sure [Google Plugin for Eclipse](https://developers.google.com/eclipse/) is installed
2. Clone the repository to your local storage and import the project
3. Under "[Right Click] > Properties > Google" 
  1. "Use Default SDK" is selected, the project is on 1.9.3
  2. "This project has a WAR directory" is selected
  3. "Use Google Web Toolkit" is selected, this project uses 2.6.0
4. Under "[Right Click] > Properties > Java Build Path > Source"
  1. Remove the default settings
  2. Add the folder "src" and "test"
5. Under "[Right Click] > Properties > Java Build Path > Libraries"
  1. Add JRE System Library, this project uses JRE7
  2. Add JUnit, this project uses JUnit 4
  3. Add "lib/objectify-4.0.jar"
  4. Add "lib/hamcrest-core-1.3.jar"
  5. Add "lib/gwt-visualization.jar"
  6. Add "lib/guava-16.0.1.jar"
  7. Add "lib/appengine-testing.jar"
  8. Add "lib/appengine-api-stubs.jar"
  
