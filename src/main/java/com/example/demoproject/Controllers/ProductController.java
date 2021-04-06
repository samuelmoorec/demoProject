package com.example.demoproject.Controllers;

import com.example.demoproject.Models.Product;
import com.example.demoproject.Repos.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ProductController {

    @Autowired
    ProductRepo productDao;

    @Value("${filestack_key}")
    private String fileStackApiKey;

    ProductController(){}

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

    @RequestMapping(path = "/keys.js", produces = "application/javascript")
    @ResponseBody
    public String apikey(){
        System.out.println(fileStackApiKey);
        return "const FileStackApiKey = `" + fileStackApiKey + "`";
    }


    @GetMapping("/product/{product_id}")
    public String vewAProduct(@PathVariable long product_id, Model model){
        Product productToView = productDao.getOne(product_id);
        model.addAttribute("product",productToView);
        return "product/one";
    }

    @GetMapping("/product/{product_id}/edit")
    public String editAProduct(@PathVariable long product_id, Model model){
        model.addAttribute("product", productDao.getOne(product_id));
        return "product/edit";
    }

    @PostMapping("/product/{product_id}/edit")
    public String EditAProduct(
            @PathVariable long product_id,
            @RequestParam(name = "product_name") String productName,
            @RequestParam(name = "product_description") String productDescription,
            @RequestParam(name = "product_price") float productPrice,
            @RequestParam(name = "product_image_url") String productImgUrl
    ){
        Product productToEdit = new Product(product_id,productName,productDescription,productPrice,productImgUrl);
        productDao.save(productToEdit);

        return "redirect:/product/" + product_id;
    }

    @GetMapping("/product/{product_id}/delete")
    public String deleteAProductForm(@PathVariable long product_id,Model model){
        Product productToDelete = productDao.getOne(product_id);
        model.addAttribute("product", productToDelete);
        return "product/delete";
    }

    @PostMapping("/product/{product_id}/delete")
    public String deleteAProduct(@PathVariable long product_id){
        productDao.deleteById(product_id);

        return "redirect:/products";
    }

}
