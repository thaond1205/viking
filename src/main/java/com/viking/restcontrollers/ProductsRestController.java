package com.viking.restcontrollers;

import com.viking.dto.MessageResponse;
import com.viking.dto.ProductsDTO;
import com.viking.dto.UpdateProductDTO;
import com.viking.entities.Product;
import com.viking.repositories.CategoryRepository;
import com.viking.repositories.ProductsRepository;
import com.viking.services.impl.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@RequestMapping("/api/products")
public class ProductsRestController {
    @Autowired
    private ProductsRepository productsRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductsService productsService;

    public String nameImage = null;


    @PostMapping(value="/create")
    @PreAuthorize("hasRole('ADMIN')")
    public MessageResponse CreateProducts(@RequestBody ProductsDTO productsDTO) {

        if(productsDTO.getName() == null || productsDTO.getName().equals("")){
            return new MessageResponse("Vui lòng nhập tên sản phẩm");
        }else if(productsDTO.getPrice() == null || productsDTO.getPrice().equals("")) {
            return new MessageResponse("Vui lòng nhập giá sản phẩm");
        }else if(productsDTO.getAvailable() == null || productsDTO.getAvailable().equals("")){
            return new MessageResponse("Vui lòng chọn trạng thái hoặt động của sản phẩm");
        }else if(productsDTO.getCategoryId() == null || productsDTO.getCategoryId().equals("")){
            return new MessageResponse("Vui lòng chọn danh mục sản phẩm");
        }else{
            if(nameImage != null) {
                productsDTO.setImage(nameImage);
                ProductsDTO pro = productsService.createProducts(productsDTO);
                if(pro != null){
                    nameImage = null;
                    return new MessageResponse("done");
                }else{
                    return new MessageResponse("faill");
                }
            }
            return null;
        }

    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> authenAdminProduct(){
        return new ResponseEntity<MessageResponse>(new MessageResponse("success"), HttpStatus.OK);
    }


    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Product> getAllProducts(){
        return productsService.findAllProducts();
    }


    @PostMapping(value="/upload")
    public ResponseEntity<?> upload(@RequestPart("image") MultipartFile image) throws Exception{

        try{
            File convertFile = new File("D:\\Spring Java 6\\viking\\src\\main\\resources\\static\\testimage\\"+image.getOriginalFilename());
            convertFile.createNewFile();
            nameImage = image.getOriginalFilename();
            FileOutputStream fout = new FileOutputStream(convertFile);
            fout.write(image.getBytes());
            fout.close();
            return new ResponseEntity<MessageResponse>( new MessageResponse("done"), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<MessageResponse>(new MessageResponse("Vui lòng chọn ảnh sản phẩm"), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("getone/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public UpdateProductDTO getProductById(@PathVariable("id") Integer id){
        return productsService.findProductRestById(id);
    }

    @PostMapping("update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateProduct(@RequestBody UpdateProductDTO entity){
        if(entity.getName() == null || entity.getName().equals("")){
            return new ResponseEntity<MessageResponse>(new MessageResponse("Vui lòng nhập tên sản phẩm!"),HttpStatus.OK);
        }else if(entity.getPrice() == null || entity.getPrice().equals("")) {
            return new ResponseEntity<MessageResponse>(new MessageResponse("Vui lòng nhập giá sản phẩm!"),HttpStatus.OK);
        }else if(entity.getAvailable() == null || entity.getAvailable().equals("") || entity.getAvailable().equals("3")){
            return new ResponseEntity<MessageResponse>(new MessageResponse("Vui lòng chọn trạng thái sản phẩm!"),HttpStatus.OK);
        }else if(entity.getCategoryId() == null || entity.getCategoryId().equals("") || entity.getCategoryId().equals("3")){
            return new ResponseEntity<MessageResponse>(new MessageResponse("Vui lòng chọn danh mục sản phẩm!"),HttpStatus.OK);
        }else {
            Product product = productsService.updateProduct(entity);
            if (product == null) {
                return new ResponseEntity<MessageResponse>(new MessageResponse("Cập nhật không thành công!"), HttpStatus.OK);
            } else {
                return new ResponseEntity<MessageResponse>(new MessageResponse("done"), HttpStatus.OK);
            }
        }

    }
}
