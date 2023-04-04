Feature: Test SVTPlay

  Scenario: SVT Play should show correct title
    Given SVT Play is available
    When User visits SVT PLay
    Then The title should be "SVT Play"