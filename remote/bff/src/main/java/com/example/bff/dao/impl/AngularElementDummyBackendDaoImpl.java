package com.example.bff.dao.impl;

import com.example.bff.dao.AngularElementDummyBackendDao;
import com.example.bff.domain.OverviewData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class AngularElementDummyBackendDaoImpl extends AbstractJsonRestDao implements AngularElementDummyBackendDao {

    public AngularElementDummyBackendDaoImpl() {
        super("angular-element-dummy-backend");
        setSendBearerToken(true);
    }

    @Override
    public List<OverviewData> getOverviewData() throws Exception {
        return findCollectionResource(OverviewData.class, "home/overview").stream().toList();
    }
}
