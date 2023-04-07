package com.iscweb.persistence.repositories

import com.iscweb.common.model.dto.ProjectionDto
import com.iscweb.persistence.common.TestSingularAttribute
import org.hibernate.query.criteria.internal.path.SingularAttributePath
import spock.lang.Specification
import spock.lang.Unroll

import javax.persistence.criteria.Root

class AbstractRepositoryWithProjectionsImplSpec extends Specification {

    def testRepository = new TestSchoolRepositoryWithProjectionsImpl()
    def testRepository2 = new TestSchoolRepositoryWithProjectionsImpl2();

    def "Test alias generation"() {
        given: "create Hibernate Selection 3 levels tree"
            def topParent = Mock(SingularAttributePath)
            topParent.getAttribute() >> new TestSingularAttribute('school')

            def middleParent = Mock(SingularAttributePath)
            middleParent.getAttribute() >> new TestSingularAttribute('schoolDistrict')
            middleParent.getParentPath() >> topParent

            def path = Mock(SingularAttributePath)
            path.getAttribute() >> new TestSingularAttribute('id')
            path.getParentPath() >> middleParent

        when: "generate aliases for different levels of Selection tree"
            String result1 = testRepository.generateAlias(path)
            String result2 = testRepository.generateAlias(middleParent)
            String result3 = testRepository.generateAlias(topParent)

        then: "check lowest level"
            result1 != null
            result1 == 'school.schoolDistrict.id'
        and: "check middle level"
            result2 != null
            result2 == 'school.schoolDistrict'
        and: "check top level"
            result3 != null
            result3 == 'school'
    }

    @Unroll
    def "Test alias generation '#alias'"(path, alias) {
        expect:
            testRepository.generateAlias(path) == alias

        where:
            path                                            | alias
            createSelectionTree(['school', 'schoolDistrict', 'id']) | 'school.schoolDistrict.id'
            createSelectionTree(['school', 'schoolDistrict'])       | 'school.schoolDistrict'
            createSelectionTree(['school'])                         | 'school'
    }

    @Unroll
    def "Test alias generation 4 levels '#alias'"(path, alias) {
        expect:
            testRepository2.generateAlias(path) == alias

        where:
            path                                                    | alias
            createSelectionTree(['level1', 'level2', 'level3', 'level4']) | 'level1.level2.level3.level4'
    }

    def "Test results transformation 2 levels"() {
        given:
            def rawData = [
                    generateRawData([Long.valueOf(1), 'school1', Long.valueOf(1), 'district #1']),
                    generateRawData([Long.valueOf(2), 'school2', Long.valueOf(1), 'district #1']),
                    generateRawData([Long.valueOf(3), 'school3', Long.valueOf(1), 'district #1'])
            ]
            def columnsSelection = [
                    createSelectionTree(['id']),
                    createSelectionTree(['name']),
                    createSelectionTree(['schoolDistrict', 'id']),
                    createSelectionTree(['schoolDistrict', 'name'])
            ]

        when:
            def result = testRepository.transformResults(rawData.stream(), columnsSelection)

        then:
            result != null
            result.size() == rawData.size()
            result.get(2).getId() == 3
            result.get(2).getSchoolDistrict() != null
            result.get(2).getSchoolDistrict().getName() == 'district #1'
    }

    def "Test results transformation 4 levels"() {
        given: "Define Tree with 3 sub-levels and select only 3 fields withing all those levels"
            def rawData = [
                    generateRawData(['level1', 'level2', 'level4']),
            ]
            def columnsSelection = [
                    createSelectionTree(['name']),
                    createSelectionTree(['level2', 'name']),
                    createSelectionTree(['level2', 'level3', 'level4', 'name']),
            ]

        when:
            def result = testRepository2.transformResults(rawData.stream(), columnsSelection)

        then:
            result != null
            result.size() == rawData.size()
        and: "Level 1 has both name and child"
            result.get(0).getName() == 'level1'
        and: "Level 2 has both name and child"
            result.get(0).getLevel2() != null
            result.get(0).getLevel2().getName() != null
            result.get(0).getLevel2().getName() == 'level2'
        and: "Level 3 should not have name but only child with name"
            result.get(0).getLevel2().getLevel3() != null
            result.get(0).getLevel2().getLevel3().getName() == null
            result.get(0).getLevel2().getLevel3().getLevel4() != null
            result.get(0).getLevel2().getLevel3().getLevel4().getName() != null
            result.get(0).getLevel2().getLevel3().getLevel4().getName() ==  'level4'
    }

    def "Test createProjection 4 levels"() {
        given: "Define projection data"
            def projection = [
                    createProjectionTree(['name']),
                    createProjectionTree(['level2', 'name']),
                    createProjectionTree(['level2', 'level3', 'level4', 'name']),
            ]

            SingularAttributePath level1Name = Mock()
            SingularAttributePath level2Name = Mock()
            SingularAttributePath level4Name = Mock()
            SingularAttributePath level2 = Mock()
            SingularAttributePath level3 = Mock()
            SingularAttributePath level4 = Mock()

            level4.get('name') >> level4Name
            level3.get('level4') >> level4
            level2.get('name') >> level2Name
            level2.get('level3') >> level3

            Root metadata = Mock()
            metadata.get('name') >> level1Name
            metadata.get('level2') >> level2

        when: "Create Hibernate Selection list from projection data"
            def result = testRepository2.createProjection(metadata, projection)

        then: "Test generated Selection list"
            result != null
        and: ""
            1 * metadata.get('name') >> level1Name
            2 * metadata.get('level2') >> level2
            1 * level2.get('name') >> level2Name
            1 * level2.get('level3') >> level3
            1 * level3.get('level4') >> level4
            1 * level4.get('name') >> level4Name
    }

    private generateRawData(List<Object> data) {
        data.toArray(new Object[0])
    }

    private createSelectionTree(List<String> paths) {
        def path = null

        paths.forEach { pathName ->
            def childPath = Mock(SingularAttributePath)
            childPath.getAttribute() >> new TestSingularAttribute(pathName)
            if (path) {
                childPath.getParentPath() >> path
            }

            path = childPath
        }

        path
    }

    private createProjectionTree(List<String> paths) {
        def projection = null as ProjectionDto
        def childProjection = null as ProjectionDto
        paths.forEach { pathName ->
            def p = new ProjectionDto(pathName, new ArrayList<ProjectionDto>())
            if (childProjection) {
                childProjection.getChildren().add(p)
            }
            childProjection = p
            if (!projection) {
                projection = childProjection
            }
        }

        projection
    }
}
