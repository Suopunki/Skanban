# Skanban

![SBT version](https://img.shields.io/badge/SBT-v1.10.7-white.svg?labelColor=darkred)
![Scala version](https://img.shields.io/badge/Scala-3.3.4_LTS-194E5C.svg?labelColor=BA3030)
![ScalaFX version](https://img.shields.io/badge/ScalaFX-v.23.0.1--R34-darkred.svg?labelColor=gray)
![License](https://img.shields.io/badge/Licence-MIT-darkred.svg)


**Skanban** is a simple kanban board desktop application for project
management. It can help you manage your tasks through a simple drag-and-drop
interface. Tasks can be organized in columns such as To Do, In Progress, and Done.

The project is written in Scala 3, uses ScalaFX for its GUI and
SBT for its build tool.

Originally Skanban was written as project for Aalto University's Programming
Studio 2 course.

## Table of Contents
- [Installation](#installation)
- [Usage](#usage)
- [Technical Details](#technical-details)
- [License](#license)

## Installation

You must have Scala and sbt installed. I suggest following the instructions
the [Scala installation guide](https://www.scala-lang.org/download/) and using `cs setup`.
It installs both Scala and SBT.

Clone the repository...

...

## Usage

### Running the application

To run the program you must have SBT installed, see [installation](#installation).

To run the application, navigate to this project in your terminal and run the command:
```
sbt run
```

## Technical Details

### Project Structure

```
Skanban
├─ build.sbt
└─ src
    ├── main
    │   ├── resources
    │   │   └── styles
    │   └── scala
    │       ├── gui
    │       │   ├── components
    │       │   └── scenes
    │       ├── logic
    │       └── Main.scala
    └── test
        └── scala
            └── ...
```

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file
for details.
