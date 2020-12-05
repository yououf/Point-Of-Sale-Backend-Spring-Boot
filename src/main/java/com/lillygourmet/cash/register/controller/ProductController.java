/*
 * Caisse Enregistreuse App - API REST
 * COded By Alias King - Younes OUFRID !!
 * Mail : oufridyounes@gmail.com
 * MNS team coders
 * */

package com.lillygourmet.cash.register.controller;
/**
 * Product Controller
 *
 * @author Alias King - Younes OUFRID
 */

import com.lillygourmet.cash.register.Exception.ResourceNotFoundException;
import com.lillygourmet.cash.register.model.Category;
import com.lillygourmet.cash.register.model.Product;
import com.lillygourmet.cash.register.model.SubCategory;
import com.lillygourmet.cash.register.repository.ProductRepository;
import com.lillygourmet.cash.register.service.ProductService;
import com.lillygourmet.cash.register.service.SubCategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.BasicJsonParser;
import org.springframework.boot.json.JsonParser;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class ProductController {

	private static Logger _log = LoggerFactory.getLogger(ProductController.class);

	@Autowired
	private ProductService ProductService;
	@Autowired
	private SubCategoryService subCategoryService;
	@Autowired
	private ProductRepository productRepository;
	@GetMapping("api/Products")
	public ResponseEntity<List<Product>> retrieveAllProducts() {
		_log.info("retrieve all Products controller...!");
		List<Product> Products = ProductService.retrieveAllProducts();
		return new ResponseEntity<List<Product>>(Products, new HttpHeaders(), HttpStatus.OK);
	}


	@GetMapping("api/Products/subCategory={id}")
	public ResponseEntity<List<Product>> retrieveProductsBySubCategory(@PathVariable Long id) {

		_log.info("retrieve SubCategory by category controller...!");
		List<Product> Products = ProductService.retrieveAllBySubCategory(id);
		return new ResponseEntity<List<Product>>(Products, new HttpHeaders(), HttpStatus.OK);
	}

	@GetMapping("api/Products/{id}")
	public ResponseEntity<Product> retrieveProductById(@PathVariable Long id) {
		_log.info("retrieve Product controller...!");
		Product Product = ProductService.getProduct(id);
		return new ResponseEntity<Product>(Product, new HttpHeaders(), HttpStatus.OK);
	}

	@PostMapping("api/Products")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Product> createProduct(@RequestBody String Product) {
		JsonParser parser = new BasicJsonParser();
		Map<String, Object> product = parser.parseMap(Product);
		// mapping of product object
		Product p = new Product(product.get("nom").toString(),product.get("description").toString(),product.get("codebarre").toString(),Float.parseFloat(product.get("pu").toString()),product.get("etatexiste").toString(),product.get("codecolor").toString(),subCategoryService.getSubCategory(Long.parseLong(product.get("subcategory_id").toString())),Boolean.parseBoolean(product.get("display").toString()));
		_log.info("create Product controller...!");
		Product savedProduct = ProductService.createProduct(p);
		return new ResponseEntity<Product>(savedProduct, new HttpHeaders(), HttpStatus.CREATED);
	}

	@DeleteMapping("api/Products/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public HttpStatus deleteProduct(@PathVariable Long id) throws Exception {

		_log.info("delete Product controller...!");
		ProductService.deleteProduct(id);
		return HttpStatus.ACCEPTED;
	}

	@PutMapping("api/Products")
	public ResponseEntity<Product> updateProduct(@RequestBody Product Product) {

		_log.info("update Product controller...!");
		Product updatedProduct= ProductService.updateProduct(Product);
		return new ResponseEntity<Product>(updatedProduct, new HttpHeaders(), HttpStatus.ACCEPTED);
	}

	// Updating Product
	@PutMapping("api/Products/{id}")
	public ResponseEntity<Product> updateSessionPOS(@PathVariable Long id, @RequestBody String Product)throws ResourceNotFoundException {
		Product prod = productRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Product not found for this id :: " + id));
		JsonParser parser = new BasicJsonParser();
		Map<String, Object> ProductPOSJson = parser.parseMap(Product);
		// Mapping Product object by JSON strategy
	//--->  prod.setSubCategory(subCategoryService.getSubCategory(Long.parseLong(ProductPOSJson.get("SubCategory_id").toString())));
		prod.setNom(ProductPOSJson.get("Nom").toString());
		prod.setDescription(ProductPOSJson.get("Description").toString());
		prod.setPu(Float.parseFloat(ProductPOSJson.get("Pu").toString()));
		prod.setEtatexiste(ProductPOSJson.get("EtatExiste").toString());
		prod.setCodecolor(ProductPOSJson.get("CodeColor").toString());
		prod.setCodebarre(ProductPOSJson.get("CodeBarre").toString());
		prod.setDisplay(Boolean.parseBoolean(ProductPOSJson.get("display").toString()));
		Product updatedProduct = ProductService.updateProduct(prod);

		_log.info("update Product controller...!");
		return new ResponseEntity<Product>(updatedProduct, new HttpHeaders(), HttpStatus.ACCEPTED);
	}
}
