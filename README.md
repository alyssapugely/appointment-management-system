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

- Login form that displays error message if incorrect login credentials are entered. It also displays the user's location, and presents the form and error messages in either English or French based on locale.
- Login form also tracks invalid login attempts in a seperate file.
- Upon successful login, a notification message is displayed if there is or is not an appointment occuring within the next 15 minutes, based on user's local time.
- Main Screen that features an interactive Appointments and Customers table.
    - The Appointments table holds the appointment ID, title, type, description, location, start and end date/time, contact, customer ID, and user ID. The table can be filtered by all appointments, or only those ocurring within the current week or month.
    - The Customers table holds the customer ID, name, phone number, address, state/province, and postal code.
- Main Screen also allows users to delete customers and appointments. A customer/appointment must be selected in the table to delete, and an error message is displayed if nothing is selected when the delete button is clicked. If a customer is selected to be deleted
  and they have associated appointments, a confirmation message is displayed notifying that their appointments will also be deleted.
- A user must also select a customer/appointment before it can be modified. An error messge is displayed if user clickes the modify button while nothing is selected.
- From the Main Screen, there is an add button under both the Appointments and Customers tables. Clicking this button opens a window that allows the user to add a new appointment or customer to the respective table.
    - The Add Appointment window contains text fields, dynamically populated dropdown menus, and calendar widgets for the user to enter appointment information.
    - The Add Customer window contains text fields and dynamically populated dropdown menus for the user to enter customer information.
    - Both Add Appointment and Add Customer windows contain user input validation- no fields can be left empty, appropriate data type of input is ensured, appointment start time must be before end time, appointments can't be scheduled outside of business hours, and
      a customer can't schedule overlapping appointments. 
    - Both Add Appointment and Add Customer windows contain cancel buttons that void the new entry and return the user to the Main Screen. A confirmation message is displayed before the user is returned to the Main Screen.
    - Both Add Appointment and Add Customer windows contain save buttons that save the new entry and return the user to the Main Screen. The new entry is displayed in its respective table on the Main Screen.
- If user selects an appointment/customer and clicks modify button under the respective table, a window opens with pre-populated data about the appointment or customer. The user can modify this info with the same capabilities as described above for the Add windows.
- From the Main Screen, there is a Reports button that opens a Reports Screen. This screen contains a back button to return to the Main Screen, and it also contains the following reports:
    - A table of appointments organized per contact (not the customer, but the person leading the appointment). This table contact appointment info, and can be filtered by contact via a dropdown menu above the table.
    - A field to display the number of appointments filtered by month and appointment type dropdown menus. The menus are dynamically populated.
    - A field to display the number of customers per division (state or province), filtered by a dynamically populated division dropdown menu.
- A logout button on the Main Screen and Reports Screen logs the user out of the application and returns them to the login form.
- An exit button on the login form closes the application.

## Prerequisites

- Install your preferred IDE with Java support, I used [IntelliJ IDEA Community Edition](https://www.jetbrains.com/idea/download/other.html). I suggest using the latest version.
- Install the latest [MySQL Installer](https://dev.mysql.com/downloads/installer/). Once package is installed, open and use the "Select Products" menu to install the latest version of MySQL Server and Workbench.
- Install [JDK 17](https://www.oracle.com/java/technologies/downloads/#java17). I used JDK 17 because it is the long term support version.
- Install the latest long term support version of [JavaFX](https://gluonhq.com/products/javafx/) that is compatible with JDK 17.
- Install [MySQL Connector Driver Version 8.0.25](https://downloads.mysql.com/archives/c-j/). This will enable the application to communicate with the MySQL database.

## Installation and Usage

1. Ensure that you have all of the above prerequisites installed.
2. This program requires the MySQL server to be configured to a certain port and user profile so the application can interface with the database. Please reach out if you are interested in obtaining the configuration details so you can establish a
   a connection with the server to run this program. 
4. Clone [this repository](https://github.com/alyssapugely/appointment-management-system)
5. Open the project in IDE with Java support.
6. To run the program in IntelliJ, click the "Run" tab in the top toolbar and select "Run 'Main'" option.

## Development

As mentioned above, I used JDK 17 in IntelliJ to develop this program. I used [Scene Builder](https://gluonhq.com/products/scene-builder/) to design the GUI components, with the support of the JavaFX SDK to program them. A note on Scene Builder- IntelliJ does have a built in Scene Builder, but it is quite slow, so I recommend installing the stand alone version I linked. I also used MySQL to create a database to store and retrieve application data.

This program uses MVC (Model View Controller) architecture. The Model package contains the Appointment, Contact, Country, Customer, Division, and User classes, which are used to create objects from these classes and manipulate the objects using the methods created in these classes. The Model package is written in Java. The View package holds seven JavaFX files that create seven different screens- Add Appointment, Update Appointment, Add Customer, Update Customer, Reports, Main, and Login screens. These files contain the code that is reponsible for the visual elements of the GUI. The Controller package contains seven classes- one controller class for each of the aforementioned screens. The Controller package is written in Java, and is responsible for making the GUI elements interactive. These controller classes contain code that manipulates the GUI based on user input. There is also a Utilities package that contains classes that hold methods used repeatedly throughout the program. Finally, a DAO (Data Access Object) package contains six classes to query each of the tables in the database (Apointments, Contacts, Countries, Customers, Divisions, Users), as well as a JDBC (Java Database Connectivity) class to open and close the connection with the MySQL database.
