package com.iscweb.persistence.repositories

import com.iscweb.common.model.entity.ISchool
import com.iscweb.persistence.model.jpa.SchoolJpa
import com.iscweb.persistence.repositories.impl.BaseRepositoryWithProjectionsImpl
import org.hibernate.query.criteria.internal.path.SingularAttributePath

import javax.persistence.criteria.Selection
import java.util.stream.Stream

class TestSchoolRepositoryWithProjectionsImpl extends BaseRepositoryWithProjectionsImpl<ISchool, SchoolJpa> {
    TestSchoolRepositoryWithProjectionsImpl() {
        super(SchoolJpa.class)
    }

    String generateAlias(SingularAttributePath path) {
        super.generateAlias(path)
    }

    List<SchoolJpa> transformResults(Stream<Object[]> resultsStream, List<Selection<?>> columnsSelection) {
        super.transformResults(resultsStream, columnsSelection)
    }
}