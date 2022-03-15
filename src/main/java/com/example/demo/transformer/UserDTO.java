package com.example.demo.transformer;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO extends Message implements Serializable {
private String userName;
private String userEmail;
private Date lastLoginDate;
}
