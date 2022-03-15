/**
 * 
 */
package com.example.demo.resource;

import java.util.List;


import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import com.example.demo.transformer.Message;
import com.example.demo.transformer.UserDTO;
import com.example.demo.transformer.UserRegisterRequest;

import lombok.NoArgsConstructor;

import static com.example.demo.common.Constants.ERROR;
import static com.example.demo.common.Constants.INVALID_REQUEST;
import static com.example.demo.common.Constants.INVALID_USER_NAME;
import static com.example.demo.common.Constants.INVALID_USER_EMAIL;
import static com.example.demo.common.Constants.INVALID_USER_PASSWORD;
import static com.example.demo.common.Constants.INVALID_PASSWORD_LENGTH;

/**
 * @author PalaniRajan
 *
 */
@Path("/user")
@NoArgsConstructor
public class UserResource {
	
	@Autowired
	private  UserService service;

	@POST
	@Path("/add")
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	public Message saveUser(final UserRegisterRequest request) {
		Message message = isNullorEmpty(request);
		if (null == message) {
			User user = service.saveUser(request);
			if (null != user) {
				message = Message.builder().messageCode("S")
						.message("User Saved successfully. User Id " + user.getUserId()).build();
			}
		}
		return message;

	}

	
	@GET
	@Path("/all")
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	public List<UserDTO> fetchUserList() {
		return service.fetchAllUser();
	}
	
	@GET
	@Path("/{id}/get")
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	public User fetchUserById(@PathParam("id") Long id) {
		return service.findUserById(id);
	}
	
	private Message isNullorEmpty(UserRegisterRequest request) {
		Message message = null;
		if (null == request) {
			message = Message.builder().messageCode(ERROR).message(INVALID_REQUEST).build();
		} else if (null == request.getUserName() || "".equals(request.getUserName())) {
			message = Message.builder().messageCode(ERROR).message(INVALID_USER_NAME).build();
		} else if (null == request.getUserEmail() || "".equals(request.getUserEmail())) {
			message = Message.builder().messageCode(ERROR).message(INVALID_USER_EMAIL).build();
		}else if (null == request.getUserPassword() || "".equals(request.getUserPassword())) {
			message = Message.builder().messageCode(ERROR).message(INVALID_USER_PASSWORD).build();
		} else if (request.getUserPassword().length() < 8) {
			message = Message.builder().messageCode(ERROR).message(INVALID_PASSWORD_LENGTH).build();
		}
		return message;
	}
	
	@DELETE
	@Path("{id}/delete")
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	public Message deleteUser(@PathParam(value = "id") final Long id) {
		service.deleteUserById(id);
			
		Message message=new Message("S", "Delete");
		return message;

	}
}
