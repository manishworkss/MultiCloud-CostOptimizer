import React from 'react';
import { DollarSign, TrendingDown, Server, ShieldCheck, Activity, Award } from 'lucide-react';

const KpiMetrics = ({ recommendations }) => {
  const hasData = recommendations && recommendations.length > 0;
  const winner = hasData ? recommendations[0] : null;
  const maxProvider = hasData ? recommendations[recommendations.length - 1] : null;

  const totalSavings = (winner && winner.estimatedSavings) ? (winner.estimatedSavings * 12).toFixed(2) : '0.00';
  const monthlyCost = winner ? winner.totalMonthlyCost : '0.00';
  const maxMonthly = maxProvider ? maxProvider.totalMonthlyCost : '0.00';

  return (
    <div style={{ display: 'grid', gridTemplateColumns: 'repeat(4, 1fr)', gap: '16px', marginBottom: '24px' }}>
      
      {/* KPI 1: Optimal Provider */}
      <div className="glass-panel" style={{ padding: '20px', display: 'flex', alignItems: 'center', gap: '14px' }}>
        <div style={{ background: 'rgba(6, 182, 212, 0.15)', padding: '12px', borderRadius: '12px', color: '#06b6d4' }}>
          <Award size={24} />
        </div>
        <div>
          <div style={{ fontSize: '0.75rem', color: '#94a3b8', textTransform: 'uppercase', fontWeight: 600 }}>Top Recommendation</div>
          <div style={{ fontSize: '1.2rem', fontWeight: 700, color: '#f8fafc', marginTop: '2px', fontFamily: 'Outfit, sans-serif' }}>
            {winner ? `${winner.providerName}` : 'AWS EC2 / Azure'}
          </div>
          <div style={{ fontSize: '0.75rem', color: '#06b6d4', fontWeight: 600 }}>
            {winner ? `Score: ${winner.recommendationScore}/100` : 'Ready to Evaluate'}
          </div>
        </div>
      </div>

      {/* KPI 2: Lowest Monthly TCO */}
      <div className="glass-panel" style={{ padding: '20px', display: 'flex', alignItems: 'center', gap: '14px' }}>
        <div style={{ background: 'rgba(16, 185, 129, 0.15)', padding: '12px', borderRadius: '12px', color: '#10b981' }}>
          <DollarSign size={24} />
        </div>
        <div>
          <div style={{ fontSize: '0.75rem', color: '#94a3b8', textTransform: 'uppercase', fontWeight: 600 }}>Optimal Monthly TCO</div>
          <div style={{ fontSize: '1.2rem', fontWeight: 700, color: '#10b981', marginTop: '2px', fontFamily: 'Outfit, sans-serif' }}>
            ${monthlyCost} <span style={{ fontSize: '0.75rem', color: '#64748b' }}>/mo</span>
          </div>
          <div style={{ fontSize: '0.75rem', color: '#64748b' }}>
            Max Market Rate: ${maxMonthly}
          </div>
        </div>
      </div>

      {/* KPI 3: Est. Annual Savings */}
      <div className="glass-panel" style={{ padding: '20px', display: 'flex', alignItems: 'center', gap: '14px' }}>
        <div style={{ background: 'rgba(139, 92, 246, 0.15)', padding: '12px', borderRadius: '12px', color: '#8b5cf6' }}>
          <TrendingDown size={24} />
        </div>
        <div>
          <div style={{ fontSize: '0.75rem', color: '#94a3b8', textTransform: 'uppercase', fontWeight: 600 }}>Projected Annual Savings</div>
          <div style={{ fontSize: '1.2rem', fontWeight: 700, color: '#8b5cf6', marginTop: '2px', fontFamily: 'Outfit, sans-serif' }}>
            ${totalSavings}
          </div>
          <div style={{ fontSize: '0.75rem', color: '#10b981', fontWeight: 600 }}>
            vs Highest Tier CSP
          </div>
        </div>
      </div>

      {/* KPI 4: Multi-Cloud Market Availability */}
      <div className="glass-panel" style={{ padding: '20px', display: 'flex', alignItems: 'center', gap: '14px' }}>
        <div style={{ background: 'rgba(59, 130, 246, 0.15)', padding: '12px', borderRadius: '12px', color: '#3b82f6' }}>
          <Server size={24} />
        </div>
        <div>
          <div style={{ fontSize: '0.75rem', color: '#94a3b8', textTransform: 'uppercase', fontWeight: 600 }}>Evaluated Cloud Vendors</div>
          <div style={{ fontSize: '1.2rem', fontWeight: 700, color: '#f8fafc', marginTop: '2px', fontFamily: 'Outfit, sans-serif' }}>
            4 Major CSPs
          </div>
          <div style={{ fontSize: '0.75rem', color: '#3b82f6', fontWeight: 600 }}>
            AWS • Azure • GCP • OCI
          </div>
        </div>
      </div>

    </div>
  );
};

export default KpiMetrics;
