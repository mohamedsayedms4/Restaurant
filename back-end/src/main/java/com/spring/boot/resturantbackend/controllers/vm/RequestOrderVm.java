package com.spring.boot.resturantbackend.controllers.vm;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Schema(
		name= "RequestOrderVm",
		description = "Save order related to user with products"
)
public class RequestOrderVm {
	@Schema(
			description = "This list is OrderVm"
	)
    List<OrderVm> orders;
}
