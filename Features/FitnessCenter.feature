Feature: Fitness Center Gym Sub-Menu Navigation
  As a user
  I want to navigate to the Fitness > Gym category on IndiaMart
  So that I can see all available gym sub-menu items

  Scenario: Retrieve all Gym sub-menu items from Fitness category
    Given I am on the IndiaMart home page
    When I navigate to the "Fitness Center" menu category
    And I select "Gym" from the Fitness sub-categories
    Then I should see a list of Gym sub-menu items
    And all sub-menu items should be stored in a collection
    And each sub-menu item name should be printed to the console
