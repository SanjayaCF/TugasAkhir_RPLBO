# MembershipMaster

## Overview

**MembershipMaster** is a comprehensive Java-based desktop application designed to streamline membership management for various services and platforms. This application provides a user-friendly interface for both regular users and administrators to manage memberships, subscriptions, and payments efficiently. With features ranging from user registration and login to detailed membership tracking and analytics, MembershipMaster is the all in one solution for hassle-free membership management. This project was developed as part of the Final Project for the Software Engineering course, demonstrating a robust and feature-rich application.

-----

## Key Features

MembershipMaster offers a wide array of features to enhance the user experience and provide powerful management tools:

### For Users:

  * **User Authentication:** Secure registration and login functionality with password encryption using **bcrypt**.
  * **Session Management:** "Remember Me" option for persistent login sessions.
  * **Membership Management:**
      * Add new memberships from a predefined list of categories and services.
      * View a comprehensive list of all your current memberships with details like price, expiration date, and benefits.
      * Search and filter your memberships by name, category, payment type, or expiration date.
      * View detailed information for each membership, including payment history, benefits, and renewal information.
      * Cancel your active memberships.
  * **Payment and Renewals:**
      * Support for multiple currencies (IDR, USD, EUR, GBP) with automatic price conversion.
      * Flexible payment intervals (Monthly, Yearly).
      * Automatic payment reminders for upcoming renewals.
  * **Personalized Experience:**
      * A personalized dashboard that welcomes you by name.
      * Dedicated sections for account information, privacy policy, and about us.

### For Administrators:

  * **Admin Dashboard:** A dedicated dashboard for managing users and memberships.
  * **User Management:**
      * View a list of all registered users with their details.
      * See the total number of users and administrators.
  * **Membership Control:**
      * Add new membership categories and services to the platform.
      * View and manage all available memberships.

-----

## 🛠️ Technologies Used

This project is built with a stack of modern and reliable technologies:

  * **Backend:** Java
  * **Framework:** JavaFX for the graphical user interface
  * **Database:** SQLite for a lightweight and serverless database solution
  * **Build Tool:** Maven for project and dependency management
  * **Libraries:**
      * **jBCrypt** for secure password hashing.
      * **OpenJFX** for building a modern and responsive user interface.

-----

## 🚀 Getting Started

To get a local copy up and running, follow these simple steps.

### Prerequisites

  * **Java Development Kit (JDK) 17 or higher:** Make sure you have a compatible version of the JDK installed.
  * **Maven:** This project uses Maven for dependency management.
  * **IDE:** An IDE like IntelliJ IDEA, Eclipse, or VS Code with Java support.

### Installation

1.  **Clone the repository:**
    ```sh
    git clone https://github.com/SanjayaCF/TugasAkhir_RPLBO.git
    ```
2.  **Navigate to the project directory:**
    ```sh
    cd TugasAkhir_RPLBO
    ```
3.  **Build the project and install dependencies using Maven:**
    ```sh
    mvn clean install
    ```

### Running the Application

Once the project is set up, you can run the application using the following Maven command:

```sh
mvn javafx:run
```

-----

## प्रोजेक्ट संरचना

The project is organized into the following structure:

```
TugasAkhir_RPLBO/
├── .idea/              # IntelliJ IDEA project files
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── org/
│   │   │       └── example/
│   │   │           └── membershipapp/
│   │   │               ├── Apps.java               # Main application class
│   │   │               ├── Main.java               # Main entry point
│   │   │               ├── manager/                # Manager classes
│   │   │               │   ├── databaseConnect.java  # Database connection
│   │   │               │   └── SessionManager.java   # User session management
│   │   │               └── view/                   # FXML controllers
│   │   └── resources/
│   │       └── org/
│   │           └── example/
│   │               └── membershipapp/
│   │                   └── view/                   # FXML files
│   └── test/
├── .gitignore          # Git ignore file
├── membership.sqlite   # SQLite database file
├── pom.xml             # Maven project configuration
└── README.md           # Project README file
```

-----

## 👥 About the Team

This project was a collaborative effort by a dedicated team of students:

  * Rich Immanuel Mahendra H (71180396)
  * Daniel Kimyuwono (71220830)
  * Rendy Ananta Kristanto (71220840)
  * Leif Sean Kusumo (71220915)
  * Sanjaya Cahyadi Fuad (71220965)
