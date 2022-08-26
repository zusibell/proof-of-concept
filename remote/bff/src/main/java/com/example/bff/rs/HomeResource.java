package com.example.bff.rs;

import com.example.bff.domain.OverviewData;
import com.example.bff.service.OverviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@Component
@RestController
@CrossOrigin(origins = "http://localhost:4100", allowedHeaders = "*")
@RequestMapping("/api/home")
public class HomeResource {

    private final OverviewService overviewService;

    @PreAuthorize("hasRole('REMOTE_READ_ALL')")
    @GetMapping("/overview")
    public List<OverviewData> helloWorld() throws Exception {
        return overviewService.getOverviewData();
    }
}
