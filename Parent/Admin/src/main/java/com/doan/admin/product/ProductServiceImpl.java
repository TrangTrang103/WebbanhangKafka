package com.doan.admin.product;

import com.doan.admin.category.CategoryRepository;
import com.doan.admin.security.UserDetailsServiceImpl;
import com.doan.mutual.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
public class ProductServiceImpl {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SizeRepository productTypeRepository;

    @Autowired
    private CategoryRepository categoryRepository;


    @Autowired
    private CloudinaryService cloudinaryService;

    @Autowired
    private ProductImgRepository productImgRepository;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    public Page<Product> getAllProduct(Pageable pageable){
         return  productRepository.findAll(pageable);
    }
    public List<Product> getAllProduct(){
        return  productRepository.findAll();
    }
    public Page<Product> searchProduct(String inputSearch, Pageable pageable){
        return productRepository.findByProduct_NameAndId(inputSearch,pageable);
    }
    public List<Size> getProductSize(){
        return productTypeRepository.findAll();
    }

    public Product saveProduct(String product_name, String code, Product productsize, Integer category,
                               Long quantity, String import_price, String sale_price, String description, MultipartFile[] listImage,String email){
        Calendar cal = Calendar.getInstance();
           Product product = new Product();
           Category category1 = categoryRepository.findCategoryById(category);
           List<Size> size = new ArrayList<>();
           product.setName(product_name);
           product.setCode(code);
           product.setCategory(category1);
           product.setQuantity(quantity);
           product.setSizes(productsize.getSizes());
           product.setImportPrice(Double.valueOf(import_price));
           product.setSalePrice(Double.valueOf(sale_price));
           product.setDescription(description);
           product.setCreatedBy(email);
           product.setCreatedTime(cal.getTime());
           productRepository.save(product);
           List<Product> listProducts = productRepository.findAll();
           product = listProducts.get(listProducts.size() - 1);
           for (MultipartFile m:
                   listImage) {
               String urlImg = cloudinaryService.uploadFile(m);
               ProductImage img = new ProductImage();
               img.setProduct(product);
               img.setUrlImage(urlImg);
               productImgRepository.save(img);
            }
           return product;
       }

       public Product saveEdit(int productId,String product_name, String code, Product productsize, int category,
                               Long quantity, String import_price, String sale_price, String description, MultipartFile[] listImage,String email){
           Calendar cal = Calendar.getInstance();
           Product product = productRepository.findByProductId(productId);
           Category category1 = categoryRepository.findCategoryById(category);
           product.setName(product_name);
           product.setCode(code);
           product.setCategory(category1);
           product.setQuantity(quantity);
           product.setSizes(productsize.getSizes());
           product.setImportPrice(Double.valueOf(import_price));
           product.setSalePrice(Double.valueOf(sale_price));
           product.setDescription(description);
           product.setUpdatedBy(email);
           product.setUpdatedTime(cal.getTime());
           productRepository.save(product);
           for (MultipartFile y:
                listImage) {
               if (!y.isEmpty()){
                   String urlImg = cloudinaryService.uploadFile(y);
                   ProductImage img = new ProductImage();
                   img.setProduct(product);
                   img.setUrlImage(urlImg);
                   productImgRepository.save(img);
               }
           }
           return product;
       }
       public void deleteProduct(int id){
        productRepository.deleteById(id);
       }

       public Product getProductId(Integer id){
       return productRepository.findByProductId(id);
       }

       public void deleteProductImage(Integer id){
        productImgRepository.deleteById(id);
       }
}
