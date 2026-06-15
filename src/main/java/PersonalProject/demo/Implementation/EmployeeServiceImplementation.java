package PersonalProject.demo.Implementation;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

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
    public EmployeeDto createEmployee(CreateEmployeeRequest createEmployeeRequest, Long tenantId) {
        tenantUtil.validateTenant(tenantId);
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
    public List<EmployeeDto> getAllEmployeesOfAStore(Long store_id, Long tenantId) {
        tenantUtil.validateTenant(tenantId);
        List<Employee> employees = employeeRepository.findAllByStoreIdAndTenantId(store_id, tenantId);
        return employees.stream().map(employeeMapper::toEmployeeDto).collect(Collectors.toList());
    }

    @Override
    public EmployeeDto getEmployeeById(Long id, Long tenantId) {
        tenantUtil.validateTenant(tenantId);
        Employee employee = employeeRepository.findByIdAndTenantId(id, tenantId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.Resource_not_found));
        return employeeMapper.toEmployeeDto(employee);
    }

    @Override
    public EmployeeDto getEmployeeByUserId(Long userId, Long tenantId) {
        tenantUtil.validateTenant(tenantId);
        Employee employee = employeeRepository.findByUserIdAndTenantId(userId, tenantId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.Resource_not_found));
        return employeeMapper.toEmployeeDto(employee);
    }

    @Override
    public List<EmployeeDto> getEmployeeByBranchId(Long branchId, Long tenantId) {
        tenantUtil.validateTenant(tenantId);
        List<Employee> employees = employeeRepository.findAllByBranchIdAndTenantId(branchId, tenantId);
        return employees.stream().map(employeeMapper::toEmployeeDto).collect(Collectors.toList());
    }

    @Override
    public EmployeeDto UpdateEmployee(Long employeeId, UpdateEmployeeRequest updateEmployeeRequest, Long tenantId) {
        tenantUtil.validateTenant(tenantId);
        Employee employee = employeeRepository.findByIdAndTenantId(employeeId, tenantId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.Resource_not_found));
        Branch branch = branchRepository.findByIdAndTenantId(updateEmployeeRequest.getBranchId(), tenantId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.Resource_not_found));
                    
        Store store = storeRepository.findByIdAndTenantId(updateEmployeeRequest.getStore_id(), tenantId);
        if (store == null) {
            throw new ResourceNotFoundException(ErrorCode.Resource_not_found);
        }
        User user = userRepository.findByIdAndTenantId(updateEmployeeRequest.getUser_id(), tenantId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.Resource_not_found));
        if (employee.getBranch() != null) {
            if(branch.getId().equals(employee.getBranch().getId())){
                // branch không thay đổi
            } else {
                employee.setBranch(branch);
            }
        } else {
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
        user.setEmail(updateEmployeeRequest.getEmail());
        user.setPhone(updateEmployeeRequest.getPhone());
        userRepository.save(user);
        employee.setEmployeeCode(updateEmployeeRequest.getEmployeeCode());
        employee.setSalary(updateEmployeeRequest.getSalary());
        employeeRepository.save(employee);
        return employeeMapper.toEmployeeDto(employee);
    }

    @Override
    public void deleteEmployee(Long employeeId, Long tenantId) {
        tenantUtil.validateTenant(tenantId);
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
