Feature: Car Wash Services Search
  As a user
  I want to find top-rated car wash services near me on IndiaMart
  So that I can select the best rated option

  Scenario: Display top 5 car wash services with rating above 4 and votes above 20
    Given I am on the IndiaMart home page
    When I search for "Car Wash Service" near "Chennai"
    Then the results page should be displayed
    And I should see at least 5 services with rating above 4 and votes above 20
    And services should be sorted with highest rating on top
    And each service should display its name and phone number
