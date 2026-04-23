# Local Artisan Marketplace API (Postman Collection)

This repository contains a Postman collection for a Local Artisan Marketplace API.

It is designed to simulate and document a full backend for a handmade products marketplace, including users, products, orders, ratings, comments, and images.

You can find the full API collection here:  
- [Postman Collection](https://damyangeorgiev-3576030.postman.co/workspace/Damyan-Georgiev's-Workspace~e0f72289-4bf5-43fa-8ee7-8f47f2793959/collection/44594701-db361edf-8cb7-4d4f-9a41-5438f6a5eaf8?action=share&source=copy-link&creator=44594701)

---

## Project Overview

The API simulates a marketplace where:

- Artisans can create and manage products  
- Users can register, login, and manage their profile  
- Customers can browse, order, rate, and comment on products  
- Images and media can be attached to products  

---

## Base URL
{{base_url}} = https://example.com/api

Replace `base_url` with your real backend URL when implementing the API.

---

## Authentication

The API uses JWT Bearer Token authentication.

After login:

```json
{
  "token": "jwt_token",
  "user": {
    "id": 1,
    "username": "vanka4",
    "email": "ivan.ivanov@gmail.com"
  }
}
```

## Collection Structure



### Authentication

- POST `/auth/register` → Register user  

- POST `/auth/login` → Login user  



---



### Users

- GET `/users` → Get all users  

- GET `/users/me` → Get current user (protected)  

- PATCH `/users/me` → Update current user (protected)  

- DELETE `/users/me` → Delete current user (protected)  



---



### Products

- GET `/products` → Get all products  

- GET `/products/{id}` → Get product by ID  

- POST `/products` → Create product (protected)  

- PUT `/products/{id}` → Update product (protected)  

- DELETE `/products/{id}` → Delete product (protected)  

- GET `/users/{id}/products` → Get products by user  



---



### Orders

- POST `/orders` → Create order (protected)  

- GET `/orders/{id}` → Get order by ID  

- GET `/users/{id}/orders` → Get user orders  



---



### Ratings

- POST `/products/{id}/ratings` → Rate product  

- GET `/products/{id}/ratings` → Get product ratings  

- GET `/products/{id}/rating-average` → Get average rating  



---



### Comments

- GET `/products/{id}/comments` → Get product comments  

- POST `/products/{id}/comments` → Add comment  

- DELETE `/comments/{id}` → Delete comment  



---



### Images

- POST `/products/{id}/images` → Add image (protected)  

- DELETE `/images/{id}` → Delete image (protected)  