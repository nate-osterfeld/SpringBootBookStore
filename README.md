# Spring Boot Microservices Book Store

## Introduction

The Book Store Microservices Project allows users to browse available books, manage their shopping cart, and place orders through a set of independently running services. Each service handles a specific responsibility such as catalog management, inventory tracking, user accounts, or order processing and communicates through RESTful endpoints. The system is designed to be scalable, modular, and easy to extend. Users can interact with the application through the exposed APIs, a UI layer, or both.

## Storyboard

[book-store-storyboard.png](https://postimg.cc/R6GqSxRM)

## Requirements

### Story #1

As a customer, I want to browse available books, so that I can discover titles to purchase.

*Given*: A catalog of books is available

*When*: The user/service requests the list of books

*When*: The user/service selects a specific book

*Then*: The system returns the book’s details, including title, author, price, and availability

### Story #2

As a customer, I want to add books to my cart, so that I can purchase multiple items at once.

*Given*: The user is viewing a book’s details

*When*: The user/service selects “Add to Cart”

*When*: The book is in stock per the Inventory Service

*Then*: The selected book is added to the user’s cart

### Story #3

As a customer, I want to place an order, so that I can complete my purchase.

*Given*: The user has items in the cart

*When*: The user/service initiates checkout

*When*: Payment information is submitted

*Then*: An order is created and inventory is reduced accordingly

## Class Diagram

[Book Store UML Diagram](https://lucid.app/lucidchart/153564ff-a12b-4fee-b99a-dd801e26b8de/edit?invitationId=inv_2f9a0f09-bbcd-4599-9458-16ba0876579b&page=0_0#)

## Class Diagram Description

Book – Represents a book entity in the catalog with attributes like title, author, and price. Serves as a resource for other services.

User – Represents a customer or admin interacting with the system. Holds personal information and links to a cart.

Cart – Stores books selected by a user before checkout. Interfaces with Inventory and Order services.

Order – Represents a completed purchase. Tracks items, user, and order status. Communicates with Inventory to update stock.

InventoryItem – Tracks stock levels of each book. Interfaces with Cart and Order services to ensure availability.

## JSON Schema

```json
{
  "type": "object",
  "properties": {
    "id": {
      "type": "integer"
    "title": {
      "type": "string"
    },
    "price": {
      "type": "decimal"
    }
  }
}
```

## Team Members and Roles

Nate Osterfeld (Backend dev)

Ben Wilhelm

Ethan Zins

Miles Cresci

## Standup

[We meet 7pm EST on Sunday](https://teams.microsoft.com/l/meetup-join/19%3ameeting_ZjY3Yjk1NjgtNzViZC00NGVjLWFiN2YtNWIyZjRkNzgwZTEz%40thread.v2/0?context=%7b%22Tid%22%3a%22f5222e6c-5fc6-48eb-8f03-73db18203b63%22%2c%22Oid%22%3a%22d5d13dc2-1235-45a1-90b8-29c8b73449e0%22%7d)
