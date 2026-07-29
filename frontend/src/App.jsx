import React, { useState, useContext } from 'react';
import { AuthContext } from './context/AuthContext';
import Navbar from './components/Navbar';
import Sidebar from './components/Sidebar';
import KpiMetrics from './components/KpiMetrics';
import AuthScreen from './components/AuthScreen';
import MfaModal from './components/MfaModal';
import DeploymentSpecForm from './components/DeploymentSpecForm';
import CostComparisonDashboard from './components/CostComparisonDashboard';
import api from './services/api';
import { Activity, LayoutDashboard, RefreshCw } from 'lucide-react';

function App() {
  const { user, loading, mfaRequired } = useContext(AuthContext);

  const [activeTab, setActiveTab] = useState('placement');
  const [isMfaModalOpen, setIsMfaModalOpen] = useState(false);
  const [isMfaSetupMode, setIsMfaSetupMode] = useState(false);

  const [evaluating, setEvaluating] = useState(false);
  const [recommendations, setRecommendations] = useState(null);
  const [currentRequestId, setCurrentRequestId] = useState(null);

  const handleOpenMfaSetup = () => {
    setIsMfaSetupMode(true);
    setIsMfaModalOpen(true);
  };

  const handleSubmitSpec = async (specData) => {
    setEvaluating(true);
    try {
      // 1. Initialize project
      const projRes = await api.post('/projects', {
        projectName: 'Enterprise Workload placement',
        applicationType: 'Microservices / Web API',
      });
      const projectId = projRes.data.projectId;

      // 2. Submit spec request
      const reqRes = await api.post(`/projects/${projectId}/requests`, specData);
      const requestId = reqRes.data.requestId;
      setCurrentRequestId(requestId);

      // 3. Trigger evaluation engine
      const evalRes = await api.post(`/requests/${requestId}/evaluate`);
      setRecommendations(evalRes.data);
    } catch (err) {
      alert('Error evaluating multi-cloud pricing');
    } finally {
      setEvaluating(false);
    }
  };

  if (loading) {
    return (
      <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'center', minHeight: '100vh', color: '#06b6d4' }}>
        <Activity size={32} className="spinning" /> Loading CostMatrix FinOps Platform...
      </div>
    );
  }

  return (
    <div style={{ minHeight: '100vh', background: '#070a12', color: '#f8fafc' }}>
      {user ? (
        /* Logged-In Professional Enterprise FinOps Dashboard */
        <>
          <Navbar onOpenMfaSetup={handleOpenMfaSetup} />
          
          <div style={{ display: 'flex', minHeight: 'calc(100vh - 65px)' }}>
            {/* Sidebar Navigation */}
            <Sidebar activeTab={activeTab} setActiveTab={setActiveTab} />

            {/* Main Content Viewport */}
            <main style={{ flex: 1, padding: '28px 36px', overflowY: 'auto' }}>
              
              {/* Header Title Bar */}
              <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'space-between', marginBottom: '24px' }}>
                <div>
                  <h1 style={{ fontSize: '1.6rem', margin: 0, fontFamily: 'Outfit, sans-serif', color: '#f8fafc' }}>
                    Multi-Cloud Infrastructure Placement Engine
                  </h1>
                  <p style={{ color: '#94a3b8', fontSize: '0.85rem', margin: '4px 0 0 0' }}>
                    Benchmark real-time tariffs across AWS, Azure, GCP, and OCI for workload placement optimization.
                  </p>
                </div>

                <div style={{ display: 'flex', gap: '12px' }}>
                  <button
                    className="btn-outline"
                    onClick={() => {
                      if (recommendations) setRecommendations([...recommendations]);
                    }}
                    style={{ fontSize: '0.8rem', padding: '8px 14px', display: 'flex', alignItems: 'center', gap: '6px' }}
                  >
                    <RefreshCw size={14} /> Refresh Live Rates
                  </button>
                </div>
              </div>

              {/* KPI Metrics Summary Row */}
              <KpiMetrics recommendations={recommendations} />

              {/* Main Dual-Column Grid Layout */}
              <div style={{ display: 'grid', gridTemplateColumns: '340px 1fr', gap: '24px', alignItems: 'start' }}>
                
                {/* Left Configurator Column */}
                <div>
                  <DeploymentSpecForm onSubmitSpec={handleSubmitSpec} loading={evaluating} />
                </div>

                {/* Right Results Column */}
                <div>
                  <CostComparisonDashboard recommendations={recommendations} requestId={currentRequestId} />
                </div>

              </div>

            </main>
          </div>

          <MfaModal
            isOpen={isMfaModalOpen || mfaRequired}
            onClose={() => setIsMfaModalOpen(false)}
            isSetupMode={isMfaSetupMode}
          />
        </>
      ) : (
        /* Realistic Auth Screen */
        <AuthScreen />
      )}
    </div>
  );
}

export default App;
