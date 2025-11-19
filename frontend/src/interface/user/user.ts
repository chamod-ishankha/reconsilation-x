import type { Role } from './login';
import type { Device } from '@/interface/layout/index.interface';
import type { MenuItem } from '@/interface/layout/menu.interface';

export type Locale = 'zh_CN' | 'en_US';

export interface UserState {
  username: string;

  /** menu list for init tagsView */
  menuList: MenuItem[];

  /** login status */
  logged: boolean;

  role: Role;

  /** user's device */
  device: Device;

  /** menu collapsed status */
  collapsed: boolean;

  /** notification count */
  noticeCount: number;

  /** user's language */
  locale: Locale;

  /** Is first time to view the site ? */
  newUser: boolean;
}
