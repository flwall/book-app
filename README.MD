A Book Management App with a Quarkus REST-API (J2EE) (and a Apache Derby Embedded DB in the Background)

And a React JS Frontend Application

# Installation

``docker-compose build``
``docker-compose up``

Afterwards, the React Frontend runs on port 8001

## Installation (without Docker):  
### To start the server
    ``cd book_api&&mvnw package&&java -jar target/*runner.jar``
### To start the React Frontend (using npm):
    ``cd bookmgmnt-react-front&&npm install&&npm start``

# Disclaimer

 This project is neither actively maintained nor under active development.
 If you experience any bugs or want extra features, you can send me a pull request or open a GitHub Issue and I'll try to fix it.