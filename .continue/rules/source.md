---
description: A description of your rule
---

Project Context & Coding Standards
Bạn là một chuyên gia Java/Spring Boot. Hãy tuân thủ cấu trúc thư mục và quy tắc viết mã dưới đây khi làm việc với source code này.

1. Project Structure Overview
   Dự án được tổ chức theo mô hình Layered Architecture truyền thống. Các thành phần chính nằm trong gói java/PersonalProject/demo:

controllers/: Tiếp nhận các HTTP Request, điều hướng xử lý. Chỉ chứa logic điều hướng, không chứa business logic.

services/: Chứa các Interfaces định nghĩa nghiệp vụ.

Implementation/: Chứa các lớp thực thi (Impls) cho các Service interfaces. Business logic tập trung hoàn toàn tại đây.

repositories/: Lớp truy xuất dữ liệu (Data Access Layer), sử dụng Spring Data JPA/Hibernate.

models /: Chứa các Entity ánh xạ với Database.

domain/: chứa các enum

Dto/: Data Transfer Objects dùng để trao đổi dữ liệu giữa các layer và API.

mapper/: Chứa logic chuyển đổi giữa Entity và DTO .

configuration/: Các lớp cấu hình Bean, Security, Swagger, CORS, v.v.

exception/: Chứa Custom Exceptions và Global Exception Handler (@ControllerAdvice).

2. Coding Guidelines for Agent
   Khi thực hiện các nhiệm vụ, hãy đảm bảo:

Flow xử lý dữ liệu
Request vào Controller.

Controller gọi Service (Interface).

Implementation xử lý logic, gọi Repository nếu cần.

Dữ liệu trả về từ Repository là Entity, phải được chuyển qua DTO tại lớp Service hoặc Controller thông qua Mapper trước khi trả về client.

Naming Conventions
Controllers: Kết thúc bằng Controller.java (vd: UserController.java).

Services: Kết thúc bằng Service.java (vd: UserService.java).

Implementations: Nằm trong folder Implementation, kết thúc bằng ServiceImpl.java (vd: UserServiceImpl.java).

Repositories: Kết thúc bằng Repository.java.

DTOs: Kết thúc bằng Dto.java hoặc Request/Response.

Quy tắc quan trọng
Luôn kiểm tra file pom.xml để biết các dependencies hiện có trước khi gợi ý thư viện mới.

Cấu hình hệ thống nằm trong src/main/resources/application.properties.

Sử dụng Lombok (@Data, @Getter, @Setter, @AllArgsConstructor, v.v.) để giảm thiểu code boilerplate nếu dự án đã tích hợp.

Khi tạo file mới, hãy tự động xác định package dựa trên folder tương ứng.

. Tech Stack Reference
Backend: Java, Spring Boot.

Build tool: Maven (pom.xml, mvnw).

Resources: Static files (CSS/JS) trong static/, HTML templates (nếu có) trong templates/.
