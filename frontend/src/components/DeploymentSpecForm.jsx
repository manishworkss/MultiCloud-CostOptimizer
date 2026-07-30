import React, { useState } from 'react';
import { Cpu, HardDrive, Database, Globe, Sliders, Play } from 'lucide-react';

const DeploymentSpecForm = ({ onSubmitSpec, loading }) => {
  const [vcpu, setVcpu] = useState(4);
  const [ramGb, setRamGb] = useState(16);
  const [storageGb, setStorageGb] = useState(200);
  const [databaseEngine, setDatabaseEngine] = useState('POSTGRESQL');
  const [bandwidthGb, setBandwidthGb] = useState(500);
  const [targetRegion, setTargetRegion] = useState('us-east-1');

  const handleSubmit = (e) => {
    e.preventDefault();
    onSubmitSpec({
      cpu: `${vcpu} vCPU`,
      ram: `${ramGb} GB`,
      storage: `${storageGb} GB`,
      operatingSystem: 'Linux',
      databaseType: databaseEngine,
      bandwidth: `${bandwidthGb} GB`,
      region: targetRegion,
    });
  };

  return (
    <div className="glass-panel glow-box-cyan" style={{ padding: '24px', height: '100%', display: 'flex', flexDirection: 'column', justifyContent: 'space-between' }}>
      <div>
        <div style={{ display: 'flex', alignItems: 'center', gap: '10px', marginBottom: '18px', borderBottom: '1px solid rgba(255, 255, 255, 0.08)', paddingBottom: '14px' }}>
          <div style={{ background: 'rgba(6, 182, 212, 0.15)', padding: '8px', borderRadius: '8px', color: '#06b6d4' }}>
            <Sliders size={20} />
          </div>
          <div>
            <h3 style={{ fontSize: '1.05rem', color: 'var(--text-primary)', margin: 0 }}>Workload Configurator</h3>
            <p style={{ fontSize: '0.75rem', color: 'var(--text-secondary)', margin: 0 }}>Configure target compute, storage &amp; region</p>
          </div>
        </div>

        <form id="spec-form" onSubmit={handleSubmit} style={{ display: 'flex', flexDirection: 'column', gap: '14px' }}>
          {/* vCPU Cores */}
          <div>
            <label style={{ fontSize: '0.78rem', fontWeight: 600, color: 'var(--text-secondary)', marginBottom: '6px', display: 'flex', alignItems: 'center', gap: '6px' }}>
              <Cpu size={14} color="#06b6d4" /> vCPU Compute Cores
            </label>
            <select className="form-control" value={vcpu} onChange={(e) => setVcpu(e.target.value)} style={{ padding: '9px 12px', fontSize: '0.85rem' }}>
              <option value={2}>2 Cores (Small / Burstable)</option>
              <option value={4}>4 Cores (Medium Enterprise)</option>
              <option value={8}>8 Cores (Large Multi-threaded)</option>
              <option value={16}>16 Cores (High Memory Cluster)</option>
              <option value={32}>32 Cores (Ultra Compute Node)</option>
            </select>
          </div>

          {/* RAM Memory */}
          <div>
            <label style={{ fontSize: '0.78rem', fontWeight: 600, color: 'var(--text-secondary)', marginBottom: '6px', display: 'flex', alignItems: 'center', gap: '6px' }}>
              <Cpu size={14} color="#3b82f6" /> RAM Memory Capacity
            </label>
            <select className="form-control" value={ramGb} onChange={(e) => setRamGb(e.target.value)} style={{ padding: '9px 12px', fontSize: '0.85rem' }}>
              <option value={4}>4 GB</option>
              <option value={8}>8 GB</option>
              <option value={16}>16 GB</option>
              <option value={32}>32 GB</option>
              <option value={64}>64 GB</option>
              <option value={128}>128 GB</option>
            </select>
          </div>

          {/* Storage Volume */}
          <div>
            <label style={{ fontSize: '0.78rem', fontWeight: 600, color: 'var(--text-secondary)', marginBottom: '6px', display: 'flex', alignItems: 'center', gap: '6px' }}>
              <HardDrive size={14} color="#10b981" /> SSD Storage Disk
            </label>
            <select className="form-control" value={storageGb} onChange={(e) => setStorageGb(e.target.value)} style={{ padding: '9px 12px', fontSize: '0.85rem' }}>
              <option value={100}>100 GB High-Speed SSD</option>
              <option value={200}>200 GB High-Speed SSD</option>
              <option value={500}>500 GB High-Speed SSD</option>
              <option value={1000}>1,000 GB (1 TB) NVMe SSD</option>
              <option value={2000}>2,000 GB (2 TB) Enterprise SSD</option>
            </select>
          </div>

          {/* Database Engine */}
          <div>
            <label style={{ fontSize: '0.78rem', fontWeight: 600, color: 'var(--text-secondary)', marginBottom: '6px', display: 'flex', alignItems: 'center', gap: '6px' }}>
              <Database size={14} color="#8b5cf6" /> Managed Database Engine
            </label>
            <select className="form-control" value={databaseEngine} onChange={(e) => setDatabaseEngine(e.target.value)} style={{ padding: '9px 12px', fontSize: '0.85rem' }}>
              <option value="POSTGRESQL">Managed PostgreSQL</option>
              <option value="MYSQL">Managed MySQL 8.0</option>
              <option value="REDIS">Managed Redis Cache</option>
              <option value="NONE">No Database Needed</option>
            </select>
          </div>

          {/* Bandwidth Traffic */}
          <div>
            <label style={{ fontSize: '0.78rem', fontWeight: 600, color: 'var(--text-secondary)', marginBottom: '6px', display: 'flex', alignItems: 'center', gap: '6px' }}>
              <Globe size={14} color="#f59e0b" /> Egress Bandwidth Traffic
            </label>
            <select className="form-control" value={bandwidthGb} onChange={(e) => setBandwidthGb(e.target.value)} style={{ padding: '9px 12px', fontSize: '0.85rem' }}>
              <option value={100}>100 GB / month</option>
              <option value={500}>500 GB / month</option>
              <option value={1000}>1,000 GB (1 TB) / month</option>
              <option value={5000}>5,000 GB (5 TB) / month</option>
            </select>
          </div>

          {/* Deployment Region */}
          <div>
            <label style={{ fontSize: '0.78rem', fontWeight: 600, color: 'var(--text-secondary)', marginBottom: '6px', display: 'flex', alignItems: 'center', gap: '6px' }}>
              <Globe size={14} color="#ec4899" /> Target Deployment Region
            </label>
            <select className="form-control" value={targetRegion} onChange={(e) => setTargetRegion(e.target.value)} style={{ padding: '9px 12px', fontSize: '0.85rem' }}>
              <option value="us-east-1">US-East (N. Virginia)</option>
              <option value="us-west-2">US-West (Oregon)</option>
              <option value="eu-central-1">EU (Frankfurt)</option>
              <option value="ap-south-1">Asia Pacific (Mumbai)</option>
              <option value="ap-southeast-1">Asia Pacific (Singapore)</option>
            </select>
          </div>
        </form>
      </div>

      <div style={{ marginTop: '20px' }}>
        <button
          type="submit"
          form="spec-form"
          className="btn-primary"
          disabled={loading}
          style={{ width: '100%', justifyContent: 'center', padding: '12px', borderRadius: '10px' }}
        >
          {loading ? (
            'Evaluating Tariff APIs...'
          ) : (
            <>
              <Play size={16} /> Run Placement Engine
            </>
          )}
        </button>
      </div>
    </div>
  );
};

export default DeploymentSpecForm;
