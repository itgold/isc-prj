import React from 'react';
import { withRouter } from 'react-router';

// variable which will point to react-router history
let globalHistory = null;

// component which we will mount on top of the app
class Spy extends React.Component {
    constructor(props) {
        super(props);
        globalHistory = props.history;
    }

    componentDidUpdate() {
        globalHistory = this.props.history;
    }

    render() {
        return null;
    }
}

export const GlobalHistory = withRouter(Spy);

// export react-router history
export default function getHistory() {
    return globalHistory;
}

/*
Example:

    import getHistory from './history'; 

    export const goToPage = () => (dispatch) => {
    dispatch({ type: GO_TO_SUCCESS_PAGE });
    getHistory().push('/success'); // at this point component probably has been mounted and we can safely get `history`
    };
*/
