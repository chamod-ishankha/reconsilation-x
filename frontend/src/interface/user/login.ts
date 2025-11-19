import { CompanyDto } from "../company";
import { StatusDto } from "../reference/status";

/** user's role */
export type Role = 'guest' | 'admin';

export interface LoginParams {
  companyEmail: string;
  email: string;
  password: string;
}

export interface CompanyRegisterParams {
  companyName: string;
  companyDescription: string;
  companyEmail: string;
  companyContactNo: string;
  companyAddress: string;
  companyWebsite: string;
  adminFirstName: string;
  adminLastName: string;
  adminPhone: string;
  adminEmail: string;
  adminPassword: string;
}

export interface CompanyRegisterResult {
  id: number;
  message: string;
}

export interface LoginResult {
  userId: number;
  firstName: string;
  lastName: string;
  email: string;
  phone: string;
  role: string;
  uniqueKey: string;
  dob: Date;
  tokenDto: TokenDto;
  whrStatus: StatusDto;
  companyDto: CompanyDto;
}

export interface TokenDto {
  tokenType: string;
  expiresIn: number;
  token: string;
  refreshToken: string;
}

export interface LogoutParams {
  token: string;
}

export interface LogoutResult { }

export interface ForgotPasswordParams {
  yourEmail: string;
  companyEmail: string;
}

export interface OtpVerificationParams {
  email: string;
  otp: string;
}

export interface ResetPasswordParams {
  email: string;
  otp: string;
  newPassword: string;
}

export interface ForgotPasswordData {
  yourEmail?: string;
  companyEmail?: string;
  email?: string;
  otp?: string;
  newPassword?: string;
}