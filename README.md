# Patient Management System

A robust, microservices-based application designed to manage patient records and billing operations. Built with Java and Spring Boot, this project implements a distributed architecture utilizing gRPC for highly efficient inter-service communication.

## 🏗️ Architecture Overview

The system is decoupled into two primary microservices:

* **Patient Service (`/patient-service`)**: The core RESTful service responsible for patient data management. It handles all CRUD operations (Create, Read, Update, Delete) via the `PatientController`.
* **Billing Service (`/billing-service`)**: An independent service that manages financial accounts for patients[cite: 3]. It operates as a gRPC server (`BillingGrpcService`) to process high-speed internal requests.

### Inter-Service Communication
The `patient-service` acts as a gRPC client (`BillingServiceGrpcClient`) to communicate directly with the `billing-service`. The strict contract between these two services is defined using Protocol Buffers (`billing_service.proto`, located in both services' `/src/main/proto/` directories).  

## 🚀 Tech Stack

* **Framework:** Java / Spring Boot
* **Build Tool:** Maven (Includes `mvnw` wrappers for consistent environments)  
* **RPC Framework:** gRPC & Protocol Buffers
* **Containerization:** Docker (`Dockerfile` provided within each service module)  

## 📂 Key Features

* **Robust Error Handling:** The `patient-service` implements a centralized `GlobalExceptionHandler` to gracefully manage domain-specific faults, such as `PatientNotFoundException` and `EmailAlreadyExistsException`.
* **Strict Data Validation:** Incoming requests are validated against custom rules (e.g., `CreatePatientValidationGroup`) utilizing a clean DTO pattern (`PatientRequestDTO`, `PatientResponceDTO`)   
* **Automated Database Seeding:** Includes a `data.sql` script within the `patient-service` resources to initialize the database upon application startup[cite: 3].
* **Ready-to-Use API Tests:** Pre-configured HTTP client files are available in the `/api-requests` and `/grpc-requests` directories for rapid endpoint testing directly from your IDE.

## ⚙️ Getting Started

### Prerequisites
* Java Development Kit (JDK)
* Docker (for containerized deployments)  

### Running the Services Locally

**1. Start the Billing Service (gRPC Server)**
Navigate to the billing service directory and use the Maven wrapper:
```bash
cd billing-service
./mvnw spring-boot:run
