import { useEffect, useState, type FC } from 'react';
import type { RouteProps } from 'react-router';

import { Button, Result } from 'antd';
import { useSelector } from 'react-redux';
import { useLocation } from 'react-router';
import { useNavigate } from 'react-router-dom';

import { useLocale } from '@/locales';

const PrivateRoute: FC<RouteProps> = props => {
  const { logged } = useSelector((state: any) => state.user);
  const navigate = useNavigate();
  const location = useLocation();
  const { formatMessage } = useLocale();

  const [count, setCount] = useState(5);

  // Countdown + auto redirect
  useEffect(() => {
    if (logged) return;

    // Immediate redirect if path is '/'
    if (location.pathname === '/') {
      navigate(`/login?from=${encodeURIComponent(location.pathname)}`, { replace: true });
      return;
    }

    const timer = setInterval(() => {
      setCount(prev => {
        if (prev <= 1) {
          navigate(`/login?from=${encodeURIComponent(location.pathname)}`, { replace: true });
          return 0;
        }
        return prev - 1;
      });
    }, 1000);

    return () => clearInterval(timer);
  }, [logged, navigate, location.pathname]);

  if (logged) {
    return props.element as React.ReactElement;
  }

  return (
    <Result
      status="403"
      title="403"
      subTitle={
        <>
          {formatMessage({ id: 'gloabal.tips.unauthorized' })}
          <br />
          <span style={{ fontSize: 14, color: '#888' }}>
            Redirecting to login in <strong>{count}</strong> seconds...
          </span>
        </>
      }
      extra={
        <Button
          type="primary"
          onClick={() =>
            navigate(`/login?from=${encodeURIComponent(location.pathname)}`, {
              replace: true,
            })
          }
        >
          {formatMessage({ id: 'gloabal.tips.goToLogin' })}
        </Button>
      }
    />
  );
};

export default PrivateRoute;
