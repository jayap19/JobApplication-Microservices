package com.jobApp.companyms.Company.messaging;

import com.jobApp.companyms.Company.dto.ReviewMessage;
import com.jobApp.companyms.Company.impl.CompanyServiceImpl;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class ReviewMessageConsumer {
     private CompanyServiceImpl companyServiceImpl;

    public ReviewMessageConsumer(CompanyServiceImpl companyServiceImpl) {
        this.companyServiceImpl = companyServiceImpl;
    }

    @RabbitListener(queues = "companyRatingQueue")
    public void consumeMessage(ReviewMessage reviewMessage){
        companyServiceImpl.updateCompanyRating(reviewMessage);
    }

}
