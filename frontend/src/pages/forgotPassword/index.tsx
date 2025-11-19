import type { CompanyRegisterParams, ForgotPasswordData, ForgotPasswordParams, LoginParams, OtpVerificationParams, ResetPasswordParams } from '@/interface/user/login';
import { useEffect, useState, type FC } from 'react';

import './index.less';

import { Button, Checkbox, Form, Input, Steps, theme as antTheme } from 'antd';
import { useDispatch } from 'react-redux';
import { useLocation, useNavigate } from 'react-router-dom';

import { LocaleFormatter, useLocale } from '@/locales';
import { formatSearch } from '@/utils/formatSearch';

import { companyRegisterAsync, ForgotPasswordRequestAsync, OtpVerificationAsync, ResetPasswordAsync } from '../../stores/user.action';

const ForgotPassword: FC = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const dispatch = useDispatch();
  const { formatMessage } = useLocale();
  const { token } = antTheme.useToken();

  const [current, setCurrent] = useState(0);
  const [forgotPasswordData, setForgotPasswordData] = useState<ForgotPasswordData>({});

  const [form] = Form.useForm();

  // Auto-fill fields when step changes
  useEffect(() => {
    if (current === 1) {
      // Moving to OTP step → fill email from step 0
      form.setFieldsValue({
        email: forgotPasswordData.yourEmail,
      });
    }

    if (current === 2) {
      // Moving to Reset Password step → fill email + otp from step 1
      form.setFieldsValue({
        email: forgotPasswordData.email,
        otp: forgotPasswordData.otp,
      });
    }
  }, [current]);

  // NEXT → send data
  const handleNext = async (values: any) => {
    setForgotPasswordData(values);

    if (current === 0) {
      const forgotPasswordReq: ForgotPasswordParams = {
        ...values,
      };
      const success = await dispatch(ForgotPasswordRequestAsync(forgotPasswordReq));
      if (!!success) {
        setCurrent(1);
      }
    }

    if (current === 1) {
      const otpVerificationReq: OtpVerificationParams = {
        ...values,
      };
      const success = await dispatch(OtpVerificationAsync(otpVerificationReq));
      if (!!success) {
        setCurrent(2);
      }
    }
  };

  // const goPrev = () => {
  //   form.setFieldsValue(forgotPasswordData);
  //   setCurrent(current - 1);
  // };

  // SUBMIT → final
  const handleSubmit = async (values: ResetPasswordParams) => {
    const fullData = {
      ...forgotPasswordData,
      ...values,
    };

    const success = await dispatch(ResetPasswordAsync(fullData));

    if (!!success) {
      navigate('/login');
    }
  };

  return (
    <div className="forgot-password-page-style" style={{ backgroundColor: token.colorBgContainer }}>
      <div className="forgot-password-page-style-form">
        <Form<ResetPasswordParams>
          form={form}
          layout="vertical"
          onFinish={{
            0: handleNext,
            1: handleNext,
            2: handleSubmit,
          }[current]}
          style={{
            width: '100%'
          }}
        >
          <h2 style={{ width: "100%", textAlign: "center" }}>Forgot Password</h2>
          <Steps current={current} style={{ marginBottom: 30 }}>
            <Steps.Step title="Request" />
            <Steps.Step title="OTP" />
            <Steps.Step title="Reset Password" />
          </Steps>
          {/* ================= STEP 1 : Request ================= */}
          {current === 0 && (
            <>
              <Form.Item
                name="yourEmail"
                rules={[{ required: true, message: formatMessage({ id: 'gloabal.tips.enterYourEmailMessage' }) }]}
              >
                <Input placeholder={formatMessage({ id: 'gloabal.tips.YourEmail' })} />
              </Form.Item>

              <Form.Item
                name="companyEmail"
                rules={[{ required: true, message: formatMessage({ id: 'gloabal.tips.enterCompanyEmailMessage' }) }]}
              >
                <Input placeholder={formatMessage({ id: 'gloabal.tips.FCompanyEmail' })} />
              </Form.Item>

              <Form.Item>
                <Button type="primary" htmlType="submit" className="forgot-password-page-style-form_button">
                  <LocaleFormatter id="gloabal.tips.Next" />
                </Button>
              </Form.Item>
            </>
          )}

          {/* ================= STEP 2 : OTP Verification ================= */}
          {current === 1 && (
            <>
              <Form.Item
                name="email"
                rules={[{ required: true, message: formatMessage({ id: 'gloabal.tips.enterYourEmailMessage' }) }]}
              >
                <Input disabled={true} placeholder={formatMessage({ id: 'gloabal.tips.YourEmail' })} />
              </Form.Item>

              <Form.Item
                name="otp"
                rules={[{ required: true, message: formatMessage({ id: 'gloabal.tips.enterOtpMessage' }) }]}
              >
                <Input placeholder={formatMessage({ id: 'gloabal.tips.Otp' })} />
              </Form.Item>

              <Form.Item>
                {/* <div style={{ display: 'flex', gap: 5 }}>
                  <Button type="dashed" className="forgot-password-page-style-form_button" onClick={goPrev}>
                    <LocaleFormatter id="gloabal.tips.Previous" />
                  </Button>
                  <Button type="primary" htmlType="submit" className="forgot-password-page-style-form_button">
                    <LocaleFormatter id="gloabal.tips.Next" />
                  </Button>
                </div> */}
                <Button type="primary" htmlType="submit" className="forgot-password-page-style-form_button">
                  <LocaleFormatter id="gloabal.tips.Next" />
                </Button>
              </Form.Item>

            </>
          )}

          {/* ================= STEP 3 : Reset Password ================= */}
          {current === 2 && (
            <>
              <Form.Item
                name="email"
                rules={[{ required: true, message: formatMessage({ id: 'gloabal.tips.enterYourEmailMessage' }) }]}
              >
                <Input disabled={true} placeholder={formatMessage({ id: 'gloabal.tips.YourEmail' })} />
              </Form.Item>

              <Form.Item
                name="otp"
                rules={[{ required: true, message: formatMessage({ id: 'gloabal.tips.enterOtpMessage' }) }]}
              >
                <Input disabled={true} placeholder={formatMessage({ id: 'gloabal.tips.Otp' })} />
              </Form.Item>

              <Form.Item
                name="newPassword"
                rules={[{ required: true, message: formatMessage({ id: 'gloabal.tips.enterNewPasswordMessage' }) }]}
              >
                <Input.Password placeholder={formatMessage({ id: 'gloabal.tips.NewPassword' })} />
              </Form.Item>

              <Form.Item>
                {/* <div style={{ display: 'flex', gap: 5 }}>
                  <Button type="dashed" className="forgot-password-page-style-form_button" onClick={goPrev}>
                    <LocaleFormatter id="gloabal.tips.Previous" />
                  </Button>
                  <Button type="primary" htmlType="submit" className="forgot-password-page-style-form_button">
                    <LocaleFormatter id="gloabal.tips.Submit" />
                  </Button>
                </div> */}
                <Button type="primary" htmlType="submit" className="forgot-password-page-style-form_button">
                  <LocaleFormatter id="gloabal.tips.Submit" />
                </Button>
              </Form.Item>

            </>
          )}
        </Form>
      </div>
    </div>
  );
};

export default ForgotPassword;
