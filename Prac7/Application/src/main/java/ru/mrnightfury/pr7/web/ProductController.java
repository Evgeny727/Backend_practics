package ru.mrnightfury.pr7.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.mrnightfury.pr7.db.model.UserRole;
import ru.mrnightfury.pr7.db.repository.BookRepository;
import ru.mrnightfury.pr7.db.repository.ProductRepository;
import ru.mrnightfury.pr7.db.repository.TelephoneRepository;
import ru.mrnightfury.pr7.db.model.Book;
import ru.mrnightfury.pr7.db.model.Product;
import ru.mrnightfury.pr7.db.model.Telephone;
import ru.mrnightfury.pr7.db.repository.UserRepository;
import ru.mrnightfury.pr7.web.filters.AuthFilter;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

	private final UserRepository userRepository;
	private final ProductRepository<Product> productRepository;
	private final BookRepository bookRepository;
	private final TelephoneRepository telephoneRepository;

	@Autowired
	public ProductController(ProductRepository<Product> productRepository,BookRepository bookRepository,
							 TelephoneRepository telephoneRepository, UserRepository userRepository) {
		this.productRepository = productRepository;
		this.bookRepository = bookRepository;
		this.telephoneRepository = telephoneRepository;
		this.userRepository = userRepository;
	}

	@GetMapping("/all")
	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}

	@PostMapping("/books")
	@AuthFilter(userRole = UserRole.SELLER)
	public Book createBook(@RequestBody Book book, @RequestAttribute Long user) {
		book.setSeller(user);
		return bookRepository.save(book);
	}

	@PostMapping("/telephones")
	@AuthFilter(userRole = UserRole.SELLER)
	public Telephone createTelephone(@RequestBody Telephone telephone, @RequestAttribute Long user) {
		telephone.setSeller(user);
		return telephoneRepository.save(telephone);
	}

	@GetMapping("/{id}")
	public Product getProductById(@PathVariable Long id) {
		return productRepository.findById(id).orElse(null);
	}

	@GetMapping("/books/{id}")
	public Book getBookById(@PathVariable Long id) {
		return bookRepository.findById(id).orElse(null);
	}

	@GetMapping("/telephones/{id}")
	public Telephone getTelephoneById(@PathVariable Long id) {
		return telephoneRepository.findById(id).orElse(null);
	}

	@PutMapping("/books/{id}")
	@AuthFilter(userRole = UserRole.SELLER)
	public Book updateBook(@PathVariable Long id, @RequestBody Book book, @RequestAttribute Long user) {
		if (bookRepository.existsById(id)) {
			Book b = bookRepository.getReferenceById(id);
			if (b.getSeller() == user) {
				book.setId(id);
				book.setSeller(user);
				return bookRepository.save(book);
			}
		}
		return null;
	}

	@PutMapping("/telephones/{id}")
	@AuthFilter(userRole = UserRole.SELLER)
	public Telephone updateTelephone(@PathVariable Long id, @RequestBody Telephone telephone,
									 @RequestAttribute Long user) {
		if (telephoneRepository.existsById(id)) {
			Telephone b = telephoneRepository.getReferenceById(id);
			if (b.getSeller() == user) {
				telephone.setId(id);
				telephone.setSeller(user);
				return telephoneRepository.save(telephone);
			}
		}
		return null;
	}

	@DeleteMapping("/books/{id}")
	@AuthFilter(userRole = UserRole.SELLER)
	public void deleteBook(@PathVariable Long id, @RequestAttribute Long user) {
		if (bookRepository.existsById(id)) {
			Book b = bookRepository.getReferenceById(id);
			if (b.getSeller() == user) {
				bookRepository.deleteById(id);
			}
		}
	}

	@DeleteMapping("/telephones/{id}")
	@AuthFilter(userRole = UserRole.SELLER)
	public void deleteTelephone(@PathVariable Long id, @RequestAttribute Long user) {
		if (telephoneRepository.existsById(id)) {
			Telephone b = telephoneRepository.getReferenceById(id);
			if (b.getSeller() == user) {
				telephoneRepository.deleteById(id);
			}
		}
	}
}