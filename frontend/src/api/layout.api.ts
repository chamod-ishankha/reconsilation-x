import type { MenuList, MenuItem } from '../interface/layout/menu.interface';
import { mapMenu } from '../interface/layout/menu.interface';
import type { Notice } from '@/interface/layout/notice.interface';
import type { AxiosRequestConfig } from 'axios';

import { request } from './request';

// /** Provides the mock menu list to be shown in the navigation sidebar */
// export const getMenuList = (config: AxiosRequestConfig = {}) => request<MenuList>('get', '/auth/menu', {}, config);
/** Provides the menu list for the sidebar, mapped to MenuItem interface */
export const getMenuList = async (config: AxiosRequestConfig = {}): Promise<MenuList> => {
    const response = await request<any>('get', '/menu', {}, config);
    // Extract the actual array from your API response
    let rawMenu = [];
    if (response && response.result && Array.isArray(response.result)) {
        rawMenu = response.result;
    }

    return mapMenu(rawMenu);
};

/** Provides the mock notification list to be shown
 * in the notification dropdown
 */
export const getNoticeList = (config: AxiosRequestConfig = {}) => request<Notice[]>('get', '/user/notice', {}, config);
