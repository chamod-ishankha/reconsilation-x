import type { LoginParams } from '@/interface/user/login';
import type { FC } from 'react';

import './index.less';

import { Button, Checkbox, Form, Input, theme as antTheme } from 'antd';
import { useDispatch } from 'react-redux';
import { useLocation, useNavigate } from 'react-router-dom';

import { LocaleFormatter, useLocale } from '@/locales';
import { formatSearch } from '@/utils/formatSearch';

import { loginAsync } from '../../stores/user.action';

const initialValues: LoginParams = {
  companyEmail: '',
  email: '',
  password: '',
  // remember: true
};

const LoginForm: FC = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const dispatch = useDispatch();
  const { formatMessage } = useLocale();
  const { token } = antTheme.useToken();

  const onFinished = async (form: LoginParams) => {
    const success = await dispatch(loginAsync(form));

    console.log('success:', success);

    if (!!success) {
      const search = formatSearch(location.search);
      const from = search.from || { pathname: '/' };

      navigate(from);
    }
  };

  return (
    <div className="login-page-style" style={{ backgroundColor: token.colorBgContainer }}>
      <Form<LoginParams> onFinish={onFinished} className="login-page-style-form" initialValues={initialValues}>
        <h2>RECONSILATION-X LOGIN</h2>
        <Form.Item
          name="companyEmail"
          rules={[
            {
              required: true,
              message: formatMessage({
                id: 'gloabal.tips.enterLoginCompanyEmailMessage',
              }),
            },
          ]}
        >
          <Input
            placeholder={formatMessage({
              id: 'gloabal.tips.loginCompanyEmail',
            })}
          />
        </Form.Item>
        <Form.Item
          name="email"
          rules={[
            {
              required: true,
              message: formatMessage({
                id: 'gloabal.tips.enterEmailMessage',
              }),
            },
          ]}
        >
          <Input
            placeholder={formatMessage({
              id: 'gloabal.tips.email',
            })}
          />
        </Form.Item>
        <Form.Item
          name="password"
          rules={[
            {
              required: true,
              message: formatMessage({
                id: 'gloabal.tips.enterPasswordMessage',
              }),
            },
          ]}
        >
          <Input
            type="password"
            placeholder={formatMessage({
              id: 'gloabal.tips.password',
            })}
          />
        </Form.Item>
        <Form.Item name="remember" valuePropName="checked" className='login-page-style-form_remember'>
          <div className="remember-row">
            <Checkbox>
              <LocaleFormatter id="gloabal.tips.rememberUser" />
            </Checkbox>

            <a href="/forgot-password">
              <LocaleFormatter id="gloabal.tips.forgotPassword" />
            </a>
          </div>
        </Form.Item>
        <Form.Item className='login-page-style-form_item-button'>
          <Button htmlType="submit" type="primary" className="login-page-style-form_login-button">
            <LocaleFormatter id="gloabal.tips.login" />
          </Button>
        </Form.Item>
        <Form.Item className='login-page-style-form_item-button'>
          <Button htmlType="button" type="dashed" className="login-page-style-form_register-button"
            onClick={() => navigate('/company-register')}>
            <LocaleFormatter id="gloabal.tips.companyRegister" />
          </Button>
        </Form.Item>
      </Form>
    </div>
  );
};

export default LoginForm;
