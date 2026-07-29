import React, { useContext } from 'react';
import { AuthContext } from '../context/AuthContext';
import { ShieldCheck, Smartphone, Key, AlertTriangle } from 'lucide-react';

const SecurityDashboard = ({ onOpenMfaSetup }) => {
  const { user } = useContext(AuthContext);

  return (
    <div className="glass-panel" style={{ padding: '32px' }}>
      <div style={{ display: 'flex', alignItems: 'center', gap: '12px', marginBottom: '32px' }}>
        <div style={{ background: 'rgba(139, 92, 246, 0.15)', padding: '12px', borderRadius: '12px', color: '#8b5cf6' }}>
          <ShieldCheck size={28} />
        </div>
        <div>
          <h2 style={{ fontSize: '1.4rem', color: '#f8fafc', margin: 0 }}>Security &amp; Authenticator</h2>
          <p style={{ color: '#94a3b8', fontSize: '0.85rem', margin: 0 }}>Manage your Two-Factor Authentication (2FA) and account security.</p>
        </div>
      </div>

      <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '24px' }}>
        {/* Account Info */}
        <div style={{ background: 'rgba(17, 24, 39, 0.4)', borderRadius: '12px', padding: '24px', border: '1px solid rgba(255,255,255,0.05)' }}>
          <h3 style={{ color: '#f8fafc', fontSize: '1.1rem', marginTop: 0, marginBottom: '20px', display: 'flex', alignItems: 'center', gap: '8px' }}>
            <Key size={18} color="#06b6d4" /> Profile Credentials
          </h3>
          <div style={{ display: 'flex', flexDirection: 'column', gap: '16px' }}>
            <div>
              <div style={{ color: '#64748b', fontSize: '0.75rem', textTransform: 'uppercase' }}>Full Name</div>
              <div style={{ color: '#f8fafc', fontSize: '0.95rem' }}>{user.fullName}</div>
            </div>
            <div>
              <div style={{ color: '#64748b', fontSize: '0.75rem', textTransform: 'uppercase' }}>Email Address</div>
              <div style={{ color: '#f8fafc', fontSize: '0.95rem' }}>{user.email}</div>
            </div>
            <div>
              <div style={{ color: '#64748b', fontSize: '0.75rem', textTransform: 'uppercase' }}>OAuth Provider</div>
              <div style={{ display: 'flex', alignItems: 'center', gap: '6px', color: '#cbd5e1', fontSize: '0.85rem', marginTop: '4px' }}>
                <img src="https://upload.wikimedia.org/wikipedia/commons/c/c1/Google_%22G%22_logo.svg" alt="Google" width="14" />
                Linked via Google OAuth 2.0
              </div>
            </div>
          </div>
        </div>

        {/* 2FA Status */}
        <div style={{ background: 'rgba(17, 24, 39, 0.4)', borderRadius: '12px', padding: '24px', border: '1px solid rgba(255,255,255,0.05)' }}>
          <h3 style={{ color: '#f8fafc', fontSize: '1.1rem', marginTop: 0, marginBottom: '20px', display: 'flex', alignItems: 'center', gap: '8px' }}>
            <Smartphone size={18} color="#10b981" /> Two-Factor Authentication (2FA)
          </h3>
          
          <div style={{ background: 'rgba(16, 185, 129, 0.1)', border: '1px solid rgba(16, 185, 129, 0.2)', padding: '16px', borderRadius: '8px', marginBottom: '20px' }}>
            <div style={{ display: 'flex', alignItems: 'center', gap: '10px', color: '#10b981', fontWeight: 600, marginBottom: '6px' }}>
              <ShieldCheck size={18} /> TOTP Authenticator
            </div>
            <p style={{ color: '#94a3b8', fontSize: '0.85rem', margin: 0, lineHeight: 1.5 }}>
              Enhance your account security by requiring a time-based one-time password (TOTP) from Google Authenticator or Authy when logging in.
            </p>
          </div>

          <button 
            className="btn-primary" 
            style={{ width: '100%', padding: '12px', justifyContent: 'center' }}
            onClick={onOpenMfaSetup}
          >
            Setup / Manage Authenticator App
          </button>
        </div>
      </div>
    </div>
  );
};

export default SecurityDashboard;
