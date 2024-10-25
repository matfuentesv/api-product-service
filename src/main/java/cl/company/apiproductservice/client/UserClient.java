package cl.company.apiproductservice.client;


import cl.company.apiproductservice.exception.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(name = "api-user", url = "${api.user.url}")
public interface UserClient {

    @PostMapping("/api/login")
    ApiResponse  login(@RequestHeader String username,
                       @RequestHeader String password);

}
