import React, { createContext, useState, useEffect } from 'react';
import api from '../services/api';

export const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null);
  const [preMfaToken, setPreMfaToken] = useState(null);
  const [mfaRequired, setMfaRequired] = useState(false);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const token = localStorage.getItem('accessToken');
    const storedUser = localStorage.getItem('userData');
    if (token && storedUser) {
      setUser(JSON.parse(storedUser));
    }
    setLoading(false);
  }, []);

  const login = async (email, password) => {
    const response = await api.post('/auth/login', { email, password });
    const data = response.data;

    if (data.mfaRequired) {
      setMfaRequired(true);
      setPreMfaToken(data.preMfaToken);
      return { mfaRequired: true };
    }

    localStorage.setItem('accessToken', data.accessToken);
    localStorage.setItem('userData', JSON.stringify(data));
    setUser(data);
    setMfaRequired(false);
    return { success: true };
  };

  const register = async (name, email, password) => {
    const response = await api.post('/auth/register', { name, email, password });
    const data = response.data;
    localStorage.setItem('accessToken', data.accessToken);
    localStorage.setItem('userData', JSON.stringify(data));
    setUser(data);
    return data;
  };

  const verifyMfa = async (totpCode) => {
    const response = await api.post('/auth/mfa/verify', {
      preMfaToken: preMfaToken || (user ? user.userId : ''),
      totpCode,
    });
    const data = response.data;
    localStorage.setItem('accessToken', data.accessToken);
    localStorage.setItem('userData', JSON.stringify(data));
    setUser(data);
    setMfaRequired(false);
    setPreMfaToken(null);
    return data;
  };

  const setupMfa = async () => {
    const response = await api.post('/auth/mfa/setup');
    return response.data; // { secretKey, qrCodeUrl, backupCodes }
  };

  const logout = () => {
    localStorage.removeItem('accessToken');
    localStorage.removeItem('userData');
    setUser(null);
    setMfaRequired(false);
    setPreMfaToken(null);
  };

  return (
    <AuthContext.Provider
      value={{
        user,
        loading,
        mfaRequired,
        login,
        register,
        verifyMfa,
        setupMfa,
        logout,
      }}
    >
      {children}
    </AuthContext.Provider>
  );
};
