# API Testing Framework

This project is designed to provide a robust and extensible framework for API testing. Using Java and RestAssured as library to perform HTTP requests.

---

## Features

- **Allure Integration**: Allure reporting for enhanced test reporting and result analysis.
- **Scalable Design**: Designed with extensibility in mind to accommodate future testing requirements.

---

## Setup

### Prerequisites

- **Java Development Kit (JDK)**: Version 8 or later.
- **Maven**: Ensure Maven is installed and available on your CLI.

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/figuedmundo/medirect-api-framework && cd medirect-api-framework 
   ```

2. Install the necessary dependencies:
   ```bash
   mvn clean install -DskipTests
   ```

---

## Running Tests

1. Execute the tests using Maven:
   ```bash
   mvn test
   ```

2. Generate and view Allure reports:
   ```bash
   allure serve target/allure-results
   ```

---

## Project Structure

```plaintext
src/main/java/com/medirect/api/          # Core API testing logic and utility classes
src/test/java/com/medirect/api/          # API test cases and base configurations
target/                                  # Build results and report files
```


---

## CI/CD Integration

This project includes a GitHub Actions workflow for running and testing the project in a CI/CD pipeline.

The latest run can be found in the Actions tab of the repository.

---
