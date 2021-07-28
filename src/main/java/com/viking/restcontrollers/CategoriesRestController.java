package com.viking.restcontrollers;

import com.viking.dto.CategoriesDTO;
import com.viking.dto.MessageResponse;
import com.viking.entities.Category;
import com.viking.services.impl.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@RequestMapping("/api/categories")
public class CategoriesRestController {

    @Autowired
    private CategoryService categoryService;


    @GetMapping("/get/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Category getCategoryId(@PathVariable("id") String id){
        return categoryService.findCategoryById(id);
    }

    @PostMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateCategory(@RequestBody CategoriesDTO category){
        System.out.println("category = " + category.getId());
        if(category.getName() == null || category.getName().equals("")){
            return new ResponseEntity<MessageResponse>(new MessageResponse("Vui lòng nhập tên danh mục!"), HttpStatus.OK);
        }else{
            Category c = categoryService.updateCategory(category);
            if(c != null){
                return new ResponseEntity<MessageResponse>(new MessageResponse("Cập nhật không thành công!"), HttpStatus.OK);
            }else{
                return new ResponseEntity<MessageResponse>(new MessageResponse("done"), HttpStatus.OK);
            }
        }
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Category> getAllCategories(){
        return categoryService.findAll();
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createCategory(@RequestBody Category category){
        if(category.getName() == null || category.getName().equals("")){
            return new ResponseEntity<MessageResponse>(new MessageResponse("Vui lòng nhập tên danh mục!"), HttpStatus.OK);
        }else{
            Category c = categoryService.createCategory(category);
            if(c != null){
                return new ResponseEntity<MessageResponse>(new MessageResponse("done"), HttpStatus.OK);
            }else{
                return new ResponseEntity<MessageResponse>(new MessageResponse("fail"), HttpStatus.OK);
            }
        }

    }

}
