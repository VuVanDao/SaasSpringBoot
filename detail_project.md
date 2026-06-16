# TÀI LIỆU CHI TIẾT DỰ ÁN (FOR FRONTEND TEAM)
## Hệ Thống Quản Lý Cửa Hàng & Kho Hàng Đa Chi Nhánh (SaaS Multi-Tenant Store & Inventory Management System)

Tài liệu này được biên soạn chi tiết nhằm giúp đội ngũ phát triển **Frontend (React JS)** hiểu rõ về nghiệp vụ, kiến trúc hệ thống, phân quyền (RBAC), thiết kế các luồng giao diện (UI/UX) và tích hợp API một cách chính xác và tối ưu nhất.

---

## 1. Tổng Quan Hệ Thống & Kiến Trúc
Dự án được xây dựng theo mô hình **SaaS Multi-Tenant** (phần mềm dịch vụ đa người thuê). Dữ liệu của từng doanh nghiệp (Tenant) hoàn toàn độc lập và tách biệt ở mức cơ sở dữ liệu.

### Quy Tắc Giao Tiếp API Bắt Buộc (Header Request)
Tất cả các API (trừ luồng đăng nhập/đăng ký) đều yêu cầu đính kèm các Header sau:
1. **`Authorization`**: `Bearer <JWT_TOKEN>` (Xác thực người dùng).
2. **`X-tenant-Id`**: `<TENANT_ID>` (Xác định Tenant hiện tại để truy vấn dữ liệu biệt lập).

> [!IMPORTANT]
> Khi người dùng đăng nhập thành công, Backend sẽ trả về `tenantId` và danh sách quyền hạn. Frontend phải lưu trữ `tenantId` này (ví dụ vào `localStorage` hoặc `Cookie`) và tự động gán vào Header `X-tenant-Id` cho mọi Request tiếp theo.

---

## 2. Hệ Thống Phân Quyền Người Dùng (Role-Based Access Control - RBAC)
Giao diện Frontend cần được thiết kế dựa trên vai trò của người dùng (`role` trả về khi đăng nhập):

| Vai Trò | Quyền Hạn Chính | Giao Diện Được Tiếp Cận |
| :--- | :--- | :--- |
| **`ROLE_SUPER_ADMIN`** | Quản trị viên toàn hệ thống SaaS. Quản lý các doanh nghiệp (Tenant). | Dashboard Hệ thống, Quản lý Tenant, Gia hạn gói dịch vụ. |
| **`ROLE_ADMIN`** | Chủ doanh nghiệp (Tenant Owner). Có toàn quyền trong Tenant đó. | Quản lý Cửa hàng, Chi nhánh, Nhân viên, Sản phẩm, Tồn kho, Báo cáo tổng quan. |
| **`ROLE_STORE_MANAGER`**| Quản lý một Cửa hàng cụ thể. | Quản lý Sản phẩm, Nhân viên thuộc cửa hàng, Nhập/Xuất kho thuộc cửa hàng. |
| **`ROLE_BRANCH_MANAGER`**| Quản lý một Chi nhánh cụ thể. | Quản lý kho hàng & hàng tồn kho của chi nhánh được phân công. |
| **`ROLE_STAFF`** / **`ROLE_CASHIER`** / **`ROLE_ADVISE`** | Nhân viên vận hành (Bán hàng, Thủ kho, Tư vấn). | Xem thông tin sản phẩm, Cập nhật tồn kho (nếu là staff), Check tồn kho. |

---

## 3. Cấu Trúc Định Tuyến Giao Diện (Sitemap & Routing)

Hệ thống điều hướng của React Router DOM nên được phân cấp như sau:

### 3.1. Public Routes
*   `/login`: Đăng nhập hệ thống.
*   `/register`: Đăng ký tài khoản doanh nghiệp mới (tạo Tenant mới).

### 3.2. Super Admin Routes (Yêu cầu `ROLE_SUPER_ADMIN`)
*   `/super-admin/dashboard`: Thống kê tổng số lượng Tenant hoạt động, doanh thu hệ thống.
*   `/super-admin/tenants`: Danh sách các doanh nghiệp đăng ký, kích hoạt/khóa tài khoản doanh nghiệp, gia hạn thời gian sử dụng dịch vụ.

### 3.3. Tenant Scope Routes (Yêu cầu Token & `activeTenantId`)
*   `/dashboard/overview`: Trang tổng quan kinh doanh (Thống kê số lượng Chi nhánh, Cửa hàng, Sản phẩm, Nhân viên, cảnh báo tồn kho thấp).
*   `/dashboard/stores`: Quản lý các Cửa hàng (Store) thuộc Tenant.
*   `/dashboard/branches`: Quản lý các Chi nhánh (Branch).
*   `/dashboard/employees`: Danh sách nhân viên, gán chi nhánh/cửa hàng cho nhân viên, chỉnh sửa vai trò (`ROLE_STAFF`, `ROLE_CASHIER`,...).
*   `/dashboard/categories`: Quản lý danh mục sản phẩm (Thêm, sửa, xóa, tìm kiếm danh mục).
*   `/dashboard/products`: Quản lý danh sách sản phẩm (Thông tin chi tiết, giá nhập, giá bán, SKU, barcode).
*   `/dashboard/inventory`:
    *   *Danh sách kho hàng:* Xem danh sách các kho hàng theo từng Chi nhánh.
    *   *Nhập kho hàng loạt (Batch Import):* Màn hình nhập số lượng lớn sản phẩm vào kho.
    *   *Kiểm kê & tồn kho:* Tra cứu số lượng tồn kho của từng sản phẩm.
*   `/dashboard/customers`: Quản lý danh sách khách hàng của doanh nghiệp.
*   `/dashboard/profile`: Thiết lập thông tin cá nhân của User hiện tại.

---

## 4. Mô Tả Chi Tiết Giao Diện Từng Màn Inform (UI/UX Specification)

### 4.1. Màn Hình Đăng Ký / Đăng Nhập (`/login` & `/register`)
*   **Thiết kế:** Minimalist, hiện đại, có hình ảnh minh họa động hoặc hiệu ứng Glassmorphism.
*   **Đăng ký (`/register`):**
    *   Nhập thông tin cá nhân: Họ tên, Email, Số điện thoại, Mật khẩu.
    *   Nhập thông tin doanh nghiệp (Tenant): Tên doanh nghiệp, Tên miền (Domain), Địa chỉ.
    *   Khi đăng ký thành công, hệ thống tự động tạo một Tenant mới và gán người dùng làm `ROLE_ADMIN` của Tenant đó.
*   **Đăng nhập (`/login`):**
    *   Form nhập Email và Mật khẩu.
    *   Sau khi thành công, lưu trữ `accessToken`, `refreshToken`, `role`, và `tenantId` vào Global State và `localStorage`.

### 4.2. Trang Tổng Quan Doanh Nghiệp (`/dashboard/overview`)
*   **Các thẻ chỉ số (KPI Cards):**
    *   Tổng số cửa hàng.
    *   Tổng số chi nhánh.
    *   Tổng số sản phẩm đang kinh doanh.
    *   Tổng số nhân viên.
*   **Widget Cảnh báo tồn kho thấp (Low Stock Alert):** Hiển thị danh sách các sản phẩm có số lượng trong kho dưới định mức tối thiểu.
*   **Biểu đồ:** Biểu đồ thống kê sản phẩm theo danh mục (Pie Chart) hoặc tăng trưởng cửa hàng theo thời gian.

### 4.3. Quản Lý Cửa Hàng & Chi Nhánh (`/dashboard/stores` & `/dashboard/branches`)
*   **Quản lý Cửa Hàng (Store):**
    *   Hiển thị danh sách cửa hàng dưới dạng Grid Card chứa: Tên thương hiệu, Địa chỉ, Số điện thoại liên hệ, Trạng thái hoạt động (`ACTIVE`, `BLOCKED`, `PENDING`).
    *   Nút bật/tắt nhanh trạng thái hoạt động của Cửa hàng (Sử dụng API `PATCH /api/stores/update_status/{id}`).
    *   Modal Thêm/Sửa Cửa hàng: Cho phép nhập thông tin liên hệ và liên kết danh sách Chi nhánh trực thuộc.
*   **Quản lý Chi Nhánh (Branch):**
    *   Hiển thị bảng (Table) danh sách chi nhánh kèm tên người quản lý (`manager`).
    *   Form Thêm/Sửa chi nhánh.

### 4.4. Quản Lý Sản Phẩm (`/dashboard/products`)
*   **Giao diện Danh sách:**
    *   Bộ lọc thông minh: Lọc theo Danh mục (Category), trạng thái (`ACTIVE`, `INACTIVE`), và tìm kiếm nhanh.
    *   Thanh tìm kiếm thông minh: Có chức năng **Debounce (300ms)** để gọi API `/api/products/store/{storeId}/search?query=...` khi người dùng nhập ký tự.
*   **Modal Thêm/Sửa Sản Phẩm:**
    *   Nhập các thông tin: Tên sản phẩm, SKU (Mã sản phẩm), Nhãn hiệu (Brand), Giá bán lẻ (`price`), Giá vốn/Giá nhập (`cost`), Danh mục (Dropdown lấy từ API `/api/categories`).

### 4.5. Quản Lý Kho Hàng & Nhập Kho Hàng Loạt (`/dashboard/inventory`)
Đây là tính năng cốt lõi và phức tạp nhất của phân hệ kho, FE cần tối ưu tốt trải nghiệm người dùng:

#### Luồng 1: Tra cứu tồn kho (Stock Lookup)
*   Hiển thị danh sách sản phẩm và số lượng tồn kho tương ứng trong từng kho hàng.
*   Khi click vào một sản phẩm, gọi API `/api/inventory_item/get_inventories_from_product/{productId}` để hiển thị danh sách các kho đang chứa sản phẩm đó kèm số lượng tương ứng dưới dạng bảng popup chi tiết.

#### Luồng 2: Nhập Kho Hàng Loạt (Batch Inventory Import)
*   **Bước 1:** Chọn Chi nhánh -> Chọn Kho hàng cụ thể (`inventoryId`) cần nhập hàng.
*   **Bước 2:** Giao diện hiển thị một danh sách dạng dòng (Dynamic Form Array).
    *   Mỗi dòng bao gồm:
        *   **Sản phẩm:** Ô tìm kiếm tự động hoàn thành (Autocomplete Search) kết nối với API `/api/products/store/{storeId}/search`.
        *   **Số lượng:** Input số (chỉ cho phép số nguyên dương > 0).
        *   **Đơn giá nhập:** Input số tiền.
        *   **Ghi chú:** Ô nhập mô tả ngắn (ví dụ: "Hàng nhập đợt 1").
    *   Có nút "Thêm dòng mới" và "Xóa dòng" ở mỗi hàng.
*   **Bước 3:** Bấm nút **"Xác nhận nhập kho"** -> Hiển thị Modal tóm tắt tổng số lượng mặt hàng và tổng giá trị đơn nhập.
*   **Bước 4:** Gửi payload JSON lên API:
    `POST /api/inventory_item/batch/{inventoryId}`
    ```json
    {
      "items": [
        { "productId": 1, "quantity": 100, "unitPrice": 15000000, "notes": "Nhập lô đầu năm" },
        { "productId": 2, "quantity": 50, "unitPrice": 8000000, "notes": "Nhập thêm" }
      ]
    }
    ```
*   **Bước 5:** Thông báo thành công bằng Toast Message và tự động chuyển hướng về trang chi tiết kho hàng để kiểm tra tồn kho mới.

### 4.6. Quản Lý Nhân Viên (`/dashboard/employees`)
*   Hiển thị danh sách nhân viên dạng bảng (Table) hoặc thẻ (Card Grid) kèm theo ảnh đại diện giả định.
*   Hiển thị rõ các Tag vai trò bằng màu sắc để dễ phân biệt (Ví dụ: `ADMIN` màu đỏ, `STORE_MANAGER` màu tím, `CASHIER` màu xanh lá, `STAFF` màu xanh dương).
*   Form Thêm mới/Cập nhật nhân viên:
    *   Liên kết tài khoản User hệ thống (chọn qua Email/SĐT).
    *   Chọn Cửa hàng và Chi nhánh phân công làm việc.
    *   Nhập mức lương (`salary`) và Trạng thái (`ACTIVE`, `INACTIVE`).

---

## 5. Hướng Dẫn Kỹ Thuật Cho Frontend (Tech Stack & Integration)

### 5.1. Quản Lý State Toàn Cục
Frontend cần quản lý các state toàn cục sau (khuyên dùng React Context hoặc Redux Toolkit):
1.  `auth`: Lưu trữ thông tin đăng nhập bao gồm: `token`, `refreshToken`, và `user` (gồm ID, họ tên, vai trò).
2.  `activeTenantId`: ID của Tenant hiện hành.
3.  `activeStoreId` và `activeBranchId`: Cửa hàng hoặc chi nhánh đang được chọn để lọc các truy vấn dữ liệu sản phẩm, nhân viên và kho hàng tương ứng.

### 5.2. File Cấu Hình Axios API Client Mẫu (`api.js`)
Dưới đây là mã nguồn gợi ý để tự động đính kèm Token, Tenant ID và cơ chế **Tự động làm mới Token (Auto Refresh Token)** khi mã truy cập hết hạn:

```javascript
import axios from 'axios';

const api = axios.create({
  baseURL: 'http://localhost:5000/api',
  headers: {
    'Content-Type': 'application/json',
  }
});

// Interceptor đính kèm Token và Tenant ID vào mỗi Request
api.interceptors.request.use((config) => {
  const token = localStorage.getItem('accessToken');
  const tenantId = localStorage.getItem('activeTenantId');

  if (token) {
    config.headers['Authorization'] = `Bearer ${token}`;
  }
  if (tenantId) {
    config.headers['X-tenant-Id'] = tenantId;
  }
  return config;
}, (error) => {
  return Promise.reject(error);
});

// Interceptor xử lý lỗi 401 (Hết hạn Token) để tự động gọi Refresh Token
api.interceptors.response.use(
  (response) => response,
  async (error) => {
    const originalRequest = error.config;
    
    // Nếu gặp lỗi 401 Unauthorized và chưa thử refresh lại lần nào
    if (error.response?.status === 401 && !originalRequest._retry) {
      originalRequest._retry = true;
      try {
        const refreshToken = localStorage.getItem('refreshToken');
        if (!refreshToken) throw new Error('No refresh token available');
        
        // Gọi API refresh token (không dùng instance 'api' để tránh loop interceptor)
        const res = await axios.post('http://localhost:5000/api/auth/refresh', { token: refreshToken });
        
        const newAccessToken = res.data.result.jwt || res.data.result.accessToken;
        localStorage.setItem('accessToken', newAccessToken);
        
        // Gán token mới và thực hiện lại request cũ
        originalRequest.headers['Authorization'] = `Bearer ${newAccessToken}`;
        return api(originalRequest);
      } catch (refreshError) {
        // Refresh token cũng hết hạn -> Xóa session và chuyển hướng về đăng nhập
        localStorage.removeItem('accessToken');
        localStorage.removeItem('refreshToken');
        localStorage.removeItem('activeTenantId');
        window.location.href = '/login';
        return Promise.reject(refreshError);
      }
    }
    return Promise.reject(error);
  }
);

export default api;
```

---

## 6. Hướng Dẫn Thiết Kế Đồ Họa & Giao Diện (UI/UX Best Practices)

Để tạo ra một giao diện hiện đại, chuyên nghiệp và có giá trị thương mại cao, nhóm FE nên tuân thủ các nguyên tắc thiết kế sau:

1.  **Chủ Đề (Theme):**
    *   Hỗ trợ chế độ Sáng/Tối (Light/Dark Mode). Chế độ tối nên sử dụng các tông màu xám đậm sang trọng (ví dụ: `#121212`, `#1e1e1e`), tránh sử dụng màu đen tuyền thuần túy.
    *   Sử dụng hiệu ứng **Glassmorphism** (nền mờ kết hợp viền bán trong suốt) cho các thẻ điều hướng phụ và hộp thoại (Modal).
2.  **Typography (Kiểu Chữ):**
    *   Sử dụng phông chữ hiện đại từ Google Fonts như **Inter** hoặc **Outfit**.
3.  **Trực Quan Hóa Trạng Thế (Status Badges):**
    *   `ACTIVE`, `In Stock`: Sử dụng nền xanh lá cây nhạt kết hợp chữ xanh lá đậm.
    *   `BLOCKED`, `Out of Stock`: Sử dụng nền đỏ nhạt kết hợp chữ đỏ đậm.
    *   `PENDING`, `Low Stock`: Sử dụng nền vàng nhạt kết hợp chữ cam/vàng đậm.
4.  **Tương tác (Micro-interactions):**
    *   Thêm hiệu ứng Hover làm nổi bật nhẹ (Scale 1.02, đổ bóng mịn) đối với các Card sản phẩm hoặc Card cửa hàng khi rê chuột qua.
    *   Hiển thị Loading Skeleton (khung xương tải trang) thay vì biểu tượng quay tròn đơn điệu khi đang tải danh sách dữ liệu lớn.
5.  **Thiết kế Đáp ứng (Responsive Design):**
    *   Tối ưu hóa bố cục hiển thị tốt từ màn hình Desktop lớn (1920px), Laptop (1366px), cho đến Máy tính bảng (Tablet) để quản lý cửa hàng có thể dễ dàng đi lại kiểm tra tồn kho.

---

## 7. Tài Liệu Tham Khảo Liên Kết
*   Tài liệu toàn bộ API Endpoints chi tiết bằng tiếng Việt: [API_ENDPOINTS_DOCUMENTATION_VN.md](file:///d:/ForJava/Project/demo/API_ENDPOINTS_DOCUMENTATION_VN.md)
*   Tài liệu toàn bộ API Endpoints chi tiết bằng tiếng Anh: [API_ENDPOINTS_DOCUMENTATION_EN.md](file:///d:/ForJava/Project/demo/API_ENDPOINTS_DOCUMENTATION_EN.md)
*   Hướng dẫn chạy mã nguồn Backend: [readme.md](file:///d:/ForJava/Project/demo/readme.md)
