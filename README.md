# Currency Converter 

## 📝 Project Overview
This is a specialized currency conversion web application built with **Java 25** and **Spring Boot**.
Unlike standard converters that rely on general market rates, this project integrates with the **Google Sheets API**, using a private spreadsheet as a dynamic database. This allows users to manually manage exchange rates (e.g., specific bank rates with custom spreads/fees) in a familiar interface, while the backend automatically fetches and applies these updates.

## 🛠 Tech Stack
* **Language:** Java 25
* **Framework:** Spring Boot 3.4+
* **Build Tool:** Maven
* **External API:** Google Sheets API v4
* **DevOps:** Docker & Docker Compose
* **CI/CD:** GitHub Actions

## 🎯 Key Features
* **Custom Rate Source:** Direct integration with Google Sheets for personalized rate management.
* **Modern Java Implementation:** Utilizing `records` and latest Java features for clean, immutable data handling.
* **DevOps Ready:** Designed for containerized environments and automated deployment.

## 🏗 System Architecture
The application follows a standard layered architecture:
1.  **Client Layer:** REST API endpoints for conversion requests.
2.  **Service Layer:** Business logic for currency calculation and Google Sheets data processing.
3.  **Data Layer:** Integration with Google Cloud Platform via Service Account.

---

## 🛠 Roadmap
- [x] **Phase 1: Initial Setup**
    - Initialize Git repository.
    - Setup Maven project structure (Java 25).
    - Define Domain Model (`CurrencyRate` record).
- [ ] **Phase 2: Google API Integration**
    - Setup Google Cloud Console project.
    - Implement Service Account authentication.
    - Create Google Sheets client service.
- [ ] **Phase 3: Core Logic**
    - Implement conversion math service.
    - Setup caching mechanism (to avoid Google API rate limits).
- [ ] **Phase 4: Web Interface**
    - Develop Spring Web REST Controllers.
    - (Optional) Simple Thymeleaf or React frontend.
- [ ] **Phase 5: DevOps & Deployment**
    - Write `Dockerfile` and `docker-compose.yml`.
    - Configure GitHub Actions for automated testing.
