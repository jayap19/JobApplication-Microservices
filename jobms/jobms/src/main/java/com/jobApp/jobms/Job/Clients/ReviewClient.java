package com.jobApp.jobms.Job.Clients;

import com.jobApp.jobms.Job.external.Review;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "review-service", url = "${review-service.url}")
public interface ReviewClient {
    @GetMapping("/reviews")
    List<Review> getReviews(@RequestParam("companyId") Long companyId);
}
