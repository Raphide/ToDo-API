# Todo Application - Back End

<!-- {add test badges here, all projects you build from here on out will have tests, therefore you should have github workflow badges at the top of your repositories: [Github Workflow Badges](https://docs.github.com/en/actions/monitoring-and-troubleshooting-workflows/adding-a-workflow-status-badge)} -->

## Demo & Snippets

- Nothing here yet, but check back later :) 

---

## Requirements / Purpose

### purpose of project

- Create an API to be integrated with <a href="https://github.com/Raphide/ToDo-App">Todo Front End</a>, that allows you to store and retrieve tasks from a database.

### MVP
- Deleting a task should set an `isArchived` flag in the database instead of deleting the task from the database
- Add a filter to the frontend application that allows you to filter tasks by category
- Categories and Todos should be stored in separate tables

###  stack used and why
- Spring
- Java
- SQL

---

## Build Steps

- Clone repository and run ```npm install``` to install dependencies.
- This Application runs alongside the <a href="https://github.com/Raphide/ToDo-App">Todo Application- Front End</a>

---

<!-- ## Design Goals / Approach

-   Design goals
-   why did you implement this the way you did?

--- -->

<!-- ## Features

-   What features does the project have?
-   list them...

--- -->

## Known issues

-   Error handling needs work
- Need to implement testing

---

## Future Goals

-   More testing

---

## Change logs

### 09/09/2024 - Various changes to the Task Page

-  Added unit and end-to-end tests

### 01/09/2024 - Various changes to the Task Page

- Created column for `isArchived`
- Added Duplication functionality
- Added Archive functionality
- Changed completeById to set `isArchived` flag as well as `completed` flag
- Added a proper README and changelog :D 

---

## What did you struggle with?

-   Hit a wall with my Todo entries overwriting themselves after creating Categories table
    - This was due to accidentally having a "setter" for Ids

---

<!-- ## Licensing Details

-   What type of license are you releasing this under?

--- -->

## Further details, related projects, reimplementations

- This an API to accompany the Front End application <a>https://github.com/Raphide/ToDo-App</a>

