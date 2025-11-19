import type { FC, ReactElement } from 'react';
import type { RouteProps } from 'react-router';

import { useIntl } from 'react-intl';

import PrivateRoute from './pravateRoute';

export interface WrapperRouteProps {
  /** document title locale id */
  titleId: string;

  /** need authentication? */
  auth?: boolean;

  /** the actual page element */
  element: ReactElement;

  /** route path */
  path?: string;

  /** other optional router fields */
  index?: boolean;
}

const WrapperRouteComponent: FC<WrapperRouteProps> = ({
  titleId,
  auth,
  element,
}) => {
  const { formatMessage } = useIntl();

  if (titleId) {
    document.title = formatMessage({ id: titleId });
  }

  return auth ? (
    <PrivateRoute element={element} />
  ) : (
    element
  );
};

export default WrapperRouteComponent;
