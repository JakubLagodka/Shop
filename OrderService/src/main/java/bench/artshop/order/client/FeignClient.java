package bench.artshop.order.client;


@org.springframework.cloud.openfeign.FeignClient("order-service-eureka-client")
public interface FeignClient {
}
