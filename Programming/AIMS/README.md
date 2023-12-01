#  An Internet Media Store
<p align="center">
  <img src="assets/images/aims_cover_image.png" />
</p>

## Getting Started

Welcome to the AIMS project. Here is a guideline to help you get started.

## Folder Structure

The workspace contains the following folders, where:

- `src`: the folder to maintain sources code
- `assets`: the folder to maintain static resources
- `test`: the folder for testing purpose

## Dependency Management
Maven project: All required dependencies are available in pom.xml

## Database: MySQL 
To connect to database: Download and open MySQL Workbench -> open file `assets/db/MySQL_Script_AIMS.sql` -> Run all commands
this script file

## Work with Payment method
- VNPay
- PayPal: To be able to pay via PayPal successfully, you need to run file `src/views/confirm.html` by LiveServer extension
in VSCode to initialize a simple server. 