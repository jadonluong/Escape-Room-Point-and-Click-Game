# Escape If You Dare!

A 2D point-and-click escape room game built in Java with JavaFX, developed as our CSC207 term project.
 
---

## Table of Contents

- [Authors](#authors)
- [About the Project](#about-the-project)
- [Features](#features)
- [Installation](#installation)
- [Usage Guide](#usage-guide)
- [License](#license)
- [Feedback](#feedback)
- [Contributing](#contributing)
---

## Authors

This project was built by **Team Escapists**:

- Baron, Jadon, Lucas, Skylar, Tina


- Developed for CSC207: Software Design, Summer 2026, University of Toronto.

---

## About the Project

We developed a point-and-click escape room game where the player explores interconnected rooms, interacts with objects, collects and combines items, and solves puzzles to progress. We built this project to practice applying Clean Architecture and SOLID design principles to a genuinely interactive, stateful application — something more design-intensive than a typical CRUD (Create, Read, Update, Delete) app, while still being scoped realistically for a team of five over six weeks.

The game is aimed at anyone who enjoys short logic puzzles and light narrative games! Whether you want to play through a connected multi-room story, or just drop into a standalone room for a quick 10–15 minute puzzle session.
 
---

## Features

- **Guest or account play** — jump in immediately as a guest, or create an account to save your progress between sessions.


- **Two ways to play**:
    - **Story Mode** — a connected series of rooms where items and clues found in one room can unlock progress in another.
    - **Quick Game** — browse and play standalone, self-contained escape rooms of varying themes and difficulty.


- **Inventory & item combination** — collect items into a persistent inventory, and combine compatible items to create new tools needed for progression.


- **Multiple puzzle types** — including cipher puzzles, anagram puzzles, and combination locks.


- **Tiered hint system** — stuck on a puzzle? Request a hint, delivered in increasing levels of specificity.


- **Pause menu** — save your progress, save & quit, or quit without saving, with clear in-app warnings about which options do and don't preserve your progress.


- **Audio controls** — toggle music and sound effects independently.


- **Resizable, scalable UI** — every screen is built to scale cleanly across different window sizes.

---

## Installation

### Requirements

| Requirement | Version |
|---|---|
| Java (JDK) | 21 (LTS) |
| Apache Maven | 3.9+ |
| JavaFX | 21.0.2 (pulled automatically via Maven) |


### Steps

1. **Clone the repository:**
```bash
   git clone <repository-url>
   cd escapeRoom
```

2. **Confirm you have Java 21 installed:**
```bash
   java -version
```
If you don't have Java 21, download it from [Adoptium](https://adoptium.net/) or your preferred JDK provider.

3. **Build the project and fetch dependencies** (JavaFX and Gson are declared in `pom.xml` and will be downloaded automatically):
```bash
   mvn clean compile
```

4. **Run the application:**
```bash
   mvn javafx:run
```

Alternatively, if you're using IntelliJ IDEA, open the project, let Maven sync, and run `app.Main` directly via the green run arrow.

### Common Issues

- **"Plugin 'org.openjfx:javafx-maven-plugin:0.0.8' not found"** — this is usually a stale IntelliJ Maven cache, not an actual missing dependency. Open the Maven tool window and click "Reload All Maven Projects."
- **JavaFX native-access warnings on startup** (e.g. `WARNING: A restricted method in java.lang.System has been called`) — these are harmless and don't affect functionality. They stem from JavaFX's internal native rendering code on recent JDK versions.
- **Blank or oversized window on first launch** — if the window opens larger than your screen, press **Alt+F4** (Windows) to close it, then relaunch — this was an early sizing bug that's since been fixed in `ViewManager`.
---

## Usage Guide

1. **Launch the app** — you'll land on the main menu.
2. **Choose to play as a guest, log in, or sign up.** Guests can play immediately but can't save progress between sessions.
3. **Pick a mode**: Story Line (connected multi-room adventure), Tutorial (guided walkthrough of the controls), or Quick Game (browse and pick a standalone room).
4. **Explore the room**: click on objects to examine them, collect items, trigger puzzles & hints.
5. **Use your inventory**: click an item, then click an object in the room to use it. Compatible items can be combined to form new tools.
6. **Stuck?** There are hints hidden in objects, click around and find out!
7. **Pause anytime**: open the pause menu to save your progress, save and quit, or quit without saving — logged-in players only, since guest progress can't be saved.
---

## License

This project is licensed under the MIT License — see the `LICENSE` file for details.

---

## Feedback

We'd love to hear your feedback on Escapists! If you'd like to report a bug, suggest a feature, or share general thoughts:

- Open an [issue](../../issues) on this repository.
- Please include: what you expected to happen, what actually happened, and steps to reproduce the issue if applicable.
  We'll do our best to respond to feedback, though as a term project, active maintenance isn't guaranteed after the course concludes.

---

## Contributing

This is a student project developed for a course, so external contributions aren't actively being accepted at this time. If you'd like to fork the project for your own experimentation:

1. Fork the repository.
2. Create a feature branch (`git checkout -b feature/your-feature-name`).
3. Make your changes and ensure `mvn clean compile` succeeds before committing.
4. Open a pull request with a clear description of your changes.
   For our own team's workflow: all changes go through a pull request into `development` rather than being pushed directly, and should compile cleanly (`mvn clean compile`) before being opened for review.