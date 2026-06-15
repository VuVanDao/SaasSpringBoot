# Tài Liệu API Endpoints (Vietnamese Version)

## Mục Lục
1. [Authentication (Xác Thực)](#authentication)
2. [Tenant Management (Quản Lý Tenant)](#tenant-management)
3. [Category (Danh Mục)](#category)
4. [Product (Sản Phẩm)](#product)
5. [Branch (Chi Nhánh)](#branch)
6. [Inventory (Kho Hàng)](#inventory)
7. [Inventory Item (Chi Tiết Kho)](#inventory-item)

---

## Authentication

### 1. POST /auth/sign_up
**Mục Đích:** Đăng ký tài khoản người dùng mới

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
**Mục Đích:** Đăng nhập vào hệ thống

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
**Mục Đích:** Refresh access token bằng refresh token

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
**Mục Đích:** Tạo tenant (đối tượng kinh doanh) mới

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
**Mục Đích:** Lấy danh sách tất cả các tenant

**Input:** Không có body, không có query parameters

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
**Mục Đích:** Lấy thông tin chi tiết của một tenant

**Input Parameters:**
- `tenantId` (path parameter, required, number): ID của tenant

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
**Mục Đích:** Cập nhật thông tin tenant

**Input Parameters:**
- `id` (path parameter, required, number): ID của tenant

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
**Mục Đích:** Xóa tenant

**Input Parameters:**
- `id` (path parameter, required, number): ID của tenant

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
**Mục Đích:** Tạo danh mục sản phẩm mới

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
**Mục Đích:** Lấy danh sách tất cả danh mục của tenant hiện tại

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
**Mục Đích:** Lấy thông tin chi tiết của một danh mục

**Input Parameters:**
- `id` (path parameter, required, number): ID của danh mục

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
**Mục Đích:** Xóa danh mục

**Input Parameters:**
- `id` (path parameter, required, number): ID của danh mục

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
**Mục Đích:** Tạo sản phẩm mới

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
**Mục Đích:** Lấy danh sách tất cả sản phẩm của store hiện tại

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
**Mục Đích:** Lấy thông tin chi tiết của một sản phẩm

**Input Parameters:**
- `id` (path parameter, required, number): ID của sản phẩm

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
**Mục Đích:** Cập nhật thông tin sản phẩm

**Input Parameters:**
- `id` (path parameter, required, number): ID của sản phẩm

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
**Mục Đích:** Xóa sản phẩm

**Input Parameters:**
- `id` (path parameter, required, number): ID của sản phẩm

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
**Mục Đích:** Lấy tất cả sản phẩm của một store cụ thể

**Input Parameters:**
- `storeId` (path parameter, required, number): ID của store

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
**Mục Đích:** Tìm kiếm sản phẩm theo tên, nhãn hiệu, hoặc danh mục trong một store

**Input Parameters:**
- `storeId` (path parameter, required, number): ID của store
- `query` (query parameter, optional, string): Từ khóa tìm kiếm (tên, brand, category)

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

**Lưu ý:** Nếu query không có hoặc rỗng, sẽ trả về tất cả sản phẩm của store

---

## Branch

### 1. POST /branches
**Mục Đích:** Tạo chi nhánh mới

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
**Mục Đích:** Lấy danh sách tất cả chi nhánh

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
**Mục Đích:** Lấy thông tin chi tiết của một chi nhánh

**Input Parameters:**
- `id` (path parameter, required, number): ID của chi nhánh

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
**Mục Đích:** Cập nhật thông tin chi nhánh

**Input Parameters:**
- `id` (path parameter, required, number): ID của chi nhánh

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
**Mục Đích:** Xóa chi nhánh

**Input Parameters:**
- `id` (path parameter, required, number): ID của chi nhánh

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
**Mục Đích:** Tạo kho hàng mới

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
**Mục Đích:** Lấy danh sách tất cả kho hàng

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
**Mục Đích:** Lấy thông tin chi tiết của một kho hàng

**Input Parameters:**
- `id` (path parameter, required, number): ID của kho hàng

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
**Mục Đích:** Cập nhật thông tin kho hàng

**Input Parameters:**
- `id` (path parameter, required, number): ID của kho hàng

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
**Mục Đích:** Xóa kho hàng

**Input Parameters:**
- `id` (path parameter, required, number): ID của kho hàng

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
**Mục Đích:** Lấy kho hàng chứa sản phẩm cụ thể

**Input Parameters:**
- `productId` (path parameter, required, number): ID của sản phẩm

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
**Mục Đích:** Lấy tất cả kho hàng của chi nhánh cụ thể

**Input Parameters:**
- `branchId` (path parameter, required, number): ID của chi nhánh

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
**Mục Đích:** Thêm nhiều sản phẩm vào kho hàng cùng một lúc

**Input Parameters:**
- `inventoryId` (path parameter, required, number): ID của kho hàng

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
**Mục Đích:** Thêm sản phẩm vào nhiều kho hàng

**Input Parameters:**
- `productId` (path parameter, required, number): ID của sản phẩm

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
**Mục Đích:** Lấy danh sách các sản phẩm trong một kho hàng

**Input Parameters:**
- `inventoryId` (path parameter, required, number): ID của kho hàng

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
**Mục Đích:** Lấy danh sách các kho hàng chứa sản phẩm cụ thể

**Input Parameters:**
- `productId` (path parameter, required, number): ID của sản phẩm

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

## Ghi Chú Quan Trọng

### Authentication
- Tất cả các endpoint (ngoài `/auth/sign_up` và `/auth/login`) yêu cầu JWT token trong header `Authorization: Bearer {token}`
- Token được lấy từ endpoint `/auth/login` hoặc `/auth/sign_up`

### Response Format
- Tất cả API responses được bao gọi trong một `ApiResponse` wrapper:
  - `code`: HTTP status code (mặc định 200)
  - `message`: Thông báo mô tả (mặc định "ok")
  - `result`: Dữ liệu thực tế

### Error Handling
- Nếu xảy ra lỗi, response sẽ có code khác 200
- Kiểm tra `message` để biết chi tiết lỗi

### Multi-Tenant
- Hệ thống hỗ trợ multi-tenant
- Mỗi tenant có thể quản lý riêng stores, branches, products, categories, inventories

### Permissions
- Người dùng với role ADMIN có thể quản lý tất cả resources của tenant
- Người dùng với role STORE_MANAGER có thể quản lý resources của store được assign
- Người dùng với role EMPLOYEE có quyền hạn chế

---

**Tài liệu này được tạo ngày:** 2026-05-27
