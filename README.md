# Appointment Management System

This program is a desktop CRUD application that interfaces with a MySQL Server database. This application features a GUI that can be used to maintain and modify user profiles and appointments.

## Table of Contents

- [Introduction](#introduction)
- [Features](#features)
- [Prerequisites](#prerequisites)
- [Installation and Usage](#installation-and-usage)
- [Development](#development)

## Introduction

I modeled this program after the concept of an international organization needing a desktop application to keep track of user profiles and their associated appointments. This program features a GUI that allows users to store and manipulate 
data about users and appointments. The information is persistent, meaning it is backed up by a MySQL Server, with any changes to user and appointment data reflected in both the GUI and the database. This application also determines the user's 
location and displays all appointment times and notifications based on their time zone. Keeping international organization requirements in mind, login screen text and relevant error messages are displayed in French if the user is located in 
France. This translation functionality could easily be expanded to include more languages to accommodate user needs.  

## Features

- Main Screen that features an interactive Parts table and Products table. These tables display information about part/product name, ID, inventory level, and cost per unit.
- On the Main Screen, a search field above each table allows users to perform full or partial searches by ID or name. An error message is displayed if there are no matching search results. If search field is empty, all results are displayed.
- Main Screen also allows users to delete parts and products from the table. A product can only be deleted if it has no associated parts. An error message is displayed if a user attempts to delete a product with an associated part.
  Before deleting a part or product, a confirmation message is displayed.
- A user must select a part/product in the table before it can be modified or deleted. An error message is displayed if user clicks modify or delete button while no part/product is selected.
- From the Main Screen, there is an add button under both the Parts and Products tables. Clicking this button opens a window that allows the user to add a new part or product to the respective table.
    - The Add Part window contains fields for the user to enter part information, as well as select whether the part is in-house or outsourced.
    - The Add Product window contains fields for the user to enter product information, as well as a table of current parts that the user can select from to add or remove an associated part to the product.
    - Both Add Part and Add Product windows contain user input validation- no fields can be left empty, appropriate data type of input is ensured, minimum inventory should be less than maximum, and current inventory must be between these two values.
    - Both Add Part and Add Product windows contain cancel buttons that void the new entry and return the user to the Main Screen.
    - Both Add Part and Add Product windows contain save buttons that save the new entry and return the user to the Main Screen. The new entry is displayed in its respective Parts or Products table on the Main Screen.
- If user selects a part/product and clicks modify button under the respective table, a window opens with pre-populated data about the part or product. The user can modify this info with the same capabilities as described above for the Add Part/Product windows.
- An exit button allows the user to exit the application.

## Prerequisites

- Install your preferred IDE with Java support, I used [IntelliJ IDEA Community Edition](https://www.jetbrains.com/idea/download/other.html). I suggest using the latest version.
- Install [JDK 17](https://www.oracle.com/java/technologies/downloads/#java17). I used JDK 17 because it is the long term support version.

## Installation and Usage

1. Ensure that you have an IDE installed that supports Java, as well as JDK 17 (see prerequisites).
2. Clone [this repository](https://github.com/alyssapugely/inventory-management-system)
3. Open the project in IDE with Java support.
4. To run the program in IntelliJ, click the "Run" tab in the top toolbar and select "Run 'Inventory Management System'" option.

## Development

As mentioned above, I used JDK 17 in IntelliJ to develop this program. I used [Scene Builder](https://gluonhq.com/products/scene-builder/) to design the GUI components, which utilizes the JavaFX library to program the GUI. Since I used a Maven build for this program, 
I did not need to download the JavaFX SDK. However, had I not used a Maven build, I would have needed to install [JavaFX SDK](https://gluonhq.com/products/javafx/). A note on Scene Builder- IntelliJ does have a built in Scene Builder, but it is quite slow, so I 
recommend installing the stand alone version I linked. 

This program uses MVC (Model View Controller) architecture. The Model package contains the Product, InHouse, Outsourced, Part, and Inventory classes, which are used to create objects from these classes and manipulate the objects using the functions created in these classes. The Model package is written in Java. The View package holds five JavaFX files that create five different screens- Add Part, Add Product, Modify Part, Modify Product, and Main Screen. These files contain the code that is reponsible for the visual elements of the GUI. The Controller package contains five classes- one controller class for each of the aforementioned screens. The Controller package is written in Java, and is responsible for making the GUI elements interactive. These controller classes contain code that manipulates the GUI based on user input. 
