# Skanban (formerly Kanbafy)

Skanban is a simple Kanban board application designed for project management, developed using Scala and ScalaFX.
Originally created for Aalto University's *Programming Studio 2* course, this tool allows users to manage tasks visually using the Kanban methodology.

I'm currently rewriting the project to improve its structure, usability and functionality, since I have learned a lot since I first developed this.
Check the progress on the "rewrite" branch if you're interested in the latest developments.
Along with the rewriting, I'm also renaming the project to "**Skanban**".

## Table of Contents

- [Installation](#installation)
  - [Installing Dependencies](#installing-dependencies)
  - [Cloning the Project](#cloning-the-project)
- [Usage](#usage)
  - [Running the Program](#running-the-program)
  - [Known Issues](#known-issues)
  - [Using the Program](#using-the-program)
  - [Running Tests](#running-tests)
- [Technical Details](#technical-details)
  - [Dependencies](#dependencies)
  - [Project Structure](#project-structure)
- [Contributing](#contributing)
- [License](#license)

## Installation

### Installing Dependencies

You need to have both **Scala** and **SBT** installed.
This project uses the currently latest LTS-version of Scala **3.3.5** and version **1.10.7** of SBT

You can check if you have them installed, and their versions by running the commands:

```bash
$ scala --version
$ sbt --version
```

If you don't have them installed, see [Scala Installation](https://www.scala-lang.org/download/).
I would suggest installing Scala with `cs setup`, as it will install the latest versions of both Scala and SBT.

### Cloning the Project

Navigate to your desired installation directory in your terminal and run the command:
```bash
$ git clone https://github.com/Suopunki/Skanban.git
```

## Usage

### Running the Program

To run the program using SBT, navigate to the project's root directory and run the command:

```bash
$ sbt run
```

### Known Issues

- There is a graphical bug with the initial sizing of the window components. This can be resolved by manually resizing the window.
- Drag-and-drop for columns requires precise placement, as the drop area between columns is narrow. This will be improved in future versions.

### Using the Program

Most operations on a board, like renaming the board, columns, or cards, are done through context-menus, which can be accessed by right-clicking the component.

The cards and the columns can be moved by dragging and dropping.

Things like saving a board, filtering or sorting cards, and viewing archived cards are available through the options in the menubar.

### Running Tests

To run the tests using SBT, navigate to the project's root directory and run the command:

```bash
$ sbt test
```

## Technical Details

### Dependencies

- GUI:
  - ScalaFX (Scala wrapper for JavaFX)
    - Documentation: http://scalafx.org/
    - GitHub repository: https://github.com/scalafx/scalafx
- Testing:
  - ScalaTest
    - Documentation: https://www.scalatest.org/
- JSON serialization:
  - circe
    - Documentation: https://circe.github.io/circe/
    - GitHub repository: https://github.com/circe/circe

### Project structure

    ├── build.sbt  <SBT configuration and dependency management>
    └── src
        ├── main
        │   ├── resources
        │   │   ├── save-files <Default directory for save-files>
        │   │   └── styles  <CSS-files for styling>
        │   └── scala  <Program source code>
        │       ├── gui  <GUI (ScalaFX related) source code>
        │       │   ├── components  <Styled components>
        │       │   └── scenes  <Scenes of the program>
        │       ├── logic  <"Business logic">
        │       └── Kanbafy.scala  <Main file for running the program>
        └── test
            └── scala   <Unit tests for the business logic> 

## Contributing
Contributions are welcome!
Feel free to open an issue or submit a pull request.
Please check the "rewrite" branch for ongoing development.

## License

[MIT License](LICENSE)
