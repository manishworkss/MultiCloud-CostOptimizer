import React, { useState, useContext } from 'react';
import { AuthContext } from './context/AuthContext';
import Navbar from './components/Navbar';
import MfaModal from './components/MfaModal';
import DeploymentSpecForm from './components/DeploymentSpecForm';
import CostComparisonDashboard from './components/CostComparisonDashboard';
import api from './services/api';
import { ShieldCheck, Lock, ArrowRight, Activity, Cpu } from 'lucide-react';

function App() {
  const { user, loading, mfaRequired, login, register } = useContext(AuthContext);

  const [isRegisterMode, setIsRegisterMode] = useState(false);
  const [name, setName] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [authError, setAuthError] = useState('');
  const [authSubmitting, setAuthSubmitting] = useState(false);

  const [isMfaModalOpen, setIsMfaModalOpen] = useState(false);
  const [isMfaSetupMode, setIsMfaSetupMode] = useState(false);

  const [evaluating, setEvaluating] = useState(false);
  const [recommendations, setRecommendations] = useState(null);
  const [currentRequestId, setCurrentRequestId] = useState(null);

  const handleAuthSubmit = async (e) => {
    e.preventDefault();
    setAuthError('');
    setAuthSubmitting(true);
    try {
      if (isRegisterMode) {
        await register(name, email, password);
      } else {
        const res = await login(email, password);
        if (res.mfaRequired) {
          setIsMfaSetupMode(false);
          setIsMfaModalOpen(true);
        }
      }
    } catch (err) {
      setAuthError(err.response?.data?.message || 'Authentication failed');
    } finally {
      setAuthSubmitting(false);
    }
  };

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
        <Activity size={32} className="spinning" /> Loading CostMatrix...
      </div>
    );
  }

  return (
    <div>
      <Navbar onOpenMfaSetup={handleOpenMfaSetup} />

      {!user ? (
        /* Authentication Screen (Login / Register) */
        <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'center', minHeight: 'calc(100vh - 80px)', padding: '24px' }}>
          <div className="glass-panel glow-box-cyan" style={{ padding: '36px', width: '100%', maxWidth: '420px' }}>
            <div style={{ textAlign: 'center', marginBottom: '24px' }}>
              <div style={{ display: 'inline-flex', padding: '12px', background: 'rgba(6, 182, 212, 0.15)', borderRadius: '12px', color: '#06b6d4', marginBottom: '12px' }}>
                <Cpu size={32} />
              </div>
              <h2 style={{ fontSize: '1.5rem' }}>
                {isRegisterMode ? 'Create CostMatrix Account' : 'Sign in to CostMatrix'}
              </h2>
              <p style={{ fontSize: '0.85rem', color: '#94a3b8', marginTop: '4px' }}>
                Multi-Cloud FinOps &amp; Infrastructure Placement
              </p>
            </div>

            {authError && (
              <div style={{ background: 'rgba(244, 63, 94, 0.15)', border: '1px solid #f43f5e', color: '#f43f5e', padding: '10px', borderRadius: '6px', fontSize: '0.85rem', marginBottom: '16px' }}>
                {authError}
              </div>
            )}

            <form onSubmit={handleAuthSubmit}>
              {isRegisterMode && (
                <div className="form-group">
                  <label>Full Name</label>
                  <input type="text" className="form-control" placeholder="DevOps Lead" value={name} onChange={(e) => setName(e.target.value)} required />
                </div>
              )}

              <div className="form-group">
                <label>Email Address</label>
                <input type="email" className="form-control" placeholder="admin@enterprise.com" value={email} onChange={(e) => setEmail(e.target.value)} required />
              </div>

              <div className="form-group">
                <label>Password</label>
                <input type="password" className="form-control" placeholder="••••••••" value={password} onChange={(e) => setPassword(e.target.value)} required />
              </div>

              <button type="submit" className="btn-primary" disabled={authSubmitting} style={{ width: '100%', marginTop: '12px', justifyContent: 'center', padding: '12px' }}>
                {authSubmitting ? 'Authenticating...' : isRegisterMode ? 'Register Account' : 'Sign In'} <ArrowRight size={16} />
              </button>
            </form>

            <div style={{ textAlign: 'center', marginTop: '20px' }}>
              <button
                onClick={() => { setIsRegisterMode(!isRegisterMode); setAuthError(''); }}
                style={{ background: 'none', border: 'none', color: '#06b6d4', fontSize: '0.85rem', cursor: 'pointer', textDecoration: 'underline' }}
              >
                {isRegisterMode ? 'Already have an account? Sign in' : "Don't have an account? Register"}
              </button>
            </div>
          </div>
        </div>
      ) : (
        /* Main Logged-In Workload Placement Application */
        <div style={{ maxWidth: '1200px', margin: '0 auto', padding: '32px 24px' }}>
          <div style={{ marginBottom: '28px' }}>
            <h1 style={{ fontSize: '1.8rem' }}>Multi-Cloud Infrastructure Placement Engine</h1>
            <p style={{ color: '#94a3b8', fontSize: '0.9rem' }}>
              Select target compute, memory, database, and regional traffic requirements to evaluate live AWS, Azure, GCP, and OCI tariffs.
            </p>
          </div>

          <DeploymentSpecForm onSubmitSpec={handleSubmitSpec} loading={evaluating} />

          <div style={{ marginTop: '32px' }}>
            <CostComparisonDashboard recommendations={recommendations} requestId={currentRequestId} />
          </div>
        </div>
      )}

      <MfaModal
        isOpen={isMfaModalOpen || mfaRequired}
        onClose={() => setIsMfaModalOpen(false)}
        isSetupMode={isMfaSetupMode}
      />
    </div>
  );
}

export default App;
