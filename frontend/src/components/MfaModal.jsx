import React, { useState, useEffect, useContext } from 'react';
import { QRCodeSVG } from 'qrcode.react';
import { AuthContext } from '../context/AuthContext';
import { ShieldCheck, Key, Copy, Check, AlertCircle } from 'lucide-react';

const MfaModal = ({ isOpen, onClose, isSetupMode = false }) => {
  const { setupMfa, verifyMfa } = useContext(AuthContext);
  const [setupData, setSetupData] = useState(null);
  const [otpCode, setOtpCode] = useState('');
  const [error, setError] = useState('');
  const [copied, setCopied] = useState(false);
  const [submitting, setSubmitting] = useState(false);

  useEffect(() => {
    if (isOpen && isSetupMode && !setupData) {
      setupMfa()
        .then((data) => setSetupData(data))
        .catch((err) => setError('Failed to initialize 2FA setup'));
    }
  }, [isOpen, isSetupMode]);

  if (!isOpen) return null;

  const handleVerify = async (e) => {
    e.preventDefault();
    setError('');
    setSubmitting(true);
    try {
      await verifyMfa(otpCode);
      onClose();
    } catch (err) {
      setError(err.response?.data?.message || 'Invalid 6-digit TOTP code');
    } finally {
      setSubmitting(false);
    }
  };

  const copySecret = () => {
    if (setupData?.secretKey) {
      navigator.clipboard.writeText(setupData.secretKey);
      setCopied(true);
      setTimeout(() => setCopied(false), 2000);
    }
  };

  return (
    <div style={{
      position: 'fixed',
      inset: 0,
      background: 'rgba(9, 13, 22, 0.85)',
      backdropFilter: 'blur(8px)',
      display: 'flex',
      alignItems: 'center',
      justifyContent: 'center',
      zIndex: 1000
    }}>
      <div className="glass-panel glow-box-cyan" style={{ padding: '32px', width: '100%', maxWidth: '460px' }}>
        <div style={{ display: 'flex', alignItems: 'center', gap: '12px', marginBottom: '20px' }}>
          <ShieldCheck size={28} color="#06b6d4" />
          <div>
            <h3 style={{ fontSize: '1.25rem' }}>
              {isSetupMode ? 'Configure 2-Factor Authentication' : '2FA Verification Required'}
            </h3>
            <p style={{ fontSize: '0.8rem', color: '#94a3b8' }}>
              Google Authenticator / Authy TOTP
            </p>
          </div>
        </div>

        {error && (
          <div style={{
            background: 'rgba(244, 63, 94, 0.15)',
            border: '1px solid #f43f5e',
            color: '#f43f5e',
            padding: '10px',
            borderRadius: '6px',
            fontSize: '0.85rem',
            marginBottom: '16px',
            display: 'flex',
            alignItems: 'center',
            gap: '8px'
          }}>
            <AlertCircle size={16} /> {error}
          </div>
        )}

        {isSetupMode && setupData && (
          <div style={{ textAlign: 'center', marginBottom: '20px' }}>
            <div style={{
              background: '#ffffff',
              padding: '16px',
              borderRadius: '12px',
              display: 'inline-block',
              marginBottom: '16px'
            }}>
              <QRCodeSVG value={setupData.qrCodeUrl} size={180} />
            </div>

            <div style={{ background: '#0b0f19', padding: '10px', borderRadius: '6px', fontSize: '0.85rem', display: 'flex', alignItems: 'center', justifyContent: 'space-between', border: '1px solid #1e293b' }}>
              <span className="mono" style={{ color: '#06b6d4', fontWeight: 600 }}>{setupData.secretKey}</span>
              <button onClick={copySecret} className="btn-outline" style={{ padding: '4px 8px', fontSize: '0.75rem' }}>
                {copied ? <Check size={14} color="#10b981" /> : <Copy size={14} />}
              </button>
            </div>
          </div>
        )}

        <form onSubmit={handleVerify}>
          <div className="form-group">
            <label>Enter 6-Digit Authenticator Code</label>
            <input
              type="text"
              className="form-control mono"
              placeholder="000000"
              maxLength={6}
              value={otpCode}
              onChange={(e) => setOtpCode(e.target.value)}
              style={{ textAlign: 'center', fontSize: '1.5rem', letterSpacing: '8px', color: '#06b6d4' }}
              required
              autoFocus
            />
          </div>

          <div style={{ display: 'flex', gap: '12px', marginTop: '24px' }}>
            <button type="button" className="btn-outline" onClick={onClose} style={{ flex: 1 }}>
              Cancel
            </button>
            <button type="submit" className="btn-primary" disabled={submitting} style={{ flex: 1, justifyContent: 'center' }}>
              {submitting ? 'Verifying...' : 'Verify & Continue'}
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default MfaModal;
