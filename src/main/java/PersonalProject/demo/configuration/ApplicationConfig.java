package PersonalProject.demo.configuration;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import PersonalProject.demo.Enums.EmployeeRole;
import PersonalProject.demo.Enums.StoreStatus;
import PersonalProject.demo.Enums.TenantStatus;
import PersonalProject.demo.Enums.UserRole;
import PersonalProject.demo.models.Branch;
import PersonalProject.demo.models.Category;
import PersonalProject.demo.models.Customer;
import PersonalProject.demo.models.Employee;
import PersonalProject.demo.models.Inventory;
import PersonalProject.demo.models.Products;
import PersonalProject.demo.models.Store;
import PersonalProject.demo.models.Tenant;
import PersonalProject.demo.models.User;
import PersonalProject.demo.repositories.BranchRepository;
import PersonalProject.demo.repositories.CategoryRepositories;
import PersonalProject.demo.repositories.CustomerRepository;
import PersonalProject.demo.repositories.EmployeeRepository;
import PersonalProject.demo.repositories.InventoryRepository;
import PersonalProject.demo.repositories.ProductRepository;
import PersonalProject.demo.repositories.StoreRepositories;
import PersonalProject.demo.repositories.TenantRepository;
import PersonalProject.demo.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class ApplicationConfig {

    private final PasswordEncoder passwordEncoder;

    @Bean
    ApplicationRunner applicationRunner(
            TenantRepository tenantRepository,
            UserRepository userRepository,
            StoreRepositories storeRepository,
            BranchRepository branchRepository,
            EmployeeRepository employeeRepository,
            CustomerRepository customerRepository,
            CategoryRepositories categoryRepository,
            ProductRepository productRepository,
            InventoryRepository inventoryRepository) {
        return args -> {
            System.out.println("--------------------- Data Initialization ---------------------");
            
            // Check if database is already initialized
            if (tenantRepository.count() > 0) {
                System.out.println("Database already initialized. Skipping seed data.");
                return;
            }

            // 1. Initialize Tenants (2 Tenants)
            System.out.println("Seeding Tenants...");
            Tenant t1 = Tenant.builder()
                    .name("AdminTenant")
                    .domain("admin.tenant.com")
                    .status(TenantStatus.ACTIVE)
                    .build();
            t1 = tenantRepository.save(t1);

            Tenant t2 = Tenant.builder()
                    .name("FashionTenant")
                    .domain("fashion.tenant.com")
                    .status(TenantStatus.ACTIVE)
                    .build();
            t2 = tenantRepository.save(t2);

            Long tenantId1 = t1.getId();
            Long tenantId2 = t2.getId();

            // 2. Initialize Users & Accounts
            System.out.println("Seeding Users...");
            
            // Global Super Admin (associated with Tenant 1)
            User superAdmin = User.builder()
                    .fullName("Global Super Admin")
                    .email("superadmin@gmail.com")
                    .password(passwordEncoder.encode("admin123"))
                    .role(UserRole.ROLE_SUPER_ADMIN)
                    .phone("0900000001")
                    .tenantId(tenantId1)
                    .build();
            userRepository.save(superAdmin);

            // Tenant 1 Users
            User storeAdmin1 = User.builder()
                    .fullName("T1 Store Manager")
                    .email("storeadmin1@gmail.com")
                    .password(passwordEncoder.encode("password123"))
                    .role(UserRole.ROLE_STORE_MANAGER)
                    .phone("0900000002")
                    .tenantId(tenantId1)
                    .build();
            userRepository.save(storeAdmin1);

            User branchManager1 = User.builder()
                    .fullName("T1 Branch Manager")
                    .email("manager1@gmail.com")
                    .password(passwordEncoder.encode("password123"))
                    .role(UserRole.ROLE_BRANCH_MANAGER)
                    .phone("0900000003")
                    .tenantId(tenantId1)
                    .build();
            userRepository.save(branchManager1);

            User staff1 = User.builder()
                    .fullName("T1 Cashier Staff")
                    .email("cashier1@gmail.com")
                    .password(passwordEncoder.encode("password123"))
                    .role(UserRole.ROLE_CASHIER)
                    .phone("0900000004")
                    .tenantId(tenantId1)
                    .build();
            userRepository.save(staff1);

            // Tenant 2 Users
            User storeAdmin2 = User.builder()
                    .fullName("T2 Store Manager")
                    .email("storeadmin2@gmail.com")
                    .password(passwordEncoder.encode("password123"))
                    .role(UserRole.ROLE_STORE_MANAGER)
                    .phone("0900000005")
                    .tenantId(tenantId2)
                    .build();
            userRepository.save(storeAdmin2);

            User branchManager2 = User.builder()
                    .fullName("T2 Branch Manager")
                    .email("manager2@gmail.com")
                    .password(passwordEncoder.encode("password123"))
                    .role(UserRole.ROLE_BRANCH_MANAGER)
                    .phone("0900000006")
                    .tenantId(tenantId2)
                    .build();
            userRepository.save(branchManager2);

            User staff2 = User.builder()
                    .fullName("T2 Cashier Staff")
                    .email("cashier2@gmail.com")
                    .password(passwordEncoder.encode("password123"))
                    .role(UserRole.ROLE_CASHIER)
                    .phone("0900000007")
                    .tenantId(tenantId2)
                    .build();
            userRepository.save(staff2);

            // 3. Initialize Stores
            System.out.println("Seeding Stores...");
            Store s1 = Store.builder()
                    .brand("Admin SaaS Store")
                    .description("Store for T1 Admin services")
                    .storeStatus(StoreStatus.ACTIVE)
                    .storeAdmin(storeAdmin1)
                    .tenantId(tenantId1)
                    .build();
            s1 = storeRepository.save(s1);

            Store s2 = Store.builder()
                    .brand("Zara Outlet")
                    .description("Zara Premium Fashion Store")
                    .storeStatus(StoreStatus.ACTIVE)
                    .storeAdmin(storeAdmin2)
                    .tenantId(tenantId2)
                    .build();
            s2 = storeRepository.save(s2);

            // 4. Initialize Branches
            System.out.println("Seeding Branches...");
            Branch b1 = Branch.builder()
                    .name("Hanoi Admin HQ")
                    .address("1 Hoan Kiem, Hanoi")
                    .phone("0241234567")
                    .email("hq-hanoi@admin.tenant.com")
                    .workingDay("Mon-Fri")
                    .store(s1)
                    .manager(branchManager1)
                    .tenantId(tenantId1)
                    .build();
            b1 = branchRepository.save(b1);

            Branch b2 = Branch.builder()
                    .name("Hanoi Zara Branch")
                    .address("88 Nguyen Chi Thanh, Hanoi")
                    .phone("0249876543")
                    .email("hanoi@zara.tenant.com")
                    .workingDay("Mon-Sun")
                    .store(s2)
                    .manager(branchManager2)
                    .tenantId(tenantId2)
                    .build();
            b2 = branchRepository.save(b2);

            // 5. Initialize Employees
            System.out.println("Seeding Employees...");
            Employee emp1 = Employee.builder()
                    .user(staff1)
                    .store(s1)
                    .branch(b1)
                    .employeeRole(EmployeeRole.CASHIER)
                    .employeeCode("EMP-T1-001")
                    .salary(BigDecimal.valueOf(8000000))
                    .tenantId(tenantId1)
                    .build();
            employeeRepository.save(emp1);

            Employee emp2 = Employee.builder()
                    .user(staff2)
                    .store(s2)
                    .branch(b2)
                    .employeeRole(EmployeeRole.CASHIER)
                    .employeeCode("EMP-T2-001")
                    .salary(BigDecimal.valueOf(10000000))
                    .tenantId(tenantId2)
                    .build();
            employeeRepository.save(emp2);

            // 6. Initialize Customers (5 Customers per Tenant = 10 Customers total)
            System.out.println("Seeding Customers...");
            for (int i = 1; i <= 5; i++) {
                // Customer for Tenant 1
                User custUserT1 = User.builder()
                        .fullName("Customer T1 " + i)
                        .email("customer" + i + "_t1@gmail.com")
                        .password(passwordEncoder.encode("customer123"))
                        .role(UserRole.ROLE_USER)
                        .phone("091000010" + i)
                        .avatar("avatar_t1_" + i + ".png")
                        .tenantId(tenantId1)
                        .build();
                custUserT1 = userRepository.save(custUserT1);

                Customer custT1 = Customer.builder()
                        .user(custUserT1)
                        .address("Hanoi Street " + i)
                        .tenantId(tenantId1)
                        .build();
                customerRepository.save(custT1);

                // Customer for Tenant 2
                User custUserT2 = User.builder()
                        .fullName("Customer T2 " + i)
                        .email("customer" + i + "_t2@gmail.com")
                        .password(passwordEncoder.encode("customer123"))
                        .role(UserRole.ROLE_USER)
                        .phone("092000020" + i)
                        .avatar("avatar_t2_" + i + ".png")
                        .tenantId(tenantId2)
                        .build();
                custUserT2 = userRepository.save(custUserT2);

                Customer custT2 = Customer.builder()
                        .user(custUserT2)
                        .address("Saigon Street " + i)
                        .tenantId(tenantId2)
                        .build();
                customerRepository.save(custT2);
            }

            // 7. Initialize Categories (5 Categories per Tenant = 10 total)
            System.out.println("Seeding Categories...");
            List<Category> t1Categories = new ArrayList<>();
            List<Category> t2Categories = new ArrayList<>();
            
            String[] t1CategoryNames = {"SaaS Services", "Consulting", "Licensing", "Hosting", "Support"};
            for (String name : t1CategoryNames) {
                Category cat = Category.builder()
                        .name(name)
                        .tenantId(tenantId1)
                        .isSystemDefault(true)
                        .build();
                t1Categories.add(categoryRepository.save(cat));
            }

            String[] t2CategoryNames = {"T-Shirts", "Jeans", "Dresses", "Footwear", "Accessories"};
            for (String name : t2CategoryNames) {
                Category cat = Category.builder()
                        .name(name)
                        .tenantId(tenantId2)
                        .isSystemDefault(true)
                        .build();
                t2Categories.add(categoryRepository.save(cat));
            }

            // Associate categories with stores
            s1.setCategories(new HashSet<>(t1Categories));
            storeRepository.save(s1);

            s2.setCategories(new HashSet<>(t2Categories));
            storeRepository.save(s2);

            // 8. Initialize Products (5 Products per Tenant = 10 total)
            System.out.println("Seeding Products...");
            
            // Products for Tenant 1
            for (int i = 0; i < 5; i++) {
                Category cat = t1Categories.get(i);
                Set<Category> cats = new HashSet<>();
                cats.add(cat);
                
                Products prod = Products.builder()
                        .name(cat.getName() + " Package")
                        .sku("SKU-T1-" + (100 + i))
                        .description("High-quality administrative service of type " + cat.getName())
                        .mrp(BigDecimal.valueOf(1500000 + i * 500000))
                        .sellingPrice(BigDecimal.valueOf(1400000 + i * 500000))
                        .brand("AdminSaaS")
                        .categories(cats)
                        .tenantId(tenantId1)
                        .build();
                productRepository.save(prod);
            }

            // Products for Tenant 2
            for (int i = 0; i < 5; i++) {
                Category cat = t2Categories.get(i);
                Set<Category> cats = new HashSet<>();
                cats.add(cat);
                
                Products prod = Products.builder()
                        .name("Zara Fashion " + cat.getName())
                        .sku("SKU-T2-" + (200 + i))
                        .description("Premium fashion item from " + cat.getName() + " collection")
                        .mrp(BigDecimal.valueOf(500000 + i * 150000))
                        .sellingPrice(BigDecimal.valueOf(450000 + i * 150000))
                        .brand("Zara")
                        .categories(cats)
                        .tenantId(tenantId2)
                        .build();
                productRepository.save(prod);
            }

            // 9. Initialize Inventories
            System.out.println("Seeding Inventories...");
            Inventory inv1 = Inventory.builder()
                    .branch(b1)
                    .inventoryName("Central Admin Inventory")
                    .tenantId(tenantId1)
                    .build();
            inventoryRepository.save(inv1);

            Inventory inv2 = Inventory.builder()
                    .branch(b2)
                    .inventoryName("Zara Hanoi Main Stock")
                    .tenantId(tenantId2)
                    .build();
            inventoryRepository.save(inv2);

            System.out.println("--------------------- Seeding Completed Successfully ---------------------");
        };
    }
}
