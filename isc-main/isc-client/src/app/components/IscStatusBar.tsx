import React, { useState } from 'react';
import Avatar from '@material-ui/core/Avatar';
import Button from '@material-ui/core/Button';
import Icon from '@material-ui/core/Icon';
import MenuItem from '@material-ui/core/MenuItem';
import Popover from '@material-ui/core/Popover';
import Typography from '@material-ui/core/Typography';
import { useDispatch, useSelector } from 'react-redux';
import { RootState } from 'app/store/appState';
import { FaBuilding } from 'react-icons/fa';
import { createStyles, makeStyles } from '@material-ui/core';
import clsx from 'clsx';
import School from 'app/domain/school';
import * as Selectors from 'app/store/selectors';
import storageService, { STORAGE_SELECTED_SCHOOL } from 'app/services/storage.service';
import * as Actions from 'app/store/actions/admin/dashboards';
import VectorSource from 'ol/source/Vector';
import IscLayersBarMenu from './IscLayersBarMenu';
import { ILayersHashmap } from './map/SchoolMapComponent';

const useStyles = makeStyles(() =>
    createStyles({
        statusBarMenu: {
            minWidth: '260px',
            borderRadius: '0px ​0px 4px 4p',
        },
        cotextMenuItem: {
            fontSize: '1em',

            '&.Mui-selected': {
                backgroundColor: 'rgba(0, 0, 0, 0.2)',
                '&:hover': {
                    backgroundColor: 'rgba(0, 0, 0, 0.4)',
                },
            },
            '&:hover': {
                backgroundColor: 'rgba(0, 0, 0, 0.2)',
            },
        },
    })
);

export default function IscStatusBar(): JSX.Element {
    const dispatch = useDispatch();
    const schools = useSelector<RootState, School[]>(Selectors.schoolsSelector);
    const allLayers = useSelector<RootState>(Selectors.allLayersSelector);
    const schoolId = useSelector<RootState, string>(rootState => {
        let updateSchool = false;
        let currentSchool = Selectors.currentSchoolSelector(rootState);
        if (!currentSchool) {
            currentSchool = storageService.readProperty(STORAGE_SELECTED_SCHOOL);
            updateSchool = true;
        }
        if (!currentSchool) {
            currentSchool = schools.length ? schools[0].id : '';
            updateSchool = true;
        }

        if (currentSchool && updateSchool) {
            storageService.updateProperty(STORAGE_SELECTED_SCHOOL, currentSchool);
            dispatch(Actions.setCurrentSchool(currentSchool));
        }

        return currentSchool || '';
    });
    const currentSchool = schools.find(s => s.id === schoolId);
    const [schoolMenu, setSchoolMenu] = useState<HTMLButtonElement | null>(null);

    const schoolMenuClick = (event: React.MouseEvent<HTMLButtonElement>): void => {
        setSchoolMenu(event.currentTarget);
    };

    const schoolMenuClose = (): void => {
        setSchoolMenu(null);
    };

    /**
     * Remove all features from each layer/source
     */
    const clearLayers = (): void => {
        Object.values(allLayers as ILayersHashmap).forEach(layer => {
            (layer.source as VectorSource<any>).clear();
        });
    };

    const onChangeSchool = (newSchoolId?: string): void => {
        if (newSchoolId) {
            if (schoolId !== newSchoolId) clearLayers();
            dispatch(Actions.setCurrentSchool(newSchoolId));
            storageService.updateProperty(STORAGE_SELECTED_SCHOOL, newSchoolId);
        }

        setSchoolMenu(null);
    };

    const classes = useStyles();
    return (
        <>
            <Button className="h-64" onClick={schoolMenuClick}>
                <Avatar>
                    <FaBuilding />
                </Avatar>

                <div className="hidden md:flex flex-col mx-12 items-start">
                    <Typography component="span" className="normal-case font-600 flex">
                        {currentSchool?.name}
                    </Typography>
                    <Typography className="text-11 capitalize" color="textSecondary">
                        {currentSchool?.district?.name}
                    </Typography>
                </div>

                <Icon className="text-16 hidden sm:flex">keyboard_arrow_down</Icon>
            </Button>

            <Popover
                open={Boolean(schoolMenu)}
                anchorEl={schoolMenu}
                onClose={schoolMenuClose}
                anchorOrigin={{
                    vertical: 'bottom',
                    horizontal: 'left',
                }}
                transformOrigin={{
                    vertical: 'top',
                    horizontal: 'left',
                }}
                classes={{
                    paper: clsx(classes.statusBarMenu, 'py-8'),
                }}
            >
                {schools.map(school => (
                    <MenuItem
                        selected={school.id === schoolId}
                        key={school.id}
                        value={school.id}
                        className={clsx(classes.cotextMenuItem, 'context-menu-item')}
                        onClick={() => onChangeSchool(school.id)}
                    >
                        {school.name}
                    </MenuItem>
                ))}
            </Popover>
            <IscLayersBarMenu />
        </>
    );
}
