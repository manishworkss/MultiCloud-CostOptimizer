import React from 'react';
import { ArrowRight, Compass, Target, Bot, CheckCircle2, CloudLightning, ShieldCheck, Zap } from 'lucide-react';

const AboutProjectDashboard = () => {
  return (
    <div style={{ padding: '40px 20px', maxWidth: '1100px', margin: '0 auto', display: 'flex', flexDirection: 'column', gap: '80px' }}>
      
      {/* 1. Hero Section */}
      <div style={{ textAlign: 'center', marginTop: '20px' }}>
        <div style={{ 
          display: 'inline-flex', 
          alignItems: 'center', 
          gap: '8px', 
          padding: '6px 16px', 
          borderRadius: '100px', 
          background: 'rgba(6, 182, 212, 0.1)', 
          border: '1px solid rgba(6, 182, 212, 0.3)',
          color: '#06b6d4',
          fontSize: '0.85rem',
          fontWeight: 500,
          marginBottom: '32px'
        }}>
          <Zap size={14} /> Introducing CostMatrix 2.0
        </div>
        
        <h1 style={{ 
          fontSize: '4.5rem', 
          lineHeight: '1.1', 
          fontWeight: 800, 
          letterSpacing: '-1.5px',
          margin: '0 0 24px 0',
          color: 'var(--text-primary)'
        }}>
          Optimize your cloud spend with <br />
          <span style={{ 
            background: 'linear-gradient(to right, #06b6d4, #3b82f6)', 
            WebkitBackgroundClip: 'text',
            WebkitTextFillColor: 'transparent'
          }}>AI precision</span>
        </h1>
        
        <p style={{ 
          fontSize: '1.15rem', 
          color: 'var(--text-secondary)', 
          maxWidth: '640px', 
          margin: '0 auto 40px auto',
          lineHeight: '1.6'
        }}>
          Stop guessing your monthly cloud bill. Get data-driven deployment recommendations, live tariffs, and an AI FinOps mentor to guide your enterprise architecture.
        </p>

        <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'center', gap: '16px' }}>
          <button style={{
            background: 'linear-gradient(to right, #06b6d4, #0284c7)',
            color: 'white',
            border: 'none',
            padding: '14px 28px',
            borderRadius: '8px',
            fontSize: '1rem',
            fontWeight: 600,
            cursor: 'pointer',
            display: 'flex',
            alignItems: 'center',
            gap: '8px',
            boxShadow: '0 4px 14px rgba(6, 182, 212, 0.3)'
          }}>
            Evaluate Placement <ArrowRight size={18} />
          </button>
          <button style={{
            background: 'transparent',
            color: 'var(--text-secondary)',
            border: '1px solid #334155',
            padding: '14px 28px',
            borderRadius: '8px',
            fontSize: '1rem',
            fontWeight: 600,
            cursor: 'pointer'
          }}>
            View Tariffs
          </button>
        </div>
      </div>

      {/* 2. Features Section */}
      <div style={{ textAlign: 'center' }}>
        <h2 style={{ fontSize: '2.5rem', fontWeight: 800, margin: '0 0 16px 0', letterSpacing: '-0.5px' }}>
          Everything you need to succeed
        </h2>
        <p style={{ color: 'var(--text-secondary)', fontSize: '1.1rem', margin: '0 auto 48px auto', maxWidth: '600px' }}>
          A comprehensive suite of FinOps tools designed to analyze your infrastructure footprint and map out your perfect deployment trajectory.
        </p>

        <div style={{ display: 'grid', gridTemplateColumns: 'repeat(3, 1fr)', gap: '24px' }}>
          {[
            {
              icon: <Compass size={28} color="#06b6d4" />,
              title: 'Multi-Cloud Discovery',
              desc: 'Our engine analyzes your compute and storage requirements to recommend the most cost-effective hyperscaler.'
            },
            {
              icon: <Target size={28} color="#3b82f6" />,
              title: 'Placement Engine',
              desc: 'Get step-by-step, actionable TCO breakdowns to confidently deploy into AWS, Azure, GCP, or Oracle.'
            },
            {
              icon: <Bot size={28} color="#10b981" />,
              title: 'AI FinOps Mentor',
              desc: 'Chat with a specialized Llama-3 agent that knows cloud pricing models and answers your complex infrastructure questions.'
            }
          ].map((feat, idx) => (
            <div key={idx} style={{
              background: 'var(--bg-surface)',
              border: '1px solid #1e293b',
              borderRadius: '16px',
              padding: '36px 24px',
              textAlign: 'left',
              boxShadow: '0 10px 30px rgba(0,0,0,0.2)'
            }}>
              <div style={{ 
                background: 'var(--bg-glass)', 
                width: '56px', 
                height: '56px', 
                borderRadius: '12px', 
                display: 'flex', 
                alignItems: 'center', 
                justifyContent: 'center',
                marginBottom: '24px',
                border: '1px solid rgba(255,255,255,0.05)'
              }}>
                {feat.icon}
              </div>
              <h3 style={{ fontSize: '1.3rem', fontWeight: 700, margin: '0 0 12px 0', color: 'var(--text-primary)' }}>{feat.title}</h3>
              <p style={{ color: 'var(--text-secondary)', margin: 0, lineHeight: '1.6', fontSize: '0.95rem' }}>{feat.desc}</p>
            </div>
          ))}
        </div>
      </div>

      {/* 3. How It Works Section */}
      <div style={{ textAlign: 'center', marginTop: '20px' }}>
        <h2 style={{ fontSize: '2.5rem', fontWeight: 800, margin: '0 0 48px 0', letterSpacing: '-0.5px' }}>
          How CostMatrix Works
        </h2>

        <div style={{ display: 'flex', flexDirection: 'column', gap: '20px', maxWidth: '800px', margin: '0 auto' }}>
          {[
            { num: '01', title: 'Submit Infrastructure Spec', desc: 'Share your workload requirements: vCPUs, RAM, storage, and networking needs.' },
            { num: '02', title: 'Review AI Recommendations', desc: 'Our engine cross-references your footprint against real-time public cloud tariffs.' },
            { num: '03', title: 'Deploy with Confidence', desc: 'Execute your deployment on the chosen provider backed by hard FinOps data.' }
          ].map((step, idx) => (
            <div key={idx} style={{
              background: 'var(--bg-surface)',
              border: '1px solid #1e293b',
              borderRadius: '16px',
              padding: '32px 40px',
              display: 'flex',
              alignItems: 'center',
              gap: '32px',
              textAlign: 'left'
            }}>
              <div style={{ fontSize: '3.5rem', fontWeight: 800, color: 'rgba(6, 182, 212, 0.2)', letterSpacing: '-2px' }}>
                {step.num}
              </div>
              <div>
                <h3 style={{ fontSize: '1.4rem', fontWeight: 700, margin: '0 0 8px 0', color: 'var(--text-primary)' }}>{step.title}</h3>
                <p style={{ color: 'var(--text-secondary)', margin: 0, fontSize: '1rem', lineHeight: '1.5' }}>{step.desc}</p>
              </div>
            </div>
          ))}
        </div>
      </div>

      {/* 4. CTA Footer */}
      <div style={{ textAlign: 'center', margin: '60px 0 40px 0' }}>
        <h2 style={{ fontSize: '2.8rem', fontWeight: 800, margin: '0 0 16px 0', letterSpacing: '-1px' }}>
          Ready to cut your cloud bill?
        </h2>
        <p style={{ color: 'var(--text-secondary)', fontSize: '1.1rem', margin: '0 auto 36px auto' }}>
          Join infrastructure teams taking the guesswork out of their multi-cloud deployments.
        </p>
        <button style={{
          background: 'linear-gradient(to right, #06b6d4, #0284c7)',
          color: 'white',
          border: 'none',
          padding: '16px 36px',
          borderRadius: '8px',
          fontSize: '1.1rem',
          fontWeight: 600,
          cursor: 'pointer',
          boxShadow: '0 4px 20px rgba(6, 182, 212, 0.3)'
        }}>
          Optimize Now
        </button>
      </div>

    </div>
  );
};

export default AboutProjectDashboard;
