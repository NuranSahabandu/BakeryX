// Update the ProductController.java class
package com.bakery.productmanagement.controller;

import com.bakery.productmanagement.model.Product;
import com.bakery.productmanagement.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String listProducts(Model model, @RequestParam(required = false) String keyword,
                               @RequestParam(required = false) String category) {
        model.addAttribute("products", productService.searchProducts(keyword, category));
        model.addAttribute("keyword", keyword);
        model.addAttribute("category", category);
        return "product-list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("product", new Product() {
            @Override
            public String displayDetails() {
                return "";
            }
        });
        return "product-add";
    }

    @PostMapping("/add")
    public String addProduct(@RequestParam String name,
                             @RequestParam double price,
                             @RequestParam String type,
                             @RequestParam(required = false, defaultValue = "false") Boolean available,
                             @RequestParam(required = false, defaultValue = "") String description,
                             RedirectAttributes redirectAttributes) {
        try {
            System.out.println("Adding product: " + name + ", Type: " + type + ", Available: " + available);
            productService.createProduct(name, price, type, available, description);
            redirectAttributes.addFlashAttribute("message", "Product added successfully!");
            return "redirect:/products";
        } catch (IllegalArgumentException e) {
            System.err.println("Error adding product: " + e.getMessage());
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/products/add";
        }
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable String id, Model model, RedirectAttributes redirectAttributes) {
        Product product = productService.getProductById(id);
        if (product == null) {
            redirectAttributes.addFlashAttribute("error", "Product not found");
            return "redirect:/products";
        }
        model.addAttribute("product", product);
        return "product-edit";
    }

    @PostMapping("/edit/{id}")
    public String updateProduct(@PathVariable String id,
                                @RequestParam String name,
                                @RequestParam double price,
                                @RequestParam(required = false, defaultValue = "false") boolean available,
                                @RequestParam(required = false, defaultValue = "") String description,
                                RedirectAttributes redirectAttributes) {
        try {
            productService.updateProduct(id, name, price, available, description);
            redirectAttributes.addFlashAttribute("message", "Product updated successfully!");
            return "redirect:/products";
        } catch (IllegalArgumentException e) {
            System.err.println("Error updating product: " + e.getMessage());
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/products/edit/" + id;
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable String id, RedirectAttributes redirectAttributes) {
        try {
            System.out.println("Deleting product with ID: " + id);
            productService.deleteProduct(id);
            redirectAttributes.addFlashAttribute("message", "Product deleted successfully!");
        } catch (IllegalArgumentException e) {
            System.err.println("Error deleting product: " + e.getMessage());
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/products";
    }
}