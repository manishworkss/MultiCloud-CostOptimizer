import React from 'react';
import { Cloud, Server, Database, Activity } from 'lucide-react';

const CloudTariffsDashboard = () => {
  return (
    <div className="glass-panel" style={{ padding: '32px' }}>
      <div style={{ display: 'flex', alignItems: 'center', gap: '12px', marginBottom: '24px' }}>
        <div style={{ background: 'rgba(59, 130, 246, 0.15)', padding: '12px', borderRadius: '12px', color: '#3b82f6' }}>
          <Cloud size={28} />
        </div>
        <div>
          <h2 style={{ fontSize: '1.4rem', color: '#f8fafc', margin: 0 }}>Cloud Provider Tariffs</h2>
          <p style={{ color: '#94a3b8', fontSize: '0.85rem', margin: 0 }}>View raw public retail pricing matrices for AWS, Azure, GCP, and OCI.</p>
        </div>
      </div>

      <div style={{ display: 'grid', gridTemplateColumns: 'repeat(2, 1fr)', gap: '20px' }}>
        {[
          { name: 'Amazon Web Services', desc: 'EC2, RDS, and EBS pricing', color: '#ff9900' },
          { name: 'Microsoft Azure', desc: 'Virtual Machines & Managed DB', color: '#0089d6' },
          { name: 'Google Cloud', desc: 'Compute Engine pricing', color: '#4285f4' },
          { name: 'Oracle Cloud', desc: 'OCI Compute & Block Storage', color: '#c74634' }
        ].map((provider) => (
          <div key={provider.name} style={{ border: `1px solid rgba(255,255,255,0.08)`, borderRadius: '12px', padding: '24px', background: 'rgba(17, 24, 39, 0.4)' }}>
            <h3 style={{ color: provider.color, margin: '0 0 8px 0' }}>{provider.name}</h3>
            <p style={{ color: '#94a3b8', fontSize: '0.85rem', margin: '0 0 16px 0' }}>{provider.desc}</p>
            <div style={{ display: 'flex', gap: '12px', color: '#64748b', fontSize: '0.75rem' }}>
              <span style={{ display: 'flex', alignItems: 'center', gap: '4px' }}><Server size={14}/> Compute</span>
              <span style={{ display: 'flex', alignItems: 'center', gap: '4px' }}><Database size={14}/> Storage</span>
              <span style={{ display: 'flex', alignItems: 'center', gap: '4px' }}><Activity size={14}/> Live API</span>
            </div>
            <button className="btn-outline" style={{ marginTop: '20px', width: '100%' }}>View Full Pricing Matrix</button>
          </div>
        ))}
      </div>
    </div>
  );
};

export default CloudTariffsDashboard;
