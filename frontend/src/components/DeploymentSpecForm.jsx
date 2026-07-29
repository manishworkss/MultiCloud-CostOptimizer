import React, { useState } from 'react';
import { Cpu, HardDrive, Globe, Database, Server, Zap } from 'lucide-react';

const DeploymentSpecForm = ({ onSubmitSpec, loading }) => {
  const [cpu, setCpu] = useState('4 Cores');
  const [ram, setRam] = useState('16 GB');
  const [storage, setStorage] = useState('200 GB SSD');
  const [operatingSystem, setOperatingSystem] = useState('Ubuntu Linux 22.04 LTS');
  const [databaseType, setDatabaseType] = useState('PostgreSQL');
  const [bandwidth, setBandwidth] = useState('500 GB');
  const [region, setRegion] = useState('US-East (N. Virginia)');

  const handleSubmit = (e) => {
    e.preventDefault();
    onSubmitSpec({
      cpu,
      ram,
      storage,
      operatingSystem,
      databaseType,
      bandwidth,
      region,
      expectedUsers: 10000,
    });
  };

  return (
    <div className="glass-panel" style={{ padding: '28px' }}>
      <div style={{ display: 'flex', alignItems: 'center', gap: '12px', marginBottom: '20px' }}>
        <Zap color="#06b6d4" size={24} />
        <div>
          <h3 style={{ fontSize: '1.2rem' }}>Workload Hardware &amp; SLA Requirements</h3>
          <p style={{ fontSize: '0.8rem', color: '#94a3b8' }}>Specify specs to run multi-cloud optimization algorithm</p>
        </div>
      </div>

      <form onSubmit={handleSubmit}>
        <div style={{ display: 'grid', gridTemplateColumns: 'repeat(2, 1fr)', gap: '16px' }}>
          <div className="form-group">
            <label><Cpu size={14} /> vCPU Compute Cores</label>
            <select className="form-control" value={cpu} onChange={(e) => setCpu(e.target.value)}>
              <option value="2 Cores">2 Cores (Small)</option>
              <option value="4 Cores">4 Cores (Medium)</option>
              <option value="8 Cores">8 Cores (Large)</option>
              <option value="16 Cores">16 Cores (XL)</option>
            </select>
          </div>

          <div className="form-group">
            <label><Server size={14} /> RAM Memory</label>
            <select className="form-control" value={ram} onChange={(e) => setRam(e.target.value)}>
              <option value="8 GB">8 GB</option>
              <option value="16 GB">16 GB</option>
              <option value="32 GB">32 GB</option>
              <option value="64 GB">64 GB</option>
            </select>
          </div>

          <div className="form-group">
            <label><HardDrive size={14} /> Storage Disk Volume</label>
            <select className="form-control" value={storage} onChange={(e) => setStorage(e.target.value)}>
              <option value="100 GB SSD">100 GB High-Speed SSD</option>
              <option value="200 GB SSD">200 GB High-Speed SSD</option>
              <option value="500 GB SSD">500 GB High-Speed SSD</option>
              <option value="1000 GB SSD">1 TB NVMe SSD</option>
            </select>
          </div>

          <div className="form-group">
            <label><Database size={14} /> Managed Database Engine</label>
            <select className="form-control" value={databaseType} onChange={(e) => setDatabaseType(e.target.value)}>
              <option value="PostgreSQL">Managed PostgreSQL</option>
              <option value="MySQL">Managed MySQL</option>
              <option value="MongoDB">Document DB / MongoDB</option>
              <option value="Redis">In-Memory Redis Cache</option>
            </select>
          </div>

          <div className="form-group">
            <label><Globe size={14} /> Egress Bandwidth Traffic</label>
            <select className="form-control" value={bandwidth} onChange={(e) => setBandwidth(e.target.value)}>
              <option value="100 GB">100 GB / month</option>
              <option value="500 GB">500 GB / month</option>
              <option value="1000 GB">1 TB / month</option>
              <option value="5000 GB">5 TB / month</option>
            </select>
          </div>

          <div className="form-group">
            <label><Globe size={14} /> Target Deployment Region</label>
            <select className="form-control" value={region} onChange={(e) => setRegion(e.target.value)}>
              <option value="US-East (N. Virginia)">US-East (N. Virginia)</option>
              <option value="US-West (Oregon)">US-West (Oregon)</option>
              <option value="EU-Central (Frankfurt)">EU-Central (Frankfurt)</option>
              <option value="AP-South (Mumbai)">AP-South (Mumbai)</option>
            </select>
          </div>
        </div>

        <button type="submit" className="btn-primary" disabled={loading} style={{ width: '100%', marginTop: '16px', justifyContent: 'center', padding: '14px' }}>
          {loading ? 'Fetching CSP Pricing & Evaluating...' : '⚡ Run Multi-Cloud Cost Optimization Engine'}
        </button>
      </form>
    </div>
  );
};

export default DeploymentSpecForm;
