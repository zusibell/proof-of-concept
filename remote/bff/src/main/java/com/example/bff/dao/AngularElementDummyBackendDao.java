package com.example.bff.dao;


import com.example.bff.domain.OverviewData;

import java.util.List;

public interface AngularElementDummyBackendDao {

    List<OverviewData> getOverviewData() throws Exception;
}
