Feature: User Authentication API

  Background: 
    Given the API endpoint is "https://api.example.com/auth"
    And the following status codes are defined:
      | Status Code       | Code |
      | Success           |  200 |
      | Unauthorized      |  401 |
      | Bad Request       |  400 |
      | Locked            |  423 |
      | Too Many Requests |  429 |

  Scenario: Valid Login
    When she sends a GET request with valid username and password
    Then the response status code should be Success
    And the response body should contain a success message and a token/session ID

  Scenario: Invalid Username
    When she sends a POST request with a bad username and a valid password
    Then the response status code should be Unauthorized
    And the response body should contain an error message indicating that the username is incorrect

  Scenario: Empty Username
    When she sends a POST request with an empty username field and a valid password
    Then the response status code should be Bad Request
    And the response body should contain an error message indicating that the username is required

  Scenario: Empty Password
    When she sends a POST request with a valid username and an empty password field
    Then the response status code should be Bad Request
    And the response body should contain an error message indicating that the password is required

  Scenario: Empty Both Fields
    When she sends a POST request with both username and password fields empty
    Then the response status code should be Bad Request
    And the response body should contain appropriate error messages for both fields

  Scenario: Account Lockout After Multiple Failed Attempts
    When she sends multiple POST requests with bad credentials (5 times)
    Then the response status code should be Locked
    And the response body should indicate that the account is temporarily locked

  Scenario: Successful Logout
    When she sends a POST request to the logout endpoint with a valid token/session ID
    Then the response status code should be Success
    And the response body should contain a success message indicating that the user has been logged out

  Scenario: Token Expiration
    When she attempts to access a protected resource with an expired token
    Then the response status code should be Unauthorized
    And the response body should indicate that the token is expired

  Scenario: Invalid Token
    When she sends a request with a bad or malformed token
    Then the response status code should be Unauthorized
    And the response body should indicate that the token is invalid

  Scenario: Password Recovery Link
    When she sends a POST request to the password recovery endpoint with a valid email
    Then the response status code should be Success
    And the response body should indicate that a password recovery email has been sent

  Scenario: Rate Limiting
    When she sends multiple login requests in a short period (10 requests within a minute)
    Then the response status code should be Too Many Requests
    And the response body should indicate that the user has exceeded the rate limit

  Scenario: Check Response Time
    When she measures the response time for a valid login request
    Then the response time should be less than 2 seconds

  Scenario: Localization/Internationalization
    When she sends a request with a valid username and password while specifying a locale in the headers
    Then the response status code should be Success
    And the response body should return messages in the specified language

  Scenario: Session Management
    When she logs in successfully
    Then the response status code should be Success
    And the response body should contain session details
