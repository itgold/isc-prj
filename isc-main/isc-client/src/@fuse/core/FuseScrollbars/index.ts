import FuseScrollbars from './FuseScrollbars';

// this is workaround to use JS component from TS component
interface FuseScrollbarsProps {
    className: string;
    children: React.ReactNode | React.ReactNode[];
    option: any;
}
const FuseScrollbarsImpl = FuseScrollbars as React.ComponentType<FuseScrollbarsProps>;

export default FuseScrollbarsImpl;
