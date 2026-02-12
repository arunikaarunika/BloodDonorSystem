🩸 BLOOD DONATION CAMP MANAGEMENT SYSTEM

📌 PROJECT OVERVIEW :

The Blood Donation Camp Management System is a Java-based database application developed to manage donor registrations, medical screening, and donation records for blood donation camps.
The system follows a structured layered architecture (Bean – DAO – Service) and integrates with an Oracle/SQL database using JDBC. It ensures proper validation, medical eligibility checks, and transaction handling while maintaining accurate donor and screening records.
This project demonstrates real-world healthcare workflow implementation, database transactions, and robust exception handling using Java.

✨ FEATURES :

📝 Register new donors with validation

🔍 View donor registration details

📅 View registrations by camp date, status, and blood group

❌ Cancel registration with reason (before screening)

🩺 Record medical screening results

💉 Mark donation as completed

✏ Adjust screening measurements

⚠ Custom exception handling for:

• Validation errors

• Donor already processed

• Medical deferral conditions


🏗 ARCHITECTURE :

The project follows a layered architecture:

• bean → Entity classes (DonorRegistration, ScreeningResult)

• dao → Database operations (Insert, Update, Select)

• service → Business logic and validation

• util → DB connection and custom exception classes


🛠 TECHNOLOGIES USED :

☕ Java – Core programming language

🔗 JDBC – Database connectivity

🗄 Oracle / SQL Database – Data storage

📦 Object-Oriented Programming (OOP) – Modular design

⚠ Custom Exception Handling – Business rule validation

💻 JDK – Compilation and execution environment

🖥 IDE – Eclipse

📸 OUTPUT SCREENSHOTS :

![WhatsApp Image 2026-02-12 at 11 43 49 AM](https://github.com/user-attachments/assets/4eaba6a3-d3a6-4846-b876-c5bfa0091249)
