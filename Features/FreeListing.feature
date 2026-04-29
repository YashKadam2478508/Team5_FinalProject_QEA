Feature: Free Listing Registration Form Validation
  As a business owner
  I want to register my business for a free listing on IndiaMart
  So that customers can discover my business

  Scenario: Show error message when phone number is invalid
    Given I am on the IndiaMart home page
    When I navigate to the Free Listing registration page
    And I fill the form with valid details but an invalid phone "ABCD12345"
    And I submit the registration form
    Then an error message should appear for the phone field
    And the captured error message text should be displayed
