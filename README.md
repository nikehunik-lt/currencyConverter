# 📈 Currency Analytics Telegram Bot & API

## 📝 Project Overview
This is a currency tracking application and Telegram Bot built with **Java** and **Spring Boot**.
The system automatically fetches daily exchange rates from a public Bank API, saves them locally in a relational database, and provides users with current rates and analytics. It features a fully functional REST API with pagination and a Telegram Bot interface for generating weekly analytical charts.

## 🛠 Tech Stack
* **Language:** Java 21
* **Framework:** Spring Boot 3.4+
* **Database:** PostgreSQL + Spring Data JPA
* **Bot API:** Telegram Bots Spring Boot Starter
* **Charting:** XChart / JFreeChart
* **Build Tool:** Maven

## 🎯 Key Features
* **Automated Data Fetching:** Scheduled tasks to pull daily rates from a public Bank API.
* **Telegram Bot Interface:** Simple commands to get current rates and weekly analytics charts.
* **REST API:** Core CRUD API for currencies and exchange rates with pagination support.
* **Local Storage:** Persistent data storage locally for analytical queries.

## 🏗 System Architecture
1. **API / Bot Layer:** REST Controllers for basic entity management and Telegram Bot handlers.
2. **Service Layer:** Business logic, Bank API integration, chart generation, and data processing.
3. **Data Layer:** Spring Data JPA Repositories interacting with a relational database.

---

## 🛠 Roadmap
- [ ] **Phase 1: Initial Setup & API Foundation (Current)**
  - Setup Spring Boot project with Spring Data JPA & Web.
  - Create related entities (`Currency` and `ExchangeRate`).
  - Implement base API (Controllers, Services, Repositories).
  - Add Pagination (`Pageable`) and basic CRUD (using `EntityManager.merge` for updates).
- [ ] **Phase 2: External API & Scheduling**
  - Integrate Open Bank API (RestTemplate / WebClient).
  - Add `@Scheduled` jobs to update database daily.
- [ ] **Phase 3: Telegram Bot & Analytics**
  - Register bot via BotFather and integrate Telegram library.
  - Implement weekly data aggregation.
  - Generate visual charts and send them via the bot.