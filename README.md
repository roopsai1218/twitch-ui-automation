# UI Automation Framework – Twitch Search Flow

## Overview

This project demonstrates a **UI automation framework built using Java, Selenium WebDriver, and TestNG** to automate a user flow on Twitch. The goal of the test is to simulate a real user searching for a game and opening a live stream while ensuring the application loads properly and dynamic elements are handled reliably.

The framework follows the **Page Object Model (POM)** design pattern to improve maintainability, readability, and scalability of automated tests.

---

# Framework Architecture

The project is structured into multiple layers to separate responsibilities.

```
src
 ├── base
 │    └── BasePage.java
 │
 ├── pages
 │    ├── HomePage.java
 │    ├── SearchPage.java
 │    └── StreamerPage.java
 │
 ├── tests
 │    └── TwitchSearchTest.java
 │
 └── utils
      ├── DriverFactory.java
      ├── WaitUtils.java
      └── ScreenshotUtil.java
```

Each layer has a specific purpose.

---

# Base Layer

## BasePage

The `BasePage` class acts as the parent class for all page objects. It provides reusable functionality such as:

* clicking elements
* sending input
* scrolling the page
* accessing the WebDriver instance

This avoids code duplication across different page classes.

Example responsibilities:

* scroll page
* click elements
* interact with DOM elements safely

---

# Utilities Layer

## DriverFactory

This class is responsible for **creating and managing the WebDriver instance**.

Responsibilities:

* launching the browser
* configuring browser options
* closing the browser after tests

Example flow:

```
Test starts
   ↓
DriverFactory initializes ChromeDriver
   ↓
Browser session is created
```

---

## WaitUtils

Modern websites like Twitch load content dynamically using JavaScript and React components. Because of this, elements may not appear immediately when the page loads.

`WaitUtils` ensures the test waits until elements are ready before interacting with them.

Implemented waits include:

* Wait for element visibility
* Wait for element clickability
* Wait for page load completion

Example:

```
Wait until element is visible
Wait until element is clickable
Wait until page DOM is fully loaded
```

This prevents common Selenium issues such as:

* ElementNotInteractableException
* ElementClickInterceptedException
* NoSuchElementException

---

## ScreenshotUtil

This utility captures screenshots during test execution. Screenshots are useful for:

* debugging failed tests
* capturing proof of successful test runs
* generating automation reports

The screenshot is captured once the stream page successfully loads.

---

# Page Object Layer

The framework uses the **Page Object Model (POM)** pattern.

Each page in the application is represented by a Java class.

---

## HomePage

Represents the Twitch homepage.

Responsibilities:

* open the website
* click on the search icon

Example flow:

```
Open Twitch website
Click search icon
```

---

## SearchPage

Handles the search functionality.

Responsibilities:

* enter the game name
* select the game from auto-suggestions
* scroll through search results

Because Twitch loads results dynamically, scrolling is required to load more streams.

Example:

```
Search for "StarCraft II"
Scroll results twice
Load additional streamers
```

---

## StreamerPage

Handles actions on the stream page.

Responsibilities:

* select the first live stream
* wait for the video player to load
* close popups if present
* capture screenshot

Special handling is required because Twitch may display popups such as:

* muted audio warnings
* copyright notices
* overlay UI elements

These are handled safely so the test continues execution.

---

# Handling Dynamic Website Loading

Modern web applications load UI components asynchronously. This means elements may appear after the initial page load.

To handle this, the framework implements several waiting strategies.

## 1. Page Load Wait

Ensures the DOM is fully loaded before interacting with the page.

```
document.readyState = complete
```

---

## 2. Explicit Waits

Explicit waits wait until a specific condition is met.

Example conditions:

* element becomes visible
* element becomes clickable
* element disappears

This ensures interactions happen only when elements are ready.

---

## 3. Scroll Handling

Some results are loaded only when the user scrolls the page.

The framework scrolls twice to ensure additional streams are loaded.

```
scrollResults()
scrollResults()
```

---

## 4. Popup Handling

Some streamers show a **muted audio warning popup**.

The framework checks for this popup and closes it if present.

This prevents test failures caused by overlays blocking the UI.

---

# Test Case Explanation

## Test Name

`searchStarcraftStreamer`

## Objective

Verify that a user can search for a game and open a live stream.

---

## Test Steps

1. Launch browser
2. Open Twitch homepage
3. Click search icon
4. Enter game name **StarCraft II**
5. Select game from auto-suggestions
6. Scroll results twice
7. Select the first live stream
8. Wait until the stream player loads
9. Handle popup if it appears
10. Capture screenshot

---

## Test Flow Diagram

```
Start Test
   ↓
Launch Browser
   ↓
Open Twitch Homepage
   ↓
Click Search
   ↓
Search "StarCraft II"
   ↓
Select Game Suggestion
   ↓
Scroll Results
   ↓
Open First Live Stream
   ↓
Wait for Video Player
   ↓
Handle Popup
   ↓
Capture Screenshot
   ↓
Close Browser
```

---

# Key Benefits of This Framework

* Clean separation of test logic and page logic
* Reusable utilities
* Stable execution using explicit waits
* Easy to maintain and extend
* Scalable for additional test scenarios

---


## Automation Challenges with Twitch

Automating a modern streaming platform like Twitch presents several technical challenges due to the highly dynamic nature of the application. Twitch is built using modern front-end frameworks and loads content asynchronously, which means traditional automation approaches may lead to unstable tests if proper synchronization strategies are not implemented.

Below are some of the main challenges encountered while automating the Twitch user flow.

---

### 1. Dynamic UI Rendering

Twitch uses dynamic front-end rendering where many UI components are generated at runtime. Elements often contain dynamically generated CSS classes such as:

```
Layout-sc-1xcs6mc-0
ScCoreButton-sc-ocjdkq-0
```

These classes change frequently and cannot be relied upon for stable locators.

**Solution implemented in the framework:**

* Use stable attributes such as `aria-label`, `data-a-target`, and `data-test-selector`.
* Avoid using auto-generated CSS classes in XPath or CSS selectors.

Example of a stable locator:

```
By.xpath("//div[@data-a-target='video-player']")
```

---

### 2. Lazy Loading of Content

Twitch loads streams dynamically as the user scrolls down the page. This means that all stream elements are not available in the DOM immediately after the search results load.

**Automation impact**

* Elements may not exist when Selenium tries to locate them.
* Tests may fail with `NoSuchElementException`.

**Solution implemented in the framework:**

* Introduce controlled scrolling using JavaScript.
* Scroll the page multiple times to allow new stream cards to load.

Example implementation:

```
scrollResults();
scrollResults();
```

---

### 3. Element Click Interception

Interactive overlays and hover effects are frequently present on Twitch stream cards. These overlays can block Selenium from clicking on elements, causing errors such as:

```
ElementClickInterceptedException
```

**Solution implemented in the framework:**

* Scroll elements into view before clicking.
* Wait for elements to become clickable.
* Use a fallback JavaScript click if the normal click fails.

Example strategy:

```
scrollIntoView()
waitForElementClickable()
fallback JS click
```

---

### 4. Slow Player Initialization

Opening a stream does not immediately load the video player. The player loads in stages:

1. Page navigation
2. Player container rendering
3. Video element initialization
4. Player controls rendering

If the test interacts too early, the video may not yet exist in the DOM.

**Solution implemented in the framework:**

The framework waits for multiple indicators that confirm the stream has loaded.

Example waits:

```
wait for player container
wait for video element
wait for player controls
```

---

### 5. Popup and Overlay Interruptions

Some streams display popups such as:

* muted audio warnings
* copyright notices
* overlay messages

These popups can block interaction with the player.

Example popup handled in the framework:

```
Muted audio notice
```

**Solution implemented in the framework:**

* Check if the popup exists
* Dismiss it only when present
* Continue test execution

---

### 6. Asynchronous Page Navigation

When navigating between pages (for example from search results to a stream), Twitch loads content asynchronously.

This can cause tests to execute before the new page has fully loaded.

**Solution implemented in the framework:**

Use explicit waits combined with a page load check:

```
document.readyState = complete
```

---

### Summary

Automating Twitch requires careful handling of dynamic UI behavior. The framework addresses these challenges by implementing:

* stable locator strategies
* explicit waits
* scroll-based content loading
* popup handling
* safe click mechanisms

These strategies ensure the test execution remains stable even when the application loads content asynchronously.


# Conclusion

This framework demonstrates how UI automation can reliably test a dynamic application like Twitch by using structured design patterns, explicit waits, and reusable utilities. The Page Object Model ensures maintainability while proper synchronization ensures stable execution even on slow or dynamically loaded web pages.
#   t w i t c h - u i - a u t o m a t i o n  
 