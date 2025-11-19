import type { CompanyRegisterParams, ForgotPasswordParams, LoginParams, OtpVerificationParams, ResetPasswordParams } from '../interface/user/login';
import type { Dispatch } from '@reduxjs/toolkit';

import { apiCompanyRegister, apiForgotPasswordRequest, apiLogin, apiLogout, apiOtpVerification, apiResetPassword } from '../api/user.api';
import { setUserItem } from './user.store';
import { createAsyncAction } from './utils';
// typed wrapper async thunk function demo, no extra feature, just for powerful typings
export const loginAsync = createAsyncAction<LoginParams, boolean>(payload => {
  return async dispatch => {
    const { result, status } = await apiLogin(payload);

    console.log('loginAsync result:', result, 'status:', status);

    if (status) {
      localStorage.setItem('t', result?.tokenDto?.token);
      localStorage.setItem('username', result.email);
      dispatch(
        setUserItem({
          logged: true,
          username: result.email,
        }),
      );

      return true;
    }

    return false;
  };
});

export const logoutAsync = () => {
  return async (dispatch: Dispatch) => {
    const { status } = await apiLogout({ token: localStorage.getItem('t')! });

    if (status) {
      localStorage.clear();
      dispatch(
        setUserItem({
          logged: false,
        }),
      );

      return true;
    }

    return false;
  };
};

export const companyRegisterAsync = createAsyncAction<CompanyRegisterParams, boolean>(payload => {
  return async dispatch => {
    const response = await apiCompanyRegister(payload)
    return response.status;
  };
});

export const ForgotPasswordRequestAsync = createAsyncAction<ForgotPasswordParams, boolean>(payload => {
  return async dispatch => {
    const response = await apiForgotPasswordRequest(payload)
    return response.status;
  };
});

export const OtpVerificationAsync = createAsyncAction<OtpVerificationParams, boolean>(payload => {
  return async dispatch => {
    const response = await apiOtpVerification(payload)
    return response.status;
  };
});

export const ResetPasswordAsync = createAsyncAction<ResetPasswordParams, boolean>(payload => {
  return async dispatch => {
    const response = await apiResetPassword(payload)
    return response.status;
  };
});
