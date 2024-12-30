Kanbafy
=======

Kanbafy is a ScalaFX-sbt project for the Programming Studio 2 course.
The program is a simple Kanban board application for project management.

## Project Structure

    ├── build.sbt  <sbt configuration and dependency management>
    └── src
        ├── main
        │   ├── resources     
        │   │   ├── saveFiles  <save files>
        │   │   └── styles  <css-files for styling>
        │   └── scala  <program source code>
        │       ├── gui  <GUI source code>
        │       │   ├── components  <styled components>
        │       │   └── scenes  <GUI scenes>
        │       ├── logic  <app/board logic source code>
        │       └── Main.scala  <main file for running the program>
        └── test
            └── scala   <unit testing source code>
 

## Running the program

In sbt shell:

`run` to build and run the program.

`test` to run the unit tests.
