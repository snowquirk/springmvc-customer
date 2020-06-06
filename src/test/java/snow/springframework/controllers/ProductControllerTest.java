package snow.springframework.controllers;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.ResponseBody;
import snow.springframework.domain.Product;
import snow.springframework.services.ProductService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ProductControllerTest {

    @Mock //Mockito mock object
    private ProductService productService;

    @InjectMocks //setup controller and injects mock objects into it.
    private ProductController productController;

    private MockMvc mockMvc;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this); //Initialize controller and mocks
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }

    @Test
    public void testList() throws Exception{


        List<Product> products = new ArrayList<>();
        products.add(new Product());
        products.add(new Product());


        //specific Mockito interaction, tell stub to return list of products
        when(productService.listAllProducts()).thenReturn((List) products); //need to strip generics to keep Mockito happy.

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(view().name("products"))
                .andExpect(model().attribute("products", hasSize(2)));

    }

    @Test
    public void testShow() throws Exception{
        Integer id = 1;

        //Tell Mockito stub to return new product for ID 1
        when(productService.getProductById(id)).thenReturn(new Product());

        mockMvc.perform(get("/product/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("product"))
                .andExpect(model().attribute("product", instanceOf(Product.class)));
    }

    @Test
    public void testEdit() throws Exception {
        Integer id = 1;
        when(productService.getProductById(id)).thenReturn(new Product());

        mockMvc.perform(get("/product/edit/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("productform"))
                .andExpect(model().attribute("product", instanceOf(Product.class) ));
    }

    @Test
    public void testNewProduct() throws Exception {
        Integer id = 1;

        //not calling service
        verifyZeroInteractions(productService);

        mockMvc.perform(get("/product/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("productform"))
                .andExpect(model().attribute("product", instanceOf(Product.class)));


    }
//    @Test
//    public void testSaveOrUpdate() throws Exception {
//        Integer id = 1;
//        String description = "Test Description";
//        BigDecimal price = new BigDecimal("9.99");
//        String imageUrl = "image.com";
//
//        Product product = new Product();
//        product.setId(id);
//        product.setDescription(description);
//        product.setPrice(price);
//        product.setImageUrl(imageUrl);
//
//        when(productService.saveOrUpdateProduct(Matchers.<Product>any())).thenReturn(product);
//
//        mockMvc.perform(post("/product")
//                .param("id", "1")
//                .param("description", description)
//                .param("price", "9.99")
//                .param("imageUrl", "image.com"))
//                .andExpect(status().is3xxRedirection())
//                .andExpect(view().name("redirect:/product/1"))
//                .andExpect(model().attribute("product", instanceOf(Product.class)))
//                .andExpect(model().attribute("product", hasProperty("id", is(id))))
//                .andExpect(model().attribute("product", hasProperty("description", is(description))))
//                .andExpect(model().attribute("product", hasProperty("price", is(price))))
//                .andExpect(model().attribute("product", hasProperty("imageUrl", is(imageUrl))));
//
//        //verify properties of bound object
//        ArgumentCaptor<Product> boundProduct = ArgumentCaptor.forClass(Product.class);
//        verify(productService).saveOrUpdateProduct(boundProduct.capture());
//
//        assertEquals(id, boundProduct.getValue().getId());
//        assertEquals(description, boundProduct.getValue().getDescription());
//        assertEquals(price, boundProduct.getValue().getPrice());
//        assertEquals(imageUrl, boundProduct.getValue().getImageUrl());
//    }

    @Test
    public void testDelete() throws Exception{
        Integer id = 1;

        mockMvc.perform(get("/product/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/products"));

        verify(productService, times(1)).deleteProduct(id);
    }


}
