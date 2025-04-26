# Habit Vibes Quote API Tehnical Design Document 



## Table Of Contents 

| Section                    | Summary                                                      |
| -------------------------- | ------------------------------------------------------------ |
| **Introduction**           | Overview of the Habit Vibes Quote API, the purpose of the API, and the goals of this technical design document. |
| **Problem Statement**      | Explanation of the problem we are solving, why an API is needed, and how it supports user experience. |
| **Tech Stack**             | High-level description of the API solution, including how the app fetches and uses motivational quotes. |
| **Coding Standards**       | Outline of coding standards that will be used for API development. |
| **API Design**             | Detailed breakdown of API endpoints, request/response structures, authentication requirements, and error handling. |
| **Release Schedule**       | Outlines the planned rollout of API functionality across three separate releases, gradually introducing active quote management, pending quote moderation, and approval features. |
| **Pending Quotes**         | Description of pending quote workflow, including creation, approval, and publishing processes. |
| **Environments**           | Summary of different environments (Development, Staging, Production) and how API deployment is handled in each. |
| **Github and CI Workflow** | Outlines the branching strategy, pull request requirements, and automated CI checks (linting, formatting, and unit tests) that must pass before merging into the master branch. |
| **Future Features**        | Discussion of potential future features and enhancements for the API and the management application. |
| **Appendix**               | Additional references, diagrams, example payloads, or other supporting information. |



#### Introduction 

This document serves to explain the API definitions we are creating for the mobile application **Habit Vibes**. **Habit Vibes** is a native Android app that allows users to create and track daily habits they input into the app. Each habit is tied to a single user's account and will not be uploaded to the API.

Instead, the API will support the app by providing motivational quotes to encourage users as they track their habits. These quotes will be stored and managed through the API. While the mobile app will only fetch quotes, we also need a way to create and manage them through a separate management application. This includes functionality for creating pending quotes, which will be discussed later in this technical design document.

In addition to defining the quote management endpoints, this document outlines the technical standards for the API, including the technologies we plan to use, code examples, and supporting tools. It also provides context around the problem we're solving and the purpose behind this system, helping readers clearly understand the goals and approach we are taking.

#### Problem Statement 

The problem we aim to solve is providing an API that meets the needs of **Habit Vibes** for its completion. Specifically, the requirement is to have a central repository for user-generated quotes that can be displayed to users. This approach moves away from saving quotes only locally on the user's device, instead allowing them to be saved in a shared space that benefits all users. The API will not only provide quotes for user motivation but also open the possibility for users to contribute to the collection of quotes shared with others. The goal is to fulfill these API requirements while adhering to established standards and ensuring compatibility with the chosen technology stack.

#### Tech Stack 

For the creation of this API, I plan to use the **Ktor** library with Kotlin support. I’ve chosen this library because, as a Kotlin-native Android developer, I’m already familiar with Kotlin and find Ktor to be a seamless fit for my skillset. The Kotlin-friendly nature of Ktor will allow me to build the API efficiently, leveraging my existing knowledge of the language. Additionally, I’m excited to explore Ktor further, as it is becoming an increasingly popular choice for building APIs, and I believe it will offer valuable insights into its capabilities and performance. This approach not only meets the technical needs of the project but also provides an opportunity to familiarize myself with a frequently used and modern tool in the Kotlin ecosystem.

#### Coding Standards 

For this project, we will follow the basic coding standards recommended by **Ktor** and best practices for Kotlin development. These standards may evolve as I continue researching and working with Ktor to optimize our approach.

### General Guidelines:

- **Naming Conventions**  
  - Use **CamelCase** for variables and functions.  
  - Use **PascalCase** for class names.  
  - Use **UPPER_SNAKE_CASE** for constants.

- **Modularity**  
  - Each module should focus on a single responsibility (e.g., routing, authentication).  
  - Functions should be small and concise, ideally under 30 lines.

- **Error Handling**  
  - Handle errors using Ktor's built-in error handling mechanisms.  
  - Log errors and provide meaningful error messages.

- **Routing**  
  - Group related routes under specific paths (e.g., `/quotes` for quote-related endpoints).  
  - Use the correct **HTTP methods** for actions (GET, POST, PUT, DELETE).

- **Security**  
  - Use **HTTPS** for secure communication.  
  - Implement authentication using Ktor’s authentication features (e.g., JWT, OAuth2).

- **Documentation**  
  - Document public functions and classes with **KDoc** annotations.

#### Future Adjustments 

These coding standards may be updated over time based on new insights and ongoing research into Ktor’s best practices.

- [Ktor Official Documentation - Code Style and Best Practices](https://ktor.io/docs/writing-ktor-applications.html)

  

#### API Design

This API is designed to manage motivational quotes for the Habit Vibes production and management mobile applications. It provides endpoints to fetch active quotes, submit new quotes (single or batch), manage pending quotes, and approve pending quotes into active quotes.

#### Data Model

All quotes follow the structure below:

```kotlin
@Serializable
data class Quote(
    val id: Int,
    val author: String,
    val source: String,
    val tags: List<String>,
    val createdAt: String,
    val loggedBy: String?
)
```

#### Endpoints

##### Fetch Active Quotes
- **Method:** `GET`
- **Path:** `/quotes`
- **Description:**  
  Fetches a list of all active, approved quotes available to users.
- **Documentation:** // Todo once endpoints becomes finalized

---

##### Submit New Quote(s)
- **Method:** `POST`
- **Path:** `/quotes`
- **Description:**  
  Accepts either a single quote or a list of quotes to add to the active quotes collection.
- **Request Body:**  
  One or multiple `Quote` objects.
- **Documentation:** // Todo once endpoints becomes finalized

---

##### Fetch Pending Quotes
- **Method:** `GET`
- **Path:** `/pending-quotes`
- **Description:**  
  Retrieves a list of quotes that have been submitted but are still pending approval.
- **Documentation:** // Todo once endpoint becomes finalized

---

##### Submit Pending Quote(s)
- **Method:** `POST`
- **Path:** `/pending-quotes`
- **Description:**  
  Accepts one or more quotes to be stored in the pending queue for review before becoming active.
- **Request Body:**  
  One or multiple `Quote` objects.
- **Documentation:** // Todo once endpoint becomes finalized

---

##### Approve Pending Quote
- **Method:** `POST`

- **Path:** `/approve-pending-quote`

- **Description:**  
  Takes in a pending quote ID or payload and promotes the quote to the active quotes list, making it available to users.
  
- **Request Body:**  
  Data required to identify and approve the pending quote (exact structure to be defined later).
  
- **Documentation:** // Todo once endpoint becomes finalized

  

#### Release Schedule

To ensure a smooth rollout and manageable development process, the API endpoints will be introduced across three structured releases:

---

##### Release 1: Submit and Fetch Active Quotes
- **Endpoints Included:**
  - `POST /quotes` — Submit one or multiple new quotes directly to the active quotes list.
  - `GET /quotes` — Fetch all active, approved quotes available to users.
- **Goal:**  
  Enable the core functionality of adding and retrieving motivational quotes for the Habit Vibes mobile app.

---

##### Release 2: Manage Pending Quotes
- **Endpoints Included:**
  - `POST /pending-quotes` — Submit one or multiple new quotes to the pending review queue.
  - `GET /pending-quotes` — Fetch all quotes that are currently pending approval.
- **Goal:**  
  Introduce a moderation workflow, allowing quotes to be reviewed before becoming public.

---

##### Release 3: Approve Pending Quotes
- **Endpoints Included:**
  - `POST /approve-pending-quote` — Approve a pending quote and promote it to the active quotes list.
- **Goal:**  
  Finalize the moderation system by providing the ability to approve and activate pending quotes.

#### Pending Quotes

Pending quotes represent user-submitted quotes that are not immediately made public to the Habit Vibes app users. Instead, they are stored in a pending state to allow for review, editing, or approval at a later time.

##### How Pending Quotes Work

- When a quote is submitted for review (instead of being made active immediately), it is sent to the `/pending-quotes` endpoint using a `POST` request.
- These quotes are stored separately from active quotes to allow moderation and quality control.
- Pending quotes are **not** visible to users until they have been manually approved.

#### Environments

The Habit Vibes API will be deployed and maintained across three different environments to ensure stability, quality, and proper validation before production releases.

##### Staging
- **Purpose:**  
  A development environment where new features, updates, and changes are first deployed.
- **Usage:**  
  Used for internal testing and validating functionality before promoting to test or release.
- **Notes:**  
  May experience breaking changes or incomplete features during active development.

##### Test
- **Purpose:**  
  A controlled environment for more formal testing, bug fixing, and QA (Quality Assurance).
- **Usage:**  
  Simulates near-production conditions for verifying stability.
- **Notes:**  
  More stable than staging; only completed features and fixes are promoted here.

##### Release
- **Purpose:**  
  The production environment where the live, stable version of the API is deployed.
- **Usage:**  
  Serves the Habit Vibes mobile app and its users.
- **Notes:**  
  Only thoroughly tested and approved updates from the Test environment are deployed here.

---

#### GitHub and CI Workflow

##### Continuous Integration (CI)

A CI workflow will be set up to automatically run validation checks on every pull request and push.  
The following tasks will be executed during the CI process:
- **Android Lint** — Verifies project code adheres to Android development best practices.
- **ktlint** — Enforces Kotlin code style and formatting.
- **Unit Tests** — Runs all project unit tests to ensure code stability.

All pull requests must successfully pass the CI pipeline before they can be merged into the `master` branch.

##### GitHub Branching Strategy

- **Master Branch**
  - Represents the current stable and deployable version of the API.
  - Only code that has passed CI and been reviewed can be merged here.
  
- **Feature Branches**
  - Developers will create feature-specific branches off `master`.
  - Example: `feature/add-quote-submission`

- **Version Branches**
  - After completing a set of features or preparing for a new API version, a version branch will be created.
  - Example: `version/v1`, `version/v2`
  - Version branches will not be deployed immediately but will be prepared for future versioned API releases.

##### Pull Request Workflow

- Open a PR against the `master` branch.
- Ensure the PR passes all CI checks (Android Lint, ktlint, Unit Tests).
- Merge into `master` only after all checks pass.

#### Future Features

One potential feature to explore in the future is integrating AI into the API to automatically analyze the database of quotes and suggest new ones based on patterns found in existing data. The AI could identify gaps in categories, popular themes, or author trends, then generate and submit new quotes directly to the API.

##### AI-Based Quote Generation
- **Goal:**  
  Leverage AI to enhance the content of the Habit Vibes app by automatically creating new motivational quotes.
  
- **How it Could Work:**
  - Implement an endpoint that triggers an AI model to analyze existing quotes stored in the API.
  - The AI would look for patterns like recurring themes, popular author styles, or underrepresented topics.
  - Based on this analysis, the AI would generate new quotes and submit them via the `POST /quotes` or `POST /pending-quotes` endpoint.

- **Potential Workflow:**
  - Add a new API endpoint like `POST /ai/generate-quotes`.
  - The AI system would receive existing quotes as input, generate new ones, and submit them back to the API as either active or pending quotes.
  
- **To-Do:**
  - Research AI model options that can generate text based on an input dataset.
  
  - Determine how to integrate AI services (could be cloud-based or custom models).
  
  - Test how to keep generated quotes relevant and high-quality for users.
  
    
  
  #### Appendix
  
  This section includes additional references, diagrams, and example payloads to help understand the functionality and structure of the Habit Vibes API.
  
  ##### 1. Additional References
  - **Ktor Documentation**  
    For a deeper dive into how Ktor is used for building APIs, visit [Ktor Official Documentation](https://ktor.io/docs/).
    
  - **Kotlin Serialization**  
    Learn more about how we use Kotlin's serialization library to handle data transfer objects (DTOs) like `Quote` by visiting [Kotlin Serialization Documentation](https://kotlinlang.org/docs/serialization.html).
  
  ##### 2. Diagrams
  - **API Interaction Flow**  
    The following diagram illustrates the high-level flow of how quotes are submitted, reviewed, and approved through the API:
  
    ```
    +---------------------+      +------------------+      +------------------+
    |   Submit Quote      | ---> |   Pending Quotes | ---> |   Approve Quote  |
    |   POST /quotes      |      |   GET /pending    |      |   POST /approve  |
    |   (or List)         |      |   (list of quotes)|      |   (approve quote)|
    +---------------------+      +------------------+      +------------------+
    ```
  
  ##### 3. Example Payloads
  
  - **Submitting a Quote (POST /quotes)**  
    Here's an example of a payload when submitting a quote:
  
    ```json
    {
      "id": 1,
      "author": "John Doe",
      "source": "Motivational Speech",
      "tags": ["motivation", "inspiration"],
      "createdAt": "2025-04-26T10:00:00Z",
      "loggedBy": "admin"
    }
    ```
  
  - **Fetching Pending Quotes (GET /pending-quotes)**  
    Example response from fetching pending quotes:
  
    ```json
    [
      {
        "id": 1,
        "author": "Jane Doe",
        "source": "Self-help Book",
        "tags": ["self-help", "growth"],
        "createdAt": "2025-04-25T15:30:00Z",
        "loggedBy": "admin"
      },
      {
        "id": 2,
        "author": "Jim Doe",
        "source": "Motivational Podcast",
        "tags": ["success", "achievement"],
        "createdAt": "2025-04-25T16:00:00Z",
        "loggedBy": "admin"
      }
    ]
    ```
  
  - **Approving a Pending Quote (POST /approve-pending-quote)**  
    Example payload for promoting a pending quote to an active quote:
  
    ```json
    {
      "quoteId": 1
    }
    ```
  
  ##### 4. Other Supporting Information
  
  - **Quote Approval Workflow**  
    Pending quotes are reviewed by a moderator and must be approved before they are visible in the app. The process involves:
    1. Submitting a quote (either as a single entry or list).
    2. Moderators reviewing the quote(s).
    3. Approving the quote(s) to become active and publicly available.
  
  - **API Versioning Strategy**  
    As the API evolves, versioning will be used to ensure backward compatibility. Each major change or feature release will be followed by a new version branch (e.g., `version/v1`, `version/v2`). This ensures that users and developers can continue to use older versions while new features are tested and deployed.
  
  ---

---

---