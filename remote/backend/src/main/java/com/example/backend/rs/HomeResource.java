package com.example.backend.rs;

import com.example.backend.domain.OverviewData;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/home")
public class HomeResource {

    @RolesAllowed("REMOTE_READ_ALL")
    @GetMapping("/overview")
    public List<OverviewData> helloWorld() {
        var overviewData = new ArrayList<OverviewData>();
        var user1 = new OverviewData();
        user1.setCity("Frankfurt");
        user1.setCountry("Germany");

        var user2 = new OverviewData();
        user2.setCity("Montreal");
        user2.setCountry("Canada");

        overviewData.add(user1);
        overviewData.add(user2);
        return overviewData;
    }
}
