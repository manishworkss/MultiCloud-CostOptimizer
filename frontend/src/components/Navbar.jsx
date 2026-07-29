import React, { useContext } from 'react';
import { AuthContext } from '../context/AuthContext';
import { ShieldCheck, Cpu, LogOut, User as UserIcon } from 'lucide-react';

const Navbar = () => {
  const { user, logout } = useContext(AuthContext);

  return (
    <nav style={{
      background: 'rgba(17, 24, 39, 0.9)',
      borderBottom: '1px solid #1e293b',
      padding: '16px 32px',
      display: 'flex',
      alignItems: 'center',
      justifyContent: 'space-between',
      backdropFilter: 'blur(12px)',
      position: 'sticky',
      top: 0,
      zIndex: 100
    }}>
      <div style={{ display: 'flex', alignItems: 'center', gap: '12px' }}>
        <div style={{
          width: '38px',
          height: '38px',
          borderRadius: '8px',
          background: 'linear-gradient(135deg, #06b6d4 0%, #3b82f6 100%)',
          display: 'flex',
          alignItems: 'center',
          justifyContent: 'center',
          boxShadow: '0 0 16px rgba(6, 182, 212, 0.4)'
        }}>
          <Cpu size={22} color="#ffffff" />
        </div>
        <div>
          <h2 style={{ fontSize: '1.25rem', fontFamily: 'Outfit, sans-serif', letterSpacing: '0.5px' }}>
            Cost<span style={{ color: '#06b6d4' }}>Matrix</span>
          </h2>
          <p style={{ fontSize: '0.7rem', color: '#64748b', textTransform: 'uppercase', letterSpacing: '1px' }}>
            FinOps Placement Engine
          </p>
        </div>
      </div>

      {user && (
        <div style={{ display: 'flex', alignItems: 'center', gap: '16px' }}>
          {user.mfaEnabled && (
            <span className="badge badge-emerald" style={{ display: 'flex', alignItems: 'center', gap: '4px' }}>
              <ShieldCheck size={14} /> 2FA Verified
            </span>
          )}

          <div style={{ display: 'flex', alignItems: 'center', gap: '8px', borderLeft: '1px solid #1e293b', paddingLeft: '16px' }}>
            <UserIcon size={18} color="#94a3b8" />
            <span style={{ fontSize: '0.9rem', fontWeight: 600, color: '#f8fafc' }}>{user.name}</span>
          </div>

          <button className="btn-outline" onClick={logout} style={{ padding: '6px 12px', fontSize: '0.8rem' }}>
            <LogOut size={14} /> Logout
          </button>
        </div>
      )}
    </nav>
  );
};

export default Navbar;
