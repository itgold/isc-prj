import React from 'react';
import { useSelector } from 'react-redux';

import { RootState } from 'app/store/appState';
import * as Selectors from 'app/store/selectors';
import { CompositeNode } from 'app/domain/composite';
import Region from 'app/domain/region';
import { SHORT_SCHOOL_NAME, RegionType } from 'app/utils/domain.constants';
import clsx from 'clsx';
import ISchoolElement from '../domain/school.element';
import { pathToRoot } from './map/utils/MapUtils';

interface EntityNameCellProps {
    entity?: ISchoolElement;
    skipEntityName?: boolean;
    skipSchoolName?: boolean;
    className?: string;
}

export const PARENT_PATH_SEPARATOR = '>';

function friendlyRegionName(regionName?: string): string {
    return SHORT_SCHOOL_NAME[regionName || ''] || regionName || '';
}

export default function EntityNameCell(props: EntityNameCellProps): JSX.Element {
    const entity = useSelector<RootState, ISchoolElement | undefined>(
        Selectors.createEntityByIdSelector(
            props.entity?.id || '',
            CompositeNode.entityType(props.entity as CompositeNode)
        )
    );
    const parentPaths = useSelector<RootState, Region[][]>(rootState =>
        pathToRoot(entity?.parentIds || [], [], rootState, !props.skipSchoolName)
    );

    const parentChains: string[] = parentPaths.map(chain =>
        chain
            .reverse()
            .map(region => friendlyRegionName(region.name))
            .join(` ${PARENT_PATH_SEPARATOR} `)
    );
    const sep = parentChains?.length ? `${PARENT_PATH_SEPARATOR} ` : '';
    const name = props.skipEntityName || !props.entity?.name ? '' : `${sep}${props.entity?.name}`;

    if (parentChains.length) {
        return (
            <td className={clsx('MuiTableCell-root MuiTableCell-sizeSmall', props.className)}>
                <ul>
                    {parentChains.map((chain, idx) => {
                        return (
                            <li key={idx} style={{ listStyle: 'circle' }}>
                                {chain} {(!props.skipEntityName && name) || ''}
                            </li>
                        );
                    })}
                </ul>
            </td>
        );
    }

    return <td className="MuiTableCell-root MuiTableCell-sizeSmall">{(!props.skipEntityName && name) || ''}</td>;
}

export function ParentPathComponent(props: EntityNameCellProps): JSX.Element {
    const node = useSelector<RootState, CompositeNode | undefined>(Selectors.createCompositeSelector(props.entity?.id));
    const parentPaths = useSelector<RootState, Region[][]>(rootState =>
        pathToRoot(node?.parentIds || [], [], rootState, !props.skipSchoolName)
    );

    // TODO: ignore all zone paths !!!
    const parentChains: string[] = parentPaths
        .filter(chain => !chain.find(region => region.type === RegionType.ZONE))
        .map(chain =>
            chain
                .reverse()
                .map(region => friendlyRegionName(region.name))
                .join(` ${PARENT_PATH_SEPARATOR} `)
        );
    const sep = parentChains?.length ? `${PARENT_PATH_SEPARATOR} ` : '';
    const name = props.skipEntityName || !props.entity?.name ? '' : `${sep}${props.entity?.name}`;

    if (parentChains.length) {
        return (
            <ul className={clsx(props.className)}>
                {parentChains.map((chain, idx) => {
                    return (
                        <li key={idx} style={{ listStyle: 'none' }}>
                            {chain} {(!props.skipEntityName && name) || ''}
                        </li>
                    );
                })}
            </ul>
        );
    }

    return <span className={clsx(props.className)}>{(!props.skipEntityName && name) || ''}</span>;
}
