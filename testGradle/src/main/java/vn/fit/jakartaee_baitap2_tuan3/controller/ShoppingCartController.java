package vn.fit.jakartaee_baitap2_tuan3.controller;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import vn.fit.jakartaee_baitap2_tuan3.model.AddCartResponseDTO;
import vn.fit.jakartaee_baitap2_tuan3.model.CustomerBillDTO;
import vn.fit.jakartaee_baitap2_tuan3.model.ShoppingCart;
import vn.fit.jakartaee_baitap2_tuan3.service.ShoppingCartService;

import java.util.List;
import java.util.Map;

@RequestScoped
@Path("/carts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ShoppingCartController {

    @Inject
    private ShoppingCartService cartService;

    @GET
    public Response getAll() {
        return Response.ok(cartService.getAllCarts()).build();
    }

    @GET
    @Path("/bill/{customerName}")
    public Response getBillByCustomer(@PathParam("customerName") String customerName) {
        CustomerBillDTO bill = cartService.calculateCustomerBill(customerName);
        return Response.ok(bill).build();
    }

    @POST
    @Path("/add-with-cap")
    public Response addWithCap(ShoppingCart cart) {
        try {
            AddCartResponseDTO result = cartService.addItemToCart(cart);
            return Response.status(Response.Status.CREATED).entity(result).build();
        } catch (IllegalArgumentException | IllegalStateException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", e.getMessage()))
                    .build();
        }
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") int id) {
        ShoppingCart cart = cartService.getCartById(id);
        if (cart == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("{\"message\": \"Cartitem not found\"}").build();
        }
        return Response.ok(cart).build();
    }

    @POST
    public Response create(ShoppingCart cart) {
        boolean success = cartService.createCart(cart);
        if (success) {
            return Response.status(Response.Status.CREATED).entity("{\"message\": \"Cart item created\"}").build();
        }
        return Response.status(Response.Status.BAD_REQUEST).entity("{\"message\":\"Failed to create cart item\"}").build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") int id, ShoppingCart cart) {
        cart.setId(id);
        boolean success = cartService.updateCart(cart);
        if (success) {
            return Response.ok("{\"message\": \"Cart item updated\"}").build();
        }
        return Response.status(Response.Status.NOT_MODIFIED).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") int id) {
        boolean success = cartService.deleteCart(id);
        if (success) {
            return Response.ok("{\"message\": \"Cart item deleted\"}").build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
