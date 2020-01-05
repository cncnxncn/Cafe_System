package Excel;

public class XlsxVO {
	
	private String productName;
	private String productUnitPrice;
	private String productStock;
	
	public XlsxVO(String []result) {
		this.productName = result[0];
		this.productUnitPrice = "¥‹¿ß(∞≥)";
		this.productStock = result[1];
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductUnitPrice() {
		return productUnitPrice;
	}

	public void setProductUnitPrice(String productUnitPrice) {
		this.productUnitPrice = productUnitPrice;
	}

	public String getProductStock() {
		return productStock;
	}

	public void setProductStock(String productStock) {
		this.productStock = productStock;
	}
	
}
