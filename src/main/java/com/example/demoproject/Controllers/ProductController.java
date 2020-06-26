package com.example.demoproject.Controllers;

import com.example.demoproject.Models.Product;
import com.example.demoproject.Repos.ProductRepo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ProductController {

    ProductRepo productDao;

    ProductController(ProductRepo productDao){
        this.productDao = productDao;
    }

    @GetMapping("/products")
    public String viewAllProducts(Model model){
        List<Product> currentProducts = productDao.findAll();
        model.addAttribute("products", currentProducts);
        return "product/viewAll";
    }

    @GetMapping("/product/create")
    public String createAProductView(){
        return "product/create";
    }

    @PostMapping("/product/create")
    public String createAProduct(
            @RequestParam(name = "product_name") String productName,
            @RequestParam(name = "product_description") String productDescription,
            @RequestParam(name = "product_price") float productPrice,
            @RequestParam(name = "product_image_url") String productImgUrl
    ){
        Product productToAdd = new Product(productName,productDescription,productPrice,productImgUrl);
        Product productInDB = productDao.save(productToAdd);

        return "redirect:/product/" + productInDB.getId();
    }


    @GetMapping("/product/{product_id}")
    public String vewAProduct(@PathVariable long product_id, Model model){
        Product productToView = productDao.getOne(product_id);
        model.addAttribute("product",productToView);
        return "product/one";
    }

    @GetMapping("/product/{product_id}/edit")
    public String editAProduct(@PathVariable long product_id){
        return "product/edit";
    }

    @GetMapping("/product/{product_id}/delete")
    public String deleteAProduct(@PathVariable long product_id){
        return "product/delete";
    }

}
