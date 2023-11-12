# Residential Estate Management System -


Project Overview
This Java-based application is designed to facilitate developers in managing a network of residential estates. It allows for the handling of multiple estates, each comprising various blocks with a finite number of rental apartments. The software provides a comprehensive system for tracking tenant information, managing rental agreements, and overseeing estate operations.

# Features
Estate and Block Management: Allows a developer to own multiple estates, each containing several blocks of apartments.
Apartment and Parking Space Details: Each apartment and parking space has unique identifiers and information on its usable area.
Tenant Management: Ability to manage tenant details, including the primary tenant responsible for rental payments.
Flexible Rental Options: Offers rental options for apartments and additional closed parking spaces, suitable for vehicle and item storage.
Person Class: Stores detailed information about individuals residing in the apartments.
Space Allocation: Ensures each apartment and parking space can only have one tenant at a time, with a limit of up to 5 rentals per person.
Automatic ID Generation: Unique identifiers for apartments and parking spaces are automatically generated.
Space Measurement Options: Includes two methods for defining space size - by volume or by dimensions (length, width, height).
Rental Duration Tracking: Tracks start and end dates of rentals, issuing TenantLetters if rentals are overdue.
Time Progression Simulation: Implements a thread mechanism to simulate the passage of time, affecting rental agreements.
Tenant Alerts: Generates alerts for rental renewals or terminations, with automatic clearing or retention of related documents.
Eviction and Clearance Procedures: Defines procedures for evicting tenants and clearing spaces after rental expiration.
Vehicle and Item Management: Classifies different types of vehicles with specific characteristics and checks for space availability before placement.
Exception Handling: Implements ProblematicTenantException and TooManyThingsException for handling specific rental scenarios.
Data Persistence: Offers functionality to save state data, with sorting features for space and item sizes.
User Interaction
The program is interactively controlled through a command-line interface, allowing users to access all functionalities described in the project scope.
Preloaded with several ready-to-use estates, apartments, parking spaces, and tenants for immediate operation.
