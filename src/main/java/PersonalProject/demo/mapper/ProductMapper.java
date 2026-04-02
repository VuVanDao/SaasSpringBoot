package PersonalProject.demo.mapper;

import PersonalProject.demo.Dto.Request.CreateProductRequest;
import PersonalProject.demo.Dto.Request.UpdateProductRequest;
import PersonalProject.demo.Dto.Response.ProductDto;
import PersonalProject.demo.models.Products;
import PersonalProject.demo.models.Store;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductMapper {

    private final storeMapper storeMapper;

    public Products convertToModel(CreateProductRequest request, Store store) {
        return Products.builder()
                .name(request.getName())
                .sku(request.getSku())
                .description(request.getDescription())
                .brand(request.getBrand())
                .image(request.getImage())
                .mrp(request.getMrp())
                .sellingPrice(request.getSellingPrice())
                .store(store)
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
        // store not updated
        return existing;
    }

    public ProductDto convertToDto(Products product, Boolean includeStore) {
        if(includeStore) {
            return ProductDto.builder()
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
                .store(storeMapper.convertToDto(product.getStore()))
                .build();
        }
        return ProductDto.builder()
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
                .build();
    }
}