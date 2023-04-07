package com.iscweb.service.composite

import com.google.common.collect.Sets
import com.iscweb.common.annotations.CompositeCacheEntry
import com.iscweb.common.model.EntityType
import com.iscweb.common.model.dto.composite.CompositeFilter
import com.iscweb.common.model.dto.composite.IRegionComposite
import com.iscweb.common.model.dto.entity.core.CameraDto
import com.iscweb.common.model.dto.entity.core.DoorDto
import com.iscweb.common.model.dto.entity.core.RegionDto
import com.iscweb.service.composite.impl.CompositeFactory
import com.iscweb.service.composite.leaf.CameraComposite
import com.iscweb.service.composite.leaf.DoorComposite
import com.iscweb.service.composite.leaf.RegionComposite
import spock.lang.Ignore
import spock.lang.Specification

class CompositeServiceTest extends Specification {

    @Ignore
    def "Test composite tree node update"() {
        given: "create mock CompositeFactory"
            CompositeFactory compositeFactory = (CompositeFactory) Spy(CompositeFactory, constructorArgs: [])
            def tree = generateCompositeTree()
            compositeFactory.build(_ as String, _ as CompositeFilter) >> tree

            def compositeService = new CompositeService()
            compositeService.getRoot().set(tree)
            compositeService.setCompositeFactory(compositeFactory)
            compositeService.invalidateCache()

        when: "build root tree"
            def root = compositeService.build(IRegionComposite.ROOT, CompositeFilter.build()) as RegionComposite

        then: "check root is not null"
            root != null
            root.entityDto != null
            root.entityDto.id == IRegionComposite.ROOT
        and: "check first level nodes"
            root.children != null
            root.children.size() == 3
            (root?.children?.get('l1.1') as RegionComposite)?.children?.get('door') != null
            (root?.children?.get('l1.2') as RegionComposite)?.children?.get('door') != null
            (root?.children?.get('l1.3') as RegionComposite)?.children?.get('door') == null
            (root?.children?.get('l1.1') as RegionComposite)?.children?.get('camera') == null
            (root?.children?.get('l1.2') as RegionComposite)?.children?.get('camera') != null
            (root?.children?.get('l1.3') as RegionComposite)?.children?.get('camera') != null

        when: "update door"
            def door = new DoorDto(
                    id: 'door',
                    name: 'Test Door Updated',
                    parentIds: Sets.newHashSet(['l1.1', 'l1.3'])
            )
            compositeService.refreshNode(door, CompositeCacheEntry.UpdateType.UPDATE)
            root = compositeService.build(IRegionComposite.ROOT, CompositeFilter.build()) as RegionComposite
            def updatedDoor = (root?.children?.get('l1.1') as RegionComposite)?.children?.get('door')
        then: "check updated door"
            updatedDoor != null
            (updatedDoor.entityDto as DoorDto).name == 'Test Door Updated'
            (updatedDoor.entityDto as DoorDto).parentIds.contains('l1.1')
            !(updatedDoor.entityDto as DoorDto).parentIds.contains('l1.2')
            (updatedDoor.entityDto as DoorDto).parentIds.contains('l1.3')
        and: "check updated hierarchy"
            (root?.children?.get('l1.1') as RegionComposite)?.children?.get('door') != null
            (root?.children?.get('l1.2') as RegionComposite)?.children?.get('door') == null
            (root?.children?.get('l1.3') as RegionComposite)?.children?.get('door') != null

        when: "insert door"
            def door2 = new DoorDto(
                    id: 'door2',
                    name: 'Test Door #2',
                    parentIds: Sets.newHashSet(['l1.2'])
            )
            compositeService.refreshNode(door2, CompositeCacheEntry.UpdateType.CREATE)
            root = compositeService.build(IRegionComposite.ROOT, CompositeFilter.build()) as RegionComposite
        then: "check if the door was added into tree"
            (root?.children?.get('l1.2') as RegionComposite)?.children?.get('door2') != null

        when: "delete door"
            def door3 = new DoorDto(id: 'door')
            compositeService.refreshNode(door3, CompositeCacheEntry.UpdateType.DELETE)
            root = compositeService.build(IRegionComposite.ROOT, CompositeFilter.build()) as RegionComposite
        then: "check if the door was added into tree"
            (root?.children?.get('l1.1') as RegionComposite)?.children?.get('door') == null
            (root?.children?.get('l1.2') as RegionComposite)?.children?.get('door') == null
            (root?.children?.get('l1.3') as RegionComposite)?.children?.get('door') == null
    }

    @Ignore
    def "Test composite tree filtering"() {
        given: "create mock CompositeFactory"
            CompositeFactory compositeFactory = (CompositeFactory) Spy(CompositeFactory, constructorArgs: [])
            def tree = generateCompositeTree()
            compositeFactory.build(_ as String, _ as CompositeFilter) >> tree

            def compositeService = new CompositeService()
            compositeService.setCompositeFactory(compositeFactory)
            compositeService.invalidateCache()

        when: "build sub tree from level 1 node"
            def root = compositeService.build('l1.1', CompositeFilter.buildBy(EntityType.DOOR)) as RegionComposite

        then: "check root is not null"
            root != null
            root.entityDto != null
            root.entityDto.id == 'l1.1'
        and: "check found door"
            root.children != null
            root.children.size() == 1
            root?.children?.get('door') != null

        when: "build full tree"
            root = compositeService.build(IRegionComposite.ROOT, CompositeFilter.buildBy(EntityType.DOOR)) as RegionComposite

        then: "check root is not null"
            root != null
            root.entityDto != null
            root.entityDto.id == IRegionComposite.ROOT
        and: "check found door"
            root.children != null
            root.children.size() == 3
            (root?.children?.get('l1.1') as RegionComposite)?.children?.get('door') != null
            (root?.children?.get('l1.2') as RegionComposite)?.children?.get('door') != null
            (root?.children?.get('l1.3') as RegionComposite)?.children?.get('door') == null
            (root?.children?.get('l1.2') as RegionComposite)?.children?.get('camera') == null
            (root?.children?.get('l1.3') as RegionComposite)?.children?.get('camera') == null
    }

    private static generateCompositeTree() {
        def root = RegionComposite.valueOf(new RegionDto(id: IRegionComposite.ROOT, name: 'ROOT'))

        def l11 = RegionComposite.valueOf(new RegionDto(id: 'l1.1', name: 'L1.1'))
        def l12 = RegionComposite.valueOf(new RegionDto(id: 'l1.2', name: 'L1.2'))
        def l13 = RegionComposite.valueOf(new RegionDto(id: 'l1.3', name: 'L1.3'))
        root.children.put(l11.entityDto.id, l11)
        root.children.put(l12.entityDto.id, l12)
        root.children.put(l13.entityDto.id, l13)

        def door = DoorComposite.valueOf(new DoorDto(id: 'door', name: 'Test Door', parentIds: Sets.newHashSet(['l1.1', 'l1.2'])))
        l11.children.put(door.entityDto.id, door)
        l12.children.put(door.entityDto.id, door)

        def camera = CameraComposite.valueOf(new CameraDto(id: 'camera', name: 'Test Camera', parentIds: Sets.newHashSet(['l1.2', 'l1.3'])))
        l12.children.put(camera.entityDto.id, camera)
        l13.children.put(camera.entityDto.id, camera)

        root
    }
}