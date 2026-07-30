import React from 'react';
import { BarChart, Bar, XAxis, YAxis, Tooltip, ResponsiveContainer, Cell } from 'recharts';
import { Award, Download, FileText, CheckCircle2, ShieldCheck } from 'lucide-react';
import api from '../services/api';

const PROVIDER_COLORS = {
  AWS: '#ff9900',
  AZURE: '#0089d6',
  GCP: '#4285f4',
  OCI: '#c74634',
};

const CostComparisonDashboard = ({ recommendations, requestId }) => {
  if (!recommendations || recommendations.length === 0) {
    return (
      <div className="glass-panel" style={{ padding: '48px', textAlign: 'center', color: 'var(--text-muted)' }}>
        <h3 style={{ fontSize: '1.2rem', color: 'var(--text-secondary)', marginBottom: '8px' }}>Placement Engine Ready</h3>
        <p style={{ fontSize: '0.85rem' }}>Select your hardware &amp; regional requirements on the left and click <strong>Run Placement Engine</strong> to calculate live multi-cloud cost benchmarks.</p>
      </div>
    );
  }

  const winner = recommendations[0];
  const totalAnnualSavings = winner.estimatedSavings ? (winner.estimatedSavings * 12).toFixed(2) : '0.00';

  const chartData = recommendations.map((rec) => ({
    name: rec.providerName,
    providerId: rec.providerId,
    monthlyCost: parseFloat(rec.totalMonthlyCost),
    score: parseFloat(rec.recommendationScore),
  }));

  const handleDownloadPdf = async () => {
    try {
      const response = await api.get(`/reports/pdf/${requestId}`, {
        responseType: 'blob',
      });
      const url = window.URL.createObjectURL(new Blob([response.data], { type: 'application/pdf' }));
      const link = document.createElement('a');
      link.href = url;
      link.setAttribute('download', `CostMatrix_Report_${requestId}.pdf`);
      document.body.appendChild(link);
      link.click();
      link.remove();
    } catch (err) {
      alert('Failed to download PDF report');
    }
  };

  const handleDownloadCsv = async () => {
    try {
      const response = await api.get(`/reports/csv/${requestId}`, {
        responseType: 'blob',
      });
      const url = window.URL.createObjectURL(new Blob([response.data], { type: 'application/csv' }));
      const link = document.createElement('a');
      link.href = url;
      link.setAttribute('download', `CostMatrix_Report_${requestId}.csv`);
      document.body.appendChild(link);
      link.click();
      link.remove();
    } catch (err) {
      alert('Failed to download CSV report');
    }
  };

  return (
    <div style={{ display: 'flex', flexDirection: 'column', gap: '20px' }}>
      
      {/* Top Winner Card Banner */}
      <div className="glass-panel glow-box-emerald" style={{ padding: '20px 24px', display: 'flex', alignItems: 'center', justifyContent: 'space-between' }}>
        <div style={{ display: 'flex', alignItems: 'center', gap: '16px' }}>
          <div style={{ background: 'rgba(16, 185, 129, 0.18)', padding: '12px', borderRadius: '12px', color: '#10b981' }}>
            <Award size={32} />
          </div>
          <div>
            <div className="badge badge-emerald" style={{ marginBottom: '4px', fontSize: '0.72rem', display: 'inline-block' }}>
              #1 Optimal Placement Recommendation
            </div>
            <h2 style={{ fontSize: '1.3rem', color: 'var(--text-primary)' }}>
              {winner.providerName} ({winner.serviceName})
            </h2>
            <p style={{ color: 'var(--text-secondary)', fontSize: '0.82rem', margin: 0 }}>
              Recommendation Score: <strong style={{ color: '#10b981' }}>{winner.recommendationScore} / 100</strong> | Region SLA Uptime: <strong>{winner.regionSlaUptime}%</strong>
            </p>
          </div>
        </div>

        <div style={{ display: 'flex', alignItems: 'center', gap: '20px' }}>
          <div style={{ textAlign: 'right' }}>
            <div style={{ fontSize: '0.72rem', color: 'var(--text-secondary)', textTransform: 'uppercase', letterSpacing: '0.5px' }}>Projected Monthly TCO</div>
            <div style={{ fontSize: '1.8rem', fontWeight: 800, color: '#10b981', fontFamily: 'Outfit, sans-serif' }}>
              ₹{winner.totalMonthlyCost} <span style={{ fontSize: '0.8rem', color: 'var(--text-muted)' }}>/ mo</span>
            </div>
            <div style={{ fontSize: '0.8rem', color: '#06b6d4', fontWeight: 600 }}>
              Est. Annual Savings: ₹{totalAnnualSavings}
            </div>
          </div>

          <div style={{ display: 'flex', flexDirection: 'column', gap: '8px' }}>
            <button className="btn-emerald" onClick={handleDownloadPdf} style={{ fontSize: '0.8rem', padding: '8px 14px' }}>
              <Download size={14} /> PDF Report
            </button>
            <button className="btn-outline" onClick={handleDownloadCsv} style={{ fontSize: '0.8rem', padding: '6px 12px' }}>
              <FileText size={14} /> Export CSV
            </button>
          </div>
        </div>
      </div>

      {/* Bar Chart Section */}
      <div className="glass-panel" style={{ padding: '20px' }}>
        <div style={{ marginBottom: '14px' }}>
          <h3 style={{ fontSize: '1.05rem', color: 'var(--text-primary)', margin: 0 }}>Multi-Cloud Monthly TCO Comparison</h3>
          <p style={{ fontSize: '0.78rem', color: 'var(--text-secondary)', margin: '2px 0 0 0' }}>Real-time calculated tariffs (Compute + Storage + Database + Bandwidth)</p>
        </div>

        <div style={{ width: '100%', height: 220 }}>
          <ResponsiveContainer>
            <BarChart data={chartData} margin={{ top: 15, right: 20, left: 10, bottom: 5 }}>
              <XAxis dataKey="name" stroke="#64748b" tick={{ fill: 'var(--text-secondary)', fontSize: 12 }} />
              <YAxis stroke="#64748b" tick={{ fill: 'var(--text-secondary)', fontSize: 12 }} unit="₹" />
              <Tooltip
                contentStyle={{ background: 'var(--bg-surface)', borderColor: 'var(--border-color)', borderRadius: '8px', color: 'var(--text-primary)' }}
                formatter={(value) => [`₹${value}`, 'Monthly Cost']}
              />
              <Bar dataKey="monthlyCost" radius={[6, 6, 0, 0]}>
                {chartData.map((entry, index) => (
                  <Cell key={`cell-${index}`} fill={PROVIDER_COLORS[entry.providerId] || '#06b6d4'} />
                ))}
              </Bar>
            </BarChart>
          </ResponsiveContainer>
        </div>
      </div>

      {/* Provider Matrix Cards */}
      <div style={{ display: 'grid', gridTemplateColumns: 'repeat(4, 1fr)', gap: '14px' }}>
        {recommendations.map((rec, idx) => (
          <div
            key={rec.providerId}
            className="glass-panel"
            style={{
              padding: '16px',
              borderTop: `4px solid ${PROVIDER_COLORS[rec.providerId] || '#06b6d4'}`,
              position: 'relative',
              display: 'flex',
              flexDirection: 'column',
              justifyContent: 'space-between'
            }}
          >
            <div>
              <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '6px' }}>
                <span className="mono" style={{ fontSize: '0.75rem', fontWeight: 700, color: PROVIDER_COLORS[rec.providerId] }}>
                  #{idx + 1} {rec.providerId}
                </span>
                <span className="badge badge-cyan" style={{ fontSize: '0.7rem' }}>{rec.recommendationScore} Score</span>
              </div>

              <h4 style={{ fontSize: '0.95rem', margin: '4px 0 10px 0', color: 'var(--text-primary)' }}>{rec.providerName}</h4>
              <div style={{ fontSize: '1.3rem', fontWeight: 700, color: 'var(--text-primary)', marginBottom: '8px', fontFamily: 'Outfit, sans-serif' }}>
                ₹{rec.totalMonthlyCost} <span style={{ fontSize: '0.75rem', color: 'var(--text-muted)' }}>/ mo</span>
              </div>
            </div>

            <div style={{ fontSize: '0.78rem', color: 'var(--text-secondary)', borderTop: '1px solid rgba(255, 255, 255, 0.05)', paddingTop: '10px', display: 'flex', flexDirection: 'column', gap: '4px' }}>
              <div style={{ display: 'flex', justifyContent: 'space-between' }}>
                <span>Yearly:</span> <strong style={{ color: 'var(--text-secondary)' }}>₹{rec.totalYearlyCost}</strong>
              </div>
              <div style={{ display: 'flex', justifyContent: 'space-between' }}>
                <span>Est. Savings:</span> <strong style={{ color: '#10b981' }}>₹{rec.estimatedSavings}</strong>
              </div>
              <div style={{ display: 'flex', justifyContent: 'space-between' }}>
                <span>SLA Uptime:</span> <strong style={{ color: '#3b82f6' }}>{rec.regionSlaUptime}%</strong>
              </div>
            </div>
          </div>
        ))}
      </div>

    </div>
  );
};

export default CostComparisonDashboard;
