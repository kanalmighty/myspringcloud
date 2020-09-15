package cloudalibaba.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {

    @Value("${server.port}")
    String serverPort;
    @RequestMapping("/payment/{id}")
    public String getPayment(@PathVariable("id") Integer id){
        return serverPort;

    }
}
