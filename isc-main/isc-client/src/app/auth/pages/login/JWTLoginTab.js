import { TextFieldFormsy } from '@fuse/core/formsy';
import Button from '@material-ui/core/Button';
import Icon from '@material-ui/core/Icon';
import InputAdornment from '@material-ui/core/InputAdornment';
import * as authActions from 'app/store/actions/auth';
import Formsy from 'formsy-react';
import React, { useEffect, useRef, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import Typography from '@material-ui/core/Typography';

function JWTLoginTab() {
    const dispatch = useDispatch();
    const login = useSelector(({ auth }) => auth.login);

    const [isFormValid, setIsFormValid] = useState(true);
    const [showPassword, setShowPassword] = useState(false);
    const [showForgotPassword, setShowForgotPassword] = useState(false);

    const formRef = useRef(null);

    const disableButton = () => {
        setIsFormValid(false);
    };

    function ForgotPassword() {
        return (
            <div className="text-right">
                <Button
                    color="primary"
                    variant="text"
                    size="small"
                    className="text-capitalize"
                    onClick={() => {
                        setShowForgotPassword(!showForgotPassword);
                    }}
                >
                    Forgot password?
                </Button>
                {showForgotPassword && (
                    <p className="text-left">
                        <span>To reset password please contact system administrator over the email: </span>
                        <a href="mailto:support@iscweb.io">support@iscweb.io</a>
                    </p>
                )}
            </div>
        );
    }

    function WarningBanner(props) {
        if (!props.error) {
            return null;
        }

        const errorCode = props.error?.response?.status;
        let errorMsg = '';
        switch (true) {
            case /4[0-9][0-9]/.test(errorCode):
                errorMsg = 'Invalid username or password';
                break;
            case /5[0-9][0-9]/.test(errorCode):
                errorMsg = 'Server is not responding';
                break;
            default:
                errorMsg = 'Something went wrong';
        }

        return (
            <Typography align="center" color="error" variant="subtitle2" className="mb-16">
                {`${errorMsg}`}
            </Typography>
        );
    }

    useEffect(() => {
        if (login.error && (login.error.email || login.error.password)) {
            formRef.current.updateInputsWithError({
                ...login.error,
            });
        }
    }, [login.error]);

    const enableButton = () => {
        setIsFormValid(true);
    };

    const handleSubmit = model => {
        dispatch(authActions.submitLogin(model));
    };

    return (
        <div className="w-full">
            <WarningBanner error={login.rest.error} />
            <Formsy
                onValidSubmit={handleSubmit}
                onValid={enableButton}
                onInvalid={disableButton}
                ref={formRef}
                className="flex flex-col justify-center w-full"
            >
                <TextFieldFormsy
                    className="mb-16 formsy-input-white"
                    type="text"
                    name="username"
                    label="Username/Email"
                    value=""
                    validations={{
                        minLength: 4,
                    }}
                    validationErrors={{
                        minLength: 'Min character length is 4',
                    }}
                    InputProps={{
                        endAdornment: (
                            <InputAdornment position="end">
                                <Icon className="text-20" color="action">
                                    email
                                </Icon>
                            </InputAdornment>
                        ),
                    }}
                    variant="outlined"
                    required
                />

                <TextFieldFormsy
                    className="mb-16 formsy-input-white"
                    type="password"
                    name="password"
                    label="Password"
                    value=""
                    InputProps={{
                        type: showPassword ? 'text' : 'password',
                        endAdornment: (
                            <InputAdornment position="end">
                                <Icon
                                    className="text-20 cursor-pointer"
                                    color="action"
                                    onClick={() => setShowPassword(!showPassword)}
                                >
                                    {showPassword ? 'visibility' : 'visibility_off'}
                                </Icon>
                            </InputAdornment>
                        ),
                    }}
                    variant="outlined"
                    required
                />
                <ForgotPassword />
                <Button
                    type="submit"
                    variant="contained"
                    color="primary"
                    className="w-full mx-auto mt-16 normal-case"
                    aria-label="LOG IN"
                    disabled={!isFormValid}
                    value="legacy"
                >
                    Login
                </Button>
            </Formsy>

            {/*
            <div className="flex flex-col items-center pt-24">
                <Typography className="text-14 font-600 py-8">Credentials</Typography>

                <Divider className="mb-16 w-256" />

                <table className="text-left w-256">
                    <thead>
                        <tr>
                            <th colSpan={3}>
                                <Typography className="font-600" color="textSecondary">
                                    Role / User / Password
                                </Typography>
                            </th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td colSpan={3}>
                                <Typography>SYSTEM_ADMINISTRATOR</Typography>
                            </td>
                        </tr>
                        <tr>
                            <td>&nbsp;</td>
                            <td>
                                <Typography>system-admin@iscweb.io</Typography>
                            </td>
                            <td>
                                <Typography></Typography>
                            </td>
                        </tr>

                        <tr>
                            <td colSpan={3}>
                                <Typography>DISTRICT_ADMINISTRATOR</Typography>
                            </td>
                        </tr>
                        <tr>
                            <td>&nbsp;</td>
                            <td>
                                <Typography>system-district-admin@iscweb.io</Typography>
                            </td>
                            <td>
                                <Typography></Typography>
                            </td>
                        </tr>

                        <tr>
                            <td colSpan={3}>
                                <Typography>ANALYST</Typography>
                            </td>
                        </tr>
                        <tr>
                            <td>&nbsp;</td>
                            <td>
                                <Typography>system-analyst@iscweb.io</Typography>
                            </td>
                            <td>
                                <Typography></Typography>
                            </td>
                        </tr>

                        <tr>
                            <td colSpan={3}>
                                <Typography>GUARD</Typography>
                            </td>
                        </tr>
                        <tr>
                            <td>&nbsp;</td>
                            <td>
                                <Typography>system-guard@iscweb.io</Typography>
                            </td>
                            <td>
                                <Typography></Typography>
                            </td>
                        </tr>

                        <tr>
                            <td colSpan={3}>
                                <Typography>GUEST</Typography>
                            </td>
                        </tr>
                        <tr>
                            <td>&nbsp;</td>
                            <td>
                                <Typography>system-guest@iscweb.io</Typography>
                            </td>
                            <td>
                                <Typography></Typography>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
            */}
        </div>
    );
}

export default JWTLoginTab;
