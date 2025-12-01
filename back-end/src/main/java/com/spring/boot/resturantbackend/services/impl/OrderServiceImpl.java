package com.spring.boot.resturantbackend.services.impl;

import com.spring.boot.resturantbackend.repositories.OrderRepo;
import com.spring.boot.resturantbackend.services.OrderService;
import com.spring.boot.resturantbackend.services.ProductService;
import com.spring.boot.resturantbackend.controllers.vm.OrderVm;
import com.spring.boot.resturantbackend.controllers.vm.RequestOrderVm;
import com.spring.boot.resturantbackend.controllers.vm.ResponseOrderVm;
import com.spring.boot.resturantbackend.dto.ProductDto;
import com.spring.boot.resturantbackend.dto.security.AccountDto;
import com.spring.boot.resturantbackend.mappers.ProductMapper;
import com.spring.boot.resturantbackend.mappers.security.AccountMapper;
import com.spring.boot.resturantbackend.models.Order;

import jakarta.transaction.SystemException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private ProductService productService;
    
    @Override
    public ResponseOrderVm requestOrder(RequestOrderVm requestOrderVm) {
    	try {
//            if (Objects.nonNull(requestOrderVm.getId())) {
//                throw new SystemException("id.must_be.null");
//            }
            // check is request has products or not
            if (CollectionUtils.isEmpty(requestOrderVm.getOrders())) {
                throw new SystemException("products.ids.must_be.notNull");
            }
            
//            get account of user
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            AccountDto accountDto = (AccountDto) authentication.getPrincipal();

//			get products by id
            List<OrderVm> ordersVm = requestOrderVm.getOrders();
            List<Long> poductIds = ordersVm.stream().map(order -> order.getId()).collect(Collectors.toList());
            
            List<ProductDto> products = productService.getProductsByIds(poductIds);
            
//         get total quantity
            Long totalNumber = ordersVm.stream().mapToLong(order -> order.getQuantity()).sum(); 
            
//            Toata Price
            Double totalPrice  = calculateTotalPrice(ordersVm ,products ) ;
            
//            save in DB
            
            Order order = new Order();
            
            order.setAccount(AccountMapper.ACCOUNT_MAPPER.toAccount(accountDto));
            order.setProducts(ProductMapper.PRODUCT_MAPPER.toProduct(products));
            order.setTotalNumber(totalNumber);
            order.setTotalPrice(totalPrice);
            
//            //            code   format to 20251102
//            String code = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) 
//                    + "-" 
//                    + System.currentTimeMillis(); // guarantees uniqueness
//		    order.setCode(code);
//		            
            order =  orderRepo.save(order);
            
            // make update on code
            String codeNext =order.getCode() + "-RES-" + order.getId();
            order.setCode(codeNext);
            order =orderRepo.save(order);
            
            return new ResponseOrderVm(order.getCode() , order.getTotalPrice() , order.getTotalNumber() );

        } catch (SystemException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    
    
    private Double calculateTotalPrice(List<OrderVm> ordersVm ,List<ProductDto> products ){
//    	id , price
    	Map<Long , Double> productMap = products.stream().collect(
    			Collectors.toMap(ProductDto::getId, ProductDto::getPrice)
    			);
    
    	return ordersVm.stream().mapToDouble(order ->{
    		Double price = productMap.get(order.getId());
    		return price * order.getQuantity();
    		
    	}).sum();

    }
}
