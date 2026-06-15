# API Endpoints Documentation (English Version)

## Table of Contents
1. [Authentication](#authentication)
2. [Tenant Management](#tenant-management)
3. [Category](#category)
4. [Product](#product)
5. [Branch](#branch)
6. [Inventory](#inventory)
7. [Inventory Item](#inventory-item)

---

## Authentication

### 1. POST /auth/sign_up
**Purpose:** Register a new user account

**Input Request Body:**
```json
{
  "email": "string (required, valid email format)",
  "password": "string (required)",
  "firstName": "string (required)",
  "lastName": "string (required)",
  "phone": "string (optional)",
  "role": "string (required, e.g., ADMIN, STORE_MANAGER, EMPLOYEE)"
}
```

**Output Response:**
```json
{
  "code": 200,
  "message": "ok",
  "result": {
    "jwt": "string (access token)",
    "refreshToken": "string",
    "message": "string",
    "userInfo": {
      "id": "number",
      "email": "string",
      "firstName": "string",
      "lastName": "string",
      "phone": "string",
      "role": "string",
      "createdAt": "timestamp"
    }
  }
}
```

---

### 2. POST /auth/login
**Purpose:** Log in to the system

**Input Request Body:**
```json
{
  "email": "string (required, valid email format)",
  "password": "string (required)"
}
```

**Output Response:**
```json
{
  "code": 200,
  "message": "ok",
  "result": {
    "jwt": "string (access token)",
    "refreshToken": "string",
    "message": "string",
    "userInfo": {
      "id": "number",
      "email": "string",
      "firstName": "string",
      "lastName": "string",
      "phone": "string",
      "role": "string",
      "createdAt": "timestamp"
    }
  }
}
```

---

### 3. POST /auth/refresh
**Purpose:** Refresh access token using a refresh token

**Input Request Body:**
```json
{
  "token": "string (required, refresh token)"
}
```

**Output Response:**
```json
{
  "jwt": "string (new access token)",
  "refreshToken": "string (new refresh token)",
  "message": "string",
  "userInfo": {
    "id": "number",
    "email": "string",
    "firstName": "string",
    "lastName": "string",
    "phone": "string",
    "role": "string",
    "createdAt": "timestamp"
  }
}
```

---

## Tenant Management

### 1. POST /tenants
**Purpose:** Create a new tenant (business entity)

**Input Request Body:**
```json
{
  "name": "string (required)",
  "description": "string (optional)",
  "email": "string (required, valid email format)",
  "phone": "string (optional)",
  "address": "string (optional)",
  "status": "string (optional, ACTIVE/INACTIVE)"
}
```

**Output Response:**
```json
{
  "code": 200,
  "message": "ok",
  "result": {
    "id": "number",
    "name": "string",
    "description": "string",
    "email": "string",
    "phone": "string",
    "address": "string",
    "status": "string",
    "createdAt": "timestamp",
    "updatedAt": "timestamp"
  }
}
```

---

### 2. GET /tenants
**Purpose:** Retrieve a list of all tenants

**Input:** No body, no query parameters

**Output Response:**
```json
{
  "code": 200,
  "message": "ok",
  "result": [
    {
      "id": "number",
      "name": "string",
      "description": "string",
      "email": "string",
      "phone": "string",
      "address": "string",
      "status": "string",
      "createdAt": "timestamp",
      "updatedAt": "timestamp"
    }
  ]
}
```

---

### 3. GET /tenants/{tenantId}
**Purpose:** Retrieve detailed information of a tenant

**Input Parameters:**
- `tenantId` (path parameter, required, number): ID of the tenant

**Output Response:**
```json
{
  "code": 200,
  "message": "ok",
  "result": {
    "id": "number",
    "name": "string",
    "description": "string",
    "email": "string",
    "phone": "string",
    "address": "string",
    "status": "string",
    "createdAt": "timestamp",
    "updatedAt": "timestamp"
  }
}
```

---

### 4. PUT /tenants/{id}
**Purpose:** Update tenant information

**Input Parameters:**
- `id` (path parameter, required, number): ID of the tenant

**Input Request Body:**
```json
{
  "name": "string (optional)",
  "description": "string (optional)",
  "email": "string (optional)",
  "phone": "string (optional)",
  "address": "string (optional)",
  "status": "string (optional)"
}
```

**Output Response:**
```json
{
  "code": 200,
  "message": "ok",
  "result": {
    "id": "number",
    "name": "string",
    "description": "string",
    "email": "string",
    "phone": "string",
    "address": "string",
    "status": "string",
    "createdAt": "timestamp",
    "updatedAt": "timestamp"
  }
}
```

---

### 5. DELETE /tenants/{id}
**Purpose:** Delete a tenant

**Input Parameters:**
- `id` (path parameter, required, number): ID of the tenant

**Output Response:**
```json
{
  "code": 200,
  "message": "ok",
  "result": "delete complete"
}
```

---

## Category

### 1. POST /categories
**Purpose:** Create a new product category

**Input Request Body:**
```json
{
  "name": "string (required)",
  "description": "string (optional)",
  "status": "string (optional, ACTIVE/INACTIVE)"
}
```

**Input Headers:**
- `Authorization`: JWT token (required)

**Output Response:**
```json
{
  "code": 200,
  "message": "Category created successfully",
  "result": {
    "id": "number",
    "name": "string",
    "description": "string",
    "status": "string",
    "tenantId": "number",
    "createdAt": "timestamp",
    "updatedAt": "timestamp"
  }
}
```

---

### 2. GET /categories
**Purpose:** Retrieve a list of all categories for the current tenant

**Input Headers:**
- `Authorization`: JWT token (required)

**Output Response:**
```json
{
  "code": 200,
  "message": "Categories retrieved successfully",
  "result": [
    {
      "id": "number",
      "name": "string",
      "description": "string",
      "status": "string",
      "tenantId": "number",
      "createdAt": "timestamp",
      "updatedAt": "timestamp"
    }
  ]
}
```

---

### 3. GET /categories/{id}
**Purpose:** Retrieve detailed information of a category

**Input Parameters:**
- `id` (path parameter, required, number): ID of the category

**Input Headers:**
- `Authorization`: JWT token (required)

**Output Response:**
```json
{
  "code": 200,
  "message": "Category retrieved successfully",
  "result": {
    "id": "number",
    "name": "string",
    "description": "string",
    "status": "string",
    "tenantId": "number",
    "createdAt": "timestamp",
    "updatedAt": "timestamp"
  }
}
```

---

### 4. DELETE /categories/{id}
**Purpose:** Delete a category

**Input Parameters:**
- `id` (path parameter, required, number): ID of the category

**Input Headers:**
- `Authorization`: JWT token (required)

**Output Response:**
```json
{
  "code": 200,
  "message": "Category deleted successfully",
  "result": null
}
```

---

## Product

### 1. POST /products
**Purpose:** Create a new product

**Input Request Body:**
```json
{
  "name": "string (required)",
  "description": "string (optional)",
  "sku": "string (required, unique)",
  "brand": "string (optional)",
  "price": "number (required)",
  "cost": "number (optional)",
  "quantity": "number (optional)",
  "categoryId": "number (required)",
  "status": "string (optional, ACTIVE/INACTIVE)"
}
```

**Input Headers:**
- `Authorization`: JWT token (required)

**Output Response:**
```json
{
  "code": 200,
  "message": "Product created successfully",
  "result": {
    "id": "number",
    "name": "string",
    "description": "string",
    "sku": "string",
    "brand": "string",
    "price": "number",
    "cost": "number",
    "categoryId": "number",
    "storeId": "number",
    "status": "string",
    "createdAt": "timestamp",
    "updatedAt": "timestamp"
  }
}
```

---

### 2. GET /products
**Purpose:** Retrieve a list of all products for the current store

**Input Headers:**
- `Authorization`: JWT token (required)

**Output Response:**
```json
{
  "code": 200,
  "message": "Products retrieved successfully",
  "result": [
    {
      "id": "number",
      "name": "string",
      "description": "string",
      "sku": "string",
      "brand": "string",
      "price": "number",
      "cost": "number",
      "categoryId": "number",
      "storeId": "number",
      "status": "string",
      "createdAt": "timestamp",
      "updatedAt": "timestamp"
    }
  ]
}
```

---

### 3. GET /products/{id}
**Purpose:** Retrieve detailed information of a product

**Input Parameters:**
- `id` (path parameter, required, number): ID of the product

**Input Headers:**
- `Authorization`: JWT token (required)

**Output Response:**
```json
{
  "code": 200,
  "message": "Product retrieved successfully",
  "result": {
    "id": "number",
    "name": "string",
    "description": "string",
    "sku": "string",
    "brand": "string",
    "price": "number",
    "cost": "number",
    "categoryId": "number",
    "storeId": "number",
    "status": "string",
    "createdAt": "timestamp",
    "updatedAt": "timestamp"
  }
}
```

---

### 4. PUT /products/{id}
**Purpose:** Update product information

**Input Parameters:**
- `id` (path parameter, required, number): ID of the product

**Input Request Body:**
```json
{
  "name": "string (optional)",
  "description": "string (optional)",
  "sku": "string (optional)",
  "brand": "string (optional)",
  "price": "number (optional)",
  "cost": "number (optional)",
  "categoryId": "number (optional)",
  "status": "string (optional)"
}
```

**Input Headers:**
- `Authorization`: JWT token (required)

**Output Response:**
```json
{
  "code": 200,
  "message": "Product updated successfully",
  "result": {
    "id": "number",
    "name": "string",
    "description": "string",
    "sku": "string",
    "brand": "string",
    "price": "number",
    "cost": "number",
    "categoryId": "number",
    "storeId": "number",
    "status": "string",
    "createdAt": "timestamp",
    "updatedAt": "timestamp"
  }
}
```

---

### 5. DELETE /products/{id}
**Purpose:** Delete a product

**Input Parameters:**
- `id` (path parameter, required, number): ID of the product

**Input Headers:**
- `Authorization`: JWT token (required)

**Output Response:**
```json
{
  "code": 200,
  "message": "Product deleted successfully",
  "result": null
}
```

---

### 6. GET /products/store/{storeId}
**Purpose:** Retrieve all products of a specific store

**Input Parameters:**
- `storeId` (path parameter, required, number): ID of the store

**Input Headers:**
- `Authorization`: JWT token (required)

**Output Response:**
```json
{
  "code": 200,
  "message": "Get all product by storeID complete",
  "result": [
    {
      "id": "number",
      "name": "string",
      "description": "string",
      "sku": "string",
      "brand": "string",
      "price": "number",
      "cost": "number",
      "categoryId": "number",
      "storeId": "number",
      "status": "string",
      "createdAt": "timestamp",
      "updatedAt": "timestamp"
    }
  ]
}
```

---

### 7. GET /products/store/{storeId}/search
**Purpose:** Search for products by name, brand, or category within a store

**Input Parameters:**
- `storeId` (path parameter, required, number): ID of the store
- `query` (query parameter, optional, string): Search keyword (name, brand, category)

**Input Headers:**
- `Authorization`: JWT token (required)

**Output Response:**
```json
{
  "code": 200,
  "message": "Products retrieved successfully",
  "result": [
    {
      "id": "number",
      "name": "string",
      "description": "string",
      "sku": "string",
      "brand": "string",
      "price": "number",
      "cost": "number",
      "categoryId": "number",
      "storeId": "number",
      "status": "string",
      "createdAt": "timestamp",
      "updatedAt": "timestamp"
    }
  ]
}
```

**Note:** If the query parameter is missing or empty, all products belonging to the store will be returned.

---

## Branch

### 1. POST /branches
**Purpose:** Create a new branch

**Input Request Body:**
```json
{
  "name": "string (required)",
  "address": "string (required)",
  "phone": "string (optional)",
  "email": "string (optional)",
  "manager": "string (optional)",
  "status": "string (optional, ACTIVE/INACTIVE)"
}
```

**Input Headers:**
- `Authorization`: JWT token (required)

**Output Response:**
```json
{
  "code": 201,
  "message": "Branch created successfully",
  "result": {
    "id": "number",
    "name": "string",
    "address": "string",
    "phone": "string",
    "email": "string",
    "manager": "string",
    "status": "string",
    "tenantId": "number",
    "createdAt": "timestamp",
    "updatedAt": "timestamp"
  }
}
```

---

### 2. GET /branches
**Purpose:** Retrieve a list of all branches

**Input Headers:**
- `Authorization`: JWT token (required)

**Output Response:**
```json
{
  "code": 200,
  "message": "Branches retrieved successfully",
  "result": [
    {
      "id": "number",
      "name": "string",
      "address": "string",
      "phone": "string",
      "email": "string",
      "manager": "string",
      "status": "string",
      "tenantId": "number",
      "createdAt": "timestamp",
      "updatedAt": "timestamp"
    }
  ]
}
```

---

### 3. GET /branches/{id}
**Purpose:** Retrieve detailed information of a branch

**Input Parameters:**
- `id` (path parameter, required, number): ID of the branch

**Input Headers:**
- `Authorization`: JWT token (required)

**Output Response:**
```json
{
  "code": 200,
  "message": "Branch retrieved successfully",
  "result": {
    "id": "number",
    "name": "string",
    "address": "string",
    "phone": "string",
    "email": "string",
    "manager": "string",
    "status": "string",
    "tenantId": "number",
    "createdAt": "timestamp",
    "updatedAt": "timestamp"
  }
}
```

---

### 4. PUT /branches/{id}
**Purpose:** Update branch information

**Input Parameters:**
- `id` (path parameter, required, number): ID of the branch

**Input Request Body:**
```json
{
  "name": "string (optional)",
  "address": "string (optional)",
  "phone": "string (optional)",
  "email": "string (optional)",
  "manager": "string (optional)",
  "status": "string (optional)"
}
```

**Input Headers:**
- `Authorization`: JWT token (required)

**Output Response:**
```json
{
  "code": 200,
  "message": "Branch updated successfully",
  "result": {
    "id": "number",
    "name": "string",
    "address": "string",
    "phone": "string",
    "email": "string",
    "manager": "string",
    "status": "string",
    "tenantId": "number",
    "createdAt": "timestamp",
    "updatedAt": "timestamp"
  }
}
```

---

### 5. DELETE /branches/{id}
**Purpose:** Delete a branch

**Input Parameters:**
- `id` (path parameter, required, number): ID of the branch

**Input Headers:**
- `Authorization`: JWT token (required)

**Output Response:**
```json
{
  "code": 200,
  "message": "Branch deleted successfully",
  "result": "Branch deleted successfully"
}
```

---

## Inventory

### 1. POST /inventory
**Purpose:** Create a new inventory

**Input Request Body:**
```json
{
  "name": "string (required)",
  "location": "string (optional)",
  "branchId": "number (required)",
  "description": "string (optional)",
  "status": "string (optional, ACTIVE/INACTIVE)"
}
```

**Input Headers:**
- `Authorization`: JWT token (required)

**Output Response:**
```json
{
  "code": 201,
  "message": "Inventory created successfully",
  "result": {
    "id": "number",
    "name": "string",
    "location": "string",
    "branchId": "number",
    "description": "string",
    "status": "string",
    "createdAt": "timestamp",
    "updatedAt": "timestamp"
  }
}
```

---

### 2. GET /inventory
**Purpose:** Retrieve a list of all inventories

**Output Response:**
```json
{
  "code": 200,
  "message": "All inventories retrieved successfully",
  "result": [
    {
      "id": "number",
      "name": "string",
      "location": "string",
      "branchId": "number",
      "description": "string",
      "status": "string",
      "createdAt": "timestamp",
      "updatedAt": "timestamp"
    }
  ]
}
```

---

### 3. GET /inventory/{id}
**Purpose:** Retrieve detailed information of an inventory

**Input Parameters:**
- `id` (path parameter, required, number): ID of the inventory

**Input Headers:**
- `Authorization`: JWT token (required)

**Output Response:**
```json
{
  "code": 200,
  "message": "Inventory retrieved successfully",
  "result": {
    "id": "number",
    "name": "string",
    "location": "string",
    "branchId": "number",
    "description": "string",
    "status": "string",
    "createdAt": "timestamp",
    "updatedAt": "timestamp"
  }
}
```

---

### 4. PUT /inventory/{id}
**Purpose:** Update inventory information

**Input Parameters:**
- `id` (path parameter, required, number): ID of the inventory

**Input Request Body:**
```json
{
  "name": "string (optional)",
  "location": "string (optional)",
  "description": "string (optional)",
  "status": "string (optional)"
}
```

**Input Headers:**
- `Authorization`: JWT token (required)

**Output Response:**
```json
{
  "code": 200,
  "message": "Inventory updated successfully",
  "result": {
    "id": "number",
    "name": "string",
    "location": "string",
    "branchId": "number",
    "description": "string",
    "status": "string",
    "createdAt": "timestamp",
    "updatedAt": "timestamp"
  }
}
```

---

### 5. DELETE /inventory/{id}
**Purpose:** Delete an inventory

**Input Parameters:**
- `id` (path parameter, required, number): ID of the inventory

**Input Headers:**
- `Authorization`: JWT token (required)

**Output Response:**
```json
{
  "code": 200,
  "message": "Inventory deleted successfully",
  "result": null
}
```

---

### 6. GET /inventory/products/{productId}
**Purpose:** Retrieve the inventory containing a specific product

**Input Parameters:**
- `productId` (path parameter, required, number): ID of the product

**Output Response:**
```json
{
  "code": 200,
  "message": "Inventory retrieved successfully",
  "result": {
    "id": "number",
    "name": "string",
    "location": "string",
    "branchId": "number",
    "description": "string",
    "status": "string",
    "createdAt": "timestamp",
    "updatedAt": "timestamp"
  }
}
```

---

### 7. GET /inventory/branches/{branchId}
**Purpose:** Retrieve all inventories of a specific branch

**Input Parameters:**
- `branchId` (path parameter, required, number): ID of the branch

**Input Headers:**
- `Authorization`: JWT token (required)

**Output Response:**
```json
{
  "code": 200,
  "message": "Inventories retrieved successfully",
  "result": [
    {
      "id": "number",
      "name": "string",
      "location": "string",
      "branchId": "number",
      "description": "string",
      "status": "string",
      "createdAt": "timestamp",
      "updatedAt": "timestamp"
    }
  ]
}
```

---

## Inventory Item

### 1. POST /inventory_item/batch/{inventoryId}
**Purpose:** Add multiple products to an inventory at once (batch insert)

**Input Parameters:**
- `inventoryId` (path parameter, required, number): ID of the inventory

**Input Request Body:**
```json
{
  "items": [
    {
      "productId": "number (required)",
      "quantity": "number (required)",
      "unitPrice": "number (optional)",
      "notes": "string (optional)"
    }
  ]
}
```

**Input Headers:**
- `Authorization`: JWT token (required)

**Output Response:**
```json
{
  "code": 201,
  "message": "Add multi product to inventory successfully",
  "result": null
}
```

---

### 2. POST /inventory_item/product-batch/{productId}
**Purpose:** Add a product to multiple inventories

**Input Parameters:**
- `productId` (path parameter, required, number): ID of the product

**Input Request Body:**
```json
{
  "items": [
    {
      "inventoryId": "number (required)",
      "quantity": "number (required)",
      "unitPrice": "number (optional)",
      "notes": "string (optional)"
    }
  ]
}
```

**Input Headers:**
- `Authorization`: JWT token (required)

**Output Response:**
```
HTTP Status 200 OK
```

---

### 3. GET /inventory_item/get_products_from_inventory/{inventoryId}
**Purpose:** Retrieve a list of products within an inventory

**Input Parameters:**
- `inventoryId` (path parameter, required, number): ID of the inventory

**Input Headers:**
- `Authorization`: JWT token (required)

**Output Response:**
```json
{
  "code": 200,
  "message": "Products in inventory retrieved successfully",
  "result": [
    {
      "id": "number",
      "productId": "number",
      "name": "string",
      "sku": "string",
      "quantity": "number",
      "unitPrice": "number",
      "notes": "string",
      "createdAt": "timestamp",
      "updatedAt": "timestamp"
    }
  ]
}
```

---

### 4. GET /inventory_item/get_inventories_from_product/{productId}
**Purpose:** Retrieve a list of inventories containing a specific product

**Input Parameters:**
- `productId` (path parameter, required, number): ID of the product

**Input Headers:**
- `Authorization`: JWT token (required)

**Output Response:**
```json
{
  "code": 200,
  "message": "Inventories retrieved by product_id successfully",
  "result": [
    {
      "id": "number",
      "inventoryId": "number",
      "name": "string",
      "location": "string",
      "quantity": "number",
      "unitPrice": "number",
      "branchId": "number",
      "createdAt": "timestamp",
      "updatedAt": "timestamp"
    }
  ]
}
```

---

## Important Notes

### Authentication
- All endpoints (except `/auth/sign_up` and `/auth/login`) require a JWT token in the `Authorization: Bearer {token}` header.
- The token is retrieved from the `/auth/login` or `/auth/sign_up` endpoints.

### Response Format
- All API responses are wrapped in an `ApiResponse` wrapper:
  - `code`: HTTP status code (default: 200)
  - `message`: Description message (default: "ok")
  - `result`: The actual returned data

### Error Handling
- If an error occurs, the response code will be different from 200.
- Check the `message` field for error details.

### Multi-Tenant
- The system supports multi-tenancy.
- Each tenant can independently manage its own stores, branches, products, categories, and inventories.

### Permissions
- Users with the ADMIN role can manage all resources of their tenant.
- Users with the STORE_MANAGER role can manage resources of their assigned store.
- Users with the EMPLOYEE role have restricted permissions.

---

**This document was created on:** 2026-05-27
