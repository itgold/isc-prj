import React, { createContext, useContext, useState } from 'react';
import Region from 'app/domain/region';

export type ICurrentBuildingContext = {
    currentBuilding?: Region;
    setCurrentBuilding: (currentBuilding?: Region) => void;
};

const CurrentBuildingContext = createContext<ICurrentBuildingContext>({
    currentBuilding: undefined, // set a default value
    setCurrentBuilding: () => {},
});

interface CurrentBuildingAdaptorProps {
    children: React.ReactNode;
    currentBuilding?: Region;
}

const CurrentBuildingAdaptor = (props: CurrentBuildingAdaptorProps): JSX.Element => {
    const [currentBuilding, setCurrentBuilding] = useState<Region | undefined>(props.currentBuilding);

    return (
        <CurrentBuildingContext.Provider
            value={{
                currentBuilding,
                setCurrentBuilding: (building?: Region) => {
                    setCurrentBuilding(building);
                },
            }}
        >
            {props.children}
        </CurrentBuildingContext.Provider>
    );
};

const useCurrentBuildingContext = () => useContext(CurrentBuildingContext);

export { CurrentBuildingContext, CurrentBuildingAdaptor, useCurrentBuildingContext };

/*
Another way to use context (useful in React class components):
<CurrentBuildingContext.Consumer>
    {context => (
        <h1>{context.currentBuilding}</h1>
    )}
</CurrentBuildingContext.Consumer>
*/
