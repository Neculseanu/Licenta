Feature: Amazon Purchase Journey

  Scenario: Search and add a product to cart
    Given I am on the Amazon home page
    When I search for "laptop"
    And I select the first product from the results
    And I add the product to the shopping cart
    And I view the shopping cart
    Then I should see the product listed in the cart