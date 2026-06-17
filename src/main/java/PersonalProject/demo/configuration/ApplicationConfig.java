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
            System.out.println("--------------------- Bắt Đầu Khởi Tạo Dữ Liệu ---------------------");
            
            // Kiểm tra xem cơ sở dữ liệu đã được khởi tạo chưa
            if (tenantRepository.count() > 0) {
                System.out.println("Cơ sở dữ liệu đã có dữ liệu mẫu. Bỏ qua bước khởi tạo.");
                return;
            }

            // 1. Khởi tạo Tenants (3 Tenants)
            System.out.println("Đang tạo các Tenant...");
            Tenant t1 = Tenant.builder()
                    .name("Tenant Quản Trị Hệ Thống")
                    .domain("system.saas.com")
                    .status(TenantStatus.ACTIVE)
                    .build();
            t1 = tenantRepository.save(t1);

            Tenant t2 = Tenant.builder()
                    .name("Tenant Thời Trang")
                    .domain("fashion.tenant.com")
                    .status(TenantStatus.ACTIVE)
                    .build();
            t2 = tenantRepository.save(t2);

            Tenant t3 = Tenant.builder()
                    .name("Tenant Công Nghệ")
                    .domain("tech.tenant.com")
                    .status(TenantStatus.ACTIVE)
                    .build();
            t3 = tenantRepository.save(t3);

            Long tenantId1 = t1.getId();
            Long tenantId2 = t2.getId();
            Long tenantId3 = t3.getId();

            // 2. Khởi tạo Người Dùng (Users)
            System.out.println("Đang tạo người dùng mẫu...");
            
            // Tài khoản thuộc Tenant 1 (Tenant Quản Trị Hệ Thống)
            User superAdmin = User.builder()
                    .fullName("Quản Trị Viên Tối Cao")
                    .email("superadmin@gmail.com")
                    .password(passwordEncoder.encode("admin123"))
                    .role(UserRole.ROLE_SUPER_ADMIN)
                    .phone("0900000001")
                    .tenantId(tenantId1)
                    .build();
            userRepository.save(superAdmin);

            User systemAdmin = User.builder()
                    .fullName("Quản Trị Viên Hệ Thống")
                    .email("admin@gmail.com")
                    .password(passwordEncoder.encode("admin123"))
                    .role(UserRole.ROLE_ADMIN)
                    .phone("0900000002")
                    .tenantId(tenantId1)
                    .build();
            userRepository.save(systemAdmin);

            // Tài khoản thuộc Tenant 2 (Thời Trang)
            User storeAdmin2 = User.builder()
                    .fullName("Quản Lý Cửa Hàng Thời Trang")
                    .email("storeadmin2@gmail.com")
                    .password(passwordEncoder.encode("password123"))
                    .role(UserRole.ROLE_STORE_MANAGER)
                    .phone("0900000003")
                    .tenantId(tenantId2)
                    .build();
            userRepository.save(storeAdmin2);

            User branchManager2 = User.builder()
                    .fullName("Quản Lý Chi Nhánh Thời Trang")
                    .email("manager2@gmail.com")
                    .password(passwordEncoder.encode("password123"))
                    .role(UserRole.ROLE_BRANCH_MANAGER)
                    .phone("0900000004")
                    .tenantId(tenantId2)
                    .build();
            userRepository.save(branchManager2);

            User staff2 = User.builder()
                    .fullName("Thu Ngân Thời Trang")
                    .email("cashier2@gmail.com")
                    .password(passwordEncoder.encode("password123"))
                    .role(UserRole.ROLE_CASHIER)
                    .phone("0900000005")
                    .tenantId(tenantId2)
                    .build();
            userRepository.save(staff2);

            // Tài khoản thuộc Tenant 3 (Công Nghệ)
            User storeAdmin3 = User.builder()
                    .fullName("Quản Lý Cửa Hàng Công Nghệ")
                    .email("storeadmin3@gmail.com")
                    .password(passwordEncoder.encode("password123"))
                    .role(UserRole.ROLE_STORE_MANAGER)
                    .phone("0900000006")
                    .tenantId(tenantId3)
                    .build();
            userRepository.save(storeAdmin3);

            User branchManager3 = User.builder()
                    .fullName("Quản Lý Chi Nhánh Công Nghệ")
                    .email("manager3@gmail.com")
                    .password(passwordEncoder.encode("password123"))
                    .role(UserRole.ROLE_BRANCH_MANAGER)
                    .phone("0900000007")
                    .tenantId(tenantId3)
                    .build();
            userRepository.save(branchManager3);

            User staff3 = User.builder()
                    .fullName("Thu Ngân Công Nghệ")
                    .email("cashier3@gmail.com")
                    .password(passwordEncoder.encode("password123"))
                    .role(UserRole.ROLE_CASHIER)
                    .phone("0900000008")
                    .tenantId(tenantId3)
                    .build();
            userRepository.save(staff3);

            // 3. Khởi tạo Cửa Hàng (Stores)
            System.out.println("Đang tạo các Cửa hàng...");
            Store s2 = Store.builder()
                    .brand("Zara Outlet Việt Nam")
                    .description("Cửa hàng thời trang cao cấp Zara chính hãng")
                    .storeStatus(StoreStatus.ACTIVE)
                    .storeAdmin(storeAdmin2)
                    .tenantId(tenantId2)
                    .build();
            s2 = storeRepository.save(s2);

            Store s3 = Store.builder()
                    .brand("Thế Giới Công Nghệ")
                    .description("Cửa hàng bán lẻ thiết bị công nghệ chính hãng")
                    .storeStatus(StoreStatus.ACTIVE)
                    .storeAdmin(storeAdmin3)
                    .tenantId(tenantId3)
                    .build();
            s3 = storeRepository.save(s3);

            // 4. Khởi tạo Chi Nhánh (Branches)
            System.out.println("Đang tạo các Chi nhánh...");
            Branch b2 = Branch.builder()
                    .name("Chi Nhánh Zara Hà Nội")
                    .address("88 Nguyễn Chí Thanh, Hà Nội")
                    .phone("0249876543")
                    .email("hanoi@zara.tenant.com")
                    .workingDay("Thứ 2 - Chủ Nhật")
                    .store(s2)
                    .manager(branchManager2)
                    .tenantId(tenantId2)
                    .build();
            b2 = branchRepository.save(b2);

            Branch b3 = Branch.builder()
                    .name("Chi Nhánh Công Nghệ Cầu Giấy")
                    .address("123 Cầu Giấy, Hà Nội")
                    .phone("0241234567")
                    .email("caugiay@tech.tenant.com")
                    .workingDay("Thứ 2 - Chủ Nhật")
                    .store(s3)
                    .manager(branchManager3)
                    .tenantId(tenantId3)
                    .build();
            b3 = branchRepository.save(b3);

            // 5. Khởi tạo Nhân Viên (Employees)
            System.out.println("Đang tạo Nhân viên...");
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

            Employee emp3 = Employee.builder()
                    .user(staff3)
                    .store(s3)
                    .branch(b3)
                    .employeeRole(EmployeeRole.CASHIER)
                    .employeeCode("EMP-T3-001")
                    .salary(BigDecimal.valueOf(12000000))
                    .tenantId(tenantId3)
                    .build();
            employeeRepository.save(emp3);

            // 6. Khởi tạo Khách Hàng (Customers)
            System.out.println("Đang tạo Khách hàng...");
            String[] vietnameseAddressesT2 = {
                "Đường Láng, Hà Nội", "Trần Duy Hưng, Hà Nội", "Kim Mã, Hà Nội", "Chùa Láng, Hà Nội", "Thái Hà, Hà Nội"
            };
            String[] vietnameseAddressesT3 = {
                "Xuân Thủy, Hà Nội", "Dịch Vọng, Hà Nội", "Tô Hiệu, Hà Nội", "Hoàng Quốc Việt, Hà Nội", "Phạm Văn Đồng, Hà Nội"
            };
            for (int i = 1; i <= 5; i++) {
                // Khách hàng cho Tenant 2
                User custUserT2 = User.builder()
                        .fullName("Khách Hàng Thời Trang " + i)
                        .email("customer" + i + "_t2@gmail.com")
                        .password(passwordEncoder.encode("customer123"))
                        .role(UserRole.ROLE_CUSTOMER)
                        .phone("092000020" + i)
                        .avatar("avatar_t2_" + i + ".png")
                        .tenantId(tenantId2)
                        .build();
                custUserT2 = userRepository.save(custUserT2);

                Customer custT2 = Customer.builder()
                        .user(custUserT2)
                        .address(vietnameseAddressesT2[i-1])
                        .tenantId(tenantId2)
                        .build();
                customerRepository.save(custT2);

                // Khách hàng cho Tenant 3
                User custUserT3 = User.builder()
                        .fullName("Khách Hàng Công Nghệ " + i)
                        .email("customer" + i + "_t3@gmail.com")
                        .password(passwordEncoder.encode("customer123"))
                        .role(UserRole.ROLE_CUSTOMER)
                        .phone("093000030" + i)
                        .avatar("avatar_t3_" + i + ".png")
                        .tenantId(tenantId3)
                        .build();
                custUserT3 = userRepository.save(custUserT3);

                Customer custT3 = Customer.builder()
                        .user(custUserT3)
                        .address(vietnameseAddressesT3[i-1])
                        .tenantId(tenantId3)
                        .build();
                customerRepository.save(custT3);
            }

            // 7. Khởi tạo Danh Mục Sản Phẩm (Categories)
            System.out.println("Đang tạo các Danh mục sản phẩm...");
            
            // Danh mục mặc định hệ thống dưới Tenant 1 (isSystemDefault = true)
            List<Category> t1Categories = new ArrayList<>();
            String[] t1CategoryNames = {"Mặc Định Hệ Thống", "Dịch Vụ Chung"};
            for (String name : t1CategoryNames) {
                Category cat = Category.builder()
                        .name(name)
                        .tenantId(tenantId1)
                        .isSystemDefault(true)
                        .build();
                t1Categories.add(categoryRepository.save(cat));
            }

            // Danh mục của Tenant 2
            List<Category> t2Categories = new ArrayList<>();
            String[] t2CategoryNames = {"Áo Thun", "Quần Jeans", "Váy Đầm", "Giày Dép", "Phụ Kiện"};
            for (String name : t2CategoryNames) {
                Category cat = Category.builder()
                        .name(name)
                        .tenantId(tenantId2)
                        .isSystemDefault(false)
                        .build();
                t2Categories.add(categoryRepository.save(cat));
            }

            // Danh mục của Tenant 3
            List<Category> t3Categories = new ArrayList<>();
            String[] t3CategoryNames = {"Điện Thoại", "Laptop", "Máy Tính Bảng", "Phụ Kiện Công Nghệ", "Thiết Bị Thông Minh"};
            for (String name : t3CategoryNames) {
                Category cat = Category.builder()
                        .name(name)
                        .tenantId(tenantId3)
                        .isSystemDefault(false)
                        .build();
                t3Categories.add(categoryRepository.save(cat));
            }

            // Liên kết danh mục với cửa hàng
            s2.setCategories(new HashSet<>(t2Categories));
            storeRepository.save(s2);

            s3.setCategories(new HashSet<>(t3Categories));
            storeRepository.save(s3);

            // 8. Khởi tạo Sản Phẩm (Products)
            System.out.println("Đang tạo các Sản phẩm...");
            
            // Sản phẩm cho Tenant 2 (Zara Fashion)
            String[] fashionProductNames = {"Áo Thun Zara Premium", "Quần Jeans Zara Slimfit", "Váy Đầm Dạ Hội", "Giày Thể Thao Zara", "Túi Xách Thời Trang"};
            BigDecimal[] fashionPrices = {
                BigDecimal.valueOf(450000), BigDecimal.valueOf(600000), BigDecimal.valueOf(750000), BigDecimal.valueOf(900000), BigDecimal.valueOf(1050000)
            };
            BigDecimal[] fashionMrps = {
                BigDecimal.valueOf(500000), BigDecimal.valueOf(650000), BigDecimal.valueOf(800000), BigDecimal.valueOf(950000), BigDecimal.valueOf(1100000)
            };
            for (int i = 0; i < 5; i++) {
                Category cat = t2Categories.get(i);
                Set<Category> cats = new HashSet<>();
                cats.add(cat);
                
                Products prod = Products.builder()
                        .name(fashionProductNames[i])
                        .sku("SKU-T2-" + (200 + i))
                        .description("Sản phẩm thời trang cao cấp từ bộ sưu tập " + cat.getName())
                        .mrp(fashionMrps[i])
                        .sellingPrice(fashionPrices[i])
                        .brand("Zara")
                        .categories(cats)
                        .tenantId(tenantId2)
                        .build();
                productRepository.save(prod);
            }

            // Sản phẩm cho Tenant 3 (Thế Giới Công Nghệ)
            String[] techProductNames = {"Điện Thoại iPhone 15 Pro", "Laptop MacBook Pro M3", "Máy Tính Bảng iPad Air", "Tai Nghe AirPods Pro", "Đồng Hồ Apple Watch Series 9"};
            BigDecimal[] techPrices = {
                BigDecimal.valueOf(27000000), BigDecimal.valueOf(43000000), BigDecimal.valueOf(15000000), BigDecimal.valueOf(5500000), BigDecimal.valueOf(9500000)
            };
            BigDecimal[] techMrps = {
                BigDecimal.valueOf(28000000), BigDecimal.valueOf(45000000), BigDecimal.valueOf(16000000), BigDecimal.valueOf(6000000), BigDecimal.valueOf(10000000)
            };
            for (int i = 0; i < 5; i++) {
                Category cat = t3Categories.get(i);
                Set<Category> cats = new HashSet<>();
                cats.add(cat);
                
                Products prod = Products.builder()
                        .name(techProductNames[i])
                        .sku("SKU-T3-" + (300 + i))
                        .description("Thiết bị công nghệ chính hãng - dòng sản phẩm " + cat.getName())
                        .mrp(techMrps[i])
                        .sellingPrice(techPrices[i])
                        .brand("Apple")
                        .categories(cats)
                        .tenantId(tenantId3)
                        .build();
                productRepository.save(prod);
            }

            // 9. Khởi tạo Kho Hàng (Inventories)
            System.out.println("Đang tạo các Kho hàng...");
            Inventory inv2 = Inventory.builder()
                    .branch(b2)
                    .inventoryName("Kho Chính Zara Hà Nội")
                    .tenantId(tenantId2)
                    .build();
            inventoryRepository.save(inv2);

            Inventory inv3 = Inventory.builder()
                    .branch(b3)
                    .inventoryName("Kho Chính Thế Giới Công Nghệ")
                    .tenantId(tenantId3)
                    .build();
            inventoryRepository.save(inv3);

            System.out.println("--------------------- Hoàn Thành Khởi Tạo Dữ Liệu Thành Công ---------------------");
        };
    }
}
