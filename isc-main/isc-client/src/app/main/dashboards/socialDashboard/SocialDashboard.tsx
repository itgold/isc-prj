import { makeStyles } from '@material-ui/core/styles';
import React from 'react';
import Widget from '@common/components/layout/widget';
import Iframe from '@common/components/core/iframe';
import FullSizeLayout from '@common/components/layout/fullsize.layout';

const useStyles = makeStyles(theme => ({
    root: {
        display: 'flex',
        flexDirection: 'column',
        minHeight: '100%',
        padding: theme.spacing(3),
    },
    widgetBody: {
        height: '100%',
        width: '100%',
    },
    containerStyle: {
        width: '100%',
        height: '100%',
    },
}));

function SocialDashboard() {
    const classes = useStyles();
    const dashboardUrl =
        "6bdb9f70-60d4-11ea-a742-436ca0706af6?_g=(refreshInterval:(pause:!t,value:0),time:(from:now-24h,to:now))&_a=(description:'',filters:!(),fullScreenMode:!t,options:(hidePanelTitles:!f,useMargins:!t),panels:!((embeddableConfig:(isLayerTOCOpen:!f,mapCenter:(lat:34.03934,lon:-118.25214,zoom:9.52),openTOCDetails:!()),gridData:(h:20,i:ccada320-0d91-4674-bf5f-40b1063713e8,w:35,x:0,y:0),id:'0d3eb530-60d7-11ea-a742-436ca0706af6',panelIndex:ccada320-0d91-4674-bf5f-40b1063713e8,type:map,version:'7.5.0'),(embeddableConfig:(),gridData:(h:7,i:'32f2cf21-23b1-431e-b850-a577cf657e60',w:13,x:35,y:0),id:'76035b90-60da-11ea-a742-436ca0706af6',panelIndex:'32f2cf21-23b1-431e-b850-a577cf657e60',type:visualization,version:'7.5.0'),(embeddableConfig:(),gridData:(h:13,i:e4012415-c00b-4dd4-b661-cbcc689d9f84,w:13,x:35,y:7),id:'550aae50-623a-11ea-86c5-6dcb44d71334',panelIndex:e4012415-c00b-4dd4-b661-cbcc689d9f84,type:visualization,version:'7.5.0'),(embeddableConfig:(),gridData:(h:6,i:'14f0b1f5-517e-42fc-9f0c-4e3c00a220bf',w:48,x:0,y:20),id:'2ea6e240-60d9-11ea-a742-436ca0706af6',panelIndex:'14f0b1f5-517e-42fc-9f0c-4e3c00a220bf',type:visualization,version:'7.5.0'),(embeddableConfig:(),gridData:(h:15,i:'50186218-99da-49ce-8075-dc37f4bab3eb',w:24,x:0,y:26),id:f3b8d2b0-623b-11ea-86c5-6dcb44d71334,panelIndex:'50186218-99da-49ce-8075-dc37f4bab3eb',type:visualization,version:'7.5.0'),(embeddableConfig:(),gridData:(h:15,i:b117184c-dbdb-4785-81fe-a852a0d5ab08,w:24,x:24,y:26),id:'5e1d9150-623b-11ea-86c5-6dcb44d71334',panelIndex:b117184c-dbdb-4785-81fe-a852a0d5ab08,type:visualization,version:'7.5.0')),query:(language:kuery,query:''),timeRestore:!t,title:'Twitter%20Activities',viewMode:view)";
    const kibanaUrl = `${window.location.protocol}//${window.location.hostname}/kibana/s/isc/app/kibana#/dashboard/${dashboardUrl}`;

    return (
        <FullSizeLayout>
            <div className={classes.root}>
                <Widget title="Social Dashboard" noBodyPadding={false} bodyId="map" bodyClass={classes.widgetBody}>
                    <Iframe className={classes.containerStyle} title="Dashboard" frameBorder="0" src={kibanaUrl} />
                </Widget>
            </div>
        </FullSizeLayout>
    );
}

export default SocialDashboard;
