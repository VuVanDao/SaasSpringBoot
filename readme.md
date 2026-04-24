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

---
