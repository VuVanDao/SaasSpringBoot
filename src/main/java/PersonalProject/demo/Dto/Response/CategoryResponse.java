package PersonalProject.demo.Dto.Response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryResponse {
    Long id;
    String name;
}
