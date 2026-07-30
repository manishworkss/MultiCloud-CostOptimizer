import React from 'react';
import { LayoutDashboard, Cpu, Cloud, FileSpreadsheet, ShieldCheck, Settings, Server, TrendingDown, Info } from 'lucide-react';

const Sidebar = ({ activeTab, setActiveTab }) => {
  const navItems = [
    { id: 'about', label: 'Platform Overview', icon: Info },
    { id: 'placement', label: 'Placement Engine', icon: LayoutDashboard },
    { id: 'providers', label: 'Cloud Tariffs', icon: Cloud },
    { id: 'reports', label: 'Audit Reports', icon: FileSpreadsheet },
  ];

  return (
    <aside style={{
      width: '240px',
      background: 'var(--bg-canvas)',
      borderRight: '1px solid var(--border-color)',
      display: 'flex',
      flexDirection: 'column',
      justifyContent: 'space-between',
      padding: '24px 16px',
      minHeight: 'calc(100vh - 65px)',
      flexShrink: 0
    }}>
      <div style={{ display: 'flex', flexDirection: 'column', gap: '24px' }}>
        {/* Section Label */}
        <div style={{ fontSize: '0.7rem', fontWeight: 700, color: 'var(--text-muted)', textTransform: 'uppercase', letterSpacing: '1px', paddingLeft: '12px' }}>
          Platform Navigation
        </div>

        {/* Nav Links */}
        <div style={{ display: 'flex', flexDirection: 'column', gap: '6px' }}>
          {navItems.map((item) => {
            const Icon = item.icon;
            const isActive = activeTab === item.id;
            return (
              <button
                key={item.id}
                onClick={() => setActiveTab(item.id)}
                style={{
                  display: 'flex',
                  alignItems: 'center',
                  gap: '12px',
                  padding: '12px 14px',
                  borderRadius: '10px',
                  border: 'none',
                  background: isActive ? 'linear-gradient(135deg, rgba(6, 182, 212, 0.15) 0%, rgba(59, 130, 246, 0.15) 100%)' : 'transparent',
                  color: isActive ? '#06b6d4' : 'var(--text-secondary)',
                  fontWeight: isActive ? 600 : 500,
                  fontSize: '0.9rem',
                  cursor: 'pointer',
                  transition: 'all 0.2s ease',
                  textAlign: 'left',
                  borderLeft: isActive ? '3px solid #06b6d4' : '3px solid transparent'
                }}
              >
                <Icon size={18} color={isActive ? '#06b6d4' : 'var(--text-muted)'} />
                <span>{item.label}</span>
              </button>
            );
          })}
        </div>
      </div>

      {/* Live Provider Health Card */}
      <div style={{
        background: 'var(--bg-surface)',
        border: '1px solid rgba(255, 255, 255, 0.05)',
        borderRadius: '12px',
        padding: '14px',
        color: 'var(--text-secondary)',
        fontSize: '0.78rem'
      }}>
        <div style={{ display: 'flex', alignItems: 'center', gap: '8px', marginBottom: '8px', color: '#10b981', fontWeight: 600 }}>
          <span style={{ width: '8px', height: '8px', borderRadius: '50%', background: '#10b981', boxShadow: '0 0 8px #10b981' }}></span>
          <span>CSP Endpoints Active</span>
        </div>
        <div style={{ display: 'flex', justifyContent: 'space-between', color: 'var(--text-secondary)', fontSize: '0.72rem', marginTop: '4px' }}>
          <span>AWS / Azure / GCP / OCI</span>
          <span style={{ color: '#10b981' }}>100%</span>
        </div>
      </div>
    </aside>
  );
};

export default Sidebar;
