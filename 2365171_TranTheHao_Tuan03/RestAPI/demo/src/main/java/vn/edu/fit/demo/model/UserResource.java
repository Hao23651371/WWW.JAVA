package vn.edu.fit.demo.model;

import jakarta.inject.Named;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;

@Named
@Path("/user")
public class UserResource {

    @GET
    @Path("/view")
    @Produces("text/json")
    public String viewUser() {
        return "Hoang Minh";
    }

    @GET
    @Path("/add/{a}/{b}")
    public int Add(@PathParam("a") int a, @PathParam("b") int b)
    {
        return a+b;
    }

    //  @GET - Lấy danh sách TẤT CẢ User
    @GET//  Đường dẫn mặc định của API sẽ là /user
    @Produces(MediaType.APPLICATION_JSON) // Khai báo dữ liệu trả về là JSON
    public Response getAllUsers() {
// tạo danh sách user
// thay đổi danh sách từ Database
        List<User> userList = new ArrayList<>();
        userList.add(new User(1, "Mai Hoàng", "hoang@gmail.com"));
        userList.add(new User(2, "Lâm Tòng", "tong@gmail.com"));
        System.out.println("Lấy danh sách toàn bộ user");
// Trả về HTTP status 200 (OK) kèm theo danh sách JSON
        return Response.ok(userList).build();
    }
    // @GET - Lấy thông tin MỘT User dựa vào id
    @GET
    @Path("/{id}") // Đường dẫn sẽ là /user/{id}
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserById(@PathParam("id") int id) {
// Tìm user trong Database theo ID
        System.out.println("Tìm kiếm user có ID: " + id);
        if (id == 1) {
            User user = new User(1, "Mai Hoàng", "hoang@gmail.com");
            return Response.ok(user).build();
        }
        else if(id==2){
            User user = new User(2, "Lâm Tòng", "tong@gmail.com");
            return Response.ok(user).build();
        }
        else
        {
// Nếu không tìm thấy, trả về lỗi 404 Not Found
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Không tìm thấy người dùng với ID: " + id)
                    .build();
        }
    }
    //  Nhận JSON từ Client để thêm 1 User mới
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createUser(User user) {
        // Giả lập: Gọi hàm lưu user vào Database tại đây
        System.out.println("Tạo user mới: " + user.getName());
        return Response.status(Response.Status.CREATED)
                .entity("Đã thêm người dùng thành công!")
                .build();
    }

    // Cập nhật User đã có dựa trên ID
    @PUT
    @Path("/{id}") // Đường dẫn sẽ là /user/{id}
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateUser(@PathParam("id") int id, User updatedUser) {
        // Giả lập: Tìm user theo id và cập nhật thông tin
        System.out.println("Cập nhật user ID " + id + " thành " +
                updatedUser.getName());
        return Response.ok("Đã cập nhật xong!").build();
    }

    //  Xóa User dựa trên ID
    @DELETE
    @Path("/{id}") //Đường dẫn sẽ là /user/{id}
    public Response deleteUser(@PathParam("id") int id) {
        // Giả lập: Xóa user có id tương ứng khỏi Database
        System.out.println("Đã xóa user ID: " + id);
        return Response.ok("Xóa người dùng thành công!").build();
    }

}