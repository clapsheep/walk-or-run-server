package com.wor.dash.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Setter
@Getter
@ToString
@NoArgsConstructor
public class ApiResponse {
	private String message; 
	private String text;
	private int code;
}
