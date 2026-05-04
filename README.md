<p align="center">
  <img src="images/tictactoe.png" alt="Tic Tac Toe Board" width="300"/>
</p>

## About this Kata

This short and simple kata should be performed using Test Driven Development (TDD).

Tic-tac-toe, also known as Xs and Os or NOUGHTS and CROSSES, is a two-player game played on a 3×3 grid. Players take turns marking positions on the board using the symbols X and O.

The objective is to place three of the same symbol in a row, which can be:

 • Horizontally  
 • Vertically  
 • Diagonally (top-left to bottom-right or top-right to bottom-left)

The game ends when one player achieves this condition or when all positions on the board are filled without a winner.

The goal of this kata is to implement the game logic while focusing on writing clean, maintainable, and well-tested code.

## Rules

The rules are described below:

 • X always goes first.  
 • Players cannot play on an already occupied position.  
 • Players alternate placing X’s and O’s on the board until either:  
 • One player has three in a row (horizontally, vertically, or diagonally)  
    • All nine squares are filled  
    • If a player places three X’s or three O’s in a row, that player wins.  
 • If all nine squares are filled and neither player has three in a row, the game is a draw.

## Software Requirements

 • Java: 17  
 • Spring Boot: 3.2  
 • Maven: 3.x  
 • JUnit: 5.x  

## Commit Message Style Guide

This project follows the [Udacity Git Commit Message Style Guide](https://udacity.github.io/git-styleguide/), which provides a consistent format for writing commit messages. Each commit message contains a **Title**, and the title consists of the type of the message and subject in the format `type: subject`.

### Commit Types

 • feat: A new feature  
 • fix: A bug fix  
 • docs: Changes to documentation  
 • style: Code formatting changes (e.g., indentation, spacing)  
 • refactor: Code changes without affecting functionality  
 • test: Adding or refactoring tests  
 • chore: Updates to build processes or auxiliary tools (e.g., configuration)

## How to Build and run the Application

 • Clone this repository:

    https://github.com/2025-DEV1-016/Tic-Tac-Toe.git

 • Build the project and run the tests by running

    mvn clean install

 • The **Model Classes** used in the project are generated from the **OpenAPI** specification during the build process. Running `mvn clean install` will regenerate the models as part of the build.

 • Run main class from IDE (IntelliJ/Eclipse):

    Navigate to tictactoe
    Click Run

 • Once started, the application will be available at:

    http://localhost:8080

## Sample Input and Output

The following examples demonstrate a typical game flow.  
The `gameId` returned from the **Create Game** API is reused in subsequent requests.

### 1.Create Game

**Request**  

    POST /games

**Response**

    {
      "gameId": "1234-abcd",
      "status": "IN_PROGRESS",
      "currentPlayer": "X",
      "board": ["", "", "", "", "", "", "", "", ""]
    }

### 2.Make Move

**Request**

    POST /games/1234-abcd/moves

    {
        "position": 0
    }

**Response**

    {
        "gameId": "1234-abcd",
        "status": "IN_PROGRESS",
        "currentPlayer": "O",
        "board": ["X", "", "", "", "", "", "", "", ""]
    }

### 3.Get Game by ID

**Request**

    GET /games/1234-abcd

**Response**

    {
      "gameId": "1234-abcd",
      "status": "IN_PROGRESS",
      "currentPlayer": "X",
      "board": ["", "", "", "", "", "", "", "", ""]
    }

### 4. Reset Game

**Request**

    POST /games/1234-abcd/reset

**Response**

    {
      "gameId": "1234-abcd",
      "status": "IN_PROGRESS",
      "currentPlayer": "X",
      "board": ["", "", "", "", "", "", "", "", ""]
    }

## Test Reports

 • After a successful build using `mvn clean install`, navigate to the `target` directory in the project root to view the reports.

 • **Jacoco Code Coverage Report :** Code Coverage report will be available in `target\site\jacoco` folder. View the report by launching `index.html`

 • **Pit test coverage report :** Mutation Coverage report will be available in `target\pit-reports` folder. View the report by launching `index.html` 
