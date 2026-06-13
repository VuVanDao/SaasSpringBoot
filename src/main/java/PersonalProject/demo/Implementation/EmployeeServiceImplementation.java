package PersonalProject.demo.Implementation;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.stripe.model.tax.Registration.CountryOptions.Us;

import PersonalProject.demo.Dto.Request.CreateEmployeeRequest;
import PersonalProject.demo.Dto.Request.UpdateEmployeeRequest;
import PersonalProject.demo.Dto.Response.EmployeeDto;
import PersonalProject.demo.Enums.ErrorCode;
import PersonalProject.demo.Enums.UserRole;
import PersonalProject.demo.exception.ResourceNotFoundException;
import PersonalProject.demo.mapper.EmployeeMapper;
import PersonalProject.demo.models.Branch;
import PersonalProject.demo.models.Employee;
import PersonalProject.demo.models.Store;
import PersonalProject.demo.models.User;
import PersonalProject.demo.repositories.BranchRepository;
import PersonalProject.demo.repositories.EmployeeRepository;
import PersonalProject.demo.repositories.StoreRepositories;
import PersonalProject.demo.repositories.UserRepository;
import PersonalProject.demo.services.EmployeeService;
import PersonalProject.demo.utils.TenantUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImplementation implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final TenantUtil tenantUtil;
    private final EmployeeMapper employeeMapper;
    private final UserRepository userRepository;
    private final StoreRepositories storeRepository;
    private final BranchRepository branchRepository;

    @Override
    public EmployeeDto createEmployee(CreateEmployeeRequest createEmployeeRequest, HttpServletRequest request) {
        Long tenantId = tenantUtil.validateTenant(request);
        User user = userRepository.findByIdAndTenantId(createEmployeeRequest.getUserId(), tenantId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.Resource_not_found));
        if(user.getRole() == UserRole.ROLE_ADMIN || user.getRole() == UserRole.ROLE_SUPER_ADMIN || user.getRole() == UserRole.ROLE_STORE_MANAGER){
            throw new IllegalArgumentException("This user has role " + user.getRole() + " cannot be employee");
        }
        Store store = storeRepository.findByIdAndTenantId(createEmployeeRequest.getStoreId(), tenantId);
        if (store == null) {
            throw new ResourceNotFoundException(ErrorCode.Resource_not_found);
        }
        Branch branch = null;
        if (createEmployeeRequest.getBranchId() != null) {
            branch = branchRepository.findByIdAndTenantId(createEmployeeRequest.getBranchId(), tenantId)
                    .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.Resource_not_found));
        }
        Employee employee = employeeMapper.toModel(createEmployeeRequest, store, branch, user, tenantId);
        employee = employeeRepository.save(employee);
        return employeeMapper.toEmployeeDto(employee);
    }

    @Override
    public List<EmployeeDto> getAllEmployeesOfAStore(Long store_id, HttpServletRequest request) {
        Long tenantId = tenantUtil.validateTenant(request);
        List<Employee> employees = employeeRepository.findAllByStoreIdAndTenantId(store_id, tenantId);
        return employees.stream().map(employeeMapper::toEmployeeDto).collect(Collectors.toList());
    }

    @Override
    public EmployeeDto getEmployeeById(Long id, HttpServletRequest request) {
        Long tenantId = tenantUtil.validateTenant(request);
        Employee employee = employeeRepository.findByIdAndTenantId(id, tenantId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.Resource_not_found));
        return employeeMapper.toEmployeeDto(employee);
    }

    @Override
    public EmployeeDto getEmployeeByUserId(Long userId, HttpServletRequest request) {
        Long tenantId = tenantUtil.validateTenant(request);
        Employee employee = employeeRepository.findByUserIdAndTenantId(userId, tenantId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.Resource_not_found));
        return employeeMapper.toEmployeeDto(employee);
    }

    @Override
    public List<EmployeeDto> getEmployeeByBranchId(Long branchId, HttpServletRequest request) {
        Long tenantId = tenantUtil.validateTenant(request);
        List<Employee> employees = employeeRepository.findAllByBranchIdAndTenantId(branchId, tenantId);
        return employees.stream().map(employeeMapper::toEmployeeDto).collect(Collectors.toList());
    }

    @Override
    public EmployeeDto UpdateEmployee(Long employeeId, UpdateEmployeeRequest updateEmployeeRequest,
            HttpServletRequest request) {
        Long tenantId = tenantUtil.validateTenant(request);
        // tìm employee theo id và tenantId
        Employee employee = employeeRepository.findByIdAndTenantId(employeeId, tenantId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.Resource_not_found));
        // check branch
        Branch branch = branchRepository.findByIdAndTenantId(updateEmployeeRequest.getBranchId(), tenantId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.Resource_not_found));
                    
        Store store = storeRepository.findByIdAndTenantId(updateEmployeeRequest.getStore_id(), tenantId);
        if (store == null) {
            throw new ResourceNotFoundException(ErrorCode.Resource_not_found);
        }
        User user = userRepository.findByIdAndTenantId(updateEmployeeRequest.getUser_id(), tenantId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.Resource_not_found));
        // th1: employee đã có branch, giờ update branch mới
        if (employee.getBranch() != null) {
            // th1.1: branch mới giống branch cũ, không update branch
            if(branch.getId().equals(employee.getBranch().getId())){
                // branch không thay đổi
            } else {
                // th1.2: branch mới khác branch cũ, update branch mới
                employee.setBranch(branch);
            }
        } else {
            // th2: employee chưa có branch, giờ set branch mới
            employee.setBranch(branch);
        }
        if(updateEmployeeRequest.getStore_id() != null && !updateEmployeeRequest.getStore_id().equals(employee.getStore().getId())){
            store = storeRepository.findByIdAndTenantId(updateEmployeeRequest.getStore_id(), tenantId);
            if (store == null) {
                throw new ResourceNotFoundException(ErrorCode.Resource_not_found);
            }
            employee.setStore(store);
        }
        if (updateEmployeeRequest.getUser_id() != null
                && !updateEmployeeRequest.getUser_id().equals(employee.getUser().getId())) {
            user = userRepository.findByIdAndTenantId(updateEmployeeRequest.getUser_id(), tenantId)
                    .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.Resource_not_found));
            employee.setUser(user);
        }
        employee.setEmail(updateEmployeeRequest.getEmail());
        employee.setPhone(updateEmployeeRequest.getPhone());
        employee.setEmployeeCode(updateEmployeeRequest.getEmployeeCode());
        employee.setSalary(updateEmployeeRequest.getSalary());
        // Cập nhật thông tin employee
        employeeRepository.save(employee);
        return employeeMapper.toEmployeeDto(employee);

    }

    @Override
    public void deleteEmployee(Long employeeId, HttpServletRequest request) {
        Long tenantId = tenantUtil.validateTenant(request);
        Employee employee = employeeRepository.findByIdAndTenantId(employeeId, tenantId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.Resource_not_found));
        User user = userRepository.findByIdAndTenantId(employee.getUser().getId(), tenantId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.Resource_not_found));
        if (user != null) {
            System.out.println("----------delete employee------------");
            user.setEmployee(null);
            userRepository.save(user);
            employee.setUser(null);
            employeeRepository.save(employee);
        }
        employeeRepository.delete(employee);
    }
    
}
