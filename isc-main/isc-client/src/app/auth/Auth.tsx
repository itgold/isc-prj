import React from 'react';
import FuseSplashScreen from '@fuse/core/FuseSplashScreen';
import * as Actions from 'app/store/actions/auth';
import * as MessageActions from 'app/store/actions/fuse/message.actions';
import { refreshAccessToken, resolveToken, isTokenExpired, updateToken } from 'app/utils/auth.utils';
import { connect } from 'react-redux';
import { bindActionCreators, Dispatch, Action } from 'redux';
import JWTToken from '@common/auth/jwt.token';
import { updateAuthContextProvider, AuthContextProvider } from '@common/auth/auth.provider';
import { RootState, AuthState } from 'app/store/appState';
import { AuthenticationError, SecurityError } from '@common/auth';
import authRoles from './authRoles';

interface StateProps {
    auth?: AuthState;
}

interface DefaultProps {
    children: React.ReactNode;
}

interface DispatchProps {
    setUserData(payload?: any): void;
    authSet(payload?: any): void;
    logout(payload?: any): void;
}

type Props = StateProps & DispatchProps & DefaultProps;

class Auth extends React.Component<Props> implements AuthContextProvider {
    static async loadUserDetails(username?: string): Promise<any> {
        return new Promise(resolve => {
            // TODO: load user details/settings here
            setTimeout(() => {
                const token = resolveToken();
                const userData = {
                    role: token?.roles || [],
                    data: {
                        displayName: username,
                        photoURL: 'assets/images/avatars/profile.jpg',
                        email: username,
                        shortcuts: ['map', 'social'],
                    },
                };
                resolve(userData);
            }, 500);
        });
    }

    adminRoles: string[];

    state = {
        waitAuthCheck: true,
    };

    constructor(props: Props) {
        super(props);

        this.adminRoles = authRoles.Administrator;
        updateAuthContextProvider(this);
    }

    componentDidMount(): void {
        Promise.all([this.securityContextCheck()]).then(() => {
            this.setState({ waitAuthCheck: false });
        });
    }

    currentToken(): JWTToken {
        let token: JWTToken | null = this.props.auth?.login?.token || null;
        if (!token) {
            const tokenFromLocalstore = resolveToken();
            token = tokenFromLocalstore;
            if (token) {
                this.props.authSet(token);
            }
        }

        return token || new JWTToken();
    }

    refreshToken(): Promise<JWTToken> {
        const token = resolveToken();
        if (token) {
            return refreshAccessToken(token.refresh_token).then(newToken => {
                updateToken(newToken);
                this.props.authSet(newToken);
                return newToken;
            });
        }

        return new Promise((resolve, reject) => {
            reject(new AuthenticationError(SecurityError.AUTH_REFRESH));
        });
    }

    logout(): void {
        this.props.logout();
    }

    isAdmin(roles: string[]): boolean {
        const isAdmin = roles
            ? roles
                  .map(role => this.adminRoles.indexOf(role) >= 0)
                  .reduce((accumulator, currentValue) => accumulator || currentValue)
            : false;

        return isAdmin;
    }

    async securityContextCheck(): Promise<boolean> {
        return new Promise(resolve => {
            this.isAuthenticated().then(authenticated => {
                if (authenticated) {
                    const token = resolveToken();
                    Auth.loadUserDetails(token?.username).then(userDetails => {
                        this.props.setUserData(userDetails);
                        resolve(true);
                    });
                } else {
                    resolve(false);
                }
            });
        });
    }

    async isAuthenticated(): Promise<boolean> {
        return new Promise(resolve => {
            const token = resolveToken();
            if (token) {
                // if the token has expired return false
                if (isTokenExpired(token)) {
                    if (token.refresh_token) {
                        this.refreshToken()
                            .then(() => {
                                resolve(true);
                            })
                            .catch(error => {
                                console.log('Unable to refresh JWT token', error);
                                this.logout();
                                resolve(false);
                            });
                    } else {
                        this.logout();
                        resolve(false);
                    }
                } else {
                    this.props.authSet(token);
                    resolve(true);
                }
            } else {
                resolve(false);
            }
        });
    }

    render(): JSX.Element {
        return this.state.waitAuthCheck ? <FuseSplashScreen /> : <>{this.props.children}</>;
    }
}

function mapDispatchToProps(dispatch: Dispatch<Action>): DispatchProps {
    return bindActionCreators(
        {
            setUserData: Actions.setUserData,
            authSet: Actions.authSet,
            authUnset: Actions.authUnset,
            logout: Actions.logout,
            showMessage: MessageActions.showMessage,
        },
        dispatch
    );
}

// eslint-disable-next-line @typescript-eslint/no-unused-vars
function mapStateToProps(state: RootState): StateProps {
    return {
        auth: state.auth,
    };
}

export default connect(mapStateToProps, mapDispatchToProps)(Auth);
