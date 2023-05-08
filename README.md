# Project Title
SafetyNet Alerts is an application that retrieves subscribers' essential information, \
such as medical history and current medications, to send to emergency service systems. \
This information is used by first responders to provide faster and more accurate care in emergency situations.


## Prerequisites

- Java 19
- Spring Boot
- Maven
- Lombok
- Google Gson
- AssertJ Core
- Surefire Report
- JaCoCo

## Dependencies

- spring-boot-starter-web
- spring-boot-starter-actuator
- spring-boot-starter-test
- springdoc-openapi-starter-webmvc-ui
- spring-boot-starter-parent

## Installation and Setup

1. Clone the repository:
   git clone https://github.com/Ivano-P/safetynetalerts.git

2. Navigate to the project directory:
   cd safetynetalerts

3. Install dependencies:
   mvn install

4. Configure environment variables or any necessary configuration files.

5. Run the application:
   mvn spring-boot:run

## Usage
This application provides a set of API endpoints to manage and retrieve information about \
people, fire stations, and medical records. Here are the key endpoints and their \
functionalities:

### People-related endpoints

Get people covered by a fire station\
Endpoint: GET http://localhost:8080/firestation?stationNumber=<station_number>
Returns a list of people covered by the specified fire station number, including their first \
name, last name, address, and phone number. It also provides a count of adults and \
children (individuals aged 18 or younger) in the covered area.

Get children living at an address\
Endpoint: GET http://localhost:8080/childAlert?address=<address>
Returns a list of children (individuals aged 18 or younger) living at the specified address.\
The list includes each child's first name, last name, age, and a list of other household \
members. If there are no children at the address, an empty list is returned.

Get phone numbers of residents served by a fire station\
Endpoint: GET http://localhost:8080/phoneAlert?firestation=<firestation_number>
Returns a list of phone numbers of residents served by the specified fire station number, \
used for sending emergency text messages to specific households.

Get residents and fire station number for an address\
Endpoint: GET http://localhost:8080/fire?address=<address>
Returns a list of residents living at the specified address, along with the fire station \
number serving that address. The list includes each resident's name, phone number, age, \
and medical history (medications, dosage, and allergies).

Get email addresses of all city residents\
Endpoint: GET http://localhost:8080/communityEmail?city=<city>
Returns a list of email addresses of all residents in the specified city.


### Fire station-related endpoints

Add, update, or delete a fire station mapping\
Endpoint: POST/PUT/DELETE http://localhost:8080/firestation
Allows adding a new fire station-address mapping, updating the fire station number for an \
address, or deleting a fire station or address mapping.


### Medical record-related endpoints

Add, update, or delete a medical record\
Endpoint: POST/PUT/DELETE http://localhost:8080/medicalRecord
Allows adding a new medical record, updating an existing medical record (assuming first name\
and last name don't change), or deleting a medical record (using a combination of first name \
and last name as a unique identifier).
To interact with these endpoints, you can use tools like Postman or curl,\
or integrate the API into a front-end application.


## Testing

To run the tests, execute the following command: 'mvn clean test' .

## License

This project is licensed under the  GNU GENERAL PUBLIC LICENSE Version 3,29 June 2007 License -\
see the [LICENSE.txt](LICENSE.txt) file for details.

## Acknowledgements

I completed this project as part of the OpenClassroom Java development course, project 5. \
Although I wrote all the code myself, I followed the objectives outlined in the project \
instructions. Additionally, I received valuable assistance from Christus T, \
a highly skilled backend Java developer, throughout the development process.