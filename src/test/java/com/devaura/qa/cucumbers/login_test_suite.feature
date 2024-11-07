@author:JuanSinMiedo
Feature: User Login-feature Test Functionality

  Background:
    Given she is on the login page at "https://www.saucedemo.com/"

  Scenario: Login successfully
    When she enters a valid username and a valid password
    Then she should be successfully logged in and redirected to the dashboard/homepage

  Scenario: Enter a bad username and a valid password
    Given the error message is "Username is incorrect"
    When she enters an invalid username and a valid password
    Then she should see the error message

  Scenario: Enter a valid username and a bad password
    Given the error message is "Password is incorrect"
    When she enters a valid username and an invalid password
    Then she should see the error message

  Scenario: Username field empty and enter a valid password
    Given the error message is "Username is required"
    When she leaves the username field empty and enters a valid password
    Then she should see the error message

  Scenario: Valid username and leave the password field empty
    Given the error message is "Password is required"
    When she enters a valid username and leaves the password field empty
    Then she should see the error message

  Scenario: Both fields empty
    Given the error messages are "Username is required" and "Password is required"
    When she leaves both the username and password fields empty
    Then she should see the appropriate error messages

  Scenario: Check the "Remember Me" option and log in with valid credentials
    When she checks the "Remember Me" option and logs in with valid credentials
    And she logs out
    Then upon returning to the login page, the username should be pre-filled

  Scenario: Leave the username and password fields empty
    Given the error message is "Login button is disabled"
    When she leaves the username and password fields empty
    Then the login button should be disabled until valid input is provided

  Scenario: Recover account with forgotten password
    Given she is on the password recovery page
    When she enters her registered email address
    And she submits the password recovery request
    Then she should see the message "Password recovery email has been sent"

  Scenario: Register a new account
    Given she is on the registration page
    When she enters a valid username, email address, and password
    And she submits the registration form
    Then she should see the message "Registration successful. Please log in."

  Scenario: Register with an existing username
    Given the error message is "Username already exists"
    When she enters an existing username, a valid email address, and a password
    And she submits the registration form
    Then she should see the error message

  Scenario: Register with an invalid email address
    Given the error message is "Invalid email address"
    When she enters a valid username, an invalid email address, and a password
    And she submits the registration form
    Then she should see the error message

  Scenario: Register with a weak password
    Given the error message is "Password is too weak"
    When she enters a valid username, a valid email address, and a weak password
    And she submits the registration form
    Then she should see the error message