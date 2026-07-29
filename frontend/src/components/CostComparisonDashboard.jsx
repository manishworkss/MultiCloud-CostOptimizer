import React from 'react';
import { BarChart, Bar, XAxis, YAxis, Tooltip, ResponsiveContainer, Cell } from 'recharts';
import { TrendingDown, Download, Award, ShieldCheck, DollarSign, FileText } from 'lucide-react';
import api from '../services/api';

const PROVIDER_COLORS = {
  AWS: '#ff9900',
  AZURE: '#0089d6',
  GCP: '#4285f4',
  OCI: '#c74634',
};

const CostComparisonDashboard = ({ recommendations, requestId }) => {
  if (!recommendations || recommendations.length === 0) return null;

  const winner = recommendations[0];
  const maxCostRec = recommendations[recommendations.length - 1];
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
      const url = window.URL.createObjectURL(new Blob([response.data], { type: 'text/csv' }));
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
    <div style={{ display: 'flex', flexDirection: 'column', gap: '24px' }}>
      {/* 1. Winner Callout Banner */}
      <div className="glass-panel glow-box-emerald" style={{ padding: '24px', display: 'flex', alignItems: 'center', justifyContent: 'space-between' }}>
        <div style={{ display: 'flex', alignItems: 'center', gap: '16px' }}>
          <div style={{ background: 'rgba(16, 185, 129, 0.2)', padding: '14px', borderRadius: '12px', color: '#10b981' }}>
            <Award size={36} />
          </div>
          <div>
            <div className="badge badge-emerald" style={{ marginBottom: '6px', display: 'inline-block' }}>
              #1 Optimal Placement Recommendation
            </div>
            <h2 style={{ fontSize: '1.4rem' }}>
              {winner.providerName} ({winner.serviceName})
            </h2>
            <p style={{ color: '#94a3b8', fontSize: '0.85rem' }}>
              Score: <strong style={{ color: '#10b981' }}>{winner.recommendationScore} / 100</strong> | Region SLA Uptime: <strong>{winner.regionSlaUptime}%</strong>
            </p>
          </div>
        </div>

        <div style={{ textAlign: 'right' }}>
          <div style={{ fontSize: '0.8rem', color: '#94a3b8', textTransform: 'uppercase', letterSpacing: '0.5px' }}>Projected Monthly TCO</div>
          <div style={{ fontSize: '2rem', fontWeight: 800, color: '#10b981', fontFamily: 'Outfit, sans-serif' }}>
            ${winner.totalMonthlyCost} <span style={{ fontSize: '0.9rem', color: '#64748b' }}>/ mo</span>
          </div>
          <div style={{ fontSize: '0.85rem', color: '#06b6d4', fontWeight: 600 }}>
            Est. Annual Savings: ${totalAnnualSavings}
          </div>
        </div>
      </div>

      {/* 2. Recharts Bar Comparison Chart */}
      <div className="glass-panel" style={{ padding: '24px' }}>
        <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'space-between', marginBottom: '20px' }}>
          <div>
            <h3 style={{ fontSize: '1.1rem' }}>Multi-Cloud Monthly TCO Comparison</h3>
            <p style={{ fontSize: '0.8rem', color: '#94a3b8' }}>Calculated compute + storage + database + bandwidth</p>
          </div>
          <div style={{ display: 'flex', gap: '10px' }}>
            <button className="btn-outline" onClick={handleDownloadCsv} style={{ fontSize: '0.8rem', padding: '6px 12px' }}>
              <FileText size={14} /> Export CSV
            </button>
            <button className="btn-emerald" onClick={handleDownloadPdf} style={{ fontSize: '0.8rem', padding: '6px 12px' }}>
              <Download size={14} /> Download PDF Audit Report
            </button>
          </div>
        </div>

        <div style={{ width: '100%', height: 260 }}>
          <ResponsiveContainer>
            <BarChart data={chartData} margin={{ top: 20, right: 30, left: 20, bottom: 5 }}>
              <XAxis dataKey="name" stroke="#64748b" tick={{ fill: '#94a3b8', fontSize: 12 }} />
              <YAxis stroke="#64748b" tick={{ fill: '#94a3b8', fontSize: 12 }} unit="$" />
              <Tooltip
                contentStyle={{ background: '#111827', borderColor: '#1e293b', borderRadius: '8px', color: '#f8fafc' }}
                formatter={(value) => [`$${value}`, 'Monthly Cost']}
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

      {/* 3. Provider Cards Matrix */}
      <div style={{ display: 'grid', gridTemplateColumns: 'repeat(4, 1fr)', gap: '16px' }}>
        {recommendations.map((rec, idx) => (
          <div
            key={rec.providerId}
            className="glass-panel"
            style={{
              padding: '20px',
              borderTop: `4px solid ${PROVIDER_COLORS[rec.providerId] || '#06b6d4'}`,
              position: 'relative',
            }}
          >
            <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '8px' }}>
              <span className="mono" style={{ fontSize: '0.75rem', fontWeight: 700, color: PROVIDER_COLORS[rec.providerId] }}>
                #{idx + 1} {rec.providerId}
              </span>
              <span className="badge badge-cyan">{rec.recommendationScore} Score</span>
            </div>

            <h4 style={{ fontSize: '1rem', marginBottom: '12px' }}>{rec.providerName}</h4>
            <div style={{ fontSize: '1.4rem', fontWeight: 700, color: '#f8fafc', marginBottom: '8px' }}>
              ${rec.totalMonthlyCost} <span style={{ fontSize: '0.75rem', color: '#64748b' }}>/ mo</span>
            </div>

            <div style={{ fontSize: '0.8rem', color: '#94a3b8', display: 'flex', flexDirection: 'column', gap: '4px' }}>
              <div>Yearly: <strong>${rec.totalYearlyCost}</strong></div>
              <div>Est. Savings: <strong style={{ color: '#10b981' }}>${rec.estimatedSavings}</strong></div>
              <div>SLA Uptime: <strong>{rec.regionSlaUptime}%</strong></div>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default CostComparisonDashboard;
