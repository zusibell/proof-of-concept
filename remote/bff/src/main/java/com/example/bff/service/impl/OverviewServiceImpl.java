package com.example.bff.service.impl;

import com.example.bff.dao.AngularElementDummyBackendDao;
import com.example.bff.domain.OverviewData;
import com.example.bff.service.OverviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OverviewServiceImpl implements OverviewService {

    private final AngularElementDummyBackendDao angularElementDummyBackendDao;

    @Override
    public List<OverviewData> getOverviewData() throws Exception {
        return angularElementDummyBackendDao.getOverviewData();
    }
}
