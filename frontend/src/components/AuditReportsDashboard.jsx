import React from 'react';
import { FileSpreadsheet, Download, FileText, Search } from 'lucide-react';

const AuditReportsDashboard = () => {
  return (
    <div className="glass-panel" style={{ padding: '32px' }}>
      <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'space-between', marginBottom: '24px' }}>
        <div style={{ display: 'flex', alignItems: 'center', gap: '12px' }}>
          <div style={{ background: 'rgba(16, 185, 129, 0.15)', padding: '12px', borderRadius: '12px', color: '#10b981' }}>
            <FileSpreadsheet size={28} />
          </div>
          <div>
            <h2 style={{ fontSize: '1.4rem', color: '#f8fafc', margin: 0 }}>Audit Reports History</h2>
            <p style={{ color: '#94a3b8', fontSize: '0.85rem', margin: 0 }}>Download past PDF and CSV placement engine evaluations.</p>
          </div>
        </div>
        <div style={{ position: 'relative' }}>
          <Search size={16} color="#64748b" style={{ position: 'absolute', left: '12px', top: '10px' }} />
          <input type="text" placeholder="Search reports..." className="form-control" style={{ paddingLeft: '36px', width: '250px' }} />
        </div>
      </div>

      <div style={{ background: 'rgba(17, 24, 39, 0.4)', borderRadius: '12px', border: '1px solid rgba(255,255,255,0.05)', overflow: 'hidden' }}>
        <table style={{ width: '100%', textAlign: 'left', borderCollapse: 'collapse' }}>
          <thead>
            <tr style={{ background: 'rgba(255,255,255,0.03)', color: '#94a3b8', fontSize: '0.8rem', textTransform: 'uppercase' }}>
              <th style={{ padding: '16px' }}>Date</th>
              <th style={{ padding: '16px' }}>Project Request</th>
              <th style={{ padding: '16px' }}>Optimal Placement</th>
              <th style={{ padding: '16px' }}>Actions</th>
            </tr>
          </thead>
          <tbody>
            {[
              { date: 'Today, 10:45 AM', name: 'Web Microservices', winner: 'Oracle Cloud (OCI)', id: 'req-1' },
              { date: 'Yesterday, 03:20 PM', name: 'AI Data Pipeline', winner: 'AWS EC2', id: 'req-2' },
              { date: 'July 25, 09:15 AM', name: 'High-Traffic DB', winner: 'Microsoft Azure', id: 'req-3' }
            ].map((report, idx) => (
              <tr key={idx} style={{ borderTop: '1px solid rgba(255,255,255,0.05)', color: '#cbd5e1', fontSize: '0.9rem' }}>
                <td style={{ padding: '16px' }}>{report.date}</td>
                <td style={{ padding: '16px', fontWeight: 500 }}>{report.name}</td>
                <td style={{ padding: '16px' }}>
                  <span className="badge badge-emerald" style={{ background: 'rgba(16, 185, 129, 0.1)', color: '#10b981', border: 'none' }}>
                    {report.winner}
                  </span>
                </td>
                <td style={{ padding: '16px' }}>
                  <div style={{ display: 'flex', gap: '8px' }}>
                    <button className="btn-outline" style={{ padding: '6px 10px', fontSize: '0.75rem', display: 'flex', alignItems: 'center', gap: '4px' }}>
                      <Download size={14}/> PDF
                    </button>
                    <button className="btn-outline" style={{ padding: '6px 10px', fontSize: '0.75rem', display: 'flex', alignItems: 'center', gap: '4px' }}>
                      <FileText size={14}/> CSV
                    </button>
                  </div>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default AuditReportsDashboard;
