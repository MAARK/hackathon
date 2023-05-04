import React from 'react';
import { BrowserRouter, Routes, Route } from "react-router-dom";
import Dashboard from './Dashboard';
import Users from './Users';
import Behaviors from './Behaviors';
import Integrations from './Integrations';
import Map from './Map';

const MainContent = () => {
  return (
    <main className="main-content">
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<Dashboard />} />
          <Route path="*" element={<Dashboard />} />
          <Route path="/dashboard" element={<Dashboard />} />
          <Route path="/users" element={<Users />} />
          <Route path="/behaviors" element={<Behaviors />} />
          <Route path="/integrations" element={<Integrations />} />
          <Route path="/map" element={<Map />} />
        </Routes>
      </BrowserRouter>
    </main>
  );
};

export default MainContent;
