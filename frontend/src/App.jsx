import React, { useState, useContext } from 'react';
import { AuthContext } from './context/AuthContext';
import Navbar from './components/Navbar';
import AuthScreen from './components/AuthScreen';
import MfaModal from './components/MfaModal';
import DeploymentSpecForm from './components/DeploymentSpecForm';
import CostComparisonDashboard from './components/CostComparisonDashboard';
import api from './services/api';
import { Activity } from 'lucide-react';

function App() {
  const { user, loading, mfaRequired } = useContext(AuthContext);

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
        <Activity size={32} className="spinning" /> Loading CostMatrix...
      </div>
    );
  }

  return (
    <div>
      {user ? (
        /* Main Logged-In Workload Placement Application */
        <>
          <Navbar onOpenMfaSetup={handleOpenMfaSetup} />
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

          <MfaModal
            isOpen={isMfaModalOpen || mfaRequired}
            onClose={() => setIsMfaModalOpen(false)}
            isSetupMode={isMfaSetupMode}
          />
        </>
      ) : (
        /* Realistic Split-Screen Sign In & Email OTP Auth Screen referencing Picture 2 */
        <AuthScreen />
      )}
    </div>
  );
}

export default App;
