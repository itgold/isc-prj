import React, { useEffect, useState } from 'react';
import FusePageCarded from '@fuse/core/FusePageCarded';
import HOCWrapper from '@fuse/utils/HOCWrapper';
import { createEmptyContext, IscTableContext, IscTableDataAdaptor } from 'app/components/table/IscTableContext';
import School from 'app/domain/school';
import { IscDataProvider } from 'app/components/table/IscTableDataProvider';
import { ICellCallback, ITableHeadColumn } from 'app/components/table/IscTableHead';
import _ from 'lodash';
import DeviceEvent from 'app/domain/deviceEvent';
import { RootState } from 'app/store/appState';
import * as Selectors from 'app/store/selectors';
import { useSelector } from 'react-redux';
import { MapControl } from 'app/components/map/deviceActions/IDeviceAction';
import ISchoolElement from 'app/domain/school.element';
import { SchoolDataProvider } from 'app/components/map/providers/SchoolDataProvider';
import DevicesGridControls from './DevicesGridControls';
import DevicesGridElements from './DevicesGridElements';

interface DevicesGridComponentProps {
    school?: School;
    columns: ITableHeadColumn[];
    mapControl: MapControl;

    customizeToolbar?: () => React.ReactNode;
    cellCallback?: ICellCallback;
    onSelectElement?: (row: ISchoolElement | null) => void;
}

export default function DevicesGridComponent(props: DevicesGridComponentProps): JSX.Element {
    const [dataContext, setDataContext] = useState<IscTableContext>(createEmptyContext());
    const [showFilters, setShowFilters] = useState<boolean>(false);
    const data = useSelector<RootState, ISchoolElement[]>(
        Selectors.createSchoolElementsSelector(props.school?.id || '')
    );

    const dataProvider: IscDataProvider = new SchoolDataProvider(
        data,
        props.columns,
        dataContext,
        (newContext: IscTableContext) => {
            if (!_.isEqual(dataContext, newContext)) {
                setDataContext(newContext);
            }
        }
    );

    const customizeToolbar = (): React.ReactNode => {
        return props.customizeToolbar ? props.customizeToolbar() : <></>;
    };

    const onSelectElement = (row: ISchoolElement | null): void => {
        if (row?.id) {
            if (props.onSelectElement) {
                props.onSelectElement(row);
            } else {
                props.mapControl.getMapComponent()?.animateFeature(row.id);
            }
        }
    };

    const cellCallback = props.cellCallback
        ? props.cellCallback
        : {
              onAction: (action: string, row: DeviceEvent) => {
                  if (action === 'highlight') {
                      if (row.deviceId) {
                          props.mapControl?.highlight({ id: row.deviceId });
                      }
                  } else {
                      console.log('Table row action not implemented!', action, row);
                  }
              },
              context: {},
          };

    useEffect(() => {
        setDataContext(createEmptyContext());
        setShowFilters(false);
    }, [props.school?.id]);

    return (
        <IscTableDataAdaptor context={dataContext} dataProvider={dataProvider}>
            <HOCWrapper
                componentRef={FusePageCarded}
                classes={{
                    content: 'flex',
                    header: 'pl-12 pr-12',
                }}
                header={
                    <DevicesGridControls
                        selectedSchool={props.school?.id}
                        showFilters={showFilters}
                        onToggleFilters={(show: boolean) => setShowFilters(show)}
                        toolBarItems={customizeToolbar()}
                    />
                }
                content={
                    <DevicesGridElements
                        columns={props.columns}
                        onSelectElement={(row: ISchoolElement | null) => onSelectElement(row)}
                        cellCallback={cellCallback}
                        showFilters={showFilters}
                    />
                }
            />
        </IscTableDataAdaptor>
    );
}
