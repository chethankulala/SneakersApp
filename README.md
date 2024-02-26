# SneakerShip App Specifications

## Overview
SneakerShip is a mobile application designed to showcase and manage a collection of top 100 sneakers. The app features a display page, sneaker details page, and a checkout page for managing selected sneakers.

## Features

### Display Page
#### Grid View: Display a grid with a column size of 2.
#### Top 100 Sneakers: Show the top 100 sneakers in the grid.
#### Item Information: Each item in the grid should contain the sneaker's image, price, and name.

### Sneaker Details Page
#### Title: Display the title of the sneaker.
#### Image: Display a larger image of the sneaker.
#### Price: Show the price of the selected sneaker.
#### Add to Cart: Include an "Add to Cart" button to add the sneaker to the checkout cart page.

### Checkout Page
#### List of Sneakers: Display a list of all sneakers added to the cart, including their image and price.
#### Removal Feature: Allow users to remove items from the cart.
#### Total Price: Show the total price of all sneakers in the cart.

## Data Format (Hardcoded)
### Sneaker Data:
[
  {
    "id": 1,
    "name": "Sneaker 1",
    "image": "sneaker1.jpg",
    "brand": "Brand A",
    "year": 2022,
    "price": 150
  },
  {
    "id": 2,
    "name": "Sneaker 2",
    "image": "sneaker2.jpg",
    "brand": "Brand B",
    "year": 2021,
    "price": 120
  },
  // ... (up to 100 sneakers)
]

## Technologies Used
Kotlin
MVVM
Room
Glide 

## Demo Video
![untitled](https://github.com/chethankulala/SneakersApp/assets/51992258/6477206f-dcd2-4119-a1a9-968c24e9379c)
