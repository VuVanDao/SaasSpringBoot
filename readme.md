# VuVanDao / SaasSpringBoot

## 1. Giới Thiệu Dự Án

Chào mừng bạn đến với **SaasSpringBoot** - Hệ thống Quản lý Cửa hàng & Kho hàng Đa chi nhánh (SaaS Multi-Tenant Store & Inventory Management System) được phát triển trên nền tảng **Spring Boot**.

### Các đặc điểm nổi bật của dự án:

- **Hỗ trợ Multi-Tenancy:** Mỗi đối tác doanh nghiệp (Tenant) sở hữu và quản lý một vùng dữ liệu riêng biệt từ cửa hàng, chi nhánh đến kho sản phẩm.
- **Phân quyền người dùng:** Cơ chế bảo mật và phân quyền chi tiết dựa trên JWT (ADMIN, STORE_MANAGER, EMPLOYEE).
- **Quản lý tồn kho linh hoạt:** Cho phép theo dõi tồn kho theo từng chi nhánh, nhập kho hàng loạt và tìm kiếm thông minh.

---

## 2. Hướng Dẫn Nhanh Cho Developer Mới (API Overview)

Để bắt đầu làm việc với hệ thống API, bạn cần nắm rõ luồng vận hành cơ bản sau:

### Luồng Xác Thực (Authentication Flow)

1. **Đăng ký / Đăng nhập:** Gửi request đến `/auth/sign_up` hoặc `/auth/login` để lấy mã **JWT (Access Token)**.
2. **Gửi Request:** Đính kèm mã JWT vào header của mọi request tiếp theo dưới dạng:
   ```http
   Authorization: Bearer {token}
   ```

### Các Nhóm API Chính

- **Xác thực (`/auth`):** Đăng ký, đăng nhập và cấp lại token.
- **Doanh nghiệp (`/tenants`):** Tạo và quản lý thông tin các Tenant trong hệ thống.
- **Sản phẩm & Danh mục (`/products`, `/categories`):** Quản lý danh sách sản phẩm và phân loại sản phẩm.
- **Chi nhánh & Kho (`/branches`, `/inventory`, `/inventory_item`):** Thiết lập chi nhánh, tạo kho hàng và cập nhật số lượng hàng tồn kho.

### Định Dạng Response Chuẩn

Tất cả các API đều trả về một cấu trúc đồng nhất (`ApiResponse`):

```json
{
  "code": 200, // HTTP Status Code
  "message": "ok", // Thông báo phản hồi
  "result": { ... } // Dữ liệu thực tế trả về
}
```

### Tài Liệu Chi Tiết (Full API Documentation)

Nếu bạn muốn tra cứu chi tiết từng endpoint (Input request body, Output response, Query parameters...), vui lòng xem tại các file tài liệu dưới đây:

- 🇺🇸 [Tài Liệu API Tiếng Anh (English Version)](API_ENDPOINTS_DOCUMENTATION_EN.md)
- 🇻🇳 [Tài Liệu API Tiếng Việt (Vietnamese Version)](API_ENDPOINTS_DOCUMENTATION_VN.md)

---

### Kí hiệu (những kí tự trong đáu () )

- D : Deprecated - những tính năng, hàm, hoặc thư viện đã không còn phù hợp và không khuyến khích sử dụng trong phiên bản mới
- EM: endpoint maintenance - các api endpoint đang trong quá trình bảo trì, không khuyến khích sử dụng
- DES: description - miêu tả

# File Tree: demo

**Generated:** 4/24/2026, 5:31:36 PM
**Root Path:** `d:\ForJava\Project\demo`

```
├── 📁 .continue
│   └── 📁 rules
│       ├── 📝 docs.md
│       ├── 📝 mainRule.md
│       ├── 📝 ruleNhatDuocODauDo.md
│       └── 📝 source.md
├── 📁 .mvn
│   └── 📁 wrapper
│       └── 📄 maven-wrapper.properties
├── 📁 src
│   ├── 📁 main
│   │   ├── 📁 java
│   │   │   └── 📁 PersonalProject
│   │   │       └── 📁 demo
│   │   │           ├── 📁 Dto
│   │   │           │   ├── 📁 Request
│   │   │           │   │   ├── ☕ CreateBranchRequest.java
│   │   │           │   │   ├── ☕ CreateCategoryRequest.java
│   │   │           │   │   ├── ☕ CreateCustomerRequest.java
│   │   │           │   │   ├── ☕ CreateEmployeeRequest.java
│   │   │           │   │   ├── ☕ CreateInventoryItemRequest.java
│   │   │           │   │   ├── ☕ CreateInventoryRequest.java
│   │   │           │   │   ├── ☕ CreateProductRequest.java
│   │   │           │   │   ├── ☕ CreateStoreRequest.java
│   │   │           │   │   ├── ☕ CreateTenantRequest.java
│   │   │           │   │   ├── ☕ CreateUserRequest.java
│   │   │           │   │   ├── ☕ LoginRequest.java
│   │   │           │   │   ├── ☕ RefreshTokenRequest.java
│   │   │           │   │   ├── ☕ UpdateBranchRequest.java
│   │   │           │   │   ├── ☕ UpdateCustomerRequest.java
│   │   │           │   │   ├── ☕ UpdateEmployeeRequest.java
│   │   │           │   │   ├── ☕ UpdateInventoryRequest.java
│   │   │           │   │   ├── ☕ UpdateProductRequest.java
│   │   │           │   │   ├── ☕ UpdateProfileRequest.java
│   │   │           │   │   └── ☕ UpdateStoreRequest.java
│   │   │           │   └── 📁 Response
│   │   │           │       ├── ☕ ApiResponse.java
│   │   │           │       ├── ☕ AuthResponse.java
│   │   │           │       ├── ☕ BranchDto.java
│   │   │           │       ├── ☕ CategoryResponse.java
│   │   │           │       ├── ☕ CustomerDto.java
│   │   │           │       ├── ☕ EmployeeDto.java
│   │   │           │       ├── ☕ InventoryDto.java
│   │   │           │       ├── ☕ InventoryItemInventory.java
│   │   │           │       ├── ☕ InventoryItemProduct.java
│   │   │           │       ├── ☕ ProductDto.java
│   │   │           │       ├── ☕ StoreDto.java
│   │   │           │       ├── ☕ TenantDto.java
│   │   │           │       └── ☕ UserDto.java
│   │   │           ├── 📁 Implementation
│   │   │           │   ├── ☕ AuthServiceImplementation.java
│   │   │           │   ├── ☕ BranchServiceImplementation.java
│   │   │           │   ├── ☕ CategoryImplementation.java
│   │   │           │   ├── ☕ CustomUserImplementation.java
│   │   │           │   ├── ☕ CustomerServiceImplementation.java
│   │   │           │   ├── ☕ EmployeeServiceImplementation.java
│   │   │           │   ├── ☕ InventoryItemImplementation.java
│   │   │           │   ├── ☕ InventoryServiceImplementation.java
│   │   │           │   ├── ☕ ProductServiceImplementation.java
│   │   │           │   ├── ☕ StoreServiceImplementation.java
│   │   │           │   ├── ☕ TenantServiceImplementation.java
│   │   │           │   └── ☕ UserServiceImplementation.java
│   │   │           ├── 📁 configuration
│   │   │           │   ├── ☕ ApplicationConfig.java
│   │   │           │   ├── ☕ ApplicationProperties.java
│   │   │           │   ├── ☕ JwtConstant.java
│   │   │           │   ├── ☕ JwtProvider.java
│   │   │           │   ├── ☕ JwtValidator.java
│   │   │           │   └── ☕ SecurityConfig.java
│   │   │           ├── 📁 controllers
│   │   │           │   ├── ☕ AuthController.java
│   │   │           │   ├── ☕ BranchController.java
│   │   │           │   ├── ☕ CategoryController.java
│   │   │           │   ├── ☕ CustomerController.java
│   │   │           │   ├── ☕ EmployeeController.java
│   │   │           │   ├── ☕ InventoryController.java
│   │   │           │   ├── ☕ InventoryItemController.java
│   │   │           │   ├── ☕ ProductController.java
│   │   │           │   ├── ☕ StoreController.java
│   │   │           │   ├── ☕ TenantController.java
│   │   │           │   └── ☕ UserController.java
│   │   │           ├── 📁 domain
│   │   │           │   ├── ☕ EmployeeRole.java
│   │   │           │   ├── ☕ ErrorCode.java
│   │   │           │   ├── ☕ StoreContact.java
│   │   │           │   ├── ☕ StoreStatus.java
│   │   │           │   ├── ☕ TenantStatus.java
│   │   │           │   └── ☕ UserRole.java
│   │   │           ├── 📁 exception
│   │   │           │   ├── ☕ GlobalException.java
│   │   │           │   ├── ☕ InvalidRoleException.java
│   │   │           │   ├── ☕ ResourceAlreadyExistException.java
│   │   │           │   ├── ☕ ResourceNotFoundException.java
│   │   │           │   ├── ☕ StoreNotUnderPermission.java
│   │   │           │   ├── ☕ TenantException.java
│   │   │           │   └── ☕ UserNotUnderPermission.java
│   │   │           ├── 📁 mapper
│   │   │           │   ├── ☕ BranchMapper.java
│   │   │           │   ├── ☕ CustomerMapper.java
│   │   │           │   ├── ☕ EmployeeMapper.java
│   │   │           │   ├── ☕ ProductMapper.java
│   │   │           │   ├── ☕ TenantMapper.java
│   │   │           │   ├── ☕ storeMapper.java
│   │   │           │   └── ☕ userMapper.java
│   │   │           ├── 📁 models
│   │   │           │   ├── ☕ AbstractModel.java
│   │   │           │   ├── ☕ AbstractTenantModel.java
│   │   │           │   ├── ☕ Branch.java
│   │   │           │   ├── ☕ Category.java
│   │   │           │   ├── ☕ Customer.java
│   │   │           │   ├── ☕ Employee.java
│   │   │           │   ├── ☕ Inventory.java
│   │   │           │   ├── ☕ InventoryItem.java
│   │   │           │   ├── ☕ Products.java
│   │   │           │   ├── ☕ RefreshToken.java
│   │   │           │   ├── ☕ Store.java
│   │   │           │   ├── ☕ Tenant.java
│   │   │           │   └── ☕ User.java
│   │   │           ├── 📁 repositories
│   │   │           │   ├── ☕ AuthRepositories.java
│   │   │           │   ├── ☕ BranchRepository.java
│   │   │           │   ├── ☕ CategoryRepositories.java
│   │   │           │   ├── ☕ CustomerRepository.java
│   │   │           │   ├── ☕ EmployeeRepository.java
│   │   │           │   ├── ☕ InventoryItemRepository.java
│   │   │           │   ├── ☕ InventoryRepository.java
│   │   │           │   ├── ☕ ProductRepository.java
│   │   │           │   ├── ☕ RefreshTokenRepository.java
│   │   │           │   ├── ☕ StoreRepositories.java
│   │   │           │   ├── ☕ TenantRepository.java
│   │   │           │   └── ☕ UserRepository.java
│   │   │           ├── 📁 services
│   │   │           │   ├── ☕ AuthService.java
│   │   │           │   ├── ☕ BranchService.java
│   │   │           │   ├── ☕ CategoryService.java
│   │   │           │   ├── ☕ CustomerService.java
│   │   │           │   ├── ☕ EmployeeService.java
│   │   │           │   ├── ☕ InventoryItemService.java
│   │   │           │   ├── ☕ InventoryService.java
│   │   │           │   ├── ☕ ProductService.java
│   │   │           │   ├── ☕ StoreService.java
│   │   │           │   ├── ☕ TenantService.java
│   │   │           │   └── ☕ UserService.java
│   │   │           ├── 📁 utils
│   │   │           │   └── ☕ TenantUtil.java
│   │   │           └── ☕ DemoApplication.java
│   │   └── 📁 resources
│   │       ├── 📁 static
│   │       ├── 📁 templates
│   │       └── 📄 application.properties
│   └── 📁 test
│       └── 📁 java
│           └── 📁 PersonalProject
│               └── 📁 demo
│                   └── ☕ DemoApplicationTests.java
├── ⚙️ .gitattributes
├── ⚙️ .gitignore
├── 📄 mvnw
├── 📄 mvnw.cmd
├── ⚙️ pom.xml
└── 📝 readme.md
```
