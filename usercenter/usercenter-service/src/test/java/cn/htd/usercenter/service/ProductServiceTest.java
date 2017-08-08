package cn.htd.usercenter.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.usercenter.dto.ProductDTO;

@Ignore
public class ProductServiceTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceTest.class);
    ApplicationContext ctx = null;
    ProductService productService = null;

    @Before
    public void setUp() {
        ctx = new ClassPathXmlApplicationContext("classpath*:/test.xml");
        productService = (ProductService) ctx.getBean("productService");
    }

    // @Test
    // public void testAddProduct() {
    // ExecuteResult<Boolean> res = productService.addProduct("1", "test",
    // "testProduct", "2");
    // Assert.assertTrue(res.isSuccess());
    // }

    @Test
    public void testQueryProduct() {
        ExecuteResult<DataGrid<ProductDTO>> res = productService.queryProductByIdAndName("1", "test", 1, 20);
        Assert.assertTrue(res.isSuccess());
    }

    // @Test
    // public void testUpdateProduct() {
    // ExecuteResult<Boolean> res = productService.updateProduct("1",
    // "test", "description", "2");
    // Assert.assertTrue(res.isSuccess());
    // }

}
