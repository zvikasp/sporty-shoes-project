package form;

import org.springframework.web.multipart.MultipartFile;

import entity.Product;

public class ProductForm {
    private String code;
    private String name;
    private double price;
    private boolean newProduct = false;
    private MultipartFile fileData;

    public ProductForm() {
        this.newProduct= true;
    }
    
    public ProductForm(Product product) {
        this.code = product.getCode();
        this.name = product.getName();
        this.price = product.getPrice();
    }

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public double getPrice() {
		return price;
	}

	public boolean isNewProduct() {
		return newProduct;
	}

	public MultipartFile getFileData() {
		return fileData;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public void setNewProduct(boolean newProduct) {
		this.newProduct = newProduct;
	}

	public void setFileData(MultipartFile fileData) {
		this.fileData = fileData;
	}
}
