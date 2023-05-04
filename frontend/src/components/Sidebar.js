import React from 'react';

const Sidebar = () => {
  return (
    <aside className="sidebar">
      <ul className="sidebar__nav">
        <li className="sidebar__nav-item">
          <a href="/dashboard" activeClassName="sidebar__nav-link--active" className="sidebar__nav-link">Dashboard</a>
        </li>
        <li className="sidebar__nav-item">
          <a href="/users" activeClassName="sidebar__nav-link--active" className="sidebar__nav-link">Users</a>
        </li>
        <li className="sidebar__nav-item">
          <a href="/behaviors" activeClassName="sidebar__nav-link--active" className="sidebar__nav-link">Behaviors</a>
        </li>
        <li className="sidebar__nav-item">
          <a href="/integrations" activeClassName="sidebar__nav-link--active" className="sidebar__nav-link">Integrations</a>
        </li>
        <li className="sidebar__nav-item">
          <a href="/map" activeClassName="sidebar__nav-link--active" className="sidebar__nav-link">Map</a>
        </li>
      </ul>
    </aside>
  );
};

export default Sidebar;
