package com.iscweb.persistence.repositories

import com.iscweb.common.model.dto.ProjectionDto
import com.iscweb.persistence.common.DtoLevel1
import com.iscweb.persistence.common.TestDtoWithChildren
import com.iscweb.persistence.repositories.impl.BaseRepositoryWithProjectionsImpl
import org.hibernate.query.criteria.internal.path.SingularAttributePath

import javax.persistence.criteria.Root
import javax.persistence.criteria.Selection
import java.util.stream.Stream

class TestSchoolRepositoryWithProjectionsImpl2  extends BaseRepositoryWithProjectionsImpl<TestDtoWithChildren, DtoLevel1> {
    TestSchoolRepositoryWithProjectionsImpl2() {
        super(DtoLevel1.class)
    }

    String generateAlias(SingularAttributePath path) {
        super.generateAlias(path)
    }

    List<DtoLevel1> transformResults(Stream<Object[]> resultsStream, List<Selection<?>> columnsSelection) {
        super.transformResults(resultsStream, columnsSelection)
    }

    List<Selection<?>> createProjection(Root<?> metadata, List<ProjectionDto> columns) {
        super.createProjection(metadata, columns)
    }
}
