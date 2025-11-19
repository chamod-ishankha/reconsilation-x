import type { CompanyRegisterParams, LoginParams } from '@/interface/user/login';
import { useState, type FC } from 'react';

import './index.less';

import { Button, Checkbox, Form, Input, Steps, theme as antTheme } from 'antd';
import { useDispatch } from 'react-redux';
import { useLocation, useNavigate } from 'react-router-dom';

import { LocaleFormatter, useLocale } from '@/locales';
import { formatSearch } from '@/utils/formatSearch';

import { companyRegisterAsync } from '../../stores/user.action';

const Register: FC = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const dispatch = useDispatch();
  const { formatMessage } = useLocale();
  const { token } = antTheme.useToken();

  const [current, setCurrent] = useState(0);
  const [companyData, setCompanyData] = useState({});
  const [adminData, setAdminData] = useState({});

  const [form] = Form.useForm();

  // NEXT → save company data
  const handleCompanyNext = (values: any) => {
    setCompanyData(values);
    setCurrent(1);
  };

  const goPrev = () => {
    form.setFieldsValue(companyData);
    setCurrent(0);
  };


  // SUBMIT → final
  const handleAdminSubmit = async (values: CompanyRegisterParams) => {
    const fullData = {
      ...companyData,
      ...values,
    };

    const success = await dispatch(companyRegisterAsync(fullData));

    if (!!success) {
      navigate('/login');
    }
  };

  return (
    <div className="login-page" style={{ backgroundColor: token.colorBgContainer }}>
      <div className="signup-page-form">
        <Form<CompanyRegisterParams>
          form={form}
          layout="vertical"
          onFinish={current === 0 ? handleCompanyNext : handleAdminSubmit}
        >
          <h2 style={{ width: "100%", textAlign: "center" }}>Register Your Company</h2>
          <Steps current={current} style={{ marginBottom: 30 }}>
            <Steps.Step title="Company" />
            <Steps.Step title="Admin" />
          </Steps>
          {/* ================= STEP 1 : COMPANY ================= */}
          {current === 0 && (
            <>
              <Form.Item
                name="companyName"
                rules={[{ required: true, message: formatMessage({ id: 'gloabal.tips.enterCompanyNameMessage' }) }]}
              >
                <Input placeholder={formatMessage({ id: 'gloabal.tips.CompanyName' })} />
              </Form.Item>

              <Form.Item name="companyDescription">
                <Input placeholder={formatMessage({ id: 'gloabal.tips.CompanyDescription' })} />
              </Form.Item>

              <Form.Item
                name="companyEmail"
                rules={[{ required: true, message: formatMessage({ id: 'gloabal.tips.enterCompanyEmailMessage' }) }]}
              >
                <Input placeholder={formatMessage({ id: 'gloabal.tips.CompanyEmail' })} />
              </Form.Item>

              <Form.Item
                name="companyContactNo"
                rules={[{ required: false, message: formatMessage({ id: 'gloabal.tips.enterCompanyContactMessage' }) }]}
              >
                <Input placeholder={formatMessage({ id: 'gloabal.tips.CompanyContact' })} />
              </Form.Item>

              <Form.Item
                name="companyAddress"
                rules={[{ required: true, message: formatMessage({ id: 'gloabal.tips.enterCompanyAddressMessage' }) }]}
              >
                <Input placeholder={formatMessage({ id: 'gloabal.tips.CompanyAddress' })} />
              </Form.Item>

              <Form.Item
                name="companyWebsite"
                rules={[{ required: false, message: formatMessage({ id: 'gloabal.tips.enterCompanyWebsiteMessage' }) }]}
              >
                <Input placeholder={formatMessage({ id: 'gloabal.tips.CompanyWebsite' })} />
              </Form.Item>

              <Form.Item>
                <Button type="primary" htmlType="submit" className="login-page-form_button">
                  Next
                </Button>
              </Form.Item>
            </>
          )}

          {/* ================= STEP 2 : ADMIN ================= */}
          {current === 1 && (
            <>
              <Form.Item
                name="adminFirstName"
                rules={[{ required: true, message: formatMessage({ id: 'gloabal.tips.enterAdminFirstNameMessage' }) }]}
              >
                <Input placeholder={formatMessage({ id: 'gloabal.tips.AdminFirstName' })} />
              </Form.Item>

              <Form.Item
                name="adminLastName"
                rules={[{ required: true, message: formatMessage({ id: 'gloabal.tips.enterAdminLastNameMessage' }) }]}
              >
                <Input placeholder={formatMessage({ id: 'gloabal.tips.AdminLastName' })} />
              </Form.Item>

              <Form.Item
                name="adminPhone"
                rules={[{ required: true, message: formatMessage({ id: 'gloabal.tips.enterAdminContactNumberMessage' }) }]}
              >
                <Input placeholder={formatMessage({ id: 'gloabal.tips.AdminContactNumber' })} />
              </Form.Item>

              <Form.Item
                name="adminEmail"
                rules={[{ required: true, message: formatMessage({ id: 'gloabal.tips.enterAdminEmailAddressMessage' }) }]}
              >
                <Input placeholder={formatMessage({ id: 'gloabal.tips.AdminEmailAddress' })} />
              </Form.Item>

              <Form.Item
                name="adminPassword"
                rules={[{ required: true, message: formatMessage({ id: 'gloabal.tips.enterAdminPasswordMessage' }) }]}
              >
                <Input.Password placeholder={formatMessage({ id: 'gloabal.tips.AdminPassword' })} />
              </Form.Item>

              <Form.Item name="remember" valuePropName="checked">
                <Checkbox>
                  <LocaleFormatter id="gloabal.tips.rememberUser" />
                </Checkbox>
              </Form.Item>

              <Form.Item>
                <div style={{ display: 'flex', gap: 5 }}>
                  <Button type="dashed" className="login-page-form_button" onClick={goPrev}>Previous</Button>
                  <Button type="primary" htmlType="submit" className="login-page-form_button">
                    <LocaleFormatter id="gloabal.tips.signup" />
                  </Button>
                </div>
              </Form.Item>

            </>
          )}
        </Form>
      </div>
    </div>
  );
};

export default Register;
