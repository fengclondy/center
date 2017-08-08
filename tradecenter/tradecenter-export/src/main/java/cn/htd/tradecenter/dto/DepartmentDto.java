package cn.htd.tradecenter.dto;


public class DepartmentDto {
	private String departmentCode;//部门编号
	private String departmentName;//部门名称
	private String productBrand;//品牌
    private Double realPrice;//实际金额
    private Double creditLimit;//信用额度
    private Double usePrice;//可用额度
    private String accountType;//类别
    
	public Double getRealPrice() {
		return realPrice;
	}
	public void setRealPrice(Double realPrice) {
		this.realPrice = realPrice;
	}
	public Double getCreditLimit() {
		return creditLimit;
	}
	public void setCreditLimit(Double creditLimit) {
		this.creditLimit = creditLimit;
	}
	public Double getUsePrice() {
		return usePrice;
	}
	public void setUsePrice(Double usePrice) {
		this.usePrice = usePrice;
	}
	public String getDepartmentCode() {
		return departmentCode;
	}
	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public String getProductBrand() {
		return productBrand;
	}
	public void setProductBrand(String productBrand) {
		this.productBrand = productBrand;
	}
	
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
    
    
    
    
}
