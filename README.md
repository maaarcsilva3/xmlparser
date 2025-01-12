# XMLParser

**XMLParser** is a simple batch program that accepts XML files as input, creates JSON files from the XML data, moves files to archive or failed directories, and generates SQL insert queries. This project demonstrates various software development principles and patterns.

## Purpose

The purpose of this project is to showcase the use of the following:

### 1. OOP Principles
- **Abstraction** (interface and abstract classes)
- **Encapsulation**
- **Inheritance**
- **Polymorphism** (method overriding)

### 2. Common Design Patterns
- **Template Pattern**

### 3. IO Processing
- File manipulation:
  - **Reading**
  - **Converting**
  - **Writing**
  - **Moving**

### 4. Database Connection Pooling

### 5. Concurrency/Multithreading

### 6. Error Handling

### 7. Logging

## Requirements
- **JRE 1.8**
- **SQL Database**

## Getting Started

- **1. Recreate the SQL Database**
Use the following schema to create the necessary database table:

```sql
CREATE TABLE `records` (
  `id` int NOT NULL AUTO_INCREMENT,
  `file_name` varchar(255) NOT NULL,
  `image_location` varchar(255) NOT NULL,
  `xml_location` varchar(255) NOT NULL,
  `json_location` varchar(255) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
);
```

- **2. Download the XMLParser-1.0.1-jar-with-dependencies.jar**
- **3. Download the config.properties and place it inside the config folder (config/config.properties)**
- **4. Make sure that the XMLParser-1.0.1-jar-with-dependencies.jar and the config folder is in the same directory.**
- **5. Update the values of the configs.properties for the location and the credentials**
- **5. Open the cmd, cd to the location of the jar file and run  java -jar XMLParser-1.0.1-jar-with-dependencies.jar**

### Important Reminders
- **1. For the xml input, please follow the format of the xml from the xml example in output/archive.**
- **2. Make sure that the xml data has FileName tag.**
- **3. The FileName tag value is the filename of the document.**

