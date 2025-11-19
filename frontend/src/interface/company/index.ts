import { StatusDto } from "../reference/status";

export interface CompanyDto {
    createdAt: Date;
    createdBy: string;
    updatedAt: Date;
    updatedBy: string;
    companyId: number;
    name: string;
    email: string;
    contactNo: string;
    address: string;
    website: string;
    companyDescription: string;
    isActive: boolean;
    maxFailedLoginAttempts: number;
    enableEmailVerification: boolean;
    allowLeaseRequests: boolean;
    companyDefaultLeaveRequestCount: number;
    status: StatusDto;
    statusId: number;
}