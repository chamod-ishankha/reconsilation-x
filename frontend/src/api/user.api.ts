import { GlobalResult } from '@/interface/global/global';
import type { CompanyRegisterParams, CompanyRegisterResult, ForgotPasswordParams, LoginParams, LoginResult, LogoutParams, LogoutResult, OtpVerificationParams, ResetPasswordParams } from '../interface/user/login';

import { request } from './request';

export const apiLogin = (data: LoginParams) => request<LoginResult>('post', '/auth/login', data);

export const apiLogout = (data: LogoutParams) => { return { status: true } };

export const apiCompanyRegister = (data: CompanyRegisterParams) => request<CompanyRegisterResult>('post', '/auth/register/company', data);

export const apiForgotPasswordRequest = (data: ForgotPasswordParams) => request<GlobalResult>('post', '/auth/forgot-password', data);

export const apiOtpVerification = (data: OtpVerificationParams) => request<GlobalResult>('post', '/auth/verify-otp', data);

export const apiResetPassword = (data: ResetPasswordParams) => request<GlobalResult>('post', '/auth/reset-password', data);