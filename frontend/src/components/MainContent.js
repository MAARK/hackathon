import React from 'react';
import { BrowserRouter, Routes, Route } from "react-router-dom";
import Dashboard from './Dashboard';
import Users from './Users';
import Behaviors from './Behaviors';
//import Integrations from './Integrations';
//import Map from './Map';

const MainContent = () => {
  return (
    <main className="main-content">
      <Dashboard />
      <BrowserRouter>
        <Routes>
          <Route path="/dashboard" component={Dashboard} />
          <Route path="/users" component={Users} />
          <Route path="/behaviors" component={Behaviors} />
        </Routes>
      </BrowserRouter>
    </main>
  );
};

export default MainContent;
