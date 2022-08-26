package com.example.bff.service;


import com.example.bff.domain.OverviewData;

import java.util.List;

public interface OverviewService {

    List<OverviewData> getOverviewData() throws Exception;
}
