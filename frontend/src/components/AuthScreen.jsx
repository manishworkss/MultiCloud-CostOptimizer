import React, { useState, useContext } from 'react';
import { AuthContext } from '../context/AuthContext';
import { Cpu, CheckCircle2, Mail, AlertCircle, Info } from 'lucide-react';

const AuthScreen = () => {
  const { login, register, verifyEmailOtp, googleAuth } = useContext(AuthContext);

  const [isRegisterMode, setIsRegisterMode] = useState(false);
  const [isOtpStep, setIsOtpStep] = useState(false);

  const [name, setName] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [showPassword, setShowPassword] = useState(false);
  const [otpCode, setOtpCode] = useState('');
  const [devOtpHint, setDevOtpHint] = useState('');

  const [error, setError] = useState('');
  const [infoMsg, setInfoMsg] = useState('');
  const [submitting, setSubmitting] = useState(false);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    setInfoMsg('');
    setSubmitting(true);

    try {
      if (isOtpStep) {
        await verifyEmailOtp(email, otpCode);
      } else if (isRegisterMode) {
        const res = await register(name, email, password);
        if (res.emailVerificationRequired) {
          setIsOtpStep(true);
          setInfoMsg(`A 6-digit OTP code has been sent to your email (${email}). Please enter it below to complete registration.`);
          if (res.otpForDevTesting) {
            setDevOtpHint(res.otpForDevTesting);
          }
        }
      } else {
        const res = await login(email, password);
        if (res.emailVerificationRequired) {
          setIsOtpStep(true);
          setInfoMsg(`Your email is not verified yet. A 6-digit OTP code has been sent to ${email}.`);
          if (res.otpForDevTesting) {
            setDevOtpHint(res.otpForDevTesting);
          }
        }
      }
    } catch (err) {
      setError(err.response?.data?.message || 'Authentication request failed');
    } finally {
      setSubmitting(false);
    }
  };

  const handleGoogleSignIn = async () => {
    setError('');
    setSubmitting(true);
    try {
      await googleAuth({
        idToken: 'google-oauth2-token-' + Date.now(),
        email: email || 'user.google@enterprise.com',
        name: name || 'Google Enterprise User',
      });
    } catch (err) {
      setError('Google Sign In failed');
    } finally {
      setSubmitting(false);
    }
  };

  return (
    <div style={{
      minHeight: '100vh',
      width: '100vw',
      display: 'flex',
      alignItems: 'center',
      justifyContent: 'center',
      background: '#070a12',
      padding: '24px'
    }}>
      {/* Outer Split Card referencing Picture 2 */}
      <div style={{
        width: '100%',
        maxWidth: '1040px',
        minHeight: '620px',
        borderRadius: '24px',
        overflow: 'hidden',
        boxShadow: '0 25px 60px -15px rgba(0, 0, 0, 0.7), 0 0 30px rgba(6, 182, 212, 0.15)',
        display: 'grid',
        gridTemplateColumns: '1fr 1fr',
        border: '1px solid rgba(255, 255, 255, 0.08)',
        background: '#ffffff'
      }}>
        
        {/* LEFT PANEL: Dark Gradient Glass & Branding */}
        <div style={{
          background: 'linear-gradient(135deg, #090d16 0%, #111827 50%, #1e1b4b 100%)',
          padding: '48px 40px',
          display: 'flex',
          flexDirection: 'column',
          justifyContent: 'space-between',
          position: 'relative',
          color: '#ffffff'
        }}>
          {/* Top Logo Header */}
          <div style={{ display: 'flex', alignItems: 'center', gap: '12px' }}>
            <div style={{
              width: '42px',
              height: '42px',
              borderRadius: '12px',
              background: 'linear-gradient(135deg, #06b6d4 0%, #3b82f6 100%)',
              display: 'flex',
              alignItems: 'center',
              justifyContent: 'center',
              boxShadow: '0 0 20px rgba(6, 182, 212, 0.5)'
            }}>
              <Cpu size={24} color="#ffffff" />
            </div>
            <div>
              <h2 style={{ fontSize: '1.4rem', fontFamily: 'Outfit, sans-serif', letterSpacing: '0.5px' }}>
                Cost<span style={{ color: '#06b6d4' }}>Matrix</span>
              </h2>
              <p style={{ fontSize: '0.7rem', color: '#94a3b8', textTransform: 'uppercase', letterSpacing: '1px' }}>
                FinOps Placement Engine
              </p>
            </div>
          </div>

          {/* Center Hero Content */}
          <div style={{ margin: '40px 0' }}>
            <h1 style={{ fontSize: '2.4rem', fontWeight: 800, marginBottom: '16px', lineHeight: '1.2' }}>
              {isOtpStep ? 'Verify Email' : isRegisterMode ? 'Get Started' : 'Welcome Back'}
            </h1>
            <p style={{ color: '#94a3b8', fontSize: '0.95rem', lineHeight: '1.6', marginBottom: '28px' }}>
              Access your multi-cloud optimization platform. Streamline your infrastructure placement and control cloud costs with CostMatrix.
            </p>

            {/* Feature Highlights */}
            <div style={{ display: 'flex', flexDirection: 'column', gap: '14px' }}>
              <div style={{ display: 'flex', alignItems: 'center', gap: '10px', fontSize: '0.88rem', color: '#cbd5e1' }}>
                <CheckCircle2 size={18} color="#06b6d4" />
                <span>Live AWS, Azure, GCP &amp; OCI price ingestion</span>
              </div>
              <div style={{ display: 'flex', alignItems: 'center', gap: '10px', fontSize: '0.88rem', color: '#cbd5e1' }}>
                <CheckCircle2 size={18} color="#10b981" />
                <span>Email OTP verification &amp; TOTP 2FA security</span>
              </div>
              <div style={{ display: 'flex', alignItems: 'center', gap: '10px', fontSize: '0.88rem', color: '#cbd5e1' }}>
                <CheckCircle2 size={18} color="#8b5cf6" />
                <span>Weighted TCO &amp; SLA Uptime Recommendation Score</span>
              </div>
            </div>
          </div>

          <div style={{ fontSize: '0.75rem', color: '#64748b' }}>
            &copy; 2026 CostMatrix Platform. Enterprise Cloud Infrastructure Management.
          </div>
        </div>

        {/* RIGHT PANEL: Clean Realistic Sign In Card referencing Picture 2 */}
        <div style={{
          background: '#ffffff',
          padding: '48px 44px',
          display: 'flex',
          flexDirection: 'column',
          justifyContent: 'center',
          color: '#0f172a'
        }}>
          <h2 style={{ fontSize: '1.8rem', fontWeight: 700, color: '#0f172a', marginBottom: '8px', fontFamily: 'Outfit, sans-serif' }}>
            {isOtpStep ? 'Enter Email OTP' : isRegisterMode ? 'Create Your Account' : 'Sign In to Your Account'}
          </h2>
          <p style={{ fontSize: '0.85rem', color: '#64748b', marginBottom: '24px' }}>
            {isOtpStep ? 'Check your email inbox for the 6-digit verification code' : 'Enter your credentials to manage cloud infrastructure'}
          </p>

          {/* Alerts */}
          {error && (
            <div style={{
              background: '#fef2f2',
              border: '1px solid #fecaca',
              color: '#dc2626',
              padding: '12px 14px',
              borderRadius: '8px',
              fontSize: '0.85rem',
              marginBottom: '20px',
              display: 'flex',
              alignItems: 'center',
              gap: '8px'
            }}>
              <AlertCircle size={18} /> {error}
            </div>
          )}

          {infoMsg && (
            <div style={{
              background: '#f0fdf4',
              border: '1px solid #bbf7d0',
              color: '#15803d',
              padding: '12px 14px',
              borderRadius: '8px',
              fontSize: '0.85rem',
              marginBottom: '20px',
              display: 'flex',
              alignItems: 'center',
              gap: '8px'
            }}>
              <Mail size={18} /> {infoMsg}
            </div>
          )}

          {devOtpHint && (
            <div style={{
              background: '#eff6ff',
              border: '1px solid #bfdbfe',
              color: '#1d4ed8',
              padding: '10px 14px',
              borderRadius: '8px',
              fontSize: '0.85rem',
              marginBottom: '20px',
              display: 'flex',
              alignItems: 'center',
              gap: '8px'
            }}>
              <Info size={18} /> <span>Your 6-Digit Email Verification OTP is: <strong style={{ letterSpacing: '2px', fontSize: '1rem' }}>{devOtpHint}</strong></span>
            </div>
          )}

          {/* FORM */}
          <form onSubmit={handleSubmit}>
            {isOtpStep ? (
              /* OTP verification input */
              <div style={{ marginBottom: '20px' }}>
                <label style={{ display: 'block', fontSize: '0.85rem', fontWeight: 600, color: '#334155', marginBottom: '6px' }}>
                  6-Digit Verification OTP Code
                </label>
                <input
                  type="text"
                  placeholder="000000"
                  maxLength={6}
                  value={otpCode}
                  onChange={(e) => setOtpCode(e.target.value)}
                  style={{
                    width: '100%',
                    padding: '14px',
                    fontSize: '1.5rem',
                    textAlign: 'center',
                    letterSpacing: '8px',
                    fontWeight: 700,
                    borderRadius: '10px',
                    border: '1px solid #cbd5e1',
                    outline: 'none',
                    color: '#0f172a',
                    fontFamily: 'JetBrains Mono, monospace'
                  }}
                  required
                  autoFocus
                />
              </div>
            ) : (
              /* Standard login/register inputs */
              <>
                {isRegisterMode && (
                  <div style={{ marginBottom: '18px' }}>
                    <label style={{ display: 'block', fontSize: '0.85rem', fontWeight: 600, color: '#334155', marginBottom: '6px' }}>
                      Full Name
                    </label>
                    <input
                      type="text"
                      placeholder="Alex Mercer"
                      value={name}
                      onChange={(e) => setName(e.target.value)}
                      style={{
                        width: '100%',
                        padding: '12px 16px',
                        fontSize: '0.95rem',
                        borderRadius: '10px',
                        border: '1px solid #cbd5e1',
                        outline: 'none',
                        color: '#0f172a'
                      }}
                      required
                    />
                  </div>
                )}

                <div style={{ marginBottom: '18px' }}>
                  <label style={{ display: 'block', fontSize: '0.85rem', fontWeight: 600, color: '#334155', marginBottom: '6px' }}>
                    Email Address
                  </label>
                  <input
                    type="email"
                    placeholder="name@company.com"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    style={{
                      width: '100%',
                      padding: '12px 16px',
                      fontSize: '0.95rem',
                      borderRadius: '10px',
                      border: '1px solid #cbd5e1',
                      outline: 'none',
                      color: '#0f172a'
                    }}
                    required
                  />
                </div>

                <div style={{ marginBottom: '24px' }}>
                  <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '6px' }}>
                    <label style={{ fontSize: '0.85rem', fontWeight: 600, color: '#334155' }}>
                      Password
                    </label>
                  </div>

                  <div style={{ position: 'relative' }}>
                    <input
                      type={showPassword ? 'text' : 'password'}
                      placeholder="••••••••"
                      value={password}
                      onChange={(e) => setPassword(e.target.value)}
                      style={{
                        width: '100%',
                        padding: '12px 16px',
                        fontSize: '0.95rem',
                        borderRadius: '10px',
                        border: '1px solid #cbd5e1',
                        outline: 'none',
                        color: '#0f172a'
                      }}
                      required
                    />
                  </div>
                </div>
              </>
            )}

            {/* Primary Submit Button matching Picture 2 */}
            <button
              type="submit"
              disabled={submitting}
              style={{
                width: '100%',
                padding: '14px',
                borderRadius: '24px',
                background: '#0f172a',
                color: '#ffffff',
                border: 'none',
                fontSize: '0.95rem',
                fontWeight: 600,
                cursor: 'pointer',
                transition: 'all 0.2s ease',
                display: 'flex',
                alignItems: 'center',
                justifyContent: 'center',
                gap: '8px'
              }}
            >
              {submitting
                ? 'Processing...'
                : isOtpStep
                ? 'Verify Email & Log In'
                : isRegisterMode
                ? 'Register & Get Email OTP'
                : 'Sign In'}
            </button>
          </form>

          {/* Divider matching Picture 2 */}
          {!isOtpStep && (
            <>
              <div style={{ display: 'flex', alignItems: 'center', gap: '12px', margin: '24px 0' }}>
                <div style={{ flex: 1, height: '1px', background: '#e2e8f0' }}></div>
                <span style={{ fontSize: '0.8rem', color: '#94a3b8', fontWeight: 500 }}>or</span>
                <div style={{ flex: 1, height: '1px', background: '#e2e8f0' }}></div>
              </div>

              {/* Google OAuth 2.0 Sign In Button matching Picture 2 */}
              <button
                type="button"
                onClick={handleGoogleSignIn}
                disabled={submitting}
                style={{
                  width: '100%',
                  padding: '12px',
                  borderRadius: '24px',
                  background: '#ffffff',
                  border: '1px solid #cbd5e1',
                  color: '#0f172a',
                  fontSize: '0.9rem',
                  fontWeight: 600,
                  cursor: 'pointer',
                  display: 'flex',
                  alignItems: 'center',
                  justifyContent: 'center',
                  gap: '10px',
                  transition: 'all 0.2s ease'
                }}
              >
                <svg width="18" height="18" viewBox="0 0 24 24">
                  <path fill="#4285F4" d="M22.56 12.25c0-.78-.07-1.53-.2-2.25H12v4.26h5.92c-.26 1.37-1.04 2.53-2.21 3.31v2.77h3.57c2.08-1.92 3.28-4.74 3.28-8.09z"/>
                  <path fill="#34A853" d="M12 23c2.97 0 5.46-.98 7.28-2.66l-3.57-2.77c-.98.66-2.23 1.06-3.71 1.06-2.86 0-5.29-1.93-6.16-4.53H2.18v2.84C3.99 20.53 7.7 23 12 23z"/>
                  <path fill="#FBBC05" d="M5.84 14.09c-.22-.66-.35-1.36-.35-2.09s.13-1.43.35-2.09V7.06H2.18C1.43 8.55 1 10.22 1 12s.43 3.45 1.18 4.94l2.85-2.22.81-.63z"/>
                  <path fill="#EA4335" d="M12 5.38c1.62 0 3.06.56 4.21 1.64l3.15-3.15C17.45 2.09 14.97 1 12 1 7.7 1 3.99 3.47 2.18 7.06l3.66 2.84c.87-2.6 3.3-4.52 6.16-4.52z"/>
                </svg>
                Sign In with Google
              </button>
            </>
          )}

          {/* Footer toggle link */}
          <div style={{ textAlign: 'center', marginTop: '24px', fontSize: '0.85rem', color: '#64748b' }}>
            {isOtpStep ? (
              <button
                onClick={() => { setIsOtpStep(false); setError(''); setInfoMsg(''); setDevOtpHint(''); }}
                style={{ background: 'none', border: 'none', color: '#2563eb', cursor: 'pointer', fontWeight: 600 }}
              >
                &larr; Back to Sign In
              </button>
            ) : (
              <span>
                {isRegisterMode ? 'Already have an account?' : "Don't have an account?"}{' '}
                <button
                  onClick={() => { setIsRegisterMode(!isRegisterMode); setError(''); setInfoMsg(''); setDevOtpHint(''); }}
                  style={{ background: 'none', border: 'none', color: '#2563eb', cursor: 'pointer', fontWeight: 600 }}
                >
                  {isRegisterMode ? 'Sign In' : 'Register'}
                </button>
              </span>
            )}
          </div>
        </div>

      </div>
    </div>
  );
};

export default AuthScreen;
