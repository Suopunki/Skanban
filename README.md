# Skanban

---

![SBT version](https://img.shields.io/badge/SBT-1.10.7-white.svg?labelColor=darkred)
![Scala version](https://img.shields.io/badge/Scala-3.3.4_LTS-194E5C.svg?labelColor=BA3030)
![ScalaFX version](https://img.shields.io/badge/ScalaFX-v.23.0.1--R34-darkred.svg?labelColor=gray)
![JDK](https://img.shields.io/badge/JDK-21+-purple.svg)
![License](https://img.shields.io/badge/Licence-MIT-darkred.svg)

---

**Skanban** is a simple kanban board desktop application for project management,
written in Scala 3 and built with ScalaFX for its graphical interface.
It allows you to manage tasks through a drag-and-drop interface, organizing
them into customizable columns such as **To Do**, **In Progress**, and **Done**.
Whether you're managing personal projects or team workflows,
Skanban helps you stay organized and on track.

Originally developed as a project for Aalto University's **Programming Studio 2** course.

## Table of Contents

- [Getting Started](#getting-started)
  - [Software Required](#software-required)
  - [Cloning the Repository](#cloning-the-repository)
- [Usage](#usage)
  - [Running the application](#running-the-application)
- [Technical Details](#technical-details)
  - [Project Structure](#project-structure)
- [Acknowledgments](#acknowledgements)
- [License](#license)

## Getting Started

### Software required

1. [Scala](https://www.scala-lang.org/) 3.3.4 or newer
2. [SBT](https://www.scala-sbt.org/) 1.10.7 or newer
3. [Java Development Kit (JDK)](https://adoptium.net/) 21 or newer

> **Note:** JDK 21 is required due to the project's dependency on JavaFX 23,
> which introduced this requirement.

You can install Scala and SBT using `cs setup` (Coursier CLI). For more
detailed setup instructions, refer to the official installation guides for
[Scala](https://docs.scala-lang.org/getting-started/index.html) and 
[SBT](https://www.scala-sbt.org/1.x/docs/Setup.html).

To verify your installations:
```bash
scala --version
sbt --version
java --version
```

<!-- Screenshots/GIFs and more detailed instructions -->

### Cloning the Repository

To get started, clone the repository by running the following command:
```bash
git clone https://github.com/Suopunki2002/Skanban.git
```

Navigate into the project directory:
```bash
cd Skanban
```

## Usage

### Running the application

To run the program you must have SBT installed, see [installation](#getting-started).

To run the application, navigate to this project in your terminal and run the command:
```bash
sbt run
```

To run the unit tests included with the project, run:
```bash
sbt test
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

### Key Dependencies

- **[ScalaFX](https://www.scalafx.org/)**: The project uses ScalaFX 23.0.1-R34 for building the user interface.
- **[Circe](https://circe.github.io/circe/)**: JSON library used for handling data parsing and serialization.
- **[ScalaTest](https://www.scalatest.org/)**: Testing framework for unit tests.


## Acknowledgements

Special thanks to Aalto University and the Programming Studio 2 course for the
initial inspiration for this project.

## License

This project is licensed under the MIT License.

See the [LICENSE](LICENSE) file for details.
