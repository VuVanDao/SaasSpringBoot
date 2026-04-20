package PersonalProject.demo.mapper;

import PersonalProject.demo.Dto.Request.CreateProductRequest;
import PersonalProject.demo.Dto.Request.UpdateProductRequest;
import PersonalProject.demo.Dto.Response.CategoryResponse;
import PersonalProject.demo.Dto.Response.ProductDto;
import PersonalProject.demo.models.Category;
import PersonalProject.demo.models.Products;
import PersonalProject.demo.models.Store;
import PersonalProject.demo.repositories.CategoryRepositories;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductMapper {

    private final storeMapper storeMapper;
    private final CategoryRepositories categoryRepository;

    public Products convertToModel(CreateProductRequest request, List<Category> categories) {
        return Products.builder()
                .name(request.getName())
                .sku(request.getSku())
                .description(request.getDescription())
                .brand(request.getBrand())
                .image(request.getImage())
                .mrp(request.getMrp())
                .sellingPrice(request.getSellingPrice())
                .tenantId(request.getTenant_id())
                .categories(new HashSet<>(categories))
                .build();
    }

    // hàm này dành cho update product
    public Products convertToModel(UpdateProductRequest request, Products existing) {
        existing.setName(request.getName());
        existing.setSku(request.getSku());
        existing.setDescription(request.getDescription());
        existing.setBrand(request.getBrand());
        existing.setImage(request.getImage());
        existing.setMrp(request.getMrp());
        existing.setSellingPrice(request.getSellingPrice());
        if (existing.getCategories().size() != request.getCategoryIds().size()) {
            List<Category> categories = categoryRepository.findAllById(request.getCategoryIds());
            existing.setCategories(new HashSet<>(categories));
        } 
        // store not updated
        return existing;
    }

    public ProductDto convertToDto(Products product, Boolean includeStore) {
        ProductDto builder  = ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .sku(product.getSku())
                .description(product.getDescription())
                .brand(product.getBrand())
                .image(product.getImage())
                .mrp(product.getMrp())
                .sellingPrice(product.getSellingPrice())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .categories(product.getCategories().stream().map(category -> CategoryResponse.builder().id(category.getId()).name(category.getName()).isSystemDefault(category.getIsSystemDefault()).build()).toList())
                .build();
        // if (includeStore) {
        //     builder.setStore(storeMapper.convertToDto(product.getStore()));
        // }
        return builder;
    }
}