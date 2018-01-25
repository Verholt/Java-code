@CWFCOM-69
Feature: Order number
	#As a CSA when I create a Customer Order in the Booking Screen I need the system to create a unique identifier for the customer order so that it can be distinguished from all other customer orders.
	#
	#Sequence will be an Alphanumeric value starting ith CO (Customer Order) and 15 Digits
	#
	#Application running 20-25 years 12million numbers per month
	#
	#Ability to seed with forinstance country code CO (XX) 15 digits?

	
	@CWFCOM-458 @CWFCOM-522
	Scenario: CWFCOM-69 Unique Order Number (Max)
		Given I am creating an order
		When last Order id is CO999999999999999
		Then the system provides an unique Number (id) starting with CO.
		And the ID is CO1
		
	@CWFCOM-457 @CWFCOM-522
	Scenario: CWFCOM-69 Unique Order Number
		Given I am creating an order
		When last Order id is CO9999999999999
		Then the system provides an unique Number (id) starting with CO.
		And the ID is CO10000000000000
		